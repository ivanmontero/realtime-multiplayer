import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private int x, y;

    public /*boolean*/ void update(){
        //boolean modified = false;
        if(Input.isKeyPressed(KeyEvent.VK_A)) {
            x--;
            //modified = true;
        }
        if(Input.isKeyPressed(KeyEvent.VK_D)) {
            x++;
            //modified = true;
        }
        if(Input.isKeyPressed(KeyEvent.VK_W)) {
            y--;
            //modified = true;
        }
        if(Input.isKeyPressed(KeyEvent.VK_S)) {
            y++;
            //modified = true;
        }
        //return modified;
    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x - 5, y - 5, 10, 10);
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
