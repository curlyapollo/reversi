import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Color color;

    private final int STRING;

    private final int COLUMN;

    public int getColumn() {
        return COLUMN;
    }

    public int getString() {
        return STRING;
    }

    public boolean isCorner;

    public boolean isEdge;

    public void setColor(Color color) {
        this.color = color;
        conditions.add(color);
    }

    public Color getColor() {
        return color;
    }

    public Cell(int string, int column, Color col) {
        this.STRING = string;
        this.COLUMN = column;
        color = col;
        conditions.add(col);
        isCorner = string == 1 && column == 1 || string == 1 && column == 8 ||
                string == 8 && column == 1 || string == 8 && column == 8;
        isEdge = string == 1 || column == 1 || string == 8 || column == 8;
    }

    public Cell(int string, int column) {
        this.STRING = string;
        this.COLUMN = column;
        color = Color.Empty;
        conditions.add(Color.Empty);
        isCorner = string == 1 && column == 1 || string == 1 && column == 8 ||
                string == 8 && column == 1 || string == 8 && column == 8;
        isEdge = string == 1 || column == 1 || string == 8 || column == 8;
    }

    private List<Color> conditions = new ArrayList<Color>();

    public List<Color> getConditions() {
        return conditions;
    }

    public void setConditions(List<Color> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return STRING + " " + COLUMN;
    }
}
