package finalProject;

import basicgraphics.BasicContainer;
import basicgraphics.BasicFrame;
import basicgraphics.ClockWorker;
import basicgraphics.SpriteComponent;
import boardgame.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.util.Random;

public class bfGame {
    BasicFrame bfG;
    Container con;
    BasicContainer bc1, bc2;
    CardLayout cards;
    SpriteComponent sc;
    JPanel mainTextPanel, actionButtonPanel, blankJPanel, blankJPanel2, playerPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, weaponLabel, weaponLabelName, blankLabel1, blankLabel2;
    JButton action1, action2, action3, action4;
    Font titleFont = new Font("Times New Roman", Font.PLAIN , 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN , 28);
    int playerHP, maxHP, monsterHP, ring;
    String weapon, position;
    JTextArea mainTextArea;
    ActionHandler actionHandler = new ActionHandler();
    Random rand = new Random();

    public static void main(String[] args){
        new bfGame();
    }

    public bfGame(){
        bfG = new BasicFrame("Text Adventure");
        con = bfG.getContentPane();
        cards = new CardLayout();
        con.setLayout(cards);
        bc1 = new BasicContainer();
        con.add(bc1, "Splash");
        bc2 = new BasicContainer();
        con.add(bc2, "Game");
        


        //if(bfG != null)
          //  bfG.disposeFrame();
        sc = new SpriteComponent(){
            @Override
            public void paintBackground(Graphics g){
                //Dimension d = new Dimension(800,600);
                Dimension q = getSize();
                g.setColor(Color.black);
                g.fillRect(0,0,q.width,q.height);
                // g.setColor(Color.blue);
                // g.fillRect(100,100,600,250);
            }
        };
        // SpriteComponent sc2 = new SpriteComponent(){
        //     @Override
        //     public void paintBackground(Graphics g){
        //         //Dimension d = new Dimension(800,600);
        //         Dimension q = new Dimension(600, 250);
        //         g.setColor(Color.blue);
        //         g.fillRect(100,100,600,250);
        //     }
        // };
        sc.setPreferredSize(new Dimension(800,600));      
       
        String [][] layout = {
            {"Title"}, 
            {"Button"}
        };
        bc1.setStringLayout(layout);
        bc1.add("Title", new JLabel("Basic Adventure"));
        JButton jstart = new JButton("Start");
        jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(con,"Game");
                // The BasicContainer bc2 must request the focus
                // otherwise, it can't get keyboard events.
                bc2.requestFocus();
                
                // Start the timer
                //ClockWorker.initialize(5);
            }
        });
        jstart.setFocusPainted(false);
        bc1.add("Button", jstart);
        
        String [][] gameLayout = {
            {"HP", "HP", "HP", "Weapon", "Weapon", "Weapon"},
            {"Main", "Main", "Main", "Main", "Main", "Main"},
            //{"Main"},
            {"X1", "X1", "Action", "Action", "X2", "X2" },
            {"X1", "X1", "Action", "Action", "X2", "X2" },
        };
        bc2.setStringLayout(gameLayout);
        
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.black);
        //bc2.add("Main", mainTextPanel);

        mainTextArea = new JTextArea("hello");
        //mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setPreferredSize(new Dimension(800,250));
        mainTextArea.setMinimumSize(mainTextArea.getPreferredSize());
        
        mainTextArea.setFont(normalFont);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);
        mainTextArea.setLineWrap(true);
        //mainTextPanel.add(mainTextArea);
        bc2.add("Main", mainTextArea);
        
        actionButtonPanel = new JPanel();
        //actionButtonPanel.setBounds(250, 350, 300, 150);
        actionButtonPanel.setPreferredSize(new Dimension(400, 450));
        actionButtonPanel.setMinimumSize(actionButtonPanel.getPreferredSize());
        actionButtonPanel.setBackground(Color.black);
        actionButtonPanel.setLayout(new GridLayout(4,1));
        bc2.add("Action", actionButtonPanel);

        action1 = new JButton("Action 1");
        // action1.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         //JOptionPane.showMessageDialog(action1, "Action 1 was pressed!");
        //     }
        // });
        action2 = new JButton("Action 2");
        action3 = new JButton("Action 3");
        action4 = new JButton("Action 4");
        // button1.setBounds(250, 350, 300, 35);
        
        action1.setFont(normalFont);
        action1.setBackground(Color.black);
        action1.setForeground(Color.white);
        action1.setFocusPainted(false);
        action1.addActionListener(actionHandler);
        action1.setActionCommand("a1");
        actionButtonPanel.add(action1);
        //bc2.add("Action1", action1);
        // action2.setBounds(250, 385, 300, 35);
        
        action2.setFont(normalFont);
        action2.setBackground(Color.black);
        action2.setForeground(Color.white);
        action2.setFocusPainted(false);
        action2.addActionListener(actionHandler);
        action2.setActionCommand("a2");
        actionButtonPanel.add(action2);
        //bc2.add("Action2", action2);
        // action3.setBounds(250, 420, 300, 35);
        
        action3.setFont(normalFont);
        action3.setBackground(Color.black);
        action3.setForeground(Color.white);
        action3.setFocusPainted(false);
        action3.addActionListener(actionHandler);
        action3.setActionCommand("a3");
        actionButtonPanel.add(action3);
        //bc2.add("Action3", action3);
        
        action4.setFont(normalFont);
        action4.setBackground(Color.black);
        action4.setForeground(Color.white);
        action4.setFocusPainted(false);
        action4.addActionListener(actionHandler);
        action4.setActionCommand("a4");
        actionButtonPanel.add(action4);
        // action4.setBounds(250, 455, 300, 35);
        //sc.add("row4", action4);
        //sc.add("Action Panel", actionButtonPanel);
        //bc2.add("Action4", action4);
        bfG.show();

        playerPanel = new JPanel();
        //playerPanel.setBounds(100,15,600,50);
        playerPanel.setPreferredSize(new Dimension(600, 50));
        playerPanel.setMinimumSize(actionButtonPanel.getPreferredSize());
        playerPanel.setBackground(Color.black);
        playerPanel.setLayout(new GridLayout(1,4));
        //bc2.add("HP", playerPanel);

        hpLabel = new JLabel("");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.white);
        playerPanel.add(hpLabel);
        hpLabelNumber = new JLabel();
        hpLabelNumber.setFont(normalFont);
        hpLabelNumber.setForeground(Color.white);
        //playerPanel.add(hpLabelNumber);
        //bc2.add("HP2", hpLabel);
        //bc2.add("HP3", hpLabelNumber);

        weaponLabel = new JLabel("Weapon:");
        weaponLabel.setFont(normalFont);
        weaponLabel.setForeground(Color.white);
        playerPanel.add(weaponLabel);
        weaponLabelName = new JLabel();
        weaponLabelName.setFont(normalFont);
        weaponLabelName.setForeground(Color.white);
        playerPanel.add(weaponLabelName);

        blankJPanel = new JPanel();
        blankJPanel.setBackground(Color.black);
        bc2.add("X1", new JLabel("X1"));
        blankJPanel2 = new JPanel();
        blankJPanel2.setBackground(Color.black);
        bc2.add("X2", new JLabel("X2"));
        
        
        playerSetup();
        bc2.add("HP", hpLabel);
        //bc2.add("HP2", hpLabelNumber);
        bc2.add("Weapon", weaponLabel);
        //bc2.add("Weapon", weaponLabelName);
        monsterSetup();
    }
    
    public void playerSetup(){
        maxHP = 15;
        playerHP = maxHP;
        weapon = "Knife";
        weaponLabelName.setText(weapon);
        hpLabel.setText("HP: " + playerHP);
        weaponLabel.setText("Weapon: " + weapon);
        //hpLabel = hpLabel + " " + hpLabelNumber;
        
        townGate();
    }
    public void monsterSetup(){
        monsterHP = 20;
    }
    public void townGate(){
        position = "townGate";
        mainTextArea.setText("You are at the town's gate. \nLooking forward, a guard is standing ready. What will you do?");

        action1.setText("Talk to the guard");
        action2.setText("Attack the guard");
        action3.setText("Leave");
        action4.setText("");
    }

    public void talkGuard(){
        position = "talkGuard";
        mainTextArea.setText("Guard: Hello, you don't look like you're from around \nhere. I apologize as I am unable to let a stranger, like you, into our town.");
        action1.setText(">");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }

    public void attackGuard(){
        position = "attackGuard";
        mainTextArea.setText("Guard: Don't be foolish.\n\nThe guard fought back, inflicting a heavy hitting \nattack.\n(You receive 3 damage)");
        playerHP = playerHP-3;
        hpLabelNumber.setText(""+playerHP);
        action1.setText(">");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }

    public void crossRoad(){
        position = "crossRoad";
        mainTextArea.setText("You are at a crossroad.\nIf you go south, you will go back to the town.");
        action1.setText("Go north");
        action2.setText("Go east");
        action3.setText("Go south");
        action4.setText("Go west");
    }
    public void north(){
        position = "north";
        if (playerHP < maxHP) {
            mainTextArea.setText("There is a river. \nYou drink the water and rest at the riverside. \n\n(You recover 2 hit points)");
            playerHP += 2;
        }
        else{
            mainTextArea.setText("There is a river. \nYou drink the water and rest at the riverside. \n\n(Your HP remains unchanged)");
        }
        hpLabelNumber.setText(""+playerHP);
        action1.setText("Go south");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }
    public void east(){
        position = "east";
        mainTextArea.setText("You walk into a forest and find a Spear!\n\n(You obtained a Spear)");
        weapon = "Spear";
        weaponLabelName.setText(weapon);
        action1.setText("Go west");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }
    public void west(){
        position = "west";
        mainTextArea.setText("You encounter a goblin!");
        action1.setText("Fight");
        action2.setText("Run");
        action3.setText("");
        action4.setText("");
    }
    public void fight(){
        position = "fight";
        mainTextArea.setText("Monster HP:" + monsterHP + "\n\nWhat will you do?");
        action1.setText("Attack");
        action2.setText("Run");
        action3.setText("");
        action4.setText("");
    }
    public void playerAtk(){
        position = "playerAtk";

        int playerDmg = 0;
        if (weapon.equals("Knife")) {
            playerDmg = rand.nextInt(3);
        }
        else if (weapon.equals("Spear")) {
            playerDmg = rand.nextInt(8);            
        }
        mainTextArea.setText("You attacked the monster and dealt " +playerDmg+ " damage!");
        monsterHP -= playerDmg;

        action1.setText(">");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }
    public void monsterAtk(){
        position = "monsterAtk";

        int monsterDmg = 0;
        monsterDmg = rand.nextInt(7);
        mainTextArea.setText("The monster attacked and dealt "+monsterDmg + " damage to you!");
        playerHP -= monsterDmg;
        hpLabelNumber.setText(""+playerHP);
        action1.setText(">");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }
    public void victory(){
        position = "victory";

        mainTextArea.setText("The monster has been vanquished!\nInspecting the corpse, you find a ring!\n\n(You obtained a Copper Bracelet)");
        ring = 1;
        action1.setText("Go east");
        action2.setText("");
        action3.setText("");
        action4.setText("");
    }
    public void defeat(){
        position = "defeat";

        mainTextArea.setText("You died!\n\n<GAME OVER>");
        action1.setText("Title Screen");
        action2.setText("");
        action3.setText("");
        action4.setText("");
        action2.setVisible(false);
        action3.setVisible(false);
        action4.setVisible(false);
    }

    public class ActionHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(action1, "This was pressed!");
            String yourAction = e.getActionCommand();

            switch (position) {
                case "townGate":
                    switch (yourAction) {
                        case "a1": talkGuard(); 
                            break;
                        case "a2": attackGuard(); 
                            break;
                        case "a3": crossRoad();
                            break;
                        default: 
                            break;
                    }
                    break;
                case "talkGuard":
                    switch (yourAction) {
                        case "a1": townGate();
                            
                            break;
                        case "a2":

                            break;
                        case "a3":

                            break;
                        default:
                            break;
                    }
                    break;
                case "attackGuard":
                    switch (yourAction) {
                        case "a1":
                            townGate();
                            break;
                        case "a2":
                            break;
                        case "a3":
                            break;
                        case "a4":
                            break;
                    }
                    break;
                case "crossRoad":
                    switch (yourAction) {
                        case "a1":
                            north();
                            break;
                        case "a2":
                            east();
                            break;
                        case "a3":
                            townGate();
                            break;
                        case "a4":
                            west();
                            break;
                    }
                    break;
                case "north":
                    switch (yourAction) {
                        case "a1":
                            crossRoad();
                            break;
                        case "a2":
                            break;
                        case "a3":
                            break;
                        case "a4":
                            break;
                    }
                    break;
                case "east":
                    switch (yourAction) {
                        case "a1":
                            crossRoad();
                            break;
                        default:
                            break;
                    }
                    break;
                case "west":
                    switch (yourAction) {
                        case "a1":
                            fight();
                            break;
                        case "a2":
                        crossRoad();
                            break;
                        case "a3":
                            break;
                        case "a4":
                            break;
                    }
                    break;
                case "fight":
                    switch (yourAction) {
                        case "a1":
                            playerAtk();
                            break;
                        case "a2":
                            crossRoad();
                            break;
                        default:
                            break;
                    }
                    break;
                case "playerAtk":
                    switch (yourAction) {
                        case "a1":
                        if (monsterHP<1) {
                            victory();
                        }
                        else{
                            monsterAtk();
                        }
                            break;
                        case "a2":
                            crossRoad();
                            break;
                        default:
                            break;
                    }
                    break;
                case "monsterAtk":
                    switch (yourAction) {
                        case "a1":
                            if (playerHP<1) {
                                defeat();
                            }
                            else{
                                fight();
                            }
                            break;
                        case "a2":
                            break;
                        default:
                            break;
                    }
                    break;
                case "defeat":
                    switch (yourAction) {
                        case "a1":
                            bc2.setVisible(false);
                            bc1.setVisible(true);
                            break;
                        default:
                            break;
                    }
                    break;
                case "victory":
                    switch (yourAction) {
                        case "a1":
                            crossRoad();
                            break;
                        default:
                            break;
                    }
                    break;
                
            
                //default:
                    //break;
            }
        }
    }
}