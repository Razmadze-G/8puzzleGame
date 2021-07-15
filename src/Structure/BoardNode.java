package src.Structure;

import java.util.ArrayList;
import java.util.List;

public class BoardNode {

	public int[][] state;
	public List<BoardNode> children;
	public BoardNode parent;
	public int depth;
	public int blankRow;
	public int blankCol;
	public DIRECTIONS direction;
	public String stringState;
	public int cost;
	public int maxCost;

	public BoardNode(int [][] state) {
		this.state = state;
		this.depth = 1; // სიღრმე
		this.children = new ArrayList<>(); //შვილი წვერო
		this.parent = null;
		this.cost = 0;
		this.maxCost = 0;
		this.stringState = stringBoard();
		this.direction = null;
		determineBlankNode();
	}

	public void determineBlankNode(){
		for(int i=0; i<=2; i++)
			for(int j=0; j<=2; j++)
				if(state[i][j]==0) {
					this.blankRow = i;
					this.blankCol = j;
					break;
				}
	}

	//მეთოდი აბრუნებს დაფის სტრინგ ვერსიას
	public String stringBoard() {
		StringBuilder sb = new StringBuilder();
		for (int[] ints : state)
			for (int anInt : ints)
				sb.append(anInt);
		return sb.toString();
	}

	//წვეროსთვის შვილის განსაზღვრა
	public void addChild(BoardNode child) {
		child.setParent(this);
		child.setDepth(this.getDepth()+1);
		child.setMaxCost(child.getCost());
		this.children.add(child);
	}

	public BoardNode createChild(int a, int b) {
		int[][] temp = new int[state.length][state.length];
		for(int i=0; i<state.length; i++)
			System.arraycopy(state[i], 0, temp[i], 0, state[i].length);
		temp[blankRow][blankCol] = temp[a][b];
		int cost = state[a][b]; 
		temp[a][b] = 0;
		BoardNode child = new BoardNode(temp);
		child.setCost(cost); //შვილის დამატება
		addChild(child);
		return child;
	}

	@Override
	public boolean equals(Object object ) {
		if(!(object instanceof BoardNode check))
			return false;
		return check.getString().equals(this.getString());
	}

	@Override
	public int hashCode() {	//ჰეშკოდი, რომელიც გენერირდება დაფის სტრინგ ვერსიიდან
		int result = 17;
		result = 37 * result + this.getString().hashCode();
		return result;
	}

	public void setParent(BoardNode parent) { //setting the Parent of the node
		this.parent = parent;
	}
	public void setDepth(int depth) {  //setting the Depth of the node
		this.depth = depth;
	}
	public int getDepth() {  //getting the Depth of the node
		return depth;
	}
	public BoardNode getParent() {  //getting the Parent of the node
		return parent;
	}
	public int getRowBlank() {  //getting the Row of the zero tile
		return blankRow;
	}
	public int getColBlank() { //getting the Column of the zero tile
		return blankCol;
	}
	public int [][] getMatrix(){ //getting the state in array form
		return state;
	}
	public int getCost() { //getting the cost of last move
		return this.cost;
	}
	public void setDir(DIRECTIONS d) {				//setting the Direction moved
		this.direction = d;
	}
	public DIRECTIONS getDir() {				//getting the direction moved
		return direction;
	}
	public String getString() { return stringState; }
	public void setCost(int i) { this.cost = i; }
	public void setMaxCost(int i) { this.maxCost = this.getParent().getMaxCost() + i; }
	public int getMaxCost() { return maxCost; }

	//მიზნის მდგომარეობაში მონაცემის სტრიქონის ძებნის მეთოდი
	public int getRow(int value) {
		int row = 0;
		int [][] goal = {{1,2,3},{4,5,6},{7,8,0}};
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				if(goal[i][j] == value) {
					row = i;
					break;
				}
		return row;
	}

	//მიზნის მდგომარეობაში მონაცემის სვეტის ძებნის მეთოდი
	public int getCol(int value) {
		int col = 0;
		int [][] goal = {{1,2,3},{4,5,6},{7,8,0}};
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				if(goal[i][j] == value)
					col = j;
		return col;
	}
}