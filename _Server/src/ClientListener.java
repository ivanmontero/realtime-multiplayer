import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener extends Thread{
    private Server server;
    private volatile Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private String name;
    private volatile boolean isConnected;

    public final int ID;

    public ClientListener(Server server, Socket socket, int id){
        this.server = server;
        this.socket = socket;
        this.ID = id;
        try{
            this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            this.socketIn = new ObjectInputStream(socket.getInputStream());
            ClientPacket cp = (ClientPacket) socketIn.readObject();
            if(cp.TYPE != PacketConstants.CONNECTING)
                throw new UnsupportedOperationException("Invalid packet type received");
            name = cp.getName();
            //System.out.println(name);
            this.socketOut.writeObject(ID);
        } catch (Exception e){
            e.printStackTrace();
        }
        isConnected = true;
    }

    @Override
    public void run(){
        while (isConnected && !Thread.currentThread().isInterrupted()) {
            try {
                ClientPacket cp = (ClientPacket) socketIn.readObject();
                if(cp.hasMessage()) {
                    System.out.println("[ID:" + ID + ", name:" + name +"] " + cp.getMessage());
                }
                server.processPacket(ID, cp);
            } catch (Exception e){
                isConnected = false; //TODO better handling
            }
        }
    }

    public void sendPacket(ServerPacket sp){
        try{
            socketOut.writeUnshared(sp);
            socketOut.reset();
        } catch (IOException e) {
            //e.printStackTrace(); //TODO handle
            isConnected = false;
        }
    }

    public void close(){
        interrupt();
        isConnected = false;
        try{
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return isConnected;
    }

}
