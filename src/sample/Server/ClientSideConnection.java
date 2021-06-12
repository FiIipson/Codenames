package sample.Server;


import sample.Controllers.Board;
import sample.Controllers.GlobalVar;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import static sample.Controllers.CreateGameSettingController.setUpBoard;

public class ClientSideConnection{
    public Socket socket;
    public ObjectOutputStream out;
    public ObjectInputStream in;
    public int playerID;
    public String playerName;
    public boolean isAHost;
    public void sendBoard(){
        try{
            out.writeObject(GlobalVar.receivedBoard);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //endGame();
            //connectionLost.setVisible(true);
        }
    }
    public void sendMoveType(GlobalVar.moveType moveType){
        try{
            out.writeObject(moveType);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //endGame();
            //connectionLost.setVisible(true);
        }
    }
    public GlobalVar.moveType receiveMoveType() throws RuntimeException {
        GlobalVar.moveType moveType = null;
        try{
            moveType = (GlobalVar.moveType) in.readObject();
        } catch (IOException | ClassNotFoundException  | ClassCastException e) {
            //e.printStackTrace();
            throw new RuntimeException(e.getMessage());
            //connectionLost.setVisible(true);
        }
        return moveType;
    }
    public Board receiveBoard() throws RuntimeException {
        Board b = null;
        try{
            b = (Board) in.readObject();
            System.out.println("[received board (Cl side connection)]");
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            //e.printStackTrace();
            throw new RuntimeException(e.getMessage());
            //endGame();
            //connectionLost.setVisible(true);
        }
        return b;
    }
    public ClientSideConnection(){
        try {
            socket = new Socket(GlobalVar.serverID, 9999);
            System.out.println("[connected to server]");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            playerID = in.readInt();
            if (playerID == 1) {
                out.writeObject(setUpBoard);
                out.flush();
            }
            playerName = GlobalVar.playerName;
            out.writeObject(playerName);
            out.flush();
        }catch (ConnectException e){
            throw new RuntimeException(e.getCause());
        }
        catch(UnknownHostException u){
            System.out.println("[error connecting to " + GlobalVar.serverID + " : Unknown host]" );
            throw new RuntimeException(u.getCause());
            //forceExit = true;
        }catch (IOException ex){
            ex.printStackTrace();
            //endGame();
            //connectionLost.setVisible(true);
        }
    }
}
