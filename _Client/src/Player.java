import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public static final int SIZE = 10;
    private int x, y;

    public Player() {
        x = Main.WINDOW_WIDTH/2;
        y = Main.WINDOW_HEIGHT/2;
    }
    public void update(){
        if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
            x--;
            if (x < SIZE / 2)
                x = SIZE / 2;
        }
        if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
            x++;
            if (x > Main.WINDOW_WIDTH - SIZE)
                x = Main.WINDOW_WIDTH - SIZE;
        }
        if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
            y--;
            if (y < SIZE / 2)
                y = SIZE / 2;
        }
        if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
            y++;
            if (y > Main.WINDOW_HEIGHT - 3 * SIZE)
                y = Main.WINDOW_HEIGHT - 3 * SIZE;
        }
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawString("x: " + x + "   y: " + y, 250, 300);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UserData toUserData(){
        return new UserData(x, y);
    }
}
