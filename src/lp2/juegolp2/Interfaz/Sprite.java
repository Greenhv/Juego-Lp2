package lp2.juegolp2.Interfaz;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import lp2.juegolp2.Facilidades.ImageLoader;

public class Sprite
{
    /**
     * Indica si se van a dibujar los bordes detecta
     * colisiones de los Sprites.
     */
    public static boolean drawBounds = false;

    /**
     * Coordenadas que marcan la posicion del objeto o personaje.
     * Se utilizan estas coordenadas de tipo float porque
     * permiten realizar movimientos con mayor precisi'n.
     */
    protected float x, y;

    /**
     * Indica el ancho y algo de la imagen actual del sprite.
     */
    protected int width, height;

    /**
     * Profundidad del objeto, utilizada en las colisiones.
     * Por defecto es 0, pero puede tomar cualquier valor.
     */
    protected int z;

    /**
     * Conjunto de rectángulos que sirven para
     * controlar las colisiones.
     */
    protected ArrayList<Rectangle> bounds;

    /**
     * Nombre de las im'genes cargadas con anterioridad desde un
     * ImagesLoader.
     */
    protected String[] imgNames;

    /**
     * Imagen actual del Sprite. Su nombre se encuentra en el
     * array imgNames.
     */
    protected BufferedImage img;

    /**
     * Indica el 'ndice en el que se encuentra la imagen actual
     * del Sprite.
     */
    protected int imgIndex;

    protected ImageLoader imgLoader;

    /**
     * Indica si es visible el componente. Si no lo es,
     * el m'todo paint(Graphics) no har' nada.
     */
    protected boolean visible;

    /**
     * Indica si est' activo. Si no lo est', no habr' cambio
     * entre las im'genes del Sprite.
     */
    protected boolean active;

    /**
     * Evita que cada vez que se cargue una imagen
     * se cambie el valor del acho y del alto por el
     * de la nueva imagen.
     */
    protected boolean preferredSize;

    /**
     * Indica cuando es posible borrar el objeto.
     */
    protected boolean delete;



    public Sprite(ImageLoader imgLoader)
    {
        this.imgLoader = imgLoader;
        x = 0;
        y = 0;
        z = 0;
        visible = true;
        active = true;
        preferredSize = false;
        delete = false;
        imgIndex = 0;
        bounds = new ArrayList<Rectangle>();
    }
    
    /**
     * Cambia la lista de nombres de las im'genes.
     * Establece la primera imagen de ese array como la
     * actual.
     */
    public void setImages(String[] imgNames)
    {
        setImages(imgNames, 0);
    }

    /**
     * Cambia la lista de nombres de las im'genes.
     * La imagen inicial va indicada por <code>initIndex</code>.
     */
    public void setImages(String[] imgNames, int initIndex)
    {
        this.imgNames = imgNames;
        imgIndex = initIndex;
        this.setImage(imgNames[initIndex]);
    }

    /**
     * Cambia la imagen por la siguiente del array. Si la siguiente
     * imagen corresponde a la 'ltima del array devuelve un true,
     * si no, false.
     * El Sprite debe de encontrarse activo.
     */
    public boolean nextImg()
    {
        // Actualizamos en 'ndice para que apunte a la
        // siguiente imagen.
        imgIndex = (imgIndex+1)%imgNames.length;
        this.setImage(imgNames[imgIndex]);
        // Si es la 'ltima imagen del array imgNames...
        if (imgIndex == imgNames.length-1) {
            return true;
        }
        return false;
    }

    /**
     * Cambia la imagen por la anterior del array. Si la anterior
     * imagen corresponde a la primera del array devuelve un true,
     * si no, false.
     * El Sprite debe de encontrarse activo.
     */
    public boolean previousImg()
    {
        // Actualizamos en 'ndice para que apunte a la
        // anterior imagen.
        imgIndex = (imgIndex-1)%imgNames.length;
        this.setImage(imgNames[imgIndex]);
        // Si es la 'ltima imagen del array imgNames...
        if (imgIndex == 0) {
            return true;
        }
        return false;
    }

    /**
     * Cambia la imagen que representa al Sprite.
     * Devuelve si es cargada con 'xito.
     * El Sprite debe de encontrarse activo.
     */
    public boolean setImage(String name)
    {
        return setImage(imgLoader.getImage(name));
    }

