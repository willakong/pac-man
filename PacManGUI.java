import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")	
//will worry about whether other components are in sync with the frame (extends JFrame)
public class PacManGUI extends JFrame implements ActionListener{

	//0. Variables and Features
	Board board = new Board();
	static String playerName = "Player";

	JPanel startPanel = new JPanel();
	JLabel startLogoImage = new JLabel(new ImageIcon("./images/pac-manlogo.png"));
	JTextField nameField = new JTextField();
	JLabel nameLabel;
	JLabel charLabel = new JLabel("Maximum 12 characters");
	JLabel charMoreLabel = new JLabel("Exceeded limit");
	JLabel startScreenImage = new JLabel(new ImageIcon("./images/pacmanLights.gif"));
	JLabel arrowkeysImage = new JLabel(new ImageIcon("./images/arrowkeys.png"));
	JLabel arrowkeysLabel = new JLabel("Use the arrow keys.");

	JPanel infoPanel = new JPanel();
	JLabel scoreLabel = new JLabel();
	JLabel highscoreLabel = new JLabel();
	JLabel livesLabel = new JLabel();
	static JLabel pauseLabel = new JLabel("PLAYING. Press 'Esc' to pause.");
	JLabel logoImage = new JLabel(new ImageIcon("./images/pacman_logo.gif"));

	JPanel statusPanel = new JPanel();
	JButton retryButton;
	JButton muteButton;
	static boolean isMute = false;
	JLabel playerLabel = new JLabel("Player");
	JTextArea playerText;
	JLabel ghostLabel = new JLabel("Ghosts");
	static JLabel ghostStatusLabel = new JLabel("Normal");
	JLabel pelletsLabel = new JLabel("Pellets");
	static JLabel pelletsStatusLabel;
	JLabel ghostsImage = new JLabel(new ImageIcon("./images/pacmanghosts.jpg"));
	JLabel cherryLabel = new JLabel(new ImageIcon("./images/Cherry.png"));
	static JLabel cherryCounterLabel = new JLabel("x " + Board.cherryCounter);
	JLabel bananaLabel = new JLabel(new ImageIcon("./images/Banana.png"));
	static JLabel bananaCounterLabel = new JLabel("x " + Board.bananaCounter);
	JLabel appleLabel = new JLabel(new ImageIcon("./images/Apple.png"));
	static JLabel appleCounterLabel = new JLabel("x " + Board.appleCounter);

	JMenuBar menuBar = new JMenuBar();
	JMenu gameMenu = new JMenu("Game");
	JMenuItem highscoresItem = new JMenuItem("Highscores");
	JMenuItem exitItem = new JMenuItem("Exit");
	JMenu helpMenu = new JMenu("Help");
	JMenuItem rulesItem = new JMenuItem("Rules");

	JFrame rulesFrame = new JFrame("Pac-Man 101");
	JPanel rulesPanel = new JPanel();
	JLabel rulesLabel1 = new JLabel("Controls");
	JLabel rulesLabel2 = new JLabel("Powerups");
	JLabel rulesLabel3 = new JLabel("Winning");
	JTextArea rulesText1;
	JTextArea rulesText2;
	JTextArea rulesText3;
	JLabel cherryImage = new JLabel(new ImageIcon("./images/Cherry.png"));
	JLabel bananaImage = new JLabel(new ImageIcon("./images/Banana.png"));
	JLabel appleImage = new JLabel(new ImageIcon("./images/Apple.png"));
	JLabel pacmanImage = new JLabel(new ImageIcon("./images/PacRightOpen.bmp"));
	JLabel foodImage1 = new JLabel(new ImageIcon("./images/StdFood.bmp"));
	JLabel foodImage2 = new JLabel(new ImageIcon("./images/StdFood.bmp"));
	JLabel arrow1 = new JLabel(new ImageIcon("./images/arrow.png"));
	JLabel arrow2 = new JLabel(new ImageIcon("./images/arrow.png"));
	JLabel arrow3 = new JLabel(new ImageIcon("./images/arrow.png"));

	JFrame highscoresFrame = new JFrame("Highscores");
	JPanel highscoresPanel = new JPanel();
	JLabel highscoresTitle = new JLabel("Records", SwingConstants.CENTER);
	JLabel highscoresFrameNameLabel = new JLabel("Name");
	JLabel highscoresFrameScoresLabel = new JLabel("Highscore");
	static JTextArea nameText;
	static JTextArea scoresText;

