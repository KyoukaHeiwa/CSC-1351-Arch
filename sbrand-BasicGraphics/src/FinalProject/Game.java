package FinalProject;

import basicgraphics.*;
import basicgraphics.images.Picture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

public class Game {
    private SpriteComponent sc;
    static Game instance;
    private Timer meteorTimer;
    public static Random RAND = new Random();
    public static Color SHOOTER_COLOR = Color.GRAY;
    public static Color BULLET_COLOR = Color.BLACK;
    public static Color ENEMY_COLOR = Color.red;

    public  static Color STRONGENEMY_COLOR = Color.ORANGE;
    public static Color BOSS_COLOR = Color.YELLOW;
    public static int BIG = 20;
    public static int SMALL = 5;
    public static int ENEMIES = 0;

    public static int SENEMIES =1;

    public static int BOSS = 0;

    private static final int MAX_POWERUPS = 1;

    private Shooter shooter;

    private Timer movementUpdateTimer;

    public static Dimension BOARD_SIZE = new Dimension(1800, 1500);

    BasicFrame bf = new BasicFrame("Final Project");
    int activeEnemies = 0;

    int activeSEnemies = 0;

    int newShooter = 0;


    private final Set<Integer> pressedKeys = new HashSet<>();
    private Timer movementTimer;

    int activepowerUps = 0;
    int activeBoss = 0;

