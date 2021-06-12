package sample.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameServerConnection implements Runnable {
    private Socket server;
    ObjectOutputStream out;
    ObjectInputStream in;

    public GameServerConnection(Socket socket) throws IOException {
        server = socket;
//        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("Server says hi");
    }
}
