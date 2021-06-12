package sample.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    ArrayList<ClientHandler> players;
    Socket player;
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientHandler(ArrayList<ClientHandler> players, Socket player) throws IOException {
        this.players = players;
        this.player = player;
//        out = new ObjectOutputStream(player.getOutputStream());
//        in = new ObjectInputStream(player.getInputStream());
    }


    @Override
    public void run() {
//        try {
//            System.out.println("Joined");
//            String name = (String) in.readObject();
//            out.writeObject(name);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
