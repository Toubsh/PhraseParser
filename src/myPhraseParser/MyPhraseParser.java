package myPhraseParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MyPhraseParser {

	private static final String DEUTSCH = "Deutsch:";
	private static final String ENGLISCH = "Englisch:";
	private static final String REGEX_PATTERN = "";

	private Scanner questioner = new Scanner(System.in);
	private ArrayList<String> myPhrases = new ArrayList<String>();
	public int decideNewline = 0;

	public void run() throws FileNotFoundException, IOException {

		fileInput();
		pairLookup();
		allPairs();
		csvWriting();

	}

	private void fileInput() throws FileNotFoundException, IOException {

		while (true) {

			System.out.println("Please input the name of the file:");

			parse(questioner.nextLine());

			System.out.println("Do you want to read another file? ('no' to quit)");
			String userNewFile = questioner.nextLine();

			if (userNewFile.equals("no"))
				break;
		}

	}

	public ArrayList<String> parse(String file) throws IOException {
		System.out.println("Where are your files located?");
		String userPath = questioner.nextLine();
		
		BufferedReader br = new BufferedReader(new FileReader(userPath + file + ".txt"));
		String myLine = br.readLine();

		while (myLine != null) {

			myLine = br.readLine();

			if (myLine == null) {

				br.close();

			} else if (myLine.contains(DEUTSCH) || myLine.contains(ENGLISCH)) {

				// TODO Regex verwenden
				myPhrases.add(myLine.replaceAll("^\\s+", ""));
				
			}

		}

		return myPhrases;

	}

	private void pairLookup() {

		while (true) {

			System.out.println("Would you like to search for a specific word to lookup a phrase? (y/n)");
			String myQuestion = questioner.nextLine();

			if (myQuestion.equals("n")) {

				break;

			} else if (myQuestion.equals("y")) {

				System.out.println("What word/character would you like to look up?");
				String userWordInput = questioner.next();

				for (int i = 0; i < myPhrases.size(); i++) {

					if (myPhrases.get(i).contains(userWordInput)) {

						System.out.println("Phrase found!");
						System.out.println(myPhrases.get(i));

						if (myPhrases.get(i).contains(DEUTSCH)) {

							int germanEngPair = i + 1;
							System.out.println(myPhrases.get(germanEngPair));

						} else if (myPhrases.get(i).contains(ENGLISCH)) {

							int englishGerPair = i - 1;
							System.out.println(myPhrases.get(englishGerPair));

						}

					}
				}
			}
		}

	}

	private void allPairs() {

		while (true) {

			System.out.println("Would you like to print out every pair that I found? (y/n)");
			String userAnswer = questioner.nextLine();

			if (userAnswer.equals("n")) {

				break;

			} else if (userAnswer.equals("y")) {

				for (int i = 0; i < myPhrases.size(); i++) {

					if (myPhrases.size() % 2 != 0) {

						System.out.println("Seems like you don't have an even pair of phrases");
						break;

					} else {

						System.out.println("Pair found!");
						System.out.println(myPhrases.get(i));
						i++;
						System.out.println(myPhrases.get(i));

					}
				}

				break;

			} else {

				System.out.println("Not a valid input!");

			}

			questioner.close();

		}

	}

	private void csvWriting() throws FileNotFoundException {
		System.out.println("Where would you like to save your file?");
		String userPath = questioner.nextLine();
		
		PrintWriter myCsv = new PrintWriter(new File(userPath + "myCsvPhrases.csv"));

		// myCsv.write(myLine.toString().replaceAll("^\\s+", ""));
		for (int i = 0; i < myPhrases.size(); i++) {
			myCsv.write(myPhrases.get(i).toString().replaceAll("^\\s+", ""));

			if (decideNewline % 2 == 0) {
				myCsv.write(", ");
			}

			decideNewline++;

			if (decideNewline % 2 == 0) {
				myCsv.write("\n");
			}

		}

		myCsv.close();

		System.out.println("A .csv file has been successfully saved to " + userPath);

	}
}
