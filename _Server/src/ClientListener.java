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

    public final int ID;

    public ClientListener(Server server, Socket socket, int id){
        this.server = server;
        this.socket = socket;
        this.ID = id;
        try{
            this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            this.socketIn = new ObjectInputStream(socket.getInputStream());
            this.socketOut.writeObject(ID);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        if(isConnected()) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ClientPacket cp = (ClientPacket) socketIn.readObject();
                    server.processPacket(ID, cp);
                    //gameData.processPacket(cp, ID);   //for instantaneous modification
                    //server.processPacket(cp, ID);     //for synchronized modification
                } catch (Exception e){
                    close(); //TODO better handling
                }
            }
        }
    }

    public boolean sendPacket(ServerPacket sp){
        try{
            socketOut.writeUnshared(sp);
            socketOut.reset();
        } catch (IOException e) {
            e.printStackTrace(); //TODO handle
            return false;
        }
        return true;
    }

    public void close(){
        interrupt();
        try{
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return socket != null && socket.isConnected();
    }

}
