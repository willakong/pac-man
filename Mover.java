import javax.swing.JLabel;

@SuppressWarnings("serial")
public abstract class Mover extends JLabel{

	private int row;			//current row
	private int column;			//current column
	private int dRow;			//current row direction (-1, 0, or 1)
	private int dColumn;		//current column direction (-1, 0, or 1)
	private boolean isDead;		//death status
	private boolean isWeak;		//weak status

	//Getters and Setters
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getdRow() {
		return dRow;
	}
	public void setdRow(int dRow) {
		this.dRow = dRow;
	}
	public int getdColumn() {
		return dColumn;
	}
	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isWeak(){
		return isWeak;
	}

	public void setWeak(boolean isWeak){
		this.isWeak = isWeak;
	}

	//dRow, ddColumn will either be -1, 0, or +1
	public void move(){
		row += dRow;
		column += dColumn;
	}

	public void setDirection(int dir){

		//Reset
		dRow = 0;
		dColumn = 0;

		//Left
		if (dir == 0){
			dColumn = -1;
		}

		//Up
		else if (dir == 1){
			dRow = -1;
		}

		//Right
		else if (dir == 2){
			dColumn = 1;
		}

		//Down
		else if (dir == 3){
			dRow = 1;
		}
	}

	public int getDirection(){

		//Left
		if (dRow == 0 && dColumn == -1){
			return 0;
		}

		//Up
		else if (dRow == -1 && dColumn == 0){
			return 1;
		}

		//Right
		else if (dRow == 0 && dColumn == 1){
			return 2;
		}

		//Down
		else {
			return 3;
		}	
	}

	public int getNextRow(){
		return row + dRow;
	}

	public int getNextColumn(){
		return column + dColumn;
	}

}