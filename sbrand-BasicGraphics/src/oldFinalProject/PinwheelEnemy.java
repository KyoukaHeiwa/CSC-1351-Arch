package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

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
        setVelocity();
    }

    public static PinwheelEnemy createPinwheelEnemy(SpriteComponent sc) {
        Picture pinwheelSprite = createPinwheelSprite(40);
        PinwheelEnemy pEnemey;
        do {
            pEnemey = new PinwheelEnemy(sc, pinwheelSprite);
        } while (Math.hypot(pEnemey.getX() - myGame.shooter.getX(), pEnemey.getY() - myGame.shooter.getY()) < myGame.MIN_DISTANCE_FROM_SHOOTER);
        return pEnemey;
    }
    
    private void setVelocity() {
        setVel(.8 * myGame.RAND.nextDouble(), .8* myGame.RAND.nextDouble());
    }
    public static Picture createPinwheelSprite(int size) {
        BufferedImage image = BasicFrame.createImage(30, 30);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();

        int centerX = w / 2;
        int centerY = h / 2;
        int length = w / 2;
        
        g2.setColor(new Color(210, 145, 255));
        g2.setStroke(new BasicStroke(3));
        
        for (int i = 0; i < 4; i++) {  
            double angle = Math.PI / 4 + i * (Math.PI / 2); 
            int endX = centerX + (int) (length * Math.cos(angle));
            int endY = centerY + (int) (length * Math.sin(angle));
            g2.drawLine(centerX, centerY, endX, endY);
        }
        
        return new Picture(image);
    }
}