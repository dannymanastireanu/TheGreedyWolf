package GameState;

import java.sql.*;
import Audio.AudioPlayer;
import Entity.Enemies.Fox;
import Entity.Enemy;
import Entity.Explosion;
import Entity.Hud;
import Entity.Player;
import Main.DataBaseManager;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg, bbg;

    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private Hud hud;

    private DataBaseManager dataBaseManager;

//    private AudioPlayer bgMusic;

    public Level1State(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.png");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0,0);
        tileMap.setTween(1);

        bg = new Background("/Backgrounds/bgMountain.png", 0.1);
        bbg = new Background("/Backgrounds/bgTree.png", 0.1);

        player = new Player(tileMap);
        player.setPosition(100, 100);

        populateWithEnemies();

        explosions = new ArrayList<>();

        hud = new Hud(player);

        dataBaseManager = new DataBaseManager(player);
        dataBaseManager.createTable();

//        bgMusic = new AudioPlayer("/Music/musicGame.mp3");
//        bgMusic.play();


    }

    private void populateWithEnemies() {
        enemies = new ArrayList<>();

        Fox foxies;
        Point[] points = new Point[] {
                new Point(30, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200)
        };

        for (int i = 0; i < points.length; i++) {
            foxies = new Fox(tileMap);
            foxies.setPosition(points[i].x, points[i].y);
            enemies.add(foxies);
        }
    }

    @Override
    public void update() {

        player.update();

        tileMap.setPosition(GamePanel.WIDTH/2 - player.getx(), GamePanel.HEIGHT/2 - player.gety());
        bbg.setPosition(tileMap.getX(), tileMap.getY());

        if(player.checkAttack())
            player.checkAttack(enemies);

        for (Enemy enemy : enemies) {
            enemy.update();
            if(enemy.isDead()) {
                enemies.remove(enemy);
                explosions.add(new Explosion(enemy.getx(), enemy.gety()));
            }
        }

        for(Explosion e : explosions) {
            e.update();
            if(e.shouldRemove()){
                explosions.remove(e);
            }
        }

        if(enemies.isEmpty()){
            System.out.println("WIN");
            System.exit(1);
        }

    }

    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);
        bbg.draw(g);

        tileMap.draw(g);

        player.draw(g);

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for(Explosion e : explosions){
            e.setMapPosition((int)tileMap.getX(), (int)tileMap.getY());
            e.draw(g);
        }

        hud.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_SPACE) player.setJumping(true);
        if(k == KeyEvent.VK_X) player.setScratching(true);

        //data base stuff
        if(k == KeyEvent.VK_S)  dataBaseManager.insetInTable();
        if(k == KeyEvent.VK_U)  dataBaseManager.updatePlayerFromDataBase();
    }

    @Override
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_X) player.setScratching(false);
        if(k == KeyEvent.VK_SPACE) player.setJumping(false);
    }
}
