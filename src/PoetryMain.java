import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class PoetryMain {
    public static void main(String[] args) throws FileNotFoundException {

        WritePoetry poem1 = new WritePoetry("green.txt");
        WritePoetry poem2 = new WritePoetry("Lester.txt");
        WritePoetry poem3 = new WritePoetry("HowMany.txt");
        WritePoetry poem4 = new WritePoetry("Zebra.txt");

        System.out.println(poem1.WritePoem( "sam", 20, true));
        System.out.println(poem2.WritePoem( "lester", 30, true));
        System.out.println(poem3.WritePoem("how", 30, false));
        System.out.println(poem4.WritePoem( "are", 50, true));
    }


    public static class WritePoetry{
        HashTable<String, WordFreqInfo> hashTable;
        public WritePoetry(String fileName) throws FileNotFoundException {
            hashTable = new HashTable<>();
            File file = new File(fileName);
            if(!file.exists()){
                System.out.println("Error: File not found: " + fileName);
                System.exit(1);
            }else {
                Scanner scanner = new Scanner(file);
                String word1 = scanner.next().toLowerCase();
                String word2 = scanner.next().toLowerCase();
                while(!word1.equals("")) {
                    if(hashTable.contains(word1)){
                        WordFreqInfo wordInfo = hashTable.find(word1);
                        wordInfo.occurCt++;
                        if(!word2.equals("")) {
                            wordInfo.updateFollows(word2);
                        }
                    }else{
                        WordFreqInfo wordInfo = new WordFreqInfo(word1, 1);
                        if(!word2.equals("")) {
                            wordInfo.updateFollows(word2);
                        }
                        hashTable.insert(word1, wordInfo);
                    }
                    word1 = word2;
                    try{
                        word2 = scanner.next().toLowerCase();
                    }catch (NoSuchElementException ex){
                        word2 = "";
                    }

                }
            }
        }

        public String WritePoem( String word, int wordCount, boolean printHashTable){
            if(printHashTable){
                System.out.println(hashTable.toString(hashTable.size()));

            }
            ArrayList<String> punctuation = new ArrayList<>();
            punctuation.add(".");
            punctuation.add(",");
            punctuation.add("!");
            punctuation.add("?");

            StringBuilder poem = new StringBuilder();
            for(int i = 0; i < wordCount; i++){
                poem.append(word);

                word = pickNextWord(word);
                boolean isPunct = false;
                for(String punct: punctuation){
                    if(word.equals(punct)){
                        i++;
                        poem.append(word);
                        if(i < wordCount-1) poem.append("\n");
                        word = pickNextWord(word);
                        isPunct = true;
                        break;
                    }
                }
                if(!isPunct && i< wordCount-1) poem.append(" ");
            }
            poem.append(".\n");
            return poem.toString();
        }

        private String pickNextWord(String word){
            WordFreqInfo wordFreqInfo = hashTable.find(word);
            String nextWord = "";

            int totalFollows = wordFreqInfo.occurCt;
            int wordFollows = wordFreqInfo.followList.size();
            int[] probabilityArray = new int[wordFollows];

            int i = 0;
            for(WordFreqInfo.Freq freq: wordFreqInfo.followList){
               probabilityArray[i] = freq.followCt;
               if(i != 0){
                   probabilityArray[i] += probabilityArray[i-1];
               }
               i++;
            }

            Random random = new Random();
            int randomNumber = random.nextInt(totalFollows);
            for (int j = 0; j < probabilityArray.length; j++) {
                if (randomNumber <= probabilityArray[j] ){
                    nextWord = wordFreqInfo.followList.get(j).follow;
                    break;
                }
            }

            return nextWord;
        }
    }
}

