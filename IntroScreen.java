package jeop;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * FileName: IntroScreen.java
 *
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/06/25
 *
 * Description: This is a GUI that will be the first screen a user sees to welcome them to the game and put in the player count with names.
 */
public class IntroScreen extends JFrame {
	/** Textfield to input player amount */
    private JTextField playerCountField;
    
    /** Field to write player names */
    private ArrayList<JTextField> nameFields = new ArrayList<>();
    
    /** Panel for names */
    private JPanel namesPanel;
    
    /** Button to enter the game board */
    private JButton startButton;
    
    private ArrayList<Player> players = new ArrayList();    
    
    /**
     * Constructor that creates intro screen 
     */
    public IntroScreen() {
        setTitle("Welcome!");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main container
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(new Color(0, 0, 150));

        // Title
        JLabel welcome = new JLabel("Welcome to Sustainability Jeopardy!");
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.BOLD, 48));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Amount of players
        JLabel countLabel = new JLabel("Number of Players (1–3):");
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerCountField = new JTextField(5);
        playerCountField.setMaximumSize(playerCountField.getPreferredSize());
        playerCountField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton confirmPlayersButton = new JButton("Confirm");
        confirmPlayersButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmPlayersButton.addActionListener(e -> {
        	if(buildNameInputs()) {
        		confirmPlayersButton.setEnabled(false);
        		confirmPlayersButton.setVisible(false);
        		playerCountField.setEnabled(false);
        	}
        });

        // Panel to hold dynamically added name fields
        namesPanel = new JPanel();
        namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
        namesPanel.setOpaque(false);
        
        // Start button
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setEnabled(false);

        startButton.addActionListener(e -> {
    		if(buildPlayersAndStart()) {
        		new MainGUI(new QuestionsAndAnswers().getQAs(),players,new ScoreBoard(players).getScoreboard());
    		}
        });

        // Add components
        main.add(Box.createVerticalStrut(60));
        main.add(welcome);
        main.add(Box.createVerticalStrut(50));
        main.add(countLabel);
        main.add(playerCountField);
        main.add(confirmPlayersButton);
        main.add(Box.createVerticalStrut(20));
        main.add(namesPanel);
        main.add(Box.createVerticalStrut(40));
        main.add(startButton);
        main.add(Box.createVerticalGlue());

        add(main);
        setVisible(true);

    }

    /**
     * returns boolean for successfully building names or not
     * @return
     */
    private boolean buildNameInputs() {
        String raw = playerCountField.getText().trim();
        int count;

        try {
            count = Integer.parseInt(raw);
            if (count < 1 || count > 3) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a number 1–3.");
            return false;
        }

        namesPanel.removeAll();
        nameFields.clear();

        for (int i = 1; i <= count; i++) {
            JLabel label = new JLabel("Player " + i + " Name:");
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 26));

            JTextField field = new JTextField(12);
            field.setMaximumSize(field.getPreferredSize());

            JPanel row = new JPanel();
            row.setOpaque(false);
            row.add(label);
            row.add(field);

            nameFields.add(field);
            namesPanel.add(row);
        }

        startButton.setEnabled(true);
        namesPanel.revalidate();
        namesPanel.repaint();
        return true;
    }
    
    /**
     * builds Players and Starts if all name fields are appropriate
     * @return
     */
    private boolean buildPlayersAndStart() {
        players = new ArrayList<>();

        for (JTextField field : nameFields) {
            String name = field.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All players must have names.");
                return false;
            }
            players.add(new Player(name));
        }
        dispose();
        return true;
    }
}
