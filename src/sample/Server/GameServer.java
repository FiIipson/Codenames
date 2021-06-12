package sample.Server;

import sample.Controllers.GlobalVar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class GameServer {
    public static boolean end;
    //private static Board BOARD;
    private static int number_of_players = 3;
    private static ServerSocket serverSocket;
    private static AtomicInteger numOfPlayers;
    private static int PORT = 9999;
    private static ArrayList<PlayerHandler> players;

    public static void main() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("[SERVER] Started!");
        numOfPlayers = new AtomicInteger(0);
        players = new ArrayList<>();
        GlobalVar.serverReady = true;

        while (numOfPlayers.get() < number_of_players) {
            System.out.println("[SERVER] Waiting for player!");
            Socket player = serverSocket.accept();
            System.out.println("[SERVER] Connected a player!");
            PlayerHandler playerHandler = new PlayerHandler(players, player);
            players.add(playerHandler);
            System.out.println("[SERVER] " + "Player " + numOfPlayers.incrementAndGet() + " joined.");
            Thread t = new Thread(playerHandler);
            t.start();
            sleep(100);
            System.out.println("[SERVER] Ready for another player!");
        }

    }
}
