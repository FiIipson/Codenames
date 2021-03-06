package sample.Controllers;

import javafx.stage.Stage;
import sample.Server.ClientSideConnection;
import sample.Server.StartServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GlobalVar {

    // MULTIPLAYER
    static public Board board;
    static public String IP;
    static public StartServer serverThread;
    static public boolean serverReady;
    static public String your_name;
    static public Thread joinThread;
    static public Board receivedBoard;
    static public ClientSideConnection csc;
    static public Stage mainStage;
    static public PlayerRole yourRole;
    static public String gameHistory = "";
    static public boolean isAHost = false;
    // MULTIPLAYER

    // RESULT SHOWN AT THE END GAME SCREEN
    static public Result result_;
    // RESULT SHOWN AT THE END GAME SCREEN

    // SETTINGS
    static public int difficulty = 1;
    static public int timeLimit = 90;
    static public int timeLimit2 = 90;
    static public boolean hints = false;
    static public int seconds = 3;
    static public int boardSize = 25;
    static public int DICTIONARY_SIZE = 466550;
    // SETTINGS

    public static int MAX_NAME_LENGTH = 25;
    public static String currentError = "Everything seems to be fine. This shouldn't have happened.";

    // NAMES
    public static String blueLeaderName = "";
    public static String blueOperativeName = "";
    public static String redLeaderName = "";
    public static String redOperativeName = "";
    // NAMES

    // HINTS AND CURRENT TEAM
    public static String hintString = "nigger";
    public static int hintNumber = 2;
    static Random r = new Random();
    public static boolean red = false;
    public static boolean operative = false;
    public static int redLeft = 8;
    public static int blueLeft = 9;
    public static int redTotal = redLeft;
    public static int blueTotal = blueLeft;
    // HINTS AND CURRENT TEAM

    // ALL WORDS
    enum WordType {
        BLUE, RED, NEUTRAL, BOMB, GUESSED_RED, GUESSED_BLUE, GUESSED_NEUTRAL, GUESSED_BOMB
    }

    public enum PlayerRole {
        BLUE_LEADER, RED_LEADER, BLUE_OPERATIVE, RED_OPERATIVE
    }

    // ALL POSSIBLE RESULTS
    enum Result {
        BLUE, RED, BLUE_BOMB, RED_BOMB
    }

    public static class Word implements Serializable {
        public String text;
        public int difficulty;
        public WordType type;
        Word(String text, int difficulty) {
            this.text = text;
            this.difficulty = difficulty;
            this.type = WordType.NEUTRAL;
        }
        public String getText() {
            return text;
        }
        public WordType getType() {
            return type;
        }
        public void setType(WordType type) {
            this.type = type;
        }

    }

    public static class Hint implements Serializable {
        String text;
        boolean first_hint;
        int number;

        public Hint(String text, boolean first_hint, int number) {
            this.text = text;
            this.first_hint = first_hint;
            this.number = number;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isFirst_hint() {
            return first_hint;
        }

        public void setFirst_hint(boolean first_hint) {
            this.first_hint = first_hint;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public static ArrayList<Word> AllWords = new ArrayList<>();
    public static String[] Dictionary = new String[DICTIONARY_SIZE];

    public static ArrayList<Word> loadWords() throws FileNotFoundException {
        File file = new File("src/sample/Controllers/words.txt");
        Scanner sc = new Scanner(file);
        String s;
        String[] t;
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            t = s.split("\t");
            AllWords.add(new Word(t[0], Integer.parseInt(t[1])));
        }
        return AllWords;
    }

    public static void loadDictionary() throws FileNotFoundException {
        File file = new File("src/sample/Controllers/dictionary.txt");
        Scanner sc = new Scanner(file);
        int count = 0;
        while (sc.hasNextLine()) Dictionary[count++] = sc.nextLine();
    }

    public static Word[] word = new Word[boardSize];

    public static void setWords(int difficulty) {
        boolean[] used = new boolean[AllWords.size()];
        int written = 0;
        Random rand = new Random();
        //generates the words
        while (written < boardSize) {
            int r = rand.nextInt(AllWords.size());
            while (!used[r]) {
                if (difficulty - AllWords.get(r).difficulty == 2 || difficulty - AllWords.get(r).difficulty == -2) continue;
                word[written++] = AllWords.get(r);
                used[r] = true;
            }
        }
        //generates types of the words
        makeNeutral(word);
        setOneType(word, WordType.BOMB, 1);
        setOneType(word, WordType.RED, redTotal);
        setOneType(word, WordType.BLUE, blueTotal);
    }

    public static void setOneType(Word [] word, WordType type, int number) {
        Random rand = new Random();
        while (number > 0) {
            int r = rand.nextInt(boardSize);
            if (word[r].getType() != WordType.NEUTRAL) continue;
            word[r].setType(type);
            number--;
        }
    }

    public static void makeNeutral(Word [] word) {
        for (Word value : word) {
            value.setType(WordType.NEUTRAL);
        }
    }

    // ALL WORDS

    public static enum moveType {
        HINTED, PLAYED
    }
}
