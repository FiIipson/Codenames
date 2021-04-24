package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("enternames.fxml")));
        primaryStage.setTitle("Enter your names");
        primaryStage.setScene(new Scene(root, 1150, 750));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
