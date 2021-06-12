package sample.Controllers;

import java.util.ArrayList;

public class BOARD {
    GlobalVar.Word[] words;
    GlobalVar.Hint hint;

    public BOARD(GlobalVar.Word[] words, GlobalVar.Hint hint) {
        this.words = words;
        this.hint = hint;
    }
}
