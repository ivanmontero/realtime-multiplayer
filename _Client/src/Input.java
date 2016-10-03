import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Input {
    private static HashMap<Integer, Boolean> keyPressedMap = new HashMap<Integer, Boolean>();
    private static HashMap<Integer, Boolean> mousePressedMap = new HashMap<Integer, Boolean>();
    private static ArrayList<Character> keyTypedQueue = new ArrayList<Character>();

    public static boolean isKeyPressed(int key){
        if(!keyPressedMap.containsKey(key)) {
            keyPressedMap.put(key, false);
            return false;
        }
        return keyPressedMap.get(key);
    }

    public static boolean isMouseButtonPressed(int mouseButton){
        if(!mousePressedMap.containsKey(mouseButton)){
            mousePressedMap.put(mouseButton, false);
            return false;
        }
        return mousePressedMap.get(mouseButton);
    }

    //public static boolean wasTyped

    public static char nextTyped() {
        if(keyTypedQueue.isEmpty())
            throw new NoSuchElementException("No more keys");
        return keyTypedQueue.remove(0);
    }

    public static boolean hasNextTyped() {
        return !keyTypedQueue.isEmpty();
    }

    public static void keyPressed(KeyEvent e){
        keyPressedMap.put(e.getKeyCode(), true);
    }

    public static void keyTyped(KeyEvent e){
        keyTypedQueue.add(e.getKeyChar());
    }

    public static void keyReleased(KeyEvent e){
        keyPressedMap.put(e.getKeyCode(), false);
    }

    public static void mousePressed(MouseEvent e){
        mousePressedMap.put(e.getButton(), true);
    }

    public static void mouseReleased(MouseEvent e){
        mousePressedMap.put(e.getButton(), false);
    }

    public static void resetKeys() {
        for (Integer i : keyPressedMap.keySet())
            keyPressedMap.put(i, false);
    }

    public static void resetMouse() {
        for (Integer i : mousePressedMap.keySet())
            mousePressedMap.put(i, false);
    }

    public static void resetTyped() {
        keyTypedQueue.clear();
    }

    public static void reset() {
        resetKeys();
        resetMouse();
        resetTyped();
    }

}
