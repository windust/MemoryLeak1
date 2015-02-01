package com.spinningnoodle.leak.example3;

import java.io.Serializable;

/**
 * Created by Freddy on 1/31/2015.
 */
public class LolMessage implements Serializable {
    byte[] image;

    public LolMessage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}
