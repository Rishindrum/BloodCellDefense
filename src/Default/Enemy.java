package Default;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{

    private Handler handler;
    private BufferedImage germ;
    Random r = new Random();
    Game game;
    int choose = 0;
    int hp = 300;

    public Enemy(int x, int y, ID id, Handler handler, Game game, Spreadsheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
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

            if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Cell|| tempObject.getId() == ID.Cell1|| tempObject.getId() == ID.Cell2|| tempObject.getId() == ID.Cell3)
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
                    velX = (r.nextInt(6 - -6) + -6);
                    velY = (r.nextInt(6 - -6) + -6);
                }
            }
            if (tempObject.getId() == ID.Bullet)
            {
                if (getBounds().intersects(tempObject.getBounds()))
                hp-=50;
            }
            if (tempObject.getId() == ID.Cell&& game.State == Game.STATE.GAME)
            {
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.hp1 -= 1;
                    if(game.hp1<=0)
                    {
                        tObject = tempObject;
                        game.cells--;
                    }

                }
            }
            if (tempObject.getId() == ID.Cell1&& game.State == Game.STATE.GAME)
            {
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.hp2 -= 1;
                    if(game.hp2<=0)
                    {
                        tObject = tempObject;
                        game.cells--;
                    }

                }
            }
            if (tempObject.getId() == ID.Cell2&& game.State == Game.STATE.GAME)
            {
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.hp3 -= 1;
                    if(game.hp3<=0)
                    {
                        tObject = tempObject;
                        game.cells--;
                    }
                }
            }
            if (tempObject.getId() == ID.Cell3&& game.State == Game.STATE.GAME)
            {
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.hp4 -= 1;
                    if(game.hp4<=0)
                    {
                        tObject = tempObject;
                        game.cells--;
                    }


                }
            }

        }

        if(hp <= 0)
        handler.removeObject(this);
        if(tObject!=null)
        {
            handler.removeObject(tObject);
        }

    }

    public void render(Graphics g) {
        g.drawImage(germ, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }}

