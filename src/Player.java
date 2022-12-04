public class Player {
    private final String NAME;

    private final Color COLOR;

    public Color getColor() {
        return COLOR;
    }

    public String getName() {
        return NAME;
    }

    public Player(String name, Color color) {
        this.NAME = name;
        this.COLOR = color;
    }
}