	static JFrame endFrame = new JFrame("Finished");
	JPanel endPanel = new JPanel();
	static JLabel endMessage = new JLabel("Game Over", SwingConstants.CENTER);
	JLabel endFrameNameLabel = new JLabel("Name");
	JLabel endFrameScoresLabel = new JLabel("Highscore");
	static JTextArea endNameText;
	static JTextArea endScoresText;
	JButton playagainButton = new JButton("Play Again");

	/**
	 * Constructor
	 */
	public PacManGUI(){

		//1. Start Panel
		startPanel.setLayout(null);

		startLogoImage.setBounds(10, 10, 580, 88);
		startScreenImage.setBounds(90, 330, 400, 150);
		arrowkeysImage.setBounds(210, 500, 170, 115);

		nameLabel = new JLabel("What is your name?", SwingConstants.CENTER);
		nameLabel.setForeground(Color.YELLOW);
		nameLabel.setFont(new Font("Elephant", Font.PLAIN, 30));
		nameLabel.setBounds(0, 195, 580, 30);

		nameField.setBounds(190, 240, 200, 30);
		nameField.setText(playerName);
		nameField.selectAll();
		nameField.setFont(new Font("Rockwell", Font.PLAIN, 18));
		nameField.addActionListener(this);

		charLabel.setForeground(Color.WHITE);
		charLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		charLabel.setBounds(205, 270, 200, 20);

		charMoreLabel.setForeground(Color.RED);
		charMoreLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		charMoreLabel.setBounds(235, 290, 100, 20);
		charMoreLabel.setVisible(false);

		arrowkeysLabel.setForeground(Color.WHITE);
		arrowkeysLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
		arrowkeysLabel.setBounds(230, 620, 200, 20);

		startPanel.setBounds(0, 0, 600, 700);
		startPanel.setBackground(Color.BLACK);
		startPanel.setFocusable(false);
		startPanel.add(startLogoImage);
		startPanel.add(startScreenImage);
		startPanel.add(arrowkeysImage);
		startPanel.add(nameLabel);
		startPanel.add(nameField);
		startPanel.add(charLabel);
		startPanel.add(charMoreLabel);
		startPanel.add(arrowkeysLabel);

		//2. Information Panel
		infoPanel.setLayout(null);

		scoreLabel = Board.scoreLabel;
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Elephant", Font.PLAIN, 20));
		scoreLabel.setBounds(20, 5, 200, 30);

		highscoreLabel = Board.highscoreLabel;
		highscoreLabel.setForeground(Color.WHITE);
		highscoreLabel.setFont(new Font("Elephant", Font.PLAIN, 20));
		highscoreLabel.setBounds(20, 40, 300, 30);

		livesLabel = Board.livesLabel;
		livesLabel.setForeground(Color.WHITE);
		livesLabel.setFont(new Font("Elephant", Font.PLAIN, 20));
		livesLabel.setBounds(20, 75, 200, 30);

		pauseLabel.setForeground(Color.WHITE);
		pauseLabel.setFont(new Font("Century", Font.PLAIN, 15));
		pauseLabel.setBounds(330, 80, 300, 20);

		logoImage.setBounds(330, 15, 250, 50);

		infoPanel.setBounds(0, 0, 600, 100);
		infoPanel.setBackground(Color.BLACK);
		infoPanel.setFocusable(false);
		infoPanel.add(scoreLabel);
		infoPanel.add(livesLabel);
		infoPanel.add(highscoreLabel);
		infoPanel.add(pauseLabel);
		infoPanel.add(logoImage);

		//3. Status Panel
		statusPanel.setLayout(null);

		retryButton = new JButton(new ImageIcon("./images/retry-icon.png"));
		retryButton.setBackground(Color.LIGHT_GRAY);
		retryButton.setBounds(40, 30, 37, 37);
		retryButton.addActionListener(this);

		muteButton = new JButton(new ImageIcon("./images/unmute-icon.png"));
		muteButton.setBackground(Color.LIGHT_GRAY);
		muteButton.setBounds(100, 30, 37, 37);
		muteButton.addActionListener(this);

		playerLabel.setForeground(Color.WHITE);
		playerLabel.setFont(new Font("Elephant", Font.PLAIN, 20));
		playerLabel.setBounds(55, 85, 80, 30);

		playerText = new JTextArea(playerName);
		playerText.setBounds(55, 120, 130, 100); 
		playerText.setForeground(Color.WHITE);
		playerText.setLineWrap(true);
		playerText.setWrapStyleWord(true);
		playerText.setFont(new Font("Verdana", Font.PLAIN, 16));
		playerText.setEditable(false);
		playerText.setOpaque(false);

		ghostLabel.setForeground(Color.WHITE);
		ghostLabel.setFont(new Font("Elephant", Font.PLAIN, 20));
		ghostLabel.setBounds(55, 165, 80, 30);

		ghostStatusLabel.setForeground(Color.WHITE);
		ghostStatusLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		ghostStatusLabel.setBounds(55, 195, 80, 20);

		pelletsLabel.setForeground(Color.WHITE);
		pelletsLabel.setFont(new Font("Elephant", Font.PLAIN, 20));
		pelletsLabel.setBounds(55, 245, 80, 30);

		pelletsStatusLabel = new JLabel("Eaten / Total");
		pelletsStatusLabel.setForeground(Color.WHITE);
		pelletsStatusLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		pelletsStatusLabel.setBounds(55, 275, 200, 20);
		
		cherryLabel.setBounds(50, 350, 22, 22);
		bananaLabel.setBounds(50, 400, 22, 22);
		appleLabel.setBounds(50, 450, 22, 22);
		
		cherryCounterLabel.setForeground(Color.WHITE);
		cherryCounterLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		cherryCounterLabel.setBounds(75, 350, 100, 25);
		
		bananaCounterLabel.setForeground(Color.WHITE);
		bananaCounterLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		bananaCounterLabel.setBounds(75, 400, 100, 25);
		
		appleCounterLabel.setForeground(Color.WHITE);
		appleCounterLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
		appleCounterLabel.setBounds(75, 450, 100, 25);
		
		ghostsImage.setBounds(25, 550, 125, 135);

		statusPanel.setBounds(600, 0, 200, 700);
		statusPanel.setBackground(Color.BLACK);
		statusPanel.setFocusable(false);
		statusPanel.add(retryButton);
		statusPanel.add(muteButton);
		statusPanel.add(playerLabel);
		statusPanel.add(playerText);
		statusPanel.add(ghostLabel);
		statusPanel.add(ghostStatusLabel);
		statusPanel.add(cherryLabel);
		statusPanel.add(cherryCounterLabel);
		statusPanel.add(bananaLabel);
		statusPanel.add(bananaCounterLabel);
		statusPanel.add(appleLabel);
		statusPanel.add(appleCounterLabel);
		statusPanel.add(pelletsLabel);
		statusPanel.add(pelletsStatusLabel);
		
		statusPanel.add(ghostsImage);

		//4. Other features
		//4.1 Menu Bar
		menuBar.setBackground(Color.BLACK);	
		gameMenu.setForeground(Color.WHITE);
		helpMenu.setForeground(Color.WHITE);

		highscoresItem.setBackground(Color.BLACK);
		highscoresItem.setForeground(Color.WHITE);
		highscoresItem.addActionListener(this);

		exitItem.setBackground(Color.BLACK);
		exitItem.setForeground(Color.WHITE);
		exitItem.addActionListener(this);

		rulesItem.setBackground(Color.BLACK);
		rulesItem.setForeground(Color.WHITE);
		rulesItem.addActionListener(this);

		gameMenu.add(highscoresItem);
		gameMenu.add(exitItem);
		helpMenu.add(rulesItem);
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);

