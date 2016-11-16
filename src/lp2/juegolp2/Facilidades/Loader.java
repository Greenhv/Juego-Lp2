package lp2.juegolp2.Facilidades;

import java.util.*;
import java.io.*;

/**
 *
 * @author pmvb
 */
public abstract class Loader
{
    /**
     * Path to the package directory.
     */
    private String packageDirectory;
    
    /**
     * Path where the resources are
     * relative to package directory.
     */
    private String relativePath;

    /**
     * File which contains the files to load.
     */
    private File loader;

    /**
     * Stores all the Objects loaded.
     */
    protected HashMap<String, Object> loaded;

    /**
     * Loader with the path of the package directory
     * and with those directories' <code>loader</code>s.
     */
    public Loader()
    {
        this(".", "");
    }

    /**
     * Loader with the indicated path and a
     * directory loader.
     *
     * @param path Directory with the assets.
     */
    public Loader(String path)
    {
        this(path, "");
    }

    /**
     * Loader with the indicated path and the
     * indicated directory.
     *
     * @param path Directory with the assets.
     * @param loader File with the images to load.
     */
    public Loader(String path, String loader)
    {
            // Obtenemos el directorio de una forma enrevesada
            // para poder utilizar el cargador en Apples.
            packageDirectory = getClass().getClassLoader().getResource("").getPath();
            packageDirectory = packageDirectory.replaceAll("%20", " ");
            packageDirectory = packageDirectory.substring(0, packageDirectory.lastIndexOf("build/"));
            setPath(path);
            setLoader(loader);
            
            System.out.println("Package Directory " + packageDirectory);
            System.out.println("Relative Path: " + relativePath);
            System.out.println("Loader: " + loader + " exists-> " + exists());
            System.exit(0);
    }

