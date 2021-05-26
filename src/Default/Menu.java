package Default;

import java.awt.*;

public class Menu {

    public Rectangle playbutton = new Rectangle (300, 250, 400, 50);
    public Rectangle helpbutton = new Rectangle (300, 350, 400, 50);
    public Rectangle exitbutton = new Rectangle (300, 450, 400, 50);
    public void render (Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Font fnt = new Font("arial", Font.BOLD, 100);
        g.setFont(fnt);
        g.setColor(Color.black);
        g.drawString("BLOOD CELL", 165, 100);
        g.drawString("DEFENSE", 260, 200);
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("PLAY", playbutton.x+165, playbutton.y+40);
        g.drawString("EXIT", exitbutton.x+170, exitbutton.y+40);
        g.drawString("HELP", helpbutton.x+165, helpbutton.y+40);
        g2d.draw(playbutton);
        g2d.draw(helpbutton);
        g2d.draw(exitbutton);
    }
}
