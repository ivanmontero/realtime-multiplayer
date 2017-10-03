import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client{
    public static final boolean DEBUG_MODE = true;          // Testing purposes
    public static final String SERVER_IP = "67.170.25.33";
    //client
    private Main main;
    private Game game;
    private ServerListener tServerListener;
    private volatile Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private int clientID;
    //server info
    private String serverIP = "localhost"; //"67.170.25.33";
    private int port = 1337;
    private boolean localhost = false;

    private volatile boolean isConnected;

    public Client(Main main) {
        this.main = main;
    }

    public void start(){
        if(connect())
            tServerListener.start();
    }

    public boolean connect(){
        try{
            if(DEBUG_MODE || Main.isLocalHost())
                socket = new Socket(serverIP, port);
            else
                socket = new Socket(SERVER_IP, port);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut.writeObject(new ClientPacket(PacketConstants.CONNECTING, getSystemName()));
            clientID = (Integer) socketIn.readObject();
        } catch (Exception e) {
            System.err.println("Failed to connect to server");
            return false;
        }
         System.out.println("Connection accepted.");
        isConnected = true;
        tServerListener = new ServerListener(this, socketIn);
        return true;
    }

    public void sendPacket(){
        sendPacket(game.createPacket());
    }

    public void sendPacket(ClientPacket cp){
        if(isConnected()){
            try {
                socketOut.writeUnshared(cp);
                socketOut.reset();
            } catch (IOException e) {
                System.err.println("Connection lost to server");
                isConnected = false;
                stop();
            }
        }
    }

    public synchronized void processPacket(ServerPacket sp){
        Map<Integer, UserData> uds = sp.getUserDatas();
        game.updateClientData(uds.get(clientID));
        uds.remove(clientID);
        game.updateUserDatas(uds);
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void stop(){
        if(isConnected)
            sendPacket(new ClientPacket(PacketConstants.CLOSING));
        if(tServerListener != null)
            tServerListener.interrupt();
        if(socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected(){
        return isConnected;
    }

    private String getSystemName() {
        /*
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else if (env.containsKey("HOSTNAME"))
            return env.get("HOSTNAME");
        else
            return "Unknown Computer";
            */
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "UNKNOWN";
        }

    }
}