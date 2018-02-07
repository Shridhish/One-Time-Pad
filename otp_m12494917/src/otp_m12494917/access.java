package otp_m12494917;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class access {
	public String readFromFile(String path) {

		StringBuffer plainText = new StringBuffer();
		try {

			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			char[] characterArray = new char[1024];
			int length=0;

			while ((length = fileReader.read(characterArray)) > 0) {
				plainText.append(characterArray, 0, length);
			}
			fileReader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return plainText.toString();

		
	}
	

	
}