		//4.2 Popups
		//4.2.1 Rules
		rulesPanel.setLayout(null);

		arrow1.setBounds(130, 13, 22, 22);
		arrow2.setBounds(155, 13, 22, 22);
		arrow3.setBounds(180, 13, 22, 22);
		cherryImage.setBounds(160, 147, 22, 22);
		bananaImage.setBounds(185, 147, 22, 22);
		appleImage.setBounds(210, 147, 22, 22);
		pacmanImage.setBounds(135, 368, 22, 22);
		foodImage1.setBounds(157, 368, 22, 22);
		foodImage2.setBounds(179, 368, 22, 22);

		rulesLabel1.setBounds(10, 5, 200, 30);
		rulesLabel1.setForeground(Color.YELLOW);
		rulesLabel1.setFont(new Font("Verdana", Font.BOLD, 24));
		rulesText1 = new JTextArea("Control Pac-Man with the arrow keys: up, down, left, and "
				+ "right. Alongside those controls, press the 'Esc' key to pause and when paused, "
				+ "press any key to continue playing.");
		rulesText1.setBounds(10, 40, 490, 100); 
		rulesText1.setForeground(Color.WHITE);
		rulesText1.setLineWrap(true);
		rulesText1.setWrapStyleWord(true);
		rulesText1.setFont(new Font("Lucida Fax", Font.PLAIN, 14));
		rulesText1.setEditable(false);
		rulesText1.setOpaque(false);

