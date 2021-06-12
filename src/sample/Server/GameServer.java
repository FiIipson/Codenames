package sample.Server;

import sample.Controllers.BOARD;
import sample.Controllers.GlobalVar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class GameServer {
    public static boolean end;
    //private static Board BOARD;
    public static int number_of_players = 2;
    private static ServerSocket serverSocket;
    public static AtomicInteger numOfPlayers;
    private static int PORT = 9999;
    private static ArrayList<PlayerHandler> players;

    public static void main() throws IOException, InterruptedException, ClassNotFoundException {
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

        //Random roles
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < number_of_players; ++i) numbers.add(i);
        Collections.shuffle(numbers);
        int count = 0;
        for (GlobalVar.PlayerRole r : GlobalVar.PlayerRole.values()) {
            players.get(numbers.get(count++)).role = r;
            System.out.println("Rola " + players.get(numbers.get(count - 1)).name + " to: " + players.get(numbers.get(count - 1)).role);
        }
        GlobalVar.loadWords();
        GlobalVar.setWords(GlobalVar.difficulty);
        GlobalVar.current_board = new BOARD(GlobalVar.word, new GlobalVar.Hint("", false, -1));
        sendToAll(GlobalVar.current_board);
        sendToAll(players);
    }

    public static void sendToAll(Object o) {
        for (PlayerHandler ph : players) {
            try {
                ph.out.writeObject(o);
                ph.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
