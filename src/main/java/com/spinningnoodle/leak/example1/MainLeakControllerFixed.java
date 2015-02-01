package com.spinningnoodle.leak.example1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Freddy on 1/31/2015.
 */
public class MainLeakControllerFixed {
    public Button searchButton;
    public FlowPane imageFlowPane;
    public Set<Stage> currentPopups = new HashSet<>();

    public void searchLOLCatz(ActionEvent actionEvent) {
        URL url = null;
        try {
            url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                    "v=1.0&rsz=8&q=lolcats%20funny");
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Referer", "http://www.spinningnoodle.com");

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            System.out.println(builder.toString());
            JSONObject json = new JSONObject(builder.toString());
            JSONArray results = json.getJSONObject("responseData").getJSONArray("results");
            int maxResult = Math.min(9, results.length());
            for (int i =0;i < maxResult;i++) {
                JSONObject result = (JSONObject) results.get(i);
                final String tbURL = result.getString("url");
                ImageView imageView = new ImageView(tbURL);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        createFrame(tbURL);
                    }
                });
                imageFlowPane.getChildren().add(imageView);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                currentPopups.remove(newStage);
            }
        });
        newStage.show();
    }

    public void closeAll(ActionEvent actionEvent) {
        currentPopups.forEach(javafx.stage.Stage::close);
        currentPopups.clear();
    }
}
