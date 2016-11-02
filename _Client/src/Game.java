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
    private volatile String messageToSend;

    public Game(Main main){
        this.main = main;
        player = new Player(main);
        userDatas = new ConcurrentHashMap<Integer, UserData>(16, 0.75f, 2);
        currentText = new StringBuilder();
        messageToSend = "";
    }

    public void update(int delta){
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
                            if(client.isConnected()) {
                                messageToSend = currentText.toString();
                            }
                            //TODO: Send Message
                            //player.setText(currentText.toString());
                            //reset text
                            currentText = new StringBuilder();
                            break;
                        default:
                                switch(Keyboard.getEventKeyChar()) {
                                    case '\b':
                                        if(currentText.length() != 0) {
                                            currentText.deleteCharAt(currentText.length() - 1);
                                        }
                                        break;
                                    default:
                                        if(main.getGraphics().getFont().canDisplay(Keyboard.getEventKeyChar()) &&
                                                main.getStringWidth(currentText.toString()) < main.getWidth() - 15) {
                                            currentText.append(Keyboard.getEventKeyChar());
                                        }
                                        break;
                                }
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
            g2d.drawString(ud.getMessage(),
                    ud.getX() - g2d.getFontMetrics().stringWidth(ud.getMessage()) / 2, ud.getY() - 8);
        }
        if(chatOn) {
            g2d.setColor(new Color(40, 40, 40, 128));
            g2d.fillRect(0, 0, main.getWidth(), main.getHeight());
            g2d.setColor(new Color(20, 20, 20, 128));
            g2d.fillRect(0, main.getHeight() - main.getHeight() / 15, main.getWidth(), main.getHeight() / 15);
            g2d.setColor(new Color(222, 222, 222, 128));
            g2d.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
            g2d.drawString(currentText.toString(), 5, main.getHeight() - main.getHeight()/40);
            if(!client.isConnected()) {
                g2d.setColor(new Color(0, 0, 0, 128));
                g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g2d.drawString("You must be connected in order to chat", 5, main.getHeight() - main.getHeight()/10);
            }
        }
        if(Keyboard.isKeyPressed(KeyEvent.VK_TAB)) {
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.fillRect(40, 40, 420, 100);
            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
            int fontHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString("W,A,S,D  - Move character", 50, 60);
            g2d.drawString("ENTER    - Open chat box/Send Message", 50, 60 + fontHeight);
            g2d.drawString("ESC      - Connect to server", 50, 60 + fontHeight * 2);
            g2d.drawString("TAB      - Show controls", 50, 60 + fontHeight * 3);
        }
        if(!client.isConnected()) {
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            g2d.drawString("ESC to connect", 0, main.getFontMetrics().getAscent());
        }
    }

    public void updateUserDatas(HashMap<Integer, UserData> uds){
        for(int i : uds.keySet()){
            if(!userDatas.containsKey(i))
                userDatas.put(i, uds.get(i));
            else
                userDatas.get(i).clientUpdateData(uds.get(i));
        }
        for(int i : userDatas.keySet()) {
            if(!uds.keySet().contains(i)) {
                userDatas.remove(i);
            }
        }
    }

    public void updateClientData(UserData client) {
        player.setText(client.getMessage());
    }

    public ClientPacket createPacket(){ // important
        String temp = messageToSend;
        messageToSend = "";
        return new ClientPacket(PacketConstants.UPDATE, player.getX(), player.getY(), temp);
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
