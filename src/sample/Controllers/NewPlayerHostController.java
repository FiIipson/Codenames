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
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Server.ClientSideConnection;
import sample.Server.StartServer;

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

    public static Board setUpBoard;

    @FXML
    protected void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/PlayOnline.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void toLobby(ActionEvent e) throws IOException, InterruptedException {
        GlobalVar.loadWords();
        GlobalVar.setWords(GlobalVar.difficulty);
        GlobalVar.board = new Board(GlobalVar.word, new GlobalVar.Hint(":/", false, -1), GlobalVar.PlayerRole.BLUE_LEADER);
        GlobalVar.your_name = name.getText();

        try {
            GlobalVar.serverThread= new StartServer();
            GlobalVar.serverThread.start();
            GlobalVar.isAHost = true;
            GlobalVar.serverThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    t.interrupt();
                    GlobalVar.serverThread=null;
                }
            });
            while (!GlobalVar.serverReady) {
                sleep(10);
                if(GlobalVar.serverThread==null){
                    showAlert("This IP address is already running a server");
                    return;
                }
                System.out.println("waited");
            }
            GlobalVar.IP="";
        }catch (RuntimeException runtimeException){
            GlobalVar.serverThread.interrupt();
            showAlert("This IP address is already running a server");
            return;
        }

        setUpBoard = GlobalVar.board;
        GlobalVar.csc = new ClientSideConnection();
        GlobalVar.receivedBoard = null;
        GlobalVar.joinThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Object o = null;
                while (GlobalVar.receivedBoard == null) {
                    try {
                        System.out.println("biorę tablice");
                        o = GlobalVar.csc.in.readObject();
                        GlobalVar.receivedBoard = (Board)o;
                        System.out.println("[received board]");
                    } catch (ClassCastException | OptionalDataException e){
                        System.out.println(o);
                    } catch (Exception e){
                        try {
                            GlobalVar.csc.socket.close();
                        }
                        catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        finally {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    GlobalVar.mainStage.close();
                                }
                            });
                            Thread.currentThread().interrupt();
                        }
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GlobalVar.mainStage.close();
                            try {
                                LobbyController.run();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    });
                    Thread.currentThread().interrupt();
                }
            }
        });
        GlobalVar.joinThread.start();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Lobby.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    void showAlert(String s) throws IOException {
        GlobalVar.currentError = s;
        Stage alertBox = new Stage();
        alertBox.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scenes/AlertBox.fxml"));
        root = loader.load();
        alertBox.setScene(new Scene(root));
        alertBox.setTitle("Info");
        alertBox.showAndWait();
    }
}
