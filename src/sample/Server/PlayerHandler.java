package sample.Server;

import sample.Controllers.GlobalVar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerHandler implements Runnable{
    ArrayList<PlayerHandler> players;
    Socket player;
    ObjectOutputStream out;
    ObjectInputStream in;
    GlobalVar.PlayerRole role;
    String Name;

    public PlayerHandler(ArrayList<PlayerHandler> players, Socket player) throws IOException {
        this.players = players;
        this.player = player;
//        out = new ObjectOutputStream(player.getOutputStream());
//        out.flush();
        in = new ObjectInputStream(player.getInputStream());
    }

    @Override
    public void run() {
        try {
            String name = (String) in.readObject();
            System.out.println("Player " + name + " joined");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
