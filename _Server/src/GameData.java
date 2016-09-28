import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameData {
    private static ConcurrentHashMap<Integer, UserData> userDatas = new ConcurrentHashMap<Integer, UserData>();
    //private static volatile boolean modified;

    /*
    public static void processPacket(ClientPacket cp, int id){
        UserData ud = cp.toUserData();
        if(!userDatas.containsKey(id))
            userDatas.put(id, ud);
        else
            userDatas.get(id).updateData(ud);
    }
    */

    public static void update(int delta) {

    }

    public static void updateUserData(int id, UserData ud){
        if(!userDatas.containsKey(id)) {
            userDatas.put(id, ud);
            //modified = true;
        } else
            /*modified = */userDatas.get(id).updateData(ud);
    }

    /*
    public static ServerPacket createPacket(){
        return new ServerPacket(new HashMap<Integer, UserData>(userDatas));
    }
    */

    public static HashMap<Integer, UserData> getUserDatas(){
        return new HashMap<Integer, UserData>(userDatas);
    }

    /*
    public static ServerPacket createPacket(int exempt){
        HashSet<UserData> uds = new HashSet<UserData>();
        for(Integer i : userDatas.keySet()){
            if(!i.equals(exempt))
                uds.add(userDatas.get(i));
        }
        return new ServerPacket(uds);
    }
    */

    public static void removeData(int id){
        userDatas.remove(id);
    }

    public static UserData get(int id){
        return userDatas.get(id);
    }

    /*
    public static boolean wasModified(){
        return modified;
    }

    public static synchronized void setModified(boolean modified){
        GameData.modified = modified;
    }
    */

}
