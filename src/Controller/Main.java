package Controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("../FXML/Interface.fxml"));
        Parent root =fxmloader.load();

        primaryStage.setTitle("Software Similarity Program");
        primaryStage.setScene(new Scene(root, 1400, 850));  //interface

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
