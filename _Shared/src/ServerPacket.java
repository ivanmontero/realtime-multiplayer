import java.io.Serializable;
import java.util.HashMap;

public class ServerPacket implements Serializable {
    private static final long serialVersionUID = -7897415282830875672L;
    private HashMap<Integer, UserData> userDatas;

    public ServerPacket(HashMap<Integer, UserData> userDatas){
        this.userDatas = userDatas;
    }

    public HashMap<Integer, UserData> getUserDatas(){
        return userDatas;
    }

}
