package FinalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

import java.awt.*;
import java.awt.event.KeyEvent;
public class Shooter extends Sprite {

    private double bulletSpeedMultiplier = 1.0;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean hasPowerUp = false;
    private Color shooterColor = Game.SHOOTER_COLOR; // Default color

    public Shooter(SpriteComponent sc) {
        super(sc);
        setPicture(Game.makeBall(shooterColor, Game.BIG));
        setX(Game.BOARD_SIZE.width / 2);
        setY(Game.BOARD_SIZE.height / 2);
    }

    public void activatePowerUp() {
        hasPowerUp = true;
        changeColor();
    }
    public void deactivatePowerUp() {
        hasPowerUp = false;
        changeColor();
    }

    private void changeColor() {

        if (hasPowerUp) {
            shooterColor = Color.BLACK; // Change to green when power-up is activated
        } else {
            shooterColor = Game.SHOOTER_COLOR; // Change back to default color
        }
        setPicture(Game.makeBall(shooterColor, Game.BIG)); // Update the picture with the new color
    }

    //@Override
    public void draw(Graphics g) {

    }
    public boolean isPowerUpActive() {
        return hasPowerUp;
    }

    public int getPowerUpBulletSize() {

        return Game.BIG;
    }

    public double getPowerUpBulletSpeedMultiplier() {

        return 1.5;
    }

    public void move(int deltaX, int deltaY) {
        // Calculate new position
        int newX = (int) (getX() + deltaX);
        int newY = (int) (getY() + deltaY);

        newX = (int) Math.max(0, Math.min(getSpriteComponent().getSize().width - getWidth(), newX));
        newY = (int) Math.max(0, Math.min(getSpriteComponent().getSize().height - getHeight(), newY));

        setX(newX);
        setY(newY);
    }

    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_A:
                movingLeft = true;
                break;
            case KeyEvent.VK_D:
                movingRight = true;
                break;
            case KeyEvent.VK_W:
                movingUp = true;
                break;
            case KeyEvent.VK_S:
                movingDown = true;
                break;
        }
    }
    public void activateSpeedPowerUp() {
        bulletSpeedMultiplier = 2.0;
    }

    public void deactivateSpeedPowerUp() {
        bulletSpeedMultiplier = 1.0; // Resets to normal speed
    }

    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_A:
                movingLeft = false;
                break;
            case KeyEvent.VK_D:
                movingRight = false;
                break;
            case KeyEvent.VK_W:
                movingUp = false;
                break;
            case KeyEvent.VK_S:
                movingDown = false;
                break;
        }
    }


    private boolean bulletSizeIncreased = false;

    public void activateBulletSizePowerUp() {
        bulletSizeIncreased = true;

    }

    public boolean isBulletSizeIncreased() {
        return bulletSizeIncreased;
    }

}
