package lp2.juegolp2.Facilidades;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader extends Loader
{
    /**
     * To create compatible images.
     */
    private GraphicsConfiguration gc;

    /**
     * ImageLoader with the path of the package directory
     * and with those directories <code>loader</code>.
     */
    public ImageLoader()
    {
        this(".", "");
    }

    /**
     * ImageLoader with the indicated path and a
     * directory loader.
     *
     * @param path Directory with the images.
     */
    public ImageLoader(String path)
    {
        this(path, "");
    }

    /**
     * ImageLoader with the indicated path and the
     * indicated directory.
     *
     * @param path Directory with the images.
     * @param loader File with the images to load.
     */
    public ImageLoader(String path, String loader)
    {
        super(path, loader);
        loaded = new HashMap<>();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Returns the default GraphicsConfiguration
     * to create compatible images.
     * 
     * @return Current GraphicsConfiguration
     */
    public GraphicsConfiguration getGraphicsConfiguration()
    {
        return gc;
    }

    /**
     * Analysing a line of text that contains a
     * load information.
     *
     * @param line Information to load an image/s.
     */
    @Override
    public void loadLine(String line)
    {
        super.loadLine(line);
        if (line.startsWith("3 ")) {
            loadSpritesheet(line);
        }
    }

    private boolean loadSpritesheet(String line)
    {
        boolean error = false;
        StringTokenizer st = new StringTokenizer(line, " ");
        if (st.countTokens() >= 4) {
            st.nextToken(); // Discard line format (1, 2, etc)
            String name = st.nextToken();
            int equals = name.indexOf("=");
            int row = 0, col = 0;
            try {
                row = Integer.parseInt(st.nextToken());
                col = Integer.parseInt(st.nextToken());
            } catch (NumberFormatException e) {
                error = true;
            }
            if (!error) {
                if (equals == -1) {
                    if (!super.load(name)) {
                        return false;
                    }
                } else {
                    String filename = name.substring(equals+1);
                    name = name.substring(0, equals);
                    if (!super.load(filename, name)) {
                        return false;
                    }
                }
                BufferedImage bi = getImage(name);
                int w = bi.getWidth() / col;
                int h = bi.getHeight() / row;
                
                String nextName = null;
                while (st.hasMoreTokens() && !error) {
                    nextName = st.nextToken();
                    equals = nextName.indexOf("=");
                    if (equals == -1) {
                        error = true;
                        break;
                    }
                    String imgPos = nextName.substring(0, equals);
                    String[] indices = imgPos.split("_");
                    int imgRow = 0;
                    int imgCol = 0;
                    try {
                        imgRow = Integer.parseInt(indices[0]);
                        imgCol = Integer.parseInt(indices[1]);
                    } catch (Exception ex) {
                        error = true;
                    }
                    if (!error) {
                        BufferedImage tmp = bi.getSubimage(imgCol*w, imgRow*h, w, h);
                        BufferedImage img = this.createCompatibleImage(tmp);
                        
                        loaded.put(nextName.substring(equals+1), img);
                        System.out.print(nextName.substring(equals+1) + " -> ");
                        System.out.println(name + imgRow + "_" + imgCol);
                    }
                }
                this.removeObject(name);
            }
        } else {
            // not enough arguments
            error = true;
        }
        if (error) {
            System.err.println("Format error in line: " + line);
        }
        return error;
    }

    @Override
    public boolean load(File f, String name, boolean rewrite) {
        if (name == null) {
            name = f.getName();
        }
        if (!rewrite && loaded.containsKey(name)) {
            return false;
        }
        try {
            BufferedImage bi = ImageIO.read(f);
            BufferedImage img = this.createCompatibleImage(bi);
            
            loaded.put(name, img);
        } catch (IOException e) {
            System.err.println("Error loading image " + f.getPath());
            return false;
        }
        System.out.println("Loaded " + name + " from "+f.getName());
        return true;
    }

    /**
     * Returns a previously loaded image.
     */
    public BufferedImage getImage(String name) {
        return (BufferedImage)loaded.get(name);
    }

    /**
     * Returns a image. If <code>load</code> is
     * <code>true</code> the image is loaded dynamically
     */
    public BufferedImage getImage(String name, boolean load, boolean rewrite) {
        Object o = super.getObject(name, load, rewrite);
        if (o == null) {
            return null;
        }
        return (BufferedImage)o;
    }
    
    public BufferedImage createCompatibleImage(BufferedImage buffer)
    {
        int transparency = buffer.getColorModel().getTransparency();
        BufferedImage img = gc.createCompatibleImage(
            buffer.getWidth(), buffer.getHeight(), transparency
        );
            
        Graphics2D g = img.createGraphics();
        g.drawImage(buffer, 0, 0, null);
        g.dispose();
        
        return img;
    }
}