/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

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
}
