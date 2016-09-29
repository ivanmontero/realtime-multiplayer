import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client{
    //client
    private Main main;
    private Game game;
    private ServerListener serverListener;
    private volatile Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private int clientID;
    //server info
    private String serverIP = "localhost"; //"67.170.25.33";
    private int port = 1337;

    public Client(Main main) {
        this.main = main;
    }

    public void start(){
        if(connect())
            serverListener.start();
    }

    public boolean connect(){
        try{
            socket = new Socket(serverIP, port);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut.writeObject(new ClientPacket(PacketConstants.CONNECTING, getSystemName()));
            clientID = (Integer) socketIn.readObject();
        } catch (Exception e) {
            System.err.println("Failed to connect to server");
            return false;
        }
         System.out.println("Connection accepted.");
        serverListener = new ServerListener(this, socketIn);
        return true;
    }

    public void sendPacket(){
        sendPacket(game.createPacket());
    }

    public void sendPacket(ClientPacket cp){
        if(isConnected() /*&& game.wasModified()*/){
            //game.setModified(false);
            try {
                socketOut.writeUnshared(cp);
                socketOut.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void processPacket(ServerPacket sp){
        HashMap<Integer, UserData> uds = sp.getUserDatas();
        uds.remove(clientID);
        game.updateUserDatas(uds);
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void stop(){
        sendPacket(new ClientPacket(PacketConstants.CLOSING));
        if(socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){
        return socket != null && socket.isConnected();
    }

    private String getSystemName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else if (env.containsKey("HOSTNAME"))
            return env.get("HOSTNAME");
        else
            return "Unknown Computer";
    }
}
