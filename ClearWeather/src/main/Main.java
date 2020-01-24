package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //final TrayIcon trayIcon = new TrayIcon(new Image);
        //final SystemTray systemTray = SystemTray.getSystemTray();


        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        var scene = new Scene(root, 908, 640);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img/icons/app.jpg")));
        primaryStage.setTitle("Clear Weather");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
