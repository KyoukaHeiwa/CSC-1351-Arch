package FinalProject;

import basicgraphics.*;
import basicgraphics.images.Picture;
import basicgraphics.SpriteComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class Boss extends Sprite {
    int hitpoints;

    private final double MAX_SPEED = 150.0;
    private int width;
    private int height;
    private int speed;
    private int strength;
    static int bossCount;
    private int phase;

    private double aggressiveSpeedMultiplier = 1.08;


    // Add Boss States
    enum State {
        AGGRESSIVE, DEFENSIVE, CHARGING
    }

    private State currentState;

    public Boss(SpriteComponent sc) {
        super(sc);
        bossCount++;
        hitpoints = 45;
        this.width = 250;
        this.height = 250;
        phase = 1;
        currentState = State.AGGRESSIVE; // Start in aggressive mode

        // Setup boss
        setupBoss(sc);
    }

    private void setupBoss(SpriteComponent sc) {
        setX((sc.getSize().width / 2) - (getWidth() / 2));
        setY(sc.getSize().height / 2 - getHeight() / 2);
        setPicture(makeBall(Color.YELLOW, 250));
        setTopCenterStartPosition(sc.getSize());
        setRandomVelocity();
    }


    @Override
    public void processEvent(SpriteCollisionEvent se) {
        super.processEvent(se);
       boolean collidedWithBoundary = true;
        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            double newX = getX();
            double newY = getY();

            if (se.xlo || se.xhi) {
                newX = Math.min(Math.max(newX, 0), getSpriteComponent().getSize().width - getWidth());
                setVel(-getVelX(), getVelY());
                collidedWithBoundary = true;
            }
            if (se.ylo || se.yhi) {
                newY = Math.min(Math.max(newY, 0), getSpriteComponent().getSize().height - getHeight());
                setVel(getVelX(), -getVelY());
                collidedWithBoundary = true;
            }
            if(collidedWithBoundary) {
                hitpoints += 5;
            }
            setX(newX);
            setY(newY);
        }
        // Implement boss behavior based on current state
        switch (currentState) {
            case AGGRESSIVE:
               setAggressiveSpeed();
                break;
            case DEFENSIVE:
                // Defensive behavior
                break;
        }

        // Change behavior based on health
        if (hitpoints < 20) {
            currentState = State.DEFENSIVE;
        }
    }
    private void setAggressiveSpeed() {
        if (getVelX() == 0 && getVelY() == 0) {
            // Ensures the boss moves if it was stationary
            setRandomVelocity();
        }
        // Increase speed by aggressiveSpeedMultiplier
        setVel(getVelX() * aggressiveSpeedMultiplier, getVelY() * aggressiveSpeedMultiplier);
    }

    private void setNormalSpeed() {
        double newVelX = getVelX() * aggressiveSpeedMultiplier;
        double newVelY = getVelY() * aggressiveSpeedMultiplier;
        if (Math.abs(getVelX()) > speed || Math.abs(getVelY()) > speed) {
            setVel(getVelX() / aggressiveSpeedMultiplier, getVelY() / aggressiveSpeedMultiplier);
            setVel(Math.min(Math.abs(newVelX), MAX_SPEED) * Math.signum(newVelX),
                    Math.min(Math.abs(newVelY), MAX_SPEED) * Math.signum(newVelY));
        }
    }
    //@Override
    public void draw(Graphics g) {
       // Draw boss
        // Draw health bar
        g.setColor(Color.GREEN);
        g.fillRect((int) getX(), (int) (getY() - 20), (hitpoints * 5), 10);
        g.setColor(Color.BLACK);
        g.drawString("HP: " + hitpoints, (int) getX(), (int) (getY() - 10));
    }

    public void takeDamage(int damage) {
        hitpoints -= damage;
        if (hitpoints <= 0) {
            setActive(false);
        }
    }

    private void setTopCenterStartPosition(Dimension screenSize) {
        double x = (screenSize.width - getWidth()) / 2.0;
        double y = 0;
        setX(x);
        setY(y);
    }

    private void setRandomVelocity() {
        double randVelX = 20 * Game.RAND.nextDouble() - 10;
        double randVelY = 20 * Game.RAND.nextDouble() - 10;

        // Ensure the velocity does not exceed the max speed
        randVelX = Math.min(Math.abs(randVelX), MAX_SPEED) * Math.signum(randVelX);
        randVelY = Math.min(Math.abs(randVelY), MAX_SPEED) * Math.signum(randVelY);

        setVel(randVelX, randVelY);
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

    private Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
