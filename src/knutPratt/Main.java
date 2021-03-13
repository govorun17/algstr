package knutPratt;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Префикс-функция от строки S строится за O(S)=O(P+T). Проход цикла по строке S содержит O(T) итераций.
 * Итого, время работы алгоритма оценивается как O(P+T).
 *
 * Предложенная реализация имеет оценку по памяти O(P+T).
 */
public class Main {
	private static int compares = 0;
	//abcabvabnabm
	//aaeffefefdaaa
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter str: ");
		String str = scanner.nextLine();

		System.out.print("Enter pattern: ");
		String pattern = scanner.nextLine();

		StringBuilder res = new StringBuilder();
		for(Integer c : kp(str, pattern)) {
			res.append(c).append(" ");
		}

		System.out.println("Positions: " + res.toString());
		System.out.println("Compares count: " + compares);
	}

	private static List<Integer> kp(String str, String pattern) {
		List<Integer> matches = new ArrayList<>();
		int i = 0;
		int j = 0;
		int[] pref = getPrefix(pattern);
		int[] p = mover(pref);

		while (i < str.length() - pattern.length() + j + 1) {
			Pair<Integer, Integer> pair = match(str, pattern, i, j);
			i = pair.getKey();
			j = pair.getValue();
			if (j == pattern.length()) {
				matches.add(i - pattern.length());
				i -= p[j] - 1;
				j -= p[j];
			}
			else if (j == 0) {
				i++;
			}
			else {
				j = pref[j-1];
			}

		}

		return matches;
	}

	private static int[] mover(int[] pref) {
		int[] result = new int[pref.length + 1];
		result[0] = 0;
		for(int i = 1; i <= pref.length; i++) {
			result[i] = pref[i-1] +1;
		}
		return result;
	}

	private static int[] getPrefix(String s) {
		int[] result = new int[s.length()];
		result[0] = 0;
		int index = 0;

		for (int i = 1; i < s.length(); i++) {
			while (index >= 0 && s.charAt(index) != s.charAt(i)) {
				index--;
			}
			index++;
			result[i] = index;
		}

		return result;
	}

	private static Pair<Integer, Integer> match(String str, String pattern, int i, int j) {
		while (i < str.length() && j < pattern.length() && str.charAt(i) == pattern.charAt(j)) {
			compares++;
			j++;
			i++;
		}
		compares++;
		return new Pair<>(i, j);
	}
}
