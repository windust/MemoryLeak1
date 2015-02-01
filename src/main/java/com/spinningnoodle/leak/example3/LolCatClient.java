package com.spinningnoodle.leak.example3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Created by Freddy on 1/31/2015.
 */
public class LolCatClient extends Application {
    private ImageView imageView;


    @Override
    public void start(Stage primaryStage) throws Exception {
        imageView = new ImageView();
        BorderPane pane = new BorderPane(imageView);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cat Client");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();

        try {
            SocketChannel socketChannel = SocketChannel.open();
            if (socketChannel.connect(new InetSocketAddress(10000))) {
                ClientReceiver receiver = new ClientReceiver(socketChannel);
                Thread receiverThread = new Thread(receiver::start);
                receiverThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private class ClientReceiver {
        ObjectInputStream ois = null;
        public ClientReceiver(SocketChannel socketChannel) {
            ObjectInputStream local = null;
            try {
                local = new ObjectInputStream(Channels.newInputStream(socketChannel));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.ois = local;
        }

        public void start() {
            while (true) try {
                LolMessage message = (LolMessage) ois.readObject();
                InputStream is = new ByteArrayInputStream(message.getImage());
                BufferedImage bufferedImage = ImageIO.read(is);
                Platform.runLater(() -> {
                    imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                });
                System.out.println("Received something at " + new Date());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
