package lp2.juegolp2.Facilidades;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.awt.Graphics;
import lp2.juegolp2.Interfaz.Sprite;

/**
 *
 * @author pmvb
 */
public class WorldObject
{
    @XStreamOmitField
    protected ImageLoader imgLoader;
    @XStreamOmitField
    protected Sprite sprite;
    
    public WorldObject(ImageLoader imgLoader)
    {
        this.imgLoader = imgLoader;
        this.sprite = new Sprite(imgLoader);
    }
    
    public void setImageLoader(ImageLoader imgLoader)
    {
        this.imgLoader = imgLoader;
        if (this.sprite == null) {
            this.sprite = new Sprite(imgLoader);
        } else {
            this.sprite.setImageLoader(imgLoader);
        }
    }
    
    public void draw(Graphics g, double x, double y)
    {
        if (this.sprite != null)
            this.sprite.paint(g, x, y);
    }
    
    public ImageLoader getImageLoader()
    {
        return this.imgLoader;
    }
}
