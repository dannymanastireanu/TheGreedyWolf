package Entity;

import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;

    private boolean gliding;

    //animations
    private ArrayList<BufferedImage> sprites;
    private final int[] numFrames = {4, 5, 2, 1, 5};

    //animation actions
    private  static final int IDLE = 0;
    private  static final int WALKING = 1;
    private  static final int JUMPING = 2;
    private  static final int FALLING = 3;
    private  static final int SCRATCHING = 4;


    public Player(TileMap tileMap) {
        super(tileMap);

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;

        scratchDamage = 8;
        scratchRange = 40;

        //load sprites
        try{

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
