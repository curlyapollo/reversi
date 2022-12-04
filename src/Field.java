import java.util.*;

public class Field {
    private final Cell[][] DATA = new Cell[8][8];

    public int getSize() {
        return DATA.length;
    }

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_YELLOW = "\u001B[33m";

    public Map<Color, Integer> getQuantityOfChips() {
        Map<Color, Integer> quantityOfChips = new HashMap<>();
        quantityOfChips.put(Color.Black, 0);
        quantityOfChips.put(Color.White, 0);
        for (Cell[] string : DATA) {
            for (Cell cell : string) {
                if (cell.getColor() != Color.Empty) {
                    quantityOfChips.put(cell.getColor(), quantityOfChips.get(cell.getColor()) + 1);
                }
            }
        }
        return quantityOfChips;
    }

    public Field() {
        for (int i = 0; i < DATA.length; i++) {
            for (int j = 0; j < DATA.length; j++) {
                if (i == DATA.length / 2 - 1 && j == DATA.length / 2 - 1 || i == DATA.length / 2 && j == DATA.length / 2) {
                    DATA[i][j] = new Cell(i + 1, j + 1, Color.White);
                } else if (i == DATA.length / 2 - 1 && j == DATA.length / 2 || i == DATA.length / 2 && j == DATA.length / 2 - 1) {
                    DATA[i][j] = new Cell(i + 1, j + 1, Color.Black);
                } else {
                    DATA[i][j] = new Cell(i + 1, j + 1);
                }
            }
        }
    }

