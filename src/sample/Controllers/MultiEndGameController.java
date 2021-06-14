package sample.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Server.ClientSideConnection;
import sample.Server.GameServer;

import java.io.IOException;
import java.io.OptionalDataException;
import java.util.Objects;

import static sample.Controllers.GlobalVar.blueTotal;
import static sample.Controllers.GlobalVar.redTotal;

public class MultiEndGameController {
    String blue_win = "Blue team wins by revealing all the cards";
    String red_win = "Red team wins by revealing all the cards";
    String red_bomb = "Red team wins by detonating enemies";
    String blue_bomb = "Blue team wins by detonating enemies";
    Board setUpBoard;
    @FXML
    Text to_be_shown;
    @FXML
    public void initialize() {
        if (GlobalVar.receivedBoard.gameResult == GlobalVar.Result.BLUE) {
            to_be_shown.setText(blue_win);
            to_be_shown.setFill(Color.BLUE);
        }
        else if (GlobalVar.receivedBoard.gameResult == GlobalVar.Result.RED) {
            to_be_shown.setText(red_win);
            to_be_shown.setFill(Color.RED);
        }
        else if (GlobalVar.receivedBoard.gameResult == GlobalVar.Result.RED_BOMB) {
            to_be_shown.setText(blue_bomb);
            to_be_shown.setFill(Color.BLUE);
        }
        else {
            to_be_shown.setText(red_bomb);
            to_be_shown.setFill(Color.RED);
        }
    }

    public static void run() throws IOException {
//        GlobalVar.mainStage.close();
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(LobbyController.class.getResource("/Scenes/MultiEndGame.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        //primaryStage.show();
        GlobalVar.mainStage.close();
        GlobalVar.mainStage = primaryStage;
        GlobalVar.mainStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            logout(primaryStage);
        });
    }

    static public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to quit the game?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        if (alert.showAndWait().orElse(null) == buttonTypeYes) {
            stage.close();
            System.exit(0);
        }
    }

    @FXML
    protected void back(ActionEvent e) throws IOException {
//        if (GlobalVar.yourRole.equals(GlobalVar.PlayerRole.BLUE_LEADER)) {
//            GlobalVar.setWords(GlobalVar.difficulty);
//            GlobalVar.board = new Board(GlobalVar.word, new GlobalVar.Hint(":/", false, -1), GlobalVar.PlayerRole.BLUE_LEADER);
//            setUpBoard = GlobalVar.board;
//            GlobalVar.makeNeutral(setUpBoard.words);
//            GlobalVar.setOneType(setUpBoard.words, GlobalVar.WordType.BOMB, 1);
//            GlobalVar.setOneType(setUpBoard.words, GlobalVar.WordType.RED, 8);
//            GlobalVar.setOneType(setUpBoard.words, GlobalVar.WordType.BLUE, 8);
//            ClientSideConnection.out.writeObject(setUpBoard);
//            GlobalVar.receivedBoard = null;
////        GlobalVar.joinThread.interrupt();
//            GlobalVar.joinThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Object o = null;
//                    while (GlobalVar.receivedBoard == null) {
//                        try {
//                            System.out.println("biorę tablice");
//                            o = GlobalVar.csc.in.readObject();
//                            GlobalVar.receivedBoard = (Board) o;
//                            System.out.println("[received board]");
//                        } catch (ClassCastException | OptionalDataException e) {
//                            System.out.println(o);
//                        } catch (Exception e) {
//                            try {
//                                GlobalVar.csc.socket.close();
//                            } catch (IOException ioException) {
//                                ioException.printStackTrace();
//                            } finally {
//                                Platform.runLater(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        GlobalVar.mainStage.close();
//                                    }
//                                });
//                                Thread.currentThread().interrupt();
//                            }
//                        }
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                GlobalVar.mainStage.close();
//                                try {
//                                    LobbyController.run();
//                                } catch (IOException ioException) {
//                                    ioException.printStackTrace();
//                                }
//                            }
//                        });
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            });
//            GlobalVar.joinThread.start();
//        }
        GlobalVar.mainStage.close();
        System.exit(0);
//        GlobalVar.mainStage.close();
//        LobbyController.run();
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Menu.fxml")));
//        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
//        stage.setScene(new Scene(root));
//        stage.show();
//        if (GlobalVar.isAHost) {
//            GlobalVar.serverThread.interrupt();
//            GameServer.serverSocket.close();
//        }
    }
}
