import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameData { // Managing of all user data
    private static ConcurrentHashMap<Integer, UserData> userDatas = new ConcurrentHashMap<Integer, UserData>();

    public static final int MESSAGE_TIME = 16 * 60 * 10; // for messages

    public static void update(int delta) {

    }

    public static void updateUserData(int id, UserData ud){
        if(!userDatas.containsKey(id)) {
            userDatas.put(id, ud);
        } else
            userDatas.get(id).updateData(ud);
    }

    public static HashMap<Integer, UserData> getUserDatas(){
        return new HashMap<Integer, UserData>(userDatas);
    }

    public static void removeData(int id){
        userDatas.remove(id);
    }

    public static UserData get(int id){
        return userDatas.get(id);
    }

}
