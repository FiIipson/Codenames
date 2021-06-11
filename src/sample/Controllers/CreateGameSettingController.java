package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class CreateGameSettingController {
    public static Board setUpBoard;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    public Text id_hints;
    @FXML
    public Text id_time_limit;
    @FXML
    public Text id_difficulty;
    @FXML
    public void initialize() throws FileNotFoundException {
        if (GlobalVar.difficulty == 0) {
            id_difficulty.setText("Easy");
        } else if (GlobalVar.difficulty == 1) {
            id_difficulty.setText("Mid");
        } else {
            id_difficulty.setText("Hard");
        }
        GlobalVar.AllWords = GlobalVar.loadWords();
    }
    @FXML
    protected void upDifficulty() {
        if (GlobalVar.difficulty < 2) GlobalVar.difficulty++;
        if (GlobalVar.difficulty == 1) id_difficulty.setText("Mid");
        else id_difficulty.setText("Hard");
    }
    @FXML
    protected void downDifficulty() {
        if (GlobalVar.difficulty > 0) GlobalVar.difficulty--;
        if (GlobalVar.difficulty == 1) id_difficulty.setText("Mid");
        else id_difficulty.setText("Easy");
    }
    @FXML
    protected void toGame(ActionEvent e) throws IOException {
        GlobalVar.setWords(GlobalVar.difficulty);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/NewPlayerHost.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    protected void back(ActionEvent e) throws IOException {
        GlobalVar.timeLimit = 90;
        GlobalVar.difficulty = 1;
        GlobalVar.hints = false;
        GlobalVar.seconds = 3;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/PlayOnline.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
