package jeop;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * FileName: ScoreBoard.java
 *
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/06/25
 *
 * Description: This is a class that extends JPanel
 */
public class ScoreBoard extends JPanel {
    private HashMap<Player, JLabel> scoreLabels = new HashMap<>();//map of players matched to their labels in the scoreboard
    /**
     * Constructor for Scoreboard, creates the scoreboard
     * @param players
     */
    public ScoreBoard(List<Player> players) {
    	setPreferredSize(new Dimension(180,0));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel main = new JPanel(new GridLayout(players.size(), 1, 0, 0));
        main.setBackground(Color.BLACK);

        for (Player p : players) {
            main.add(buildPodium(p));
        }
        add(main, BorderLayout.CENTER);
    }
    /**
     * creates each Panel for each player depending on amount of players
     * @param player
     * @return
     */
    private JPanel buildPodium(Player player) {
        JPanel podium = new JPanel(new GridBagLayout());
        podium.setBackground(new Color(0, 0, 120));
        podium.setBorder(BorderFactory.createLineBorder(Color.white, 4));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel score = new JLabel("$" + player.getMoney());
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Arial", Font.BOLD, 40));
        scoreLabels.put(player,score);

        JLabel name = new JLabel(player.getName());
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Arial", Font.BOLD, 32));

        podium.add(score, gbc);
        podium.add(name, gbc);

        return podium;
    }
    /**
     * refreshScores to repaint the scoreboard when called if the values of the scores of the players change(like when answering a question)
     */
    public void refreshScores() {
    	for (Player p : scoreLabels.keySet()) {
    		scoreLabels.get(p).setText("$" + p.getMoney());
    	}
    	revalidate();
    	repaint();
    }
    /**
     * returns the Scoreboard panel of the whole class, of type ScoreBoard
     * @return
     */
    public ScoreBoard getScoreboard() {
        return this;
    }
}
