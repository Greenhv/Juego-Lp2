package com.mycompany.juegolp2;

import java.util.HashMap;

public class Position
{
    private int X;
    private int Y;

    public Position(int x, int y)
    {
        this.setX(x);
        this.setY(y);
    }

    public Position(Position pos)
    {
        this.setX(pos.getX());
        this.setY(pos.getY());
    }
            
    public void print()
    {
        System.out.println(this.toString());
    }

    public String toString()
    {
        return "(" + getX() + ", " + getY() + ")";
    }

    public boolean equals(Position pos)
    {
        return this.getX() == pos.getX()
               &&
               this.getY() == pos.getY();
    }

    public int getX()
    {
        return X;
    }

    public void setX(int x)
    {
        this.X = x;
    }

    public int getY()
    {
        return Y;
    }

    public void setY(int y)
    {
        this.Y = y;
    }
    
    public Position move(Direction dir)
    {
        switch (dir) {
            case UP:
                this.setX(this.getX() - 1);
                break;
            case RIGHT:
                this.setY(this.getY() + 1);
                break;
            case DOWN:
                this.setX(this.getX() + 1);
                break;
            case LEFT:
                this.setY(this.getY() - 1);
                break;
        }
        return this.copy();
    }
    
    public Position copy()
    {
        return new Position(getX(), getY());
    }
}
