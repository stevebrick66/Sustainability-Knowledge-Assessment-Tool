/**
 * FileName: Player.java
 *
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/03/25
 *
 * Description: This class creates a player that will have a name and their score.
 */
package jeop;

public class Player {
	
	/** Player's name */
	private String name;
	
	/** Players score */
	private int money;
	
	/**
	 * Constructor for a Player
	 * @param name Name of the Player
	 */
	public Player(String name) {
		this.name = name;
		this.money = 0;
	}
	
	/**
	 * Gets the Player's name
	 * @return Player's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add's a certain amount to a Player's score
	 * @param amount Money to add to a Player's score
	 */
	public void addMoney(int amount) {
		money += amount;
	}
	
	/**
	 * Gets the score of a Player
	 * @return Player's score
	 */
	public int getMoney() {
		return money;
	}

}
