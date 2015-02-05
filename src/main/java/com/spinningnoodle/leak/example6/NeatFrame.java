package com.spinningnoodle.leak.example6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Freddy on 2/2/2015.
 */
public class NeatFrame extends JFrame {
    byte[] padding = new byte[3096];
    public NeatFrame(int number) {
        super("NeatFrame #"+number);
        setLayout(new BorderLayout());
        JButton myButton =new JButton("Button # "+number);
        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Woa!"+number);
            }
        });
        add(myButton);
        setSize(300,300);
        setVisible(true);
    }
}
