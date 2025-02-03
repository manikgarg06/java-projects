package snakeandladder.model;

import snakeandladder.exception.InvalidLadderException;
import snakeandladder.exception.InvalidSnakeException;

import java.util.Objects;

public class Ladder {
    private int start;
    private int end;

    public Ladder(int start, int end) throws InvalidLadderException {
        this.start = start;
        this.end = end;
        if (this.start > this.end)
            throw new InvalidLadderException("Invalid Ladder configuration");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ladder ladder = (Ladder) o;
        return start == ladder.start;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
