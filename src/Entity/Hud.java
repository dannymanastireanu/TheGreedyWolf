package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Hud {

    private Player player;
    private BufferedImage image;
    private Font font;

    public  Hud(Player player){
        this.player = player;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Hud/hud.png"));
            font = new Font("Consolas", Font.PLAIN, 13);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {

        g.drawImage(image, 0, 0, null);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 15);
    }
}
