/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author sbrandt
 */
class Bullet extends Sprite {
    Bullet(SpriteComponent sc, Sprite sp, int direction) {
        super(sc);
        double angle = 0; 
        if (direction == KeyEvent.VK_DOWN)
            angle = Math.PI / 2;
        else if (direction == KeyEvent.VK_UP)
            angle = -Math.PI / 2;
        else if (direction == KeyEvent.VK_LEFT)
            angle = Math.PI; 
        
        setPicture(makeTriangleBullet(angle));
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());
        double velocity = 4.0 * myGame.bulletSpeedMultiplier;
        setVel(velocity * Math.cos(angle), velocity * Math.sin(angle));
    }

    Bullet(SpriteComponent sc, Shooter sp, int x, int y) {
        super(sc);
        double delx = x - sp.getX() - sp.getWidth() / 2;
        double dely = y - sp.getY() - sp.getHeight() / 2;
        double angle = Math.atan2(dely, delx);
        setPicture(makeTriangleBullet(angle));
        setX(sp.getX() + (myGame.BIG - myGame.SMALL) / 2);
        setY(sp.getY() + (myGame.BIG - myGame.SMALL) / 2);
        double dist = Math.sqrt(delx * delx + dely * dely);
        setVel(5 * delx / dist * myGame.bulletSpeedMultiplier, 5 * dely / dist * myGame.bulletSpeedMultiplier);
    }
    
    public boolean isOutOfGameArea() {
        return this.getX() < 0 || this.getX() > myGame.BOARD_SIZE.getWidth() || this.getY() < 0 || this.getY() > myGame.BOARD_SIZE.getHeight();
    }
    private Picture makeTriangleBullet(double angle) {
        int size = 20; 
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
    
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        int[] xPoints = {size / 2, 5, size - 5};
        int[] yPoints = {5, size - 5, size - 5};
    
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, size / 2, size / 2);
        g.setTransform(transform);
    
        g.setColor(Color.YELLOW);
        g.fillPolygon(xPoints, yPoints, 3);
    
        int shrink = 3; 
        int[] xInnerPoints = {size / 2, 5 + shrink, size - 5 - shrink};
        int[] yInnerPoints = {5 + shrink, size - 5, size - 5};
        g.setColor(Color.BLACK);
        g.fillPolygon(xInnerPoints, yInnerPoints, 3);
    
        return new Picture(image);
    }
}