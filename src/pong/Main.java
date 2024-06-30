/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pong;

import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author chg
 */
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Pong");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(new Pong());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
