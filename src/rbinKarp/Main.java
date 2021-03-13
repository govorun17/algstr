package rbinKarp;


import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {

	}

	private static final int basement = 59;
	private static final int divider = 433494437;

	public List<Integer> GetPatternEntries(String text, String pattern) {
		List<Integer> result = new ArrayList<>();
		int textLength = text.length();
		int patternLength = pattern.length();
		int hashText = text.charAt(0) % divider;
		int hashPattern = pattern.charAt(0) % divider;
		int multiplier = 1;
		int i = 1;

		for(; i < patternLength; ++i) {
			hashText = (hashText * basement + text.charAt(i)) % divider;
			hashPattern = (hashPattern * basement + pattern.charAt(i)) % divider;
			multiplier = (multiplier * basement) % divider;
		}

		for(i = 0; i < textLength - patternLength; ++i) {
			if(hashText == hashPattern) {
				boolean match = true;
				for(int j = 0; j < patternLength; ++j) {
					if(text.charAt(i + j) != pattern.charAt(j)) {
						match = false;
						break;
					}
				}
				if(match) {
					result.add(i);
				}
			}
			hashText = (basement * (hashText - multiplier * text.charAt(i)) + text.charAt(i + patternLength) + divider) % divider;
		}
		if(hashText == hashPattern) {
			boolean match = true;
			for(int j = 0; j < patternLength; ++j) {
				if(text.charAt(i + j) != pattern.charAt(j)) {
					match = false;
					break;
				}
			}
			if(match) {
				result.add(i);
			}
		}
		return result;
	}
}