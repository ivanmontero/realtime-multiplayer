import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Keyboard {
    private static HashMap<Integer, Boolean> keyPressedMap = new HashMap<Integer, Boolean>();
    private static ArrayList<Character> keyTypedQueue = new ArrayList<Character>();
    private static ArrayList<KeyboardEvent> keyEventQueue = new ArrayList<KeyboardEvent>();

    public static boolean isKeyPressed(int key){
        if(!keyPressedMap.containsKey(key)) {
            keyPressedMap.put(key, false);
            return false;
        }
        return keyPressedMap.get(key);
    }

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


    public static void resetKeys() {
        for (Integer i : keyPressedMap.keySet())
            keyPressedMap.put(i, false);
    }

    public static void resetTyped() {
        keyTypedQueue.clear();
    }






    private class KeyboardEvent {
        private KeyEvent event;
        private boolean state;

        public KeyboardEvent(KeyEvent e, boolean state) {
            this.event = e;
            this.state = state;
        }

        public int getKeyCode() {
            return event.getKeyCode();
        }

        public char getKeyChar() {
            return event.getKeyChar();
        }

        public boolean getKeyState() {
            return state;
        }
    }
}
