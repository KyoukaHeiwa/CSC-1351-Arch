package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;

import basicgraphics.BasicFrame;
import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

import java.awt.*;

public class PinwheelEnemy extends Enemy {

    public PinwheelEnemy(SpriteComponent sc, Picture sprite) {
        super(sc, sprite);
        setPicture(sprite);
        this.shouldFollow = false;
        //setVelocity();
    }

    public static PinwheelEnemy createPinwheelEnemy(SpriteComponent sc) {
        Picture pinwheelSprite = createPinwheelSprite(30);
        PinwheelEnemy pEnemy = null;
        boolean positionValid = false;
        int attempts = 0;
    
        
        Random localRand = new Random();
    
        while (!positionValid && attempts < 100) {
            int x = localRand.nextInt(myGame.BOARD_SIZE.width - myGame.SMALL);
            int y = localRand.nextInt(myGame.BOARD_SIZE.height - myGame.SMALL);
            double distance = Math.hypot(x - myGame.shooter.getX(), y - myGame.shooter.getY());
    
            if (distance >= myGame.MIN_DISTANCE_FROM_SHOOTER) {
                if (pEnemy == null) {
                    pEnemy = new PinwheelEnemy(sc, pinwheelSprite);
                }
                pEnemy.setX(x);
                pEnemy.setY(y);
                pEnemy.setVelocity();
                positionValid = true;
            }
    
            attempts++;
        }
    
        if (!positionValid) {
            if (pEnemy == null) {
                pEnemy = new PinwheelEnemy(sc, pinwheelSprite);
            }
            pEnemy.setX(myGame.BOARD_SIZE.width / 2);
            pEnemy.setY(myGame.BOARD_SIZE.height / 2);
            pEnemy.setVelocity();
        }
    
        return pEnemy;
    }
    
    private void setVelocity() {
        Random localRand = new Random();
    
        
        double speedFactor = 0.8; 
        double vx = speedFactor * (localRand.nextDouble() * 2 - 1); 
        double vy = speedFactor * (localRand.nextDouble() * 2 - 1); 
    
        setVel(vx, vy);
    }

    public static Picture createPinwheelSprite(int size) {
        BufferedImage image = BasicFrame.createImage(size, size);
        Graphics2D g = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();
    
        int centerX = w / 2;
        int centerY = h / 2;
        int length = w / 2;
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(210, 145, 255));
        g.setStroke(new BasicStroke(2));
    
        
        int[] endX = new int[8];
        int[] endY = new int[8];
    
        for (int i = 0; i < 8; i++) {  
            double angle = i * (Math.PI / 4); 
            endX[i] = centerX + (int) (length * Math.cos(angle));
            endY[i] = centerY + (int) (length * Math.sin(angle));
            g.drawLine(centerX, centerY, endX[i], endY[i]);
        }
    
        
        for (int i = 0; i < 8; i++) {
            
            int nextIndex = (i + 1) % 8;
            if (i % 2 == 1) {  
                g.drawLine(endX[i], endY[i], endX[nextIndex], endY[nextIndex]);
            }
        }
    
        return new Picture(image);
    }
    
    public void update() {

        //top boundary
        if (getX() < 0 || getX() + getWidth() > myGame.BOARD_SIZE.width) {
            setVel(-getVelX(), getVelY()); // Reverse x velocity
        }

        // bottom boundary
        if (getY() < 0 || getY() + getHeight() > myGame.BOARD_SIZE.height) {
            setVel(getVelX(),-getVelY()); // Reverse y velocity
        }
    }

    @Override
    public void processEvent(SpriteCollisionEvent ev) {
        SpriteComponent sc = getSpriteComponent();
        if (ev.xlo){
            // setX(sc.getSize().width-getWidth());
            // //shooterWallCollide.playOverlapping();
            // setVel(vx, vy);
            update();
            
        }
        if (ev.xhi) {
            // setX(0);
            // //shooterWallCollide.playOverlapping();
            // setVel(-vx, vy);
            update();

        }
        if (ev.ylo) {
            // setY(sc.getSize().height - getHeight());
            // //shooterWallCollide.playOverlapping();
            // setVel(vx, vy);
            update();

        }
        if (ev.yhi) {
            // setY(0);
            // //shooterWallCollide.playOverlapping();
            // setVel(vx, -vy);
            update();
        }
    }
}