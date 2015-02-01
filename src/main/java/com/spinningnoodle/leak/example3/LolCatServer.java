package com.spinningnoodle.leak.example3;

import com.spinningnoodle.leak.Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Freddy on 1/31/2015.
 * Server that sends LOL Cat pictures at random
 */

public class LolCatServer {
    boolean local = true;
    List<ServerSender> connections = new CopyOnWriteArrayList<>();
    Timer sendTimer = new Timer("Company Timer",true);
    final List<String> urls;
    Random random = new Random();

    public LolCatServer() {
        this.urls = new ArrayList<>(Utilities.getOfflineImages());
    }

    public void start() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(10);
        service.scheduleAtFixedRate(this::sendMessage, 5000, 50, TimeUnit.MILLISECONDS);

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(10000));
            while (true) {
                SocketChannel channel = serverSocketChannel.accept();
                connections.add(new ServerSender(channel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String url = urls.get(random.nextInt(urls.size()));
        for (ServerSender conn : connections) {
            try {
                BufferedImage image = ImageIO.read(new URL(url));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                byte[] bytes = baos.toByteArray();

                LolMessage message = new LolMessage(bytes);
                conn.sendMessage(message);
            } catch (IOException e) {
                connections.remove(conn);
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args) {
        LolCatServer server = new LolCatServer();
        server.start();
    }

    /**
     * Created by Freddy on 1/31/2015.
     */
    public static class ServerSender {
        private final ObjectOutputStream oos;
        SocketChannel channel;


        public ServerSender(SocketChannel channel) {
            this.channel = channel;
            OutputStream os = Channels.newOutputStream(channel);
            ObjectOutputStream local = null;
            try {
                local = new ObjectOutputStream(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            oos = local;
        }

        public void sendMessage(Object message) throws IOException {
            oos.writeObject(message);
        }
    }
}