		rulesLabel2.setBounds(10, 140, 200, 30);
		rulesLabel2.setForeground(Color.YELLOW);
		rulesLabel2.setFont(new Font("Verdana", Font.BOLD, 24));
		rulesText2 = new JTextArea("Collect powerups to aid you in your food-eating quest! There are "
				+ "3 different powerups you could encounter. The first is a cherry that gives you bonus "
				+ "points. The second is a banana that grants you one more life. The third is an apple "
				+ "that enables Pac-Man the ability to eat ghosts and earn more points for 10 seconds. "
				+ "These powerups will show up in random locations on blank spaces so be sure to eat as "
				+ "many as you can.");
		rulesText2.setBounds(10, 175, 480, 200); 
		rulesText2.setForeground(Color.WHITE);
		rulesText2.setLineWrap(true);
		rulesText2.setWrapStyleWord(true);
		rulesText2.setFont(new Font("Lucida Fax", Font.PLAIN, 14));
		rulesText2.setEditable(false);
		rulesText2.setOpaque(false);

		rulesLabel3.setBounds(10, 360, 200, 30);
		rulesLabel3.setForeground(Color.YELLOW);
		rulesLabel3.setFont(new Font("Verdana", Font.BOLD, 24));
		rulesText3 = new JTextArea("To win, finish eating all of the food pellets. Try to beat the "
				+ "highscore which is found in: Menu > Game > Highscores. "
				+ "However, if you reach zero lives, it will result in a game over so eat as many "
				+ "bananas as you can and stay away from the ghosts.");
		rulesText3.setBounds(10, 395, 480, 200); 
		rulesText3.setForeground(Color.WHITE);
		rulesText3.setLineWrap(true);
		rulesText3.setWrapStyleWord(true);
		rulesText3.setFont(new Font("Lucida Fax", Font.PLAIN, 14));
		rulesText3.setEditable(false);
		rulesText3.setOpaque(false);

		rulesPanel.setBackground(Color.BLACK);
		rulesPanel.setBounds(0, 0, 500, 600);
		rulesPanel.add(rulesLabel1);
		rulesPanel.add(rulesLabel2);
		rulesPanel.add(rulesLabel3);
		rulesPanel.add(rulesText1);
		rulesPanel.add(rulesText2);
		rulesPanel.add(rulesText3);
		rulesPanel.add(arrow1);
		rulesPanel.add(arrow2);
		rulesPanel.add(arrow3);
		rulesPanel.add(cherryImage);
		rulesPanel.add(bananaImage);
		rulesPanel.add(appleImage);
		rulesPanel.add(pacmanImage);
		rulesPanel.add(foodImage1);
		rulesPanel.add(foodImage2);


		rulesFrame.setSize(500, 600);
		rulesFrame.setResizable(false);
		rulesFrame.add(rulesPanel);

		//4.2.2 Highscores
		highscoresPanel.setLayout(null);

		highscoresTitle.setForeground(Color.YELLOW);
		highscoresTitle.setBounds(0, 10, 500, 30);
		highscoresTitle.setFont(new Font("Verdana", Font.BOLD, 24));

		highscoresFrameNameLabel.setForeground(Color.YELLOW);
		highscoresFrameNameLabel.setBounds(15, 50, 100, 30);
		highscoresFrameNameLabel.setFont(new Font("Verdana", Font.BOLD, 24));

