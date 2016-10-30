import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public static final int SIZE = 10;
    private int x, y;
    private Main main;
    private String text;

    public Player(Main main) {
        x = Main.WINDOW_WIDTH/2;
        y = Main.WINDOW_HEIGHT/2;
        text = "";
        this.main = main;
    }
    public void update(){
        if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
            x--;
            if (x < SIZE / 2)
                x = SIZE / 2;
        }
        if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
            x++;
            if (x > Main.WINDOW_WIDTH - SIZE/2)
                x = Main.WINDOW_WIDTH - SIZE/2;
        }
        if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
            y--;
            if (y < SIZE / 2)
                y = SIZE / 2;
        }
        if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
            y++;
            if (y > Main.WINDOW_HEIGHT - SIZE/2)
                y = Main.WINDOW_HEIGHT - SIZE/2;
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawString("x: " + x + "   y: " + y, 250, 300);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        //TODO: Text splitting
        g2d.drawString(text, x - g2d.getFontMetrics().stringWidth(text) / 2, y - 8);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setText(String str) {
        this.text = str;
    }

    public UserData toUserData(){
        return new UserData(x, y);
    }
}
