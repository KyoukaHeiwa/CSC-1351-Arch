
package FinalProject;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;

import java.awt.*;
public class Bullet extends Sprite {
    private int damage;
    private int bulletSize;

    public Bullet(SpriteComponent sc, Shooter sp, double x, double y) {
        super(sc);
        this.bulletSize = bulletSize;
        setPicture(Game.makeBall(Game.BULLET_COLOR, Game.SMALL));


        setX(sp.getX() + (sp.getWidth() - getWidth()) / 2);
        setY(sp.getY() + (sp.getHeight() - getHeight()) / 2);

        double delX = x - getX();
        double delY = y - getY();
        double distance = Math.sqrt(delX * delX + delY * delY);

        setVelocity(10 * delX / distance, 10 * delY / distance);

        damage = 1;
    }

    private void setVelocity(double velX, double velY) {
        setVel(velX, velY);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    //@Override
    public void draw(Graphics g) {

    }
}
