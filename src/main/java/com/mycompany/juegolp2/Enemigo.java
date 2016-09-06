/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juegolp2;

/**
 *
 * @author pmvb
 */
public class Enemigo extends Entidad
{
    private int nivel_enemigo;
    
    public Enemigo(String nombre)
    {
        super(nombre);
        nivel_enemigo = 1;
    }
}
