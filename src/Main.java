import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private static void menu() throws IOException {
        String req, name1 = "", name2 = "", rep;
        int max1 = -1, max2 = -1;
        do {
            System.out.println("""
                    Выбери, какую хочешь игру:
                    1. Играть с другим игроком ('/pvp')
                    2. Играть с новичком-компьютером ('/beg')
                    3. Играть с профессионалом-компьютером ('/pro')""");
            req = READER.readLine();
        } while (!"/pvp".equals(req) && !"/beg".equals(req) && !"/pro".equals(req));
        System.out.println("Как зовут первого игрока?");
        name1 = READER.readLine();
        if ("/pvp".equals(req)) {
            System.out.println("Как зовут второго игрока?");
            name2 = READER.readLine();
        }
        do {
            switch (req) {
                case "/pvp" -> {
                    Game game = new Game(name1, name2);
                    if (max1 < game.processPVP() / 100) {
                        max1 = game.processPVP();
                    }
                    if (max2 < game.processPVP() % 100) {
                        max2 = game.processPVP();
                    }
                }
                case "/beg" -> {
                    Game game = new Game(name1, false);
                    if (max1 < game.processWithComputer(true)) {
                        max1 = game.processWithComputer(true);
                    }
                }
                case "/pro" -> {
                    Game game = new Game(name1, true);
                    if (max1 < game.processWithComputer(true)) {
                        max1 = game.processWithComputer(true);
                    }
                }
            }
            System.out.println("Если хочешь продолжить, введите '/rep'.");
            rep = READER.readLine();
        } while ("/rep".equals(rep));
        System.out.println("Максимальный выигрыш " + name1 + ": " + max1);
        if ("/pvp".equals(req)) {
            System.out.println("Максимальный выигрыш " + name2 + ": " + max2);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        menu();
    }
}