    public Pair bestMoveForBeginner(Color beginner) {
        double maxProfit = 0;
        List<Cell> cells = possibleMoves(beginner);
        Cell bestCell = cells.get(0);
        double profit;
        for (Cell cell : cells) {
            profit = 0;
            if (cell.isCorner) {
                profit += 0.8;
            } else if (cell.isEdge) {
                profit += 0.4;
            }
            profit += moveDownByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveDownLeftComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveLeftByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveUpLeftByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveUpByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveUpRightByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveRightByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1) + moveDownRightByComputer(beginner, cell.getString() - 1, cell.getColumn() - 1);
            if (profit > maxProfit) {
                bestCell = cell;
                maxProfit = profit;
            }
        }
        return new Pair(bestCell, maxProfit);
    }

    public Cell bestMoveForProfessional(Color professional) {
        double maxProfit = 0;
        List<Cell> cells = possibleMoves(professional);
        Cell bestCell = cells.get(0);
        double profit;
        for (Cell cell : cells) {
            profit = 0;
            if (cell.isCorner) {
                profit += 0.8;
            } else if (cell.isEdge) {
                profit += 0.4;
            }
            profit += moveDownByComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveDownLeftComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveLeftByComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveUpLeftByComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveUpByComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveUpRightByComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveRightByComputer(professional, cell.getString() - 1, cell.getColumn() - 1) + moveDownRightByComputer(professional, cell.getString() - 1, cell.getColumn() - 1);
            makeAMoveByPlayer(professional, cell.getString(), cell.getColumn(), false);
            if (professional == Color.White) {
                profit -= bestMoveForBeginner(Color.Black).getProfit();
            } else {
                profit -= bestMoveForBeginner(Color.White).getProfit();
            }
            backData(1);
            if (profit > maxProfit) {
                bestCell = cell;
                maxProfit = profit;
            }
            System.out.println(cell + " " + profit);
        }
        return bestCell;
    }

    private int moveDownByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = string + 1;
        while (i < DATA.length && DATA[i][column].getColor() != player && DATA[i][column].getColor() != Color.Empty) {
            i++;
        }
        if (i < DATA.length && DATA[i][column].getColor() == player) {
            for (int j = string + 1; j < i; j++) {
                if (!isChecking) {
                    DATA[j][column].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveDownByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = string + 1;
        while (i < DATA.length && DATA[i][column].getColor() != player && DATA[i][column].getColor() != Color.Empty) {
            i++;
        }
        if (i < DATA.length && DATA[i][column].getColor() == player) {
            for (int j = string + 1; j < i; j++) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void makeAMoveByBeginner(Color beginner) {
        Cell chosen = bestMoveForBeginner(beginner).getCell();
        makeAMoveByPlayer(beginner, chosen.getString(), chosen.getColumn(), false);
    }

    public void makeAMoveByProfessional(Color professional) {
        Cell chosen = bestMoveForProfessional(professional);
        makeAMoveByPlayer(professional, chosen.getString(), chosen.getColumn(), false);
    }

    public int makeAMoveByPlayer(Color player, int string, int column, boolean isChecking) {
        string--;
        column--;
        if (DATA[string][column].getColor() != Color.Empty || moveUpByPlayer(player, string, column, isChecking) + moveDownByPlayer(player, string, column, isChecking) + moveRightByPlayer(player, string, column, isChecking) + moveLeftByPlayer(player, string, column, isChecking) + moveDownRightByPlayer(player, string, column, isChecking) + moveDownLeftByPlayer(player, string, column, isChecking) + moveUpLeftByPlayer(player, string, column, isChecking) + moveUpRightByPlayer(player, string, column, isChecking) == 0) {
            return 1;
        }
        if (!isChecking) {
            DATA[string][column].setColor(player);
            for (int i = 0; i < DATA.length; i++) {
                for (int j = 0; j < DATA.length; j++) {
                    if (DATA[i][j].getConditions().size() != DATA[string][column].getConditions().size()) {
                        DATA[i][j].setColor(DATA[i][j].getColor());
                    }
                }
            }
        }
        return 0;
    }

    private int moveLeftByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = column - 1;
        while (i >= 0 && DATA[string][i].getColor() != player && DATA[string][i].getColor() != Color.Empty) {
            i--;
        }
        if (i >= 0 && DATA[string][i].getColor() == player) {
            for (int j = column - 1; j > i; j--) {
                if (!isChecking) {
                    DATA[string][j].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveLeftByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = column - 1;
        while (i >= 0 && DATA[string][i].getColor() != player && DATA[string][i].getColor() != Color.Empty) {
            i--;
        }
        if (i >= 0 && DATA[string][i].getColor() == player) {
            for (int j = column - 1; j > i; j--) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int moveUpRightByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = string - 1;
        int j = column + 1;
        while (i >= 0 && j < DATA.length && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i--;
            j++;
        }
        if (i >= 0 && j < DATA.length && DATA[i][j].getColor() == player) {
            for (int k = 1; k < string - i; k++) {
                if (!isChecking) {
                    DATA[string - k][column + k].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveUpRightByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = string - 1;
        int j = column + 1;
        while (i >= 0 && j < DATA.length && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i--;
            j++;
        }
        if (i >= 0 && j < DATA.length && DATA[i][j].getColor() == player) {
            for (int k = 1; k < string - i; k++) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int moveDownRightByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = string + 1;
        int j = column + 1;
        while (i < DATA.length && j < DATA.length && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i++;
            j++;
        }
        if (i < DATA.length && j < DATA.length && DATA[i][j].getColor() == player) {
            for (int k = 1; k < i - string; k++) {
                if (!isChecking) {
                    DATA[string + k][column + k].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveDownRightByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = string + 1;
        int j = column + 1;
        while (i < DATA.length && j < DATA.length && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i++;
            j++;
        }
        if (i < DATA.length && j < DATA.length && DATA[i][j].getColor() == player) {
            for (int k = 1; k < i - string; k++) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int moveDownLeftByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = string + 1;
        int j = column - 1;
        while (i < DATA.length && j >= 0 && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i++;
            j--;
        }
        if (i < DATA.length && j >= 0 && DATA[i][j].getColor() == player) {
            for (int k = 1; k < i - string; k++) {
                if (!isChecking) {
                    DATA[string + k][column - k].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveDownLeftComputer(Color player, int string, int column) {
        int counter = 0;
        int i = string + 1;
        int j = column - 1;
        while (i < DATA.length && j >= 0 && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i++;
            j--;
        }
        if (i < DATA.length && j >= 0 && DATA[i][j].getColor() == player) {
            for (int k = 1; k < i - string; k++) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int moveUpLeftByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = string - 1;
        int j = column - 1;
        while (i >= 0 && j >= 0 && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i--;
            j--;
        }
        if (i >= 0 && j >= 0 && DATA[i][j].getColor() == player) {
            for (int k = 1; k < string - i; k++) {
                if (!isChecking) {
                    DATA[string - k][column - k].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveUpLeftByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = string - 1;
        int j = column - 1;
        while (i >= 0 && j >= 0 && DATA[i][j].getColor() != player && DATA[i][j].getColor() != Color.Empty) {
            i--;
            j--;
        }
        if (i >= 0 && j >= 0 && DATA[i][j].getColor() == player) {
            for (int k = 1; k < string - i; k++) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int moveRightByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = column + 1;
        while (i < DATA.length && DATA[string][i].getColor() != player && DATA[string][i].getColor() != Color.Empty) {
            i++;
        }
        if (i < DATA.length && DATA[string][i].getColor() == player) {
            for (int j = column + 1; j < i; j++) {
                if (!isChecking) {
                    DATA[string][j].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveRightByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = column + 1;
        while (i < DATA.length && DATA[string][i].getColor() != player && DATA[string][i].getColor() != Color.Empty) {
            i++;
        }
        if (i < DATA.length && DATA[string][i].getColor() == player) {
            for (int j = column + 1; j < i; j++) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int moveUpByPlayer(Color player, int string, int column, boolean isChecking) {
        int counter = 0;
        int i = string - 1;
        while (i >= 0 && DATA[i][column].getColor() != player && DATA[i][column].getColor() != Color.Empty) {
            i--;
        }
        if (i >= 0 && DATA[i][column].getColor() == player) {
            for (int j = string - 1; j > i; j--) {
                if (!isChecking) {
                    DATA[j][column].setColor(player);
                }
                counter++;
            }
        }
        return counter;
    }

    private int moveUpByComputer(Color player, int string, int column) {
        int counter = 0;
        int i = string - 1;
        while (i >= 0 && DATA[i][column].getColor() != player && DATA[i][column].getColor() != Color.Empty) {
            i--;
        }
        if (i >= 0 && DATA[i][column].getColor() == player) {
            for (int j = string - 1; j > i; j--) {
                if (DATA[i][j].isEdge) {
                    counter += 2;
                } else {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void backData(int rounds) {
        for (int i = 0; i < DATA.length; i++) {
            for (int j = 0; j < DATA.length; j++) {
                DATA[i][j].setColor(DATA[i][j].getConditions().get(DATA[i][j].getConditions().size() - 1 - rounds));
                DATA[i][j].setConditions(DATA[i][j].getConditions().subList(0, DATA[i][j].getConditions().size() - 1 - rounds));
            }
        }
    }

    public boolean isAble(Color player) {
        for (int i = 1; i < DATA.length + 1; i++) {
            for (int j = 1; j < DATA.length + 1; j++) {
                if (makeAMoveByPlayer(player, i, j, true) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Cell> possibleMoves(Color player) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < DATA.length; i++) {
            for (int j = 0; j < DATA.length; j++) {
                if (makeAMoveByPlayer(player, i + 1, j + 1, true) == 0) {
                    cells.add(DATA[i][j]);
                }
            }
        }
        return cells;
    }

    public void possibleMovesOnField(Color player) {
        StringBuilder ans = new StringBuilder();
        ans.append("+---".repeat(DATA.length + 1)).append("+\n");
        ans.append("|   |");
        for (int i = 1; i <= DATA.length; i++) {
            ans.append(" ").append(i).append(" |");
        }
        ans.append("\n");
        ans.append("+---".repeat(DATA.length + 1)).append("+");
        System.out.println(ans);
        for (int i = 0; i < DATA.length; i++) {
            ans.append("| ").append(i + 1).append(" |");
            System.out.print("| " + (i + 1) + " |");
            for (int j = 0; j < DATA.length; j++) {
                switch (DATA[i][j].getColor()) {
                    case Empty -> {
                        if (makeAMoveByPlayer(player, i + 1, j + 1, true) == 0) {
                            System.out.print(ANSI_YELLOW + " @ " + ANSI_RESET + "|");
                        } else {
                            System.out.print("   |");
                        }
                    }
                    case Black -> System.out.print(" b |");
                    case White -> System.out.print(" w |");
                }
            }
            System.out.println();
            ans = new StringBuilder();
            ans.append("+---".repeat(DATA.length + 1)).append("+\n");
            System.out.print(ans);
        }
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append("+---".repeat(DATA.length + 1)).append("+\n");
        ans.append("|   |");
        for (int i = 1; i <= DATA.length; i++) {
            ans.append(" ").append(i).append(" |");
        }
        ans.append("\n");
        ans.append("+---".repeat(DATA.length + 1)).append("+\n");
        for (int i = 0; i < DATA.length; i++) {
            ans.append("| ").append(i + 1).append(" |");
            for (int j = 0; j < DATA.length; j++) {
                switch (DATA[i][j].getColor()) {
                    case Empty -> ans.append("   |");
                    case Black -> ans.append(" b |");
                    case White -> ans.append(" w |");
                }
            }
            ans.append("\n");
            ans.append("+---".repeat(DATA.length + 1)).append("+\n");
        }
        return ans.toString();
    }
}
