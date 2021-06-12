package sample.Controllers;

import java.util.ArrayList;

public class BOARD {
    ArrayList<GlobalVar.Word> words;
    GlobalVar.Hint hint;

    public BOARD(ArrayList<GlobalVar.Word> words, GlobalVar.Hint hint) {
        this.words = words;
        this.hint = hint;
    }
}
