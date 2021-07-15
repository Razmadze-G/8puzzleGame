package src.Solver;

import src.Game.Game;
import src.Structure.BoardNode;
import src.Structure.Successor;

import java.util.*;

public class Astar {
    List<BoardNode> path;
    private final BoardNode initialNode;
    public PriorityQueue<BoardNode> pQueue = new PriorityQueue<>(new manhattanComparator());
    public HashMap<Integer,BoardNode> visited = new HashMap<>();
    public Astar(BoardNode node) {
        this.initialNode = node;
    }

    //კომპარატორი მანჰეტენის ევრისტიკისთვის
    public class manhattanComparator implements Comparator<BoardNode> {
        public int compare(BoardNode a, BoardNode b) {
            return (a.getMaxCost() + manhattan(a)) - (b.getMaxCost() + manhattan(b));
        }
    }

    //მეთოდი აბრუნებს "მანჰეტენის მანძილს"
    public int manhattan(BoardNode node) {
        int result = 0;
        int[][] state = node.getMatrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                result += Math.abs(i - node.getRow(value)) + Math.abs(j - node.getCol(value));
            }
        }
        return result;
    }

    //A* ძებნის ალგორითმი რომელიც პრიორიტეტების რიგში სორტავს მონაცემებს მანჰეტენის მანძილის მიხედვით
    public void search() {
        BoardNode node = initialNode;
        pQueue.add(node);
        while (!(pQueue.isEmpty())) { // ციკლი არ დასრულდება სანამ პრიორიტეტების რიგი არ დაცარიელდება
            node = pQueue.poll();
            visited.put(node.hashCode(), node);
            if (Game.stringBoard(node.state).equals(Game.stringBoard(Game.goal))) {
                actionsPath(initialNode, node); //თუ ბოლო წვერო განხილულია მაშინ ეს მეთოდი მოძებნის პირველ წვერომდე გზას
                return;
            }
            Successor s = new Successor(); // Successor კლასი აბრუნებს შემდეგ შესაძლო სვლების მასივს
            List<BoardNode> list = s.successor(node);

            for (BoardNode temp : list) {
                if (!visited.containsKey(temp.hashCode())) //hashCode-ის გამოყენებით ვამოწმებთ არის თუ არა ეს წვერო უკვე გამოკვლეული
                    if (!(pQueue.contains(temp)))
                        pQueue.add(temp);
            }
        }
    }

    //მეთოდი ადგენს ყველა მოქმედებისგან შემდგარ გზას.
    public void actionsPath(BoardNode initialNode, BoardNode goalNode) {
        BoardNode tempNode = goalNode;
        List<BoardNode> list = new ArrayList<>();
        while(!(tempNode.equals(initialNode))) {
            list.add(tempNode);
            tempNode = tempNode.getParent();
        }
        list.add(initialNode);
        path = list;
        printPath();
    }

    //მოქმედებათა გზის ბეჭდვის მეთოდი
    public void printPath() {
        for(int i = path.size() - 1; i >= 0; i--) {
            System.out.println("\nDirection Moved: " + path.get(i).getDir());
            System.out.println(path.get(i).getDepth() - 1 + "th move");
            System.out.println(path.get(i).getCost() + "th node was moved");
            System.out.println("MaxCost: " + path.get(i).getMaxCost());
            System.out.println("Current Node: \n");
            System.out.println(Arrays.deepToString(path.get(i).getMatrix())
                    .replace("], ", "]\n")
                    .replace("[[", "[")
                    .replace("]]", "]") + "\n");
        }
    }
}