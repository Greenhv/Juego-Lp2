package lp2.juegolp2;

import java.util.ArrayList;

/**
 *
 * @author pmvb
 */
public class Saco
{
    private ArrayList<Artefacto> saco;

    public Saco()
    {
        this.saco = new ArrayList<>();
    }

    public Artefacto getItem(int index)
    {
        return this.saco.get(index);
    }

    public void addItem(Artefacto item)
    {
        this.saco.add(item);
    }

    public void removeItem(int index)
    {
        this.saco.remove(index);
    }
    
    public int size()
    {
        return this.saco.size();
    }
    
    public boolean empty()
    {
        return this.size() == 0;
    }
    
    public String toString()
    {
        String str = "";
        for (int i = 0; i < saco.size(); ++i)
            str += Integer.toString(i) + ". " + saco.get(i);
        str += "\n";
        return str;
    }
}
