package pumpkin.darkest_dawn.core.tasks;

import java.util.Random;

// Verification system for the bot. Uses human logic.
public class Verify {
	
	/* Java Utils */
	private Random random = new Random(); // Random number generator used by the constructor.
	
	/* Questions (Goes up to 30 questions and answers but is limited here
	 * to keep things shortened. Additionally, they are censored to keep
	 * from others abusing the information found here on the server.)
	 */
	private String q0 = "censored";
	private String q1 = "censored";
	private String q2 = "censored";
	private String q3 = "censored";
	private String q4 = "censored";
	
	/* Answers */
	private String a0 = "censored";
	private String a1 = "censored";
	private String a2 = "censored";
	private String a3 = "censored";
	private String a4 = "censored";
	
	/* Assigned Fields */
	private String question; // The randomly selected question
	private String answer; // The randomly selected answer
	
	
	
	/* Constructor for this class. Uses the Random object
	 * to generate a random number from 0 to 29, then assigns
	 * the 'question' and 'answer' fields to the value of the
	 * String name corresponding to this random number.
	 */
	public Verify() {
		
		switch (random.nextInt(30)) {
			
			case 0:
				question = q0;
				answer = a0;
				break;
				
			case 1:
				question = q1;
				answer = a1;
				break;
				
			case 2:
				question = q2;
				answer = a2;
				break;
				
			case 3:
				question = q3;
				answer = a3;
				break;
				
			case 4:
				question = q4;
				answer = a4;
				break;
			
		}
		
	}

	/* Compares two strings then returns a boolean. Before
	 * checking, the response is adjusted to remove any
	 * unnecessary characters. Returns true if the 
	 * resulting Strings match.
	 * 
	 * Parameters:
	 * response - Answer given by the user.
	 * answer - The expected response.
	 */ 
	public boolean check(String response, String answer) {
		
		if (response.trim().replaceAll("[^A-z +]", "").toLowerCase().equals(answer)) return true;
		else return false;
		
	}

	/* Retrieves the randomly selected question. */
	public String getQuestion() {
		
		return question;
		
	}

	/* Retrieves the answer for this question. */
	public String getAnswer() {
		
		return answer;
		
	}
	
}
