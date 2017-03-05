package org.jcef;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class abc {
	 public static void main(String[] args) throws IOException {
		Support supp = new Support();
		String script = supp.getScript("./resources/script.js");
		System.out.print(script);
	 }
}
