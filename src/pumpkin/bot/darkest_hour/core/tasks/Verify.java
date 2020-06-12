package pumpkin.darkest_dawn.core.tasks;

import java.util.Random;

// Verification system for the bot. Uses human logic.
public class Verify {
	
	/* Java Utils */
	private Random random = new Random(); // Random number generator used by the constructor.
	
	/* Questions */
	private String q0 = "What drink comes from a cow?";
	private String q1 = "What is the device you use to type in a computer?";
	private String q2 = "What is the name of this server?";
	private String q3 = "What is the fifth appendage of your hand that isn't technically a finger?";
	private String q4 = "What part of the arm is used to grab hold of things?";
	private String q5 = "What do bee's make?";
	private String q6 = "What are trees made out of?";
	private String q7 = "Red and blue make what color?";
	private String q8 = "What's the color of the ocean?";
	private String q9 = "What do we breathe?";
	private String q10 = "What animal goes moo?";
	private String q11 = "What's the color of the grass?";
	private String q12 = "What is the name for our planet?";
	private String q13 = "What animal goes meow?";
	private String q14 = "What animal goes bark?";
	private String q15 = "What is the color of a cherry?";
	private String q16 = "What color is a cloud?";
	private String q17 = "What is the color of snow?";
	private String q18 = "How many legs does a spider have?";
	private String q19 = "What color is the sun?";
	private String q20 = "What color is the inside of a watermelon?"; // TODO: change this shit yo
	private String q21 = "What color is dirt?";
	private String q22 = "What color is paper?";
	private String q23 = "What removes pencil marks from paper?";
	private String q24 = "What do you pour in cereal?";
	private String q25 = "What drink do you dip cookies in?";
	private String q26 = "What color is a carrot?";
	private String q27 = "What color is a banana?";
	private String q28 = "What color is a leaf during spring?";
	private String q29 = "What is a baby dog called?";
	
	/* Answers */
	private String a0 = "milk";
	private String a1 = "keyboard";
	private String a2 = "breaking dawn";
	private String a3 = "thumb";
	private String a4 = "hand";
	private String a5 = "honey";
	private String a6 = "wood";
	private String a7 = "purple";
	private String a8 = "blue";
	private String a9 = "air";
	private String a10 = "cow";
	private String a11 = "green";
	private String a12 = "earth";
	private String a13 = "cat";
	private String a14 = "dog";
	private String a15 = "red";
	private String a16 = "white";
	private String a17 = "white";
	private String a18 = "eight";
	private String a19 = "yellow";
	private String a20 = "red";
	private String a21 = "brown";
	private String a22 = "white";
	private String a23 = "eraser";
	private String a24 = "milk";
	private String a25 = "milk";
	private String a26 = "orange";
	private String a27 = "yellow";
	private String a28 = "green";
	private String a29 = "puppy";
	
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
