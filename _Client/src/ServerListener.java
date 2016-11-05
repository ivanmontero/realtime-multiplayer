import java.io.ObjectInputStream;

public class ServerListener extends Thread {

    private Client client;
    private ObjectInputStream socketIn;
    //private long last_time;

    public ServerListener(Client client, ObjectInputStream socketIn){
        super("Server Listener");
        this.client = client;
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try{
                //ServerPacket sp = (ServerPacket) socketIn.readObject();
                client.processPacket((ServerPacket) socketIn.readObject());
            } catch (Exception e){
                if(client.isConnected()) {
                    System.err.println("Connection lost to server");
                    client.setConnected(false);
                    client.stop();
                }
            }
            /*
            long time = System.nanoTime();
            int delta_time = (int) ((time - last_time) / 1000000);
            System.out.println(delta_time);
            last_time = time;
            */
        }
    }

}
