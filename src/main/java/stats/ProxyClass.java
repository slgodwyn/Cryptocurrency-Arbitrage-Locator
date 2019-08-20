package stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProxyClass {
	File proxy_list = new File("Proxy_List.txt");
	Random rand;// = new Random();
	
	public String getFileContents() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Proxy_List.txt"));
		
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    return everything;
		} finally {
		    br.close();
		}
	}
	
	public String getRandomAddressAndPort() throws IOException {
		rand = new Random();
		/*
		//System.out.println(new File("Proxy_List.txt").getAbsolutePath());
	    
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Proxy_List.txt");
		System.out.println(inputStream.toString());
		
		String resourceFile = "/ArbitrageLocatorWebApp/src/main/resources/Proxy_List.txt";
	    
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classloader.getResourceAsStream("Proxy_List.txt");
		System.out.println(stream.toString());
	    
		/*
	    InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceFile);
	 
	    //if (resourceStream != null) {
	        BufferedReader resReader = new BufferedReader(new InputStreamReader(resourceStream));
	        System.out.println(resReader.lines().collect(Collectors.joining()));
	    //}
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		*/
		
		File file = new File("C:\\Users\\Viktor\\eclipse-workspace_4_j2ee\\ArbitrageLocatorWebApp\\src\\main\\resources\\Proxy_List.txt");
		
		String address_plus_port = Files.readAllLines(Paths.get(file.getAbsolutePath())).get(rand.nextInt(300 ));
		return address_plus_port;
	}
}
