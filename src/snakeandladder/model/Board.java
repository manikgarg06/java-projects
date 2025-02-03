package snakeandladder.model;

import snakeandladder.exception.InvalidPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private int size;
    private List<Player> playerPositions;
    private List<Snake> snakePositions;
    private List<Ladder> ladderPositions;

    public Board(int size, List<Snake> snakePositions, List<Ladder> ladderPositions) throws InvalidPositionException {
        this.size = size;
        this.playerPositions = new ArrayList<>();
        this.snakePositions = snakePositions;
        this.ladderPositions = ladderPositions;

        if (isConfigInvalid())
            throw new InvalidPositionException("Invalid Snake or ladder position");
    }

    void movePlayer(Player p, int roll) {
        int newPosition = p.getPosition() + roll;
        if (newPosition > size)
            return;

        Optional<Ladder> ladderPosition = ladderPositions.stream().filter(pos -> pos.getStart() == newPosition).findFirst();
        if (ladderPosition.isPresent()) {
            System.out.println("Boosted by ladder");
            p.move(ladderPosition.get().getEnd() - p.getPosition());
            return;
        }
        Optional<Snake> snakePosition = snakePositions.stream().filter(pos -> pos.getHead() == newPosition).findFirst();
        if (snakePosition.isPresent()) {
            System.out.println("Beaten by snake");
            p.move(snakePosition.get().getTail() - p.getPosition());
            return;
        }
        p.move(roll);
    }

    public boolean isConfigInvalid() {
        return isSnakeConfigInvalid() || isLadderConfigInvalid();
    }

    public boolean isSnakeConfigInvalid() {
        return snakePositions.stream().filter(pos -> !(pos.getHead() > 0 && pos.getHead() < size && pos.getTail() > 0 && pos.getTail() < size)).findFirst().isPresent();
    }

    public boolean isLadderConfigInvalid() {
        return ladderPositions.stream().filter(pos -> !(pos.getStart() > 0 && pos.getStart() < size && pos.getEnd() > 0 && pos.getEnd() <= size)).findFirst().isPresent();
    }

    boolean checkForWinner(Player p) {
        return p.getPosition() == size;
    }

    public List<Player> getPlayerPositions() {
        return playerPositions;
    }

    public void setPlayerPositions(List<Player> playerPositions) {
        this.playerPositions = playerPositions;
    }

}
