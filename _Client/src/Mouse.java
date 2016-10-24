import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Mouse {
    private static HashMap<Integer, Boolean> mousePressedMap = new HashMap<Integer, Boolean>();

    public static boolean isMouseButtonPressed(int mouseButton){
        if(!mousePressedMap.containsKey(mouseButton)){
            mousePressedMap.put(mouseButton, false);
            return false;
        }
        return mousePressedMap.get(mouseButton);
    }

    public static void mousePressed(MouseEvent e){
        mousePressedMap.put(e.getButton(), true);
    }

    public static void mouseReleased(MouseEvent e){
        mousePressedMap.put(e.getButton(), false);
    }

    public static void resetMouse() {
        for (Integer i : mousePressedMap.keySet())
            mousePressedMap.put(i, false);
    }

}
