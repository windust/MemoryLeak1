package com.spinningnoodle.leak.example1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Freddy on 1/31/2015.
 */
public class MainPanel extends Application {
    private Object controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("MainPanel.fxml"));
        Parent root = loader.load();
        stage.setTitle("Meetup Raffle");
        stage.setScene(new Scene(root, 800, 600));
        //stage.getOwner().setX();
        controller = loader.getController();
//        stage.setOnCloseRequest(e -> controller.stop(stage));
        stage.show();

    }
}
