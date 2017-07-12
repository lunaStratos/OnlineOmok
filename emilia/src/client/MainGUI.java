package client;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import vo.accountData;
import vo.account_tenshu;
import vo.gameData;
import vo.loginData;
import vo.makeRoom;
import vo.roomData;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainGUI extends JFrame implements ActionListener, Runnable {
	CardLayout card = new CardLayout(); // ī�巹�̾ƿ� ����� ����
	private String id; // �� ���� ���̵� �� (2�� protocol)
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static Socket socket;
	private Thread th;
	private JList gameRoom;
	private clientManager mg;
	private boolean b;
	private ArrayList<makeRoom> makeRoomGet;
	private JPanel mainPanel;
	private JPanel loginPanel;
	private JButton loginButton;
	private JButton newsButton;
	private JButton NewAccountLoginButton;
	private JLabel pwLabel;
	private JTextField IDField;
	private JTextField pwField;
	private JLabel ID_Label;
	private JLabel loginStatusLabel;
	private JPanel roomGUIPanel;
	private JTextField chatTextField;
	private JScrollPane scrollPane;
	private JLabel TodaysMessageLabel;
	private JScrollPane chatScroll;
	private JScrollPane roomListScroll;
	private JButton NewRoom;
	private JTextField roomTitle;
	private JButton MakeRoom;
	private JButton cancel;
	private JTextArea textArea;
	private JList nowList;
	private JPanel battleNetPanel;
	private JTextField BattelChat;
	private JButton GamereadyButton;
	private JButton OutRoom;
	private JScrollPane charTextAreaScroll;
	private JPanel playerPanel;
	private JLabel player1pLabel;
	private String player1;
	private String player2;
	private JTextArea charTextArea;
	private JMenuItem CaptureMenu;
	private JMenuItem exitMenu;
	private JFXPanel AD_Panel_2;
	// newAccount panel
	private JTextField passWordField;
	private JTextField pwIDField;
	private JButton newAccountButton;
	private JButton newAccountCancelButton;
	private ImageIcon backgroundLoginPanel;
	JButton modifyButton;
	JButton deleteButton;
	// ���⼭ ���ʹ� battlePanel
	private ImageIcon whiteStone;
	private ImageIcon blackStone;
	private ImageIcon refreshStone;
	private JLabel whiteStoneOn;
	private JLabel blackStoneOn;
	private JLabel refreshStoneOn;
	private JLabel baookpans;
	private JLabel nowTime;
	private Image stoneImage[]; // ����� �ٵ��� �׸������� �����
	private boolean doolSelect; // �ٵϵ� ������ �Ͼ�� �����Ƽ� �Ͼ���� true �������� false
	private int stoneX;
	private int stoneY;
	private int intXInt = 37;
	private int intYInt = 37;
	private int count;
	private int omokTable[][]; // ������ ���� �ٵϵ� int 2���� �迭 // �߿���.
	private ImageIcon whiteDool;
	private ImageIcon blackDool;
	private boolean gameStart = false; // �Ѵ� ����� ���ӽ��� true
	private boolean gameplayerSelect = true; // ���� �ؾ� �� �÷��̾� true�Ͻ� ���.
	private int gameWin; // �������� 0, �Ͼᵹ�� �̱�� 1, �������� �̱�� 2
	private JPanel newAccountPanel;
	private JMenuItem MakePersonMenuPop;
	private ArrayList<String> todayArrayList;
	private int winme;// �� �¸���
	private int defeatme; // �� �й��
	private int winOppo; // ���� �¸���
	private int defeatOppo; // ���� �й��
	private ArrayList<account_tenshu> FirstArray;

	public MainGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ==============���Ͽ���===============
		conn();
		// ==============�޴������� �������� ������ ����===============
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu MenuPOP = new JMenu("Menu");
		menuBar.add(MenuPOP);

		CaptureMenu = new JMenuItem("Capture");
		MenuPOP.add(CaptureMenu);

		MakePersonMenuPop = new JMenuItem("\uB9CC\uB4E0\uC0AC\uB78C\uB4E4");
		MenuPOP.add(MakePersonMenuPop);

		exitMenu = new JMenuItem("Exit");
		MenuPOP.add(exitMenu);
		// ==============�޴������� �������� ������ ����===============
		getContentPane().setLayout(null);
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1014, 799);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(card);
		// ==============�α��� �г� ī�� �ʱ⿡ ���� ===============
		stoneImage = ImageUtils.loadImages();// 1. �ٵ��� 2. �ٵϵ� ȭ��Ʈ 3. ������
		// ==============�̹��� �г� �ʱ⿡ ���� ===============

		loginPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[3], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		// image
		ImageIcon loginButtonImage = new ImageIcon("src/images/login.jpg");
		ImageIcon registerButtonImage = new ImageIcon("src/images/register.jpg");
		ImageIcon newsButtonImage = new ImageIcon("src/images/news.jpg");
		// image
		loginPanel.setLayout(null);

		newsButton = new JButton(newsButtonImage);
		newsButton.setBounds(398, 688, 150, 46);
		loginPanel.add(newsButton);

		loginButton = new JButton(loginButtonImage);
		loginButton.setBounds(236, 688, 150, 46);
		loginPanel.add(loginButton);

		NewAccountLoginButton = new JButton(registerButtonImage);
		NewAccountLoginButton.setBounds(74, 688, 150, 46);
		loginPanel.add(NewAccountLoginButton);

		pwLabel = new JLabel("Password");
		pwLabel.setBounds(166, 660, 57, 15);
		loginPanel.add(pwLabel);

		IDField = new JTextField();
		IDField.setColumns(10);
		IDField.setBounds(251, 626, 116, 21);
		loginPanel.add(IDField);

		pwField = new JTextField();
		pwField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginSend();
				}
			}
		});
		pwField.setColumns(10);
		pwField.setBounds(251, 657, 116, 21);
		loginPanel.add(pwField);

		ID_Label = new JLabel("ID");
		ID_Label.setBounds(166, 632, 57, 15);
		loginPanel.add(ID_Label);

		loginStatusLabel = new JLabel(
				"\uC544\uC774\uB514\uC640 \uC554\uD638\uB97C \uC785\uB825\uD574 \uC8FC\uC138\uC694");
		loginStatusLabel.setBounds(196, 601, 201, 15);
		loginPanel.add(loginStatusLabel);

		// ==============2. ���� �г� ī�� ===============
		roomGUIPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[4], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		roomGUIPanel.setLayout(null);

		chatTextField = new JTextField();
		chatTextField.setColumns(10);
		chatTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // ä��â ���� ��������
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chat();
				}

			}
		});
		chatTextField.setBounds(32, 674, 697, 21);
		chatTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
		roomGUIPanel.add(chatTextField);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(669, 74, 311, 236);
		roomGUIPanel.add(scrollPane);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		// new EmptyBorder(5,5,5,5)

		nowList = new JList();
		scrollPane.setViewportView(nowList);

		chatScroll = new JScrollPane();
		chatScroll.setBounds(32, 426, 590, 211);
		roomGUIPanel.add(chatScroll);
		chatScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

		textArea = new JTextArea();
		textArea.setEditable(false);
		chatScroll.setViewportView(textArea);

		roomListScroll = new JScrollPane();
		roomListScroll.setBounds(32, 72, 582, 249);
		roomGUIPanel.add(roomListScroll);

		roomListScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

		gameRoom = new JList();
		gameRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("���콺 ����");
				if (e.getComponent() == gameRoom) { // ���õ� ��
					if (e.getClickCount() == 2) { // ����Ŭ���� �Ҷ�
						int temp = gameRoom.getSelectedIndex();
						System.out.println(temp);
						if (makeRoomGet.get(temp).getInwon() == 1) {
							// �ο��� 1�̸� ���� �ִ�.
							roomData dataIn = new roomData(roomData.roomIn, id, makeRoomGet.get(temp).getMakeName());
							// makeRoomGet�� ���� �� ����Ʈ�� ���� array
							// �����, �� ���̵�, ���� ���� ���̵�
							mg.send(dataIn);
							player1pLabel.setText(id + "�� ����:: " + winme + "�� | " + defeatme + "��");
							card.show(mainPanel, "battleNetPanel");

						} else if (makeRoomGet.get(temp).getInwon() == 2) {
							int question = JOptionPane.showConfirmDialog(null, "�ο��� �� ���� �� �� �����ϴ�.");

						}

					}
				}
			}
		});
		roomListScroll.setViewportView(gameRoom);

		AD_Panel_2 = new JFXPanel();
		AD_Panel_2.setBounds(691, 725, 320, 50);
		roomGUIPanel.add(AD_Panel_2);

		// ==============3. ��Ʋ�� �г� ���� ===============

		battleNetPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[5], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		battleNetPanel.setLayout(null);
		BattelChat = new JTextField();
		BattelChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chatGame();
				}
			}
		});

		BattelChat.setColumns(10);
		BattelChat.setBounds(768, 585, 225, 21);
		battleNetPanel.add(BattelChat);
		BattelChat.setBorder(new EmptyBorder(0, 0, 0, 0));

		ImageIcon GamereadyButtonImage = new ImageIcon("src/images/ready.jpg");
		GamereadyButton = new JButton(GamereadyButtonImage);
		GamereadyButton.setBounds(844, 642, 144, 105);
		battleNetPanel.add(GamereadyButton);

		ImageIcon outroomImage = new ImageIcon("src/images/out.jpg");
		OutRoom = new JButton(outroomImage);
		OutRoom.setBounds(754, 642, 78, 105);
		battleNetPanel.add(OutRoom);

		charTextAreaScroll = new JScrollPane();
		charTextAreaScroll.setBounds(768, 217, 225, 334);
		battleNetPanel.add(charTextAreaScroll);
		charTextAreaScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

		charTextArea = new JTextArea();
		charTextArea.setEditable(false);
		charTextAreaScroll.setViewportView(charTextArea);

		playerPanel = new JPanel();
		playerPanel.setBounds(768, 57, 230, 63);
		battleNetPanel.add(playerPanel);
		playerPanel.setLayout(new GridLayout(1, 2, 0, 0));
		playerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		playerPanel.setBackground(Color.white);

		player1pLabel = new JLabel("1P");
		playerPanel.add(player1pLabel);
		player1pLabel.setBackground(Color.white);

		baookpans = new JLabel(); // �ٵ����� �÷����� ��
		baookpans.setBounds(12, 10, 718, 718);
		battleNetPanel.add(baookpans);

		ImageIcon badookpan = new ImageIcon(stoneImage[0]);// �ٵ���
		baookpans.setIcon(badookpan); // �ٵ��� �׸� �󺧿� ���̱�
		whiteDool = new ImageIcon(stoneImage[1]);// �Ͼᵹ �̹���
		blackDool = new ImageIcon(stoneImage[2]);// ������ �̹���

		omokTable = new int[23][23]; // 0~19 0~19 �ٵ��� �迭 (�������� ���� ��)]

		// 0. ȸ������ ���============================
		newAccountPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[6], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		newAccountPanel.setLayout(null);
		JLabel AccountIDLabel = new JLabel("id");
		AccountIDLabel.setBounds(399, 631, 57, 15);
		newAccountPanel.add(AccountIDLabel);

		JLabel AccountPwLabel = new JLabel("Password");
		AccountPwLabel.setBounds(399, 670, 57, 15);
		newAccountPanel.add(AccountPwLabel);

		passWordField = new JTextField();
		passWordField.setBounds(479, 667, 116, 21);
		newAccountPanel.add(passWordField);
		passWordField.setColumns(10);

		pwIDField = new JTextField();
		pwIDField.setBounds(479, 628, 116, 21);
		newAccountPanel.add(pwIDField);
		pwIDField.setColumns(10);
		// ���� ��ư �̹�����
		ImageIcon newAccountCancelButtonImage = new ImageIcon("src/images/accountCancel.jpg");
		ImageIcon newAccountButtonImage = new ImageIcon("src/images/accountNew.jpg");
		ImageIcon modifyButtonImage = new ImageIcon("src/images/accounyModify.jpg");
		ImageIcon deleteButtonImage = new ImageIcon("src/images/accountDelete.jpg");
		//
		newAccountCancelButton = new JButton(newAccountCancelButtonImage); // ��ҹ�ư
		newAccountCancelButton.setBounds(242, 701, 145, 44);
		newAccountPanel.add(newAccountCancelButton);

		newAccountButton = new JButton(newAccountButtonImage); // ������ ��ư
		newAccountButton.setBounds(386, 701, 145, 44);
		newAccountPanel.add(newAccountButton);

		JScrollPane yakguan = new JScrollPane();
		yakguan.setBounds(31, 77, 949, 492);
		newAccountPanel.add(yakguan);

		JTextArea newAccountTextArea = new JTextArea();
		newAccountTextArea.setText(
				"\uCD1D\uCE59\r\n\r\n \r\n\r\n\uC81C 1 \uC7A5 \uCD1D\uCE59\r\n\r\n \r\n\r\n\uC81C 1 \uC870 (\uBAA9 \uC801)\r\n  \uC774 \uC57D\uAD00\uC740 (\uC8FC)\uC6B0\uC8FC\uC758 \uAE30\uC6B4 ( \uC774\uD558 \"\uD68C\uC0AC\"\uB77C \uD569\uB2C8\uB2E4.)\uC774 \uC81C\uACF5\uD558\uB294 \uC815\uBCF4\uC11C\uBE44\uC2A4( \uC774\uD558 \"\uC11C\uBE44\uC2A4\"\uB77C \uD55C\uB2E4)\uC758 \r\n  \uC774\uC6A9\uC870\uAC74 \uBC0F \uC808\uCC28\uC5D0 \uAD00\uD55C \uC0AC\uD56D\uC744 \uADDC\uC815\uD568\uC744 \uBAA9\uC801\uC73C\uB85C \uD569\uB2C8\uB2E4. \uC81C 2 \uC870 (\uC57D\uAD00\uC758 \uD6A8\uB825 \uBC0F \uBCC0\uACBD)\r\n\u2460 \uC774 \uC57D\uAD00\uC758 \uB0B4\uC6A9\uC740 \uC11C\uBE44\uC2A4 \uD654\uBA74\uC5D0 \uAC8C\uC2DC\uD558\uAC70\uB098 \uAE30\uD0C0\uC758 \uBC29\uBC95\uC73C\uB85C \uD68C\uC6D0\uC5D0\uAC8C \uACF5\uC9C0\uD568\uC73C\uB85C\uC368 \uD6A8\uB825\uC774 \r\n    \uBC1C\uC0DD\uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uD569\uB9AC\uC801\uC778 \uC0AC\uC720\uAC00 \uBC1C\uC0DD\uB420 \uACBD\uC6B0\uC5D0\uB294 \uC774 \uC57D\uAD00\uC744 \uBCC0\uACBD\uD560 \uC218 \uC788\uC73C\uBA70, \uC57D\uAD00\uC774 \uBCC0\uACBD\uB41C \uACBD\uC6B0\uC5D0\uB294 \r\n    \uCD5C\uC18C\uD55C 7\uC77C\uC804\uC5D0 \u2460\uD56D\uACFC \uAC19\uC740 \uBC29\uBC95\uC73C\uB85C \uACF5\uC2DC\uD569\uB2C8\uB2E4. \uC81C 3 \uC870 (\uC57D\uAD00 \uC678 \uC900\uCE59)\r\n  \uC774 \uC57D\uAD00\uC5D0 \uBA85\uC2DC\uB418\uC9C0 \uC54A\uC740 \uC0AC\uD56D\uC740 \uC804\uAE30\uD1B5\uC2E0\uAE30\uBCF8\uBC95, \uC804\uAE30\uD1B5\uC2E0\uC0AC\uC5C5\uBC95 \uBC0F \uAE30\uD0C0 \uAD00\uB828\uBC95\uB839\uC758 \uADDC\uC815\uC5D0 \r\n  \uB530\uB985\uB2C8\uB2E4. \uC81C 4 \uC870(\uC6A9\uC5B4\uC758 \uC815\uC758)\r\n  \uC774 \uC57D\uAD00\uC5D0\uC11C \uC0AC\uC6A9\uD558\uB294 \uC6A9\uC5B4\uC758 \uC815\uC758\uB294 \uB2E4\uC74C\uACFC \uAC19\uC2B5\uB2C8\uB2E4.\r\n    \u2460 \uD68C\uC6D0.\uD68C\uC6D0\uC0AC : \uD68C\uC0AC\uC640 \uC11C\uBE44\uC2A4 \uC774\uC6A9\uACC4\uC57D\uC744 \uCCB4\uACB0\uD55C \uAC1C\uC778\uC774\uB098 \uBC95\uC778 \uB610\uB294 \uBC95\uC778\uC5D0 \uC900\uD558\uB294 \uB2E8\uCCB4.\r\n\r\n    \u2461 \uC6B4\uC601\uC790 : \uC11C\uBE44\uC2A4\uC758 \uC804\uBC18\uC801\uC778 \uAD00\uB9AC\uC640 \uC6D0\uD65C\uD55C \uC6B4\uC601\uC744 \uC704\uD558\uC5EC \uD68C\uC0AC\uAC00 \uC120\uC815\uD55C \uC0AC\uB78C.\r\n\r\n    \u2462 \uC544\uC774\uB514(ID): \uD68C\uC6D0\uC2DD\uBCC4\uACFC \uD68C\uC6D0\uC758 \uC11C\uBE44\uC2A4 \uC774\uC6A9\uC744 \uC704\uD558\uC5EC \uD68C\uC6D0 \uAC00\uC785 \uC2DC \uC0AC\uC6A9\uD558\uB294 \uBB38\uC790\uC640 \r\n    \uC22B\uC790\uC758 \uC870\uD569.\r\n\r\n    \u2463 \uBE44\uBC00\uBC88\uD638 : \uD68C\uC6D0(\uD68C\uC6D0\uC0AC)\uC758 \uBE44\uBC00 \uBCF4\uD638\uB97C \uC704\uD574 \uD68C\uC6D0 \uC790\uC2E0\uC774 \uC124\uC815\uD55C \uBB38\uC790\uC640 \uC22B\uC790\uC758 \uC870\uD569.\r\n\r\n    \u2464 \uC11C\uBE44\uC2A4\uC911\uC9C0: \uC815\uC0C1\uC774\uC6A9 \uC911 \uD68C\uC0AC\uAC00 \uC815\uD55C \uC77C\uC815\uD55C \uC694\uAC74\uC5D0 \uB530\uB77C \uC77C\uC815\uAE30\uAC04\uB3D9\uC548 \uC11C\uBE44\uC2A4\uC758 \uC81C\uACF5\uC744 \r\n    \uC911\uC9C0\uD558\uB294\uAC83.\r\n\r\n    \u2465 \uD574\uC9C0 : \uD68C\uC0AC \uB610\uB294 \uD68C\uC6D0\uC774 \uC11C\uBE44\uC2A4 \uAC1C\uD1B5 \uD6C4 \uC774\uC6A9\uACC4\uC57D\uC744 \uD574\uC57D\uD558\uB294 \uAC83.\r\n\r\n    \u2466 \uBE45\uC7A5 \uD68C\uC6D0 : \uD68C\uC6D0(\uC774\uD558 \"\uD68C\uC6D0\uC0AC\"\uB77C \uCE6D\uD568)\uC774 \uD55C\uB2EC\uC5D0 \uC77C\uC815\uAE08\uC561\uC744 \uB0B4\uACE0 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uB294 \r\n    \uAC1C\uC778\uC774\uB098 \uB2E8\uCCB4.\r\n\r\n    \u2467 \uC804\uC0AC\uB3C5 \uD68C\uC6D0 : \uBB34\uB8CC \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uB294 \uAC1C\uC778\uC774\uB098 \uB2E8\uCCB4.\r\n \r\n\r\n \r\n\uC774\uC6A9\uC57D\uAD00 \uCCB4\uACB0\r\n \t\r\n\uC81C 2\uC7A5 \uC774\uC6A9\uACC4\uC57D \uCCB4\uACB0 \uC81C 5 \uC870 ( \uC11C\uBE44\uC2A4\uC758 \uAD6C\uBD84)\r\n\u2460 \uD68C\uC0AC\uAC00 \uD68C\uC6D0\uC5D0\uAC8C \uC81C\uACF5\uD558\uB294 \uC11C\uBE44\uC2A4\uB294 \uBB34\uB8CC\uC11C\uBE44\uC2A4\uC640 \uC720\uB8CC\uC11C\uBE44\uC2A4 \uB4F1\uC73C\uB85C \uAD6C\uBD84\uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uC11C\uBE44\uC2A4\uC758 \uC885\uB958\uC640 \uB0B4\uC6A9 \uB4F1\uC740 \uD68C\uC0AC\uAC00 \uACF5\uC9C0\uB098 \uC11C\uBE44\uC2A4 \uC774\uC6A9\uC548\uB0B4\uC5D0\uC11C \uBCC4\uB3C4\uB85C \uC815\uD558\uB294 \uBC14\uC5D0 \uC758\uD569\uB2C8\uB2E4.\r\n\r\n\uC81C 6 \uC870( \uC774\uC6A9\uACC4\uC57D\uC758 \uC131\uB9BD)\r\n\u2460 \uC544\uB798 \" \uC704\uC758 \uC774\uC6A9\uC57D\uAD00\uC5D0 \uB3D9\uC758\uD558\uC2ED\uB2C8\uAE4C? \" \uB77C\uB294 \uBB3C\uC74C\uC5D0 \uD68C\uC6D0(\uD68C\uC6D0\uC0AC)\uC774 \"\uB3D9\uC758\" \uBC84\uD2BC\uC744 \uB204\uB974\uBA74 \uC774 \r\n    \uC57D\uAD00\uC5D0 \uB3D9\uC758\uD558\uB294 \uAC83\uC73C\uB85C \uAC04\uC8FC\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uC774\uC6A9\uACC4\uC57D\uC740 \uD68C\uC6D0(\uD68C\uC6D0\uC0AC)\uC774 \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD558\uC5EC \uD68C\uC0AC\uAC00 \uC2B9\uB099\uD568\uC73C\uB85C\uC368 \uC131\uB9BD\uD569\uB2C8\uB2E4. \uD68C\uC0AC\uB294 \uC2E0\uCCAD\uC77C\r\n    24\uC2DC\uAC04 \uC774\uB0B4\uC5D0 \uC774\uC6A9 \uC2B9\uB099\uC758 \uC758\uC0AC\uB97C \uC774\uC6A9 \uC2E0\uCCAD \uC2DC \uAE30\uC7AC\uD55C e-mail\uC744 \uD1B5\uD558\uC5EC \uC774\uC6A9 \uC2E0\uCCAD\uC790\uC5D0\uAC8C \r\n    \uD1B5\uC9C0\uD569\uB2C8\uB2E4. \uC81C 7 \uC870( \uC774\uC6A9 \uC2E0\uCCAD \uBC0F \uC2B9\uB099)\r\n\u2460 \uC774\uC6A9\uC2E0\uCCAD\uC740 \uC11C\uBE44\uC2A4\uC758 \uC774\uC6A9\uC790\uB4F1\uB85D\uC5D0\uC11C \uB2E4\uC74C\uC0AC\uD56D\uC744 \uAC00\uC785\uC2E0\uCCAD \uC591\uC2DD\uC5D0 \uAE30\uB85D\uD558\uC5EC \uC2E0\uCCAD\uD569\uB2C8\uB2E4. \r\n    \u24D0 \uC774\uB984\r\n    \u24D1 e-mail\r\n    \u24D2 \uC544\uC774\uB514,\uBE44\uBC00\uBC88\uD638\r\n    \u24D3 \uC8FC\uBBFC\uB4F1\uB85D\uBC88\uD638\r\n    \u24D4 \uC8FC\uC18C\r\n    \u24D5 \uC804\uD654\uBC88\uD638 \r\n    \u24D6 \uD68C\uC6D0\uB4F1\uAE09\r\n    \u24D7 \uAC1C\uC778.\uD68C\uC6D0\uC0AC\r\n    \u24D8 \uBE45\uC7A5 \uAD6C\uC0AC \uC5EC\uBD80\r\n\r\n\u2461 \uC774\uC6A9\uACC4\uC57D\uC740 \uD68C\uC6D0\uC758 \uC774\uC6A9\uC790\uB4F1\uB85D\uC5D0 \uB300\uD558\uC5EC \uD68C\uC0AC\uC758 \uC774\uC6A9\uC2B9\uB099\uC73C\uB85C \uC131\uB9BD\uD55C\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uB2E4\uC74C\uACFC \uAC19\uC740 \uC0AC\uC720\uAC00 \uBC1C\uC0DD\uD55C \uACBD\uC6B0 \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD55C \uC2B9\uB099\uC744 \uAC70\uBD80\uD569\uB2C8\uB2E4.\r\n    \u24D0\uBD88\uAC74\uC804\uD55C \uC74C\uB780\uBB3C\uC774\uB098 \uBD88\uBC95\uAC70\uB798, \uB300\uD55C\uBBFC\uAD6D \uBC95\uB960\uC5D0 \uC704\uBC18\uB418\uB294 \uB0B4\uC6A9\uC744 \uAE30\uC7AC\uD55C\uC790.\r\n    \u24D1\uAE30\uD0C0 \uC704\uBC95\uD55C \uC774\uC6A9\uC2E0\uCCAD\uC784\uC774 \uD655\uC778\uB41C \uACBD\uC6B0 \uC81C 8 \uC870( \uC774\uC6A9\uACC4\uC57D \uC0AC\uD56D\uC758 \uBCC0\uACBD) \r\n\u2460 \uD68C\uC6D0\uC740 \uC774\uC6A9 \uC2E0\uCCAD\uC2DC \uAE30\uC7AC\uD55C \uC0AC\uD56D\uC774 \uBCC0\uACBD\uB418\uC5C8\uC744 \uACBD\uC6B0\uC5D0\uB294 \uC6B4\uC601\uC790\uC5D0\uAC8C \uC218\uC815\uC694\uCCAD\uC744 \uD574\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\uC81C 9 \uC870( \uC774\uC6A9\uACC4\uC57D\uC758 \uC885\uB8CC )\r\n\u2460 \uC774\uC6A9\uACC4\uC57D\uC740 \uD68C\uC6D0 \uB610\uB294 \uD68C\uC0AC\uC758 \uD574\uC9C0\uC5D0 \uC758\uD558\uC5EC \uC989\uC2DC \uBF08\uC640 \uC0B4\uC774 \uBD84\uB9AC\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC6D0\uC740 \uD574\uC9C0\uC758\uC0AC \uBC1C\uC0DD \uC2DC \uC989\uC2DC \uD68C\uC0AC\uC5D0 e-mail\uC744 \uD1B5\uD55C \uD574\uC9C0 \uC2E0\uCCAD\uC744 \uD569\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uB2E4\uC74C\uACFC \uAC19\uC740 \uC0AC\uC720 \uBC1C\uC0DD\uC2DC \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD55C \uBE45\uC7A5\uC744 \uAD6C\uC0AC \uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n    \u24D0 \uBD88\uAC74\uD55C \uB0B4\uC6A9\uC744 \uC720\uD3EC\uD558\uAC70\uB098 \uD68C\uC0AC\uC5D0 \uC911\uB300\uD55C \uC190\uD574\uB97C \uB07C\uCE5C \uAC1C\uC778\uC774\uB098 \uD68C\uC6D0\uC0AC.\r\n    \u24D1 \uD14C\uB9C8 \uC1FC\uD551\uC5D0 \uBD88\uAC74\uC804\uD55C \uB0B4\uC6A9\uC744 \uB2F4\uACE0 \uC788\uAC70\uB098 \uBD88\uBC95\uAC70\uB798 \uB4F1\uC744 \uC704\uD55C \uBAA9\uC801\uC73C\uB85C \uC6B4\uC601\uB420 \uACBD\uC6B0\r\n    \u24D2 \uAE30\uD0C0 \uD68C\uC0AC\uC758 \uD569\uB9AC\uC801\uC778 \uD310\uB2E8\uC5D0 \uC758\uD558\uC5EC \uC8FC\uC720\uC18C \uD68C\uC6D0<\uD68C\uC6D0\uC0AC>\uC73C\uB85C \uBD80\uC801\uD569\uD558\uB2E4\uACE0 \uD310\uB2E8\uB420 \uACBD\uC6B0 \r\n \t \r\n\uC11C\uBE44\uC2A4 \uC774\uC6A9\r\n \t\r\n\uC81C 3 \uC7A5 \uC11C\uBE44\uC2A4 \uC774\uC6A9\r\n\r\n\uC81C 10 \uC870 (\uC11C\uBE44\uC2A4\uC758 \uC774\uC6A9 \uAC1C\uC2DC)\r\n\u2460 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC758 \uC774\uC6A9\uC2E0\uCCAD\uC744 \uC2B9\uB099\uD55C \uB54C\uBD80\uD130 \uC989\uC2DC \uC11C\uBE44\uC2A4\uB97C \uAC1C\uC2DC\uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uC758 \uC5C5\uBB34\uC0C1 \uB610\uB294 \uAE30\uC220\uC0C1\uC758 \uC7A5\uC560\uB85C \uC778\uD558\uC5EC \uC11C\uBE44\uC2A4\uB97C \uAC1C\uC2DC\uD558\uC9C0 \uBABB\uD558\uB294 \uACBD\uC6B0\uC5D0\uB294 \uC11C\uBE44\uC2A4\uC5D0 \r\n    \uACF5\uC9C0\uD558\uAC70\uB098 \uD68C\uC6D0\uC5D0\uAC8C \uC989\uC2DC \uC774\uB97C \uD1B5\uC9C0\uD569\uB2C8\uB2E4. \uC81C 11 \uC7A5 ( \uC11C\uBE44\uC2A4\uC758 \uB0B4\uC6A9 )\r\n\r\n\u2460 \uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uC758 \uC6B4\uC6A9\uACFC \uAD00\uB828\uD558\uC5EC \uC11C\uBE44\uC2A4 \uD654\uBA74, \uD648\uD398\uC774\uC9C0, \uC774\uBA54\uC77C \uB4F1\uC5D0 \uAD11\uACE0 \uB4F1\uC744 \uAC8C\uC7AC\uD560 \uC218 \r\n    \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uB97C \uC6B4\uC6A9\uD568\uC5D0 \uC788\uC5B4\uC11C \uAC01\uC885 \uC815\uBCF4\uB97C \uC11C\uBE44\uC2A4\uC5D0 \uAC8C\uC7AC\uD558\uB294 \uBC29\uBC95 \uB4F1\uC73C\uB85C \uD68C\uC6D0\uC5D0\uAC8C \uC81C\uACF5\uD560\r\n    \uC218 \uC788\uC2B5\uB2C8\uB2E4. \uC81C 14\uC870 (\uAC8C\uC2DC\uBB3C \uB610\uB294 \uB0B4\uC6A9\uBB3C\uC758 \uC0AD\uC81C)\r\n\uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uC758 \uAC8C\uC2DC\uBB3C \uB610\uB294 \uB0B4\uC6A9\uBB3C\uC774  \uADDC\uC815\uC5D0 \uC704\uBC18\uB418\uAC70\uB098 \uAC8C\uC2DC\uAE30\uAC04\uC744 \uCD08\uACFC\uD558\uB294 \uACBD\uC6B0 \uC0AC\uC804 \uD1B5\uC9C0\uB098 \uB3D9\uC758 \uC5C6\uC774 \uC774\uB97C \uC0AD\uC81C\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4. \uC81C 15 \uC870 ( \uC11C\uBE44\uC2A4 \uC81C\uACF5\uC758 \uC911\uC9C0)\r\n\uD68C\uC0AC\uB294 \uC5B8\uC81C\uB4E0\uC9C0, \uADF8\uB9AC\uACE0 \uC218\uC2DC\uB85C \uBCF8 \uC11C\uBE44\uC2A4(\uD639\uC740 \uADF8 \uC77C\uBD80)\uB97C, \uD1B5\uC9C0\uD558\uAC70\uB098 \uB610\uB294 \uD1B5\uC9C0\uD568\uC774 \uC5C6\uC774, \uC77C\uC2DC \uB610\uB294 \uC601\uAD6C\uC801\uC73C\uB85C \uC218\uC815\uD558\uAC70\uB098 \uC911\uB2E8\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4. \uADC0\uD558\uB294 \uC11C\uBE44\uC2A4\uC758 \uC218\uC815, \uC911\uB2E8 \uB610\uB294 \uC911\uC9C0\uC5D0 \uB300\uD574 \uD68C\uC0AC\uAC00 \uADC0\uD558 \uB610\uB294 \uC81C 3\uC790\uC5D0 \uB300\uD558\uC5EC \uC5B4\uB5A0\uD55C \uCC45\uC784\uB3C4 \uC9C0\uC9C0 \uC54A\uC74C\uC5D0 \uB3D9\uC758\uD569\uB2C8\uB2E4. \r\n \t \r\n\uAD8C\uB9AC\uC640 \uC758\uBB34\r\n \t\r\n\uC81C 4 \uC7A5 \uAD8C\uB9AC\uC640 \uC758\uBB34 \uC81C 16 \uC870 ( \uD68C\uC0AC\uC758 \uC758\uBB34)\r\n\u2460 \uD68C\uC0AC\uB294 \uD2B9\uBCC4\uD55C \uC0AC\uC720\uAC00 \uC5C6\uB294 \uD55C \uC11C\uBE44\uC2A4 \uC81C\uACF5\uC124\uBE44\uB97C \uD56D\uC0C1 \uC6B4\uC6A9 \uAC00\uB2A5\uD55C \uC0C1\uD0DC\uB85C \uC720\uC9C0\uBCF4\uC218\uD558\uC5EC\uC57C\uD558\uBA70,\r\n    \uC548\uC815\uC801\uC73C\uB85C \uC11C\uBE44\uC2A4\uB97C \uC81C\uACF5\uD560 \uC218 \uC788\uB3C4\uB85D \uCD5C\uC120\uC758 \uB178\uB825\uC744 \uB2E4\uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uAD00\uACC4 \uBC95\uB839\uC5D0 \uC758\uD558\uC5EC \uC218\uC0AC\uC0C1\uC758  \uBAA9\uC801\uC73C\uB85C \uAD00\uACC4\uAE30\uAD00\uC73C\uB85C\uBD80\uD130 \uC694\uAD6C\uBC1B\uC740 \uACBD\uC6B0, \uC815\uBCF4\uD1B5\uC2E0 \uC724\uB9AC\uC704\uC6D0\uD68C\uC758\r\n    \uC694\uCCAD\uC774 \uC788\uB294 \uACBD\uC6B0, \uAE30\uD0C0 \uAD00\uACC4\uBC95\uB839\uC5D0 \uC758\uD55C \uACBD\uC6B0 \uD68C\uC0AC\uB294 \uAC1C\uC778\uC815\uBCF4\uB97C \uC81C\uACF5\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uC640 \uAD00\uB828\uD55C \uD68C\uC6D0\uC758 \uBD88\uB9CC\uC0AC\uD56D\uC774 \uC811\uC218\uB418\uB294 \uACBD\uC6B0 \uC774\uB97C \uC989\uC2DC \uCC98\uB9AC\uD558\uC5EC\uC57C \uD558\uBA70, \uC989\uC2DC \uCC98\r\n    \uB9AC\uAC00 \uACE4\uB780\uD55C \uACBD\uC6B0 \uADF8 \uC0AC\uC720\uC640 \uCC98\uB9AC\uC77C\uC815\uC744 \uC11C\uBE44\uC2A4 \uB610\uB294 \uC804\uC790\uC6B0\uD3B8\uC744 \uD1B5\uD558\uC5EC \uB3D9 \uD68C\uC6D0\uC5D0\uAC8C \uD1B5\uC9C0\uD558\uC5EC\uC57C\r\n    \uD569\uB2C8\uB2E4. \uC81C 17 \uC870 \uD68C\uC6D0\uC758 \uC758\uBB34\r\n\u2460 \uD68C\uC6D0\uC740 \uAD00\uACC4 \uBC95\uB839, \uBCF8 \uC57D\uAD00\uC758 \uADDC\uC815, \uC774\uC6A9\uC548\uB0B4 \uBC0F \uC11C\uBE44\uC2A4\uC0C1\uC5D0 \uACF5\uC9C0\uD55C \uC8FC\uC758\uC0AC\uD56D, \uD68C\uC0AC\uAC00 \uD1B5\uC9C0\uD558\uB294 \r\n    \uC0AC\uD56D\uC744 \uC900\uC218\uD558\uC5EC\uC57C \uD558\uBA70, \uAE30\uD0C0 \uD68C\uC0AC\uC758 \uC5C5\uBB34\uC5D0 \uBC29\uD574\uB418\uB294 \uD589\uC704\uB97C \uD558\uC5EC\uC11C\uB294 \uC544\uB2C8\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC6D0\uC740 \uD68C\uC0AC\uC758 \uC0AC\uC804 \uB3D9\uC758\uC5C6\uC774 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uC5EC \uC5B4\uB5A0\uD55C \uC601\uB9AC\uD589\uC704\uB3C4 \uD560 \uC218 \uC5C6\uC73C\uBA70, \uBC95\uC5D0 \uC800\uCD09\uB418\uB294\r\n    \uC790\uB8CC\uB97C \uBC30\uD3EC \uB610\uB294 \uAC8C\uC7AC\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC6D0\uC740 \uC790\uC2E0\uC758 \uC544\uC774\uB514\uC640  \uBE44\uBC00\uBC88\uD638\uB97C \uC720\uC9C0 \uAD00\uB9AC\uD560 \uCC45\uC784\uC774 \uC788\uC73C\uBA70 \uC790\uC2E0\uC758 \uC544\uC774\uB514\uC640 \uBE44\uBC00\uBC88\uD638\uB97C \r\n    \uC0AC\uC6A9\uD558\uC5EC \uBC1C\uC0DD\uD558\uB294 \uBAA8\uB4E0 \uACB0\uACFC\uC5D0 \uB300\uD574 \uC804\uC801\uC778 \uCC45\uC784\uC774 \uC788\uC2B5\uB2C8\uB2E4. \uB610\uD55C \uC790\uC2E0\uC758 \uC544\uC774\uB514\uC640 \uBE44\uBC00\uBC88\uD638\uAC00\r\n    \uC790\uC2E0\uC758 \uC2B9\uB099\uC5C6\uC774 \uC0AC\uC6A9\uB418\uC5C8\uC744 \uACBD\uC6B0 \uC989\uC2DC \uD68C\uC0AC\uC5D0  \uC2E0\uACE0\uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\u2463 \uD68C\uC6D0\uC740 \uC774\uC6A9\uC2E0\uCCAD\uC2DC\uC758 \uAE30\uC7AC\uB0B4\uC6A9\uC5D0 \uB300\uD574 \uC9C4\uC2E4\uD558\uACE0 \uC815\uD655\uD558\uBA70 \uD604\uC7AC\uC758 \uC0AC\uC2E4\uACFC \uC77C\uCE58\uD558\uB3C4\uB85D \uC720\uC9C0\uD558\uACE0 \r\n    \uAC31\uC2E0\uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4. \r\n\r\n\u2464 \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uC5EC \uC5BB\uC740 \uC815\uBCF4\uB97C \uD68C\uC0AC\uC758 \uC0AC\uC804 \uC2B9\uB099\uC5C6\uC774 \uBCF5\uC0AC, \uBCF5\uC81C, \uBCC0\uACBD, \uBC88\uC5ED, \uCD9C\uD310, \uBC29\uC1A1\r\n    \uAE30\uD0C0\uC758 \uBC29\uBC95\uC73C\uB85C \uC0AC\uC6A9\uD558\uAC70\uB098 \uC774\uB97C \uD0C0\uC778\uC5D0\uAC8C \uC81C\uACF5\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2465 \uD68C\uC6D0\uC740 \uC74C\uB780\uBB3C \uAC8C\uC7AC, \uC720\uD3EC\uB4F1 \uC0AC\uD68C\uC9C8\uC11C\uB97C \uD574\uCE58\uB294 \uD589\uC704\uB97C \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2466 \uD68C\uC6D0\uC740 \uD0C0\uC778\uC758 \uBA85\uC608\uB97C \uD6FC\uC190\uD558\uAC70\uB098 \uBAA8\uC695\uD558\uB294 \uD589\uC704\uC640 \uD0C0\uC778\uC758 \uC9C0\uC801\uC7AC\uC0B0\uAD8C \uB4F1\uC758 \uAD8C\uB9AC\uB97C \uCE68\uD574\uD558\uB294 \r\n    \uD589\uC704\uB97C \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2467 \uD68C\uC6D0\uC740 \uD574\uD0B9 \uB610\uB294 \uCEF4\uD4E8\uD130 \uBC14\uC774\uB7EC\uC2A4\uB97C \uC720\uD3EC\uD558\uB294 \uC77C, \uD0C0\uC778\uC758 \uC758\uC0AC\uC5D0 \uBC18\uD558\uC5EC \uAD11\uACE0\uC131 \uC815\uBCF4\uB4F1 \uC77C\uC815\uD55C \r\n    \uB0B4\uC6A9\uC744 \uC9C0\uC18D\uC801\uC73C\uB85C \uC804\uC1A1\uD558\uB294 \uD589\uC704\uB97C \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2468 \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uC758 \uC6B4\uC601\uC5D0 \uC9C0\uC7A5\uC744 \uC8FC\uAC70\uB098 \uC904 \uC6B0\uB824\uAC00 \uC788\uB294 \uC77C\uCCB4\uC758 \uD589\uC704, \uAE30\uD0C0 \uAD00\uACC4 \uBC95\uB839\uC5D0 \uC704\uBC30\uB418\uB294\r\n    \uC77C\uC744 \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4. \uC81C 18 \uC870 (\uC591\uB3C4 \uAE08\uC9C0)\r\n  \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uC758 \uC774\uC6A9\uAD8C\uD55C, \uAE30\uD0C0 \uC774\uC6A9\uACC4\uC57D\uC0C1 \uC9C0\uC704\uB97C \uD0C0\uC778\uC5D0\uAC8C \uC591\uB3C4, \uC99D\uC5EC\uD560 \uC218 \uC5C6\uC73C\uBA70, \uAC8C\uC2DC\uBB3C\uC5D0 \r\n  \uB300\uD55C \uC800\uC791\uAD8C\uC744 \uD3EC\uD568\uD55C \uBAA8\uB4E0 \uAD8C\uB9AC \uBC0F \uCC45\uC784\uC740 \uC774\uB97C \uAC8C\uC2DC\uD55C \uD68C\uC6D0\uC5D0\uAC8C \uC788\uC2B5\uB2C8\uB2E4.\r\n\uC81C 19 \uC870 ( \uACC4\uC57D\uD574\uC9C0 \uBC0F \uC774\uC6A9\uC81C\uD55C)\r\n\u2460 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC774 \uC57D\uAD00\uC758 \uB0B4\uC6A9\uC744 \uC704\uBC18\uD558\uACE0, \uD68C\uC0AC \uC18C\uC815\uC758 \uAE30\uAC04 \uC774\uB0B4\uC5D0 \uC774\uB97C \uD574\uC18C\uD558\uC9C0 \uC544\uB2C8\uD558\uB294 \uACBD\uC6B0 \r\n    \uC11C\uBE44\uC2A4 \uC774\uC6A9\uACC4\uC57D\uC744 \uD574\uC9C0\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uC81C \u2460\uD56D\uC5D0 \uC758\uD574 \uD574\uC9C0\uB41C \uD68C\uC6D0\uC774 \uB2E4\uC2DC \uC774\uC6A9\uC2E0\uCCAD\uC744 \uD558\uB294 \uACBD\uC6B0 \uC77C\uC815\uAE30\uAC04 \uADF8 \uC2B9\uB099\uC744 \uC81C\uD55C\uD560 \uC218\r\n    \uC788\uC2B5\uB2C8\uB2E4. \r\n\r\n\u2462 \uD68C\uC6D0\uC774 \uC774\uC6A9\uACC4\uC57D\uC744 \uD574\uC9C0\uD558\uACE0\uC790 \uD558\uB294 \uB54C\uC5D0\uB294 \uD68C\uC6D0 \uBCF8\uC778\uC774 \uC11C\uBE44\uC2A4 \uB610\uB294 \uC804\uC790\uC6B0\uD3B8\uB97C \uD1B5\uD558\uC5EC \uD574\uC9C0\r\n    \uC2E0\uCCAD\uC744 \uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\uC81C 20 \uC870 (\uC190\uD574\uBC30\uC0C1)\r\n  \uD68C\uC0AC\uAC00 \uC81C\uACF5\uB418\uB294 \uC11C\uBE44\uC2A4\uC640 \uAD00\uB828\uD558\uC5EC \uD68C\uC6D0\uC5D0\uAC8C \uC5B4\uB5A0\uD55C \uC190\uD574\uAC00 \uBC1C\uC0DD\uD558\uB354\uB77C\uB3C4 \uD68C\uC0AC\uC758 \uC911\uB300\uD55C \uACFC\uC2E4\uC5D0\r\n  \uC758\uD55C \uACBD\uC6B0\uB97C \uC81C\uC678\uD558\uACE0 \uC774\uC5D0 \uB300\uD558\uC5EC \uCC45\uC784\uC744 \uBD80\uB2F4\uD558\uC9C0 \uC544\uB2C8\uD569\uB2C8\uB2E4. \uB2E8, \uC190\uD574\uBC30\uC0C1\uC758 \uBC94\uC704\uB294 \uBCC4\uB3C4\uB85C \r\n  \uD68C\uC0AC\uC758 \uADDC\uC815\uC5D0 \uB530\uB985\uB2C8\uB2E4. \uC81C 21 \uC870 (\uBA74\uCC45 \uC870\uD56D)\r\n\u2460 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC774 \uC11C\uBE44\uC2A4\uC5D0 \uAC8C\uC7AC\uD55C \uC815\uBCF4, \uC790\uB8CC, \uC0AC\uC2E4\uC758 \uC815\uD655\uC131, \uC2E0\uB8B0\uC131\uB4F1  \uB0B4\uC6A9\uC5D0 \uAD00\uD558\uC5EC\uB294 \uC5B4\uB5A0\uD55C \r\n    \uCC45\uC784\uB3C4  \uBD80\uB2F4\uD558\uC9C0 \uC544\uB2C8\uD558\uACE0 \uC11C\uBE44\uC2A4 \uC790\uB8CC\uC5D0 \uB300\uD55C \uCDE8\uC0AC\uC120\uD0DD \uB610\uB294 \uC774\uC6A9\uC73C\uB85C \uBC1C\uC0DD\uD558\uB294 \uC190\uD574\uB4F1\uC5D0 \uB300\r\n    \uD574\uC11C\uB294 \uCC45\uC784\uC774 \uBA74\uC81C\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC774 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uC5EC \uAE30\uB300\uD558\uB294 \uC190\uC775\uC774\uB098 \uC11C\uBE44\uC2A4\uB97C \uD1B5\uD558\uC5EC \uC5BB\uC740 \uC790\uB8CC\uB85C \uC778\uD55C  \uC190\uD574\uC5D0\r\n    \uAD00\uD558\uC5EC \uCC45\uC784\uC774 \uBA74\uC81C\uB429\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uD68C\uC6D0 \uC0C1\uD638\uAC04 \uB610\uB294 \uD68C\uC6D0\uACFC \uC81C 3\uC790 \uC0C1\uD638\uAC04\uC5D0 \uC11C\uBE44\uC2A4\uB97C \uB9E4\uAC1C\uB85C \uBC1C\uC0DD\uD55C \uBD84\uC7C1\uC5D0 \uB300\uD574\uC11C\uB294 \uAC1C\uC785\uD560\r\n    \uC758\uBB34\uAC00 \uC5C6\uC73C\uBA70 \uC774\uB85C \uC778\uD55C \uC190\uD574\uB97C \uBC30\uC0C1\uD560 \uCC45\uC784\uB3C4 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2463 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC758 \uADC0\uCC45\uC0AC\uC720\uB85C \uC778\uD558\uC5EC \uC11C\uBE44\uC2A4 \uC774\uC6A9\uC758 \uC7A5\uC560\uAC00 \uBC1C\uC0DD\uD55C \uACBD\uC6B0\uC5D0\uB294 \uCC45\uC784\uC774 \uBA74\uC81C \uB429\uB2C8\uB2E4.\r\n\r\n\u2464 \uD68C\uC0AC\uB294 \uC81C\uD734\uC0AC\uAC00 \uC81C\uACF5\uD558\uB294 \uBD80\uAC00\uC11C\uBE44\uC2A4\uC758 \uD488\uC9C8 \uBC0F \uC7A5\uC560\uC5D0 \uB300\uD55C \uCC45\uC784\uC740 \uBA74\uC81C \uB429\uB2C8\uB2E4.\r\n\r\n\u2465 \uBCF8 \uC57D\uAD00\uC758 \uADDC\uC815\uC744 \uC704\uBC18\uD568\uC73C\uB85C \uC778\uD558\uC5EC \uD68C\uC0AC\uC5D0 \uC190\uD574\uAC00 \uBC1C\uC0DD\uD558\uAC8C \uB418\uB294 \uACBD\uC6B0, \uC774 \uC57D\uAD00\uC744 \uC704\uBC18\uD55C \uD68C\uC6D0\uC740\r\n    \uD68C\uC0AC\uC5D0 \uBC1C\uC0DD\uD558\uB294 \uBAA8\uB4E0 \uC190\uD574\uB97C  \uBC30\uC0C1\uD558\uC5EC\uC57C \uD558\uBA70, \uB3D9 \uC190\uD574\uB85C\uBD80\uD130 \uD68C\uC0AC\uB97C \uBA74\uCC45\uC2DC\uCF1C\uC57C  \uD569\uB2C8\uB2E4. \uC81C 22 \uC870 ( \uBD84\uC7C1\uC758 \uD574\uACB0 )\r\n\u2460 \uD68C\uC0AC\uC640 \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uC640 \uAD00\uB828\uD558\uC5EC \uBC1C\uC0DD\uD55C \uBD84\uC7C1\uC744 \uC6D0\uB9CC\uD558\uAC8C \uD574\uACB0\uD558\uAE30 \uC704\uD558\uC5EC \uD544\uC694\uD55C \uBAA8\uB4E0 \uB178\uB825\uC744\r\n    \uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uBAA8\uB4E0 \uB178\uB825\uC5D0\uB3C4 \uBD88\uAD6C\uD558\uACE0 \uC18C\uC1A1\uC774 \uC81C\uAE30\uB420 \uACBD\uC6B0, \uC18C\uC1A1\uC740 \uD68C\uC0AC\uAC00 \uAD00\uD560\uD558\uB294 \uBC95\uC6D0\uC758 \uAD00\uD560\uB85C \uD569\uB2C8\uB2E4.\r\n \t \r\n\uBD80\uCE59\r\n \t\r\n\uC81C 1 \uC870 ( \uC2DC\uD589\uC77C)\r\n    \uC774 \uC57D\uAD00\uC740 2004\uB144 10\uC6D4 05\uC77C\uBD80\uD130 \uC2DC\uD589\uD569\uB2C8\uB2E4.");
		yakguan.setViewportView(newAccountTextArea);
		yakguan.setBorder(new EmptyBorder(0, 0, 0, 0));

		modifyButton = new JButton(modifyButtonImage); // ������ư
		modifyButton.setBounds(531, 701, 145, 44);
		newAccountPanel.add(modifyButton);

		deleteButton = new JButton(deleteButtonImage); // ������ư
		deleteButton.setBounds(675, 701, 145, 44);
		newAccountPanel.add(deleteButton);
		loginButton.addActionListener(this);
		GamereadyButton.addActionListener(this);
		OutRoom.addActionListener(this);
		newAccountButton.addActionListener(this);
		newAccountCancelButton.addActionListener(this);
		NewAccountLoginButton.addActionListener(this);
		deleteButton.addActionListener(this);
		modifyButton.addActionListener(this);
		MakePersonMenuPop.addActionListener(this);
		CaptureMenu.addActionListener(this);
		exitMenu.addActionListener(this); // ����
		newsButton.addActionListener(this); // ����
		baookpans.addMouseListener(new mlistener());

		mainPanel.add("login", loginPanel);
		mainPanel.add("roomGUI", roomGUIPanel);

		// ���� ��ư �̹�����
		ImageIcon NewRoomImage = new ImageIcon("src/images/newRoomM.jpg");
		ImageIcon MakeRoomImage = new ImageIcon("src/images/RoomMakeRoom.jpg");
		ImageIcon cancelImage = new ImageIcon("src/images/makeCancel.jpg");
		// ���� ��ư �̹�����

		NewRoom = new JButton(NewRoomImage); // ���ο�� ����� (Ȱ��ȭ)
		NewRoom.setBounds(724, 537, 218, 90);
		roomGUIPanel.add(NewRoom);
		NewRoom.setEnabled(true);

		MakeRoom = new JButton(MakeRoomImage); // �����
		MakeRoom.setBounds(667, 483, 150, 44);
		roomGUIPanel.add(MakeRoom);
		MakeRoom.setEnabled(false);

		cancel = new JButton(cancelImage); // ����� ���
		cancel.setBounds(835, 483, 150, 44);
		roomGUIPanel.add(cancel);
		cancel.setEnabled(false);

		roomTitle = new JTextField();
		roomTitle.setBounds(667, 452, 318, 21);
		roomGUIPanel.add(roomTitle);
		roomTitle.setEnabled(false);
		roomTitle.setColumns(10);

		TodaysMessageLabel = new JLabel("�̳׶� 399�δ� Ŀ������͸� ���� �� �����ϴ�. ");
		TodaysMessageLabel.setBounds(200, 739, 520, 15);
		roomGUIPanel.add(TodaysMessageLabel);

		nowTime = new JLabel();
		nowTime.setBounds(662, 427, 318, 15);
		roomGUIPanel.add(nowTime);
		todayArrayList = new tips(); // extend�� ����

		Thread thx = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (true) {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int a = (int) (Math.random() * 30);
					Date date = new Date();
					SimpleDateFormat stm = new SimpleDateFormat("aa hh �� mm �� ss");
					stm.format(date);
					TodaysMessageLabel.setText(todayArrayList.get(a));
					nowTime.setText("���ݽð���: " + stm.format(date) + "�� �Դϴ�.");

				}
			}
		};
		thx.start();
		cancel.addActionListener(this);

		// ==============�׼ǹ�ư ===============
		MakeRoom.addActionListener(this);
		NewRoom.addActionListener(this);
		mainPanel.add("battleNetPanel", battleNetPanel);
		mainPanel.add("newAccountPanel", newAccountPanel);

		// --------------mainPanelCardlayout����----------------
		newAccountCancelButton.addActionListener(this);
		Platform.runLater(new Runnable() { // this will run initFX as
			// JavaFX-Thread
			@Override
			public void run() {
				webViewAD(AD_Panel_2);
			}
		});
		// --------------mainPanelCardlayout����----------------

		// bt.setBackground(Color.red);
		// bt.setBorderPainted(false);
		// bt.setFocusPainted(false);
		// ������ ����
		Thread th = new Thread(this);
		th.start();
		// ������ ����
		setVisible(true);
		setSize(1035, 858);
		setResizable(false); // �������� ����

	}

	@Override
	// ==================== 1. ��ư �κ�===========

	public void actionPerformed(ActionEvent e) { // actionPerformed ��ư �κ�
		// TODO Auto-generated method stub

		if (e.getSource() == NewRoom) { // ����:: ���ο� �� ����� ���ý�
			roomTitle.setEnabled(true);
			MakeRoom.setEnabled(true);
			cancel.setEnabled(true);

		} else if (e.getSource() == cancel) { // ����:: �� ����� ���
			roomTitle.setText("");
			roomTitle.setEnabled(false);
			cancel.setEnabled(false);
			MakeRoom.setEnabled(false);
		} else if (e.getSource() == MakeRoom) { // ���� :: �� �����
			String x = roomTitle.getText(); // �ӽ�
			x.replaceAll(" ", ""); // ��ĭ����
			if (x.equals("")) {
			} else { //
				roommake(); // �� ����� ��
			}
		} else if (e.getSource() == loginButton) { // �α��� :: �α��� ��ư
			loginSend(); // �α��� �ϱ�

		} else if (e.getSource() == GamereadyButton) { // ���ӹ�::���ӷ���
			boolean readyOne = true;
			for (int i = 0; i < makeRoomGet.size(); i++) {
				if (makeRoomGet.get(i).getMakeName().equals(id)) {
					if (makeRoomGet.get(i).getInwon() == 1) {
						readyOne = false;
					}
				}
			}
			if (readyOne == false) {
				JOptionPane.showConfirmDialog(null, "���� �ο��� 1��ۿ� ���� �ʾҽ��ϴ�.");
			} else {
				GamereadyButton.setEnabled(false);
				gameData gameReadyDate = new gameData(gameData.ready, id, makeRoomGet);
				mg.send(gameReadyDate);
			}

		} else if (e.getSource() == OutRoom) { // ���ӹ�:: �� ������
			if (gameStart == true) { // �����߿��� �� ������ ����
				JOptionPane.showConfirmDialog(null, "�����߿��� ���� ������ �����ϴ�.");
			} else {
				roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, false);
				mg.send(outRoomDate);
				roomData refreshRoomWait = new roomData(roomData.roomRefresh, null);
				card.show(mainPanel, "roomGUI");
			}

		} else if (e.getSource() == NewAccountLoginButton) { // ���::����ī��Ʈ�г�
			card.show(mainPanel, "newAccountPanel");
		} else if (e.getSource() == newAccountButton) { // New Account! �����ϱ�
			newAccountInsert();
		} else if (e.getSource() == newAccountCancelButton) { // ��� -> �α���ȭ����
			card.show(mainPanel, "login");
			pwIDField.setText(""); // �ʵ� �ʱ�ȭ
			passWordField.setText(""); // �ʵ� �ʱ�ȭ
		} else if (e.getSource() == modifyButton) { // ��������::�����ϱ�
			modifyAccount();
		} else if (e.getSource() == deleteButton) { // ��������::�����
			deleteAccount();
		} else if (e.getSource() == CaptureMenu) { // ������� ĸ���ϱ� ���
			capture();
		} else if (e.getSource() == exitMenu) {// ������
			System.exit(0);
		} else if (e.getSource() == MakePersonMenuPop) { // ��������
			new makePersons().setVisible(true);
		} else if (e.getSource() == newsButton) { // �ѱ� ������ȸ ���� �ҷ�����
			new omokNews().setVisible(true);
		}
	}

	// ==================== 2. ���콺 �κ�===========
	public class mlistener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			System.out.println("���콺 ����");
			if (e.getComponent() == gameRoom) { // ���õ� ��
				if (e.getClickCount() == 2) { // ����Ŭ���� �Ҷ�
					int temp = gameRoom.getSelectedIndex();
					System.out.println(temp);
					if (makeRoomGet.get(temp).getInwon() == 1) {
						// �ο��� 1�̸� ���� �ִ�.
						roomData dataIn = new roomData(roomData.roomIn, id, makeRoomGet.get(temp).getMakeName());
						// makeRoomGet�� ���� �� ����Ʈ�� ���� array
						// �����, �� ���̵�, ���� ���� ���̵�
						mg.send(dataIn);
						doolSelect = false; // ���� ����� �������� �Ѵ�.
						gameplayerSelect = false; // ���� ���߿� �÷��� �Ѵ�.
						card.show(mainPanel, "battleNetPanel");

					} else if (makeRoomGet.get(temp).getInwon() == 2) {
						int question = JOptionPane.showConfirmDialog(null, "�ο��� �� ���� �� �� �����ϴ�");

					}

				}
			} else if (e.getComponent() == baookpans) { // �ٵϵ� �δ� �κ� baookpans

				if (gameplayerSelect == false) { // �ڱ� ������ �ƴ϶��
					JOptionPane.showConfirmDialog(null, "���� ����� ������ �ƴմϴ�.");
				} else if (gameplayerSelect == true) { // �δ°� �����ϸ�
					stoneX = e.getX(); // x ��
					stoneY = e.getY(); // y ��

					if (gameStart) {
						System.out.println(stoneX + " | " + stoneY);
						boolean stoneons = StoneON(); // ������ ������ �� �ø���
						if (stoneons) { // true
							boolean check33 = checkStone33();
							System.out.println(check33);
							if (check33 == false) {
								JOptionPane.showConfirmDialog(null, "�װ��� 33�̶� �Ѽ� �����ϴ�.");
								fleshBack(); // ������
							} else {
								StoneOnSend();
								spriteStone(stoneX, stoneY); // �׷��� ��� (�ȿ� int����
																// ����
																// �Ѵ�)
							}
						} else {

						}

					} else {
						JOptionPane.showConfirmDialog(null, "���� �κд� ready�� ���� �ʾҽ��ϴ�.");
					}

				}

			}

		}
	} // mlisener ed

	public void resetPlayer() { // player Reset û�ҿ�
		player1 = ""; // �÷��̾� 1 String�ʱ�ȭ
		player2 = ""; // �÷��̾� 2 String�ʱ�ȭ
		gameStart = false; // �Ѵ� Ready���� ����
		gameplayerSelect = true; // �����÷��� �ʱ�ȭ
		gameWin = 0; // ���� �̱�� �� üũ
		int[][] refreshOmokTable = new int[23][23];
		this.omokTable = refreshOmokTable;
		GamereadyButton.setEnabled(true);
		BattelChat.setText("");
		resetStoneBadookpan();
		player1pLabel.setText("");
		doolSelect = false;

	}

	public void winDefeatConfirm() {
		// TODO Auto-generated method stub
		if (gameWin == 1) { // �Ͼᵹ�� �̱�
			int gameInduce = JOptionPane.showConfirmDialog(null, "�� ���� ���� �̰���ϴ�. ���� �����ðڽ��ϱ�?");
			if (gameInduce >= 0) {
				// �Ͼᵹ�� �̰�ٸ� ������ +1, ������ -1�� ��Ų��.
				if (id.equals(player1)) { // ������ �̰�ٸ� player1�� �̰�ٸ�
					gameData whiteScoreData = new gameData(gameData.winWhite, player1, 1, 0);
					mg.send(whiteScoreData); // �Ͼᵹ �¸� ���
					gameData blackScoreData = new gameData(gameData.defeatBlack, player2, 0, 1);
					mg.send(blackScoreData); // ������ �й� ���
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate);
				} else if (id.equals(player2)) { // ������ �̰�ٸ� player1�� �̰�ٸ�
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate);
				}
				resetPlayer();
				card.show(mainPanel, "roomGUI"); // �г� �Ѱ���
			}
		} else if (gameWin == 2) { // �������� �̱� ���
			int gameInduce2 = JOptionPane.showConfirmDialog(null, "�� ���� ���� �̰���ϴ�.");
			if (gameInduce2 >= 0) {

				if (id.equals(player1)) { // �������� �̰�ٸ� player2�� �̰�ٸ�
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate); // �� ������
				} else if (id.equals(player2)) { // �������� �̰�ٸ� player2�� �̰�ٸ�
					gameData blackScoreData = new gameData(gameData.winBlack, player2, 1, 0);
					mg.send(blackScoreData); // ������ �¸� ���
					gameData whiteScoreData = new gameData(gameData.defeatWhite, player1, 0, 1);
					mg.send(whiteScoreData); // �Ͼᵹ �й� ���
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate); // �� ������
				}
				resetPlayer(); // �÷��̾� �����Ϳ� true �ʱ�ȭ
				card.show(mainPanel, "roomGUI"); // �г� �Ѱ���
			}
		} // ���� Ȯ��
	}

	public void newAccountInsert() { // ���ο� ���� ����
		String id = pwIDField.getText();// id
		String pw = passWordField.getText(); // id
		if (id.equals("") || pw.equals("")) { // ���� �ϳ��� ������ �ִ� ���
			JOptionPane.showConfirmDialog(null, "������ �־ �Է��� �� �� �����ϴ�. ");
		} else { // ������ �ƴ϶��
			accountData data = new accountData(accountData.newAccount, id, pw, false);
			mg.send(data);
		}
		pwIDField.setText(""); // �ʵ� �ʱ�ȭ
		passWordField.setText(""); // �ʵ� �ʱ�ȭ
	}

	public void webViewAD(JFXPanel fxPanel) { // ������(���۾ֵ弾��)
		Group group = new Group();
		Scene scene = new Scene(group);
		AD_Panel_2.setScene(scene);
		WebView webView = new WebView();
		group.getChildren().add(webView);
		webView.setMinSize(320, 50);
		webView.setMaxSize(320, 50);
		// 320 50 size Google Adsense�� ���.
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://paran.dothome.co.kr/adon.htm");

	}

	private void deleteAccount() { // ��������
		String id = passWordField.getText(); // id
		String pw = pwIDField.getText();// password
		if (id.equals("") || pw.equals("")) { // ���� �ϳ��� ������ �ִ� ���
			JOptionPane.showConfirmDialog(null, "������ �ֽ��ϴ�.");
		} else { // ������ �ƴ϶��
			accountData data = new accountData(accountData.deleteAccount, id, pw, false);
			mg.send(data);
		}
		pwIDField.setText(""); // �ʵ� �ʱ�ȭ
		passWordField.setText(""); // �ʵ� �ʱ�ȭ
	}

	private void modifyAccount() { // ��������
		String id = pwIDField.getText();// id
		String pw = passWordField.getText(); // pw
		if (id.equals("") || pw.equals("")) { // ���� �ϳ��� ������ �ִ� ���
			JOptionPane.showConfirmDialog(null, "������ �־ �Է��� �� �� �����ϴ�. ");
		} else { // ������ �ƴ϶��
			accountData data = new accountData(accountData.modifiyAccount, id, pw, false);
			mg.send(data);
		}
		pwIDField.setText(""); // �ʵ� �ʱ�ȭ
		passWordField.setText(""); // �ʵ� �ʱ�ȭ
	}

	public void refresh() { // �α��ν� ���
		roomData data = new roomData(roomData.loginAc, this.id, 0, 0, null, null); // ����
		mg.send(data);
		// ����Ʈ ���� ���� ������
	}

	public void roommake() { // �� ����� ����� ��� (�Ͼᵹ, ���÷���)
		makeRoom mr = new makeRoom(roomTitle.getText(), id, null, 1, false, false);
		roomData dataBattleRoomMake = new roomData(roomData.roomMake, id, "", mr, null, null, false, false);
		// ��������, ���̵�, ���� ���̵�, �渮��Ʈ, ?, 1����, 2����
		mg.send(dataBattleRoomMake);
		card.show(mainPanel, "battleNetPanel");
		player1 = this.id; // player1�� ������ ���� ���� ����̴�.
		gameplayerSelect = true; // ���÷��̾�
		doolSelect = true; // �� ���� (�Ͼᵹ)
		player1pLabel.setText(id + "�� ����:: " + winme + "�� | " + defeatme + "��");

	}

	public void conn() { // ���� ���� �޼ҵ�
		try {
			socket = new Socket("localhost", 8888);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void login(loginData dat) { // �����忡�� ���� ������ ����
		System.out.println(dat.getId() + " | " + dat.getPw() + " | " + dat.isLoginBoolean());
		if (dat.isLoginBoolean()) {
			winme = dat.getAct().getWin();
			defeatme = dat.getAct().getDefeat();
			card.show(mainPanel, "roomGUI"); // �г� �Ѱ���
		} else {
			loginStatusLabel.setText("�߸��� ���̵� Ȥ�� ��й�ȣ�� �Է��ϼ̽��ϴ�.");
		}
	}

	public void loginSend() { // �α��� �� ���Ǵ� �޼ҵ�
		String id = IDField.getText(); // id
		this.id = id; // �� ���̵� ���� ������ ���� �� ��������
		String pw = pwField.getText();// password
		if (id.equals("") || pw.equals("")) { // ���� ����
			JOptionPane.showConfirmDialog(null, "������ �Է��� �� �����ϴ�. ");
		} else {
			System.out.println("loginSend: " + id);
			loginData data = new loginData(loginData.login, id, pw, false, null);
			System.out.println(data);
			mg.send(data); // send
			refresh();
		}

	}

	public void chat() { // ä��
		String chat = chatTextField.getText(); // text String

		if (chat.equals("")) { // ��ĭ ä�� ����

		} else if (chat.substring(0, 1).equals("@")) { // �ӼӸ� ���
			String chatGet = chat.substring(chat.indexOf(" ")); // ä�� ����
			String idOpposing = chat.substring(1, chat.indexOf(" "));
			roomData chatPerson = new roomData(roomData.p2pchat, id, idOpposing, chatGet);
			mg.send(chatPerson); // output ������
			chatTextField.setText(""); // �ʱ�ȭ
		} else { // ä��
			roomData textData = new roomData(roomData.Allchat, id, chat);
			mg.send(textData); // output ������
			chatTextField.setText(""); // �ʱ�ȭ
		}

	}

	public void chatGame() { // ���ӻ��ÿ��� ä��
		String chat = BattelChat.getText(); // text String
		String id = this.id;
		if (chat.equals("")) { // ��ĭ ä�� ����
		} else { // ä��
			gameData textData = new gameData(gameData.chatPerson, id, player1, chat, player2);
			mg.send(textData); // output ������
			BattelChat.setText(""); // �ʱ�ȭ
		}

	}

	public void capture() { // ĸ��
		try {
			Robot robot = new Robot();
			// �� ����� ȭ���� ũ�⸦ �������� ���
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			// ������� ȭ���� selectRect�� ����,���� ǥ��
			// Rectangle selectRect = new Rectangle((int) screen.getWidth(),
			// (int) screen.getHeight());
			Rectangle selectRect = new Rectangle(1035, 858);
			for (int i = 0; i < 1; i++) {
				BufferedImage buffimg = robot.createScreenCapture(selectRect);
				File screenfile = new File("c:/screen" + i + ".jpg");
				ImageIO.write(buffimg, "jpg", screenfile);
			}
			JOptionPane.showConfirmDialog(null, "c:/�� screen.jpg�� �����Ǿ����ϴ�.");
		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refreshConnectList(roomData dat) {// �α��� �� ����Ʈ �޾ƿ���

		FirstArray = dat.getUsernames();
		nowList.setListData(FirstArray.toArray()); // �����̵��
		System.out.println(FirstArray.toString());
		makeRoomGet = dat.getMakeroom();
		gameRoom.setListData(makeRoomGet.toArray()); // ���Ӵ����

	}

	public void StoneOnSend() {
		int induceOnX = ((stoneX) / intXInt) + 2; // X�� ���� ���밪 1~19
		int induceOnY = ((stoneY) / intYInt) + 2; // Y�� ���� ���밪 1~19
		if (doolSelect == true) { // �Ͼᵹ���ʶ��.

			omokTable[induceOnY][induceOnX] = 1;
			// table�� �ִ� ���ڸ� �ٲ۴�. �������� �Ʒ��� if������ ó��
			count++;
			// doolSelect = false;
			// ������ ���� �ٲٱ� ����
			gameData whiteDoolData = new gameData(gameData.whiteGo, id, player1, player2, omokTable, true, stoneX,
					stoneY);
			gameplayerSelect = false;
			mg.send(whiteDoolData);

		}

		else if (doolSelect == false) { // ������
			omokTable[induceOnY][induceOnX] = 2;
			// ������ ������(2�� �������Ѵ�)
			count++;
			// doolSelect = true; // �Ͼ�� ��
			gameData blackDoolData = new gameData(gameData.blackGo, id, player1, player2, omokTable, false, stoneX,
					stoneY);
			gameplayerSelect = false;
			mg.send(blackDoolData);

		} // if end
	}

	public boolean StoneON() // �� �ø��� intTable�� �����ϴ� ���
	{
		boolean flags = true;
		// ù �������� x = 46, y = 63

		if (gameStart == true) { // �Ѵ� ���� �Ǹ� gameStart�� true�� �ȴ�.
			for (int i = 2; i < 21; i++) { // x
				int induceOnX = ((stoneX) / intXInt) + 2; // X�� ���� ���밪 1~19
				for (int j = 2; j < 21; j++) {
					int induceOnY = ((stoneY) / intYInt) + 2; // Y�� ���� ���밪 1~19
					if (i == induceOnX && j == induceOnY) {
						// �ΰ��� for ���� ���� ���
						if (omokTable[induceOnY][induceOnX] == 0) {
							// table��0�� ���.

							if (doolSelect == true) { // �Ͼᵹ���ʶ��.

								omokTable[induceOnY][induceOnX] = 1;
								// table�� �ִ� ���ڸ� �ٲ۴�. �������� �Ʒ��� if������ ó��
								count++;
								// doolSelec�� ����t = false;
								// ������ ���� �ٲ�

							} else if (doolSelect == false) { // ������
								omokTable[induceOnY][induceOnX] = 2;
								// ������ ������(2�� �������Ѵ�)
								count++;
								// doolSelect = true; // �Ͼ�� ��

							} // if end
						} else {
							JOptionPane.showConfirmDialog(null, "�װ��� �� �� �����ϴ�. �̹� ���� �ֽ��ϴ�.");
							flags = false;
						}
						// ��������� ������ ������ �κ�
					}
				} // 2�� for�� end
			} // 1�� for�� end

		}
		return flags;
	}

	public void spriteStone(int xs, int ys) // �ٵ� ���� �ø��� �׷��� ���
	{
		for (int i = 0; i < 21; i++) // x
		{
			int induceOnX = (xs / intXInt); // X�� ���� ���밪 1~19

			for (int j = 0; j < 21; j++) {
				int induceOnY = (ys / intYInt); // Y�� ���� ���밪 1~19
				int x = 8;
				int y = 8;

				if (i == induceOnX && j == induceOnY) {
					if (omokTable[induceOnY + 2][induceOnX + 2] == 1) {
						// 2��° ó��, ���⼭ ���� �׷������� �ø��� �۾��� �Ѵ�
						makeWhite();
						baookpans.add(whiteStoneOn);
						whiteStoneOn.setBounds(induceOnX * 37 + x, induceOnY * 37 + y, 34, 34);
					} else if (omokTable[induceOnY + 2][induceOnX + 2] == 2) {
						makeBlack();
						baookpans.add(blackStoneOn);
						blackStoneOn.setBounds(induceOnX * 37 + x, induceOnY * 37 + y, 34, 34);
					}

					for (int q = 2; q < 21; q++) // System ���� �����ִ� �ӽ� UI
					{
						System.out.println();

						for (int w = 2; w < 21; w++) {
							System.out.print(omokTable[q][w]);
						}
					}
				} // if

			} // for
		} // for
	}

	public void resetStoneBadookpan() { // �ٵ��� �ʱ�ȭ
		// refreshStone = new ImageIcon("src/images/badookpan.jpg");
		// refreshStoneOn = new refreshStoneL(refreshStone); // extends �� ����
		// baookpans.add(refreshStoneOn);
		// refreshStoneOn.setBounds(0, 0, 718, 718);
		baookpans.removeAll();

	}

	// public void resetStoneBadookpan() { // �ٵ��� �ʱ�ȭ
	// refreshStone = new ImageIcon("src/images/refresh.jpg");
	// refreshStoneOn = new refreshStoneL(refreshStone); // extends �� ����
	// baookpans.add(refreshStoneOn);
	// refreshStoneOn.setBounds(0, 0, 718, 718);
	//
	//
	// for (int i = 1; i <= 718; i++) { // x
	// int induceOnX = ((i) / 37) + 2; // X�� ���� ���밪 1~19
	// for (int j = 1; j <= 718; j++) {
	// int induceOnY = ((j) / 37) + 2; // Y�� ���� ���밪 1~19
	// refreshStoneOn.setBounds(induceOnX * 37 + 8, induceOnY * 37 + 8, 34, 34);
	// } // 2�� for�� end
	// } // 1�� for�� end
	//
	// }

	public void makeWhite() { // �Ͼᵹ �ø��� ���
		whiteStone = new ImageIcon("src/images/whiteStone.png");
		whiteStoneOn = new whiteStone(whiteStone);
	}

	public void makeBlack() { // ������ �ø��� ���
		blackStone = new ImageIcon("src/images/blackStone.png");
		blackStoneOn = new blackStone(blackStone); // extends �� ����
	}

	public void checkStone() // �ٵϵ� �˻�
	{
		int whiteXarrayCheck = 0; // X�� ȭ��Ʈ �˻�
		int blackXarrayCheck = 0;// X�� ���� �˻�
		int whiteYarrayCheck = 0;// Y�� ȭ��Ʈ �˻�
		int blackYarrayCheck = 0;// Y�� ���� �˻�
		int whitezLowArrayCheck = 0;// ���� �밢�� �˻� ȭ��Ʈ
		int blackzLowArrayCheck = 0; // ���� �밢�� �˻� ��
		int whitezUpArrayCheck = 0; // ���� �밢�� �˻� ȭ��Ʈ
		int blackzUpArrayCheck = 0; // ���� �밢�� �˻� ��

		for (int stoneX = 2; stoneX < 21; stoneX++) // x 0~18 stoneY|stoneX
		{
			for (int stoneY = 2; stoneY < 21; stoneY++) // y 0~18 ���� �˻�
			{
				for (int dool = 1; dool <= 2; dool++) {
					if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY + 1][stoneX] == dool
							&& omokTable[stoneY - 1][stoneX] == dool && omokTable[stoneY + 2][stoneX] == dool
							&& omokTable[stoneY - 2][stoneX] == dool) {
						// black & white ���� �˻�
						if (dool == 1) {
							whiteYarrayCheck++;
						}

						else if (dool == 2) {
							blackYarrayCheck++;
						}
					}

					else if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY][stoneX + 1] == dool
							&& omokTable[stoneY][stoneX + 2] == dool && omokTable[stoneY][stoneX - 1] == dool
							&& omokTable[stoneY][stoneX - 2] == dool) {
						// black & white ���� �˻�

						if (dool == 1) {
							whiteXarrayCheck++;
						}

						else if (dool == 2) {
							blackXarrayCheck++;
						}
					}

					else if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY + 1][stoneX + 1] == dool
							&& omokTable[stoneY + 2][stoneX + 2] == dool && omokTable[stoneY - 1][stoneX - 1] == dool
							&& omokTable[stoneY - 2][stoneX - 2] == dool) {
						// black & white �ϰ� �밢�� �˻�
						if (dool == 1) {
							whitezLowArrayCheck++;
						}

						else if (dool == 2) {
							blackzLowArrayCheck++;
						}
					}

					else if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY - 1][stoneX + 1] == dool
							&& omokTable[stoneY - 2][stoneX + 2] == dool && omokTable[stoneY + 1][stoneX - 1] == dool
							&& omokTable[stoneY + 2][stoneX - 2] == dool) {
						// white Stone ��� �밢�� �˻�
						if (dool == 1) {
							whitezUpArrayCheck++;
						}

						else if (dool == 2) {
							blackzUpArrayCheck++;
						}
					} // if end
				} // 1, 2 for
			}
		} // 1 / for

		System.out.println();
		System.out.println("�Ͼ�� ���� " + whiteXarrayCheck);
		System.out.println("������ ���� " + blackXarrayCheck);
		System.out.println("�Ͼ�� ���� " + whiteYarrayCheck);
		System.out.println("������ ���� " + blackYarrayCheck);
		System.out.println("�Ͼ�� �ο�밢��  " + whitezLowArrayCheck);
		System.out.println("������ �ο�밢��  " + blackzLowArrayCheck);
		System.out.println("�Ͼ�� ���밢��  " + whitezUpArrayCheck);
		System.out.println("������ ���밢��  " + blackzUpArrayCheck);
		System.out.println(count + "�� °");
		int whiteWin = whiteXarrayCheck + whiteYarrayCheck + whitezLowArrayCheck + whitezUpArrayCheck;
		int blackWin = blackXarrayCheck + blackYarrayCheck + blackzLowArrayCheck + blackzUpArrayCheck;
		if (whiteWin >= 1) { // �Ͼᵹ �¸�
			gameWin = 1;
		} else if (blackWin >= 1) { // ������ �
			gameWin = 2;
		} else {
			// 0�� �״�� �д�.
		}
		// 1�� ������ ���� �̱�� ��.
	}

	public boolean checkStone33() { // 33�ٵϵ� �˻� false�� ��� 33�� �ٽ� reback����ߤ� ��
		boolean gameChanege = true;
		for (int stoneiX = 2; stoneiX < 21; stoneiX++) // x 0~18 stoneY|stoneX
		{

			for (int stoneiY = 2; stoneiY < 21; stoneiY++) // y 0~18 ���� �˻�
			{
				int whiteXarrayCheck = 0; // X�� ȭ��Ʈ �˻�
				int blackXarrayCheck = 0;// X�� ���� �˻�
				int whiteYarrayCheck = 0;// Y�� ȭ��Ʈ �˻�
				int blackYarrayCheck = 0;// Y�� ���� �˻�
				int whitezLowArrayCheck = 0;// ���� �밢�� �˻� ȭ��Ʈ
				int blackzLowArrayCheck = 0; // ���� �밢�� �˻� ��
				int whitezUpArrayCheck = 0; // ���� �밢�� �˻� ȭ��Ʈ
				int blackzUpArrayCheck = 0; // ���� �밢�� �˻� ��
				for (int dool = 1; dool <= 2; dool++) {

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY + 1][stoneiX] == dool
							&& omokTable[stoneiY - 1][stoneiX] == dool) {
						// black & white ���� �˻�
						if (dool == 1) { // �Ͼ�� ����
							if (omokTable[stoneiY - 2][stoneiX] == 2 || omokTable[stoneiY + 2][stoneiX] == 2) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								whiteYarrayCheck++;
							}

						}

						else if (dool == 2) { // ������ ����

							if (omokTable[stoneiY - 2][stoneiX] == 1 || omokTable[stoneiY + 2][stoneiX] == 1) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								blackYarrayCheck++;
							}

						}
					}

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY][stoneiX + 1] == dool
							&& omokTable[stoneiY][stoneiX - 1] == dool) {
						// black & white ���� �˻�

						if (dool == 1) {
							if (omokTable[stoneiY][stoneiX - 2] == 2 || omokTable[stoneiY][stoneiX + 2] == 2) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								whiteXarrayCheck++;
							}

						}

						else if (dool == 2) {
							if (omokTable[stoneiY][stoneiX - 2] == 1 || omokTable[stoneiY][stoneiX + 2] == 1) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								blackXarrayCheck++;
							}

						}
					}

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY + 1][stoneiX + 1] == dool
							&& omokTable[stoneiY - 1][stoneiX - 1] == dool) {
						// black & white �ϰ� �밢�� �˻�
						if (dool == 1) {
							if (omokTable[stoneiY - 2][stoneiX - 2] == 2 || omokTable[stoneiY + 2][stoneiX + 2] == 2) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								whitezLowArrayCheck++;
							}

						}

						else if (dool == 2) {
							if (omokTable[stoneiY - 2][stoneiX - 2] == 1 || omokTable[stoneiY + 2][stoneiX + 2] == 1) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								blackzLowArrayCheck++;
							}

						}
					}

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY - 1][stoneiX + 1] == dool
							&& omokTable[stoneiY + 1][stoneiX - 1] == dool) {
						// white Stone ��� �밢�� �˻�
						if (dool == 1) {
							if (omokTable[stoneiY + 2][stoneiX - 2] == 2 || omokTable[stoneiY - 2][stoneiX + 2] == 2) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								whitezUpArrayCheck++;
							}

						}

						else if (dool == 2) {
							if (omokTable[stoneiY + 2][stoneiX - 2] == 1 || omokTable[stoneiY - 2][stoneiX + 2] == 1) {
								// ���ܿ� ��� ���� �ִٸ� �ƹ��� ��ġ�� ������ �ʴ´�.
							} else {
								blackzUpArrayCheck++;
							}

						}
					} // if end

				} // 1, 2 for
				int white33 = whiteXarrayCheck + whiteYarrayCheck + whitezLowArrayCheck + whitezUpArrayCheck;
				int black33 = blackXarrayCheck + blackYarrayCheck + blackzLowArrayCheck + blackzUpArrayCheck;
				if (white33 >= 2) { // �Ͼᵹ�� 33�̸�
					gameChanege = false;
				} else if (black33 >= 2) { // �������� 33�̸�
					gameChanege = false;
				}
				// System.out.println("33 ��Ȳ");
				// System.out.println(count + "�� °");
				// System.out.println("�Ͼ�� ���� " + whiteXarrayCheck);
				// System.out.println("������ ���� " + blackXarrayCheck);
				// System.out.println("�Ͼ�� ���� " + whiteYarrayCheck);
				// System.out.println("������ ���� " + blackYarrayCheck);
				// System.out.println("�Ͼ�� �ο�밢�� " + whitezLowArrayCheck);
				// System.out.println("������ �ο�밢�� " + blackzLowArrayCheck);
				// System.out.println("�Ͼ�� ���밢�� " + whitezUpArrayCheck);
				// System.out.println("������ ���밢�� " + blackzUpArrayCheck);
			}
		} // 1 / for

		return gameChanege; // false�� ������ ����
	}

	public void fleshBack() { // ������ & 33�� ��� �ٽ� ����ġ
		int induceOnX = ((stoneX) / intXInt) + 2; // X�� ���� ���밪 1~19
		int induceOnY = ((stoneY) / intYInt) + 2; // Y�� ���� ���밪 1~19
		if (doolSelect == true) { // �Ͼᵹ���ʶ��.
			omokTable[induceOnY][induceOnX] = 0;
		}

		else if (doolSelect == false) { // ������
			omokTable[induceOnY][induceOnX] = 0;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		mg = new clientManager(output, input);
		while (true) {
			Object getData = null;
			try {
				getData = mg.call(); // get
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getData instanceof loginData) { // �α��� �κ�
				// protocol, id, pw, loginboolean
				switch (((loginData) getData).getState()) {
				case loginData.login:
					login((loginData) getData);

					break;
				}

			} else if (getData instanceof roomData) { // ���� �κ�
				switch (((roomData) getData).getState()) {
				case roomData.loginAc:
					refreshConnectList((roomData) getData);
					System.out.println("�޴�����loninAc : " + ((roomData) getData).getUsernames());
					break;
				case roomData.Allchat: // ���뿡�� ��üä��
					System.out.println(((roomData) getData).getMessage());
					textArea.setCaretColor(Color.BLACK);
					textArea.append(((roomData) getData).getId() + " : " + ((roomData) getData).getMessage() + "\n");
					textArea.setCaretPosition(textArea.getDocument().getLength());
					// �׻� �ؽ�Ʈ �Ʒ��� ������
					// int state, String id, String message
					break;

				case roomData.p2pchat: // ���뿡�� ��üä��
					if (((roomData) getData).getIdOpposing().equals(id)) {
						textArea.setCaretColor(Color.red); // �ӼӸ��� RED
						textArea.append(((roomData) getData).getId() + " ���� ���� �ӼӸ� > "
								+ ((roomData) getData).getMessage() + "\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					} else if (((roomData) getData).getId().equals(id)) { // �ӼӸ���
																			// �������
																			// ä��â��
																			// ǥ��
						textArea.setCaretColor(Color.red);
						textArea.append(((roomData) getData).getIdOpposing() + "�Բ� " + ((roomData) getData).getId()
								+ " ���� �ӼӸ� > " + ((roomData) getData).getMessage() + "\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					}
					break;

				case roomData.roomMake: // ���ӹ� ����
					makeRoomGet = ((roomData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray());
					// ����Ʈ�� �����Ѵ�.
					break;

				case roomData.roomIn: // ���ӹ� ����
					makeRoomGet = ((roomData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray());
					// ���� ������ ������ player2�̴�.
					for (int index = 0; index < makeRoomGet.size(); index++) {
						if (makeRoomGet.get(index).getMakeName2().equals(id)) {
							// ���� ���� getmakename"2"�� ���ٸ�(�մ��̶��)
							player1 = makeRoomGet.get(index).getMakeName();
							player2 = makeRoomGet.get(index).getMakeName2();

						} else if (makeRoomGet.get(index).getMakeName().equals(id)) {
							// ���� ���� getmakename"1"�� ���ٸ�(�����̶��)
							player2 = makeRoomGet.get(index).getMakeName2();

						}
					}
					System.out.println(player2);
					// ����.

					break;
				case roomData.roomDestroy: // ���ӹ� ������
					System.out.println(((roomData) getData).getId());
					System.out.println(((roomData) getData).getMakeroom());
					makeRoomGet = ((roomData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray());

					break;
				case roomData.roomRefresh: // ����� �ʱ�ȭ
					ArrayList<account_tenshu> userslist = ((roomData) getData).getUsernames();
					nowList.setListData(userslist.toArray());
					break;

				}
			} else if (getData instanceof gameData) { // ���Ӻκ�

				switch (((gameData) getData).getState()) {
				case gameData.ready: // ����
					makeRoomGet = ((gameData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray()); // �ϴ� ����Ʈ ����
					for (int index = 0; index < makeRoomGet.size(); index++) {
						if (makeRoomGet.get(index).getMakeName().equals(id)
								|| makeRoomGet.get(index).getMakeName2().equals(id)) {
							// �� id ���������� �� ���̵���
							if (makeRoomGet.get(index).isPlayer1() == true
									&& makeRoomGet.get(index).isPlayer2() == true) {
								gameStart = true;
								BattelChat.setText("������ �����մϴ�.");
							}
						}
					}
					System.out.println("���� ���� gameStart" + gameStart);
					break;

				case gameData.whiteGo: // �Ͼ� ���� �ø��� ��� // �ȿ� true��
										// �ִ�.gameplayerSelect = true;

					if (((gameData) getData).getIdOpposing().equals(player1)
							|| ((gameData) getData).getplayer2().equals(player2)) { // 2��°
						// �������
						System.out
								.println("id: " + id + " | " + "player1: " + player1 + " | " + "player2 : " + player2);
						this.omokTable = ((gameData) getData).getOmokTable();
						int StoneWhiteX = ((gameData) getData).getX();
						int StoneWhiteY = ((gameData) getData).getY();
						spriteStone(StoneWhiteX, StoneWhiteY);

						checkStone(); // �� üũ, �й� �¸� ����
						winDefeatConfirm(); // ����Ȯ��
					}
					if (((gameData) getData).getplayer2().equals(id)) {
						// �÷��̾�2�� ���̵� ���� ���ٸ�(�������� �� ����̶��)
						// true�� �����Ѵ�.
						gameplayerSelect = true;
						System.out.println(gameplayerSelect);
					}
					break;
				case gameData.blackGo: // ���� ���� �ø��� ��� // �ȿ� false��
										// �ִ�.gameplayerSelect = true;

					if (((gameData) getData).getIdOpposing().equals(player1)
							|| ((gameData) getData).getplayer2().equals(player2)) {
						System.out
								.println("id: " + id + " | " + "player1: " + player1 + " | " + "player2 : " + player2);
						this.omokTable = ((gameData) getData).getOmokTable();
						int StoneBlackX = ((gameData) getData).getX();
						int StoneBlackY = ((gameData) getData).getY();
						spriteStone(StoneBlackX, StoneBlackY);

						checkStone(); // �� üũ, �й� �¸� ����
						winDefeatConfirm();
					}

					if (((gameData) getData).getIdOpposing().equals(id)) {
						// �÷��̾�1�� ���̵� ���� ���ٸ�(�Ͼᵹ�� �� ����̶��)
						// true�� �����Ѵ�.
						gameplayerSelect = true;
						System.out.println(gameplayerSelect);
					}

					break;
				case gameData.chatPerson: // ������ ���� ä��
					if (((gameData) getData).getIdOpposing().equals(id)
							|| ((gameData) getData).getplayer2().equals(id)) {
						// �����ϳ��� �� ���̵���
						System.out.println(((gameData) getData).getMessage());
						charTextArea.append(
								((gameData) getData).getId() + " : " + ((gameData) getData).getMessage() + "\n");
						charTextArea.setCaretPosition(charTextArea.getDocument().getLength());
					}
					// �˻�� ���� Ŭ���̾�Ʈ���� �Ѵ�.
					break;
				case gameData.winWhite:

					break;
				case gameData.defeatWhite:

					break;
				case gameData.winBlack:

					break;
				case gameData.defeatBlack:

					break;

				}
			} else if (getData instanceof accountData) { // ȸ������ �κ�
				switch (((accountData) getData).getState()) {
				case accountData.newAccount:
					if (((accountData) getData).isFlag()) {
						JOptionPane.showConfirmDialog(null, "���������� �ԷµǾ����ϴ�.");
						card.show(mainPanel, "login"); // �г� �ѱ��
					} else {
						JOptionPane.showConfirmDialog(null, "�ߺ��� ���̵� �־ ������ �������� �ʾҽ��ϴ�.");
					}
					break;

				case accountData.deleteAccount:
					if (((accountData) getData).isFlag()) { // true�� ���
						JOptionPane.showConfirmDialog(null, "���������� �����Ǿ����ϴ�.");
					} else {
						JOptionPane.showConfirmDialog(null, "���� �����̶� ������ �� �� �����ϴ�.");
					}
					break;
				case accountData.modifiyAccount:
					if (((accountData) getData).isFlag()) {
						JOptionPane.showConfirmDialog(null, "���������� �����Ǿ����ϴ�.");
					} else {
						JOptionPane.showConfirmDialog(null, "���� �����̶� ������ �� �� �����ϴ�.");
					}
					break;

				}
			}

		} // while

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainGUI();
	}
}