    static Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }
    public void initialize(){
        spawnPowerUps();

    }
    private void spawnPowerUps() {
        final SpriteComponent sc = new SpriteComponent();
        for (int i = 0; i < MAX_POWERUPS; i++) {
            PowerUp powerUp = new PowerUp(sc);
            sc.addSprite(powerUp);
        }
    }
    public void resetGame(SpriteComponent sc) {

        Shooter shooter = new Shooter(sc);
        sc.addSprite(shooter);
        activeEnemies = ENEMIES;
        activeSEnemies = SENEMIES;
        initialize();
        run();
    }
    private void spawnMeteor(SpriteComponent sc) {
        Meteor meteor = new Meteor(sc);
        sc.addSprite(meteor);
    }
    private void respawnShooter(SpriteComponent sc){
        Shooter newShooter = new Shooter(sc);
        sc.addSprite(newShooter);
    }
    
    public void run() {

        this.sc = new SpriteComponent();

        final SpriteComponent sc = new SpriteComponent();

        meteorTimer = new Timer(true);
        meteorTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnMeteor(sc);
            }
        }, 0, 5000);

        sc.setPreferredSize(BOARD_SIZE);
        sc.setBackground(Color.BLACK);
        sc.setOpaque(true);
        String[][] layout = {{"center"}};
        bf.setStringLayout(layout);
        bf.getContentPane().setBackground(Color.BLACK);
        bf.add("center", sc);

        final Shooter shooter = new Shooter(sc);

        for (int i = 0; i < ENEMIES; i++) {
            Enemy enemy = new Enemy(sc);
            sc.addSprite(enemy);
            activeEnemies++;
        }

        //Strong enemy spawning
        for (int i = 0; i < SENEMIES; i++) {
            StrongEnemy senemy = new StrongEnemy(sc);
            sc.addSprite(senemy);
            activeSEnemies++;
        }
        //Boss spawning
        for (int i = 0; i < BOSS; i++) {
            Boss b = new Boss(sc);
            sc.addSprite(b);
            activeBoss++;
        }
        //Powerup spawning
        for (int i = 0; i < MAX_POWERUPS; i++) {
            PowerUp p = new PowerUp(sc);
            sc.addSprite(p);
            activepowerUps++;
        }

        //Player movement
        KeyAdapter shooterMovementListener = new KeyAdapter() {
            private int speed = 10;


            @Override
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();

                switch(keyCode){
                    case KeyEvent.VK_A:
                        pressedKeys.add(KeyEvent.VK_A);
                        break;
                    case KeyEvent.VK_D:
                        pressedKeys.add(KeyEvent.VK_D);
                        break;
                    case KeyEvent.VK_W:
                        pressedKeys.add(KeyEvent.VK_W);
                        break;
                    case KeyEvent.VK_S:
                        pressedKeys.add(KeyEvent.VK_S);
                        break;
                }
                updateMovement();
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();

                // Update the movement direction when the corresponding key is released
                switch (keyCode) {
                    case KeyEvent.VK_A:
                        pressedKeys.remove(KeyEvent.VK_A);
                        break;
                    case KeyEvent.VK_D:
                        pressedKeys.remove(KeyEvent.VK_D);
                        break;
                    case KeyEvent.VK_W:
                        pressedKeys.remove(KeyEvent.VK_W);
                        break;
                    case KeyEvent.VK_S:
                        pressedKeys.remove(KeyEvent.VK_S);
                        break;
                }
                updateMovement();
            }
            private void updateMovement() {
                int deltaX = 0;
                int deltaY = 0;

                if (pressedKeys.contains(KeyEvent.VK_A)) {
                    deltaX -= speed;
                }
                if (pressedKeys.contains(KeyEvent.VK_D)) {
                    deltaX += speed;
                }
                if (pressedKeys.contains(KeyEvent.VK_W)) {
                    deltaY -= speed;
                }
                if (pressedKeys.contains(KeyEvent.VK_S)) {
                    deltaY += speed;
                }
                shooter.move(deltaX, deltaY);
            }
        };

        bf.addKeyListener(shooterMovementListener);
        bf.addMenuAction("Help", "Controls", new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(bf.getContentPane(), "WASD to move, Click to shoot.");
            }
        });
        bf.addMenuAction("Help", "New Game", new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(bf.getContentPane(), "Have fun!");
            }
        });
        bf.addMenuAction("File", "Load", new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(bf.getContentPane(), "Have fun!");
            }
        });
        bf.addMenuAction("File", "Store", new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(bf.getContentPane(), "Have fun!");
            }
        });
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                new Bullet(sc, shooter, me.getX(), me.getY());
                Bullet bullet = new Bullet(sc, shooter, me.getX(), me.getY());
                sc.addSprite(bullet);
            }
        };
        sc.addMouseListener(ma);
        //Collision between Enemy and shooter
        sc.addSpriteSpriteCollisionListener(Enemy.class, Shooter.class, new SpriteSpriteCollisionListener<Enemy, Shooter>() {
            @Override
            public void collision(Enemy sp1, Shooter sp2 ) {
                sp1.setActive(true);
                sp2.setActive(false);
                JOptionPane.showMessageDialog(bf.getContentPane(), "You Were torched by a sunspot, Thanks for playing!");
                System.exit(0);

            }
        });
        //Collision for powerup and shooter
        sc.addSpriteSpriteCollisionListener(PowerUp.class, Shooter.class, new SpriteSpriteCollisionListener<PowerUp, Shooter>() {
            @Override
            public void collision(PowerUp sp1, Shooter sp2) {
                sp1.setActive(false);
                activepowerUps--;
            }
        });
        //Boss and shooter collision
        sc.addSpriteSpriteCollisionListener(Shooter.class, Boss.class, new SpriteSpriteCollisionListener<Shooter, Boss>() {
            @Override
            public void collision(Shooter sp4, Boss sp3 ) {
                sp3.setActive(true);
                sp4.setActive(false);
                JOptionPane.showMessageDialog(bf.getContentPane(), "The Sun has set you Ablaze, Thanks for playing!");
                System.exit(0);
            }
        });
        //Boss and bullet collision
        sc.addSpriteSpriteCollisionListener(Boss.class, Bullet.class, new SpriteSpriteCollisionListener<Boss, Bullet>() {
            @Override
            public void collision(Boss boss, Bullet bullet) {
                int damage = bullet.getDamage();
                boss.takeDamage(damage);
                bullet.setActive(false);
                boss.hitpoints-= damage;
                if(boss.hitpoints <= 0){
                    boss.setActive(false);
                    activeBoss--;
                    checkEndOfGame();
                }
            }
        });
        //Strong Enemy and Bullet collision
        sc.addSpriteSpriteCollisionListener(StrongEnemy.class, Bullet.class, new SpriteSpriteCollisionListener<StrongEnemy, Bullet>() {
            @Override
            public void collision(StrongEnemy s, Bullet bullet) {
                int damage = bullet.getDamage();
                s.takeDamage(damage);
                bullet.setActive(false);
                s.hitpoints -=damage;
                if(s.hitpoints <=0) {
                    s.setActive(false);
                    activeSEnemies--;
                    checkEndOfGame();
                }
            }
        });
        //Shooter and Strong Enemy collision
        sc.addSpriteSpriteCollisionListener(Shooter.class, StrongEnemy.class, new SpriteSpriteCollisionListener<Shooter, StrongEnemy>() {
            @Override
            public void collision(Shooter sp4, StrongEnemy sp5 ) {
                sp5.setActive(true);
                sp4.setActive(false);
                JOptionPane.showMessageDialog(bf.getContentPane(), "You Were consumed by a plasma ball, Thanks for playing!");
                System.exit(0);
                checkEndOfGame();
                respawnShooter(sc);
            }
        });
        sc.addSpriteSpriteCollisionListener(Shooter.class, Meteor.class, new SpriteSpriteCollisionListener<Shooter, Meteor>() {
            @Override
            public void collision(Shooter sp1, Meteor sp2) {
                sp1.setActive(false);
                sp2.setActive(false);
                JOptionPane.showMessageDialog(bf.getContentPane(), "You have been ROCKED, Thanks for playing!");
                System.exit(0);
                checkEndOfGame();
                respawnShooter(sc);
            }
        });
        //Bullet and Enemy Collision
        Boss boss = spawnBoss(sc);
        sc.addSpriteSpriteCollisionListener(Enemy.class, Bullet.class, new SpriteSpriteCollisionListener<Enemy, Bullet>() {
            @Override
            public void collision(Enemy sp1, Bullet sp2) {
                sp1.setActive(false);
                sp2.setActive(false);
                activeEnemies--;
                checkEndOfGame();
            }
        });

        bf.show();
    }
    private void checkEndOfGame() {
        if (activeEnemies == 0  && activeSEnemies == 0 && activeBoss == 0) {
            int response = JOptionPane.showConfirmDialog(bf.getContentPane(), "You destroyed all enemies, Do you want to continue to the next Galaxy?","Next Galaxy", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                activeEnemies++;
                activeSEnemies++;
                activeBoss++;

            }else{
                JOptionPane.showMessageDialog(bf.getContentPane(), "Thanks for Playing!");
                System.exit(0);
            }
        }
    }
    private boolean bossSpawned = false;
    private Boss spawnBoss(SpriteComponent sc) {
        Boss boss  = new Boss(sc);
        sc.addSprite(boss);
        bossSpawned = true;
        ClockWorker.initialize(20);
        ClockWorker.addTask(sc.moveSprites());
        bf.show();
        return boss;
    }
    public void promptRestart(String message) {

        int response = JOptionPane.showConfirmDialog(bf.getContentPane(), message + " Try again?", "Defeated", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            activeEnemies++;
            activeSEnemies++;
            activeBoss++;
        respawnShooter(sc);
        } else {
            JOptionPane.showMessageDialog(bf.getContentPane(), "Thanks for Playing!");
            System.exit(0);
        }
    }
    }
