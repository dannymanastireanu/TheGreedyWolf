package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Oposum extends Enemy {

    private BufferedImage[] sprites;

    public Oposum(TileMap tileMap) {
        super(tileMap);

        moveSpeed = 0.6;
        maxSpeed = 2;
        fallSpeed = 0.5;
        maxFallSpeed = 20.0;

        width = 36;
        height = 28;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 5;
        damage = 1;

        BufferedImage spritessheet = null;
        try {
            spritessheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemy/oposum.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sprites = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            sprites[i] = spritessheet.getSubimage(i*36, 0 , 36, 28);
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    public void setNextPosition() {

        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed)
                dx = -maxSpeed;
        }

        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed)
                dx = maxSpeed;
        }

        if(falling){
            dy += fallSpeed;
        }
    }

    public void update() {

        setNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400){
                flinching = false;
            }
        }

        if(right && dx == 0){
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        if(!facingRight) {
            g.drawImage(animation.getImage(), (int) (x + xmap - 36 / 2), (int) (y + ymap - 28 / 2), null);
        } else {
            g.drawImage(animation.getImage(), (int) (x + xmap -36 / 2 + 36), (int) (y + ymap - 28/2), -36, 28, null);
        }
    }
}
