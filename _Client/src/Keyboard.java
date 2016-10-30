import java.awt.event.KeyEvent;
import java.util.*;

//TODO: Shift to keybinds

public class Keyboard {
    private static HashMap<Integer, Boolean> keyPressedMap = new HashMap<Integer, Boolean>();
    private static Queue<Character> keyTypedQueue = new LinkedList<Character>();
    private static Queue<KeyboardEvent> keyEventQueue = new LinkedList<KeyboardEvent>();
    private static KeyboardEvent currentEvent;

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
        return keyTypedQueue.remove();
    }

    public static boolean next() {
        if(keyEventQueue.isEmpty()) {
            currentEvent = null;
            return false;
        }
        currentEvent = keyEventQueue.remove();
        return true;
    }

    public static boolean getEventKeyState() {
        return currentEvent.getKeyState();
    }

    public static boolean hasNextTyped() {
        return !keyTypedQueue.isEmpty();
    }

    public static int getEventKeyCode() {
        return currentEvent.getKeyCode();
    }

    public static char getEventKeyChar() {
        return currentEvent.getKeyChar();
    }

    public static void keyPressed(KeyEvent e){
        keyPressedMap.put(e.getKeyCode(), true);
        keyEventQueue.add(new KeyboardEvent(e, true));

    }

    public static void keyTyped(KeyEvent e){
        keyTypedQueue.add(e.getKeyChar());
    }

    public static void keyReleased(KeyEvent e){
        keyPressedMap.put(e.getKeyCode(), false);
        keyEventQueue.add(new KeyboardEvent(e, false));
    }

    public static void resetKeys() {
        for (Integer i : keyPressedMap.keySet())
            keyPressedMap.put(i, false);
        resetTyped();
        resetKeyEvents();
    }

    public static void resetTyped() {
        keyTypedQueue.clear();
    }

    public static void resetKeyEvents() {
        keyEventQueue.clear();
    }

    public static KeyEvent getKeyEvent() {
        return currentEvent.getKeyEvent();
    }

    private static class KeyboardEvent {
        private KeyEvent event;
        //true if pressed, false if released
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

        public KeyEvent getKeyEvent() {
            return event;
        }
    }
}
