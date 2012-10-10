/*
 * author: Adam Thrash
 * function: Comment Trimming
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Trimmer {
    
    public static void main(String[] args) throws FileNotFoundException {
        Trimmer trimmer = new Trimmer("stopwords.txt", "outputComment.txt");
        trimmer.processInput();
        trimmer.removeStopWords();
        trimmer.print();
    }
    /**
     * holds the list of stopwords in an ArrayList
     */
    public ArrayList<String> listOfStopWords;
    public ArrayList<String> listOfCommentWords;
    public ArrayList<String> notStopWords;
    public File stopwords;
    public File input;
    
    /**
     * Constructor
     * @param stopwords path to text file of stopwords
     * @param input path to text file containing comments from code to index
     */
    public Trimmer(String stopwordsPath, String inputPath){
        stopwords = new File(stopwordsPath);
        input = new File(inputPath);
        listOfStopWords = new ArrayList<String>();
        listOfCommentWords = new ArrayList<String>();
        notStopWords = new ArrayList<String>();
    }
    
    /**
     * Processes the input files of the list of stopwords and the comments to be
     * checked for stopwords by placing the contents of those two files into
     * ArrayList<String>
     * @throws FileNotFoundException
     */
    public void processInput() throws FileNotFoundException{
        Scanner scanner = new Scanner(new FileReader(stopwords));
        try {
            while (scanner.hasNextLine()){
                listOfStopWords.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        
        scanner = new Scanner(new FileReader(input));
        try {
            while (scanner.hasNextLine()){
                processLine(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Processes individual lines of comments from the input file containing
     * comments.
     * Helps processInput() add the comments to an ArrayList<String>.
     * @param line the line to be processed.
     */
    public void processLine(String line){
        Scanner scanner = new Scanner(line);
        try {
            while (scanner.hasNext()){
                listOfCommentWords.add(scanner.next());
            }
        } finally {
            scanner.close();
        }
    }
    
    
    /**
     * The main function of the program; removes stopwords by matching each
     * comment word against the entire list of stopwords and adding the comment
     * to the ArrayList<String> notStopWords if the comment does not match any
     * stopwords.
     */
    public void removeStopWords(){
        for (int i = 0; i<listOfCommentWords.size(); i++){
            int counter = 0;
            String comment = listOfCommentWords.get(i);
            comment = comment.trim();
            comment = comment.toLowerCase();
            for (int j = 0; j<listOfStopWords.size(); j++){
                String stopword = listOfStopWords.get(j);
                stopword = stopword.trim();
                stopword = stopword.toLowerCase();
                //System.err.println(comment + " vs " + stopword);
                if (!comment.equals(stopword)){
                    counter++;
                }
                
            }
            if (counter == listOfStopWords.size()){
                notStopWords.add(comment);
            }
        }
    }
    
    /**
     * Mostly useless function to allow me to check my work. Prints out the
     * array that contains all words from the comments that are not stopwords.
     */
    public void print(){
        //System.out.print(listOfCommentWords);
        //System.out.print(listOfStopWords);
        System.out.print(notStopWords);
    }
    
    
    
}