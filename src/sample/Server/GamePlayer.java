package sample.Server;

import sample.Controllers.GlobalVar;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePlayer {



    public static void main() throws IOException {
        Socket socket = new Socket(GlobalVar.IP, 9999);

        GameServerConnection gameServerConnection = new GameServerConnection(socket);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(GlobalVar.your_name);
        out.flush();

        new Thread(gameServerConnection).start();
    }
}
