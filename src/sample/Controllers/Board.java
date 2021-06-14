package sample.Controllers;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    public GlobalVar.Word[] words;
    public GlobalVar.Hint hint;
    public GlobalVar.PlayerRole turn;
    public String hintingPlayer = "no one";
    public GlobalVar.PlayerRole hintingPlayerRole;
    public boolean endGame = false;
    public GlobalVar.Result gameResult;

    public Board(GlobalVar.Word[] words, GlobalVar.Hint hint, GlobalVar.PlayerRole turn) {
        this.words = words;
        this.hint = hint;
        this.turn = turn;
    }
}
