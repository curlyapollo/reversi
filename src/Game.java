import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private final Field GAMEFIELD;

    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private final Player PLAYER1;

    private final Player PLAYER2;

    private Player turn;

    private int round;

    public Game(String name1, String name2) throws IOException {
        PLAYER1 = new Player(name1, Color.Black);
        PLAYER2 = new Player(name2, Color.White);
        GAMEFIELD = new Field();
        turn = PLAYER1;
        round = 1;
        processPVP();
    }

    public Game(String name1, boolean isAdvanced) {
        PLAYER1 = new Player(name1, Color.Black);
        if (!isAdvanced) {
            PLAYER2 = new Player("DeepBlue", Color.White);
        } else {
            PLAYER2 = new Player("DeepDeepBlue", Color.White);
        }
        GAMEFIELD = new Field();
        turn = PLAYER1;
        round = 1;
    }

    private int inputString() {
        int string = 0;
        do {
            try {
                System.out.print("Введи номер строки(1-8): ");
                string = Integer.parseInt(READER.readLine());
            } catch (IOException e) {
                System.err.println("Ошибка: " + e);
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат");
            }
        } while (string < 1 || string > GAMEFIELD.getSize());
        return string;
    }

    private int inputColumn() {
        int column = 0;
        do {
            try {
                System.out.print("Введи номер столбца(1-8): ");
                column = Integer.parseInt(READER.readLine());
            } catch (IOException e) {
                System.err.println("Ошибка: " + e);
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат");
            }
        } while (column < 1 || column > GAMEFIELD.getSize());
        return column;
    }

    public int processWithComputer(boolean isBeginner) throws IOException {
        System.out.println("Начинаем игру, первым ходит " + PLAYER1.getName());
        while (GAMEFIELD.isAble(PLAYER1.getColor()) || GAMEFIELD.isAble(PLAYER2.getColor())) {
            if (turn == PLAYER1) {
                GAMEFIELD.possibleMovesOnField(turn.getColor());
            }
            int resultOfMove;
            if (!GAMEFIELD.isAble(turn.getColor())) {
                System.out.println(turn.getName() + " пропускает ход.");
                if (turn == PLAYER1) {
                    turn = PLAYER2;
                } else {
                    turn = PLAYER1;
                }
            }
            if (turn == PLAYER1) {
                do {
                    backMoves();
                    int string = inputString();
                    int column = inputColumn();
                    resultOfMove = GAMEFIELD.makeAMoveByPlayer(turn.getColor(), string, column, false);
                    if (resultOfMove == 1) {
                        System.out.println("На эту клетку нельзя сходить. Сходите по-другому.");
                    }
                } while (resultOfMove == 1);
            } else {
                if (isBeginner) {
                    GAMEFIELD.makeAMoveByBeginner(PLAYER2.getColor());
                } else {
                    GAMEFIELD.makeAMoveByProfessional(PLAYER2.getColor());
                }
            }
            System.out.println(PLAYER1.getName() + " " +
                    GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) + ":" +
                    GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor())
                    + " " + PLAYER2.getName());
            if (turn == PLAYER1) {
                System.out.println(GAMEFIELD);
            }
            if (turn == PLAYER1) {
                turn = PLAYER2;
            } else {
                turn = PLAYER1;
            }
            round++;
        }
        System.out.println(GAMEFIELD + "Игра закончена.");
        if (GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) >
                GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor())) {
            System.out.println("Победил " + PLAYER1.getName());
        } else if (GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) <
                GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor())) {
            System.out.println("Победил " + PLAYER2.getName());
        }
        return GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor());
    }

    public int processPVP() throws IOException {
        System.out.println("Начинаем игру, первым ходит " + PLAYER1.getName());
        while (GAMEFIELD.isAble(PLAYER1.getColor()) || GAMEFIELD.isAble(PLAYER2.getColor())) {
            GAMEFIELD.possibleMovesOnField(turn.getColor());
            int resultOfMove;
            if (!GAMEFIELD.isAble(turn.getColor())) {
                System.out.println(turn.getName() + " пропускает ход.");
                if (turn == PLAYER1) {
                    turn = PLAYER2;
                } else {
                    turn = PLAYER1;
                }
            }
            do {
                backMoves();
                int string = inputString();
                int column = inputColumn();
                resultOfMove = GAMEFIELD.makeAMoveByPlayer(turn.getColor(), string, column, false);
                if (resultOfMove == 1) {
                    System.out.println("На эту клетку нельзя сходить. Сходите по-другому.");
                }
            } while (resultOfMove == 1);
            System.out.println(PLAYER1.getName() + " " +
                    GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) + ":" +
                    GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor())
                    + " " + PLAYER2.getName());
            if (turn == PLAYER1) {
                turn = PLAYER2;
            } else {
                turn = PLAYER1;
            }
            round++;
        }
        System.out.println(GAMEFIELD + "Игра закончена.");
        if (GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) >
                GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor())) {
            System.out.println("Победил " + PLAYER1.getName());
        } else if (GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) <
                GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor())) {
            System.out.println("Победил " + PLAYER2.getName());
        }
        return GAMEFIELD.getQuantityOfChips().get(PLAYER1.getColor()) * 100 +
                GAMEFIELD.getQuantityOfChips().get(PLAYER2.getColor());
    }

    private void backMoves() throws IOException {
        System.out.print("Если хочешь вернуться на n ходов введи '/z'," +
                " иначе нажмите просто 'Enter'(или что-угодно другое): ");
        var req = READER.readLine();
        if ("/z".equals(req)) {
            int moves = 0;
            do {
                try {
                    System.out.print("Количество ходов(0-" + ((round - 1) / 2) + "): ");
                    moves = Integer.parseInt(READER.readLine());
                } catch (IOException e) {
                    System.err.println("Ошибка: " + e);
                } catch (NumberFormatException e) {
                    System.err.println("Неверный формат");
                }
            } while (moves < 0 || moves > (round - 1) / 2);
            round -= moves * 2;
            GAMEFIELD.backData(moves * 2);
            GAMEFIELD.possibleMovesOnField(turn.getColor());
        }
    }
}
