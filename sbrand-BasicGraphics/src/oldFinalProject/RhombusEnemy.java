package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import basicgraphics.BasicFrame;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.*;

public class RhombusEnemy extends Enemy {
    public RhombusEnemy(SpriteComponent sc, Picture sprite) {
        super(sc, sprite);
        setPicture(sprite);
    }

    public static Picture createRhombusSprite(int size) {
        BufferedImage image = BasicFrame.createImage(30,30);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();
        int[] x = {w / 2, w, w / 2, 0};
        int[] y = {0, h / 2, h, h / 2};
        g2.setColor(Color.CYAN);
        g2.fillPolygon(x, y, 4);
        
        int inside = size / 8;
        int[] innerX = {w /2 , w - inside, w / 2, inside};
        int[] innerY = {inside, h / 2, h - inside, h / 2};
        g2.setColor(Color.WHITE);
        g2.fillPolygon(innerX, innerY, 4);
        
        return new Picture(image);
    }

    public static RhombusEnemy createRhombusEnemy(SpriteComponent sc) {
        Picture rhombusSprite = createRhombusSprite(30);
        RhombusEnemy rEnemy;
        do {
            rEnemy = new RhombusEnemy(sc, rhombusSprite);
        } while (Math.hypot(rEnemy.getX() - myGame.shooter.getX(), rEnemy.getY() - myGame.shooter.getY()) < myGame.MIN_DISTANCE_FROM_SHOOTER);
        return rEnemy;
    }

    @Override
    public void move(Dimension d) {
        // TODO Auto-generated method stub
        super.move(d);
        chaseShooter();
        
    }
    private void chaseShooter() {
        Shooter shooter = myGame.getShooter(); 
        double sX = shooter.getCenterX();
        double sY = shooter.getCenterY();

        double angle = Math.atan2(sY - this.getCenterY(), sX - this.getCenterX());
        double enemySpeed = .5; //Enemey's speed
        
        this.setVel(enemySpeed * Math.cos(angle), enemySpeed * Math.sin(angle));
    }
}