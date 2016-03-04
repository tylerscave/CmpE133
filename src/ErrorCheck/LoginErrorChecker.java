package ErrorCheck;

import javax.swing.JOptionPane;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginErrorChecker {

	public LoginErrorChecker()
	{}
	
	public static void invalidEmail(TextField email) throws Exception
	{
		
		if (email.getText().equals("") || !email.getText().contains("@"))
			{JOptionPane.showMessageDialog(null,
				    "Please Enter a Valid Email",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			throw new Exception();
			}
	}
	
	public static void invalidPass(PasswordField pass) throws Exception
	{
		if (pass.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null,
				    "Please Enter a Valid Password",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		throw new Exception();
		}
	}
}
