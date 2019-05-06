package Main;

import GameState.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;


    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        gsm.keyPressed(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gsm.keyReleased(keyEvent.getKeyCode());
    }

    private void init(){

        image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_BGR);

        g = (Graphics2D) image.getGraphics();

        running = true;

        gsm = new GameStateManager();
    }

    @Override
    public void run() {

        init();
        long lastTime = System.nanoTime();
        double targetFPS = 60.0;
        double ns = 1000000000.0 / targetFPS;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(running) {
            long nowTime = System.nanoTime();
            delta += (nowTime - lastTime) / ns;
            lastTime = nowTime;

            while(delta >= 1) {
                update();
                updates++;
                draw();
                drawToScreen();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void update() {
        gsm.update();
    }

    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE,null);
        g.dispose();
    }
}
