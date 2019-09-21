import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Desktop;
import java.io.File;
import java.util.Scanner;

public class runner {
	
	public static void fun() throws IOException, InterruptedException{
		Process p;
		Scanner myObj = new Scanner(System.in);
		System.out.print("File directory : ");
		String dir, file, sourceDir = "src/main/java/com/abyeti/";
		dir = myObj.nextLine();
		System.out.print("File name : ");
		file = myObj.nextLine();
		String[] cmd = { "/bin/sh", "-c", "cp "+dir+file+" "+sourceDir+" && mvn test site && rm "+sourceDir+file};
		System.out.println(cmd[2]);
		p = Runtime.getRuntime().exec(cmd);
		BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		BufferedReader outPutReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String outPut = "";
		
		String line = outPutReader.readLine();
		do{
			if(line != null){
				outPut += line + "\n";
				line = outPutReader.readLine();
			}				
		}while(line != null);
		//String errorOutPut = "";
		
		String line1 = errorReader.readLine();
		do{
			if(line1 != null){
				outPut += line1 + "\n";
				line1 = errorReader.readLine();
			}
		}while(line1 != null);
		int val = p.waitFor();		
		System.out.println(outPut);
	}
	
	public static void main(String[] args)  {
		System.out.println("Initializing analysis");
		try {
			fun();
			File htmlFile = new File("target/site/findbugs.html");
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
