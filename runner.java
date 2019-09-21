import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Desktop;
import java.io.File;
import java.util.Scanner;

public class runner {
	
	public static void fun() throws IOException, InterruptedException{
		Scanner myObj = new Scanner(System.in);
		System.out.print("File directory with leading slash : ");
		String dir, file, sourceDir = "src/main/java/com/abyeti/";
		dir = myObj.nextLine();
		System.out.print("File name : ");
		file = myObj.nextLine();
		String[] copyFile = { "/bin/sh", "-c", "cp "+dir+file+" "+sourceDir};
		String[] mvnBuild = { "/bin/sh", "-c","mvn test site"};
		String[] deleteFile = { "/bin/sh", "-c","rm "+sourceDir+file};
		System.out.println("Copying File");
		runCmd(copyFile);
		System.out.println("File copied\n");
		System.out.println("Building maven project. This may take a few minutes...");
		runCmd(mvnBuild);
		System.out.println("Project build successfully\n");
		System.out.println("Deleting File");
		runCmd(deleteFile);
		System.out.println("File Deleted\n");
	}

	public static void runCmd(String[] cmd) throws IOException, InterruptedException{
		Process p;
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
		String errorOutPut = "";
		
		String line1 = errorReader.readLine();
		do{
			if(line1 != null){
				errorOutPut += line1 + "\n";
				line1 = errorReader.readLine();
			}
		}while(line1 != null);
		int val = p.waitFor();	
		System.out.println(errorOutPut);	
		//System.out.println(outPut);
	}
	
	public static void main(String[] args)  {
		System.out.println("Initializing analysis");
		try {
			fun();
			System.out.println("Opening Analysis in Browser\n");
			File htmlFile = new File("target/site/findbugs.html");
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
