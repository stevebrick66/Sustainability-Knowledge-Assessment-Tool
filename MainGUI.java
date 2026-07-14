
package jeop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * MainGui where the main portion of the program runs after IntroScreen runs and creates scoreboard then calls mainGUI
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/06/25
 */
public class MainGUI extends JFrame{
	private int turn = 0;//variable for players turn
	private static final Font catFont = new Font("Arial", Font.BOLD, 18);//font for category Names
	private static final Font moneyFont = new Font("Sansseriff", Font.BOLD, 76);//font for score boxes
	private static final double MARGX_PROP = .011;//borderMargins of frame for top and bottom
	private static final double MARGY_PROP = .01;//borderMargins of frame for left and right

	private int xSize; // xSize of JFrame
	private int ySize; // ySize of JFrame
	private int gridMargins; //gridMargins between all components inside JFrame
	private ArrayList<String[]> qNa; //ArrayList for trivia txt file of questions and answers read into ArrayList of String arrays(question index 0, answer index 1)
	private ArrayList<Player> players;//ArrayList for players from Player class, created in IntroScreen and passed in in MainGUI constructor
	private ArrayList<JButton> allQuestions = new ArrayList(); //ArrayList of all question buttons(used everytime question button is activated to check if all buttons are disabled in order to later end the game).

