package com.spinningnoodle.leak.example1;

import com.spinningnoodle.leak.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Freddy on 1/31/2015.
 */
public class MainLeakController {
    public Button searchButton;
    public FlowPane imageFlowPane;
    public Set<Stage> currentPopups = new HashSet<>();
    public CheckBox offlineCheckBox;

    public void searchLOLCatz(ActionEvent actionEvent) {
        Collection<String> urls;
        urls = offlineCheckBox.isSelected() ? Utilities.getOfflineImages() : Utilities.getImages();

        for (String url : urls) {
            ImageView imageView = new ImageView(url);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (int i =0;i < 20;i++) {
                        createFrame(url);
                    }
                }
            });
            imageFlowPane.getChildren().add(imageView);
        }
    }



    private void createFrame(String tbURL) {
        Stage newStage = new Stage();
        BorderPane pane = new BorderPane(new ImageView(tbURL));
        Scene scene = new Scene(pane);
        newStage.setScene(scene);
        newStage.setTitle(tbURL);
        currentPopups.add(newStage);
        newStage.setX(0);
        newStage.setY(0);
        newStage.show();
    }

    public void closeAll(ActionEvent actionEvent) {
        currentPopups.forEach(javafx.stage.Stage::close);
    }
}
