package sample.Server;

import java.io.IOException;

public class StartServer extends Thread{
    @Override
    public void run() {
        try {
            GameServer.main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