		highscoresFrameScoresLabel.setForeground(Color.YELLOW);
		highscoresFrameScoresLabel.setBounds(300, 50, 200, 30);
		highscoresFrameScoresLabel.setFont(new Font("Verdana", Font.BOLD, 24));

		String allNames = "";
		allNames = String.format
				("  1. %s\n\n"
						+ "  2. %s\n\n"
						+ "  3. %s\n\n"
						+ "  4. %s\n\n"
						+ "  5. %s\n\n"
						+ "  6. %s\n\n"
						+ "  7. %s\n\n"
						+ "  8. %s\n\n"
						+ "  9. %s\n\n"
						+ "10. %s\n\n", 
						Board.namesArray[0],
						Board.namesArray[1],
						Board.namesArray[2],
						Board.namesArray[3],
						Board.namesArray[4],
						Board.namesArray[5],
						Board.namesArray[6],
						Board.namesArray[7],
						Board.namesArray[8],
						Board.namesArray[9]);

		nameText = new JTextArea(allNames);
		nameText.setBounds(10, 90, 200, 550); 
		nameText.setForeground(Color.WHITE);
		nameText.setLineWrap(true);
		nameText.setWrapStyleWord(true);
		nameText.setFont(new Font("Lucida Fax", Font.PLAIN, 20));
		nameText.setEditable(false);
		nameText.setOpaque(false);

		String allScores = "";
		allScores = String.format
				("%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n"
						+ "%d\n\n", 
						Board.highscoresArray[0],
						Board.highscoresArray[1],
						Board.highscoresArray[2],
						Board.highscoresArray[3],
						Board.highscoresArray[4],
						Board.highscoresArray[5],
						Board.highscoresArray[6],
						Board.highscoresArray[7],
						Board.highscoresArray[8],
						Board.highscoresArray[9]);

		scoresText = new JTextArea(allScores);
		scoresText.setBounds(300, 90, 200, 550); 
		scoresText.setForeground(Color.WHITE);
		scoresText.setLineWrap(true);
		scoresText.setWrapStyleWord(true);
		scoresText.setFont(new Font("Lucida Fax", Font.PLAIN, 20));
		scoresText.setEditable(false);
		scoresText.setOpaque(false);

		highscoresPanel.setBackground(Color.BLACK);
		highscoresPanel.setBounds(0, 0, 500, 650);
		highscoresPanel.add(highscoresTitle);
		highscoresPanel.add(highscoresFrameNameLabel);
		highscoresPanel.add(nameText);
		highscoresPanel.add(highscoresFrameScoresLabel);
		highscoresPanel.add(scoresText);

		highscoresFrame.setSize(500, 650);
		highscoresFrame.setResizable(false);
		highscoresFrame.add(highscoresPanel);

		//4.2.3 End Game Message
		endPanel.setLayout(null);

		endMessage.setForeground(Color.YELLOW);
		endMessage.setBounds(0, 10, 500, 30);
		endMessage.setFont(new Font("Verdana", Font.BOLD, 24));

		endFrameNameLabel.setForeground(Color.YELLOW);
		endFrameNameLabel.setBounds(15, 50, 100, 30);
		endFrameNameLabel.setFont(new Font("Verdana", Font.BOLD, 24));

		endFrameScoresLabel.setForeground(Color.YELLOW);
		endFrameScoresLabel.setBounds(300, 50, 200, 30);
		endFrameScoresLabel.setFont(new Font("Verdana", Font.BOLD, 24));

		endNameText = new JTextArea(allNames);
		endNameText.setBounds(10, 90, 200, 550); 
		endNameText.setForeground(Color.WHITE);
		endNameText.setLineWrap(true);
		endNameText.setWrapStyleWord(true);
		endNameText.setFont(new Font("Lucida Fax", Font.PLAIN, 20));
		endNameText.setEditable(false);
		endNameText.setOpaque(false);

		endScoresText = new JTextArea(allScores);
		endScoresText.setBounds(300, 90, 200, 550); 
		endScoresText.setForeground(Color.WHITE);
		endScoresText.setLineWrap(true);
		endScoresText.setWrapStyleWord(true);
		endScoresText.setFont(new Font("Lucida Fax", Font.PLAIN, 20));
		endScoresText.setEditable(false);
		endScoresText.setOpaque(false);

