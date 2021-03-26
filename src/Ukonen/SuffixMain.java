package Ukonen;

import java.util.Scanner;

public class SuffixMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter str: ");
        String str = scanner.nextLine();


        SuffixTree suffixTree=new SuffixTree();
        suffixTree.buildSuffixTree(str+"#");
        System.out.println("root|");
        suffixTree.setSuffixIndexByDFS(suffixTree.root,"    ");

    }
}
