package sample.Server;

import sample.Controllers.GlobalVar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    ArrayList<ClientHandler> players;
    Socket player;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;

    public ClientHandler(ArrayList<ClientHandler> players, Socket player) throws IOException {
        this.players = players;
        this.player = player;
        out = new ObjectOutputStream(player.getOutputStream());
        in = new ObjectInputStream(player.getInputStream());
        out.writeObject(GlobalVar.Player.getName());
    }


    @Override
    public void run() {
    }
}
