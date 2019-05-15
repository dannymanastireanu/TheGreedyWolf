package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    private boolean gliding;

    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;


    //animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {2, 5, 2, 1, 5};

    //animation actions
    private  static final int IDLE = 0;
    private  static final int WALKING = 1;
    private  static final int JUMPING = 2;
    private  static final int FALLING = 3;
    private  static final int SCRATCHING = 4;


    public Player(TileMap tileMap) {
        super(tileMap);

        width = 80;
        height = 46;
        cwidth = 57;
        cheight = 23;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -2.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;

        scratchDamage = 3;
        scratchRange = 30;

        //load sprites
        try{
            BufferedImage spritesSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.png"));
            sprites = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for (int j = 0; j < numFrames[i]; j++) {
                    bi[j] = spritesSheet.getSubimage(j * width, i * height, width, height);
                }
                sprites.add(bi);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
    }

    public void setHealth(int health) { this.health = health; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public void setScratching(boolean bool) { scratching = bool; }

    public void update() {

        //update position
        setNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 1000)
                flinching = false;
        }

        //set animation
        if(scratching){
            if(currentAction != SCRATCHING){
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(60);
            }
        }
        else if(dy > 0){
            if(currentAction != FALLING){
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
            }
        }
        else if(dy < 0){
            if(currentAction != JUMPING){
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
            }
        }
        else if(left || right){
            if(currentAction != WALKING){
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
            }
        }
        else {
            if(currentAction != IDLE){
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(600);
            }
        }

        animation.update();

        if(currentAction != SCRATCHING){
            if(right)   facingRight = true;
            if(left)    facingRight = false;
        }
    }

    public void draw(Graphics2D g) {

        setMapPosition();

        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0){
                return;
            }
        }

        super.draw(g);
    }

    private void setNextPosition() {

        //movement
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
        else {
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0)
                    dx = 0;
            }
            else if(dx < 0){
                dx += stopSpeed;
                if(dx > 0)
                    dx = 0;
            }
        }

        if(currentAction == SCRATCHING && !(jumping || falling))
            dx = 0;

        //jumping
        if(jumping){
            dy = jumpStart;
            falling = true;
        }

        //falling
        if(falling){
            dy += fallSpeed;
            if(dy < 0 && !jumping)  dy += stopJumpSpeed;
            if(dy > maxFallSpeed)   dy = maxFallSpeed;
        }
    }

    public void checkAttack(ArrayList<Enemy> enemies) {
        for (Enemy e : enemies) {
            if(facingRight) {
                if (e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2)
                    e.hit(scratchDamage);
            }
             else {
                    if (e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2)
                        e.hit(scratchDamage);
             }

             if(intersects(e)){
                 hit(e.getDamage());
             }
        }
    }

    public void hit(int damage){
        if(flinching)   return;
        health -= damage;
        if(health < 0)  health = 0;
        if(health == 0) dead = true;
        if(dead) {
            System.out.println("You dead");
            System.exit(1);
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    public boolean checkAttack(){
        return scratching;
    }

}
