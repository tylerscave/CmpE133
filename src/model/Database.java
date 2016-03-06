package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;

/**
*
* Follows Singleton design pattern. Used to hold user information.
* @author Shubaan
*/


public class Database {
		private int rows;//number of members
		private int columns;//number of fields
		private static String[][] database = new String[50][10];
		
		public void load() throws FileNotFoundException{
			Scanner countElementScanner = new Scanner(new FileReader("Database"));
			int numOfElements = 0;
			countElementScanner.useDelimiter(",");
			while(countElementScanner.hasNext()){
				countElementScanner.next();
				 numOfElements++;}
			System.out.println(numOfElements);	
			setColumns(8);
			setRows(numOfElements/getColumns());
			BufferedReader br = new BufferedReader(new FileReader("Database"));
			try {
				for (int i=0;i<getRows();i++){
					String splitArray[]=br.readLine().split(",");
					for (int j=0; j<getColumns();j++){

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
		

		public void save() throws IOException{
		    File outFile = new File ("Database");
		    FileWriter fWriter = new FileWriter (outFile);
		    PrintWriter pWriter = new PrintWriter (fWriter);
		    for (int i = 0; i<7;i++){
				for (int j=0;j<8;j++){
					pWriter.print(database[i][j]+", ");
					}
				pWriter.print("\n");
				}
			pWriter.print("saved ");

		    pWriter.close();
		}
		public void add(String email,String password, String lastname, String firstname, String address, String phone, MemberType memberType, String paymentMethod){
			database[2][0]=email;
			database[2][1]=password;
			database[2][2]=lastname;
			database[2][3]=firstname;
			database[2][4]=address;
			database[2][5]=phone;
			database[2][6]="student";
			database[2][7]=paymentMethod;
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
