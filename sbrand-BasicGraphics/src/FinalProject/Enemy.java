package FinalProject;

import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;

import java.awt.*;

public class Enemy extends Sprite {
    static int enemyCount;
    private int speed;
    private int strength;
    private int duplicationLimit = 2;

    @Override
    public void setActive(boolean b) {
        if (isActive() == b)
            return;
        if (b)
            enemyCount++;
        else
            enemyCount--;
        super.setActive(b);
    }

    public Enemy(SpriteComponent sc) {
        super(sc);
        enemyCount++;
        setPicture(Game.makeBall(Game.ENEMY_COLOR, Game.BIG));
        while (true) {
            setX(Game.RAND.nextInt(Game.BOARD_SIZE.width) - Game.SMALL);
            setY(Game.RAND.nextInt(Game.BOARD_SIZE.height) - Game.SMALL);
            if (Math.abs(getX() - Game.BOARD_SIZE.width / 2) < 2 * Game.BIG
                    && Math.abs(getY() - Game.BOARD_SIZE.height / 2) < 2 * Game.BIG) {

            } else {
                break;
            }
        }
        // A random speed
        setVel(6 * Game.RAND.nextDouble() - 1, 6 * Game.RAND.nextDouble());
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

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();

        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            if (se.xlo) {
                setX(sc.getSize().width - getWidth());
            }
            if (se.xhi) {
                setX(0);
            }
            if (se.ylo) {
                setY(sc.getSize().height - getHeight());
            }
            if (se.yhi) {
                setY(0);
            }
        if(duplicationLimit > 0){
            duplicateEnemy();
        }
        }

    }

    private void duplicateEnemy() {
        // Decrement the duplication limit
        duplicationLimit--;

        // Create a duplicate of this enemy
        Enemy duplicate = new Enemy(getSpriteComponent());
        duplicate.duplicationLimit = this.duplicationLimit; // Copy the duplication limit
        getSpriteComponent().addSprite(duplicate);
    }

    //@Override
    public void draw(Graphics g) {

    }
}
