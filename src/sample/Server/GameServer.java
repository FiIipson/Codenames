package sample.Server;

import sample.Controllers.Board;
import sample.Controllers.GlobalVar;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    public static boolean end;
    private static Board BOARD;
    private int number_of_players = 3;
    private ServerSocket serverSocket;
    private AtomicInteger numOfPlayers;
    private static int PORT = 9999;
    private static ArrayList<ServerSideConnection> players;
    public GameServer(){
        end = false;
        System.out.println("[server started]");
        numOfPlayers= new AtomicInteger(0);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (BindException bindException){
            throw new RuntimeException(bindException.getCause());
        } catch(IOException ex){
            ex.printStackTrace();
        }
        players = new ArrayList<>();
    }
    public void acceptConnections(){
        try{
            System.out.println("[waiting for connections]");
            GlobalVar.serverReady = true;
            while(numOfPlayers.get() < number_of_players && !end){
                Socket client = serverSocket.accept();
                ServerSideConnection ssc = new ServerSideConnection(client,players, numOfPlayers.incrementAndGet());
                players.add(ssc);
                System.out.println("[new thread created]");
                Thread t = new Thread(ssc);
                t.start();
                System.out.println("[ready for another connection]");
                if(BOARD != null) number_of_players = BOARD.getNumber_of_players();
            }
            System.out.println("[all players joined]");
            sendToAll(BOARD);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    private class ServerSideConnection implements Runnable{

        private Socket socket;
        private ArrayList<ServerSideConnection> connections;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private int playerID;
        private String playerName;

        public ServerSideConnection(Socket s, ArrayList<ServerSideConnection> playrs, int playerID){
            socket = s;
            connections = playrs;
            this.playerID = playerID;
            try {
                out = new ObjectOutputStream(s.getOutputStream());
                out.flush();
                in = new ObjectInputStream(s.getInputStream());
                out.writeInt(playerID);
                out.flush();
                if(playerID == 1){
                    BOARD = (Board)in.readObject();
                    System.out.println("[board set by player 1 : " + playerName + "]");
                }
                //this.playerName = (String) in.readObject();
                //BOARD.getPlayers()[playerID - 1].setName(playerName);
            }catch(IOException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
            System.out.println("[player " + this.playerID + " : " + playerName + " joined]" );
        }
        @Override
        public void run() {
            try {
                while (numOfPlayers.get() < number_of_players && !end ) {
                    Thread.onSpinWait();
                }
                outer :while(true){
                    for(ServerSideConnection s : connections){
                        if(s.socket.isClosed()){
                            sendToAll(null);
                            Thread.currentThread().interrupt();
                            System.out.println("enddddddd");
                            break outer;
                        }
                    }
                    BOARD = (Board) in.readObject();
                    System.out.println("[received board from " + playerName + "]");
                    sendToAll(BOARD);
                    GlobalVar.moveType moveType = (GlobalVar.moveType) in.readObject();
                    sendToAll(moveType);
                }

            } catch (IOException | ClassNotFoundException ignored) {
                System.out.println("[connection terminated : " + playerName + "]");
                try {
                    sendToAll(null);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Thread.currentThread().interrupt();
                System.out.println("enddddddd");
                if(end)System.exit(0);
            }

        }

    }

    public void sendToAll(Object o) throws IOException {
        for(ServerSideConnection ssc : players){
            try{
                ssc.out.writeObject(o);
                ssc.out.flush();
            }catch (SocketException socketException){
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args){
        try {
            GameServer gs = new GameServer();
            gs.acceptConnections();
        } catch (RuntimeException runtimeException){
            throw new RuntimeException(runtimeException.getCause());
        }
    }
}