	/**
	 * Where all of the game(aside from the introScreen) is run.
	 * qNa is the trivia txt file read in, players is ArrayList of Players in the game, ScoreBoard is the scoreboard created during IntroScreen
	 * @param qNa
	 * @param players
	 * @param scoreboard
	 */
	public MainGUI(ArrayList<String[]> qNa, ArrayList<Player> players,ScoreBoard scoreboard){
		this.qNa = qNa;
		this.players = players;
		System.out.println(players.size());
		xSize = 1100;
		ySize = 1100;
		gridMargins = xSize/75;
		setSize(xSize,ySize);
		setLocation(400,0);
		setTitle("not Jeopardy!");
		JPanel sbContent = new JPanel(new BorderLayout(gridMargins,0));//scoreboard and content panel
		JPanel content = new JPanel(new BorderLayout());//content panel with the category boxes and score boxes
		content.setBackground(Color.black);
		sbContent.setBackground(Color.BLACK);

		JPanel categories = createCategoryBoxes();
		JPanel qPanels = createScoreBoxes(scoreboard);
		reSize(sbContent,categories,qPanels);
		content.add(categories, BorderLayout.NORTH);
		content.add(qPanels, BorderLayout.CENTER);
		sbContent.add(content,BorderLayout.CENTER);
		sbContent.add(scoreboard, BorderLayout.WEST);
		addComponentListener(new ComponentAdapter() {//Listens to the JFrame component in order to resize components in it whenever the main window is resized
			@Override
			public void componentResized(ComponentEvent e) {
				updateScaledBorder(sbContent);//resizes the main panel the contentPane is set to for the duration of the program(right until the end screen)
				reSize(sbContent,categories,qPanels);//resizes components within the main panel
			}
		});
		setContentPane(sbContent);
		setVisible(true);
	}
	/**
	 * resizes the mainPanel and its components
	 * @param mainPanel
	 * @param categories
	 * @param scoreBoxes
	 */
	private void reSize(JPanel mainPanel, JPanel categories, JPanel scoreBoxes) {
		xSize = mainPanel.getWidth();//for when the window is resized immediately gets the new with and height separate from the ones initialized when the mainGUI is first visible
		ySize = mainPanel.getHeight();
		gridMargins = xSize/75; //recalculates margins based on the new x and y
		updateScaledBorder(mainPanel); // same for the border
	}
	/**
	 * creates category boxes with names of category
	 * @return
	 */
	private JPanel createCategoryBoxes() {
		JPanel categories = new JPanel(new GridLayout(1,5,gridMargins,gridMargins));
		categories.setBackground(Color.black);

		String[] categoryNames = {
				"Energy\nPollinator Path",
				"Biking\nFood",
				"Water\nAcademics",
				"Reuse\nRecycling",
				"Organics Recycling\nSpecialized Recycling"
		};//Categories are taken from the trivia pdf in the final project, there are two questions for every category, engagement category is ignored and the other 10 are combined to make five categories with 4 questions each

		for (String name : categoryNames) {

			JPanel category = new JPanel(new GridBagLayout());
			category.setBackground(Color.BLUE);

			JTextArea catName = new JTextArea(name);
			catName.setLineWrap(true);
			catName.setWrapStyleWord(true);
			catName.setBorder(null);
			catName.setBackground(Color.BLUE);
			catName.setForeground(Color.WHITE);
			catName.setFont(catFont);
			catName.setAlignmentX(Component.CENTER_ALIGNMENT);
			catName.setAlignmentY(Component.CENTER_ALIGNMENT);
			category.add(catName);
			categories.add(category);
		}

		categories.setBorder(BorderFactory.createMatteBorder(0,0,gridMargins,0,Color.BLACK));
		return categories;
	}
	//creates insets for margins
	public static Insets panelInsets(int panelX, int panelY){
		int top = (int) (panelY * .04);
		int bottom = top;
		int left = (int) (panelX * .044);
		int right = left;
		return new Insets(top, left, bottom, right);
	}
	//creates scoreBoxes with score, and score button(that creates QAPanels everytime one of the question buttons is pushed
	public JPanel createScoreBoxes(ScoreBoard sb) {
		JPanel qPanels = new JPanel(new GridLayout(4,5,gridMargins,gridMargins));
		qPanels.setBackground(Color.BLACK);
		for (int i = 0; i < 20; i++) {
			final int index = i;//created to use current i index
			JPanel buttonScorePanel = new JPanel();
			JButton buttonScore = new JButton("Question: ");
			buttonScore.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					int val = (100 * ((index / 5) + 1));
					JDialog jd = new QAPanels(qNa.get(index)[0], qNa.get(index)[1], xSize, ySize, getPlayer(), val,sb,(player, correct, amount) -> { // <-- callback
						if (correct) {
						} else {
							turn = (turn + 1) % players.size();
						}
					}).getQDialog(MainGUI.this);
					jd.setVisible(true);
					buttonScore.setEnabled(false);
					buttonScore.setVisible(false);
					int count = 0;
					for(JButton button : allQuestions) {
						if(button.isEnabled()) {
							count++;
						}
					}
					if(count == 0) {
						getContentPane().removeAll();
						JPanel winScreen = new JPanel(new BorderLayout());
						winScreen.setBackground(Color.BLUE);
						winScreen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20));
						// Find player with highest score
						Player highestScore = players.get(0);
						for (Player p : players) {
							if (highestScore.getMoney() < p.getMoney()) {
								highestScore = p;
							}
						}
						JTextArea winLabel = new JTextArea(highestScore.getName() + " is the winner!",2,14);
						winLabel.setFont(catFont);
						winLabel.setForeground(Color.YELLOW);
						winLabel.setBackground(Color.BLUE);
						winLabel.setEditable(false);
						winLabel.setFocusable(false);
						winLabel.setOpaque(true);
						winLabel.setLineWrap(true);
						winLabel.setWrapStyleWord(true);
						winLabel.setMargin(panelInsets(3 * xSize / 5, 3 * ySize / 5));
						winLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						winLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

						winScreen.add(winLabel, BorderLayout.CENTER);
						winLabel.addComponentListener(new ComponentAdapter() {//for any resizing of the window when at the end screen
							@Override
							public void componentResized(ComponentEvent e) {
								int newFontSize = Math.min(winLabel.getWidth() / 10, winLabel.getHeight() / 2);
								winLabel.setFont(new Font("SansSerif", Font.BOLD, newFontSize));//make font scale to window
							}
						});
						JButton tryAgainButton = new JButton("Restart");
						tryAgainButton.addActionListener(tAB-> {
							dispose();
							new IntroScreen();

						});
						JButton closeButton = new JButton("Exit");
						closeButton.addActionListener(cB-> {
							dispose();
						});
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.gridx = GridBagConstraints.RELATIVE;
						gbc.gridy = 0;
						gbc.insets = new Insets(0, 15, 0, 15);
						gbc.anchor = GridBagConstraints.CENTER;
						JPanel options = new JPanel(new GridLayout(1,2,2,0));
						options.setBorder(BorderFactory.createLineBorder(Color.black,gridMargins/3));
						JPanel tryAgain = new JPanel(new GridBagLayout());
						tryAgain.setBorder(BorderFactory.createLineBorder(Color.white, 1));
						JPanel close = new JPanel(new GridBagLayout());
						close.setBorder(BorderFactory.createLineBorder(Color.white, gridMargins/3));
						JLabel tryAgainLabel = new JLabel("Try again?");
						tryAgainLabel.setBackground(Color.blue);
						JLabel closeLabel = new JLabel("Exit");
						tryAgainLabel.setFont(moneyFont);
						closeLabel.setFont(moneyFont);
						closeLabel.setBackground(Color.blue);
						tryAgain.add(tryAgainLabel,gbc);
						tryAgain.add(tryAgainButton, gbc);
						close.add(closeLabel,gbc);
						close.add(closeButton, gbc);
						options.add(tryAgain);
						options.add(close);
						winScreen.add(options, BorderLayout.SOUTH);
						updateScaledBorder(winScreen);
						setContentPane(winScreen);
						revalidate();
						repaint();
					}
				}
			});
			allQuestions.add(buttonScore);//everytime button is created and assigned to one of the scorebox grid it is also added to allQuestions arrayList later used to track when all questions answered and buttons disabled -> end screen
			buttonScore.setForeground(Color.YELLOW);
			buttonScore.setBackground(Color.BLUE);
			JPanel buttonPanel = new JPanel(new BorderLayout());
			buttonPanel.setBorder(new EmptyBorder(panelInsets(xSize,ySize)));
			JPanel scoreButton = new JPanel(new BorderLayout());
			JPanel score = new JPanel(new GridBagLayout());
			score.setBackground(Color.BLUE);

			JLabel value = new JLabel("$ " + (100 * ((i / 5) + 1)));
			value.setFont(moneyFont);
			value.setHorizontalAlignment(JLabel.CENTER);

			value.setBorder(BorderFactory.createEmptyBorder());
			value.setForeground(Color.YELLOW);

			score.add(value);
			scoreButton.add(score, BorderLayout.CENTER);
			buttonScorePanel.add(buttonScore);
			buttonScorePanel.setBackground(Color.BLUE);
			scoreButton.add(buttonScorePanel, BorderLayout.SOUTH);
			qPanels.add(scoreButton);
		}
		return qPanels;
	}
	/**
	 * returns current player whose turn it is
	 * @return
	 */
	private Player getPlayer() {
		return players.get(turn);
	}
	/**
	 * creates border for mainPanel of frame
	 * @param c
	 */
	private void updateScaledBorder(JComponent c) {
		int top = (int)(ySize * MARGY_PROP);
		int left = (int)(xSize * MARGX_PROP);
		int bottom = top;
		int right = left;

		c.setBorder(new EmptyBorder(top, left, bottom, right));
		c.revalidate();
	}

}

