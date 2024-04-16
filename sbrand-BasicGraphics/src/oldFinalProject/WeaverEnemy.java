package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import basicgraphics.BasicFrame;
import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;
import java.awt.geom.AffineTransform;

import java.awt.*;

public class WeaverEnemy extends Enemy {
    private static final int DODGE_DISTANCE = 50; // The distance at which the enemy starts to dodge bullets

    public WeaverEnemy(SpriteComponent sc, Picture sprite) {
        super(sc, sprite);
        setPicture(sprite);
    }

    
    public static Picture createWeaverSprite(int size) {
        BufferedImage image = BasicFrame.createImage(30, 30);
        Graphics2D g2 = (Graphics2D) image.getGraphics();


        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, size, size);

        g2.setColor(Color.GREEN);
        g2.fillRect(0, 0, size, size);

        int halfSize = 30 / 2;
        int quarterSize = 30 / 4;
        int threeQuarterSize = 3 * 30 / 4;

        g2.setColor(Color.BLACK);
        int[] xPoints = {halfSize, quarterSize, halfSize, threeQuarterSize};
        int[] yPoints = {quarterSize, halfSize, threeQuarterSize, halfSize};
        g2.fillPolygon(xPoints, yPoints, 4);

        return new Picture(image);
    }
    public static WeaverEnemy createWeaverEnemy(SpriteComponent sc) {
        Picture weaverSprite = createWeaverSprite(30);
        WeaverEnemy wEnemy;
        do {
            wEnemy = new WeaverEnemy(sc, weaverSprite);
        } while (Math.hypot(wEnemy.getX() - myGame.shooter.getX(), wEnemy.getY() - myGame.shooter.getY()) < myGame.MIN_DISTANCE_FROM_SHOOTER);
        return wEnemy;
    }
    @Override
    public void move(Dimension d) {
        super.move(d);
        chaseShooter();
        dodgeBullets();
    }
    private void chaseShooter() {
        Shooter shooter = myGame.getShooter();
        double sX = shooter.getCenterX();
        double sY = shooter.getCenterY();

        double angle = Math.atan2(sY - this.getCenterY(), sX - this.getCenterX());
        double enemySpeed = .5; //This is the speed of the enemy

        this.setVel(enemySpeed * Math.cos(angle), enemySpeed * Math.sin(angle));
    }

    /**
     * This method makes the enemy dodge bullets by detecting if the bullet is within a certain distance.
     */
    private void dodgeBullets() {
        List<Bullet> bullets = new ArrayList<>(myGame.getBullets());
        for (Bullet bullet : bullets) {
            if (bullet != null) {
                double distance = Math.hypot(this.getX() - bullet.getX(), this.getY() - bullet.getY());
                if (distance < DODGE_DISTANCE) {
                    double dodgeX = this.getX() - bullet.getX();
                    double dodgeY = this.getY() - bullet.getY();
                    this.setX(this.getX() + dodgeX / distance);
                    this.setY(this.getY() + dodgeY / distance);   
                }
            }
        }
    }
}

