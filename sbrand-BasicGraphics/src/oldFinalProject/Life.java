package oldFinalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;

public class Life extends Sprite {
    public Life(SpriteComponent sc) {
        super(sc);
        setPicture(Shooter.createDiamondSprite(30));
        myGame.sc.addSprite(this);
    }
}