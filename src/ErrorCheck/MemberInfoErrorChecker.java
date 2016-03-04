package ErrorCheck;

import javax.swing.JOptionPane;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MemberInfoErrorChecker {
	public static void invalidBlank(TextField... fields) throws Exception
	{
		for (TextField field : fields)
		{
			if (field.getText().equals(""))
			{
				field.setStyle("-fx-control-inner-background: #FF0000");
			}
		}
		for (TextField field : fields)
		{
			JOptionPane.showMessageDialog(null,
				    "Please Fill In The Highlighted Fields",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			throw new Exception();
		}
	
	}
}
