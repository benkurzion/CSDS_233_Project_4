import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tokenizer {
    private ArrayList<String> wordArr = new ArrayList<>();

    public Tokenizer(String fileName) {
        String currentLine;
        int beginningOfWord = 0;
        boolean inWord = false;
        String temp = "";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(fileName));
            currentLine = bf.readLine();
            while (currentLine != null) {
                beginningOfWord = 0;
                for (int index = 0; index < currentLine.length(); index++) {
                    temp = "";
                    if (currentLine.charAt(index) == ' ' || index == currentLine.length() - 1) {
                        if (index == currentLine.length() - 1) {
                            temp = (currentLine.substring(beginningOfWord, index + 1).toLowerCase());
                        }else if (inWord) {
                            temp = (currentLine.substring(beginningOfWord, index).toLowerCase());
                        }
                        temp = temp.replaceAll("[^a-zA-Z0-9]", "");
                        if (!temp.equals(""))
                            wordArr.add(temp);
                        inWord = false;
                        beginningOfWord = index + 1;
                    } else {
                        inWord = true;
                    }
                }
                currentLine = bf.readLine();
            }
        } catch (IOException e) {
            System.out.println("Caught exception for BufferedReader");
        }
    }

    //overloaded constructor
    public Tokenizer(String[] listOfWords) {
        int beginningOfWord;
        boolean inWord;
        String temp = "";
        for (int i = 0; i < listOfWords.length; i++) {
            beginningOfWord = 0;
            inWord = false;
            for (int index = 0; index < listOfWords[i].length(); index++) {
                if (listOfWords[i].charAt(index) == ' ' || index == listOfWords[i].length() - 1) {
                    if (index == listOfWords[i].length() - 1) {
                        temp = (listOfWords[i].substring(beginningOfWord, index + 1).toLowerCase());

                    }else if (inWord) {
                        temp = (listOfWords[i].substring(beginningOfWord, index).toLowerCase());
                    }
                    temp = temp.replaceAll("[^a-zA-Z0-9]", "");
                    if (!temp.equals(""))
                        wordArr.add(temp);
                    inWord = false;
                    beginningOfWord = index + 1;
                } else {
                    inWord = true;
                }
            }
        }
    }

    public ArrayList<String> wordList() {
        return this.wordArr;
    }

    /*
    public static void main(String[] args) {
        Tokenizer obj = new Tokenizer("C:\\Users\\benku\\College\\2021-22\\CSDS 233\\Project 4\\TestingTokenizer.txt");
        for (String s : obj.wordList()) {
            System.out.println(s);
        }

        Tokenizer obj2 = new Tokenizer(new String[]{"Hel**lo ben pie  ", "world", "a"});
        for(String s : obj2.wordList()){
            System.out.println(s);
        }
    }

     */
}
