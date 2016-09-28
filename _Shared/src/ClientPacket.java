import java.io.Serializable;

public class ClientPacket implements Serializable {
    private static final long serialVersionUID = 4789217797082144125L;

    public final int TYPE;

    public final int X, Y;

    public ClientPacket(int packetType){
        this.TYPE = packetType;
        this.X = 0;
        this.Y = 0;
    }

    public ClientPacket(int packetType, UserData ud){
        this.TYPE = packetType;
        this.X = ud.getX();
        this.Y = ud.getY();
    }

    public ClientPacket(int packetType, int x, int y){
        this.TYPE = packetType;
        this.X = x;
        this.Y = y;
    }

    public ClientPacket(int packetType, String name) {
        if (packetType != PacketConstants.CONNECTING)
            throw new UnsupportedOperationException("Incorrect constructor usage");
        this.TYPE = packetType;
        this.X = 0;
        this.Y = 0;
        //TODO MESSAGE
    }

    public UserData toUserData(){
        if(TYPE != PacketConstants.UPDATE)
            throw new UnsupportedOperationException("Client packet of type " + TYPE + " cannot be made into user data");
        return new UserData(X, Y);
    }
}
