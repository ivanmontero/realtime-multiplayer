import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * TODO list
 * server controls
 * proper exiting
 * chat program
 */

public class Main extends JPanel implements ActionListener{
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;
    private JFrame window;
    private Game game;
    private Client client;
    private Timer timer;

    public Main(){
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
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(false);
        window.addKeyListener(new KeyboardInput());
        window.addMouseListener(new MouseInput());
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
        game.update();
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
        client.stop(true);
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
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
