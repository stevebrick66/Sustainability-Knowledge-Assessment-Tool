package jeop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * FileName: QAPanels.java
 *
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/06/25
 *
 * Description: Callback method used between mainGUI and QAPanels to decide the player's turn based on whether the answer in QAPanels for the current player was right or not
 */
public class QAPanels{
	Font qa = new Font("Arial",Font.BOLD, 40);
	
	private String question;
	private String yourAnswer = "answer not received yet";
	private String answer;
	private int xSize;
	private int ySize;
	private int score;
	private ScoreBoard sb;
	private AnswerListener listener;
	private Player p;
	
	/**
	 * sets all instance variables in QAPanels
	 * @param question
	 * @param answer
	 * @param xSize
	 * @param ySize
	 * @param p
	 * @param score
	 * @param sb
	 * @param listener
	 */
	public QAPanels(String question, String answer, int xSize, int ySize, Player p, int score, ScoreBoard sb, AnswerListener listener) {
		this.question = question;
		this.answer = answer;
		this.xSize = xSize;
		this.ySize = ySize;
		this.p = p;
		this.sb = sb;
		this.score = score;
		this.listener = listener;
	}
	/**
	 * gets the JFrame of the parent class (MainGUI) and creates the Question dialog from it then returns it
	 * @param jf
	 * @return
	 */
	public JDialog getQDialog(JFrame jf) {
	    JDialog qDialog = new JDialog(jf, "title", true);
	    qDialog.setUndecorated(true);

	    JPanel holdAll = new JPanel(new BorderLayout());
	    holdAll.setBorder(BorderFactory.createLineBorder(Color.BLACK,xSize/75));

	    JTextArea qPrompt = new JTextArea(question, 5, 5);
	    qPrompt.setFont(qa);
	    qPrompt.setMargin(MainGUI.panelInsets(3*xSize/5, 3*ySize/5));
	    qPrompt.setBackground(Color.blue);
	    qPrompt.setForeground(Color.YELLOW);
	    qPrompt.setLineWrap(true);
	    qPrompt.setWrapStyleWord(true);
	    qPrompt.setEditable(false);


	    JLabel aPrompt = new JLabel("What is: ");
	    aPrompt.setFont(qa);
	    aPrompt.setForeground(Color.white);

	    JTextField playerAns = new JTextField(20);
	    playerAns.setBackground(Color.blue);
	    playerAns.setForeground(Color.WHITE);
	    playerAns.addActionListener(new ActionListener() {
	    	
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		yourAnswer = playerAns.getText();
	    		
	    		qDialog.dispose();
	    		JDialog jd = getADialog(jf,yourAnswer, listener);
	    		jd.setVisible(true);
	    	}
	    });
	    JPanel answerPanel = new JPanel();
	    answerPanel.add(aPrompt);
	    answerPanel.add(playerAns);
	    answerPanel.setBackground(Color.blue);
	    answerPanel.setBorder(BorderFactory.createMatteBorder(xSize/75, 0, 0, 0, Color.black));
	    
	    
	    holdAll.add(qPrompt, BorderLayout.CENTER);
	    holdAll.add(answerPanel, BorderLayout.SOUTH);

	    qDialog.add(holdAll);

	    qDialog.setPreferredSize(new Dimension(3 * xSize / 5, 3 * ySize / 5));
	    qDialog.pack();

	    qDialog.setLocationRelativeTo(jf);
	    return qDialog;
	}
	/**
	 * Creates answer dialogue and designs it visually, then returns it
	 * @param jf
	 * @param yourAnswer
	 * @return
	 */
	public JDialog getADialog(JFrame jf, String yourAnswer, AnswerListener listener) {
		this.yourAnswer = yourAnswer;
		JDialog aDialog = new JDialog(jf, "title", true);
	    aDialog.setUndecorated(true);
	    JPanel holdAll = new JPanel(new BorderLayout());
	    holdAll.setBorder(BorderFactory.createLineBorder(Color.BLACK,xSize/75));
	    JTextArea answerResults = null;
	    boolean correct = yourAnswer.toLowerCase().equals(answer.toLowerCase());
	    if(correct){
		   answerResults = new JTextArea("Correct! It is: " + answer, 5, 5);
		   p.addMoney(score);
		   sb.refreshScores();
	    }
	    else {
	    	answerResults = new JTextArea("Incorrect! It is: " + answer, 5, 5);
			p.addMoney(-score);
			sb.refreshScores();
	    }
	    sb.refreshScores();
	    listener.onAnswered(p, correct, score);
	    answerResults.setFont(qa);
	    answerResults.setMargin(MainGUI.panelInsets(3*xSize/5, 3*ySize/5));
	    answerResults.setBackground(Color.blue);
	    answerResults.setForeground(Color.YELLOW);
	    
	    answerResults.setLineWrap(true);
	    answerResults.setWrapStyleWord(true);
	    answerResults.setEditable(false);
	    
	    holdAll.add(answerResults,BorderLayout.CENTER);

	    JTextArea guess = new JTextArea("You put: " + yourAnswer, 5, 10);    
	    guess.setFont(qa);
	    guess.setMargin(MainGUI.panelInsets(3*xSize/5, 3*ySize/5));
	    guess.setBackground(Color.blue);
	    guess.setForeground(Color.YELLOW);
	    
	    guess.setLineWrap(true);
	    guess.setWrapStyleWord(true);
	    guess.setEditable(false);
	    
	    JPanel guessPanel = new JPanel(new GridBagLayout());
	    guessPanel.add(guess);
	    guessPanel.setBackground(Color.blue);
	    guessPanel.setBorder(BorderFactory.createMatteBorder(xSize/75, 0, 0, 0, Color.black));
	    
	    
	    
	    
	    
	    JButton closeButton = new JButton("Close");
	    closeButton.addActionListener(e -> aDialog.dispose());
	    closeButton.setForeground(Color.black);	
	    closeButton.setBackground(Color.white);	

	    
	    	    
	    
	    

	    JPanel closePanel = new JPanel();
	    closePanel.setBorder(BorderFactory.createMatteBorder(xSize/150, 0, 0, 0, Color.black));
	    closePanel.setBackground(Color.blue);
	    closePanel.add(closeButton);
	    
	    JPanel guessClosePanel = new JPanel(new BorderLayout());
	    guessClosePanel.add(closePanel,BorderLayout.SOUTH);
	    guessClosePanel.add(guessPanel,BorderLayout.CENTER);
	    
	    holdAll.add(guessClosePanel,BorderLayout.SOUTH);
	    
	    aDialog.add(holdAll);
	    aDialog.setPreferredSize(new Dimension(3 * xSize / 5, 3 * ySize / 5));
	    aDialog.pack();

	    aDialog.setLocationRelativeTo(jf);
	    
	    return aDialog;
	}

}
