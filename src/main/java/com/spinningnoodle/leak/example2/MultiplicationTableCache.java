package com.spinningnoodle.leak.example2;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Freddy on 2/1/2015.
 */
public class MultiplicationTableCache {
    public static MultiplicationTableCache INSTANCE = new MultiplicationTableCache();
    Map<Point2D,CacheResult> cacheResultMap = new ConcurrentHashMap<>();

    public static MultiplicationTableCache getInstance() { return INSTANCE;}

    public static class CacheResult {
        byte[] padding = new byte[2048];
        long result;
    }




}
