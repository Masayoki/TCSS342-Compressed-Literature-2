import java.util.PriorityQueue;

public class CodingTree {

    public MyHashTable<String, String> myCodes;

    public String myBits;

    public CodingTree(String theMessage) {
        MyHashTable<String, Integer> strFreq = countCharFreq(theMessage);
        PriorityQueue<HuffmanTree> priorityQueue = createTrees(strFreq);
        HuffmanTree hTree = createFullTree(priorityQueue);
        myCodes = new MyHashTable<String, String>(32768); //max size of table
        createCodeMap(hTree.root);
        myBits = encodeMessage(theMessage);
    }

    /**
     * MyHashTable creates a table and counts frequency of characters.
     * @param theString
     * @return frequency
     */
    private MyHashTable<String, Integer> countCharFreq(String theString) {
        MyHashTable<String, Integer> frequency = new MyHashTable<String, Integer>(32768);
        int i = 0;
        while (i < theString.length()) {
            StringBuilder sb = new StringBuilder();
            boolean run = false;
            boolean test = ((int) theString.charAt(i) >= 48 && (int) theString.charAt(i) <= 57)
                    || ((int) theString.charAt(i) >= 65 && (int) theString.charAt(i) <= 90)
                    || ((int) theString.charAt(i) >= 97 && (int) theString.charAt(i) <= 122)
                    || ((int) theString.charAt(i) == 39 || (int) theString.charAt(i) == 45);
            if (!test) {
                String temp = Character.toString(theString.charAt(i));
                if (frequency.containsKey(temp)) {
                    int count = frequency.get(temp);
                    frequency.put(temp, count + 1);
                } else {
                    frequency.put(temp, 1);
                }
            }
            while (i < theString.length() && test) {
                run = true;
                sb.append(theString.charAt(i));
                i++;
                test = ((int) theString.charAt(i) >= 48 && (int) theString.charAt(i) <= 57)
                        || ((int) theString.charAt(i) >= 65 && (int) theString.charAt(i) <= 90)
                        || ((int) theString.charAt(i) >= 97 && (int) theString.charAt(i) <= 122)
                        || ((int) theString.charAt(i) == 39 || (int) theString.charAt(i) == 45);
            }
            if (run && frequency.containsKey(sb.toString())) {
                int count = frequency.get(sb.toString());
                frequency.put(sb.toString(), count + 1);
                i--;
            } else if (run) {
                frequency.put(sb.toString(), 1);
                i--;
            }
            i++;
        }
        return frequency;
    }

    /**
     * PriorityQueue creates priority queue.
     * @param theCharFrequency
     * @return priorityQueue
     */
    private PriorityQueue<HuffmanTree> createTrees(MyHashTable<String, Integer> theCharFrequency) {
        PriorityQueue<HuffmanTree> priorityQueue = new PriorityQueue<HuffmanTree>();
        for (String s : theCharFrequency.keySet) {
            priorityQueue.offer(new HuffmanTree(s, theCharFrequency.get(s)));
        }
        return priorityQueue;
    }

    private HuffmanTree createFullTree(PriorityQueue<HuffmanTree> theQueue) {
        while (theQueue.size() > 1) {
            HuffmanTree tree1 = theQueue.remove();
            HuffmanTree tree2 = theQueue.remove();
            theQueue.add(new HuffmanTree(tree1, tree2));
        }
        return theQueue.remove();
    }

    private void createCodeMap(HuffmanTree.Node theRoot) {
        if (theRoot.left != null) {
            theRoot.left.code = theRoot.code + '0';
            createCodeMap(theRoot.left);
            theRoot.right.code = theRoot.code + '1';
            createCodeMap(theRoot.right);
        } else {
            myCodes.put(theRoot.myString, theRoot.code);
        }
    }

    /**
     * encodeMessage method
     * @param theMessage
     * @return sb.toString();
     */
    private String encodeMessage(String theMessage) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < theMessage.length()) {
            StringBuilder sb2 = new StringBuilder();
            boolean run = false;
            boolean test = ((int) theMessage.charAt(i) >= 48 && (int) theMessage.charAt(i) <= 57)
                    || ((int) theMessage.charAt(i) >= 65 && (int) theMessage.charAt(i) <= 90)
                    || ((int) theMessage.charAt(i) >= 97 && (int) theMessage.charAt(i) <= 122)
                    || ((int) theMessage.charAt(i) == 39 || (int) theMessage.charAt(i) == 45);
            if (!test) {
                String temp = Character.toString(theMessage.charAt(i));
                if (myCodes.containsKey(temp)) {
                    sb.append(myCodes.get(temp));
                }
            }
            while (i < theMessage.length() && test) {
                run = true;
                sb2.append(theMessage.charAt(i));
                i++;
                test = ((int) theMessage.charAt(i) >= 48 && (int) theMessage.charAt(i) <= 57)
                        || ((int) theMessage.charAt(i) >= 65 && (int) theMessage.charAt(i) <= 90)
                        || ((int) theMessage.charAt(i) >= 97 && (int) theMessage.charAt(i) <= 122)
                        || ((int) theMessage.charAt(i) == 39 || (int) theMessage.charAt(i) == 45);
            }
            if (run) {
                sb.append(myCodes.get(sb2.toString()));
                i--;
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * HuffmanTree inner class.
     * @author Michael Doan, Uladzimir Hanevich, and Salahuddin Majed
     *
     */
    private class HuffmanTree implements Comparable<HuffmanTree> {

        private Node root;

        public HuffmanTree(String theString, int theWeight) {
            root = new Node(theString, theWeight);
        }

        public HuffmanTree(HuffmanTree theTree1, HuffmanTree theTree2) {
            root = new Node();
            root.left = theTree1.root;
            root.right = theTree2.root;
            root.weight = theTree1.root.weight + theTree2.root.weight;
        }

        /**
         * Node Inner class
         * @author Michael Doan, Uladzimir Hanevich, and Salahuddin Majed
         *
         */
        private class Node {
            private String myString;
            private int weight;
            private Node left;
            private Node right;
            private String code = "";

            public Node() {
            }

            public Node(String theString, int theWeight) {
                myString = theString;
                weight = theWeight;
            }
        }

        /**
         * compareTo method compares the roots of the Huffman tree.
         * @return result.
         */
        public int compareTo(HuffmanTree theOther) {
            int result = 0;
            if (root.weight < theOther.root.weight) {
                result = -1;
            } else if (root.weight > theOther.root.weight) {
                result = 1;
            }
            return result;
        }
    }
}