		playagainButton.setBounds(190, 575, 120, 30);
		playagainButton.setFont(new Font("Lucida Fax", Font.BOLD, 14));
		playagainButton.setForeground(Color.BLUE);
		playagainButton.setBackground(Color.BLACK);
		playagainButton.addActionListener(this);

		endPanel.setBackground(Color.BLACK);
		endPanel.setBounds(0, 0, 500, 650);
		endPanel.add(endMessage);
		endPanel.add(endFrameNameLabel);
		endPanel.add(endNameText);
		endPanel.add(endFrameScoresLabel);
		endPanel.add(endScoresText);
		endPanel.add(playagainButton);

		endFrame.setSize(500, 650);
		endFrame.setResizable(false);
		endFrame.add(endPanel);

		//5. Setting the features
		setLayout(null);
		setTitle("Pac-Man");
		setSize(800, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setJMenuBar(menuBar);
		add(startPanel);
		add(statusPanel);
		add(infoPanel);

		setResizable(false);
		setVisible(true);
	}

	/**
	 * Actions
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == exitItem){
			System.exit(0);
		}

		else if (e.getSource() == highscoresItem){
			highscoresFrame.setVisible(true);
		}

		else if (e.getSource() == rulesItem){
			rulesFrame.setVisible(true);
		}

		else if (e.getSource() == retryButton && board != null){
			Board.score = 0;
			Board.scoreLabel.setText("Score: " + Board.score);
			Board.lives = 3;
			Board.livesLabel.setText("Lives: " + Board.lives);
			ghostStatusLabel.setText("Normal");
			isMute = false;
			muteButton.setIcon(new ImageIcon("./images/unmute-icon.png"));
			pelletsStatusLabel.setText("Eaten / Total");
			Board.food = 0;
			Board.eaten = 0;
			Board.cherryCounter = 0;
			Board.bananaCounter = 0;
			Board.appleCounter = 0;
			cherryCounterLabel.setText("x " + Board.cherryCounter);
			bananaCounterLabel.setText("x " + Board.bananaCounter);
			appleCounterLabel.setText("x " + Board.appleCounter);
			board.stopTimers();			
			startPanel.setVisible(true);
			nameField.requestFocus();
			nameField.selectAll();
		}

		else if (e.getSource() == playagainButton){
			board.updateScoresList();
			Board.score = 0;
			Board.scoreLabel.setText("Score: " + Board.score);
			Board.lives = 3;
			Board.livesLabel.setText("Lives: " + Board.lives);
			ghostStatusLabel.setText("Normal");
			isMute = false;
			muteButton.setIcon(new ImageIcon("./images/unmute-icon.png"));
			pelletsStatusLabel.setText("Eaten / Total");
			Board.food = 0;
			Board.eaten = 0;
			Board.cherryCounter = 0;
			Board.bananaCounter = 0;
			Board.appleCounter = 0;
			cherryCounterLabel.setText("x " + Board.cherryCounter);
			bananaCounterLabel.setText("x " + Board.bananaCounter);
			appleCounterLabel.setText("x " + Board.appleCounter);
			board.stopTimers();
			endFrame.setVisible(false);
			startPanel.setVisible(true);
			nameField.requestFocus();
			nameField.selectAll();
		}

		else if (e.getSource() == muteButton){
			if (isMute == false){
				muteButton.setIcon(new ImageIcon("./images/mute-icon.png"));
				isMute = true;
			}

			else if (isMute == true){
				muteButton.setIcon(new ImageIcon("./images/unmute-icon.png"));
				isMute = false;
			}
			
			if (startPanel.isVisible() == true){
				nameField.requestFocus();
				nameField.selectAll();
			}
			else {
				board.requestFocus();
			}
		}

		else if (e.getSource() == nameField){

			if (nameField.getText().length() <= 12){
				playerName = nameField.getText();
				playerText.setText(playerName);
				charMoreLabel.setVisible(false);
				startPanel.setVisible(false);

				if (board != null){
					remove(board);
				}
				board = new Board();
				board.setBounds(0, 100, 600, 600);
				board.addKeyListener(board);		//adds a listener for keyboard to board
				add(board);
				board.setVisible(true);
				board.requestFocus();

				board.loadBoard();
			}
			else if (nameField.getText().length() > 12){
				charMoreLabel.setVisible(true);
			}
		}
	}	
}
