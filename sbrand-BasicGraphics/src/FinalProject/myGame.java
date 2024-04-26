/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import basicgraphics.sounds.ReusableClip;
import basicgraphics.sounds.SoundPlayer;
import basicgraphics.images.Picture;

import java.awt.RenderingHints.Key;
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

import static FinalProject.Enemy.enemyCount;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import basicgraphics.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class myGame {
    public final static Random RAND = new Random();
    final public static Color SHOOTER_COLOR = Color.blue;
    final public static Color BULLET_COLOR = Color.blue;
    final public static Color ENEMY_COLOR = Color.red;
    final public static Color EXPLOSION_COLOR = Color.orange;
    final public static int BIG = 20;
    final public static int SMALL = 5;
    public static int ENEMIES = 10;
    final public static Dimension BOARD_SIZE = new Dimension(1400,1000);
    static SpriteComponent sc, sc2;
    static Shooter shooter;
    private static List<Enemy> listOfEnemies = new CopyOnWriteArrayList<>();
    private static List<Bullet> listOfBullets = new CopyOnWriteArrayList<>();    
    static final double MIN_DISTANCE_FROM_SHOOTER = 200; 
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
    final static ReusableClip smartBomb = new ReusableClip("Fire_smartbomb.wav");
    public static List<Life> lifeSprites = new ArrayList<>();
    public static List<Bomb> bombSprites = new ArrayList<>();
    List<Sprite> spritesToRemove = new CopyOnWriteArrayList<>();
    JPanel titlePanel, startPanel;
    boolean gameIsRunning = false;
    final int numStars = 20;
    public static int numRhombusEnemiesToSpawn = 1;
    public static int numWeaverEnemiesToSpawn = 1;
    private boolean isBulletActive = false;


    private Timer rhombusEnemyTimer, weaverEnemyTimer, frameTimer;

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
    // static Picture makeBall(Color color, int size) {
    //     Image im = BasicFrame.createImage(size, size);
    //     Graphics g = im.getGraphics();
    //     g.setColor(color);
    //     g.fillOval(0, 0, size, size);
    //     return new Picture(im);
    // }
    private int score = 0;
    private int highScore = 0;

    private String highScoreFile = "highscore.txt";
    private JLabel scoreLabel, highScoreLabel;


    int enemiesToKill = 20;
    
    public JLabel gameTitle = new JLabel("Geometry Wars");
    
    // final static ReusableClip shooterWallCollide = new ReusableClip("Ship_hitwall.wav");
    private Timer pEnemyTimer, rEnemyTimer, wEnemyTimer;

    BasicFrame bf = new BasicFrame("Geometry Wars");
    Set<Integer> keys = new HashSet<>();


    public void run() {

        final Container content = bf.getContentPane();
        final CardLayout cards = new CardLayout();
        content.setLayout(cards);
        final BasicContainer bc1 = new BasicContainer();
        content.add(bc1,"Splash");
        final BasicContainer bc2 = new BasicContainer();
        content.add(bc2,"Game");
        loadHighScore();
        // bf.addMenuAction("Help", "About", new Runnable() {
        //     @Override
        //     public void run() {
                // JOptionPane.showMessageDialog(bf.getContentPane(), "WASD to move, Arrow Keys and M1 to shoot, Q to use Smart Bomb");
        //     }
        // });
        sc = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.black); 
                g.fillRect(0, 0, d.width, d.height);
                final int NUM_LIGHTS = 30;
            //wEnemy.setVelocity();
            RAND.setSeed(0);
                g.setColor(Color.white);
                for(int i=0;i<NUM_LIGHTS;i++) {
                    int diameter = RAND.nextInt(5)+1;
                    int xpos = RAND.nextInt(d.width);
                    int ypos = RAND.nextInt(d.height);
                    g.fillOval(xpos, ypos, diameter, diameter);
                }
            }
        };
        sc2 = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.black);
                g.fillRect(0, 0, d.width, d.height);
            }
        };
        sc.setPreferredSize(BOARD_SIZE);
        sc2.setPreferredSize(BOARD_SIZE);

        String[][] splashLayout = {
            {"Background"},
            {"Title"},
            {"Button"}
        };
        bc1.setStringLayout(splashLayout);

        bf.getContentPane().setBackground(Color.black);

        //bc1.add("Title", gameTitle);
        bc1.add("Background", sc2);
        titlePanel = new JPanel();
        titlePanel.setBounds(BOARD_SIZE.width / 2 - 100, BOARD_SIZE.height / 3, 176, 34);
        titlePanel.setBackground(Color.BLACK);
        titlePanel.add(gameTitle);
        sc2.add("Title", titlePanel);

        //scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        gameTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gameTitle.setOpaque(true); 
        gameTitle.setBackground(Color.BLACK);
        gameTitle.setForeground(Color.WHITE);
        JOptionPane.showMessageDialog(bf.getContentPane(), "WASD to move, Arrow Keys and M1 to shoot, Q to use Smart Bomb");

        JButton jstart = new JButton("Start");
        jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameIsRunning = true;
                for (Sprite sprite : spritesToRemove) {
                    sprite.setActive(false);
                }
                spritesToRemove.clear();
                cards.show(content,"Game");
                // The BasicContainer bc2 must request the focus
                // otherwise, it can't get keyboard events.
                bc2.requestFocus();
                
                // Start the timer
                //ClockWorker.initialize(5);
                // ClockWorker.initialize(10);
                ClockWorker.addTask(sc.moveSprites());
                themeSong.playOverlapping();
                themeSong.loop();
                shooterSpawn.playOverlapping();
            }
        });
        startPanel = new JPanel();
        startPanel.setBounds(BOARD_SIZE.width / 2 - 50, 650, 90, 30);
        startPanel.setBackground(Color.WHITE);
        startPanel.setLayout(new GridLayout(1,1));
        jstart.setFont(new Font("Arial", Font.BOLD, 24));
        jstart.setBackground(Color.BLACK);
        jstart.setForeground(Color.WHITE);
        jstart.setFocusPainted(false);
        jstart.setMargin(new Insets(10, 0, 0, 0));
        startPanel.add(jstart);
        sc2.add("Button",startPanel);

        //bc1.setBackground(BULLET_COLOR);
        String[][] gameLayout = {
            {"Score", "High Score"},
            {"Geometry Wars", "Geometry Wars"}
        };
        bc2.setStringLayout(gameLayout);
        scoreLabel = new JLabel("Score: " + score, SwingConstants.LEFT);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreLabel.setOpaque(true); 
        scoreLabel.setBackground(Color.BLACK); 
        scoreLabel.setForeground(Color.GREEN); 
        highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.RIGHT);
        highScoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        highScoreLabel.setOpaque(true); 
        highScoreLabel.setBackground(Color.BLACK); 
        highScoreLabel.setForeground(Color.GREEN); 
        shooter = new Shooter(sc);
        for (int i = 0; i < numStars; i++) {
            Stars star = new Stars(sc2);
            spritesToRemove.add(star);
        }
        ClockWorker.initialize(16);
        ClockWorker.addTask(sc2.moveSprites());
        initLivesAndBombs();
        
        bc2.add("Score", scoreLabel);
        bc2.add("High Score", highScoreLabel);
        bc2.add("Geometry Wars", sc);
        ClockWorker.addTask(new Task() {
                    public void run(){
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
                        //updateEnemies();
                        updateGameElements();
                    }
        });
        ClockWorker.initialize(16);
        frameTimer = new Timer();
        pEnemyTimer = new Timer();
        rEnemyTimer = new Timer();
        wEnemyTimer = new Timer();
        pEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameIsRunning == true) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            PinwheelEnemy pinwheelEnemy = PinwheelEnemy.createPinwheelEnemy(sc);
                            listOfEnemies.add(pinwheelEnemy);
                            pEnemySpawn.playOverlapping();
                        }
                    });
                }
            }
        }, 0, 1000);
        rEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameIsRunning) {
                    for (int i = 0; i < numRhombusEnemiesToSpawn; i++) {
                        RhombusEnemy rhombusEnemy = RhombusEnemy.createRhombusEnemy(sc);
                        listOfEnemies.add(rhombusEnemy);
                        rEnemySpawn.playOverlapping();
                    }
                }
            }
        }, 5000, 1500);

        rhombusEnemyTimer = new Timer();
        rhombusEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameIsRunning) {
                    numRhombusEnemiesToSpawn = numRhombusEnemiesToSpawn < 3 ? numRhombusEnemiesToSpawn + 1 : numRhombusEnemiesToSpawn;
                }
            }
        }, 30000, 30000);
        wEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameIsRunning) {
                    for (int i = 0; i < numWeaverEnemiesToSpawn; i++) {
                        WeaverEnemy weaverEnemy = WeaverEnemy.createWeaverEnemy(sc);
                        listOfEnemies.add(weaverEnemy);
                        //sc.addSprite(weaverEnemy);
                        wEnemySpawn.playOverlapping();
                    }
                }
            }
        }, 15000, 2000); // Starts spawning after 30 seconds, then every 2 seconds

        //Task to increase the number of Weaver enemies every 30 seconds
        weaverEnemyTimer = new Timer();
        weaverEnemyTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameIsRunning) {
                    numWeaverEnemiesToSpawn = numWeaverEnemiesToSpawn < 3 ? numWeaverEnemiesToSpawn + 1 : numWeaverEnemiesToSpawn;
                }
            }
        }, 45000, 38000); 

        KeyAdapter shooterControlListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                keys.add(ke.getKeyCode());
                if (ke.getKeyCode() == KeyEvent.VK_Q && shooter.getBombs() > 0){
                    // If the Q button is pressed, remove all sprites
                    // for (Sprite sprite : new ArrayList<>(listOfEnemies)) {
                    //     sprite.setActive(false);
                    // }
                    // listOfEnemies.clear();
                    shooter.bombLost();
                    Sprite bombSprite = bombSprites.remove(bombSprites.size() - 1);
                    bombSprite.setActive(false);
                    smartBomb.playOverlapping();
                    spritesToRemove.addAll(listOfEnemies);
                }
                if (!isBulletActive) {
                    switch (ke.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                            new Bullet(sc, shooter, KeyEvent.VK_RIGHT);
                            isBulletActive = true;
                            bulletSound.playOverlapping();
                            break;
                        case KeyEvent.VK_LEFT:
                            new Bullet(sc, shooter, KeyEvent.VK_LEFT);
                            isBulletActive = true;
                            bulletSound.playOverlapping();
                            break;
                        case KeyEvent.VK_UP:
                            new Bullet(sc, shooter, KeyEvent.VK_UP);
                            isBulletActive = true;
                            bulletSound.playOverlapping();
                            break;
                        case KeyEvent.VK_DOWN:
                            new Bullet(sc, shooter, KeyEvent.VK_DOWN);
                            isBulletActive = true;
                            bulletSound.playOverlapping();
                            break;
                    }
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
                    case KeyEvent.VK_Q:
                        for (Sprite sprite : spritesToRemove) {
                            sprite.setActive(false);
                        }
                        spritesToRemove.clear();
                        break;
                    case KeyEvent.VK_RIGHT:
                        isBulletActive = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        isBulletActive = false;
                        break;
                    case KeyEvent.VK_UP:
                        isBulletActive = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        isBulletActive = false;
                        break;
                }
            }
        };
        bc2.addKeyListener(shooterControlListener);
        
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                Bullet bullet = new Bullet(sc,shooter,me.getX(),me.getY());
                listOfBullets.add(bullet);
                bulletSound.playOverlapping();
            }
        };
        sc.addMouseListener(ma);
        bf.show();

        // ClockWorker.initialize(10);
        // ClockWorker.addTask(sc.moveSprites());           
    }

    public static List<Enemy> getActiveEnemies() {
        // TODO Auto-generated method stub
        return listOfEnemies;
    }
    private void initLivesAndBombs() {
        int lifeWidth = 30; 
        int bombWidth = 30;
        int spacing = 10;  

        int totalLifeWidth = shooter.getLives() * (lifeWidth + spacing) - spacing;
        int totalBombWidth = shooter.getBombs() * (bombWidth + spacing) - spacing;

        int startLifeX = (BOARD_SIZE.width / 2) - totalLifeWidth - spacing; // Start to the left of center
        int startBombX = (BOARD_SIZE.width / 2) + spacing; // Start to the right of center

        for (int i = 0; i < shooter.getLives(); i++) {
            Life life = new Life(sc);
            life.setX(startLifeX + i * (lifeWidth + spacing));
            life.setY(30); 
            lifeSprites.add(life);
        }

        for (int i = 0; i < shooter.getBombs(); i++) {
            Bomb bomb = new Bomb(sc);
            bomb.setX(startBombX + i * (bombWidth + spacing));
            bomb.setY(30); 
            bombSprites.add(bomb);
        }
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


    public void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void playerDied() {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
        score = 0; 
    }

    public void updateGameElements() {
        updateEnemies();
        updateBullets();
        checkCollisions();
    }
    
    private void updateEnemies() {

        for(Enemy enemy : listOfEnemies) {
            if (enemy.shouldFollow) {
                enemy.followShooter();
            }
            if (!enemy.isActive()) {
                listOfEnemies.remove(enemy);
            }
        }
    }

    private void updateBullets() {
        List<Bullet> toRemove = new ArrayList<>(); // This is holding my bullets that I want to remove
    
        for (Bullet bullet : listOfBullets) {
            if (!bullet.isActive() || bullet.isOutOfGameArea()) {
                toRemove.add(bullet);
            }
        }
    
        listOfBullets.removeAll(toRemove);
    }
    
    private void checkCollisions() {
        sc.addSpriteSpriteCollisionListener(Enemy.class, Shooter.class, new SpriteSpriteCollisionListener<Enemy, Shooter>() {
            @Override
            public void collision(Enemy sp1, Shooter sp2) {
                shooterDeath.playOverlapping();
                for (Enemy enemy : myGame.getActiveEnemies()) {
                    enemy.setActive(false);
                }
                shooter.setX(BOARD_SIZE.width / 2);
                shooter.setY(BOARD_SIZE.height / 2);
                shooter.lifeLost();
                if (!lifeSprites.isEmpty()) {
                    smartBomb.playOverlapping();
                    Sprite lifeSprite = lifeSprites.remove(lifeSprites.size() - 1);
                    lifeSprite.setActive(false);
                }
                if (shooter.getLives() <= 0){
                    playerDied();
                    gameOver.playOverlapping();
                    saveHighScore();
                    sp1.setActive(false);
                    sp2.setActive(false);
                    ClockWorker.finish();
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
                
                if (sp1 instanceof PinwheelEnemy) {
                    score += 25 * scoreMultiplier; 
                }
                else if (sp1 instanceof RhombusEnemy) {
                    score += 50 * scoreMultiplier; 
                    scoreLabel.setText("Score: " + score); 
                } 
                else if (sp1 instanceof WeaverEnemy) {
                    score += 100 * scoreMultiplier; 
                    scoreLabel.setText("Score: " + score); 
                }
                if (enemyKillCount == enemiesToKill) {
                    scoreMultiplier++;
                    enemiesToKill *= 2;
                }
                if (score % 5000 == 0) {
                    myGame.bulletSpeedMultiplier *= 1.3;
                }
            }
        });
    }
}
