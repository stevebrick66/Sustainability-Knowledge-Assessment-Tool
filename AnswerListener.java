package jeop;
/**
 * FileName: AnswerListener.java
 *
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/06/25
 *
 * Description: Callback method used between mainGUI and QAPanels to decide the player's turn based on whether the answer in QAPanels for the current player was right or not
 */
public interface AnswerListener {
    void onAnswered(Player player, boolean correct, int score);
}
