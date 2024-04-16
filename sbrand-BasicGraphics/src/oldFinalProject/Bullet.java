/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oldFinalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import java.awt.event.KeyEvent;

/**
 *
 * @author sbrandt
 */
class Bullet extends Sprite {
    Bullet(SpriteComponent sc,Sprite sp,int direction) {
        super(sc);
        setPicture(myGame.makeBall(myGame.BULLET_COLOR, myGame.SMALL));
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());
        if(direction == KeyEvent.VK_DOWN)
            setVel(0, 4.0 * myGame.bulletSpeedMultiplier);
        else if(direction == KeyEvent.VK_UP)
            setVel(0, -4.0 * myGame.bulletSpeedMultiplier);
        else if(direction == KeyEvent.VK_RIGHT)
            setVel(4.0 * myGame.bulletSpeedMultiplier, 0);
        else if(direction == KeyEvent.VK_LEFT)
            setVel(4.0 * myGame.bulletSpeedMultiplier, 0);
    }

    Bullet(SpriteComponent sc, Shooter sp, int x, int y) {
        super(sc);
        setPicture(myGame.makeBall(myGame.BULLET_COLOR, myGame.SMALL));
        setX(sp.getX()+(myGame.BIG-myGame.SMALL)/2);
        setY(sp.getY()+(myGame.BIG-myGame.SMALL)/2);
        double delx = x-sp.getX()-sp.getWidth()/2;
        double dely = y-sp.getY()-sp.getHeight()/2;
        double dist = Math.sqrt(delx*delx+dely*dely);
        setVel(4*delx/dist * myGame.bulletSpeedMultiplier, 4*dely/dist * myGame.bulletSpeedMultiplier);
    }
    public boolean isOutOfGameArea() {
        // Replace GAME_AREA_WIDTH and GAME_AREA_HEIGHT with the actual dimensions of your game area
        return this.getX() < 0 || this.getX() > myGame.BOARD_SIZE.getWidth() || this.getY() < 0 || this.getY() > myGame.BOARD_SIZE.getHeight();
    }
    
}
