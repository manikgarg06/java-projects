package snakeandladder.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int position;
    private int moves;

    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.moves = 0;
    }

    int rollDice() {
        return (int) Math.ceil(Math.random() * 6);
    }

    void move(int spaces) {
        position += spaces;
        moves++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
}
