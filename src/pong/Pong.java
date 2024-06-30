/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pong;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author chg
 */
public class Pong extends JPanel implements ActionListener, KeyListener, MouseListener {

    private int width = 700;
    private int height = 400;
    private int borderSpace = 5;
    private Point player_1, player_Compu, ball, btnPoint;
    private int player_W = height / 40;
    private int player_H = height / 8;
    private int ballSize = (int) (player_W * 1.5);
    private int vel = 10;
    private int velX = 10;
    private int velY = 10;
    private boolean up = false;
    private boolean down = false;
    private boolean ballActive = false;
    private int scoreA;
    private int scoreB;
    private boolean gameOver = false;
    private int btnW;
    private int btnH;

    public Pong() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        setLayout(null);
        startGame();
    }

    private void startGame() {
        player_1 = new Point(borderSpace * 2, height / 2 - player_H / 2);
        player_Compu = new Point((width - borderSpace * 2) - player_W, height / 2 - player_H / 2);
        ball = new Point(player_1.x + player_W, height / 2 - ballSize / 2);
        Timer time = new Timer(1000 / 65, this);
        time.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background color
        g.setColor(new Color(5, 115, 230));
        g.fillRect(borderSpace, borderSpace, width - borderSpace * 2, height - borderSpace * 2);
        // Outside border
        g.setColor(Color.WHITE);
        g.drawRect(borderSpace, borderSpace, width - borderSpace * 2, height - borderSpace * 2);
        // Middle line
        int rW = 4;
        int rH = 14;// have to be pair num
        int space = (height - rH - borderSpace * 2) % rH;

        for (int i = borderSpace + space / 2; i < height - borderSpace; i += rH) {
            g.fillRect(width / 2 - rW, i, rW, rH);
            i += rH;
        }
        // Player_1
        g.fillRect(player_1.x, player_1.y, player_W, player_H);
        // Compu player
        g.fillRect(player_Compu.x, player_Compu.y, player_W, player_H);
        // ball
        g.fillOval(ball.x, ball.y, ballSize, ballSize);
        // Score
        g.drawString("Score: " + scoreA, width / 4 - 30, borderSpace * 4);
        g.drawString("Score: " + scoreB, width - width / 4 - 30, borderSpace * 4);
        // Game Over
        if (gameOver) {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        // G.O. windows
        g.setColor(new Color(50, 50, 50, 90));
        g.fillRect(0, 0, width, height);

        // Game over string
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("GAME OVER", width / 2 - 100, height / 2);

        // button draw
        g.setColor(new Color(210, 125, 60));
        double per = 0.6;
        btnW = 100;
        btnH = 30;
        int round = 15;
        btnPoint = new Point(width / 2 - btnW / 2, (int) (height * per));
        g.fillRoundRect(btnPoint.x, btnPoint.y, btnW, btnH, round, round);
        g.setColor(Color.WHITE);
        g.drawRoundRect(btnPoint.x, btnPoint.y, btnW, btnH, round, round);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("PLAY AGAIN", width / 2 - 35, (int) (height * per) + 20);
    }

    private void update() {
        // movement player 1
        if (!down && up && player_1.y > borderSpace) {
            player_1.y -= vel;
        }
        if (!up && down && player_1.y + player_H < (height - borderSpace)) {
            player_1.y += vel;
        }
        // movement player computer
        if (ballActive && ball.x > width / 3) {
            if (ball.y < player_Compu.y + player_H / 2
                    && player_Compu.y > borderSpace) {
                player_Compu.y -= vel;
            }
            if (ball.y > player_Compu.y + player_H / 2
                    && player_Compu.y + player_H < (height - borderSpace)) {
                player_Compu.y += vel;
            }
        }

        // movement ball inactive
        if (!ballActive) {
            if (ball.x < width / 2) {
                ball = new Point(player_1.x + player_W, player_1.y + player_H / 2);
            } else {
                ball = new Point(player_Compu.x - ballSize, player_Compu.y + player_H / 2);
            }
        }

        // movement ball in the court
        if (ballActive) {
            ball.x += velX;
            ball.y += velY;
        }
        if (ball.y > height - borderSpace || ball.y <= borderSpace) {
            velY *= -1;
        }

        if (ball.x + ballSize > player_Compu.x //right Side
                //&& ball.x + ballSize < player_Compu.x + player_W
                && ball.y + ballSize / 2 >= player_Compu.y
                && ball.y + ballSize / 2 <= player_Compu.y + player_H
                || ball.x < player_1.x + player_W // left side 
                //&& ball.x > player_1.x
                && ball.y + ballSize / 2 >= player_1.y
                && ball.y + ballSize / 2 <= player_1.y + player_H) {
            velX *= -1;
        }
    }

    private void score() {
        if (ball.x + ballSize > width - borderSpace) {
            scoreA++;
            ballActive = false;
            ball = new Point(player_1.x + player_W, player_1.y + player_H / 2);
            velX = Math.abs(velX);
        }
        if (ball.x < borderSpace) {
            scoreB++;
            ballActive = false;
            ball = new Point(player_Compu.x - ballSize, player_Compu.y + player_H / 2);
            velX = Math.abs(velX);
            velX *= -1;
        }
        if (scoreA == 5 || scoreB == 5) {
            gameOver = true;
        }
    }

    private void restart() {
        scoreA = 0;
        scoreB = 0;
        gameOver = false;
        ball = new Point(player_1.x + player_W, height / 2 - ballSize / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        score();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!ballActive) {
                ballActive = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver
                && e.getPoint().x > btnPoint.x
                && e.getPoint().x < btnPoint.x + btnW
                && e.getPoint().y > btnPoint.y
                && e.getPoint().y < btnPoint.y + btnH) {
            restart();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
