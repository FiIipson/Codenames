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

import static java.lang.Thread.sleep;

public class NewPlayerHostController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    TextField name;
    @FXML
    ChoiceBox<GlobalVar.WordType> your_team;

    @FXML
    void initialize() {
        your_team.getItems().add(GlobalVar.WordType.BLUE);
        your_team.getItems().add(GlobalVar.WordType.RED);
    }

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
        GlobalVar.loadWords();
        GlobalVar.setWords(GlobalVar.difficulty);
        GlobalVar.board = new Board(GlobalVar.AllWords, new GlobalVar.Hint("", false, 0));
        try {
            GlobalVar.serverThread = new StartServer();
            GlobalVar.serverThread.start();
            GlobalVar.serverThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    t.interrupt();
                    GlobalVar.serverThread = null;
                }
            });
            while (!GlobalVar.serverReady) {
                sleep(10);
                if (GlobalVar.serverThread == null) {
                    System.out.println("[This IP address is currently running a server]");
                    return;
                }
                System.out.println("[waited]");
            }
            GlobalVar.serverID = "";
        } catch (RuntimeException | InterruptedException runtimeException) {
            GlobalVar.serverThread.interrupt();
            System.out.println("[This IP address is currently running a server]");
            return;
        }

        CreateGameSettingController.setUpBoard = GlobalVar.board;
        GlobalVar.csc = new ClientSideConnection();
        GlobalVar.receivedBoard = null;
        GlobalVar.joinThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = null;
                while (GlobalVar.receivedBoard == null) {
                    try {
                        o = GlobalVar.csc.in.readObject();
                        System.out.println(".");
                        GlobalVar.receivedBoard = (Board) o;
                        if (GlobalVar.receivedBoard == null) {
                            System.out.println("elo");
                        }
                        System.out.println("[received board (Host)]" + (o == null ? "null" : "not-null"));
                    } catch (ClassCastException | OptionalDataException e){
                        System.out.println(o);
                    } catch (Exception e){
//                        System.out.println("second");
//                        e.printStackTrace();
                        try {
                            GlobalVar.csc.socket.close();
                        }
                        catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        finally {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                Thread.currentThread().interrupt();
            }
        });
        GlobalVar.joinThread.start();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Lobby.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
