package Default;

import Default.GameObject;
import Default.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell2 extends GameObject {

    BufferedImage cell;
    public Cell2(int x, int y, ID id, Spreadsheet ss) {

        super(x, y, id, ss);
        cell = ss.grabImage(0, 128+82,198, 198 );
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(cell, x-99, y-99, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x-75,y-75,150,150);
    }
}