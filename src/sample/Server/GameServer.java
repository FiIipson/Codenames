package sample.Server;

import sample.Controllers.GlobalVar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    public static boolean end;
    //private static Board BOARD;
    private static int number_of_players = 3;
    private static ServerSocket serverSocket;
    private static AtomicInteger numOfPlayers;
    private static int PORT = 9999;
    private static ArrayList<ClientHandler> players;

    public static void main() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("[SERVER] Started!");
        numOfPlayers = new AtomicInteger(0);
        players = new ArrayList<>();
        GlobalVar.serverReady = true;

        while (numOfPlayers.get() < number_of_players) {
            System.out.println("[SERVER] Waiting for player!");
            Socket player = serverSocket.accept();
            System.out.println("[SERVER] Connected a player!");
            ClientHandler clientHandler = new ClientHandler(players, player);
            players.add(clientHandler);
            System.out.println("[SERVER] " + numOfPlayers.incrementAndGet() + " player joined");
            //if (numOfPlayers.get() > 1) {
            String name = (String)GameServerConnection.in.readObject();
            System.out.println(name);
            //}
        }
    }
}
