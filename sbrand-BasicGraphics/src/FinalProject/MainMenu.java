package FinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainMenu extends JFrame {

    private JButton startButton;
    private JButton controlsButton;
    private JButton settingsButton;
    private JButton exitButton;
    public MainMenu() {
        super("FinalProject");

        StarryBackgroundPanel menuPanel = new StarryBackgroundPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.BLACK); // Set the background color to black

        JLabel titleLabel = new JLabel("Eclipse");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Set the text color to white
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = createCustomButton("Click to Start");
        JButton controlsButton = createCustomButton("Controls");
        JButton settingsButton = createCustomButton("Game info");
        JButton exitButton = createCustomButton("Exit");

        // Start game action
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Game game = new Game();
                game.run();
            }
        });
        // Controls dialog action
        controlsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showControlsDialog();
            }
        });

        // Settings dialog action (placeholder)
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettingsDialog();
            }
        });

        // Exit action
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adding components to menu panel with padding
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        menuPanel.add(startButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(controlsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(settingsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(exitButton);
        menuPanel.add(Box.createVerticalGlue());

        getContentPane().add(menuPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Adjusted size for the additional content
        setVisible(true);

    }

    private void setupKeyboardNavigation() {
        JButton[] buttons = {startButton, controlsButton, settingsButton, exitButton};
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            if (index > 0) buttons[index - 1].requestFocus();
                            break;
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_S:
                            if (index < buttons.length - 1) buttons[index + 1].requestFocus();
                            break;

                    }
                }
            });
        }
    }
        private JButton createCustomButton (String text){
            JButton button = new JButton(text);
            button.setForeground(Color.WHITE); // Set text color to white
            button.setBackground(Color.BLACK); // Set background color to black
            button.setFocusPainted(true);
            button.setFocusable(true);
            return button;
        }

        private void showControlsDialog () {
            JOptionPane.showMessageDialog(this, "Controls:\nWASD to move\nClick to shoot", "Controls", JOptionPane.INFORMATION_MESSAGE);
        }

        private void showSettingsDialog () {
            // Placeholder for settings dialog
            JOptionPane.showMessageDialog(this, "The sun has broken free from its constraints\n Using your M.O.O.N ship blast the sun and its pawns to continue eternal night.\n Powerup Pyramid info: \n Black:Bullet strength\n Cyan:Shield", "Game info", JOptionPane.INFORMATION_MESSAGE);
        }

        public static void main (String[]args){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MainMenu();
                }
            });
        }
    }