    /**
     * Cambia la images del Sprite por la que indica
     * el 'ndice pasado como argumento.
     */
    public boolean setImage(int index)
    {
        return setImage(imgNames[index]);
    }

    /**
     * Carga un conjunto de im'genes numeradas.
     * 
     * @param name El nombre de la im'gen que debe de
     * contener el asterisco '*'.
     * @param n1 Representa en inicio del contador. N'mero
     * incluido en la carga
     * @param n2 El n'mero de im'genes que hay que cargar
     * a partir del 'ndice n1. El n'mero n1+n2 tmb est'
     * incluido en la lista de nombres de im'genes.
     */
    public boolean setImages(String name, int n1, int n2)
    {
        int wildcard = name.indexOf("*");
        if (wildcard != -1 && n2 > 1) {
            imgNames = new String[n2];
            // Contador
            int i = 0;
            // stores the enumerated different names
            String fullName;
            while (i<n2) {
                fullName =
                    name.substring(0,wildcard)+(n1+i)
                    +name.substring(wildcard+1);
                imgNames[i++] = fullName;
            }
            setImages(imgNames);
            return true;
        } else {
            return setImage(name);
        }
    }

    /**
     * Cambia la imagen que representa al Sprite.
     * Devuelve si es cargada con 'xito.
     * El Sprite debe de encontrarse activo.
     */
    public boolean setImage(BufferedImage img)
    {
        this.img = img;
        if (img == null || !active) {
            return false;
        }
        if (!preferredSize) {
            width = img.getWidth();
            height = img.getHeight();
        }
        return true;
    }

    /**
     * Cambia el tama'o de la imagen al especificado por
     * el programador y evita que se cambie el tama'o
     * cada vez que se seleccione otra imagen distinta.
     * Para cambiar a los valores predeterminados de la
     * imagen basta con introducir valores negativos.
     */
    public void setPreferredSize(int w, int h)
    {
        preferredSize = !(w <= 0 && h <= 0);
        if (preferredSize) {
            width = w;
            height = h;
        } else {
            width = img.getWidth();
            height = img.getHeight();
        }
    }

    /**
     * Pinta la imagen en las coordenadas x e y,
     * en caso de que sea visible, y sin ning'n efecto.
     */
    public void paint(Graphics g)
    {
        if (visible) {
            g.drawImage(img, (int)x, (int)y, -width, height, null);
        }
    }

    /**
     * Pinta la imagen en las coordenadas indicadas,
     * en caso de que sea visible, y sin ning'n efecto.
     */
    public void paint(Graphics g, double x, double y)
    {
        if (visible) {
            g.drawImage(img, (int)x, (int)y, -width, height, null);
        }
    }

    /**
     * Pinta la imagen en las coordenadas indicadas y
     * con las dimensiones especificadas, siempre
     * en caso de que sea visible.
     */
    public void paint(Graphics g, double x, double y, int w, int h)
    {
        if (visible) {
            g.drawImage(img, (int)x, (int)y, w, h, null);
            if (drawBounds) {
                g.setColor(Color.RED);
                for (int i=0; i<bounds.size(); i++) {
                    Rectangle r = bounds.get(i);
                    g.drawRect((int)(x+r.getX()), (int)(y+r.getY()),
                        -(int)(r.getWidth()), (int)(r.getHeight()));
                }
            }
        }
    }

    /**
     * Debe implementarse este método para definir el comportamiento
     * del objeto.
     */
    public void act()
    {
    }

    // SET methods --------------------------------------------------
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public void setImageLoader(ImageLoader imgLoader) {
        if (imgLoader != null) {
            this.imgLoader = imgLoader;
        }
    }
    //  end of SET methods ------------------------------------------


    // GET methods --------------------------------------------------
    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    public BufferedImage getImage()
    {
        return img;
    }
    
    public ArrayList<Rectangle> getBounds()
    {
        return bounds;
    }
    //  end of GET methods ------------------------------------------
    
    // BOOLEAN methods ----------------------------------------------
    public boolean isVisible()
    {
        return visible;
    }
    //  end of BOOLEAN methods --------------------------------------
}
