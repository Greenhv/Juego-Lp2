package com.mycompany.juegolp2;

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
}
