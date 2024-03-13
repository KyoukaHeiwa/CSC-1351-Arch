package bfGame;

import basicgraphics.BasicFrame;
import basicgraphics.SpriteComponent;

import java.awt.*;

public class bfGame {
    BasicFrame bfG;
    Container bfGC;

    public static void main(String[] args){

        new bfGame();
    }

    public bfGame(){
        bfG = BasicFrame.getFrame();
        if(bfG != null)
            bfG.disposeFrame();
        bfG = new BasicFrame("Text Adventure Nice!");
        SpriteComponent sc = new SpriteComponent(){
            @Override
            public void paintBackground(Graphics g){
                Dimension d = new Dimension(800,600);
                g.setColor(Color.black);
                g.fillRect(0,0,d.width,d.height);
            }
        };
        Dimension d = new Dimension(800,600);
        sc.setLayout(null);
        sc.setPreferredSize(d);
        bfG.createBasicLayout(sc);
        bfG.show();
        bfGC = bfG.getContentPane();
    }
}