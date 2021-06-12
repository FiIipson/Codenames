package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Server.GamePlayer;
import sample.Server.GameServer;
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
    public void toLobby(ActionEvent e) throws IOException, InterruptedException {
        GlobalVar.redLeaderName = name.getText();
        System.out.println(GlobalVar.redLeaderName);
        GlobalVar.serverThread = new StartServer();
        GlobalVar.serverThread.start();
        GlobalVar.serverThread.setUncaughtExceptionHandler((t, e1) -> {
            t.interrupt();
            GlobalVar.serverThread=null;
        });

        while (!GlobalVar.serverReady) {
            sleep(10);
        }
        GlobalVar.IP = "";

        GamePlayer.main();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/Lobby.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
