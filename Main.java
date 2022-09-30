import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    //We need to be able to run the text file WarAndPeace to be able to compress it and to do the same with the other files
    public static void main(String[] theArgs) throws IOException {
        long startTime = System.currentTimeMillis();
        InputStream inFile = null;
        InputStreamReader inFileReader = null;
        BufferedReader input = null;
        PrintStream op1 = null;
        PrintStream op2 = null;
        StringBuilder sb = new StringBuilder();
        try {
            File original = new File("./src/WarAndPeace.txt");
            inFile = new FileInputStream("./src/WarAndPeace.txt");
            inFileReader = new InputStreamReader(inFile);
            input = new BufferedReader(inFileReader);
            op1 = new PrintStream(new File("./src/codes.txt"));
            op2 = new PrintStream(new File("./src/compressed.txt"));
            int value = 0;

            while ((value = input.read()) != -1) {
                sb.append((char) value);
            }

            CodingTree tree = new CodingTree(sb.toString());
            op1.print(tree.myCodes.toString());

            for (int i = 0; i < tree.myBits.length(); i += 8) {
                String s = new String(tree.myBits.substring(i, Math.min(tree.myBits.length(), i + 8)));
                int b = Integer.parseInt(s, 2);
                op2.print((char) b);
            }

            inFile.close();
            inFileReader.close();
            input.close();
            op1.close();
            op2.close();
            File result = new File("./src/compressed.txt");
            long ol = original.length();
            long rl = result.length();
            System.out.println("Original File Size: " + ol * 8 + " bits");
            System.out.println("Compressed File Size: " + rl * 8 + " bits");
            double cr = ((double)rl/ol) * 100;
            System.out.println(String.format("Compression Ratio: %.2f", cr) + "%");

        } catch (FileNotFoundException e) {
            System.out.println("Can't open file - " + e);

        } catch (Exception e) {
            e.printStackTrace();

        }
        long st = System.currentTimeMillis();
        System.out.println("Running time: " + (st - startTime) + " milliseconds");

        //testMyHashTable();
        //testCodingTree();
    }
    //Used to test MyHashTable
    public static void testMyHashTable() {
        MyHashTable<String, String> temp = new MyHashTable<String, String>(1 << 15);
        temp.put("yes", "please");
        temp.put("No", "Bugs");
        System.out.println(temp.get("yes") + temp.get("No"));
        temp.put("yes", "What do you mean");
        System.out.println(temp);
    }
    //Used to test CodingTree
    public static void testCodingTree() throws IOException {
        String m1 = new String(Files.readAllBytes(Paths.get("./src/WarAndPeace.txt")));
        CodingTree testOutput = new CodingTree(m1);
        System.out.println(testOutput.myCodes.toString());
    }
}
