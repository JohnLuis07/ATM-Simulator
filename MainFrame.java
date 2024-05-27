import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame implements ActionListener{

	//----- TEXTFIELDS -----
	private JTextField mainUsernameTxt;
	private JPasswordField mainPasswordTxt;
	
	private JTextField regisFirstnameTxt;
	private JTextField regisLastnameTxt;
	private JTextField regisUsernameTxt;
	private JPasswordField regisPasswordTxt;
	private JPasswordField regisConfirmPasswordTxt;
	private JTextField regisPhoneTxt;

	private JTextField depositAmountTxt;

	private JTextField withdrawAmountTxt;
	
	//----- BUTTONS -----
	private JButton mainLoginBtn;
	private JButton mainRegisterBtn;
	
	private JButton regisRegisterBtn;
	private JButton regisCancelBtn;
	
	private JButton loggedDepositBtn;
	private JButton loggedWithdrawBtn;
	private JButton loggedBalanceBtn;
	private JButton loggedHistoryBtn;
	private JButton loggedTransferBtn;
	private JButton loggedLogoutBtn;
	
	private JButton depositDepositBtn;
	private JButton depositCancelBtn;
	
	private JButton withdrawWithdrawBtn;
	private JButton withdrawCancelBtn;
	
	private JButton balanceCancelBtn;
	
	private JButton historyGobackBtn;
	
	private JButton transferTransferBtn;
	private JButton transferGobackBtn;
	
	//----- CHECK BOXES -----
	private JCheckBox mainShowPassChk;
	private JCheckBox regisShowPassChk;
	private JCheckBox balanceShowPassChk;
	
	//----- PANELS -----
	private JLayeredPane layeredPane;
	private JPanel mainPanel;
	private JPanel regisPanel;
	private JPanel loggedPanel;
	private JPanel depositPanel;
	private JPanel withdrawPanel;
	private JPanel balancePanel;
	private JPanel historyPanel;
	private JPanel transferPanel;
	
	//----- LABELS -----
	private JLabel loggedNameLbl;
	private JLabel loggedDateLbl;
	
	private JLabel balanceNameDetail;
	private JLabel balanceUsernameDetail;
	private JLabel balancePasswordDetail;
	private JLabel balancePhoneDetail;
	private JLabel balanceBalanceDetail;
	
	private JLabel transactionDetails;
	
	//----- JTextArea -----
	private JTextArea transactionArea;
	
	//-----------------------------------------------
	String FilePath = "./";
	String accountsDirectory = FilePath+"userAccounts";
	String accountPath = accountsDirectory+"/";
	String name;
	String username;
	String password;
	String number;
	
    String money;
    int login_money;
    String login_name;
    String login_username;
    String login_password;
    String login_number;
    String login_transaction;
    
    String nameDetail;
    String usernameDetail;
    String passwordDetail;
    String phoneDetail;
    String moneyDetail;
    String transactionDetail;
    
    char[] charPass;
    
    KeyStore keys = new KeyStore();
    String key = keys.getKey();
    AESEncryptionDecryption crypto = new AESEncryptionDecryption();
    
    private JTextField otherUsernameTxt;
    private JTextField transferAmountTxt;
    private JPasswordField confirmPassTxt;
	
	public void switchPanels(Container layeredPane, JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
    static void modifyFile(String filePath, String oldString, String newString) {
        String oldContent = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }
            String newContent = oldContent.replaceFirst(Pattern.quote(oldString), newString);
            new FileWriter(filePath, false).close();
            FileWriter writer = new FileWriter(filePath);
            writer.write(newContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	public MainFrame() {
		
		//----------------------- FRAME -----------------------
		setForeground(new Color(0, 0, 0));
		setResizable(false);
		setTitle("ATM Simulator");
		getContentPane().setBackground(Color.BLACK);
		setBounds(100, 100, 800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		//----------------------- LAYERED PANEL -----------------------
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 11, 764, 439);
		getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		//----------------------- IMAGES -----------------------
		//FRAME LOGO
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("res/logo.png"));
		Image logoIco = logo.getImage();
		Image smallLogo = logoIco.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Image bigLogo = logoIco.getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
		ImageIcon designLogo = new ImageIcon(smallLogo);
		ImageIcon designBigLogo = new ImageIcon(bigLogo);
		
		//BACKGROUND IMAGE
		ImageIcon backgroundImg = new ImageIcon(getClass().getClassLoader().getResource("res/background.jpg"));
		
		//LOGGED-IN IMAGES
		ImageIcon logInIco = new ImageIcon(getClass().getClassLoader().getResource("res/logInIco.png"));
		Image getImg = logInIco.getImage();
		Image newImg = getImg.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
		logInIco = new ImageIcon(newImg);
	
		ImageIcon depositIco = new ImageIcon(getClass().getClassLoader().getResource("res/deposit.png"));
		getImg = depositIco.getImage();
		newImg = getImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		depositIco = new ImageIcon(newImg);
		
		ImageIcon withdrawIco = new ImageIcon(getClass().getClassLoader().getResource("res/withdraw.png"));
		getImg = withdrawIco.getImage();
		newImg = getImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		withdrawIco = new ImageIcon(newImg);
		
		ImageIcon balanceIco = new ImageIcon(getClass().getClassLoader().getResource("res/balance.png"));
		getImg = balanceIco.getImage();
		newImg = getImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		balanceIco = new ImageIcon(newImg);
		
		ImageIcon historyIco = new ImageIcon(getClass().getClassLoader().getResource("res/history.png"));
		getImg = historyIco.getImage();
		newImg = getImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		historyIco = new ImageIcon(newImg);
		
		ImageIcon transferIco = new ImageIcon(getClass().getClassLoader().getResource("res/transfer.png"));
		getImg = transferIco.getImage();
		newImg = getImg.getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH);
		transferIco = new ImageIcon(newImg);
		
		ImageIcon logoutIco = new ImageIcon(getClass().getClassLoader().getResource("res/logout.png"));
		getImg = logoutIco.getImage();
		newImg = getImg.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		logoutIco = new ImageIcon(newImg);
		
		//REGISTRATION IMAGES
		ImageIcon signUpIco = new ImageIcon(getClass().getClassLoader().getResource("res/signUpIco.png"));
		getImg = signUpIco.getImage();
		newImg = getImg.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
		signUpIco = new ImageIcon(newImg);
			
		//----------------------- MAIN PANEL -----------------------
			
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(mainPanel, "name_45864022398500");
		mainPanel.setLayout(null);
		
		mainUsernameTxt = new JTextField();
		mainUsernameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		mainUsernameTxt.setToolTipText("Enter your username.");
		mainUsernameTxt.setBounds(454, 169, 157, 20);
		mainPanel.add(mainUsernameTxt);
		mainUsernameTxt.setColumns(10);
		
		mainPasswordTxt = new JPasswordField();
		mainPasswordTxt.setFont(new Font("Arial", Font.BOLD, 12));
		mainPasswordTxt.setToolTipText("Enter your password.");
		mainPasswordTxt.setColumns(10);
		mainPasswordTxt.setBounds(454, 223, 157, 20);
		mainPanel.add(mainPasswordTxt);
		
		JLabel mainTitleLbl = new JLabel("ATM Simulator");
		mainTitleLbl.setForeground(new Color(0, 0, 0));
		mainTitleLbl.setFont(new Font("Arial", Font.BOLD, 45));
		mainTitleLbl.setIcon(designBigLogo);
		mainTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		mainTitleLbl.setBounds(277, 54, 477, 84);
		mainPanel.add(mainTitleLbl);
		
		JLabel mainUsernameLbl = new JLabel("Username:");
		mainUsernameLbl.setForeground(Color.WHITE);
		mainUsernameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		mainUsernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		mainUsernameLbl.setBounds(307, 170, 135, 17);
		mainPanel.add(mainUsernameLbl);
		
		JLabel mainPasswordLbl = new JLabel("Password:");
		mainPasswordLbl.setForeground(Color.WHITE);
		mainPasswordLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		mainPasswordLbl.setFont(new Font("Arial", Font.BOLD, 15));
		mainPasswordLbl.setBounds(307, 224, 135, 17);
		mainPanel.add(mainPasswordLbl);
		
		mainShowPassChk = new JCheckBox("Show Password");
		mainShowPassChk.setForeground(Color.WHITE);
		mainShowPassChk.setBackground(Color.WHITE);
		mainShowPassChk.setOpaque(false);
		mainShowPassChk.setFont(new Font("Arial", Font.BOLD, 15));
		mainShowPassChk.setToolTipText("Shows your password.");
		((JPasswordField) mainPasswordTxt).setEchoChar('*');
		mainShowPassChk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainShowPassChk.isSelected()) {
					((JPasswordField) mainPasswordTxt).setEchoChar((char)0);
				}else {
					((JPasswordField) mainPasswordTxt).setEchoChar('*');
				}
			}
		});
		mainShowPassChk.setBounds(611, 222, 148, 23);
		mainShowPassChk.setFocusable(false);
		mainPanel.add(mainShowPassChk);
		
		//----------------------- REGISTRATION PANEL -----------------------
		
		regisPanel = new JPanel();
		regisPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(regisPanel, "name_45873043876700");
		regisPanel.setLayout(null);
		
		regisFirstnameTxt = new JTextField();
		regisFirstnameTxt.setToolTipText("Enter your first name.");
		regisFirstnameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		regisFirstnameTxt.setColumns(10);
		regisFirstnameTxt.setBounds(457, 68, 157, 20);
		regisPanel.add(regisFirstnameTxt);
		
		regisLastnameTxt = new JTextField();
		regisLastnameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		regisLastnameTxt.setToolTipText("Enter your last name.");
		regisLastnameTxt.setColumns(10);
		regisLastnameTxt.setBounds(457, 118, 157, 20);
		regisPanel.add(regisLastnameTxt);
		
		regisUsernameTxt = new JTextField();
		regisUsernameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		regisUsernameTxt.setToolTipText("Enter your username.");
		regisUsernameTxt.setColumns(10);
		regisUsernameTxt.setBounds(457, 168, 157, 20);
		regisPanel.add(regisUsernameTxt);
		
		regisPasswordTxt = new JPasswordField();
		regisPasswordTxt.setFont(new Font("Arial", Font.BOLD, 12));
		regisPasswordTxt.setToolTipText("Enter your password.");
		regisPasswordTxt.setColumns(10);
		regisPasswordTxt.setBounds(457, 218, 157, 20);
		regisPanel.add(regisPasswordTxt);
		
		regisConfirmPasswordTxt = new JPasswordField();
		regisConfirmPasswordTxt.setFont(new Font("Arial", Font.BOLD, 12));
		regisConfirmPasswordTxt.setToolTipText("Confirm your password by re-entering it again.");
		regisConfirmPasswordTxt.setColumns(10);
		regisConfirmPasswordTxt.setBounds(457, 268, 157, 20);
		regisPanel.add(regisConfirmPasswordTxt);
		
		regisPhoneTxt = new JTextField();
		regisPhoneTxt.setFont(new Font("Arial", Font.BOLD, 12));
		regisPhoneTxt.setToolTipText("Enter your phone number.");
		regisPhoneTxt.setColumns(10);
		regisPhoneTxt.setBounds(457, 318, 157, 20);
		regisPanel.add(regisPhoneTxt);
		
		JLabel regisTitleLbl = new JLabel("Registration");
		regisTitleLbl.setForeground(Color.BLACK);
		regisTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		regisTitleLbl.setFont(new Font("Arial", Font.BOLD, 45));
		regisTitleLbl.setBounds(0, 0, 301, 87);
		regisPanel.add(regisTitleLbl);
		
		JLabel regisFirstnameLbl = new JLabel("First Name:");
		regisFirstnameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		regisFirstnameLbl.setForeground(Color.WHITE);
		regisFirstnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		regisFirstnameLbl.setBounds(311, 68, 135, 17);
		regisPanel.add(regisFirstnameLbl);
		
		JLabel regisLastnameLbl = new JLabel("Last Name:");
		regisLastnameLbl.setForeground(Color.WHITE);
		regisLastnameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		regisLastnameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		regisLastnameLbl.setBounds(311, 118, 135, 17);
		regisPanel.add(regisLastnameLbl);
		
		JLabel regisUsernameLbl = new JLabel("Username:");
		regisUsernameLbl.setForeground(Color.WHITE);
		regisUsernameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		regisUsernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		regisUsernameLbl.setBounds(311, 168, 135, 17);
		regisPanel.add(regisUsernameLbl);
		
		JLabel regisPasswordLbl = new JLabel("Password:");
		regisPasswordLbl.setForeground(Color.WHITE);
		regisPasswordLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		regisPasswordLbl.setFont(new Font("Arial", Font.BOLD, 15));
		regisPasswordLbl.setBounds(311, 218, 135, 17);
		regisPanel.add(regisPasswordLbl);
		
		JLabel regisConfirmPasswordLbl = new JLabel("Confirm Password:");
		regisConfirmPasswordLbl.setForeground(Color.WHITE);
		regisConfirmPasswordLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		regisConfirmPasswordLbl.setFont(new Font("Arial", Font.BOLD, 15));
		regisConfirmPasswordLbl.setBounds(303, 268, 143, 17);
		regisPanel.add(regisConfirmPasswordLbl);
		
		JLabel regisPhoneLbl = new JLabel("Phone Number:");
		regisPhoneLbl.setForeground(Color.WHITE);
		regisPhoneLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		regisPhoneLbl.setFont(new Font("Arial", Font.BOLD, 15));
		regisPhoneLbl.setBounds(311, 318, 135, 17);
		regisPanel.add(regisPhoneLbl);
		
		regisShowPassChk = new JCheckBox("Show Password");
		regisShowPassChk.setForeground(Color.WHITE);
		regisShowPassChk.setFont(new Font("Arial", Font.BOLD, 15));
		regisShowPassChk.setToolTipText("Shows your password.");
		regisPasswordTxt.setEchoChar('*');
		regisShowPassChk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (regisShowPassChk.isSelected()) {
					regisPasswordTxt.setEchoChar((char)0);
				}else {
					regisPasswordTxt.setEchoChar('*');
				}
			}
		});
		regisShowPassChk.setBounds(614, 218, 143, 23);
		regisShowPassChk.setOpaque(false);
		regisShowPassChk.setFocusable(false);
		regisPanel.add(regisShowPassChk);
		
		////----------------------- LOGGED-IN PANEL -----------------------
		
		loggedPanel = new JPanel();
		loggedPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(loggedPanel, "name_45876100638200");
		loggedPanel.setLayout(null);
		
		JPanel loggedHeaderPanel = new JPanel();
		loggedHeaderPanel.setBackground(new Color(255, 255, 255));
		loggedHeaderPanel.setBounds(0, 0, 764, 63);
		loggedPanel.add(loggedHeaderPanel);
		loggedHeaderPanel.setLayout(null);
		
		loggedNameLbl = new JLabel();
		loggedNameLbl.setText("Welcome Juan Dela Cruz");
		loggedNameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		loggedNameLbl.setForeground(new Color(0, 0, 0));
		loggedNameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		loggedNameLbl.setBounds(385, 11, 338, 18);
		loggedHeaderPanel.add(loggedNameLbl);
		
		loggedDateLbl = new JLabel("dd/mm/yyyy");
		loggedDateLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		loggedDateLbl.setFont(new Font("Arial", Font.BOLD, 12));
		loggedDateLbl.setBounds(566, 27, 157, 25);
		loggedHeaderPanel.add(loggedDateLbl);
		
		JLabel loggedChooseLbl = new JLabel("CHOOSE AN OPTION TO CONTINUE");
		loggedChooseLbl.setForeground(new Color(255, 255, 255));
		loggedChooseLbl.setFont(new Font("Arial", Font.BOLD, 15));
		loggedChooseLbl.setBounds(40, 75, 291, 41);
		loggedPanel.add(loggedChooseLbl);
		
		////----------------------- DEPOSIT PANEL -----------------------
		
		depositPanel = new JPanel();
		depositPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(depositPanel, "name_45879488697200");
		depositPanel.setLayout(null);
		
		JPanel depositBoxPanel = new JPanel();
		depositBoxPanel.setBackground(new Color(0, 0, 0, 50));
		depositBoxPanel.setBounds(225, 32, 300, 370);
		depositPanel.add(depositBoxPanel);
		depositBoxPanel.setLayout(null);
		
		JLabel depositBoxTitle = new JLabel("Deposit Money");
		depositBoxTitle.setForeground(new Color(255, 255, 255));
		depositBoxTitle.setHorizontalAlignment(SwingConstants.CENTER);
		depositBoxTitle.setBackground(new Color(51, 153, 153));
		depositBoxTitle.setOpaque(true);
		depositBoxTitle.setFont(new Font("Arial", Font.BOLD, 30));
		depositBoxTitle.setBounds(0, 0, 300, 80);
		depositBoxPanel.add(depositBoxTitle);
		
		JLabel depositInfoLbl = new JLabel("From 100 to 50,000 only.");
		depositInfoLbl.setForeground(new Color(0, 0, 0));
		depositInfoLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		depositInfoLbl.setFont(new Font("Arial", Font.BOLD, 11));
		depositInfoLbl.setBounds(70, 120, 180, 20);
		depositBoxPanel.add(depositInfoLbl);
		
		JLabel depositAmountLbl = new JLabel("Enter amount:");
		depositAmountLbl.setForeground(new Color(255, 255, 255));
		depositAmountLbl.setBounds(10, 100, 110, 20);
		depositBoxPanel.add(depositAmountLbl);
		depositAmountLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		depositAmountLbl.setFont(new Font("Arial", Font.BOLD, 15));
		
		depositAmountTxt = new JTextField();
		depositAmountTxt.setFont(new Font("Arial", Font.BOLD, 12));
		depositAmountTxt.setBounds(123, 100, 130, 20);
		depositBoxPanel.add(depositAmountTxt);
		depositAmountTxt.setColumns(10);
		
		//----------------------- WITHDRAW PANEL -----------------------
		
		withdrawPanel = new JPanel();
		withdrawPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(withdrawPanel, "name_45882085983600");
		withdrawPanel.setLayout(null);
		
		JPanel withdrawBoxPanel = new JPanel();
		withdrawBoxPanel.setBackground(new Color(0, 0, 0, 50));
		withdrawBoxPanel.setBounds(225, 32, 300, 370);
		withdrawPanel.add(withdrawBoxPanel);
		withdrawBoxPanel.setLayout(null);
		
		JLabel withdrawBoxTitle = new JLabel("Withdraw Money");
		withdrawBoxTitle.setBackground(new Color(51, 153, 153));
		withdrawBoxTitle.setOpaque(true);
		withdrawBoxTitle.setFont(new Font("Arial", Font.BOLD, 30));
		withdrawBoxTitle.setForeground(new Color(255, 255, 255));
		withdrawBoxTitle.setHorizontalAlignment(SwingConstants.CENTER);
		withdrawBoxTitle.setBounds(0, 0, 300, 80);
		withdrawBoxPanel.add(withdrawBoxTitle);
		
		JLabel withdrawInfoLbl = new JLabel("Up to 50,000Php only.");
		withdrawInfoLbl.setForeground(new Color(0, 0, 0));
		withdrawInfoLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		withdrawInfoLbl.setFont(new Font("Arial", Font.BOLD, 11));
		withdrawInfoLbl.setBounds(70, 120, 180, 20);
		withdrawBoxPanel.add(withdrawInfoLbl);
		
		JLabel withdrawAmountLbl = new JLabel("Enter amount:");
		withdrawAmountLbl.setForeground(new Color(255, 255, 255));
		withdrawAmountLbl.setBounds(10, 100, 110, 20);
		withdrawBoxPanel.add(withdrawAmountLbl);
		withdrawAmountLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		withdrawAmountLbl.setFont(new Font("Arial", Font.BOLD, 15));
		
		withdrawAmountTxt = new JTextField();
		withdrawAmountTxt.setFont(new Font("Arial", Font.BOLD, 12));
		withdrawAmountTxt.setBounds(123, 100, 130, 20);
		withdrawBoxPanel.add(withdrawAmountTxt);
		withdrawAmountTxt.setColumns(10);
		
		//----------------------- CHECK ACCOUNT DETAILS/BALANCE PANEL -----------------------
		
		balancePanel = new JPanel();
		balancePanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(balancePanel, "name_45884896795100");
		balancePanel.setLayout(null);
		
		JLabel balanceTitleLbl = new JLabel("Account Details and Balance");
		balanceTitleLbl.setBackground(new Color(0, 0, 0));
		balanceTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balanceTitleLbl.setForeground(new Color(255, 255, 255));
		balanceTitleLbl.setFont(new Font("Arial", Font.BOLD, 30));
		balanceTitleLbl.setBounds(25, 11, 442, 61);
		balancePanel.add(balanceTitleLbl);
		
		JPanel balanceLabelPanel = new JPanel();
		balanceLabelPanel.setBackground(new Color(102, 204, 204));
		balanceLabelPanel.setBounds(115, 83, 156, 323);
		balancePanel.add(balanceLabelPanel);
		balanceLabelPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel balanceNameLbl = new JLabel("Name:");
		balanceNameLbl.setForeground(new Color(255, 255, 255));
		balanceNameLbl.setBackground(new Color(102, 204, 204));
		balanceNameLbl.setVisible(true);
		balanceNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balanceNameLbl.setFont(new Font("Arial", Font.BOLD, 20));
		balanceLabelPanel.add(balanceNameLbl);
		
		JLabel balanceUsernameLbl = new JLabel("Username:");
		balanceUsernameLbl.setForeground(new Color(255, 255, 255));
		balanceUsernameLbl.setBackground(new Color(102, 204, 204));
		balanceUsernameLbl.setVisible(true);
		balanceUsernameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balanceUsernameLbl.setFont(new Font("Arial", Font.BOLD, 20));
		balanceLabelPanel.add(balanceUsernameLbl);
		
		JLabel balancePasswordLbl = new JLabel("Password:");
		balancePasswordLbl.setForeground(new Color(255, 255, 255));
		balancePasswordLbl.setBackground(new Color(102, 204, 204));
		balancePasswordLbl.setVisible(true);
		balancePasswordLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balancePasswordLbl.setFont(new Font("Arial", Font.BOLD, 20));
		balanceLabelPanel.add(balancePasswordLbl);
		
		JLabel balancePhoneLbl = new JLabel("Phone Number:");
		balancePhoneLbl.setForeground(new Color(255, 255, 255));
		balancePhoneLbl.setBackground(new Color(102, 204, 204));
		balancePhoneLbl.setVisible(true);
		balancePhoneLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balancePhoneLbl.setFont(new Font("Arial", Font.BOLD, 20));
		balanceLabelPanel.add(balancePhoneLbl);
		
		JLabel balanceBalanceLbl = new JLabel("Balance:");
		balanceBalanceLbl.setForeground(new Color(255, 255, 255));
		balanceBalanceLbl.setBackground(new Color(102, 204, 204));
		balanceBalanceLbl.setVisible(true);
		balanceBalanceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balanceBalanceLbl.setFont(new Font("Arial", Font.BOLD, 20));
		balanceLabelPanel.add(balanceBalanceLbl);
		
		JPanel balanceDetailPanel = new JPanel();
		balanceDetailPanel.setBackground(new Color(0, 153, 204));
		balanceDetailPanel.setBounds(281, 83, 255, 323);
		balancePanel.add(balanceDetailPanel);
		balanceDetailPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		balanceNameDetail = new JLabel();
		balanceNameDetail.setBackground(new Color(0, 153, 204));
		balanceNameDetail.setText("Juan Dela Cruz");
		balanceNameDetail.setOpaque(true);
		balanceNameDetail.setHorizontalAlignment(SwingConstants.CENTER);
		balanceNameDetail.setForeground(new Color(255, 255, 255));
		balanceNameDetail.setFont(new Font("Arial", Font.BOLD, 15));
		balanceDetailPanel.add(balanceNameDetail);
		
		balanceUsernameDetail = new JLabel();
		balanceUsernameDetail.setBackground(new Color(0, 153, 204));
		balanceUsernameDetail.setText("juan");
		balanceUsernameDetail.setOpaque(true);
		balanceUsernameDetail.setHorizontalAlignment(SwingConstants.CENTER);
		balanceUsernameDetail.setForeground(new Color(255, 255, 255));
		balanceUsernameDetail.setFont(new Font("Arial", Font.BOLD, 15));
		balanceDetailPanel.add(balanceUsernameDetail);
		
		balancePasswordDetail = new JLabel();
		balancePasswordDetail.setBackground(new Color(0, 153, 204));
		balancePasswordDetail.setText("*****");
		balancePasswordDetail.setOpaque(true);
		balancePasswordDetail.setHorizontalAlignment(SwingConstants.CENTER);
		balancePasswordDetail.setForeground(new Color(255, 255, 255));
		balancePasswordDetail.setFont(new Font("Arial", Font.BOLD, 15));
		balanceDetailPanel.add(balancePasswordDetail);
		
		balancePhoneDetail = new JLabel();
		balancePhoneDetail.setBackground(new Color(0, 153, 204));
		balancePhoneDetail.setText("09xxxxxxxx");
		balancePhoneDetail.setOpaque(true);
		balancePhoneDetail.setHorizontalAlignment(SwingConstants.CENTER);
		balancePhoneDetail.setForeground(new Color(255, 255, 255));
		balancePhoneDetail.setFont(new Font("Arial", Font.BOLD, 15));
		balanceDetailPanel.add(balancePhoneDetail);
		
		balanceBalanceDetail = new JLabel();
		balanceBalanceDetail.setBackground(new Color(0, 153, 204));
		balanceBalanceDetail.setText("10000 Php");
		balanceBalanceDetail.setOpaque(true);
		balanceBalanceDetail.setHorizontalAlignment(SwingConstants.CENTER);
		balanceBalanceDetail.setForeground(new Color(255, 255, 255));
		balanceBalanceDetail.setFont(new Font("Arial", Font.BOLD, 15));
		balanceDetailPanel.add(balanceBalanceDetail);
		
		balanceShowPassChk = new JCheckBox("Show Password");
		balanceShowPassChk.setForeground(new Color(255, 255, 255));
		balanceShowPassChk.setFont(new Font("Arial", Font.BOLD, 12));
		balanceShowPassChk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (balanceShowPassChk.isSelected()) {
					balancePasswordDetail.setText(login_password);
				}else {
					balancePasswordDetail.setText(String.valueOf(charPass));
				}
			}
		});
		balanceShowPassChk.setBounds(542, 226, 146, 32);
		balanceShowPassChk.setOpaque(false);
		balanceShowPassChk.setFocusable(false);
		balancePanel.add(balanceShowPassChk);
		
		
		//----------------------- TRANSACTION HISTORY PANEL -----------------------
		
		historyPanel = new JPanel();
		historyPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(historyPanel, "name_45892558983900");
		historyPanel.setLayout(null);

		JPanel transactionPanel = new JPanel();
		transactionPanel.setBackground(new Color(0, 0, 0, 50));
		transactionPanel.setBounds(92, 49, 553, 303);
		historyPanel.add(transactionPanel);
		transactionPanel.setLayout(null);
		
		transactionDetails = new JLabel("Transaction History");
		transactionDetails.setForeground(new Color(255, 255, 255));
		transactionDetails.setFont(new Font("Arial", Font.BOLD, 30));
		transactionDetails.setBounds(21, 18, 413, 38);
		transactionPanel.add(transactionDetails);
		
		JScrollPane trasanctionScrollPane = new JScrollPane();
		trasanctionScrollPane.setBounds(21, 67, 508, 216);
		transactionPanel.add(trasanctionScrollPane);
		
		transactionArea = new JTextArea();
		transactionArea.setForeground(new Color(255, 255, 255));
		transactionArea.setBackground(new Color(0, 102, 204));
		transactionArea.setFont(new Font("Monospaced", Font.BOLD, 15));
		trasanctionScrollPane.setViewportView(transactionArea);
		transactionArea.setEditable(false);

		//----------------------- TRANSFER MONEY PANEL -----------------------
		transferPanel = new JPanel();
		transferPanel.setBackground(new Color(102, 204, 204));
		layeredPane.add(transferPanel, "name_48492168212800");
		transferPanel.setLayout(null);
		
		JPanel transferBoxPanel = new JPanel();
		transferBoxPanel.setBackground(new Color(0, 0, 0, 50));
		transferBoxPanel.setBounds(225, 32, 300, 370);
		transferPanel.add(transferBoxPanel);
		transferBoxPanel.setLayout(null);
		
		JLabel transferBoxTitleLbl = new JLabel("Transfer Money");
		transferBoxTitleLbl.setForeground(new Color(255, 255, 255));
		transferBoxTitleLbl.setBackground(new Color(51, 153, 153));
		transferBoxTitleLbl.setOpaque(true);
		transferBoxTitleLbl.setFont(new Font("Arial", Font.BOLD, 30));
		transferBoxTitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		transferBoxTitleLbl.setBounds(0, 0, 300, 80);
		transferBoxPanel.add(transferBoxTitleLbl);
		
		JLabel otherUsernameLbl = new JLabel("Enter recipient's username:");
		otherUsernameLbl.setForeground(new Color(255, 255, 255));
		otherUsernameLbl.setFont(new Font("Arial", Font.BOLD, 15));
		otherUsernameLbl.setBounds(15, 115, 203, 14);
		transferBoxPanel.add(otherUsernameLbl);
		
		JLabel transferAmountLbl = new JLabel("Enter amount to transfer:");
		transferAmountLbl.setForeground(new Color(255, 255, 255));
		transferAmountLbl.setFont(new Font("Arial", Font.BOLD, 15));
		transferAmountLbl.setBounds(15, 179, 203, 14);
		transferBoxPanel.add(transferAmountLbl);
		
		JLabel confirmPassLbl = new JLabel("Enter password to confirm transfer:");
		confirmPassLbl.setForeground(new Color(255, 255, 255));
		confirmPassLbl.setFont(new Font("Arial", Font.BOLD, 15));
		confirmPassLbl.setBounds(15, 244, 254, 14);
		transferBoxPanel.add(confirmPassLbl);
		
		otherUsernameTxt = new JTextField();
		otherUsernameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		otherUsernameTxt.setBounds(15, 130, 191, 20);
		transferBoxPanel.add(otherUsernameTxt);
		otherUsernameTxt.setColumns(10);
		
		transferAmountTxt = new JTextField();
		transferAmountTxt.setFont(new Font("Arial", Font.BOLD, 12));
		transferAmountTxt.setBounds(15, 194, 191, 20);
		transferBoxPanel.add(transferAmountTxt);
		transferAmountTxt.setColumns(10);
		
		confirmPassTxt = new JPasswordField();
		confirmPassTxt.setFont(new Font("Arial", Font.BOLD, 12));
		confirmPassTxt.setBounds(15, 258, 191, 20);
		transferBoxPanel.add(confirmPassTxt);
		confirmPassTxt.setColumns(10);
		
		//-------- BUTTONS -------
		// MAIN PANEL BUTTONS
		mainRegisterBtn = new JButton("Register");
		mainRegisterBtn.setFont(new Font("Arial", Font.BOLD, 12));
		mainRegisterBtn.setToolTipText("Register new user.");
		mainRegisterBtn.setBounds(487, 302, 90, 25);
		mainRegisterBtn.setFocusable(false);
		mainRegisterBtn.addActionListener(this);
		mainPanel.add(mainRegisterBtn);
		
		mainLoginBtn = new JButton("Log In");
		mainLoginBtn.setFont(new Font("Arial", Font.BOLD, 12));
		mainLoginBtn.setToolTipText("Log in through entered username.");
		mainLoginBtn.setBounds(487, 268, 90, 25);
		mainLoginBtn.setFocusable(false);
		mainLoginBtn.addActionListener(this);
		mainPanel.add(mainLoginBtn);
		
		// REGISTRATION PANEL BUTTONS
		regisRegisterBtn = new JButton("Register");
		regisRegisterBtn.setFont(new Font("Arial", Font.BOLD, 12));
		regisRegisterBtn.setToolTipText("Register your data as new user.");
		regisRegisterBtn.setBounds(525, 350, 90, 25);
		regisRegisterBtn.setFocusable(false);
		regisRegisterBtn.addActionListener(this);
		regisPanel.add(regisRegisterBtn);
		
		regisCancelBtn = new JButton("Cancel");
		regisCancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		regisCancelBtn.setToolTipText("Cancel registration.");
		regisCancelBtn.setBounds(630, 390, 90, 25);
		regisCancelBtn.setFocusable(false);
		regisCancelBtn.addActionListener(this);
		regisPanel.add(regisCancelBtn);
		
		// LOGGED-IN PANEL BUTTONS
		loggedDepositBtn = new JButton("Deposit Money");
		loggedDepositBtn.setForeground(new Color(255, 255, 255));
		loggedDepositBtn.setBackground(new Color(51, 204, 204));
		loggedDepositBtn.setIcon(depositIco);
		loggedDepositBtn.setIconTextGap(85);
		loggedDepositBtn.setHorizontalAlignment(SwingConstants.TRAILING);
		loggedDepositBtn.setFont(new Font("Arial", Font.BOLD, 20));
		loggedDepositBtn.setToolTipText("Allows you to deposit money to your account.");
		loggedDepositBtn.setBounds(40, 120, 310, 74);
		loggedDepositBtn.setFocusable(false);
		loggedDepositBtn.addActionListener(this);
		loggedPanel.add(loggedDepositBtn);
		
		loggedWithdrawBtn = new JButton("Withdraw Money");
		loggedWithdrawBtn.setForeground(new Color(255, 255, 255));
		loggedWithdrawBtn.setBackground(new Color(51, 204, 204));
		loggedWithdrawBtn.setIcon(withdrawIco);
		loggedWithdrawBtn.setIconTextGap(68);
		loggedWithdrawBtn.setHorizontalAlignment(SwingConstants.TRAILING);
		loggedWithdrawBtn.setFont(new Font("Arial", Font.BOLD, 20));
		loggedWithdrawBtn.setToolTipText("Allows you to withdraw money from this account.");
		loggedWithdrawBtn.setBounds(40, 220, 310, 74);
		loggedWithdrawBtn.setFocusable(false);
		loggedWithdrawBtn.addActionListener(this);
		loggedPanel.add(loggedWithdrawBtn);
		
		loggedBalanceBtn = new JButton("Account Details/Balance");
		loggedBalanceBtn.setForeground(new Color(255, 255, 255));
		loggedBalanceBtn.setBackground(new Color(51, 204, 204));
		loggedBalanceBtn.setIcon(balanceIco);
		loggedBalanceBtn.setIconTextGap(40);
		loggedBalanceBtn.setFont(new Font("Arial", Font.BOLD, 16));
		loggedBalanceBtn.setHorizontalAlignment(SwingConstants.TRAILING);
		loggedBalanceBtn.setToolTipText("Check your account details and balance.");
		loggedBalanceBtn.setBounds(40, 320, 310, 74);
		loggedBalanceBtn.setFocusable(false);
		loggedBalanceBtn.addActionListener(this);
		loggedPanel.add(loggedBalanceBtn);
		
		loggedHistoryBtn = new JButton("View Transaction History");
		loggedHistoryBtn.setForeground(new Color(255, 255, 255));
		loggedHistoryBtn.setBackground(new Color(0, 102, 204));
		loggedHistoryBtn.setIcon(historyIco);
		loggedHistoryBtn.setIconTextGap(35);
		loggedHistoryBtn.setHorizontalTextPosition(SwingConstants.LEADING);
		loggedHistoryBtn.setHorizontalAlignment(SwingConstants.LEADING);
		loggedHistoryBtn.setFont(new Font("Arial", Font.BOLD, 16));
		loggedHistoryBtn.setToolTipText("Allows you to view your past transactions.");
		loggedHistoryBtn.setBounds(414, 120, 310, 74);
		loggedHistoryBtn.setFocusable(false);
		loggedHistoryBtn.addActionListener(this);
		loggedPanel.add(loggedHistoryBtn);
		
		loggedTransferBtn = new JButton("Transfer Money");
		loggedTransferBtn.setForeground(new Color(255, 255, 255));
		loggedTransferBtn.setBackground(new Color(0, 102, 204));
		loggedTransferBtn.setIcon(transferIco);
		loggedTransferBtn.setIconTextGap(60);
		loggedTransferBtn.setHorizontalTextPosition(SwingConstants.LEADING);
		loggedTransferBtn.setHorizontalAlignment(SwingConstants.LEADING);
		loggedTransferBtn.setFont(new Font("Arial", Font.BOLD, 20));
		loggedTransferBtn.setToolTipText("Transfer money to another account.");
		loggedTransferBtn.setBounds(414, 220, 310, 74);
		loggedTransferBtn.setFocusable(false);
		loggedTransferBtn.addActionListener(this);
		loggedPanel.add(loggedTransferBtn);
		
		loggedLogoutBtn = new JButton("Log Out");
		loggedLogoutBtn.setForeground(new Color(255, 255, 255));
		loggedLogoutBtn.setBackground(new Color(0, 102, 204));
		loggedLogoutBtn.setIcon(logoutIco);
		loggedLogoutBtn.setIconTextGap(145);
		loggedLogoutBtn.setHorizontalTextPosition(SwingConstants.LEADING);
		loggedLogoutBtn.setHorizontalAlignment(SwingConstants.LEADING);
		loggedLogoutBtn.setFont(new Font("Arial", Font.BOLD, 20));
		loggedLogoutBtn.setToolTipText("Log out account.");
		loggedLogoutBtn.setBounds(414, 320, 310, 74);
		loggedLogoutBtn.setFocusable(false);
		loggedLogoutBtn.addActionListener(this);
		loggedPanel.add(loggedLogoutBtn);
		
		depositCancelBtn = new JButton("Cancel");
		depositCancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		depositCancelBtn.setBounds(630, 390, 90, 25);
		depositCancelBtn.setFocusable(false);
		depositCancelBtn.addActionListener(this);
		depositPanel.add(depositCancelBtn);
		
		depositDepositBtn = new JButton("Deposit");
		depositDepositBtn.setForeground(new Color(255, 255, 255));
		depositDepositBtn.setBackground(new Color(0, 102, 204));
		depositDepositBtn.setFont(new Font("Arial", Font.BOLD, 20));
		depositDepositBtn.setBounds(80, 290, 135, 50);
		depositDepositBtn.setFocusable(false);
		depositDepositBtn.addActionListener(this);
		depositBoxPanel.add(depositDepositBtn);
		
		withdrawWithdrawBtn = new JButton("Withdraw");
		withdrawWithdrawBtn.setForeground(new Color(255, 255, 255));
		withdrawWithdrawBtn.setBackground(new Color(0, 102, 204));
		withdrawWithdrawBtn.setFont(new Font("Arial", Font.BOLD, 20));
		withdrawWithdrawBtn.setBounds(80, 290, 135, 50);
		withdrawWithdrawBtn.setFocusable(false);
		withdrawWithdrawBtn.addActionListener(this);
		withdrawBoxPanel.add(withdrawWithdrawBtn);
		
		withdrawCancelBtn = new JButton("Cancel");
		withdrawCancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		withdrawCancelBtn.setBounds(630, 390, 90, 25);
		withdrawCancelBtn.setFocusable(false);
		withdrawCancelBtn.addActionListener(this);
		withdrawPanel.add(withdrawCancelBtn);
		
		balanceCancelBtn = new JButton("Cancel");
		balanceCancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		balanceCancelBtn.setBounds(630, 390, 90, 25);
		balanceCancelBtn.setFocusable(false);
		balanceCancelBtn.addActionListener(this);
		balancePanel.add(balanceCancelBtn);
		
		historyGobackBtn = new JButton("Go Back");
		historyGobackBtn.setFont(new Font("Arial", Font.BOLD, 12));
		historyGobackBtn.addActionListener(this);
		historyGobackBtn.setBounds(630, 390, 90, 25);
		historyGobackBtn.setFocusable(false);
		historyPanel.add(historyGobackBtn);
		
		transferTransferBtn = new JButton("Transfer Money");
		transferTransferBtn.setForeground(new Color(255, 255, 255));
		transferTransferBtn.setBackground(new Color(0, 102, 204));
		transferTransferBtn.setFont(new Font("Arial", Font.BOLD, 13));
		transferTransferBtn.setBounds(80, 290, 135, 50);
		transferTransferBtn.addActionListener(this);
		transferTransferBtn.setFocusable(false);
		transferBoxPanel.add(transferTransferBtn);
		
		transferGobackBtn = new JButton("Cancel");
		transferGobackBtn.setFont(new Font("Arial", Font.BOLD, 12));
		transferGobackBtn.setBounds(630, 390, 90, 25);
		transferGobackBtn.setFocusable(false);
		transferGobackBtn.addActionListener(this);
		transferPanel.add(transferGobackBtn);
		
		//---------- ADDING IMAGES -----------
		//FRAME
		setIconImage(logoIco);
		
		//MAIN
		JLabel mainLoginLbl = new JLabel("", logInIco, JLabel.CENTER);
		mainLoginLbl.setBounds(10, 73, 332, 304);
		mainPanel.add(mainLoginLbl);
		
		JLabel mainBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		mainBackgroundLbl.setBounds(0, 0, 764, 439);
		mainPanel.add(mainBackgroundLbl);
		
		//REGISTRATION
		JLabel regisSignUpLbl = new JLabel("", signUpIco, JLabel.CENTER);
		regisSignUpLbl.setBounds(10, 83, 338, 293);
		regisPanel.add(regisSignUpLbl);
		
		JLabel regisBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		regisBackgroundLbl.setBounds(0, 0, 764, 439);
		regisPanel.add(regisBackgroundLbl);
		
		//LOGGED
		JLabel loggedTitle = new JLabel();
		loggedTitle.setForeground(new Color(0, 0, 0));
		loggedTitle.setText("ATM Simulator");
		loggedTitle.setIcon(designLogo);
		loggedTitle.setIconTextGap(10);
		loggedTitle.setOpaque(false);
		loggedTitle.setFont(new Font("Arial", Font.BOLD, 20));
		loggedTitle.setBounds(40, 0, 310, 63);
		loggedHeaderPanel.add(loggedTitle);
		
		JLabel loggedBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		loggedBackgroundLbl.setBounds(0, 0, 764, 439);
		loggedPanel.add(loggedBackgroundLbl);
		
		//DEPOSIT
		JLabel depositBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		depositBackgroundLbl.setBounds(0, 0, 764, 439);
		depositPanel.add(depositBackgroundLbl);
		
		//WITHDRAW
		JLabel withdrawBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		withdrawBackgroundLbl.setBounds(0, 0, 764, 439);
		withdrawPanel.add(withdrawBackgroundLbl);
		
		//CHECK DETAILS/BALANCE
		JLabel balanceBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		balanceBackgroundLbl.setBounds(0, 0, 764, 439);
		balancePanel.add(balanceBackgroundLbl);

		//TRANSACTION HISTORY
		JLabel historyBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		historyBackgroundLbl.setBounds(0, 0, 764, 439);
		historyPanel.add(historyBackgroundLbl);
		
		//TRANSFER
		JLabel transferBackgroundLbl = new JLabel("", backgroundImg, JLabel.CENTER);
		transferBackgroundLbl.setBounds(0, 0, 764, 439);
		transferPanel.add(transferBackgroundLbl);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		File f = new File(accountsDirectory);
		if(!f.exists()){
			f.mkdir();
		}
		//---------- MAIN PANEL BUTTONS ----------
		if (e.getSource() == mainRegisterBtn) {
			mainUsernameTxt.setText(null);
			mainPasswordTxt.setText(null);
			mainShowPassChk.setSelected(false);
			mainPasswordTxt.setEchoChar('*');
			regisConfirmPasswordTxt.setEchoChar('*');
			switchPanels(layeredPane, regisPanel);
		}
		
		else if (e.getSource() == mainLoginBtn) {
			username = mainUsernameTxt.getText();
			password = String.valueOf(mainPasswordTxt.getPassword());
			File file = new File(accountPath + username + ".txt");
			if (file.exists()) {
                try {
                    Scanner dataReader = new Scanner(file);
                    money = crypto.decrypt(dataReader.nextLine(), key);
                    login_money = Integer.parseInt(money);
                    login_name = crypto.decrypt(dataReader.nextLine(), key);
                    login_username = crypto.decrypt(dataReader.nextLine(), key);
                    login_password = crypto.decrypt(dataReader.nextLine(), key);
                    login_number = crypto.decrypt(dataReader.nextLine(), key);
                    login_transaction = crypto.decrypt(dataReader.nextLine(), key);

                    if (username.equals(login_username) && password.equals(login_password)) {
                    	JOptionPane.showMessageDialog(mainPanel, "Logged In Successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        				mainUsernameTxt.setText(null);
        				mainPasswordTxt.setText(null);
        				mainShowPassChk.setSelected(false);
        				((JPasswordField) mainPasswordTxt).setEchoChar('*');
        				loggedNameLbl.setText("Welcome " + login_name);
        			    SimpleDateFormat formatter = new SimpleDateFormat(
        			            "dd/MM/yyyy");
        			    Date date = new Date();
        				loggedDateLbl.setText(formatter.format(date));
                    	switchPanels(layeredPane, loggedPanel);
                    }else if(!username.equals(login_username)) {
                    	JOptionPane.showMessageDialog(mainPanel, "Username is not registered.", "Invalid", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(username.equals(login_username) && !password.equals(login_password)) {
                    	JOptionPane.showMessageDialog(mainPanel, "Incorrect Password.", "Invalid", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(FileNotFoundException e1) {
                	e1.printStackTrace();
                }
            }else if(username.isEmpty()) {
            	JOptionPane.showMessageDialog(mainPanel, "Please enter username.", "Invalid", JOptionPane.ERROR_MESSAGE);
            }else {
            	JOptionPane.showMessageDialog(mainPanel, "Username is not registered.", "Invalid", JOptionPane.ERROR_MESSAGE);
            }
		}
		
		//---------- REGISTRATION PANEL BUTTONS ----------
		else if (e.getSource() == regisRegisterBtn) {
				String firstname = regisFirstnameTxt.getText().concat(" ");
				String lastname = regisLastnameTxt.getText();
				name = firstname.concat(lastname);
				char[] charName = name.toCharArray();
				boolean space = true;
				for (int i = 0; i < name.length(); i++) {
					if (Character.isLetter(charName[i])) {
						if(space) {
							charName[i] = Character.toUpperCase(charName[i]);
							space = false;
						}
					}else {
						space = true;
					}
				}
				name = String.valueOf(charName).strip();
				username = regisUsernameTxt.getText().strip();
				char[] charUsername = username.toCharArray();
				boolean usernameSpace = false;
				for (int i = 0; i < username.length(); i++) {
					if (charUsername[i] == ' ') {
						usernameSpace = true;
						break;
					}
				}
				password = String.valueOf(regisPasswordTxt.getPassword());
				String confirmPassword = String.valueOf(regisConfirmPasswordTxt.getPassword());
				number = regisPhoneTxt.getText().strip();
				File file = new File(accountPath + username + ".txt");
				String filename = file.getName();
				boolean flag = true;
				for (int i = 0; i < number.length(); i++) {
					flag = Character.isDigit(number.charAt(i));
					if (!flag) {
						break;}
				}
				if (file.exists()) {
					JOptionPane.showMessageDialog(regisPanel, "Username is already taken.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (firstname.isEmpty()) {
					JOptionPane.showMessageDialog(regisPanel, "Please enter first name.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (lastname.isEmpty()) {
					JOptionPane.showMessageDialog(regisPanel, "Please enter last name.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (username.isEmpty()) {
					JOptionPane.showMessageDialog(regisPanel, "Please enter username.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (usernameSpace == true) {
					JOptionPane.showMessageDialog(regisPanel, "Username must not contain space.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (password.length() < 8) {
					JOptionPane.showMessageDialog(regisPanel, "Password must be atleast 8 characters.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (!password.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(regisPanel, "Password doesn't match.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (password.isEmpty()) {
					JOptionPane.showMessageDialog(regisPanel, "Please enter password.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (number.isEmpty()) {
					JOptionPane.showMessageDialog(regisPanel, "Please enter phone number.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (number.length() > 11 || number.length() < 11) {
					JOptionPane.showMessageDialog(regisPanel, "Phone number must be 11 numbers.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (!flag) {
					JOptionPane.showMessageDialog(regisPanel, "Phone number must be integers.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else {
					User_Info reg = new User_Info(name, username, password, number, "TRANSACTION:0", accountPath);
					JOptionPane.showMessageDialog(regisPanel, "Registered Successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
					regisFirstnameTxt.setText(null);
					regisLastnameTxt.setText(null);
					regisUsernameTxt.setText(null);
					regisPasswordTxt.setText(null);
					regisConfirmPasswordTxt.setText(null);
					regisPhoneTxt.setText(null);
					regisShowPassChk.setSelected(false);
					regisPasswordTxt.setEchoChar('*');
					switchPanels(layeredPane, mainPanel);
				}
		}
		
		else if (e.getSource() == regisCancelBtn) {
			regisFirstnameTxt.setText(null);
			regisLastnameTxt.setText(null);
			regisUsernameTxt.setText(null);
			regisPasswordTxt.setText(null);
			regisConfirmPasswordTxt.setText(null);
			regisPhoneTxt.setText(null);
			regisShowPassChk.setSelected(false);
			regisPasswordTxt.setEchoChar('*');
			switchPanels(layeredPane, mainPanel);
		}
		
		//---------- LOGGED-IN PANEL BUTTONS ----------
		else if (e.getSource() == loggedDepositBtn) {
			switchPanels(layeredPane, depositPanel);
		}
		
		else if (e.getSource() == loggedWithdrawBtn) {
			switchPanels(layeredPane, withdrawPanel);
		}
		
		else if (e.getSource() == loggedBalanceBtn) {
            balanceNameDetail.setText(login_name);
            balanceUsernameDetail.setText(login_username);
            charPass = login_password.toCharArray();
            for(int i = 0; i < login_password.length(); i++) {
            	charPass[i] = '*';
            }
            balancePasswordDetail.setText(String.valueOf(charPass));
            balancePhoneDetail.setText(login_number);
            balanceBalanceDetail.setText(login_money + " Php");
			switchPanels(layeredPane, balancePanel);
		}
		
		else if (e.getSource() == loggedHistoryBtn) {
			File file = new File(accountPath + username + ".txt");
			Scanner dataReader;
			String transaction;
			String fileData = "";
			try {
				dataReader = new Scanner(file);
				String temp = "TRANSACTION:0";
				if (login_transaction.equals(temp)) {
					JOptionPane.showMessageDialog(loggedPanel, "No transaction history.", "Info", JOptionPane.INFORMATION_MESSAGE);
				}else {
					for (int j = 0; j < 6; j++) {
                        dataReader.nextLine();
                    }
                    while (dataReader.hasNextLine()) {
                    	transaction = crypto.decrypt(dataReader.nextLine(), key).concat("\n");
                    	fileData += transaction;
                    }
                    transactionArea.setText(fileData);
                    transactionDetails.setText("Transaction History");
                    switchPanels(layeredPane, historyPanel);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (e.getSource() == loggedTransferBtn) {
			((JPasswordField) confirmPassTxt).setEchoChar('*');
			switchPanels(layeredPane, transferPanel);
		}
		
		else if (e.getSource() == loggedLogoutBtn) {
			int choice = JOptionPane.showOptionDialog(loggedPanel,
					"Are you sure you want to log out?",
					"Info",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					null,
					0);
			if (choice == 0) {
				switchPanels(layeredPane, mainPanel);
			}
		}
		
		//---------- DEPOSIT PANEL BUTTONS ----------
		else if (e.getSource() == depositDepositBtn) {
			String amount = depositAmountTxt.getText();
			boolean flag = true;
			for (int i = 0; i < amount.length(); i++) {
				flag = Character.isDigit(amount.charAt(i));
				if (!flag) {
					break;}
			}
			if (amount.isEmpty()) {
				JOptionPane.showMessageDialog(depositPanel, "Please enter amount.", "Invalid", JOptionPane.ERROR_MESSAGE);
			}else if (!flag) {
				JOptionPane.showMessageDialog(depositPanel, "Invalid amount.\n(Letters, characters, and spaces are invalid.)",
						"Invalid", JOptionPane.ERROR_MESSAGE);
			}else {
				int temp = Integer.parseInt(amount);
				if (temp > 50000) {
					JOptionPane.showMessageDialog(depositPanel, "Please deposit not more than 50,000.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (temp < 100) {
					JOptionPane.showMessageDialog(depositPanel, "Please deposit not less than 100.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (temp == 0) {
					JOptionPane.showMessageDialog(depositPanel, "Please enter valid amount.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
                        // Open file, Replace original amount with updated with time also
                        FileWriter f0 = new FileWriter(accountPath + username + ".txt", true);
                        String old_money = Integer.toString(login_money);
                        login_money += Integer.parseInt(amount);
                        money = Integer.toString(login_money);
                        String to_be_deposited = Integer.toString(login_money);
                        
                        modifyFile(accountPath + username + ".txt", crypto.encrypt(old_money, key), crypto.encrypt(to_be_deposited, key));

                        SimpleDateFormat formatter = new SimpleDateFormat(
                                "dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        f0.write(crypto.encrypt(("Php(+" + temp + ") \t|| \t" + formatter.format(date)
                                + " \t|| \tSelf Deposit "), key) + "\n");
                        login_transaction = "TRANSACTION:1";
                        modifyFile(accountPath + username + ".txt", crypto.encrypt("TRANSACTION:0", key),
                                crypto.encrypt("TRANSACTION:1", key));
                        f0.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
					JOptionPane.showMessageDialog(depositPanel, "Deposited Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
					depositAmountTxt.setText(null);
					switchPanels(layeredPane, loggedPanel);
                }
			}
		}
		
		else if (e.getSource() == depositCancelBtn) {
			depositAmountTxt.setText(null);
			switchPanels(layeredPane, loggedPanel);
		}
		
		//---------- WITHDRAW PANEL BUTTONS ----------
		else if (e.getSource() == withdrawWithdrawBtn) {
			String amount = withdrawAmountTxt.getText();
			boolean flag = true;
			for (int i = 0; i < amount.length(); i++) {
				flag = Character.isDigit(amount.charAt(i));
				if (!flag) {
					break;}
			}
			if (amount.isEmpty()) {
				JOptionPane.showMessageDialog(withdrawPanel, "Please enter amount.", "Invalid", JOptionPane.ERROR_MESSAGE);
			}else if (!flag) {
				JOptionPane.showMessageDialog(withdrawPanel, "Invalid amount.\n(Letters, characters, and spaces are invalid.)"
						, "Invalid", JOptionPane.ERROR_MESSAGE);
			}else {
				int temp = Integer.parseInt(amount);
				if (temp > 50000) {
					JOptionPane.showMessageDialog(withdrawPanel, "Please withdraw not more than 50,000.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (temp > login_money) {
					JOptionPane.showMessageDialog(withdrawPanel, "Please withdraw not more than your current balance.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else if (temp == 0) {
					JOptionPane.showMessageDialog(withdrawPanel, "Please enter valid amount.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
                        // Open file, Replace original amount with updated with time also
                        FileWriter f0 = new FileWriter(accountPath + username + ".txt", true);
                        String old_money = Integer.toString(login_money);
                        login_money -= Integer.parseInt(amount);
                        money = Integer.toString(login_money);
                        String to_be_withdrawed = Integer.toString(login_money);
                        modifyFile(accountPath + username + ".txt", crypto.encrypt(old_money, key), crypto.encrypt(to_be_withdrawed, key));

                        SimpleDateFormat formatter = new SimpleDateFormat(
                                "dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        f0.write(crypto.encrypt(("Php(-" + temp + ") \t|| \t" + formatter.format(date)
                                + " \t|| \tSelf Withdraw"), key) + "\n");

                        login_transaction = "TRANSACTION:1";
                        modifyFile(accountPath + username + ".txt", crypto.encrypt("TRANSACTION:0", key),
                                "TRANSACTION:1");
                        f0.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
					JOptionPane.showMessageDialog(withdrawPanel, "Withdrawed Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
					withdrawAmountTxt.setText(null);
					switchPanels(layeredPane, loggedPanel);
				}
            }
		}
		
		else if (e.getSource() == withdrawCancelBtn) {
			withdrawAmountTxt.setText(null);
			switchPanels(layeredPane, loggedPanel);
		}
		
		//---------- ACCOUNT DETAILS/BALANCE PANEL BUTTONS ----------
		else if (e.getSource() == balanceCancelBtn) {
			balanceShowPassChk.setSelected(false);
			switchPanels(layeredPane, loggedPanel);
		}
		
		//---------- TRANSACTION HISTORY PANEL BUTTONS ----------
		else if (e.getSource() == historyGobackBtn) {
			switchPanels(layeredPane, loggedPanel);
		}
		
		//---------- TRANSFER MONEY PANEL BUTTONS ----------
		else if (e.getSource() == transferTransferBtn) {
			String username_to_transfer = otherUsernameTxt.getText();
			String amount_transfer_update = transferAmountTxt.getText();
			boolean flag = true;
			for (int i = 0; i < amount_transfer_update.length(); i++) {
				flag = Character.isDigit(amount_transfer_update.charAt(i));
				if (!flag) {
					break;}
			}
			String confirmPass = String.valueOf(confirmPassTxt.getPassword());
			File file_to_transfer = new File(accountPath + username_to_transfer + ".txt");
           
			if (file_to_transfer.exists()) {
				if (username_to_transfer.equals(login_username)) {
					JOptionPane.showMessageDialog(transferPanel, "You can't transfer money to yourself.", "Invalid", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						Scanner dataReader = new Scanner(file_to_transfer);
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
	                Scanner dataReader2;
					try {
						dataReader2 = new Scanner(file_to_transfer);
	                    String money_old = crypto.decrypt(dataReader2.nextLine(), key);
	                    String name_transfer = crypto.decrypt(dataReader2.nextLine(), key);
	                    int money_old_user = Integer.parseInt(money_old);
	                    
	                    if (amount_transfer_update.isEmpty()) {
	    					JOptionPane.showMessageDialog(transferPanel, "Please enter amount.", "Invalid", JOptionPane.ERROR_MESSAGE);
	    				}else if (!flag) {
	    					JOptionPane.showMessageDialog(transferPanel, "Invalid amount.\n(Letters, characters, and spaces are invalid.)"
	    							, "Invalid", JOptionPane.ERROR_MESSAGE);
	    				}else if (!confirmPass.equals(login_password)) {
	    					JOptionPane.showMessageDialog(transferPanel, "Incorrect password.", "Invalid", JOptionPane.ERROR_MESSAGE);
	    				}else {
	    					int temp = Integer.parseInt(amount_transfer_update);
	    					if (temp > login_money) {
	    						JOptionPane.showMessageDialog(transferPanel, "Please transfer money not more than your current balance.", "Invalid", JOptionPane.ERROR_MESSAGE);
	    					}else if (temp == 0) {
	    						JOptionPane.showMessageDialog(transferPanel, "Please enter valid amount.", "Invalid", JOptionPane.ERROR_MESSAGE);
	    					}else {
	                        String to_upd = Integer.toString(login_money);
	                        login_money -= Integer.parseInt(amount_transfer_update);
	                        String to_upd2 = Integer.toString(login_money);
	                        modifyFile(accountPath + username + ".txt", crypto.encrypt(to_upd, key), crypto.encrypt(to_upd2, key));

	                        String to_update = Integer.toString(money_old_user);
	                        money_old_user += Integer.parseInt(amount_transfer_update);
	                        String to_update_2 = Integer.toString(money_old_user);
	                        modifyFile(accountPath + username_to_transfer + ".txt", crypto.encrypt(to_update, key),
	                                crypto.encrypt(to_update_2, key));
	                        modifyFile(accountPath + username_to_transfer + ".txt", crypto.encrypt("TRANSACTION:0", key),
	                                crypto.encrypt("TRANSACTION:1", key));
	                        try {
	                            FileWriter f11 = new FileWriter(
	                                    accountPath + username_to_transfer + ".txt", true);
	                            SimpleDateFormat formatter = new SimpleDateFormat(
	                                    "dd/MM/yyyy HH:mm:ss");
	                            Date date = new Date();
	                            f11.write(crypto.encrypt("Php(+" + amount_transfer_update + ") \t|| \t"
	                                    + formatter.format(date) + " \t|| \tTransferred from "
	                                    + username + " (" + login_name + ")", key) +"\n");
	                            f11.close();

	                            FileWriter f12 = new FileWriter(accountPath + username + ".txt", true);
	                            SimpleDateFormat formatter2 = new SimpleDateFormat(
	                                    "dd/MM/yyyy HH:mm:ss");
	                            Date date2 = new Date();
	                            f12.write(crypto.encrypt("Php(-" + amount_transfer_update + ") \t|| \t"
	                                    + formatter2.format(date2) + " \t|| \tTransferred to "
	                                    + username_to_transfer + " (" + name_transfer + ")", key)+"\n");
	                            f12.close();
	                        }catch (IOException e1) {
	                            e1.printStackTrace();
	                        }
		    				dataReader2.close();
		    				JOptionPane.showMessageDialog(transferPanel, "Transfered Successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
		    				otherUsernameTxt.setText(null);
		    				transferAmountTxt.setText(null);
		    				confirmPassTxt.setText(null);
		    				switchPanels(layeredPane, loggedPanel);
	    					}
	    				}
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(transferPanel, "Username doesn't exist.", "Invalid", JOptionPane.ERROR_MESSAGE);
            }
		}
		
		else if (e.getSource() == transferGobackBtn) {
			otherUsernameTxt.setText(null);
			transferAmountTxt.setText(null);
			confirmPassTxt.setText(null);
			switchPanels(layeredPane, loggedPanel);
		}
	}
}
