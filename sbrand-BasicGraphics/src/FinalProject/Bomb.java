package FinalProject;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.image.BufferedImage;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

public class Bomb extends Sprite{
    public Bomb(SpriteComponent sc) {
        super(sc);
        setPicture(createBombSprite(15));
    }
    
    public static Picture createBombSprite(int size) {
        int diameter = size * 2;
        BufferedImage image = BasicFrame.createImage(diameter, diameter);
        Graphics2D g2d = image.createGraphics();
    
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
    
        g2d.setColor(Color.WHITE);
        int outlineWidth = size / 10; 
        int totalDiameter = diameter - outlineWidth; 
        g2d.fillOval(outlineWidth / 2, outlineWidth / 2, diameter - outlineWidth, diameter - outlineWidth);
    
        g2d.setColor(Color.BLACK);
        int blackCircleDiameter = totalDiameter - (2 * outlineWidth); 
        g2d.fillOval(outlineWidth + (outlineWidth / 2), outlineWidth + (outlineWidth / 2), blackCircleDiameter, blackCircleDiameter);
    
        g2d.setColor(Color.RED);
        int redDotRadius = size / 3; 
        int redDotX = image.getWidth() / 2 - redDotRadius / 2;
        int redDotY = image.getHeight() / 2 - redDotRadius / 2;
        g2d.fillOval(redDotX, redDotY, redDotRadius, redDotRadius);
    
        return new Picture(image);
    }
}
