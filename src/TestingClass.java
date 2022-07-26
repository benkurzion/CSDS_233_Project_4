
import org.junit.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestingClass {

    @Test
    public void testingTokenizer(){
    /*

       I HAVE TESTED THE TOKENIZER WITH A TEXT DOCUMENT. I HAVE INCLUDED THE TESTING FILE IN MY SUBMISSION
       THIS MAIN METHOD SHOULD RUN THE TEST

        public static void main(String[] args){
            Tokenizer obj = new Tokenizer("C:\\Users\\benku\\College\\2021-22\\CSDS 233\\Project 4\\TestingTokenizer.txt");//replace with file location
            for(String s : obj.wordList()){
                System.out.println(s);
            }
        }
     */
        Tokenizer t = new Tokenizer(new String[]{});
        Tokenizer t2 = new Tokenizer(new String[]{"ben"});
        Tokenizer t3 = new Tokenizer(new String[]{"^&&*(_"});
        ArrayList<String> a = new ArrayList<>();
        //empty token
        compareArrayList(a, t.wordList());
        ArrayList<String> a2 = new ArrayList<>();
        a2.add("ben");
        //single element token
        compareArrayList(a2, t2.wordList());
        ArrayList<String> a3 = new ArrayList<>();
        //compare only special characters
        compareArrayList(a3, t3.wordList());
    }


    @Test
    public void testingHashEntry(){
        HashEntry entry = new HashEntry("testing", 15);
        assertEquals("testing", entry.getKey());
        assertEquals(15, entry.getValue());
        entry.setValue(10);
        assertEquals(10, entry.getValue());
    }

    @Test
    public void testingHashTable(){
        HashTable table = new HashTable();
        table.put("hello", 1 );
        table.put("ben", 5);
        table.put("world", 2);
        assertEquals("world (2) ben (5) hello (1) ", table.print());
        assertEquals(2, table.get("world"));
        //forcing collisions in put
        table.put("one", 1, 1);
        table.put("two", 2, 1);
        table.put("three", 3, 1);
        assertEquals(1, table.get("one", 1));
        assertEquals(2, table.get("two", 1));
        assertEquals(3, table.get("three", 1));
        //testing update
        table.update("hello", 10);
        assertEquals(10, table.get("hello"));
        //testing update non-existent word
        table.update("four", 4);
        assertEquals(4, table.get("four"));
        //inserting word that is in table
        table.put("world", 5);
        assertEquals(2, table.get("world"));
    }



    @Test
    public void testingWordStat(){
        WordStat obj = new WordStat(new String[]{"hey", "a", "Hello", "hey", "hello", "Hello", "Hey", "Hello", "a", "A", "A", "a", "Ben"});
        assertEquals(5, obj.wordCount("a"));
        assertEquals(4, obj.wordCount("hello"));
        assertEquals(1, obj.wordCount("ben"));
        assertEquals(1,obj.wordRank("a"));
        assertEquals(3, obj.wordRank("hey"));
        compare1DArray(new String[]{"a" , "hello", "hey"}, obj.mostCommonWords(3));
        compare1DArray(new String[]{"ben", "hey", "hello"}, obj.leastCommonWords(3));
        assertEquals(2, obj.wordPairCount("hey", "hello"));
        compare1DArray(new String[]{"a a", "hello hey", "hey hello"}, obj.mostCommonWordPairs(3));
        compare1DArray(new String[]{"a", "hello"}, obj.mostCommonCollocs(2, "a", 1));
        assertEquals("ben hey hello", obj.generateWordString(2, "ben"));
        WordStat obj2 = new WordStat("C:\\\\Users\\\\benku\\\\College\\\\2021-22\\\\CSDS 233\\\\Project 4\\\\src\\\\TestingTokenizer.txt");
        assertEquals(1, obj2.wordRank("the"));
        assertEquals(3, obj2.wordPairCount("my", "wife"));
    }
    private void compare1DArray(String[] expected, String[] actual){
        assertEquals(expected.length, actual.length);
        for(int i = 0; i < expected.length; i++){
            assertEquals(expected[i], actual[i]);
        }
    }

    private void compareArrayList(ArrayList<String> expected, ArrayList<String> actual){
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), actual.get(i));
        }
    }

}
