package sample.Server;

import sample.Controllers.Board;
import sample.Controllers.GlobalVar;
import sample.Controllers.NewPlayerHostController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSideConnection {

    public Socket socket;
    public static ObjectOutputStream out;
    public ObjectInputStream in;
    public int playerID;
    public String playerName;

    public static void sendBoard(){
        try{
            out.writeObject(GlobalVar.receivedBoard);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Board receiveBoard() throws RuntimeException {
        Board b = null;
        try{
            b = (Board) in.readObject();
            System.out.println("[received board]");
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e.getMessage());
        }
        return b;
    }
    public ClientSideConnection(){
        try {
            socket = new Socket(GlobalVar.IP, 9999);

            System.out.println("[connected to server]");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            playerID = in.readInt();
            if (playerID == 1) {
                out.writeObject(NewPlayerHostController.setUpBoard);
                out.flush();
            }
            GlobalVar.loadDictionary();
            if (playerID > 2) {
                if (playerID == 3)
                    GlobalVar.yourRole = GlobalVar.PlayerRole.BLUE_OPERATIVE;
                else
                    GlobalVar.yourRole = GlobalVar.PlayerRole.RED_OPERATIVE;
            } else {
                if (playerID == 1)
                    GlobalVar.yourRole = GlobalVar.PlayerRole.BLUE_LEADER;
                else
                    GlobalVar.yourRole = GlobalVar.PlayerRole.RED_LEADER;
            }
            playerName = GlobalVar.your_name;
            out.writeObject(playerName);
            out.flush();
        } catch (ConnectException e){
            throw new RuntimeException(e.getCause());
        }
        catch(UnknownHostException u){
            System.out.println("[error connecting to " + GlobalVar.IP + " : Unknown host]" );
            throw new RuntimeException(u.getCause());
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
