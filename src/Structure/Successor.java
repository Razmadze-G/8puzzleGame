package src.Structure;

import java.util.ArrayList;
import java.util.List;

public class Successor {
	public Successor() { }

	//ეს მეთოდი იღებს დაფის მიმდინარე მდგომარეობას და აბრუნებს შესაძლო მდგომარეობების სიას
	public List<BoardNode> successor(BoardNode node) {
		List<BoardNode> list = new ArrayList<>();
		int row = node.getRowBlank();
		int col = node.getColBlank();
		
		// თუ ზევით მოძრაობაა შესაძლებელი მაშინ იქმნება შესაბამისი შვილი / up
		if(row != 0) {
			BoardNode upNode = node.createChild(row-1, col);
			upNode.setDir(DIRECTIONS.UP);
			list.add(upNode);
		}
		
		// თუ დაბლა მოძრაობაა შესაძლებელი მაშინ იქმნება შესაბამისი შვილი / down
		if(row != 2) {
			BoardNode downNode = node.createChild(row+1, col);
			downNode.setDir(DIRECTIONS.DOWN);
			list.add(downNode);
		}
		
		// თუ მარცხნივ მოძრაობაა შესაძლებელი მაშინ იქმნება შესაბამისი შვილი / left
		if(col != 0) {
			BoardNode leftNode = node.createChild(row, col-1);
			leftNode.setDir(DIRECTIONS.LEFT);
			list.add(leftNode);
		}

		// თუ მარჯვნივ მოძრაობაა შესაძლებელი მაშინ იქმნება შესაბამისი შვილი / right
		if(col != 2) {
			BoardNode rightNode = node.createChild(row, col+1);
			rightNode.setDir(DIRECTIONS.RIGHT);
			list.add(rightNode);
		}

		return list;  // ბრუნდება შვილი წვეროების მასივი.
	}
}