/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

/**
 *
 * @author sbrandt
 */
public class Enemy extends Sprite {
    static int enemyCount;
     boolean shouldFollow = false;
    
    @Override
    public void setActive(boolean b) {
        if(isActive() == b)
            return;
        if(b)
            enemyCount++;
        else
            enemyCount--;
        super.setActive(b);
    }

    public Enemy(SpriteComponent sc, Picture sprite) {
        super(sc);
        enemyCount++;
        setPicture(sprite);;
        initPosition();
        setVelocity();
    }
    private void setVelocity() {
        setVel(1 * myGame.RAND.nextDouble() - 1, 1 * myGame.RAND.nextDouble());
    }
    private void initPosition() {
        Shooter shooter = myGame.getShooter();
        double shooterX = shooter.getX();
        double shooterY = shooter.getY();
        double distance;
        do{
        setX(myGame.RAND.nextInt(myGame.BOARD_SIZE.width) - myGame.SMALL);
        setY(myGame.RAND.nextInt(myGame.BOARD_SIZE.height) - myGame.SMALL);
        distance = Math.hypot(getX() - shooterX, getY() - shooterY);
        }
        while(distance < myGame.MIN_DISTANCE_FROM_SHOOTER);
    }
    final static ReusableClip clip = new ReusableClip("die.wav");

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if(se.eventType == CollisionEventType.WALL_INVISIBLE) {
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
        }

//        if (se.eventType == CollisionEventType.SPRITE) {
//        }
    
    }

    public void followShooter() {
        Shooter shooter = myGame.getShooter();
        double enemyX = getX();
        double enemyY = getY();
        double shooterX = shooter.getX();
        double shooterY = shooter.getY();

        double directionX = shooterX - enemyX;
        double directionY = shooterY - enemyY;
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
        
        if (magnitude > 0) {
            directionX /= magnitude;
            directionY /= magnitude;
        }

        setVel(directionX * getSpeed(), directionY * getSpeed());

        super.move(myGame.BOARD_SIZE);
    }

}
