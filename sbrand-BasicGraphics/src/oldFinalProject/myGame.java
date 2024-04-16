/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oldFinalProject;

import basicgraphics.BasicFrame;
import basicgraphics.ClockWorker;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.sounds.ReusableClip;
import basicgraphics.sounds.SoundPlayer;
import basicgraphics.Task;
import basicgraphics.images.Picture;
import static oldFinalProject.Enemy.enemyCount;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import basicgraphics.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class myGame {
    final public static Random RAND = new Random();
    final public static Color SHOOTER_COLOR = Color.blue;
    final public static Color BULLET_COLOR = Color.blue;
    final public static Color ENEMY_COLOR = Color.red;
    final public static Color EXPLOSION_COLOR = Color.orange;
    final public static int BIG = 20;
    final public static int SMALL = 5;
    public static int ENEMIES = 10;
    final public static Dimension BOARD_SIZE = new Dimension(1400,1000);
    static SpriteComponent sc;
    static Shooter shooter;
    private static List<Enemy> listOfEnemies = new CopyOnWriteArrayList<>();
    private static List<Bullet> listOfBullets = new ArrayList<>();
    static final double MIN_DISTANCE_FROM_SHOOTER = 200; // Change this to the desired distance
    static int enemyKillCount = 0;
    static int scoreMultiplier = 1;
    public static int bulletSpeedMultiplier = 1;
    final static ReusableClip themeSong = new ReusableClip("theme.wav");
    //final static ReusableClip menuSong = new ReusableClip("menu.wav");
    final static ReusableClip enemyDeath = new ReusableClip("Enemy_explode.wav");
    final static ReusableClip wEnemySpawn = new ReusableClip("Enemy_spawn_green.wav");
    final static ReusableClip pEnemySpawn = new ReusableClip("Enemy_spawn_purple.wav");
    final static ReusableClip rEnemySpawn = new ReusableClip("Enemy_spawn_blue.wav");
    final static ReusableClip bulletSound = new ReusableClip("Fire_Hispeed.wav");
    final static ReusableClip shooterDeath = new ReusableClip("Ship_explode.wav");
    final static ReusableClip shooterSpawn = new ReusableClip("Player_Spawn.wav");
    final static ReusableClip gameOver = new ReusableClip("Game_over.wav");
    public static List<Life> lifeSprites = new ArrayList<>();
    List<Sprite> spritesToRemove = new ArrayList<>();

    public static void main(String[] args) {
        myGame g = new myGame();
        g.run();
    }
    public static void removeEnemy(Enemy enemy) {
        listOfEnemies.remove(enemy);
    }
    public static List<Bullet> getBullets() {
        return listOfBullets;
    }
    public static Shooter getShooter() {
        // TODO Auto-generated method stub
        return shooter;
    }
    static Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }
    private int score = 0;
    private int highScore = 0;

    private String highScoreFile = "highscore.txt";
    private JLabel scoreLabel, highScoreLabel;


    int enemiesToKill = 20;
    
    public JLabel gameTitle = new JLabel("Geometry Wars");
    
    // final static ReusableClip shooterWallCollide = new ReusableClip("Ship_hitwall.wav");
    private Timer pEnemyTimer, rEnemyTimer, wEnemyTimer;

    BasicFrame bf = new BasicFrame("Shooter!");
    Set<Integer> keys = new HashSet<>();


    public void run() {

        final Container content = bf.getContentPane();
        final CardLayout cards = new CardLayout();
        content.setLayout(cards);
        BasicContainer bc1 = new BasicContainer();
        content.add(bc1,"Splash");
        final BasicContainer bc2 = new BasicContainer();
        content.add(bc2,"Game");
        loadHighScore();
        
        sc = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.black); 
                g.fillRect(0, 0, d.width, d.height);
                final int NUM_STARS = 30;
                Random rand = new Random();
                rand.setSeed(0);
                g.setColor(Color.white);
                for(int i=0;i<NUM_STARS;i++) {
                    int diameter = rand.nextInt(5)+1;
                    int xpos = rand.nextInt(d.width);
                    int ypos = rand.nextInt(d.height);
                    g.fillOval(xpos, ypos, diameter, diameter);
                }
            }
        };
        sc.setPreferredSize(BOARD_SIZE);
        String[][] splashLayout = {
            {"Title"},
            {"Button"}
        };
        // bf.setStringLayout(layout);
        // bf.add("center",sc);
        // bf.add("north", scoreLabel);
        bc1.setStringLayout(splashLayout);
        // menuSong.play();
        // menuSong.loop();
        bf.getContentPane().setBackground(Color.black);
        bc1.add("Title", gameTitle);
        //scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gameTitle.setOpaque(true); // Make the background opaque
        gameTitle.setBackground(Color.BLACK); // Set the background color to black
        gameTitle.setForeground(Color.WHITE);
        JButton jstart = new JButton("Start");
        jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(content,"Game");
                // The BasicContainer bc2 must request the focus
                // otherwise, it can't get keyboard events.
                bc2.requestFocus();
                
                // Start the timer
                //ClockWorker.initialize(5);
                ClockWorker.initialize(10);
                ClockWorker.addTask(sc.moveSprites());
                themeSong.playOverlapping();
                themeSong.loop();
                shooterSpawn.playOverlapping();
                new Thread(() -> {
                    while (true) {
                        if (keys.contains(KeyEvent.VK_W)) {
                            shooter.setVelY(-2); // Move up
                        }
                        if (keys.contains(KeyEvent.VK_S)) {
                            shooter.setVelY(2); // Move down
                        }
                        if (keys.contains(KeyEvent.VK_A)) {
                            shooter.setVelX(-2); // Move left
                        }
                        if (keys.contains(KeyEvent.VK_D)) {
                            shooter.setVelX(2); // Move right
                        }
                        shooter.move();
                        updateEnemies();
                        removeInactiveBullets();
                        // Iterator<Enemy> iterator = listOfEnemies.iterator();
                        // while (iterator.hasNext()) {
                        //     Sprite sprite = iterator.next();
                        //     if (!sprite.isActive()) {
                        //         iterator.remove();
                        //     }
                        // }    
                        // If all enemies are inactive, spawn more enemies
                        // if (allEnemiesInactive) {
                        //     for(int i=0;i<ENEMIES;i++) {
                        //         RhombusEnemy rhombusEnemy = RhombusEnemy.createRhombusEnemy(sc);
                        //         PinwheelEnemy pinwheelEnemy = PinwheelEnemy.createPinwheelEnemy(sc);
                        //         WeaverEnemy weaverEnemy = WeaverEnemy.createWeaverEnemy(sc);
                        //         listOfEnemies.add(rhombusEnemy);
                        //         listOfEnemies.add(pinwheelEnemy);
                        //         listOfEnemies.add(weaverEnemy);
                        //         sc.addSprite(rhombusEnemy);
                        //         sc.addSprite(pinwheelEnemy);
                        //         sc.addSprite(weaverEnemy);
                        //     }
                        // }
        
                        try {
                            Thread.sleep(1000 / 60); // Update 60 times per second
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        bc1.add("Button",jstart);
        bc1.setBackground(BULLET_COLOR);
        String[][] gameLayout = {
            {"Score", "High Score"},
            {"Geometry Wars", "Geometry Wars"}
        };
        bc2.setStringLayout(gameLayout);
        scoreLabel = new JLabel("Score: " + score, SwingConstants.LEFT);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setOpaque(true); // Make the background opaque
        scoreLabel.setBackground(Color.BLACK); // Set the background color to black
        scoreLabel.setForeground(Color.GREEN); // Set the text color to white
        highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.RIGHT);
        highScoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        highScoreLabel.setOpaque(true); // Make the background opaque
        highScoreLabel.setBackground(Color.BLACK); // Set the background color to black
        highScoreLabel.setForeground(Color.GREEN); // Set the text color to white
        shooter = new Shooter(sc);
        int lifeW= 20;
        int totalW = shooter.getLives() * lifeW;
        int startX = (BOARD_SIZE.width - totalW) / 2;
        for (int i = 0; i < shooter.getLives(); i++) {
            Life life = new Life(sc);
            //life.setX(i * 20); // Position the life sprites next to each other
            life.setX(startX + i * lifeW);
            lifeSprites.add(life);
        }
        

        bc2.add("Score", scoreLabel);
        bc2.add("High Score", highScoreLabel);
        bc2.add("Geometry Wars", sc);
        pEnemyTimer = new Timer();
        rEnemyTimer = new Timer();
        wEnemyTimer = new Timer();
        pEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                RhombusEnemy rhombusEnemy = RhombusEnemy.createRhombusEnemy(sc);
                listOfEnemies.add(rhombusEnemy);
                sc.addSprite(rhombusEnemy);
                rEnemySpawn.playOverlapping();
            }
        }, 0, 1000);
        wEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                WeaverEnemy weaverEnemy = WeaverEnemy.createWeaverEnemy(sc);
                listOfEnemies.add(weaverEnemy);
                sc.addSprite(weaverEnemy);
                wEnemySpawn.playOverlapping();
            }
        }, 0, 2000);
        rEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                PinwheelEnemy pinwheelEnemy = PinwheelEnemy.createPinwheelEnemy(sc);
                listOfEnemies.add(pinwheelEnemy);
                sc.addSprite(pinwheelEnemy);
                pEnemySpawn.playOverlapping();
            }
        }, 0, 1500);
        KeyAdapter shooterControlListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                keys.add(ke.getKeyCode());
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    // If the space bar is pressed, remove all sprites
                    // for (Sprite sprite : new ArrayList<>(listOfEnemies)) {
                    //     sprite.setActive(false);
                    // }
                    // listOfEnemies.clear();
                    spritesToRemove.addAll(listOfEnemies);
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
                keys.remove(ke.getKeyCode());
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_S:
                        shooter.setVelY(0);
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_D:
                        shooter.setVelX(0);
                        break;
                    case KeyEvent.VK_SPACE:
                        for (Sprite sprite : spritesToRemove) {
                            sprite.setActive(false);
                        }
                        spritesToRemove.clear();
                        break;
                }
            }
        };
        bc2.addKeyListener(shooterControlListener);
        
        
        // new Thread(() -> {
        //     while (true) {
        //         if (keys.contains(KeyEvent.VK_W)) {
        //             shooter.setVelY(-2); // Move up
        //         }
        //         if (keys.contains(KeyEvent.VK_S)) {
        //             shooter.setVelY(2); // Move down
        //         }
        //         if (keys.contains(KeyEvent.VK_A)) {
        //             shooter.setVelX(-2); // Move left
        //         }
        //         if (keys.contains(KeyEvent.VK_D)) {
        //             shooter.setVelX(2); // Move right
        //         }
        //         shooter.move();
        //         updateEnemies();
        //         removeInactiveBullets();

        //         boolean allEnemiesInactive = true;
        //         for (Enemy enemy : listOfEnemies) {
        //             if (enemy.isActive()) {
        //                 allEnemiesInactive = false;
        //                 break;
        //             }
        //         }

        //         // If all enemies are inactive, spawn more enemies
        //         if (allEnemiesInactive) {
        //             for(int i=0;i<ENEMIES;i++) {
        //                 RhombusEnemy rhombusEnemy = RhombusEnemy.createRhombusEnemy(sc);
        //                 PinwheelEnemy pinwheelEnemy = PinwheelEnemy.createPinwheelEnemy(sc);
        //                 WeaverEnemy weaverEnemy = WeaverEnemy.createWeaverEnemy(sc);
        //                 listOfEnemies.add(rhombusEnemy);
        //                 listOfEnemies.add(pinwheelEnemy);
        //                 listOfEnemies.add(weaverEnemy);
        //                 sc.addSprite(rhombusEnemy);
        //                 sc.addSprite(pinwheelEnemy);
        //                 sc.addSprite(weaverEnemy);
        //             }
        //         }

        //         try {
        //             Thread.sleep(1000 / 60); // Update 60 times per second
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }).start();
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                Bullet bullet = new Bullet(sc,shooter,me.getX(),me.getY());
                listOfBullets.add(bullet);
                bulletSound.playOverlapping();
            }
        };
        sc.addMouseListener(ma);
        
        sc.addSpriteSpriteCollisionListener(Enemy.class, Shooter.class, new SpriteSpriteCollisionListener<Enemy, Shooter>() {
            @Override
            public void collision(Enemy sp1, Shooter sp2) {
                shooterDeath.playOverlapping();
                gameOver.playOverlapping();
                sc.removeSprite(sp2);
                shooter.setX(BOARD_SIZE.width / 2);
                shooter.setY(BOARD_SIZE.height / 2);
                sc.addSprite(sp2);
                shooter.lifeLost();
                if (shooter.getLives() < lifeSprites.size()){
                    Life lostLife = lifeSprites.get(lifeSprites.size() - 1);
                    lostLife.setActive(false);
                }
                if (shooter.getLives() <= 0){
                    playerDied();
                    saveHighScore();
                    sp1.setActive(false);
                    sp2.setActive(false);
                    JOptionPane.showMessageDialog(sc, "You lose! Game Over!");
                    System.exit(0);
                }
            }
        });
        sc.addSpriteSpriteCollisionListener(Enemy.class, Bullet.class, new SpriteSpriteCollisionListener<Enemy, Bullet>() {
            @Override
            public void collision(Enemy sp1, Bullet sp2) {
                enemyDeath.playOverlapping();
                sp1.setActive(false);
                sp2.setActive(false);
                myGame.removeEnemy(sp1);
                enemyKillCount++;
                if (sp1 instanceof RhombusEnemy) {
                    score += 50 * scoreMultiplier; // Increase the score by 25 if the enemy is a RhombusEnemy
                    scoreLabel.setText("Score: " + score); // Update the score label
                } else if (sp1 instanceof PinwheelEnemy) {
                    score += 25 * scoreMultiplier; // Increase the score by 50 if the enemy is a PinwheelEnemy
                    scoreLabel.setText("Score: " + score); // Update the score label
                }
                else if (sp1 instanceof WeaverEnemy) {
                    score += 100 * scoreMultiplier; // Increase the score by 50 if the enemy is a PinwheelEnemy
                    scoreLabel.setText("Score: " + score); // Update the score label
                }
                if (enemyKillCount == enemiesToKill) {
                    scoreMultiplier++;
                    enemiesToKill *= 2;
                }
                if (score % 5000 == 0) {
                    myGame.bulletSpeedMultiplier *= 1.3;
                }
                int killThreshold = 50;
                if (enemyKillCount == killThreshold) {
                    // JOptionPane.showMessageDialog(sc, "You win! Game Over!");
                    // System.exit(0);
                    killThreshold *= 1.3;
                    ENEMIES *= 1.3;
                }
            }
        });
        
        bf.show();
        // ClockWorker.initialize(10);
        // ClockWorker.addTask(sc.moveSprites());
        
        
        
    }

    public void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Save high score to file
    public void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Call this method whenever the player dies
    public void playerDied() {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
        score = 0; // Reset score
    }
    public void removeInactiveBullets() {
        Iterator<Bullet> bulletIterator = listOfBullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (!bullet.isActive() || bullet.isOutOfGameArea()) {
                bulletIterator.remove();
            }
        }
    }
    

    synchronized void updateEnemies() {
        for (Enemy enemy : listOfEnemies) {
            enemy.move(myGame.BOARD_SIZE);
        }
        listOfEnemies.removeIf(sprite -> !sprite.isActive());
    }
}
