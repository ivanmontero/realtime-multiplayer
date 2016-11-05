import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final int INTERVAL = 16;

    private static int currentClientID = 0;
    private int port;
    private String ipAddress;
    private Main main;
    private ServerSocket serverSocket;
    //client threads
    private Map<Integer, ClientListener> tClientListeners; //SHASHMAP
    //threads
    private Timer tOutTimer;
    private Thread tClientConnect;

    public Server(Main main) {
        this.main = main;
        this.tClientListeners = new ConcurrentHashMap<Integer, ClientListener>(32, 0.75f, 100);
        this.tOutTimer = new Timer("Server Out");
        this.tClientConnect = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[INFO] Client connect thread started");
                while (!Thread.currentThread().isInterrupted()) {
                    try{
                        Socket socket = serverSocket.accept();
                        socket.setPerformancePreferences(0, 2, 1);
                        ClientListener cl
                                = new ClientListener(Server.this, socket, ++currentClientID);
                        System.out.println("[INFO] Client " + socket.getLocalAddress().getHostAddress()
                                + " has connected with ID:" + currentClientID);
                        cl.start();
                        tClientListeners.put(currentClientID, cl);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println("[INFO] Client connect thread terminated");
            }
        }, "Client Connect");
    }

    public boolean connect(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(serverSocket.getReceiveBufferSize());
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to bind to port " + port);
            return false;
        }
        serverSocket.setPerformancePreferences(0, 2, 1);
        System.out.println("[INFO] Successfully bound to port " + port);
        System.out.println("[INFO] Server public IP: " + getPublicIPAddress());
        return true;
    }

    public void start(){
        tClientConnect.start();
        tOutTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GameData.update(INTERVAL);
                sendPacket();
            }
        }, 0, INTERVAL);
        System.out.println("[INFO] Server out thread started");
    }

    public void sendPacket(){
        if(!tClientListeners.isEmpty()) {
            ServerPacket sp = new ServerPacket(GameData.getUserDatas());
            for (int i : tClientListeners.keySet()) {
                ClientListener cl = tClientListeners.get(i);
                cl.sendPacket(sp);
                if(!cl.isConnected()) {
                    disconnectClient(i);
                }
            }
        }
    }

    public void stop(){
        tClientConnect.interrupt();
        tOutTimer.cancel();
        disconnectAll();
        try {
            serverSocket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    //specialized packets, send hashmap instead
    public void sendPacket(){
        for(int i = tClientListeners.size() - 1; i >= 0; i--){
            ClientListener cl = tClientListeners.get(i);
            cl.sendPacket(GameData.createPacket(cl.ID));
        }
    }
    */

    //NOT synchronized so multiple threads can use at the same time
    public void processPacket(int id, ClientPacket cp){
        switch(cp.TYPE){
            case PacketConstants.UPDATE:
                GameData.updateUserData(id, cp.toUserData());
                break;
            case PacketConstants.CLOSING:
                disconnectClient(id);
                break;
        }
    }

    public boolean isConnected() {
        return serverSocket != null && serverSocket.isBound();
    }

    public void disconnectClient(int id) {
        if(tClientListeners.containsKey(id)) {
            if(tClientListeners.get(id).isConnected())
                tClientListeners.get(id).close();
            tClientListeners.remove(id);
            GameData.removeData(id);
            System.out.println("[INFO] Client ID:" + id + " disconnected");
        }
    }

    public void disconnectAll() {
        System.out.println("[INFO] Disconnecting all clients");
        for(int i : tClientListeners.keySet()) {
            disconnectClient(i);
        }
    }

    public String getPublicIPAddress() {
        String ip = "";
        try {
            URL whatsMyIP = new URL("http://checkip.amazonaws.com");
            ip = (new BufferedReader(new InputStreamReader(whatsMyIP.openStream()))).readLine();
        } catch (Exception e) {
            return "UNKNOWN";
        }
        ipAddress = ip;
        return ip;
    }

}
