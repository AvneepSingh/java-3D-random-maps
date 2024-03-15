import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferInt;
import java.io.Serial;
import java.util.ArrayList;
public class Game extends JFrame implements Runnable {
    public Screen screen;
    public Camera camera;
    public ArrayList<Texture> textures;
    @Serial
    private static final long serialVersionUID = 1L;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private final Thread thread;
    private boolean running;
    private final BufferedImage image;
    public int[] pixels;

public static int[][] map =Maps.getMap3();

    public Game() {
        thread = new Thread(this);
        image = new BufferedImage(640*2, 480*2, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        setSize(640*2, 480*2);
        setResizable(false);
        setTitle("3D Engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.blue);
        setLocationRelativeTo(null);
        setVisible(true);
        start();
        textures = new ArrayList<>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        textures.add(Texture.sonapanchod);
        textures.add(Texture.sona2);
        textures.add(Texture.sona3);
        screen = new Screen(map, mapWidth, mapHeight, textures, 640*2,480*2);
        camera = new Camera(4.5, 4.5, 0.6, 0, 0, -0.66);
        addKeyListener(camera);
    }
    private synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;//60 times per second
        double delta = 0;
        requestFocus();
        while(running) {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 60 times a second
            {
                //handles all the logic restricted time
                while(camera==null){System.out.println("...");}
                camera.update(map);
                while(screen==null){System.out.println("waiting {screen}");}
                screen.update(camera, pixels);
                delta--;
            }
            render();//displays to the screen unrestricted time
        }
    }
}