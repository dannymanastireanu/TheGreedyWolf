package Entity;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    boolean playOnce;

    public Animation(){
        this.playOnce = false;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playOnce = false;
    }

    public void update() {

        if(delay == -1) return;

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame = 0;
            playOnce = true;
        }
    }

    public BufferedImage[] getFrames() {
        return frames;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean hasPlayOnce() {
        return playOnce;
    }

    public void setPlayOnce(boolean playOnce) {
        this.playOnce = playOnce;
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }
}
