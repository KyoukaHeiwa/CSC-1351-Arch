package FinalProject;

import basicgraphics.*;
import java.awt.*;
import java.util.Random;
import basicgraphics.images.Picture;

public class PowerUp extends Sprite {
    private static final Color POWER_UP_COLOR = Color.BLACK;
    private static final int SIZE = 20;

    private final Random random = new Random();
    private final PowerUpType type;

    public PowerUp(SpriteComponent sc) {
        super(sc);
        this.type = generateRandomPowerUpType();
        setPicture(createPowerUpPicture());
        setRandomPosition();
    }

    private PowerUpType generateRandomPowerUpType() {
        PowerUpType[] types = PowerUpType.values();
        return types[random.nextInt(types.length)];
    }

    private Picture createPowerUpPicture() {
        Image image = BasicFrame.createImage(SIZE, SIZE);
        Graphics g = image.getGraphics();
        g.setColor(POWER_UP_COLOR);

        int[] xPoints = {SIZE / 2, 0, SIZE};
        int[] yPoints = {0, SIZE, SIZE};
        g.fillPolygon(xPoints, yPoints, 3);
        return new Picture(image);
    }

    private void setRandomPosition() {
        int x = random.nextInt(Game.BOARD_SIZE.width - SIZE);
        int y = random.nextInt(Game.BOARD_SIZE.height - SIZE);
        setX(x);
        setY(y);
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {


        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            // Bounce off walls
            if (se.xlo || se.xhi) {
                setVelX(-getVelX());
            }
            if (se.ylo || se.yhi) {
                setVelY(-getVelY());
            }
        }
    }

    //@Override
    public void draw(Graphics g) {

    }
    private void setVelX(double v) {

    }

    public PowerUpType getType() {
        return type;
    }

    public enum PowerUpType {

        WEAPON_UPGRADE
        // Add more types as needed
    }
}
