package Default;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class Game extends Canvas implements Runnable, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private Spreadsheet ss;

    private static BufferedImage level = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;
    private BufferedImage endscreen = null;

    public int ammo = 50;
    public int hp1 = 400;
    public int hp2 = 400;
    public int hp3 = 400;
    public int hp4 = 400;
    public int cells = 4;
    public int wave = 1;
    public int HighScore = 1;

    private boolean skip = true;

    public boolean gameover = false;
    public boolean mouseIn = false;
    public boolean playhover = false;
    public boolean helphover = false;
    public boolean exithover = false;
    public boolean rethover = false;

    private Menu menu;
    private Help help;

    public enum STATE{
        MENU,
        GAME,
        OVER,
        HELP
    }

    public STATE State = STATE.MENU;

    public Game() {
        new Window(1000, 563, "Prabhas Rishindra CS Game!", this);
        start();

        handler = new Handler();
        camera = new Camera(0,0);

        menu = new Menu();
        this.addKeyListener(new KeyInput(handler, this));
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/LEVEL.png");
        sprite_sheet = loader.loadImage("/sprite.png");

        ss = new Spreadsheet (sprite_sheet);

        floor = ss.grabImage(0, 0, 32, 32);

        endscreen = loader.loadImage("/gameover.jpg");

        this.addMouseListener(new MouseInput(handler, camera, this, ss));
        this.addMouseMotionListener(this);
        loadLevel(level);
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                //updates++;
                delta--;
            }
            try {
                render();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
        }
        stop();
    }

    public void tick() {
        if (State == STATE.GAME) {
            for(int i =0; i<handler.object.size(); i++) {
                if(handler.object.get(i).getId() == ID.Player) {
                    camera.tick(handler.object.get(i));
                }
            }
        }


        handler.tick();
    }

    public void render() throws InterruptedException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        //////////////////////////////////

        g2d.translate(-camera.getX(), -camera.getY());

        for(int xx = 0; xx < 30*72; xx+=32)
        {
            for(int yy = 0; yy < 30*72; yy+=32)
                g.drawImage(floor, xx, yy, null);
        }

        handler.render(g);

        if (State == STATE.GAME) {
            g2d.translate(camera.getX(), camera.getY());
            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 16);
            g.setColor(Color.green);
            g.fillRect(5, 5, (int) (hp1 * 2), 16);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 16);
            g.drawString("Top Left Red Cell Health", 10, 5 + 12);
            g.setColor(Color.gray);
            g.fillRect(5, 26, 200, 16);
            g.setColor(Color.green);
            g.fillRect(5, 26, (int) (hp2 * 2), 16);
            g.setColor(Color.black);
            g.drawRect(5, 26, 200, 16);
            g.drawString("Top Right Red Cell Health", 10, 26 + 12);
            g.setColor(Color.gray);
            g.fillRect(5, 47, 200, 16);
            g.setColor(Color.green);
            g.fillRect(5, 47, (int) (hp4 * 2), 16);
            g.setColor(Color.black);
            g.drawRect(5, 47, 200, 16);
            g.drawString("Bottom Right Red Cell Health", 10, 47 + 12);
            g.setColor(Color.gray);
            g.fillRect(5, 68, 200, 16);
            g.setColor(Color.green);
            g.fillRect(5, 68, (int) (hp3*2), 16);
            g.setColor(Color.black);
            g.drawRect(5, 68, 200, 16);
            g.drawString("Bottom Left Red Cell Health", 10, 68 + 12);
            g.setColor(Color.gray);
            g.fillRect(5, 89, 200, 16);
            g.setColor(Color.white);
            g.fillRect(5, 89, ammo * 2, 16);
            g.setColor(Color.black);
            g.drawRect(5, 89, 200, 16);
            g.drawString("Ammo: " + ammo, 10, 89 + 12);
            g.setColor(Color.cyan);
            g.fillRect(5, 110, 200, 16);
            g.setColor(Color.black);
            g.drawRect(5, 110, 200, 16);
            g.drawString("Wave: " + wave, 10, 110 + 12);
            gameover = cells <= 2;

            if (gameover)
                State = STATE.OVER;
        }
        if(State == STATE.MENU)
        {
            ammo=50;
            hp1=100;
            hp2=100;
            hp3=100;
            hp4=100;
            gameover=false;
            cells=4;
            wave=1;
            menu.render(g);
            if (playhover) {
                g.setColor(Color.white);
                g.drawRect(300, 250, 400, 50);
            }
            if (helphover) {
                g.setColor(Color.white);
                g.drawRect(300, 350, 400, 50);
            }
            if (exithover) {
                g.setColor(Color.white);
                g.drawRect(300, 450, 400, 50);
            }
        }
        if(State == STATE.OVER)
        {
            camera.setX(0);
            camera.setY(0);
            g.setColor(Color.black);
            g.fillRect(0, 0, 1230, 1000);
            g.drawImage(endscreen, 150, 100, null);
            Font fnt = new Font("Arial", Font.BOLD, 75);
            g.setFont(fnt);
            g.setColor(Color.CYAN);
            g.drawString("Highest Wave: " + HighScore, 200, 100);
            if (mouseIn) {
                g.setColor(Color.white);
                g.drawRect(370, 287, 200, 25);
            }
        }

        if(State == STATE.HELP)
        {
            Graphics2D g2 = (Graphics2D) g;
            //black background
            Color maroon = new Color(120,0,0);
            g.setColor(maroon);
            g.fillRect(0, 0, 1000, 1000);

            //back button
            g.setColor(Color.white);
            g.drawRect(20,  20,  100,  50); //Back to menu button
            g.setFont(new Font("", 1, 30));
            g.drawString("BACK", 28, 55);
            if (rethover) {
                g.setColor(new Color(254, 44, 84));
                g.drawRect(20,  20,  100,  50); //Back to menu button
                g.setFont(new Font("", 1, 30));
                g.drawString("BACK", 28, 55);
            }

            //words at top
            g.setColor(new Color(254, 44, 84));
            g.setFont(new Font("", 1, 50));
            g.drawString("DIRECTIONS", 350, 180);

            //directions
            g.setColor(Color.white);
            g.setFont(new Font("", 1, 19));
            g.drawString("Use the W, A, S and D  keys to move. You are a white blood cell trying to save your red blood cells from ", 10, 250);
            g.drawString("germs. For as long as you can, attempt to shoot off the germs from your red blood cells. Click in the ", 10, 290);
            g.drawString("direction you want to shoot. You will see the ammo and health of each of your red blood cells on the top ", 10, 330);
            g.drawString("left. Once two red blood cells have been destroyed, you have lost. In order to gain ammo, collect the ", 10, 370);
            g.drawString("red and white pills. They each give 10 ammo. But, you cannot collect these pills if you have more than", 10, 410);
            g.drawString("90 ammo already. Survive as many waves as you can. Good luck, and please save the blood cells!", 10, 450);
        }
        //////////////////////////////////
        g.dispose();
        bs.show();

    }

    //loading the level
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for(int xx = 0; xx< w; xx++)
        {
            for(int yy = 0; yy<h; yy++)
            {
                int pixel = image.getRGB(xx,yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255)
                    handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));

                if(blue == 255 && green == 0)
                    handler.addObject(new Player(xx*32, yy*32, ID.Player, handler, this, ss));


                if(green == 255 && blue == 0)
                    handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler, this, ss));

                if(red == 150)
                    handler.addObject(new Cell(xx*32, yy*32, ID.Cell, ss));

                if(red == 153)
                handler.addObject(new Cell(xx*32, yy*32, ID.Cell1, ss));

                if(red == 156)
                handler.addObject(new Cell(xx*32, yy*32, ID.Cell2, ss));

                if(red == 159)
                handler.addObject(new Cell(xx*32, yy*32, ID.Cell3, ss));

                if(blue == 255 && green == 255)
                    handler.addObject(new Crate(xx*32, yy*32, ID.Crate, ss));
            }
        }
    }


    private void loadWave(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        int Ecount = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Enemy)
                Ecount ++;
        }

        int Ccount = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Crate)
                Ccount ++;
        }

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                skip = !skip;
                if(green == 255 && blue == 0 && Ecount < 30 && !skip) {
                    handler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, handler, this, ss));
                    Ecount++;
                }

                if(blue == 255 && green == 255 && Ccount < 9) {
                    handler.addObject(new Crate(xx*32, yy*32, ID.Crate, ss));
                    Ccount++;
                }

            }
        }
    }

    private void loadPlay(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        int Ccount1 = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Cell)
                Ccount1 ++;
        }

        int Ccount2 = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Cell1)
                Ccount2 ++;
        }

        int Ccount3 = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Cell2)
                Ccount3 ++;
        }

        int Ccount4 = 0;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Cell3)
                Ccount4 ++;
        }

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;

                skip = !skip;

                if(red == 150  && Ccount1 < 1 && !skip) {
                    handler.addObject(new Cell(xx * 32, yy * 32, ID.Cell, ss));
                    Ccount1++;
                }

                if(red == 153  && Ccount2 < 1 && !skip) {
                    handler.addObject(new Cell1(xx * 32, yy * 32, ID.Cell1, ss));
                    Ccount2++;
                }
                if(red == 156  && Ccount3 < 1 && !skip) {
                    handler.addObject(new Cell2(xx * 32, yy * 32, ID.Cell2, ss));
                    Ccount3++;
                }
                if(red == 159  && Ccount4 < 1 && !skip) {
                    handler.addObject(new Cell3(xx * 32, yy * 32, ID.Cell3, ss));
                    Ccount4++;
                }
            }
        }
    }



    public static void main(String[] args) {

        Game game = new Game();
        game.HighScore = 1;

       while(game.isRunning){
           if(game.wave==1)
               game.loadPlay(level);
            try {
                TimeUnit.MILLISECONDS.sleep(25000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.loadWave(level);
            game.wave++;
            if(game.State == STATE.GAME) {
                game.HighScore = game.wave;
            }
        }


    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

        if(State == STATE.OVER) {
            if (e.getX() >= 370 && e.getX() <= 570) {
                if (e.getY() >= 287 && e.getY() <= 312)
                    mouseIn = true;
            }
            if (e.getX() < 370 || e.getX() > 570) {
                mouseIn = false;
            }
            if (e.getY() < 287 || e.getY() > 312)
                mouseIn = false;
        }

        if(State == STATE.HELP) {
            if (e.getX() >= 20 && e.getX() <= 120) {
                if (e.getY() >= 20 && e.getY() <= 70)
                    rethover = true;
            }
            if (e.getX() < 20 || e.getX() > 120) {
                rethover = false;
            }
            if (e.getY() < 20 || e.getY() > 70)
                rethover = false;
        }
        if(State == STATE.MENU){
            if (e.getX() >= 300 && e.getX() <= 700)
            {
                if (e.getY() >= 250 && e.getY() <= 300)
                    playhover = true;
            }
            if (e.getX() < 300 || e.getX() > 700) {
                playhover = false;
            }
            if (e.getY() < 250 || e.getY() > 300)
                playhover = false;
            if (e.getX() >= 300 && e.getX() <= 700)
            {
                if (e.getY() >= 350 && e.getY() <= 400)
                    helphover = true;
            }
            if (e.getX() < 300 || e.getX() > 700) {
                helphover = false;
            }
            if (e.getY() < 350 || e.getY() > 400)
                helphover = false;
            if (e.getX() >= 300 && e.getX() <= 700)
            {
                if (e.getY() >= 450 && e.getY() <= 500)
                    exithover = true;
            }
            if (e.getX() < 300 || e.getX() > 700) {
                exithover = false;
            }
            if (e.getY() < 450 || e.getY() > 500)
                exithover = false;
        }
    }
}