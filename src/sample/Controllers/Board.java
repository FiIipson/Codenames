package sample.Controllers;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    ArrayList<GlobalVar.Word> words;
    GlobalVar.Hint last_hint;
    int number_of_players = 2;
    GlobalVar.Player[] players;

    public Board(ArrayList<GlobalVar.Word> words, GlobalVar.Hint last_hint) {
        this.words = words;
        this.last_hint = last_hint;
    }

    public ArrayList<GlobalVar.Word> getWords() {
        return words;
    }

    public GlobalVar.Hint getLast_hint() {
        return last_hint;
    }

    public void setLast_hint(GlobalVar.Hint last_hint) {
        this.last_hint = last_hint;
    }

    public int getNumber_of_players() {
        return number_of_players;
    }

    public void setNumber_of_players(int number_of_players) {
        this.number_of_players = number_of_players;
    }

    public GlobalVar.Player[] getPlayers() {
        return players;
    }
}
