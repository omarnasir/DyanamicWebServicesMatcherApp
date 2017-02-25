package wsdlcomparator;

import java.io.File;

public class Main {
	public static void main(String[] args) {

		File folder = new File("src/resources/Correct");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".wsdl")) {
				boolean result = DomParser.domParserMethod(listOfFiles[i]);
				System.out.println("File " + listOfFiles[i].getName() + "; Result: " + result);
			}
		}
	}
}
