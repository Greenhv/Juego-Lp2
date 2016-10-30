package lp2.juegolp2.Mundo;

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
        return "(" + X + ", " + Y + ")";
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Position)) {
            return false;
        }
        return this.equals((Position) obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.X;
        hash = 11 * hash + this.Y;
        return hash;
    }
    
    public boolean equals(Position pos)
    {
        return (this.X == pos.X)
               &&
               (this.Y == pos.Y);
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
                this.Y--;
                break;
            case RIGHT:
                this.X++;
                break;
            case DOWN:
                this.Y++;
                break;
            case LEFT:
                this.X--;
                break;
        }
        return this.copy();
    }
    
    public Position copy()
    {
        return new Position(getX(), getY());
    }
    
    public double distanceTo(Position pos)
    {
        return Math.sqrt(Math.pow(this.X-pos.X, 2) + Math.pow(this.Y-pos.Y, 2));
    }
}
