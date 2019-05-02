package GameState;

import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;

public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg, bbg;


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
        bbg = new Background("/Backgrounds/bgTree.png", 3);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);
        bbg.draw(g);

        tileMap.draw(g);
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }
}
