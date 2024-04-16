package FinalProject;

import basicgraphics.*;
import basicgraphics.images.Picture;

import java.awt.*;

public class Meteor extends Sprite {
    public Meteor(SpriteComponent sc) {
        super(sc);
        setPicture(makeMeteorPicture());

        int border = Game.RAND.nextInt(4);
        int x = 0, y = 0;
        final int SPEED_MIN = 1, SPEED_MAX = 6;
        switch (border){
            case 0:
                x = Game.RAND.nextInt(Game.BOARD_SIZE.width - Game.BIG);
                y = 0;
                setVel(Game.RAND.nextInt(11) - 5, Game.RAND.nextInt(SPEED_MAX) + SPEED_MIN);
                break;
            case 1:
                x = Game.BOARD_SIZE.width - Game.BIG;
                y = Game.RAND.nextInt(Game.BOARD_SIZE.height - Game.BIG);
                setVel(-(Game.RAND.nextInt(SPEED_MAX) + SPEED_MIN), Game.RAND.nextInt(11) - 5);
                break;
            case 2:
                x = Game.RAND.nextInt(Game.BOARD_SIZE.width - Game.BIG);
                y = Game.BOARD_SIZE.height - Game.BIG;
                setVel(Game.RAND.nextInt(11) - 5, -(Game.RAND.nextInt(SPEED_MAX) + SPEED_MIN));
                break;
            case 3:
                x = 0;
                y = Game.RAND.nextInt(Game.BOARD_SIZE.height - Game.BIG);
                setVel(Game.RAND.nextInt(SPEED_MAX) + SPEED_MIN, Game.RAND.nextInt(11) - 5);
                break;
        }
        setX(x);
        setY(y);
        setX(Game.BOARD_SIZE.width - getWidth());
        setY(0);

        setVel(-10, 10);
    }

    //@Override
    public void draw(Graphics g) {

    }

    private Picture makeMeteorPicture() {
        Image image = BasicFrame.createImage(Game.BIG, Game.BIG);
        Graphics g = image.getGraphics();
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, 0, Game.BIG, Game.BIG);
        return new Picture(image);
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {

        if (se.eventType == CollisionEventType.WALL_INVISIBLE) {
            setActive(false);
        }
    }
}
