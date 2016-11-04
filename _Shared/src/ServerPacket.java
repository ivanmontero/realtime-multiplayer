import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServerPacket implements Serializable {
    private static final long serialVersionUID = -7897415282830875672L;
    private Map<Integer, UserData> userDatas;

    public ServerPacket(Map<Integer, UserData> userDatas){
        this.userDatas = userDatas;
    }

    public Map<Integer, UserData> getUserDatas(){
        return userDatas;
    }

}
