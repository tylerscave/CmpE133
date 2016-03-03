package model;
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

		private Scanner scanner;
		//private Hashtable <LoginInformation, Member> database;
		private static String[][] database = new String[10][8];

		public void load() throws FileNotFoundException{
			scanner = new Scanner(new FileReader("Database"));
			store(scanner);
			
		}
		
		public void store(Scanner scanner){
			Context c = Context.getInstance();
			Member member = c.getMember();
			LoginInformation loginInfo = member.getLoginInfo();
			/*for (int i = 0; i<10;i++){
				for(int j = 0; j<8;j++){
					database[i][j] = "";
					}
				}*/
			//while(scanner.hasNext()){
			for (int i = 0; i<2;i++){
				for(int j = 0; j<8;j++){
					database[i][j] = scanner.next();//}
					}
				}
			for (int i = 0; i<10;i++){
				for (int j=0;j<8;j++){
					System.out.print(database[i][j]+" ");
					}
					System.out.print("\n");
				}
			try {
				save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//database.put(loginInfo,member);
			/*loginInfo = database.get(member);
			String email = loginInfo.getEmail();
			String password = loginInfo.getPassword();
			System.out.print(email);
			 */
		}
		public static void save() throws IOException{
		    File outFile = new File ("Database");
		    FileWriter fWriter = new FileWriter (outFile);
		    PrintWriter pWriter = new PrintWriter (fWriter);
		    for (int i = 0; i<10;i++){
				for (int j=0;j<8;j++){
					pWriter.print(database[i][j]+" ");
					}
				pWriter.print("\n");
				}
		    pWriter.close();
		}
		public static void add(String email,String password, String lastname, String firstname, String address, String phone, MemberType memberType, String paymentMethod){
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
}
