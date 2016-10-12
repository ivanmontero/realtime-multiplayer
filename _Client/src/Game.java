import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Game {
    private Main main;
    private Client client;
    private Player player;
    private ConcurrentHashMap<Integer, UserData> userDatas;

    public Game(Main main){
        this.main = main;
        player = new Player();
        userDatas = new ConcurrentHashMap<Integer, UserData>(16, 0.75f, 2);
    }

    public void update(){
        player.update();
    }

    public void render(Graphics2D g2d){
        player.render(g2d);
        g2d.setColor(Color.BLACK);
        for(UserData ud : userDatas.values()){
            g2d.fillRect(ud.getX() - 5, ud.getY() - 5, 10, 10);
        }
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
