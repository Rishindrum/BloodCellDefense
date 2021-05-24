package Default;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{

    private Handler handler;
    private BufferedImage germ;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    int health = 100;
    int cells = 4;

    public Enemy(int x, int y, ID id, Handler handler, Spreadsheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        germ = ss.grabImage(128+10, 10, 50, 50);
    }

    public void tick() {
        x += velX;
        y += velY;
        GameObject tObject = null;
        choose = r.nextInt(10);

        for(int i = 0; i < handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Cell)
            {
                if(getBounds().intersects(tempObject.getBounds()))
                {
                    x += (velX*5) * -1;
                    y += (velY*5) * -1;
                    velX *= -1;
                    velY *= -1;
                }
                else if(choose == 0 )
                {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);
                }
            }
            if (tempObject.getId() == ID.Bullet)
            {
                if (getBounds().intersects(tempObject.getBounds()))
                hp-=50;
            }
            if (tempObject.getId() == ID.Cell)
            {
                if (getBounds().intersects(tempObject.getBounds())) {
                    health -= 1;
                    if(health<=0)
                        tObject = tempObject;

                }
            }

        }

        if(hp <= 0)
        handler.removeObject(this);
        if(health<=0 && tObject!=null)
        {
            handler.removeObject(tObject);
            cells--;
        }

    }

    public void render(Graphics g) {
        g.drawImage(germ, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }}

