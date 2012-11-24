package grapheme;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

public class GenerateGraphemDico {

	public static String VOCAB_PATH;
	public static String RULE_PATH;
	public static String OUTPUT_PATH;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (3 == args.length) {
			VOCAB_PATH = args[0];
			RULE_PATH = args[1];
			OUTPUT_PATH = args[2];

			try {
				writeToFile(OUTPUT_PATH,
						generateGraphem(createRuleMap(RULE_PATH), VOCAB_PATH));
			} catch (Exception e) {
				System.out.println("Make sure u type the path correctly. :D");
			}
		}
	}

	public static String generateGraphem(HashMap<String, String> map,
			String vocabPath) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new FileInputStream(vocabPath), "UTF-8");
		StringBuilder graphem = new StringBuilder();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			graphem.append(line);
			for (int i = 0; i < line.length(); i++) {
				String tmp = map.get(new String(new char[] { line.charAt(i) }));
				if (null != tmp) {
					graphem.append(tmp);
				}
			}
			graphem.append(System.getProperty("line.separator"));
		}
		return new String(graphem);
	}

	public static void writeToFile(String outputPath, String content)
			throws IOException {
		Writer out = new OutputStreamWriter(new FileOutputStream(outputPath),
				"UTF-8");
		try {
			out.write(content);
		} finally {
			out.close();
		}
	}

	public static HashMap<String, String> createRuleMap(String rulePath)
			throws FileNotFoundException, IOException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new FileInputStream(rulePath), "UTF-8");

		String key;
		String value;
		HashMap<String, String> map = new HashMap<>();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (-1 < line.indexOf('(')) {
				String[] aRule = (line.substring(line.indexOf('(') + 1,
						line.indexOf(')'))).split(",");
				key = (aRule[0]).substring(1, 2);
				value = aRule[1].substring(1, aRule[1].length() - 1);
				map.put(key, value);
			}
		}
		return map;
	}

}
