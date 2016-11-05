import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameData { // Managing of all user data
    private static Map<Integer, UserData> userDatas = new ConcurrentHashMap<Integer, UserData>(32, 0.75f, 100);
    private static Map<Integer, Integer> messageDurations  = new ConcurrentHashMap<Integer, Integer>(32, 0.75f, 100);

    public static final int MESSAGE_TIME = 16 * 60 * 5; // for messages

    public static void update(int delta) {
        for(int key : messageDurations.keySet()) {
            messageDurations.put(key, messageDurations.get(key) + delta);
            if (messageDurations.get(key) > MESSAGE_TIME) {
                messageDurations.remove(key);
                userDatas.get(key).setMessage("");
            }
        }
    }

    public static void updateUserData(int id, UserData ud){
        if(!userDatas.containsKey(id)) {
            userDatas.put(id, ud);
        } else {
            userDatas.get(id).serverUpdateData(ud);
        }
        if(ud.hasMessage()) {
            messageDurations.put(id, 0);
        }
    }

    public static Map<Integer, UserData> getUserDatas(){
        return new HashMap<Integer, UserData>(userDatas);
    }

    public static void removeData(int id){
        userDatas.remove(id);
        messageDurations.remove(id);
    }

    public static UserData get(int id){
        return userDatas.get(id);
    }

}
