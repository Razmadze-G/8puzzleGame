package src.Game;

import org.jetbrains.annotations.NotNull;
import src.Structure.BoardNode;
import src.Solver.Astar;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static int[][] start = new int[3][3]; // შემოტანილი დაფა
    public static int[][] goal = {{1, 2, 3},
                                  {4, 5, 6},
                                  {7, 8, 0}}; // მიზნის დაფა

    /* რამდენიმე ამოხსნადი მაგალითი:
    * 173802645    453728106    738215064
      {1, 7, 3}    {4, 5, 3}    {7, 3, 8}
      {8, 0, 2}    {7, 2, 8}    {2, 1, 5}
      {6, 4, 5}    {1, 0, 6}    {0, 6, 4} */

    public static void main(String[] args) {
        System.out.println("აუცილებელია, რომ დაფა შეიცავდეს მხოლოდ ერთ ცალ '0'-ს! იგი თამაშში ცარიელ უჯრად აღიქმება");
        System.out.println("\nშეიყვანეთ საწყისი დაფა...");
        while(true){
            fillStateFromConsole(); // დაფის ინიციალიზაცია
            if(isSolvable(start)){
                if(equalsGoalTable())
                    System.out.println("მოცემული დაფა ემთხვევა მიზნის მდგომარეობას.");
                break;
            }
            System.out.println("არაამოხსნადია! სცადეთ თავიდან.");
        }
        
        Astar astar = new Astar(new BoardNode(start));
        astar.search(); // ამოხსნა
    }

    //დაფის კონსოლიდან შეტანის მეთოდი
    public static void fillStateFromConsole() {
        int ZeroCount = 0;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                try {
                    start[i][j] = Integer.parseInt(JOptionPane.showInputDialog("Enter the value of tile [" + i + "][" + j + "] : "));
                    if(start[i][j] == 0)
                        ZeroCount++;
                } catch (HeadlessException e) {
                    e.printStackTrace();
                }
        if(ZeroCount == 0) {
            System.out.println("აუცილებელია, რომ დაფა შეიცავდეს '0'-ს!");
            fillStateFromConsole();
        } else if(ZeroCount > 1) {
            System.out.println("აუცილებელია, რომ დაფა შეიცავდეს მხოლოდ ერთ ცალ '0'-ს!");
            fillStateFromConsole();
        }
    }

    //მეთოდი აბრუნებს მიმდინარე დაფის String ვერსიას
    public static @NotNull String stringBoard(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] ints : board)
            for (int anInt : ints)
                sb.append(anInt);
        return sb.toString();
    }

    //მეთოდი ადგენს ამოხსნადია თუ არა მოცემული დაფა
    public static boolean isSolvable(int[][] board) {
        int invCount = 0;
        for (int i = 0; i < 3 - 1; i++)
            for (int j = i + 1; j < 3; j++)
                if (board[j][i] > 0 && board[j][i] > 0 && board[j][i] > board[i][j])
                    invCount++;
        return invCount % 2 != 0;
    }

    private static boolean equalsGoalTable() {
        boolean equals = true;
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if (start[i][j] != goal[i][j]) {
                    equals = false;
                    break;
                }
        return equals;
    }
}