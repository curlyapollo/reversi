public class Pair {
    private final Cell CELL;

    private final double PROFIT;

    public Pair(Cell cell, double profit) {
        this.CELL = cell;
        this.PROFIT = profit;
    }

    public Cell getCell() {
        return CELL;
    }

    public double getProfit() {
        return PROFIT;
    }
}
