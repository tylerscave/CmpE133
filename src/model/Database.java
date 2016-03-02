package model;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
public class Database {

		private Scanner scanner;

		public void load() throws FileNotFoundException{
			scanner = new Scanner(new FileReader("Database"));
			System.out.print(scanner.next());

		}
}
