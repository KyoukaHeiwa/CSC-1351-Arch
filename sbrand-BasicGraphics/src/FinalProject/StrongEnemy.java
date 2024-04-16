package FinalProject;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;

public class StrongEnemy extends Enemy {

    int hitpoints;
    static int SenemyCount;
    private int speed;
    private int strength;
    private long lastShootTime = 0;
    private int shootingInterval = 2000;

    @Override
    public void setActive(boolean b) {
        if (isActive() == b)
            return;
        if (b)
            SenemyCount++;
        else
            SenemyCount--;
        super.setActive(b);
    }

    public StrongEnemy(SpriteComponent sc) {
        super(sc);
        SenemyCount++;
        hitpoints = 10;
        setSize(75);
        setPicture(makeBall(Color.ORANGE, 75));
        while (true) {
            setX(Game.RAND.nextInt(Game.BOARD_SIZE.width) - Game.SMALL);
            setY(Game.RAND.nextInt(Game.BOARD_SIZE.height) - Game.SMALL);
            if (Math.abs(getX() - Game.BOARD_SIZE.width / 2) < 2 * Game.BIG
                    && Math.abs(getY() - Game.BOARD_SIZE.height / 2) < 2 * Game.BIG) {
            } else {
                break;
            }
        }
        setVel(15 * Game.RAND.nextDouble() - 1, 15 * Game.RAND.nextDouble());
    }



    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;

        setPicture(makeBall(Color.ORANGE, size));
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        boolean collidedWithBoundary = false;
        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            if (se.xlo) {
                setX(sc.getSize().width - getWidth());
                collidedWithBoundary = true;
            }
            if (se.xhi) {
                setX(0);
                collidedWithBoundary = true;
            }
            if (se.ylo) {
                setY(sc.getSize().height - getHeight());
                collidedWithBoundary = true;
            }
            if (se.yhi) {
                setY(0);
                collidedWithBoundary = true;
            }
            if (collidedWithBoundary){
                hitpoints += 2;
                int newSize = Math.min(getSize() + 15, 200);
                setPicture(makeBall(Color.BLACK, newSize));
                setX(Math.min(getX(), sc.getSize().width - newSize));
                setY(Math.min(getY(), sc.getSize().height - newSize));
                setSize(newSize);
            }
        }

    }
    private Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }
    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints <= 0) {
            setActive(false);
        }
    }

    @Override
    public void draw(Graphics g) {

    }

}
