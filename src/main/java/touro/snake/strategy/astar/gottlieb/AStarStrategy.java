package touro.snake.strategy.astar.gottlieb;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;
import touro.snake.strategy.astar.Node;

import java.util.ArrayList;
import java.util.List;

public class AStarStrategy implements SnakeStrategy {
    @Override
    public void turnSnake(Snake snake, Garden garden) {
        List<Node> open = new ArrayList<>();
        List<Node> closed = new ArrayList<>();

        Food food = garden.getFood();
        Node start = new Node(snake.getHead());

        Node currNode;
        open.add(start);

        while (!open.isEmpty()) {

            if (food == null) {
                break;
            }

             currNode = open.get(0);

            for (Node node : open) {
                if (node.getCost() < currNode.getCost()) {
                    currNode = node;
                }
            }

            open.remove(currNode);
            closed.add(currNode);

            if (currNode.getX() == food.getX() && currNode.getY() == food.getY()) {
                while (currNode.getParent() != start) {
                    currNode = currNode.getParent();
                }

                Direction direction = start.directionTo(currNode);
                snake.turnTo(direction);
                continue;
            }

            Direction[] directions = Direction.values();
            for (Direction dir : directions) {
                Square neighbor = currNode.moveTo(dir);
                if (snake.contains(neighbor) ||
                        !neighbor.inBounds() ||
                        closed.contains(neighbor) ||
                        open.contains(neighbor)) {
                    continue;
                }
                else {
                    open.add(new Node(neighbor, currNode, food));
                }
            }
        }
    }
}
