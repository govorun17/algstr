package naive;

import java.util.Scanner;

/**
 * В наивном алгоритме поиск всех допустимых сдвигов производится с помощью цикла,
 * в котором проверяется условие t[s..s+m−1]=p для каждого из n−m+1 возможных значений s.
 *
 * Алгоритм работает за O(m⋅(n−m)). В худшем случае m= n/2, что даёт O(n^2/4)=O(n^2).
 * Однако если m достаточно мало по сравнению с n,
 * то тогда асимптотика получается близкой к O(n),
 * поэтому этот алгоритм достаточно широко применяется на практике.
 *
 * - Требует O(1) памяти.
 * - Приемлемое время работы на практике. Благодаря этом алгоритм применяется,
 * например, в браузерах и текстовых редакторах (при использовании Ctrl + F),
 * потому что обычно паттерн, который нужно найти, очень короткий по сравнению с самим текстом.
 * Также наивный алгоритм используется в стандартных библиотеках языков высокого уровня (C++, Java),
 * потому что он не требует дополнительной памяти.
 * - Простая и понятная реализация.
 */
public class Main {
	private static int compares = 0;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter str: ");
		String str = scanner.nextLine();

		System.out.print("Enter pattern: ");
		String pattern = scanner.nextLine();

		System.out.println("Position: " + naive(str, pattern));
		System.out.println("Compares count: " + compares);
	}

	private static int naive(String str, String pattern) {
		for(int i = 0; i < str.length() - pattern.length() + 1; i++) {
			int res = compareStrings(str, pattern, i);
			if(res == pattern.length()) {
				return i + 1;
			}
		}
		return -1;
	}

	static int compareStrings(String str, String pattern, int position) {
		if (position > str.length()) {
			return -1;
		}
		else {
			var count = 0;
			for (int i = 0; i < pattern.length(); i++) {
				if (str.charAt(position + i) == pattern.charAt(i)) {
					count++;
				}
				compares++;
			}
			return count;
		}
	}
}
