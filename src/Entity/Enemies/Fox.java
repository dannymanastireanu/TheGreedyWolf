package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Fox extends Enemy {

    private BufferedImage[] sprites;

    public Fox(TileMap tileMap) {
        super(tileMap);

        moveSpeed = 0.3;
        maxSpeed = 1;
        fallSpeed = 0.3;
        maxFallSpeed = 10.0;

        width = 33;
        height = 32;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 2;
        damage = 1;

        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemy/fox.png"));
            sprites = new BufferedImage[6];
            for (int i = 0; i < 6; i++) {
                sprites[i] = spritesheet.getSubimage(i*33, 0, 33, 32);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    private void setNextPosition() {

        if (left){
            dx -= moveSpeed;
            if(dx < - maxSpeed)
                dx = -maxSpeed;
        }
        else if (right){
            dx += moveSpeed;
            if(dx > maxSpeed)
                dx = maxSpeed;
        }

        if(falling){
            dy +=fallSpeed;
        }
    }

    public void update() {

        setNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400)
                flinching = false;
        }

        //collision with walls
        if(right && dx == 0){
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0){
            right = true;
            left = false;
            facingRight = true;
        }

        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        if(facingRight) {
            g.drawImage(animation.getImage(), (int) (x + xmap - 33 / 2), (int) (y + ymap - 32 / 2), null);
        } else {
            g.drawImage(animation.getImage(), (int) (x + xmap -33 / 2 + 33), (int) (y + ymap - 32/2), -33, 32, null);
        }
    }
}
