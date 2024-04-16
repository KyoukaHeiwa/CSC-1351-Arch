package FinalProject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StarryBackgroundPanel extends JPanel {
    private final int STAR_COUNT = 100;
    private final List<Point> stars = new ArrayList<>();
    private final Timer timer;

    public StarryBackgroundPanel() {
        setBackground(Color.BLACK);
        createStars();
        timer = new Timer(100, e -> updateStars());
        timer.start();
    }

    private void createStars() {
        for (int i = 0; i < STAR_COUNT; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            stars.add(new Point(x, y));
        }
    }

    private void updateStars() {
        if (!stars.isEmpty()) {
            stars.remove((int) (Math.random() * stars.size()));
        }
        // Add a new star at a random location
        int x = (int) (Math.random() * getWidth());
        int y = (int) (Math.random() * getHeight());
        stars.add(new Point(x, y));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStars(g);
    }
    private void drawStars(Graphics g) {
        g.setColor(Color.WHITE);
        for (Point star : stars) {
            g.fillOval(star.x, star.y, 2, 2); // Draw each star
        }
    }
}
