package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
*
* Follows Singleton design pattern. Used to hold user information.
* Reads elements from database file 
* @author Shubaan
*/


public class Database {
		private static int rows;//number of members
		private static int columns;//number of fields
		public static String[][] database = new String[50][10];
		
		public void load() throws FileNotFoundException{
			Scanner countElementScanner = new Scanner(new FileReader("Database"));
			int numOfElements = 0;
			countElementScanner.useDelimiter(",");
			while(countElementScanner.hasNext()){
				countElementScanner.next();
				 numOfElements++;}
			columns = 8;
			rows =numOfElements/columns;
			BufferedReader br = new BufferedReader(new FileReader("Database"));
			try {
				for (int i=0;i<rows;i++){
					String splitArray[]=br.readLine().split(",");
					for (int j=0; j<columns;j++){

						database[i][j]=splitArray[j].trim();
					}
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		

		public static void save() throws IOException{
		    File outFile = new File ("Database");
		    FileWriter fWriter = new FileWriter (outFile);
		    PrintWriter pWriter = new PrintWriter (fWriter);
		    for (int i = 0; i<rows;i++){
				for (int j=0;j<columns;j++){
					pWriter.print(database[i][j]+", ");
					}
				pWriter.print("\n");
				}
			pWriter.print("saved ");//sanity check
		    pWriter.close();
		}
		public static void add(String email,String password, String lastname, String firstname, String address, String phone, MemberType memberType, String paymentMethod){
			boolean found = false;
			System.out.print("Add button pressed\n");
			for(int i = 0; i<rows; i++){
				if(database[i][0]==email){
					database[i][0]=email;
					database[i][1]=password;
					database[i][2]=lastname;
					database[i][3]=firstname;
					database[i][4]=address;
					database[i][5]=phone;
					database[i][6]="student";
					database[i][7]=paymentMethod;
					
					found=true;
				}
			}
			if(!found){
			database[rows][0]=email;
			database[rows][1]=password;
			database[rows][2]=lastname;
			database[rows][3]=firstname;
			database[rows][4]=address;
			database[rows][5]=phone;
			database[rows][6]="student";
			database[rows][7]=paymentMethod;
			rows++;}
			try {
				save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void setRows(int r){
			rows = r;
		}
		public int getRows(){
			return rows;
		}
		
		public void setColumns(int c){
			columns = c;
		}
		public int getColumns(){
			return columns;
		}
}
