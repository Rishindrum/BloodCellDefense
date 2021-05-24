package Default;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate  extends GameObject{
    BufferedImage crate;
    public Crate(int x, int y, ID id, Spreadsheet ss) {
        super(x, y, id, ss);
        crate = ss.grabImage(0, 128+80+200, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(crate, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
