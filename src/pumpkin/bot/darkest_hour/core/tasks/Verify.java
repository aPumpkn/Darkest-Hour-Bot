package pumpkin.bot.darkest_hour.core.tasks;

import java.util.Random;

// Verification system for the bot. Uses human logic.
public class Verify {
	
	/* Java Utils */
	private Random random = new Random(); // Random number generator used by the constructor.
	
	/* Questions */
  // to keep the verification secure, these questions and answwers will be censored.
  // Each q and a that have a matching number are a matching set.
	private String q0 = "censored";
	private String q1 = "censored";
	private String q2 = "censored";
  private String q3... // continues sequentially up to q29
	
	/* Answers */
	private String a0 = "censored";
  private String a1 = "censored";
  private String a2 = "censored";
  private String a3... // continues sequentially up to a29
	
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
				
			case 5:
				question = q5;
				answer = a5;
				break;
				
			case 6:
				question = q6;
				answer = a6;
				break;
				
			case 7:
				question = q7;
				answer = a7;
				break;
				
			case 8:
				question = q8;
				answer = a8;
				break;
				
			case 9:
				question = q9;
				answer = a9;
				break;
				
			case 10:
				question = q10;
				answer = a10;
				break;
				
			case 11:
				question = q11;
				answer = a11;
				break;
				
			case 12:
				question = q12;
				answer = a12;
				break;
				
			case 13:
				question = q13;
				answer = a13;
				break;
				
			case 14:
				question = q14;
				answer = a14;
				break;
				
			case 15:
				question = q15;
				answer = a15;
				break;
				
			case 16:
				question = q16;
				answer = a16;
				break;
				
			case 17:
				question = q17;
				answer = a17;
				break;
				
			case 18:
				question = q18;
				answer = a18;
				break;
				
			case 19:
				question = q19;
				answer = a19;
				break;
				
			case 20:
				question = q20;
				answer = a20;
				break;
				
			case 21:
				question = q21;
				answer = a21;
				break;
				
			case 22:
				question = q22;
				answer = a22;
				break;
				
			case 23:
				question = q23;
				answer = a23;
				break;
				
			case 24:
				question = q24;
				answer = a24;
				break;
				
			case 25:
				question = q25;
				answer = a25;
				break;
				
			case 26:
				question = q26;
				answer = a26;
				break;
				
			case 27:
				question = q27;
				answer = a27;
				break;
				
			case 28:
				question = q28;
				answer = a28;
				break;
				
			case 29:
				question = q29;
				answer = a29;
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
		
		if (response.trim().replaceAll(" +", " ").replaceAll("[^A-z ]", "").toLowerCase().equals(answer)) return true;
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
