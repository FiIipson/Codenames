package sample.Controllers;

import java.util.Random;

public class GlobalVar {
    // SETTINGS
    static public int difficulty = 1;
    static public int timeLimit = 90;
    static public boolean hints = false;
    static public int seconds = 3;
    static public int boardSize = 25;
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
    public static String hintString = "";
    public static int hintNumber = 0;
    static Random r = new Random();
    public static boolean red = r.nextBoolean();
    public static boolean operative = false;
    public static int redLeft = 8 + (red ? 1 : 0);
    public static int blueLeft = 8 + (red ? 0 : 1);
    public static int redTotal = redLeft;
    public static int blueTotal = blueLeft;
    // HINTS AND CURRENT TEAM

    // ALL WORDS
    enum WordType {
        BLUE, RED, NEUTRAL, BOMB, GUESSED
    }

    public static class Word {
        String text;
        int difficulty;
        WordType type;
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

    public static Word[] allWords = new Word[] {
            new Word("DENMARK", 0),
            new Word("EUROPE", 0),
            new Word("GAME", 0),
            new Word("QUEEN", 0),
            new Word("LONDON", 0),
            new Word("BRIDGE", 0),
            new Word("SCIENTIST", 0),
            new Word("SKY", 0),
            new Word("NIGHT", 0),
            new Word("FENCE", 0),
            new Word("VEHICLE", 0),
            new Word("POLICE", 0),
            new Word("MIDNIGHT", 0),
            new Word("SHARK", 0),
            new Word("STADIUM", 0),
            new Word("PLATE", 0),
            new Word("AMBULANCE", 1),
            new Word("COURT", 1),
            new Word("WOODPECKER", 1),
            new Word("EGYPT", 1),
            new Word("SEAL", 1),
            new Word("GIANT", 1),
            new Word("GREECE", 1),
            new Word("THUMB", 1),
            new Word("MICROSCOPE", 1),
            new Word("MODEL", 1),
            new Word("DEN", 1),
            new Word("TAIL", 1),
            new Word("OLYMPUS", 1),
            new Word("RING", 1),
            new Word("FRAME", 1),
            new Word("POLE", 1),
            new Word("SHAKESPEARE", 1),
            new Word("TIME", 2),
            new Word("BOWL", 2),
            new Word("POUND", 2),
            new Word("DUTCHMAN", 2),
            new Word("HUMOUR", 2),
            new Word("HOOD", 2),
            new Word("BRANCH", 2),
            new Word("CIRCLE", 2),
            new Word("DOT", 2),
            new Word("MAX", 2),
            new Word("DIVER", 2),
            new Word("BEDROCK", 2),
            new Word("PIPE", 2),
            new Word("CHAIR", 2),
            new Word("DEATH", 2),
            new Word("DRONE", 2),
            new Word("INK", 2)
    };

    public static Word[] word = new Word[boardSize];

    public static void setWords(int difficulty) {
        boolean[] used = new boolean[allWords.length];
        int written = 0;
        Random rand = new Random();
        //generates the words
        while (written < boardSize) {
            int r = rand.nextInt(allWords.length);
            if (!used[r]) {
                if (difficulty - allWords[r].difficulty == 2 || difficulty - allWords[r].difficulty == -2) continue;
                word[written++] = allWords[r];
                used[r] = true;
            }
        }
        //generates types of the words
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

    // ALL WORDS
}
