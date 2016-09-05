package com.mycompany.juegolp2;

/**
 * Created by pmvb on 01/09/16.
 */
public enum Direction
{
    UP {
        @Override
        public void move(Position pos)
        {
            pos.setY(pos.getY() - 1);
        }
    },
    RIGHT {
        @Override
        public void move(Position pos)
        {
            pos.setX(pos.getX() + 1);
        }
    },
    DOWN {
        @Override
        public void move(Position pos) {
            pos.setY(pos.getY() + 1);
        }
    },
    LEFT {
        @Override
        public void move(Position pos) {
            pos.setX(pos.getX() - 1);
        }
    };

    public abstract void move(Position pos);
}