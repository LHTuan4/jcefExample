package org.jcef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Support {
	public static String getScript(String file) throws IOException {
	    StringBuilder builder = new StringBuilder();
	    try (InputStream input = new FileInputStream(file)) {
	        BufferedReader reader = new BufferedReader(
	            new InputStreamReader(input, "UTF-8"));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            builder.append(line);
	        }
	    }
	    return builder.toString();
	}
}
