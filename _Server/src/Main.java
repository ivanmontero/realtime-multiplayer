import java.util.Scanner;

// Ivan Montero
// 11/4/2016
public class Main {
    public static final int DEFAULT_PORT = 1337;
    private Server server;
    private Scanner console;

    public Main(){
        server = new Server(this);
        console = new Scanner(System.in);
        start();
        loop();
        exit();
    }

    public void start(){
        while (!server.isConnected()){
            System.out.print("Server port (default=" + DEFAULT_PORT+ "): ");
            String input = console.nextLine();
            if(input.equals(""))
                server.connect(DEFAULT_PORT);
            else{
                int port;
                try{
                    port = Integer.parseInt(input);
                } catch (NumberFormatException e){
                    System.out.println("[ERROR] Please input a valid port.");
                    continue;
                }
                server.connect(port);
            }
        }
        server.start();
    }

    public void loop(){
        while(true){
            String cmd = console.nextLine();
            String[] cmds = cmd.split(" ");
            switch(cmds[0]){
                case "stop":
                    server.stop();
                    exit();
                    break;
                case "list":
                    break;
            }
        }
    }

    public void exit(){
        System.exit(0);
    }

    public static void main(String[] args){
        new Main();
    }

}
