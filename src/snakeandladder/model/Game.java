package snakeandladder.model;


import snakeandladder.exception.InvalidPositionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private Board board;
    private List<Snake> snakes;
    private List<Ladder> ladders;
    private Player winner;
    List<String> players;

    public Game(int boardSize, List<String> players, List<Snake> snakes, List<Ladder> ladders) throws InvalidPositionException {
        this.snakes = snakes;
        this.ladders = ladders;
        initializeBoard(boardSize);
        initializePlayers(players);
    }

    public static void main(String[] args) {
        try {
            List<Snake> snakeList = Arrays.asList(new Snake(12, 6), new Snake(23, 18));
            List<Ladder> ladderList = Arrays.asList(new Ladder(6, 10), new Ladder(16, 24));
            Game game = new Game(24, Arrays.asList("Manik", "Garg"), snakeList, ladderList);
            game.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initializeBoard(int boardSize) throws InvalidPositionException {
        this.board = new Board(boardSize, snakes, ladders);
    }

    void initializePlayers(List<String> players) {
        List<Player> playerPositions = new ArrayList<>();
        for (String playerName : players) {
            playerPositions.add(new Player(playerName));
        }
        this.board.setPlayerPositions(playerPositions);
    }

    Player checkForWin() {
        return this.winner;
    }

    void play() {
        while (true) {
            for (int i = 0; i < board.getPlayerPositions().size(); i++) {
                Player player = board.getPlayerPositions().get(i);
                int roll = player.rollDice();
                System.out.println("Player :: " + player.getName() + " has got " + roll);
                board.movePlayer(player, roll);
                System.out.println("Player :: " + player.getName() + " has been moved to " + player.getPosition());
                if (board.checkForWinner(player)) {
                    System.out.println("Winner of the game :: " + player.getName());
                    this.winner = player;
                    return;
                }
            }
        }
    }
}
