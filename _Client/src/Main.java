import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;

/**
 * TODO list
 * server controls
 * proper exiting
 * chat program
 */
// Ivan Montero
// 11/4/2016
public class Main extends JPanel implements ActionListener{
    private static Main instance;
    private static boolean isLocalhost;

    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;
    private JFrame window;
    private Game game;
    private Client client;
    private Timer timer;

    private Main(){
        super(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window = new JFrame("Client");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        window.add(this);
        window.setSize(WINDOW_WIDTH + 6, WINDOW_HEIGHT + 28);
        window.setResizable(false);
        window.addKeyListener(new KeyboardInput());
        window.addMouseListener(new MouseInput());
        window.setFocusTraversalKeysEnabled(false);
        window.setFocusable(true);
        window.setVisible(true);

        game = new Game(this);
        client = new Client(this);
        game.setClient(client);
        client.setGame(game);
        //listeners

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update(16);
        if(!client.isConnected() && Keyboard.isKeyPressed(KeyEvent.VK_ESCAPE))
            client.start();
        client.sendPacket();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (game != null)
            game.render(g2d);
    }

    public void exit(){
        client.stop();
    }

    private class KeyboardInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            Keyboard.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Keyboard.keyReleased(e);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            Keyboard.keyTyped(e);
        }
    }

    private class MouseInput extends MouseAdapter {
        //@Override
        //public void mouseClicked(MouseEvent e) {
        //    super.mouseClicked(e);
        //}

        @Override
        public void mousePressed(MouseEvent e) {
            Mouse.mousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Mouse.mouseReleased(e);
        }
    }

    public static void main(String[] args){
        if(args.length > 0 && args[0].equals("localhost")) {
            isLocalhost = true;
        }
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 15);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("could not get system look and feel");
        }
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
        JOptionPane.showMessageDialog(null,
                "W,A,S,D  - Move character\n" +
                "ENTER    - Open chat box/Send Message\n" +
                "ESC      - Connect to server\n" +
                "TAB      - Show controls", "Controls", JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                instance = new Main();
            }
        });
    }

    public int getStringWidth(String str) {
        return getGraphics().getFontMetrics(getFont()).stringWidth(str);
    }

    public FontMetrics getFontMetrics() {
        return getGraphics().getFontMetrics();
    }

    public static boolean isLocalHost() {
        return isLocalhost;
    }

    public static Main getInstance() {
        return instance;
    }

    /* //Test
    private String getComputerName()
    {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else if (env.containsKey("HOSTNAME"))
            return env.get("HOSTNAME");
        else
            return "Unknown Computer";
    }
     */
}
