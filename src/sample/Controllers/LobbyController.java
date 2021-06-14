package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.util.Objects;

public class LobbyController {
    @FXML
    static Parent root;
    @FXML
    static Stage stage;

    public static void run() throws IOException {
        Parent root = null;
        root = FXMLLoader.load(LobbyController.class.getResource("/Scenes/OnlineGame.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        GlobalVar.mainStage = primaryStage;

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
}
