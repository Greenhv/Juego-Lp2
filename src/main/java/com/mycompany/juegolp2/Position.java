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
    
    public void move(Direction dir)
    {
        switch (dir) {
            case UP:
                this.setY(this.getY() - 1);
                break;
            case RIGHT:
                this.setX(this.getX() + 1);
                break;
            case DOWN:
                this.setY(this.getY() + 1);
                break;
            case LEFT:
                this.setX(this.getX() - 1);
                break;
        }
    }
}
