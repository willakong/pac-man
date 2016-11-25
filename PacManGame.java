/* PacMan Game
 * 
 * Enhanced by: Willa Kong
 * 
 * Additional Features:
 * -an information panel
 * 		-a score label (tracks current score)
 * 		-a highscore label (tracks top highscore)
 * 		-a lives label (tracks current lives)
 * 		-a pause label (tracks if game is paused or not)
 * 				-pause is toggled through the 'Esc' key
 * 		-the Pac-Man logo
 * 
 * -a status panel
 * 		-a retry button (lets the player to retry the game)
 * 		-a mute button (lets the player mute and unmute the game)
 * 		-a name label (displays current player's name)
 * 		-a ghost label (displays ghosts' current status)
 * 			-normal vs. weak
 * 		-a pellets label (displays how many pellets were eaten over the total)
 * 		-a powerups label (keeps track of how many powerups were collected)
 * 		-an image of the ghosts
 * 
 * -a start panel
 * 		-the Pac-Man logo
 * 		-a field for the name (displayed in the status panel and for highscores)
 * 			-has a limit of 12 characters and displays an error message if exceeded
 * 		-a loading Pac-Man and ghosts GIF
 * 		-a brief image depicting controls (arrow keys)
 * 
 * -a menu bar
 * 		-highscores (displays top ten scores and players in another frame)
 * 		-exit (quickly exit the program)
 * 		-rules (explains the controls, powerups, and how to win in another frame)
 * 
 * -powerups
 * 		-added cherries (gives bonus points)
 * 		-added bananas (give an additional life)
 * 		-added apples (acts as the 'power pellets'; enables ability to eat ghosts)
 * 			-ghosts change to the iconic 'blue' skin
 * 			-3s before the ghosts turn back, they blink as a warning
 * 			-when eaten while weak, ghosts teleport to home
 * 			-AI of ghosts change to running away from Pac-Man
 * 		-limited total number of active powerups on the board (1 at a time)
 * 		-limited total number of powerups per game (8 for apples, 5 for bananas
 * 			and unlimited for cherries)
 * 		
 * -gameplay
 * 		-changed the default skin of the ghosts
 * 		-ghosts are helped out of their homes
 * 		-Pac-Man and ghosts can't enter/re-enter the home
 * 		-AI of the ghosts were adjusted
 * 			-each ghost has a distinct personality
 * 				-the random, chaser, seeker, and follower
 * 		-scoring system was changed (number of points * number of lives)
 * 		-added sound effects
 * 
 * -highscores
 * 		-scores and names are saved externally to a text file
 * 		-added a popup at the end displaying the highscore (with an appropriate 
 * 			'Congratulations' or 'Game Over' message)
 *
 *-for fun
 *		-Easter Eggs were added (certain names change the ghosts' skin)
 *			-i.e. apple, bert, ernie, hello, homer, luke, rock
 */

public class PacManGame {

	public static void main(String[] args) {
		
		new PacManGUI();

	}

}
