package jeop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * FileName: QuestionsAndAnswers.java
 *
 * @authors Michael Matara, Steven Chand, Sharlene Camposeco Silvestre, Elianan Sileshe
 * Course: CISC 230
 * Date: 12/06/25
 *
 * Description: This is the file responsible for reading in the txt file in the project file and converting it to an ArrayList of String[] objects with two Strings question index 0 and answer index 1 and creating a method to return that ArrayList
 */
public class QuestionsAndAnswers {
	private ArrayList<String[]> qNa;
	
	private final String fileName = "trivia.txt";
	public QuestionsAndAnswers() {
		qNa = new ArrayList();
		try {
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNext()) {
				qNa.add(sc.nextLine().split(";"));
				
			}
			sc.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public ArrayList<String[]> getQAs(){
		return qNa;
	}
}
