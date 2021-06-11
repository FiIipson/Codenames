package sample.Controllers;

import sample.Server.GameServer;

public class StartServer extends Thread {
    @Override
    public void run() {
        try {
            GameServer.main(new String[0]);
        }catch (RuntimeException runtimeException){
            throw new RuntimeException(runtimeException.getCause());
        }
    }
}