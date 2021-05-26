package Default;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private Game game;
    private Spreadsheet ss;


    public MouseInput (Handler handler, Camera camera, Game game, Spreadsheet ss)
    {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }
    public void mousePressed(MouseEvent e)
    {
        if (game.State == Game.STATE.GAME) {
            int mx = (int) (e.getX() + camera.getX());
            int my = (int) (e.getY() + camera.getY());

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ID.Player && game.ammo >= 1) {
                    handler.addObject(new Bullet(tempObject.getX() + 16, tempObject.getY() + 24, ID.Bullet, handler, mx, my, ss));
                    game.ammo--;
                }
            }
        }
        if(game.State == Game.STATE.MENU)
        {
            if (e.getX() >= 300 && e.getX() <= 700)
            {
                if (e.getY() >= 250 && e.getY() <= 300)
                    game.State = Game.STATE.GAME;
            }
            if (e.getX() >= 300 && e.getX() <= 700)
            {
                if (e.getY() >= 350 && e.getY() <= 400)
                    game.State = Game.STATE.HELP;
            }
            if (e.getX() >= 300 && e.getX() <= 700)
            {
                if (e.getY() >= 450 && e.getY() <= 500)
                    System.exit(1);
            }
        }
        if(game.State == Game.STATE.OVER)
        {
            if (e.getX() >= 370 && e.getX() <= 570)
            {
                if (e.getY() >= 287 && e.getY() <= 312)
                    game.State = Game.STATE.MENU;
            }
        }
        if(game.State == Game.STATE.HELP)
        {
            if (e.getX() >= 20 && e.getX() <= 120)
            {
                if (e.getY() >= 20 && e.getY() <= 70)
                    game.State = Game.STATE.MENU;
            }
        }
    }

}
