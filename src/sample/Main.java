package sample;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/mainFrame.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.<MainController>getController();
        primaryStage.setOnCloseRequest(mainController.onCloseMethod);
        primaryStage.setTitle("Just Contacts");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
