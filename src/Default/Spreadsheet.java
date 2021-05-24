package Default;

import java.awt.image.BufferedImage;

public class Spreadsheet {

    private BufferedImage image;

    public Spreadsheet (BufferedImage image)
    {
        this.image = image;
    }

    public BufferedImage grabImage (int x, int y, int w, int h)
    {
        return image.getSubimage(x,y, w, h);
    }
}
