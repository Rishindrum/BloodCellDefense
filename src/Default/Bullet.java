package Default;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private Handler handler;
    private BufferedImage bullet;

    public Bullet(int x, int y, ID id, Handler handler, int mx, int my, Spreadsheet ss) {
        super(x, y, id, ss);
        this.handler = this.handler;
        bullet = ss.grabImage(32+32,128+80+200+32,8,8);
        velX = (mx - x) /10;
        velY = (my - y) /10;
    }

    public void tick() {
        x+=velX;
        y += velY;
    }

    public void render(Graphics g) {
        g.drawImage(bullet,x,y,null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
