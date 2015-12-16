package userInterface;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import account.AccountRepository;

public class LoginDialog {

	public void inputAccountInformation() {
		JTextField account = new JTextField(10);
		JPasswordField password = new JPasswordField(10);
		AccountRepository accounts=AccountRepository.getInstance();
		accounts.manageData();
		JPanel p = new JPanel();
		p.add(new JLabel("Id :"));
		p.add(account);
		p.add(new JLabel("Password : "));
		p.add(password);
		boolean passwordRight = false;
		int choice = JOptionPane.showConfirmDialog(null, p, "Enter your account : ", JOptionPane.OK_CANCEL_OPTION);
		if (choice == -1)
			System.exit(0);
		if (choice == 0) {
			String acc = account.getText();
			@SuppressWarnings("deprecation")
			String pw = password.getText();
			passwordRight = accounts.login(acc, pw);
			if (!passwordRight)
				JOptionPane.showMessageDialog(null, "Wrong id or password");
		}

	}

}
