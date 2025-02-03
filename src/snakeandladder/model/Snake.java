package snakeandladder.model;

import snakeandladder.exception.InvalidSnakeException;

import java.util.Objects;

public class Snake {
    private Integer head;
    private Integer tail;

    public Snake(Integer head, Integer tail) throws InvalidSnakeException {
        this.head = head;
        this.tail = tail;

        if(this.tail > this.head)
            throw new InvalidSnakeException("Invalid Snake configuration");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return head == snake.head;
    }

    @Override
    public int hashCode() {
        return Objects.hash(head);
    }

    public Integer getHead() {
        return head;
    }

    public Integer getTail() {
        return tail;
    }

}
