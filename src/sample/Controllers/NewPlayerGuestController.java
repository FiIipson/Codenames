package sample.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server.ClientSideConnection;

import java.io.IOException;
import java.io.OptionalDataException;
import java.util.Objects;

public class NewPlayerGuestController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    TextField name;
    @FXML
    ChoiceBox<GlobalVar.WordType> your_team;
    @FXML
    TextField IPAddress;

    @FXML
    protected void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/PlayOnline.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void toLobby(ActionEvent e) throws IOException {
        GlobalVar.playerName = name.getText();
        GlobalVar.serverID = IPAddress.getText();

        try{
            GlobalVar.csc = new ClientSideConnection();
        }catch (RuntimeException exception){
            return;
        }
        Object o = null;
        GlobalVar.receivedBoard = null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = null;
                while(GlobalVar.receivedBoard == null){
                    try{
                        o = GlobalVar.csc.in.readObject();
                        GlobalVar.receivedBoard = (Board)o;
                        System.out.println("[received board (New Player Guest)]" + (o == null ? "null" : "not-null"));
                    } catch (ClassCastException | OptionalDataException e){
                        e.printStackTrace();
                        System.out.println(o);
                    } catch (Exception e){
                        e.printStackTrace();
                        try {
                            GlobalVar.csc.socket.close();
                        }
                        catch (IOException ioException) {
                            ioException.printStackTrace();
                        }catch (NullPointerException ignored){}
                        //GameJoiningWindow.end=false;
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println();
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        HanabiMain.gameInformation.settingsStage.close();
//                    }
//                });
                System.out.println("[received board (New Player Guest)]" + (o == null ? "nullll" : "not-nulllll"));
            }
        });
        t.start();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Lobby.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
