import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Game {
    private Main main;
    private Client client;
    private Player player;
    private ConcurrentHashMap<Integer, UserData> userDatas;
    private boolean chatOn;
    private StringBuilder currentText;

    public Game(Main main){
        this.main = main;
        player = new Player();
        userDatas = new ConcurrentHashMap<Integer, UserData>(16, 0.75f, 2);
        currentText = new StringBuilder();
    }

    public void update(){
        if(!chatOn) {
            player.update();
        }
        while(Keyboard.next()) {
            if(Keyboard.getEventKeyState()) {
                if(!chatOn) {
                    switch(Keyboard.getEventKeyCode()) {
                        case KeyEvent.VK_ENTER:
                            chatOn = true;
                            break;
                    }
                } else {
                    switch(Keyboard.getEventKeyCode()) {
                        case KeyEvent.VK_ENTER:
                            chatOn = false;
                            //TODO: Send Message
                            currentText = new StringBuilder();
                            break;
                        default:
                            if(getStringLength(currentText.toString()) < main.getWidth() - 15)
                                currentText.append(Keyboard.getEventKeyChar());
                            break;
                    }
                }
            }
        }
    }

    public void render(Graphics2D g2d){
        player.render(g2d);
        g2d.setColor(Color.BLACK);
        for(UserData ud : userDatas.values()){
            g2d.fillRect(ud.getX() - 5, ud.getY() - 5, 10, 10);
        }
        if(chatOn) {
            g2d.setColor(new Color(40, 40, 40, 128));
            g2d.fillRect(0, 0, main.getWidth(), main.getHeight());
            g2d.setColor(new Color(20, 20, 20, 128));
            g2d.fillRect(0, main.getHeight() - main.getHeight() / 15, main.getWidth(), main.getHeight() / 15);
            g2d.setColor(new Color(222, 222, 222, 128));
            g2d.drawString(currentText.toString(), 5, main.getHeight() - main.getHeight()/40);
        }
    }

    public int getStringLength(String str) {
        return main.getGraphics().getFontMetrics(main.getFont()).stringWidth(str);
    }

    public void updateUserDatas(HashMap<Integer, UserData> uds){
        for(Integer i : uds.keySet()){
            if(!userDatas.containsKey(i))
                userDatas.put(i, uds.get(i));
            else
                userDatas.get(i).updateData(uds.get(i));
        }
    }

    public ClientPacket createPacket(){ // important
        return new ClientPacket(PacketConstants.UPDATE, player.getX(), player.getY());
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
