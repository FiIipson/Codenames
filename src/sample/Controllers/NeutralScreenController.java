package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NeutralScreenController {
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Text time;
    @FXML public Button aa;
    @FXML public Button ab;
    @FXML public Button ac;
    @FXML public Button ad;
    @FXML public Button ae;
    @FXML public Button ba;
    @FXML public Button bb;
    @FXML public Button bc;
    @FXML public Button bd;
    @FXML public Button be;
    @FXML public Button ca;
    @FXML public Button cb;
    @FXML public Button cc;
    @FXML public Button cd;
    @FXML public Button ce;
    @FXML public Button da;
    @FXML public Button db;
    @FXML public Button dc;
    @FXML public Button dd;
    @FXML public Button de;
    @FXML public Button ea;
    @FXML public Button eb;
    @FXML public Button ec;
    @FXML public Button ed;
    @FXML public Button ee;

    @FXML
    public void initialize() {
        int timeLimit = GlobalVar.timeLimit;
        int seconds = GlobalVar.seconds;
        if (timeLimit == 0 || timeLimit == 210) time.setText("∞");
        else {
            time.setText(timeLimit / 60 + ":" + seconds + "0");
        }
        Button[] button = new Button[] {aa, ab, ac, ad, ae, ba, bb, bc, bd, be, ca, cb, cc, cd, ce, da, db, dc, dd, de, ea, eb, ec, ed, ee};
        int count = 0;
        for (Button b : button) b.setText(GlobalVar.word[count++].getText());
    }

    @FXML
    public void toLoadingScreen(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scenes/ChangeScreen.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}