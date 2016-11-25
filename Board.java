//Images: 22p x 22p

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import sun.audio.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements KeyListener, ActionListener{

	//Timers
	private Timer gameTimer = new Timer(225, this);			//moving
	private Timer animateTimer = new Timer(50, this);		//chomp

	//Images - Constants (all capitals)
	private final ImageIcon WALL = new ImageIcon("./images/StdWall.bmp");
	private final ImageIcon FOOD = new ImageIcon("./images/StdFood.bmp");
	private final ImageIcon BLANK = new ImageIcon("./images/Black.bmp");
	private final ImageIcon DOOR = new ImageIcon("./images/Black.bmp");
	private final ImageIcon SKULL = new ImageIcon("./images/Skull.bmp");
	private final ImageIcon POINTS = new ImageIcon("./images/Cherry.png");
	private final ImageIcon LIVES = new ImageIcon("./images/Banana.png");
	private final ImageIcon POWERPELLETS = new ImageIcon("./images/Apple.png");
	private final ImageIcon WEAKWARNING = new ImageIcon("./images/WeakGhostWarning.gif");

	//2D Arrays - [Rows] [Columns] - "Virtual Graph Paper"
	private JLabel[][] cell = new JLabel[25][27];	//images
	private char[][] maze = new char[25][27];		//char's from the text file

	//Characters: PacMan and Ghosts
	private PacMan pacMan;
	private Ghost[] ghost = new Ghost[4];

	//Variables
	public static int food = 0;				//amount of food
	public static int eaten = 0;			//amount of food
	public static int cherryCounter = 0;	//amount of cherries eaten
	public static int bananaCounter = 0;	//amount of bananas eaten
	public static int appleCounter = 0;		//amount of apples eaten
	public static int score = 0;			//score
	public static int highscore = 0;		//highscore
	public static int lives = 3;			//lives
	private int pStep;						//steps for animating Pacman's chomp

	private int gateRow = 0;				//row coordinate of the gate
	private int gateColumn = 0;				//column coordinate of the gate
	private int pRow = 0;					//pacman's original row
	private int pColumn = 0;				//pacman's original column

	private long startTime;					//holds the start time (apple)
	private long endTime;					//holds the end time (apple)
	private long elapsedTime = 10000;		//holds the elapsed time (apple)

	private int easterEggNum = 0;				//number determines easter egg

	//Reading the score
	Scanner inputScore;				

	//Powerup Numbers
	int cherryNum = 0;
	int bananaNum = 0;
	int totalBananaNum = 0;
	int appleNum = 0;
	int totalAppleNum = 0;

	//Highscore and Name Array
	static String[] scoreNameArray = new String[11];
	static int[] highscoresArray = new int[10];
	static String[] namesArray = new String[10];

	//Scores
	static JLabel scoreLabel = new JLabel("Score: " +  score);
	static JLabel highscoreLabel = new JLabel("Highscore: " + highscore);
	static JLabel livesLabel = new JLabel("Lives: " + lives);

	//Pause
	private boolean isPaused = false;

	//Files
	File mazeFile = new File("maze.txt");
	File highscoresFile = new File("highscores.txt");

	/**
	 * Construct Maze
	 */
	public Board(){

		//1. Set the layout (grid), background (black) and JLabel
		setLayout(new GridLayout(25, 27));
		setBackground(Color.BLACK);

		//2. Create PacMan and the ghosts
		pacMan = new PacMan();

		ghost[0] = new Ghost(0);
		ghost[1] = new Ghost(1);
		ghost[2] = new Ghost(2);
		ghost[3] = new Ghost(3);

		//3. Read the scores
		readScore();
	}

	/**
	 * Load the maze onto the screen from a text file
	 */
	public void loadBoard() {

		//Variables: rows, scanner
		int r = 0;
		Scanner inputMaze;	

		//0. Play intro
		if (PacManGUI.isMute == false){
			playSound(loadIntroSound());
		}

		//1. Open the maze text file for input
		try {
			inputMaze = new Scanner(mazeFile);

			//2. Cycle through all the rows in the maze file
			while(inputMaze.hasNext()){

				//2.1 Read the next line from the maze file
				maze[r] = inputMaze.nextLine().toCharArray();

				//2.2 For each row, cycle through all the columns
				for (int c = 0; c < maze[r].length; c++){

					//Create a new picture
					cell[r][c] = new JLabel();

					//Depending on the symbol in the maze file

					//2.2.1 Wall
					if (maze[r][c] == 'W'){
						cell[r][c].setIcon(WALL);
					}

					//2.2.2 Food
					else if (maze[r][c] == 'F'){
						cell[r][c].setIcon(FOOD);
						food++;		//keep track of the amount of food
						PacManGUI.pelletsStatusLabel.setText("0 / " + food);
					}

					//2.2.3 PacMan
					else if (maze[r][c] == 'P'){
						cell[r][c].setIcon(pacMan.getIcon());
						pRow = r;
						pColumn = c;
						pacMan.setRow(r);
						pacMan.setColumn(c);
						pacMan.setDirection(0);		//start left
					}

					//2.2.4 Ghost
					else if (maze[r][c] == '0' || maze[r][c] == '1' 
							|| maze[r][c] == '2' || maze[r][c] == '3'){
						int gNum = (Character.getNumericValue(maze[r][c]));
						//int gNum = (int)(maze[r][c]) - 48; <-- using ASCII

						//2.2.4.1 Easter Eggs
						if (PacManGUI.playerName.equalsIgnoreCase("apple") 
								|| PacManGUI.playerName.equalsIgnoreCase("steve")
								|| PacManGUI.playerName.equalsIgnoreCase("jobs")){
							cell[r][c].setIcon(Ghost.IMAGE[5]);
							ghost[gNum].setIcon(Ghost.IMAGE[5]);
							easterEggNum = 5;
						}
						else if (PacManGUI.playerName.equalsIgnoreCase("bert")
								|| PacManGUI.playerName.equalsIgnoreCase("muppet")){
							cell[r][c].setIcon(Ghost.IMAGE[6]);
							ghost[gNum].setIcon(Ghost.IMAGE[6]);
							easterEggNum = 6;
						}
						else if (PacManGUI.playerName.equalsIgnoreCase("ernie")
								|| PacManGUI.playerName.equalsIgnoreCase("muppets")){
							cell[r][c].setIcon(Ghost.IMAGE[7]);
							ghost[gNum].setIcon(Ghost.IMAGE[7]);
							easterEggNum = 7;
						}
						else if (PacManGUI.playerName.equalsIgnoreCase("hello") 
								|| PacManGUI.playerName.equalsIgnoreCase("hi")
								|| PacManGUI.playerName.equalsIgnoreCase("howdy")
								|| PacManGUI.playerName.equalsIgnoreCase("hiya")
								|| PacManGUI.playerName.equalsIgnoreCase("yo")
								|| PacManGUI.playerName.equalsIgnoreCase("bonjour")
								|| PacManGUI.playerName.equalsIgnoreCase("ni hao")){
							cell[r][c].setIcon(Ghost.IMAGE[8]);
							ghost[gNum].setIcon(Ghost.IMAGE[8]);
							easterEggNum = 8;
						}
						else if (PacManGUI.playerName.equalsIgnoreCase("homer") 
								|| PacManGUI.playerName.equalsIgnoreCase("simpsons")
								|| PacManGUI.playerName.equalsIgnoreCase("simpson")){
							cell[r][c].setIcon(Ghost.IMAGE[9]);
							ghost[gNum].setIcon(Ghost.IMAGE[9]);
							easterEggNum = 9;
						}
						else if (PacManGUI.playerName.equalsIgnoreCase("luke") 
								|| PacManGUI.playerName.equalsIgnoreCase("star wars")
								|| PacManGUI.playerName.equalsIgnoreCase("r2d2")
								|| PacManGUI.playerName.equalsIgnoreCase("vadar")){
							cell[r][c].setIcon(Ghost.IMAGE[10]);
							ghost[gNum].setIcon(Ghost.IMAGE[10]);
							easterEggNum = 10;
						}
						else if (PacManGUI.playerName.equalsIgnoreCase("rock") 
								|| PacManGUI.playerName.equalsIgnoreCase("cool")
								|| PacManGUI.playerName.equalsIgnoreCase("awesome")){
							cell[r][c].setIcon(Ghost.IMAGE[11]);
							ghost[gNum].setIcon(Ghost.IMAGE[11]);
							easterEggNum = 11;
						}

						//2.2.4.2 Normal
						else{
							cell[r][c].setIcon(ghost[gNum].getIcon());
							easterEggNum = 0;
						}
						ghost[gNum].setRow(r);
						ghost[gNum].setColumn(c);
					}

					//2.2.5 Door
					else if (maze[r][c] == 'D'){
						cell[r][c].setIcon(DOOR);
					}

					//2.2.6 Gate (Ghosts' home)
					else if (maze[r][c] == 'G'){
						cell[r][c].setIcon(BLANK);
						gateRow = r;
						gateColumn = c;
					}

					//2.2.7 Add the current cell to the board
					add(cell[r][c]);
				}

				//2.3 Increment the row
				r++;	
			}			
			//3. Close the maze text file
			inputMaze.close();		
		}

		//4. Catch the file if there is an error
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}	
	}

	/**
	 * Play sounds
	 */
	public static void playSound(AudioStream audio){
		AudioPlayer.player.start(audio);
	}

	/**
	 * Stop sounds
	 */
	public static void stopSound(AudioStream audio){
		AudioPlayer.player.stop(audio);
	}

	/**
	 * Load the sounds - 
	 */
	public static AudioStream loadIntroSound(){
		try{
			InputStream input = new FileInputStream("./sounds/pacmanintro.wav");
			AudioStream audio = new AudioStream(input);

			return audio;
		}
		catch (IOException e){
			System.out.println("Error");
		}

		return null;
	}

	/**
	 * Load the sounds - eating
	 */
	public static AudioStream loadEatSound(){
		try{
			InputStream input = new FileInputStream("./sounds/fruiteat.wav");
			AudioStream audio = new AudioStream(input);

			return audio;
		}
		catch (IOException e){
			System.out.println("Error");
		}
		return null;
	}

	/**
	 * Load the sounds - dying
	 */
	public static AudioStream loadDeathSound(){
		try{
			InputStream input = new FileInputStream("./sounds/killed.wav");
			AudioStream audio = new AudioStream(input);
			return audio;
		}
		catch (IOException e){
			System.out.println("Error");
		}
		return null;
	}

	/**
	 * Load the sounds - chomping
	 */
	public static AudioStream loadChompSound(){

		try{
			InputStream input = new FileInputStream("./sounds/pacchomp.wav");
			AudioStream audio = new AudioStream(input);
			return audio;
		}
		catch (IOException e){
			System.out.println("Error");
		}
		return null;
	}

	/**
	 * Load the sounds - eating a ghost
	 */
	public static AudioStream loadEatGhostSound(){
		try{
			InputStream input = new FileInputStream("./sounds/ghosteaten.wav");
			AudioStream audio = new AudioStream(input);
			return audio;
		}
		catch (IOException e){
			System.out.println("Error");
		}
		return null;
	}

	/**
	 * Actions
	 */
	public void actionPerformed(ActionEvent e) {

		//1. If action is game timer
		if (e.getSource() == gameTimer){

			//1.1 Move pacman and ghosts
			performMove(pacMan);
			moveGhosts();
			cherries();
			bananas();
			apples();

			//1.2 When the elapsed time has passed, reset to normal
			if (ghost[0].isWeak() == true && ghost[1].isWeak() == true
					&& ghost[2].isWeak() == true && ghost[3].isWeak() == true){
				
				for (int i = 0; i < 4; i++){
					if (endTime - System.currentTimeMillis() < 3000 
							&& endTime - System.currentTimeMillis() > 0){
						ghost[i].setIcon(WEAKWARNING);
						cell[ghost[i].getRow()][ghost[i].getColumn()].setIcon(WEAKWARNING);
					}

					else if (endTime < System.currentTimeMillis()){
						if (easterEggNum != 0){
							ghost[i].setIcon(Ghost.IMAGE[easterEggNum]);
							cell[ghost[i].getRow()][ghost[i].getColumn()].setIcon(Ghost.IMAGE[easterEggNum]);
						}
						else if (easterEggNum == 0){
							ghost[i].setIcon(Ghost.IMAGE[i]);
							cell[ghost[i].getRow()][ghost[i].getColumn()].setIcon(Ghost.IMAGE[i]);
						}
						ghost[i].setWeak(false);
						PacManGUI.ghostStatusLabel.setText("Normal");
					}
				}	
			}

			//1.3 Check if all food is eaten
			if (food == eaten){
				stopGame();
			}
		}

		//2. Otherwise, if the action is the animation timer
		else if (e.getSource() == animateTimer){

			//2.1 Animate PacMan through the current step
			animatePacMan(pStep);

			//2.2 Increment the step number
			pStep++;

			//2.3 If the step is the last step then reset the steps
			if (pStep == 3){
				pStep = 0;
			}
		}
	}

	/**
	 * Animates PacMan's chomp and standing still
	 * @param pStep2
	 */
	private void animatePacMan(int pStep) {

		//1. If it is step 0 of animation
		//1  is open mouth
		if (pStep == 0){
			//1.1 Open mouth in current cell
			cell[pacMan.getRow()][pacMan.getColumn()]
					.setIcon(PacMan.IMAGE[pacMan.getDirection()][1]);

			//1.2 Delay the animation timer
			animateTimer.setDelay(50);
		}

		//2. Otherwise if it is step 1 of animation
		else if (pStep == 1){
			//2.1 Blank the current cell
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);
		}

		//3. Otherwise if it is step 2 of animation
		else if (pStep == 2){

			//3.0 Load the chomp sound
			if (PacManGUI.isMute == false){
				playSound(loadChompSound());
			}
			//3.1 Move pacMan
			pacMan.move();

			//3.2 If there is any food in the new square on the board
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F'){

				//3.2.1 Increment the score with the lives and display it
				score += 1 * lives;
				scoreLabel.setText("Score: " + score);

				//3.2.2 Mark the maze at the new position to 'E' for eaten
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				//3.3.2 Increment the amount of eaten food
				eaten++;
				PacManGUI.pelletsStatusLabel.setText(eaten + " / " + food);
			}

			//3.3 If there is a cherry in the new square on the board
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'C'){

				//3.3.0 Load eating the sound
				if (PacManGUI.isMute == false){
					playSound(loadEatSound());
				}
				//3.3.1 Increment the score with the lives and display it
				score += 10 * lives;
				scoreLabel.setText("Score: " + score);

				//3.3.2 Mark the maze at the new position to 'E' for eaten
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				cherryNum--;
				cherryCounter++;
				PacManGUI.cherryCounterLabel.setText("x " + cherryCounter);
			}

			//3.4 If there is a banana in the new square on the board
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'B'){

				//3.4.0 Load eating the sound
				if (PacManGUI.isMute == false){
					playSound(loadEatSound());
				}

				//3.4.1 Increment the lives counter
				lives++;
				livesLabel.setText("Lives: " + lives);

				//3.4.2 Mark the maze at the new position to 'E' for eaten
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				bananaNum--;
				bananaCounter++;
				PacManGUI.bananaCounterLabel.setText("x " + bananaCounter);
			}

			//3.5 If there is an apple in the new square on the board
			if (maze[pacMan.getRow()][pacMan.getColumn()] == 'A'){

				//3.5.0 Load eating the sound and set the ghost status
				if (PacManGUI.isMute == false){
					playSound(loadEatSound());
				}
				PacManGUI.ghostStatusLabel.setText("Weak");

				//3.5.1 Get the time when the apple was eaten
				startTime = System.currentTimeMillis();

				//3.5.2 Start the apple powerup
				appleAction();

				//3.5.3 Mark the maze at the new position to 'E' for eaten
				maze[pacMan.getRow()][pacMan.getColumn()] = 'E';

				appleNum--;
				appleCounter++;
				PacManGUI.appleCounterLabel.setText("x " + appleCounter);
			}

			//3.6 If there is a weak ghost in the new square
			for (Ghost g: ghost){

				//3.6.1 If collision occurs but ghosts are weak
				if (g.getRow() == pacMan.getRow() && g.getColumn() == pacMan.getColumn()
						&& g.isWeak() == true){

					//3.6.1.0 Load the sound
					if (PacManGUI.isMute == false){
						playSound(loadEatGhostSound());
					}

					//3.6.1.1 Update the score
					score += 50 * lives;
					scoreLabel.setText("Score: " + score);

					//3.6.1.2 Teleport the ghost back to their home and replace image
					if (maze[g.getRow()][g.getColumn()] == 'F'){
						cell[g.getRow()][g.getColumn()].setIcon(FOOD);
					}
					else if (maze[g.getRow()][g.getColumn()] == 'C'){
						cell[g.getRow()][g.getColumn()].setIcon(POINTS);
					}
					else if (maze[g.getRow()][g.getColumn()] == 'B'){
						cell[g.getRow()][g.getColumn()].setIcon(LIVES);
					}
					else if (maze[g.getRow()][g.getColumn()] == 'A'){
						cell[g.getRow()][g.getColumn()].setIcon(POWERPELLETS);
					}
					else{
						cell[g.getRow()][g.getColumn()].setIcon(BLANK);
					}

					g.setRow(12);
					g.setColumn(13);
					g.setIcon(Ghost.IMAGE[4]);
					cell[12][13].setIcon(Ghost.IMAGE[4]);
				}
			}

			//3.7 Stop the animation timer
			animateTimer.stop();

			//3.8 If pacMan is dead then show a skull (runs into a ghost)
			if (pacMan.isDead()){
				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);			
			}

			//3.9 Otherwise, show the appropriate closed pacMan based on direction
			//0 is closed mouth
			else {
				cell[pacMan.getRow()][pacMan.getColumn()]
						.setIcon(PacMan.IMAGE[pacMan.getDirection()][0]);
			}	
		}
	}

	/**
	 * Apple Powerup Actions (Power Pellet)
	 */
	private void appleAction(){

		//0. Define end time
		endTime = startTime + elapsedTime;

		//1. Change the ghosts to weak
		for (Ghost g: ghost){
			cell[g.getRow()][g.getColumn()].setIcon(Ghost.IMAGE[4]);
			g.setIcon(Ghost.IMAGE[4]);
			g.setWeak(true);
		}
	}

	/**
	 * What happens when a player presses a key
	 */
	public void keyPressed(KeyEvent key) {
		//1. If the game isn't running and PacMan is alive, then start the timers
		if (gameTimer.isRunning() == false && pacMan.isDead() == false){
			gameTimer.start();
		}

		//2. If PacMan is still alive and the game is not over then
		if (pacMan.isDead() == false && food != eaten){

			//2.1 Track direction based on key pressed 
			// -37 since ASCII codes for cursor key starts at 37
			int direction = key.getKeyCode() - 37;

			//2.2 Change direction of PacMan;
			//Left: 37 (0), Up: 38 (1), Right: 39 (2), Down: 40 (3)
			if (direction == 0 && maze[pacMan.getRow()][pacMan.getColumn() - 1] != 'W'
					&& maze[pacMan.getRow()][pacMan.getColumn() - 1] != 'G'){
				pacMan.setDirection(0);
			}

			else if (direction == 1 && maze[pacMan.getRow() - 1][pacMan.getColumn()] != 'W'
					&& maze[pacMan.getRow() - 1][pacMan.getColumn()] != 'G'){
				pacMan.setDirection(1);
			}

			else if (direction == 2 && maze[pacMan.getRow()][pacMan.getColumn() + 1] != 'W'
					&& maze[pacMan.getRow()][pacMan.getColumn() + 1] != 'G'){
				pacMan.setDirection(2);
			}

			else if (direction == 3 && maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'W'
					&& maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'G'){
				pacMan.setDirection(3);
			}
		}
		//3. If key pressed is the esc key, PAUSE
		if (key.getKeyCode() == 27){

			//3.1 If game is already running, then pause
			if (isPaused == false){	
				gameTimer.stop();
				animateTimer.stop();
				isPaused = true;
				PacManGUI.pauseLabel.setText("PAUSED. Press any key to continue.");
			}

			//3.2 If the game is already paused, then resume
			else if (isPaused == true) {
				gameTimer.start();
				isPaused = false;
				PacManGUI.pauseLabel.setText("PLAYING. Press 'Esc' to pause.");
			}
		}
		else if (key.getKeyCode() != 27 && isPaused == true){
			PacManGUI.pauseLabel.setText("PLAYING. Press 'Esc' to pause.");
			isPaused = false;
		}
	}

	/**
	 * Determines the moves of either PacMan or the Ghosts
	 * @param mover
	 */
	private void performMove(Mover mover){

		//1. If a mover is at a door then teleport to other side
		if (mover.getColumn() == 1){
			mover.setColumn(24);
			cell[12][1].setIcon(DOOR);
		}
		else if (mover.getColumn() == 25){
			mover.setColumn(2);
			cell[12][25].setIcon(DOOR);
		}

		//2. If there is no wall or gate where the Mover object wants to go then
		if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W'
				&& maze[mover.getNextRow()][mover.getNextColumn()] != 'G'){

			//2.1 If the Mover object is PacMan then animate a 'chomp'
			if (mover == pacMan){
				animateTimer.start();
			}

			//2.2 Otherwise the Mover is a ghost
			else {

				//2.2.1 If the cell where the Ghost is has food then reset it
				if (maze[mover.getRow()][mover.getColumn()] == 'F'){
					cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);
				}

				else if (maze[mover.getRow()][mover.getColumn()] == 'C'){
					cell[mover.getRow()][mover.getColumn()].setIcon(POINTS);
				}

				else if (maze[mover.getRow()][mover.getColumn()] == 'B'){
					cell[mover.getRow()][mover.getColumn()].setIcon(LIVES);
				}

				else if (maze[mover.getRow()][mover.getColumn()] == 'A'){
					cell[mover.getRow()][mover.getColumn()].setIcon(POWERPELLETS);
				}

				//2.2.2 If the ghost moves to 'I', move the ghosts to the gate 'G'
				else if (maze[mover.getRow()][mover.getColumn()] == 'I'){
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);
					mover.setRow(gateRow - 1);
					mover.setColumn(gateColumn);
				}

				//2.2.3 Otherwise reset the cell to blank
				else {
					cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);
				}

				//2.2.4 Move the ghost's position
				mover.move();

				//2.2.5 If a collision has occurred then death occurs
				if (collided() && lives <= 0){
					livesLabel.setText("Lives: 0");
					death();
				}

				//2.2.6 Otherwise update the picture on the screen
				else {
					cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());
				}
			}
		}
	}

	/**
	 * Moves the ghosts in a random pattern (AI)
	 */
	private void moveGhosts(){

		//0.Direction Array
		int[] dir = new int[4];

		//1. If ghost is normal
		if (ghost[0].isWeak() == false && ghost[1].isWeak() == false
				&& ghost[2].isWeak() == false && ghost[3].isWeak() == false){

			//1. Ghost 0 [Random]
			dir[0] = 0;

			//1.1 Keep selecting random directions to avoid 'backtracking'
			//Left (0), Right (2), Up (1), Down (3)
			dir[0] = (int)(Math.random() * 4);		

			//2. Ghost 1 [Chaser]
			dir[1] = 0;

			//2.1 Find PacMan's location and compare to ghost's location and chase
			//Left (0), Right (2), Up (1), Down (3)

			//Chase PacMan (left)
			if (pacMan.getColumn() < ghost[1].getColumn()){
				dir[1] = 0;
			}
			//Chase PacMan (right)
			else if (pacMan.getColumn() > ghost[1].getColumn()){
				dir[1] = 2;
			}
			//Chase PacMan (up)
			else if (pacMan.getRow() < ghost[1].getRow()){
				dir[1] = 1;
			}
			//Chase PacMan (down)
			else if (pacMan.getRow() > ghost[1].getRow()){
				dir[1] = 3;
			}

			//3. Ghost 2 [Seeker]
			dir[2] = 0;

			//3.1 If the seeker is on the same row or column, it will give chase
			//Left (0), Right (2), Up (1), Down (3)

			//Same Row
			if (pacMan.getRow() == ghost[2].getRow()){
				//Left
				if (pacMan.getColumn() < ghost[2].getColumn()){
					dir[2] = 0;
				}
				//Right
				else if (pacMan.getColumn() > ghost[2].getColumn()){
					dir[2] = 2;
				}
			}
			//Same Column	
			else if (pacMan.getColumn() == ghost[2].getColumn()){
				//Up
				if (pacMan.getRow() < ghost[2].getRow()){
					dir[2] = 1;
				}
				//Down
				else if (pacMan.getRow() > ghost[2].getRow()){
					dir[2] = 3;
				}
			}

			//4. Ghost 3 [Follower]
			dir[3] = 0;

			//3.1 Mimics PacMan's moves
			//Left (0), Right (2), Up (1), Down (3)

			if (pacMan.getDirection() == 0){
				dir[3] = 0;
			}
			else if (pacMan.getDirection() == 1){
				dir[3] = 1;
			}
			else if (pacMan.getDirection() == 2){
				dir[3] = 2;
			}
			else if (pacMan.getDirection() == 3){
				dir[3] = 3;
			}
		}

		//2. If ghost is weak
		//Left (0), Right (2), Up (1), Down (3)
		else if (ghost[0].isWeak() == true && ghost[1].isWeak() == true
				&& ghost[2].isWeak() == true && ghost[3].isWeak() == true){

			for (int i = 0; i < 4; i++){
				//Walls - Up
				if (ghost[i].getRow() - 1 == 'W'){
					if (ghost[i].getColumn() - 1 != 'W'){
						dir[i] = 0;
					}
					else{
						dir[i] = 2;
					}
				}
				//Walls - Down
				else if (ghost[i].getRow() + 1 == 'W'){
					if (ghost[i].getColumn() - 1 != 'W'){
						dir[i] = 0;
					}
					else{
						dir[i] = 2;
					}
				}
				//Walls - Left
				else if (ghost[i].getColumn() - 1 == 'W'){
					if (ghost[i].getRow() + 1 != 'W'){
						dir[i] = 3;
					}
					else {
						dir[i] = 1;
					}
				}
				//Walls - Up
				else if (ghost[i].getColumn() + 1 == 'W'){
					if (ghost[i].getRow() + 1 != 'W'){
						dir[i] = 3;
					}
					else {
						dir[i] = 1;
					}
				}
				//Runaway from PacMan (left)
				else if (pacMan.getColumn() > ghost[i].getColumn()){
					dir[i] = 0;
				}
				//Runaway from PacMan (right)
				else if (pacMan.getColumn() < ghost[i].getColumn()){
					dir[i] = 2;
				}
				//Runaway from PacMan (up)
				else if (pacMan.getRow() > ghost[i].getRow()){
					dir[i] = 1;
				}
				//Runaway from PacMan (down)
				else if (pacMan.getRow() < ghost[i].getRow()){
					dir[i] = 3;
				}
			}	
		}

		//3. Setting directions and moving all the ghosts
		for (int i = 0; i < 4; i++){
			ghost[i].setDirection(dir[i]);
			performMove(ghost[i]);
		}
	}

	/**
	 * Determines if PacMan has collided with a Ghost
	 * @return
	 */
	private boolean collided() {		
		//1. Cycle through all the ghosts to see if anyone has captured PacMan
		//Enhanced for loop
		for (Ghost g: ghost){

			//1.1 If the ghost is in the same location then stop everything and return true
			if (g.getRow() == pacMan.getRow() && g.getColumn() == pacMan.getColumn()
					&& g.isWeak() == false){
				gameTimer.stop();
				animateTimer.stop();
				lives--;
				livesLabel.setText("Lives: " + lives);
				if (PacManGUI.isMute == false){
					playSound(loadDeathSound());
				}

				//1.1.1 Reset PacMan's location if still more lives
				if (lives > 0){

					//1.1.1.1 Replace PacMan's place
					if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F'){
						cell[pacMan.getRow()][pacMan.getColumn()].setIcon(FOOD);
					}

					else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'C'){
						cell[pacMan.getRow()][pacMan.getColumn()].setIcon(POINTS);
					}

					else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'B'){
						cell[pacMan.getRow()][pacMan.getColumn()].setIcon(LIVES);
					}

					else if (maze[pacMan.getRow()][pacMan.getColumn()] == 'A'){
						cell[pacMan.getRow()][pacMan.getColumn()].setIcon(POWERPELLETS);
					}

					else{
						cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);
					}

					//1.1.1.2 Move PacMan
					pacMan.setRow(pRow);
					pacMan.setColumn(pColumn);
					cell[pRow][pColumn].setIcon(PacMan.IMAGE[0][0]);

					//1.1.1.3 Resets the ghosts to the four corners of the board
					for (Ghost ghost: ghost){
						if (maze[ghost.getRow()][ghost.getColumn()] == 'F'){
							cell[ghost.getRow()][ghost.getColumn()].setIcon(FOOD);
						}
						else if (maze[ghost.getRow()][ghost.getColumn()] == 'C'){
							cell[ghost.getRow()][ghost.getColumn()].setIcon(POINTS);
						}
						else if (maze[ghost.getRow()][ghost.getColumn()] == 'B'){
							cell[ghost.getRow()][ghost.getColumn()].setIcon(LIVES);
						}
						else if (maze[ghost.getRow()][ghost.getColumn()] == 'A'){
							cell[ghost.getRow()][ghost.getColumn()].setIcon(POWERPELLETS);
						}
						else{
							cell[ghost.getRow()][ghost.getColumn()].setIcon(BLANK);
						}
					}

					//1.1.1.4 Move the ghosts
					ghost[0].setRow(2);
					ghost[0].setColumn(2);
					if (easterEggNum != 0){
						cell[2][2].setIcon(Ghost.IMAGE[easterEggNum]);
						ghost[0].setIcon(Ghost.IMAGE[easterEggNum]);
					}
					else if (easterEggNum == 0){
						cell[2][2].setIcon(Ghost.IMAGE[0]);
						ghost[0].setIcon(Ghost.IMAGE[0]);
					}

					ghost[1].setRow(22);
					ghost[1].setColumn(2);
					if (easterEggNum != 0){
						cell[22][2].setIcon(Ghost.IMAGE[easterEggNum]);
						ghost[1].setIcon(Ghost.IMAGE[easterEggNum]);
					}
					else if (easterEggNum == 0){
						cell[22][2].setIcon(Ghost.IMAGE[1]);
						ghost[1].setIcon(Ghost.IMAGE[1]);
					}

					ghost[2].setRow(2);
					ghost[2].setColumn(24);
					if (easterEggNum != 0){
						cell[2][24].setIcon(Ghost.IMAGE[easterEggNum]);
						ghost[2].setIcon(Ghost.IMAGE[easterEggNum]);
					}
					else if (easterEggNum == 0){
						cell[2][24].setIcon(Ghost.IMAGE[2]);
						ghost[2].setIcon(Ghost.IMAGE[2]);
					}

					ghost[3].setRow(22);
					ghost[3].setColumn(24);if (easterEggNum != 0){
						cell[22][24].setIcon(Ghost.IMAGE[easterEggNum]);
						ghost[3].setIcon(Ghost.IMAGE[easterEggNum]);
					}
					else if (easterEggNum == 0){
						cell[22][24].setIcon(Ghost.IMAGE[3]);
						ghost[3].setIcon(Ghost.IMAGE[3]);
					}

				}
				return true;
			}
		}
		//2. If no ghosts were in the same location then return false
		return false;
	}

	/**
	 * Stop the game when PacMan and a ghost 'collide' and lives are 0
	 */
	private void death() {	
		//1. Set PacMan dead
		pacMan.setDead(true);

		//2. Stop the game
		stopGame();

		//3. Determine current location of PacMan on the screen and assign a picture
		//of a skull
		cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);		
	}

	/**
	 * Stops the game timer
	 */
	private void stopGame() {		
		//1. If PacMan is dead then stop the timers
		if (pacMan.isDead() == true){
			animateTimer.stop();
			gameTimer.stop();
			PacManGUI.endMessage.setText("Game Over");

			//1.1 Set the highscore and reset the score if higher
			if (score > highscore){
				highscore = score;
				writeScore(highscoresFile, PacManGUI.playerName, highscore);
			}
		}

		//2. If all the food is eaten then stop the timers
		else if (food == eaten){
			animateTimer.stop();
			gameTimer.stop();
			PacManGUI.endMessage.setText("Congratulations!");

			//2.1 Set the highscore and reset the score if higher
			if (score > highscore){
				highscore = score;
				writeScore(highscoresFile, PacManGUI.playerName, highscore);
			}
		}

		//3. Update scores list
		updateScoresList();
	}

	/**
	 * Update the highscores list
	 */
	public void updateScoresList(){
		//1. Update the scores
		readScore();

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
						namesArray[0],
						namesArray[1],
						namesArray[2],
						namesArray[3],
						namesArray[4],
						namesArray[5],
						namesArray[6],
						namesArray[7],
						namesArray[8],
						namesArray[9]);
		PacManGUI.nameText.setText(allNames);
		PacManGUI.endNameText.setText(allNames);

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
						highscoresArray[0],
						highscoresArray[1],
						highscoresArray[2],
						highscoresArray[3],
						highscoresArray[4],
						highscoresArray[5],
						highscoresArray[6],
						highscoresArray[7],
						highscoresArray[8],
						highscoresArray[9]);
		PacManGUI.scoresText.setText(allScores);
		PacManGUI.endScoresText.setText(allScores);

		//2. Display the end game frame
		PacManGUI.endFrame.setVisible(true);
	}

	/**
	 * Stops both of the timers (to be able to stop in the GUI class)
	 */
	public void stopTimers(){
		gameTimer.stop();
		animateTimer.stop();
	}

	/**
	 * Displays the cherry powerups (more points)
	 */
	private void cherries(){
		Random r = new Random();
		int row = r.nextInt(23)+1;
		int column = r.nextInt(23)+1;

		if(maze[row][column] != 'W' && maze[row][column] != 'G'
				&& maze[row][column] != 'C' && maze[row][column] != 'B' 
				&& maze[row][column] != 'A' && maze[row][column] != 'F'
				&& maze[row][column] != 'X' && maze[row][column] != 'I'
				&& maze[row][column] != '0' && maze[row][column] != '1'
				&& maze[row][column] != '2' && maze[row][column] != '3'
				&& cherryNum < 1){
			cell[row][column].setIcon(POINTS);
			maze[row][column] = 'C';
			cherryNum++;
		}
	}

	/**
	 * Displays the apple powerups (power pellets)
	 */
	private void apples(){
		Random r = new Random();
		int row = r.nextInt(23)+1;
		int column = r.nextInt(23)+1;

		if(maze[row][column] != 'W' && maze[row][column] != 'G'
				&& maze[row][column] != 'C' && maze[row][column] != 'B' 
				&& maze[row][column] != 'A' && maze[row][column] != 'F'
				&& maze[row][column] != 'X' && maze[row][column] != 'I'
				&& maze[row][column] != '0' && maze[row][column] != '1'
				&& maze[row][column] != '2' && maze[row][column] != '3'
				&& appleNum < 1 && totalAppleNum < 8){
			cell[row][column].setIcon(POWERPELLETS);
			maze[row][column] = 'A';
			appleNum++;
			totalAppleNum++;
		}
	}

	/**
	 * Displays the banana powerups (more lives) 
	 */
	private void bananas(){
		Random r = new Random();
		int row = r.nextInt(23)+1;
		int column = r.nextInt(23)+1;

		if(maze[row][column] != 'W' && maze[row][column] != 'G'
				&& maze[row][column] != 'C' && maze[row][column] != 'B' 
				&& maze[row][column] != 'A' && maze[row][column] != 'F'
				&& maze[row][column] != 'X' && maze[row][column] != 'I'
				&& maze[row][column] != '0' && maze[row][column] != '1'
				&& maze[row][column] != '2' && maze[row][column] != '3'
				&& bananaNum < 1 && totalBananaNum < 5){
			cell[row][column].setIcon(LIVES);
			maze[row][column] = 'B';
			bananaNum++;
			totalBananaNum++;
		}
	}

	/**
	 * Reads a highscore from the text file
	 */
	private void readScore(){

		//1. Reads the file
		try {
			inputScore = new Scanner(highscoresFile);

			//1.0
			inputScore.useDelimiter(System.lineSeparator());

			//1.1 Read the values in as a String into the array
			int i = 0;
			while (inputScore.hasNextLine()){
				scoreNameArray[i] = inputScore.next();
				i++;
			}

			//1.2 Separate the String into the integer section and assign the highest
			String highscoreString = scoreNameArray[0];
			highscoreString = highscoreString.substring(0, highscoreString.indexOf(";"));
			int highscoreNum = Integer.valueOf(highscoreString);
			highscoreLabel.setText("Highscore: " + highscoreNum);

			//1.3 Assign the top ten players
			for (int j = 0; j < 10; j++){
				String scoreString = scoreNameArray[j];
				scoreString = scoreString.substring(0, scoreString.indexOf(";"));

				int scoreInteger = Integer.valueOf(scoreString);
				highscoresArray[j] = scoreInteger;

				String nameString = scoreNameArray[j];
				nameString = nameString.substring(nameString.indexOf(";") + 1, 
						nameString.length() - 1);
				namesArray[j] = nameString;
			}
		}

		//2. Catch the file if not found
		catch (FileNotFoundException e){
			System.out.println("File not found");
		}

		//3. Close the file
		inputScore.close();
	}

	/**
	 * Writing the highscore and name to an external file
	 */
	private void writeScore(File file, String newPlayer, int newScore){

		try {

			//1. Assign the score and name into a new String and store it
			String scoreName = newScore + ";" + newPlayer + ";";
			scoreNameArray[10] = scoreName;

			//2. Sort the new array in terms of descending order
			Arrays.sort(scoreNameArray, new Comparator<String>(){

				//2.1 Method to compare the values
				public int compare(String s1, String s2)
				{
					String n1 = s1.substring(0,s1.indexOf(";"));
					String n2 = s2.substring(0,s2.indexOf(";"));

					return Integer.valueOf(n1).compareTo(Integer.valueOf(n2));
				}
			}
					);

			//3. Rewrite the highscores file
			FileWriter writer = new FileWriter(file);

			for (int i = 10; i > 0; i--){
				if (i != 1){
					writer.write(scoreNameArray[i] + System.lineSeparator());
				}
				else if (i == 1){
					writer.write(scoreNameArray[i]);
				}
			}

			//4. Close the file writer
			writer.close();
		}

		//5. Catch the file if there is an IO Exception
		catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Mandatory method to implement KeyListener
	 * @Override
	 */
	public void keyReleased(KeyEvent key) {
		//not used
	}

	/**
	 * Mandatory method to implement KeyListener
	 * @Override
	 */
	public void keyTyped(KeyEvent key) {
		//not used
	}
}