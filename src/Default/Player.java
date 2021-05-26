package Default;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    Handler handler;
    Game game;
    private BufferedImage white_image;

    public Player(int x, int y, ID id, Handler handler, Game game, Spreadsheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;

        white_image = ss.grabImage(35, 128+80+200, 51, 51);
    }

    public void tick() {
        x += velX;
        y += velY;

        collision();

        //movement
        if(handler.isUp()) velY = -7;
        else if(!handler.isDown()) velY = 0;

        if(handler.isDown()) velY = 7;
        else if(!handler.isUp()) velY = 0;

        if(handler.isRight()) velX = 7;
        else if(!handler.isLeft()) velX = 0;

        if(handler.isLeft()) velX = -7;
        else if(!handler.isRight()) velX = 0;
    }

    private void collision() {
        for(int i =0; i<handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Cell || tempObject.getId() == ID.Cell1 || tempObject.getId() == ID.Cell2 || tempObject.getId() == ID.Cell3) {

                if(getBounds().intersects((Rectangle) tempObject.getBounds())) {
                    x += velX * -1;
                    y += velY * -1;
                }

            }
            if(tempObject.getId() == ID.Crate) {

                if(getBounds().intersects((Rectangle) tempObject.getBounds())&&game.ammo<=90) {
                    game.ammo += 10;
                    handler.removeObject(tempObject);
                }

            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(white_image, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,51,51);
    }
}
