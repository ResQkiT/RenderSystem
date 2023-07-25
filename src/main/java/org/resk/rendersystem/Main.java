package org.resk.rendersystem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

/**
 * Hello world!
 *
 */
public class Main extends Canvas
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = WIDTH / 16 * 9;
    private static final int SCALE = 3;
    private String title = "Game";
    private boolean running = false;
    private BufferStrategy bs = null;
    private Graphics g = null;
    private Render renderer;
    private JFrame frame = new JFrame(title);
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int pixels[] = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Main() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        renderer = new Render(WIDTH, HEIGHT, pixels);
    }

   private void start(){
        running = true;
        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long jvmLastTime = System.nanoTime();
                long timer = System.currentTimeMillis();
                double jvmPartTime = 1_000_000_000.0 / 60;
                double delta = 0;
                int updates = 0;
                int frames = 0;

                while (running){
                    long jvmNow = System.nanoTime();
                    delta += (jvmNow - jvmLastTime);
                    jvmLastTime = jvmNow;
                    if(delta >= jvmPartTime) {
                        update();
                        updates += 1;
                        delta = 0;
                    }
                    render();
                    frames += 1;
                    if(System.currentTimeMillis() - timer > 1000){
                        timer += 1000;
                        frame.setTitle(title + " | " + "Updates: "+ updates + " Frames: " + frames);
                        updates = 0;
                        frames = 0;
                    }
                }
            }
        }).start();
   }



    private void update(){

   }

   private void render(){
        if(bs == null){
            createBufferStrategy(3);
            bs = getBufferStrategy();
        }
        renderer.clear();
        renderer.render();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0,0, getWidth(), getHeight(), null);
        g.dispose();
        bufferSwap();
   }

   private void bufferSwap(){
        bs.show();
   }
    private void init() {
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

}
