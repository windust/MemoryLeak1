package com.spinningnoodle.leak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Freddy on 1/31/2015.
 */
public class Utilities {

    public static Collection<String> getImages() {
        Collection<String> urls = new ArrayList<>();
        try {
            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
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
                urls.add(tbURL);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static Collection<String> getOfflineImages() {
        File folder = new File("src/main/resources/cats/");
        File[] listOfFiles = folder.listFiles();
        Collection<String> images = new ArrayList<>();
        for (File file : listOfFiles) {
            try {
                images.add(file.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
