import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public static final int SIZE = 10;
    private int x, y;
    private boolean chatOn = false;
    private StringBuilder currentText;

    public Player() {
        x = Main.WINDOW_WIDTH/2;
        y = Main.WINDOW_HEIGHT/2;
        currentText = new StringBuilder();
    }

    /*
    TODO Move the chat GUI to the Game class
     */

    public void update(){
        if(Input.isKeyPressed(KeyEvent.VK_ENTER)) { //Make if enter is typed
            if(!chatOn) {
                chatOn = true;
                Input.resetTyped();
                currentText = new StringBuilder();
            } else {
                //TODO: send text
                chatOn = false;
            }
        }
        if(!chatOn) {
            if (Input.isKeyPressed(KeyEvent.VK_A)) {
                x--;
                if (x < SIZE / 2)
                    x = SIZE / 2;
            }
            if (Input.isKeyPressed(KeyEvent.VK_D)) {
                x++;
                if (x > Main.WINDOW_WIDTH - SIZE)
                    x = Main.WINDOW_WIDTH - SIZE;
            }
            if (Input.isKeyPressed(KeyEvent.VK_W)) {
                y--;
                if (y < SIZE / 2)
                    y = SIZE / 2;
            }
            if (Input.isKeyPressed(KeyEvent.VK_S)) {
                y++;
                if (y > Main.WINDOW_HEIGHT - 3 * SIZE)
                    y = Main.WINDOW_HEIGHT - 3 * SIZE;
            }
        } else {
            while(Input.hasNextTyped())
                currentText.append(Input.nextTyped());
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
