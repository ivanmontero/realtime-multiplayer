import java.io.Serializable;

public class ClientPacket implements Serializable {
    private static final long serialVersionUID = 4789217797082144125L;

    public final int TYPE;
    //data for connecting
    private String name;
    //data for update
    private int x, y;

    public ClientPacket(int packetType){
        this.TYPE = packetType;
    }

    public ClientPacket(int packetType, UserData ud){
        this.TYPE = packetType;
        this.x = ud.getX();
        this.y = ud.getY();
    }

    public ClientPacket(int packetType, int x, int y){
        this.TYPE = packetType;
        this.x = x;
        this.y = y;
    }

    public ClientPacket(int packetType, String name) { //Use to send client data
        if (packetType != PacketConstants.CONNECTING)
            throw new IllegalArgumentException("Incorrect constructor usage");
        this.TYPE = packetType;
        this.name = name;
    }

    public UserData toUserData(){
        if(TYPE != PacketConstants.UPDATE)
            throw new UnsupportedOperationException("Client packet of type " + TYPE + " cannot be made into user data");
        return new UserData(x, y);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