    /**
     * Set the path of the files directories or the loader file.<br>
     * The <code>path</code> is relative to the main directory of the package.<br>
     * The default path is <code>"."</code> (directory of the package).<br>
     * Don't use <code>".."</code>
     * 
     * @param path Path to set
     */
    public void setPath(String path)
    {
        try {
            path = path.trim();
            StringBuilder s = new StringBuilder(path);
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i) == '\\') {
                    s.replace(i, i+1, "/");
                }
            }
            path = s.toString();
            if (path.charAt(path.length()-1) != '/') {
                path += "/";
            }
            this.relativePath = path;
        } catch (Exception e) {
            System.err.println("Error by setting the resource directory.");
            e.printStackTrace();
        }
    }

    /**
     * A loader is a comfortable way to upload files. A
     * loader can be a File (if <code>name</code> is a
     * valid file name) or directory of the <code>path</code>
     * (if <code>name</code> equals "").
     *
     * @param name Filename or directory name
     * @return Whether the file exists
     */
    public boolean setLoader(String name)
    {
        if (name == null) 
            return false;
        loader = getFile(name);
        if (loader == null)
            return false;
        return loader.exists();
    }

    /**
     * Load all the files of the loader.
     *
     * @return Whether the load was successful
     */
    public boolean startLoader()
    {
        if (loader != null && loader.exists()) {
            if (loader.isDirectory()) {
                return loadDirectory(loader);
            } else if (loader.isFile()) {
                if (loader.canRead()) {
                    return readLoaderFile();
                }
            }
        }
        return false;
    }

    private boolean readLoaderFile()
    {
        BufferedReader br;
        try {
            InputStream is = new FileInputStream(loader);
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            System.out.println("-- Reading loader --");
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // if is an empty line or a commentary
                if (line.length() == 0 ||
                    line.startsWith("//")) {
                        continue;
                } // else... read the line and load
                loadLine(line);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file loader: " + loader.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Read line with configuration information
     *
     * @param line Information to load an image/s.
     */
    public void loadLine(String line)
    {
        if (line.startsWith("1 ")) {
            loadSingleFile(line);
        } else if (line.startsWith("2 ")) {
            loadNumeratedFiles(line);
        }
    }

    private void loadSingleFile(String line)
    {
        int equals = line.indexOf("=");
        if (equals == -1) {
            load(line.substring(2));
        } else {
            load(line.substring(equals+1),
                line.substring(2, equals));
        }
    }

    private void loadNumeratedFiles(String line)
    {
        boolean error = false;
        StringTokenizer st = new StringTokenizer(line, " ");
        int tokens = st.countTokens();
        st.nextToken();
        
        String fileName = st.nextToken();
        String loadedName = null;
        int equals = fileName.indexOf("=");
        if (equals != -1) {
            loadedName = fileName.substring(0, equals);
            fileName = fileName.substring(equals+1);
        }
        int wildcard = fileName.indexOf("*");
        if (wildcard != -1) {
            int i=0;
            // stores the enumerated different names
            String fullName;
            // for different number of argumenst
            if (tokens == 2) {
                do {
                    fullName = 
                        fileName.substring(0,wildcard)+(i++)
                        +fileName.substring(wildcard+1);
                } while (load(fullName,loadedName+(i-1)));
            } else if (tokens >= 3) {
                int numFiles = 0;
                try {
                    numFiles = Integer.parseInt(st.nextToken());
                    if (tokens == 4) {
                        i = Integer.parseInt(st.nextToken());
                        numFiles += i;
                    }
                } catch (NumberFormatException e) {
                    error = true;
                }
                while (i < numFiles) {
                    fullName = fileName.substring(0,wildcard)+(i++)
                                +fileName.substring(wildcard+1);
                    load(fullName, loadedName);
                }
            }
        } else {
            error = true;
        }
        if (error) {
            System.err.println("Error format in line: "+line);
        }
    }

    /**
     * Load all files from a directory
     *
     * @param d Directory from which to load files
     * be loaded.
     */
    public boolean loadDirectory(File d)
    {
        if (!d.isDirectory())
            return false;
        File[] f = d.listFiles();
        for (File file : f) {
            if(file.isFile()) {
                load(file);
            }
        }
        return true;
    }

    public boolean load(File f)
    {
        return load(f, f.getName(), false);
    }

    /**
     * You must implement this method if you want to load
     * any Object. Don't forget add the object to the 
     * <code>loaded:ArrayList</code>.
     * @return Whether the object was loaded successfully
     */
    public abstract boolean load(File f, String name, boolean rewrite);

    public boolean load(String n)
    {
        return load(n, n);
    }

    public boolean load(String fileName, String name)
    {
        File f = new File(getPath()+fileName);
        if (f.exists()) {
            if (f.isDirectory()) {
                loadDirectory(f);
            } else if (f.isFile()) {
                return load(f, name, false);
            }
        } else {
            System.err.println("Not found: " + f.getPath());
        }
        return false;
    }

    /**
     * Returns an Object.
     */
    public Object getObject(String name, boolean load, boolean rewrite)
    {
        Object aux = loaded.get(name);
        if ((load && aux == null) || rewrite) {
            File f = new File(getPath() + name);
            if (f.exists() && f.isFile()) {
                load(f, name, rewrite);
            }
        }
        return loaded.get(name);
    }

    /**
     * Remove a previously loaded image.
     */
    public void removeObject(String name)
    {
        loaded.remove(name);
    }

    /**
     * Remove all loaded images.
     */
    public void removeAllObjects() 
    {
        loaded.clear();
    }

    public File getFile(String name) 
    {
        try {
            File file = new File(packageDirectory + relativePath + name);
            return file;
        } catch (Exception e) {
            System.err.println("Error loading file: " + name);
        }
        return null;
    }

    public String getRelativePath()
    {
        return relativePath;
    }

    public String getPath()
    {
        return packageDirectory + relativePath;
    }

    public String getPackagePath()
    {
        return packageDirectory;
    }

    public File getLoader()
    {
        return loader;
    }

    public void changeName(String name, String newName)
    {
        loaded.put(newName, loaded.get(name));        
        removeObject(name);
    }

    public void put(String name, Object object)
    {
        loaded.put(name, object);
    }

    /**
     * Checks if the loader file or directory exists.
     *
     * @return Whether the loader exists
     */
    public boolean exists()
    {
        return (loader != null) ? loader.exists() : false;
    }
}
