package com.company;

import javax.swing.*;
import java.awt.*;

public class MainWindow1 extends JFrame {

    public MainWindow1() {
        setSize(1000, 1000);
        setTitle("1");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        this.add(new Game(this));
        setVisible(true);
    }
}