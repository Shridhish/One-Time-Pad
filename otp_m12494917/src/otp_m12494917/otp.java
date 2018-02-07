package otp_m12494917;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class otp {

	public String Enc(String text,String key) {

		String plainText="";
		byte[] bytes = text.getBytes();
		StringBuilder binary = new StringBuilder();
		StringBuilder sb = new StringBuilder();



		/* Converting the text "bear" to its binary digits and storing in a variable called plainText*/
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}

		}


		plainText=binary.toString();

		//System.out.println("plainText :" +plainText);

		String keyWord="";
		long start_time = System.nanoTime();

		for(int i = 0; i <= key.length() - 8; i+=8)
		{
			int letter = Integer.parseInt(key.substring(i, i+8), 2);
			keyWord =keyWord+ (char) letter;
		}  


		/*Modular operation of the two binary digits and writing the output to the ciphertext.txt file*/
		if(text.length()==keyWord.length()) {
			for(int i = 0; i < plainText.length(); i++) {
				sb.append((plainText.charAt(i) ^ key.charAt(i)));
			}
		} 
		else {
			System.out.println("“error: length is incorrect!");
			System.exit(1);

		}
		long end_time = System.nanoTime();

		String cipherText = sb.toString();

		String cipher="";


		/*print cipher text to the terminal */

		for(int i = 0; i <= cipherText.length() - 8; i+=8)
		{
			int letter = Integer.parseInt(cipherText.substring(i, i+8), 2);
			cipher = cipher+ (char) letter;
		}  
		System.out.println("The cipher we get after modular operation: " +cipher);


		/* writing to the cipher.txt file*/
		try {
			PrintWriter out = new PrintWriter( "C:\\Users\\Shridhish\\eclipse-workspace\\otp_m12494917\\data\\ciphertext.txt" );
			out.println( cipherText );
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


		double diff=(end_time - start_time) / 1e6;

		System.out.println("Encryption Run time in ms: "+diff);
		System.out.println("********************************");

		return plainText;

	}
	public String Dec(String key, String cipher) {

		StringBuilder sb=new StringBuilder();

		String keyWord="";
		String cipherText="";
		for(int i = 0; i <= key.length() - 8; i+=8)
		{
			int letter = Integer.parseInt(key.substring(i, i+8), 2);
			keyWord = keyWord+ (char) letter;
		}

		for(int i = 0; i <= cipher.length() - 8; i+=8)
		{
			int letter = Integer.parseInt(cipher.substring(i, i+8), 2);
			cipherText = cipherText+ (char) letter;
		}


		if(keyWord.length()==cipherText.length()) {
			for(int i = 0; i < key.length(); i++) {
				sb.append((key.charAt(i) ^ cipher.charAt(i)));
			}
		} 
		else {
			System.out.println("“error: length is incorrect!");
			System.exit(1);

		}
		String result=sb.toString();

		return result;
	}

	/* This function writes the word to the text file*/
	public void writeToFile(String cipherText,String key) {

		String originalText="";
		StringBuilder sb=new StringBuilder();

		for(int i = 0; i < key.length(); i++) {
			sb.append((cipherText.charAt(i) ^ key.charAt(i)));
		}

		String word=sb.toString();

		/* Converting the binary digits to the original String*/
		for(int i = 0; i <= word.length() - 8; i+=8)
		{
			int letter = Integer.parseInt(word.substring(i, i+8), 2);
			originalText =originalText+ (char) letter;
		}  


		/* Writing the original String to result.txt*/
		try {
			PrintWriter out = new PrintWriter( "C:\\Users\\Shridhish\\eclipse-workspace\\otp_m12494917\\data\\result.txt" );
			out.println( originalText );
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String KeyGen(int lambda) {

		Random rand=new Random();
		String secretKey="";

		/*Randomly generating values of 0s and 1s and storing it in a variable secretKey*/
		for(int i=0;i<lambda;i++) {

			secretKey=secretKey+rand.nextInt(2);

		}


		/* Writing the generated 0s and 1s to the newKey.txt*/
		try {
			PrintWriter out = new PrintWriter( "C:\\Users\\Shridhish\\eclipse-workspace\\otp_m12494917\\data\\newKey.txt" );
			out.println( secretKey );
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return secretKey;

	}

	/* To calculate the frequency of the random values*/
	public void keygenFrequency(int lamda) {

		Random rand=new Random();
		HashMap<String,Integer> map=new HashMap<>();
		String secretKey="";
		
		/*key generation for 5000 times */
		for(int i=0;i<5000;i++) {

			secretKey=rand.nextInt(2)+"";

			if(!map.containsKey(secretKey) ) {
				map.put(secretKey, 1);
			}
			else {
				map.put(secretKey, map.get(secretKey)+1);
			}

		}

		/* These loops are used to check the uniform distribution of bits*/
		for(String s:map.keySet()) {
			System.out.println("keys :" +s);
		}

		for(int i:map.values()) {
			System.out.println("valueFrequency :" +i);
		}



	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String sText_fileName="C:\\Users\\Shridhish\\eclipse-workspace\\otp_m12494917\\data\\plaintext.txt";
		String key_fileName="C:\\Users\\Shridhish\\eclipse-workspace\\otp_m12494917\\data\\key.txt";

		/* object creation*/
		otp obj1=new otp();
		access obj2=new access();

		String text="";
		String key="";
		String cipherText="";
		text=obj2.readFromFile(sText_fileName);
		System.out.println("text: " +text);

		key=obj2.readFromFile(key_fileName);
		System.out.println("key:" +key);

		obj1.Enc(text, key);

		cipherText=obj2.readFromFile("C:\\Users\\Shridhish\\eclipse-workspace\\otp_m12494917\\data\\ciphertext.txt");
		//System.out.println("Cipher Text: " +cipherText);



		obj1.Dec(key, cipherText);

		obj1.writeToFile(cipherText, key);

		System.out.print("Enter a secret parameter value between 1 and 128 inclusive :");
		Scanner read=new Scanner(System.in);
		int input=read.nextInt();
		read.close();



		String secretKey=obj1.KeyGen(input);
		System.out.println("The randomly generated secret key :" +secretKey);

		/* Checking the frequency distribution by giving security parameter lambda=3*/
		obj1.keygenFrequency(3);









	}

}
