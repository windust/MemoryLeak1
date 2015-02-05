package com.spinningnoodle.leak.example6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Freddy on 2/2/2015.
 */
public class FrameWithButtons {
    NeatFrame priorFrame = null;
    int count = 0;
    public static void main(String[] args) {
        FrameWithButtons buttons = new FrameWithButtons();
        buttons.start();
    }

    private void start() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priorFrame != null) priorFrame.dispose();
                priorFrame = new NeatFrame(++count);
            }
        });
        timer.setRepeats(true);
        timer.start();
        JFrame endingFrame = new JFrame("Stopping");
        endingFrame.setLayout(new BorderLayout());
        JButton close = new JButton("CLOSE");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                endingFrame.dispose();
            }
        });
        endingFrame.add(close);
        endingFrame.setSize(300,200);
        endingFrame.setLocation(200,200);
        endingFrame.setVisible(true);
    }
}
