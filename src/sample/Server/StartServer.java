package sample.Server;

import java.io.IOException;

public class StartServer extends Thread{
    @Override
    public void run() {
        try {
            GameServer.main(new String[0]);
        } catch (RuntimeException runtimeException) {
            throw new RuntimeException(runtimeException.getCause());
        }
    }
}
