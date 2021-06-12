package sample.Server;

import sample.Controllers.BOARD;
import sample.Controllers.GlobalVar;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class PlayerHandler implements Runnable{
    ArrayList<PlayerHandler> players;
    Socket player;
    ObjectOutputStream out;
    ObjectInputStream in;
    GlobalVar.PlayerRole role;
    public String name;

    public PlayerHandler(ArrayList<PlayerHandler> players, Socket player) throws IOException {
        this.players = players;
        this.player = player;
        out = new ObjectOutputStream(player.getOutputStream());
        out.flush();
        in = new ObjectInputStream(player.getInputStream());
    }

    @Override
    public void run() {
        try {
            name = (String) in.readObject();
            System.out.println("Player " + name + " joined");
            while (GameServer.numOfPlayers.get() < GameServer.number_of_players && !GameServer.end) {
                Thread.onSpinWait();
            }
            System.out.println("Player " + name + " started working");
            outer: while(true) {
                for (PlayerHandler ph : players) {
                    if (ph.player.isClosed()) {
                        Thread.currentThread().interrupt();
                        System.out.println("Enddd");
                        break outer;
                    }
                }
                System.out.println("1");
                GlobalVar.current_board = (BOARD) in.readObject();
                System.out.println("2");
                System.out.println("Received board from player: " + name);
                GameServer.sendToAll(GlobalVar.current_board);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
