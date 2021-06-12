package sample.Controllers;

import java.io.Serializable;
import java.util.ArrayList;

public class BOARD implements Serializable {
    GlobalVar.Word[] words;
    GlobalVar.Hint hint;

    public BOARD(GlobalVar.Word[] words, GlobalVar.Hint hint) {
        this.words = words;
        this.hint = hint;
    }
}
