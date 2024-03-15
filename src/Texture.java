import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
public class Texture {
    public static Texture wood = new Texture("src/res/wood.png", 64);
    public static Texture brick = new Texture("src/res/redbrick.png", 64);
    public static Texture bluestone = new Texture("src/res/bluestone.png", 64);
    public static Texture stone = new Texture("src/res/greystone.png", 64);

    public static Texture sonapanchod =  new Texture("src/res/sonapanchod.png",64);

    public static Texture sona2 =  new Texture("src/res/sona2.png",64);

    public static Texture sona3 = new Texture("src/res/sona3.png",64);
    public int[] pixels;
    private String loc;
    public final int SIZE;
    public Texture(String location, int size) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }
    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
