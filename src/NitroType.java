import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class NitroType implements KeyListener {

    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];
    private int avg;
    private int counter;
    private char current;
    private String c;
    private Car car;
    private NitroTypeViewer n;
    private boolean gameStatus;
    private String temp;
    long start;
    long end;
    public NitroType(String word) {
        gameStatus = true;
        words = new ArrayList<String>();
        n = new NitroTypeViewer(this);
        car = new Car(0, n);
        n.addKeyListener(this);
        counter = 0;
        avg = 0;
        temp = word;
        start = System.currentTimeMillis();
    }

    public void generate() {
        makeWords("", temp);
    }
    public void makeWords(String word, String letters) {
        if (letters.length() == 1) {
            words.add(word);
            words.add(word + letters.charAt(0));
            return;
        }
        if(!word.isEmpty()){
            words.add(word);
        }
        for (int i = 0; i < letters.length(); i++) {
            if (i == 0) {
                makeWords(word + letters.charAt(i), letters.substring(i + 1));
            }
            else {
                makeWords(word + letters.charAt(i), letters.substring(0, i) + letters.substring(i + 1));
            }

        }
    }

    public void game() {
        Scanner input = new Scanner(System.in);
        if (words == null) {
            words.add("Sorry but your letters couldn't find a combination, so write this instead!");
        }
        else {
            words.remove(0);
        }
        n.repaint();
    }
    public void keyPressed(KeyEvent e) {
        current = e.getKeyChar();
        c = "";
        c += current;
        if (c.equals(temp.substring(0,1))) {
            car.setX(car.getX() + avg);
            temp = temp.substring(1);
            counter++;
            if (temp.isEmpty()) {
                end = System.currentTimeMillis();
                gameStatus = false;
            }
        }
        n.repaint();
    }

    public void removeDuplicates(){
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }
    public void keyTyped(KeyEvent e) {
        // Nothing required for this program.
        // However, as a KeyListener, this class must supply this method
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing required for this program.
        // However, as a KeyListener, this class must supply this method
    }

    public boolean find(String word) {
        //Special thanks to Diego Villegas for helping me write find!
        int low = 0;
        int high = DICTIONARY_SIZE;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (word.equals(DICTIONARY[mid])) {
                return true;
            } else if (word.compareTo(DICTIONARY[mid]) < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
    }

    public void checkWords() {
        for(int i = 0; i < words.size(); i++){
            if(!find(words.get(i))){
                words.remove(i);
                i--;
            }
        }
    }

    public int getCounter() {
        return counter;
    }

    public Car getCar() {
        return car;
    }

    public String getTest() {
        return temp;
    }

    public String getTime() {
        String time = "" + (double) (end - start) / 1000;
        return time;
    }

    public int getAvg() {
        return avg;
    }

    public boolean getStatus() {
        return gameStatus;
    }

    public void make(int num) {
        if (num < words.size()) {
            for (int i = 0; i < num; i++) {
                temp += " " + words.get(i);
            }
        }
        else if (num > words.size()) {
            for (int i = 0; i < words.size(); i++) {
                temp += " " + words.get(i);
            }
        }
    }

    public void sort(){
        // YOUR CODE HERE
        words = mergeSort(0, words.size() - 1);
    }
    // Merge Sort to sort words lexicographyically
    public ArrayList<String> mergeSort(int low, int high){
        // Base case
        if(low == high){
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(words.get(low));
            return temp;
        }
        int med = (low + high) / 2;
        // Recursive Step
        ArrayList<String> tempOne = new ArrayList<String>();
        tempOne = mergeSort(low, med);
        ArrayList<String> tempTwo = new ArrayList<String>();
        tempTwo = mergeSort(med + 1, high);
        return merge(tempOne, tempTwo);

    }
    // Returns a sorted ArrayList
    public ArrayList<String> merge(ArrayList<String> temp, ArrayList<String> tempTwo){
        ArrayList<String> merged = new ArrayList<String>();
        int a = 0;
        int b = 0;
        while(a < temp.size() && b < tempTwo.size()) {
            if (temp.get(a).compareTo(tempTwo.get(b)) < 0) {
                merged.add(temp.get(a));
                a++;
            } else {
                merged.add(tempTwo.get(b));
                b++;
            }
        }
        while(a < temp.size()){
            merged.add(temp.get(a++));
        }
        while(b < tempTwo.size()){
            merged.add(tempTwo.get(b++));
        }
        return merged;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Make sure you're prepared! The timer starts as soon as the window opens!");
        System.out.println("How many words max would you like? ");
        s = new Scanner(System.in);
        int high = s.nextInt();
        String temp = s.nextLine();
        String letters;
        do {
            System.out.print("Enter your word you would like to base your experience off: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));
        NitroType.loadDictionary();
        NitroType n = new NitroType(letters);
        n.generate();
        n.sort();
        n.checkWords();
        n.removeDuplicates();
        n.make(high);
        int a = n.temp.length();
        int b = 1000 / a;
        n.avg = b;
        n.game();
    }
}