package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion {

    private int x;
    private int y;
    private int xmap;
    private int ymap;

    private int width;
    private int height;

    private Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    public Explosion(int x, int y) {

        this.x = x;
        this.y = y;

        width = 40;
        height = 41;

        try {
            BufferedImage spriteshit = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemy/enemy-deadth.png"));
            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteshit.getSubimage(i*width, 0, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }

    public void update() {
        animation.update();
        if(animation.hasPlayOnce())
            remove = true;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void setMapPosition(int x, int y) {
        xmap = x;
        ymap= y;
    }

    public void draw(Graphics2D g) {
        g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
    }

}
