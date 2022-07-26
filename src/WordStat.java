import javax.swing.plaf.IconUIResource;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class WordStat {

    //to get and store the input words:
    private Tokenizer tokenizer;
    private ArrayList<String> wordList;
    //for the single word computations:
    private HashTable table;
    private ArrayList<String> uniqueWordList;
    private ComparableHashEntry[] array;
    private PriorityQueue<ComparableHashEntry> priorityQueue;
    //for the double word computations:
    private HashTable tableOfPairs;
    private ArrayList<String> uniqueWordPairs;
    private PriorityQueue<ComparableHashEntry> pairPriorityQueue;
    private ComparableHashEntry[] arrayOfPairs;



    /**
     * Constructor that takes in a text file
     * @param fileName address to the text file
     */
    public WordStat(String fileName){
        tokenizer = new Tokenizer(fileName);
        wordList = tokenizer.wordList();
        uniqueWordList = new ArrayList<>();
        table = new HashTable();
        int currentItemFreq;
        for(int i = 0; i < wordList.size(); i++){
            currentItemFreq = table.get(wordList.get(i));
            if(currentItemFreq == -1){//item is not in list
                table.put(wordList.get(i), 1);
                uniqueWordList.add(wordList.get(i));
                //System.out.println(wordList.get(i) + " is inserted into table\n");
            }else{//word is in the list
                table.update(wordList.get(i), currentItemFreq + 1);
            }
        }
        //uniqueWordList now has all unique words. no nulls
        //use priority queue to order the values by frequency
        priorityQueue = new PriorityQueue<>(uniqueWordList.size(), new Comparator<ComparableHashEntry>() {
            @Override
            public int compare(ComparableHashEntry o1, ComparableHashEntry o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0; i < uniqueWordList.size(); i++){
            priorityQueue.add(new ComparableHashEntry(uniqueWordList.get(i), table.get(uniqueWordList.get(i))));
        }
        //change the priority queue to an array to be easily handled
        array = new ComparableHashEntry[uniqueWordList.size()];
        for(int i = 0; i < uniqueWordList.size(); i++){
            array[i] = priorityQueue.poll();
        }
        //array should be in sorted order from the lowest frequency to the greatest.

         /*

        Same thing but for word pairs::

         */
        tableOfPairs = new HashTable();
        uniqueWordPairs = new ArrayList<>();
        String twoWordsTogether = "";
        for(int i = 0; i < wordList.size() - 1; i++){
            twoWordsTogether = wordList.get(i) + " " + wordList.get(i + 1);
            currentItemFreq = tableOfPairs.get(twoWordsTogether);
            if(currentItemFreq == -1){
                tableOfPairs.put(twoWordsTogether, 1);
                uniqueWordPairs.add(twoWordsTogether);
            }else{
                tableOfPairs.update(twoWordsTogether, currentItemFreq + 1);
            }
        }
        //uniqueWordPairs now has only unique wordsPairs with their associated frequency
        pairPriorityQueue = new PriorityQueue<>(uniqueWordPairs.size(), new Comparator<ComparableHashEntry>() {
            @Override
            public int compare(ComparableHashEntry o1, ComparableHashEntry o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0; i < uniqueWordPairs.size(); i++){
            pairPriorityQueue.add(new ComparableHashEntry(uniqueWordPairs.get(i), tableOfPairs.get(uniqueWordPairs.get(i))));
        }
        //pairPriorityQueue has all unique words ordered by frequency
        arrayOfPairs = new ComparableHashEntry[uniqueWordPairs.size()];
        for(int i = 0; i < uniqueWordPairs.size(); i++){
            arrayOfPairs[i] = pairPriorityQueue.poll();
        }
        //priority queue moved to an array for easy usability
    }

    /**
     * Constructor that takes a string of words
     * @param list string of words
     */
    public WordStat(String[] list){
        tokenizer = new Tokenizer(list);
        wordList = tokenizer.wordList();
        uniqueWordList = new ArrayList<>();
        table = new HashTable();
        int currentItemFreq;
        for(int i = 0; i < wordList.size(); i++){
            currentItemFreq = table.get(wordList.get(i));
            if(currentItemFreq == -1){//item is not in list
                table.put(wordList.get(i), 1);
                uniqueWordList.add(wordList.get(i));
            }else{//word is in the list
                table.update(wordList.get(i), currentItemFreq + 1);
            }
        }
        //uniqueWordList now has all unique words. no nulls
        //use priority queue to order the values by frequency
        priorityQueue = new PriorityQueue<>(uniqueWordList.size(), new Comparator<ComparableHashEntry>() {
            @Override
            public int compare(ComparableHashEntry o1, ComparableHashEntry o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0; i < uniqueWordList.size(); i++){
            priorityQueue.add(new ComparableHashEntry(uniqueWordList.get(i), table.get(uniqueWordList.get(i))));
        }
        //change the priority queue to an array to be easily handled
        array = new ComparableHashEntry[uniqueWordList.size()];
        for(int i = 0; i < uniqueWordList.size(); i++){
            array[i] = priorityQueue.poll();
        }
        //array should be in sorted order from the lowest frequency to the greatest.

         /*

        Same thing but for word pairs::

         */
        tableOfPairs = new HashTable();
        uniqueWordPairs = new ArrayList<>();
        String twoWordsTogether = "";
        for(int i = 0; i < wordList.size() - 1; i++){
            twoWordsTogether = wordList.get(i) + " " + wordList.get(i + 1);
            currentItemFreq = tableOfPairs.get(twoWordsTogether);
            if(currentItemFreq == -1){
                tableOfPairs.put(twoWordsTogether, 1);
                uniqueWordPairs.add(twoWordsTogether);
            }else{
                tableOfPairs.update(twoWordsTogether, currentItemFreq + 1);
            }
        }
        //uniqueWordPairs now has only unique wordsPairs with their associated frequency
        pairPriorityQueue = new PriorityQueue<>(uniqueWordPairs.size(), new Comparator<ComparableHashEntry>() {
            @Override
            public int compare(ComparableHashEntry o1, ComparableHashEntry o2) {
                return o1.compareTo(o2);
            }
        });
        for(int i = 0; i < uniqueWordPairs.size(); i++){
            pairPriorityQueue.add(new ComparableHashEntry(uniqueWordPairs.get(i), tableOfPairs.get(uniqueWordPairs.get(i))));
        }
        //pairPriorityQueue has all unique words ordered by frequency
        arrayOfPairs = new ComparableHashEntry[uniqueWordPairs.size()];
        for(int i = 0; i < uniqueWordPairs.size(); i++){
            arrayOfPairs[i] = pairPriorityQueue.poll();
        }
        //priority queue moved to an array for easy usability
    }


    /*

        THE METHODS BELOW:

     */


    public int wordCount(String word){
        for(int i = 0; i < array.length; i++){
            if(array[i].getKey().equals(word)){
                return array[i].getValue();
            }
        }
        return 0;
    }

    public int wordPairCount(String w1, String w2){
        String temp = w1 + " " + w2;
        for(int i = 0; i < arrayOfPairs.length; i++){
            if(temp.equals(arrayOfPairs[i].getKey()))
                return arrayOfPairs[i].getValue();
        }
        return 0;
    }

    public int wordRank(String word){
        for(int i = 0; i < array.length; i++) {
            if(array[i].getKey().equals(word)){
                return array.length - i;
            }
        }
        return 0;
    }

    public int wordPairRank(String w1, String w2){
        String temp = w1 + " " + w2;
        for(int i = 0; i < arrayOfPairs.length; i++){
            if(arrayOfPairs[i].getKey().equals(temp)){
                return arrayOfPairs.length - i;
            }
        }
        return 0;
    }

    public String[] mostCommonWords(int k){
        if(k > array.length)
            throw new NoSuchElementException("Not that many elements exist");
        String[] temp = new String[k];
        int counter = 0;
        for(int i = array.length - 1; i >= array.length - k; i--){
            temp[counter] = array[i].getKey();
            counter++;
        }
        return temp;
    }

    public String[] leastCommonWords(int k){
        if(k > array.length)
            throw new NoSuchElementException("Not that many elements exist");
        String[] temp = new String[k];
        for(int i = 0; i < k; i++){
            temp[i] = array[i].getKey();
        }
        return temp;
    }

    public String[] mostCommonWordPairs(int k){
        if(k > arrayOfPairs.length)
            throw new NoSuchElementException("Not that many elements exist");
        String[] temp = new String[k];
        int counter = 0;
        for(int i = arrayOfPairs.length - 1; i >= arrayOfPairs.length - k; i--){
            temp[counter] = arrayOfPairs[i].getKey();
            counter++;
        }
        return temp;
    }

    public String[] mostCommonCollocs(int k, String baseWord, int i){
        ArrayList<ComparableHashEntry> temp = new ArrayList<>();
        boolean foundBaseWord = false;
        boolean isOriginal;
        //find the location of baseWord in the wordList
        for(int j = 0; j < wordList.size(); j++){
            if(wordList.get(j).equals(baseWord)){
                foundBaseWord = true;
                if(i == -1) {//get all of the unique words before baseWord
                    for(int x = j + 1; x < wordList.size(); x++){
                        isOriginal = true;
                        for(int y = 0; y < temp.size(); y++){//checking for originality
                            if(wordList.get(x).equals(temp.get(y).getKey()))
                                isOriginal = false;
                        }
                        if(isOriginal){//this word is original. Need to add its frequency to the temp ArrayList
                            for(int y = 0; y < array.length; y++){
                                if(array[y].getKey().equals(wordList.get(x))){
                                    temp.add(array[y]);
                                }
                            }
                        }
                    }
                }else{//get all of the unique words after baseWord
                    for(int x = 0; x < j; x++){
                        isOriginal = true;
                        for(int y = 0; y < temp.size(); y++){//checking for originality
                            if(wordList.get(x).equals(temp.get(y).getKey()))
                                isOriginal = false;
                        }
                        if(isOriginal){//this word is original. Need to add its frequency to the temp ArrayList
                            for(int y = 0; y < array.length; y++){
                                if(array[y].getKey().equals(wordList.get(x))){
                                    temp.add(array[y]);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(!foundBaseWord)
            throw new NoSuchElementException("Base Word is not in the original text");
        //temp should have all of the words either before or after baseWord and their associated frequencies
        PriorityQueue<ComparableHashEntry> pq = new PriorityQueue<>(k, new Comparator<ComparableHashEntry>() {
            @Override
            public int compare(ComparableHashEntry o1, ComparableHashEntry o2) {
                return o1.compareTo(o2);
            }
        });
        for(int j = 0; j < temp.size(); j++){
            pq.add(temp.get(j));
        }
        //priority queue now has the words sorted.
        //remove the extra terms from pq
        while(pq.size() > k){
            pq.poll();
        }
        String[] output = new String[k];
        for(int j = 0; j < k; j++){
            output[output.length - 1 - j] = pq.poll().getKey();
        }
        return output;
    }

    public String generateWordString(int k, String startWord){
        StringBuilder output = new StringBuilder();
        int indexOfStartWord = -1;
        for(int i = 0; i < array.length; i++){
            if(array[i].getKey().equals(startWord)){
                indexOfStartWord = i;
                break;
            }
        }
        if (indexOfStartWord == -1 || indexOfStartWord + k >= array.length){
            throw new IllegalArgumentException();
        }
        for(int i = indexOfStartWord; i <= k; i++){
            output.append(array[i].getKey());
            if(i != k)
                output.append(" ");
        }
        return output.toString();
    }

    public static void main (String[] args) {
        WordStat obj = new WordStat(new String[]{"hey", "a", "Hello", "hey", "hello", "Hello", "Hey", "Hello", "a", "A", "A", "a", "Ben"});
        System.out.println("Can make a word stat object with various words. Creating this object automatically uses the tokenizer, hashtable, and hashentry methods");
        System.out.println("wordCount of a = " + obj.wordCount("a"));
        System.out.println("wordPairCount of a and hello is " + obj.wordPairCount("a", "hello"));
        System.out.println("wordrank of a = " + obj.wordRank("a"));
        System.out.println("wordPairRank of a and hello is " + obj.wordPairRank("a", "hello"));
        String[] temp = obj.mostCommonWords(3);
        System.out.println("the 3 most common words are ");
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }
        String[] temp1 = obj.leastCommonWords(3);
        System.out.println("the three least common words are ");
        for (int i = 0; i < temp1.length; i++) {
            System.out.println(temp1[i]);
        }
        String[] temp2 = obj.mostCommonWordPairs(3);
        System.out.println("the 3 most common word pairs are");
        for (int i = 0; i < temp2.length; i++) {
            System.out.println(temp2[i]);
        }
        String[] temp3 = obj.mostCommonCollocs(2, "hello", 1);
        System.out.println("The 2 most common words that occur to the right of hello are");
        for (int i = 0; i < temp3.length; i++) {
            System.out.println(temp3[i]);
        }
    }

    private void printArray(){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i].getKey() + " has freq " + array[i].getValue() + "\n");
        }
    }


    private class ComparableHashEntry extends HashEntry implements Comparable<ComparableHashEntry> {


        private ComparableHashEntry(String key, int value){
            super(key, value);
        }

        @Override
        public int compareTo(ComparableHashEntry o) {
            return this.getValue() - o.getValue();
        }

    }

}
