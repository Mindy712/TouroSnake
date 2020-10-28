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

        open.add(start);

        if (food == null) {
            return;
            }

        runAStar(snake, open, closed, food, start);
    }

    private void runAStar(Snake snake, List<Node> open, List<Node> closed, Food food, Node start) {
        Node currNode;
        while (!open.isEmpty()) {

            currNode = getLowestCostNode(open, closed);

            currNode = moveToFood(snake, food, start, currNode);
            if (currNode == null) {
                break;
            }

            generateChildren(snake, open, closed, food, currNode);
        }
    }

    private Node getLowestCostNode(List<Node> open, List<Node> closed) {
        Node currNode;
        currNode = open.get(0);

        for (Node node : open) {
            if (node.getCost() < currNode.getCost()) {
                currNode = node;
            }
        }

        open.remove(currNode);
        closed.add(currNode);
        return currNode;
    }

    private Node moveToFood(Snake snake, Food food, Node start, Node currNode) {
        if (currNode.equals(food)) {
            while (!currNode.getParent().equals(start)) {
                currNode = currNode.getParent();
            }

            Direction direction = start.directionTo(currNode);
            snake.turnTo(direction);
            return null;
        }
        return currNode;
    }

    private void generateChildren(Snake snake, List<Node> open, List<Node> closed, Food food, Node currNode) {
        Direction[] directions = Direction.values();
        for (Direction dir : directions) {
            Node neighbor = new Node(currNode.moveTo(dir), currNode, food);
            if (snake.contains(neighbor) ||
                    !neighbor.inBounds() ||
                    closed.contains(neighbor)) {
                continue;
            }
            else if (open.contains(neighbor)){
                int index = open.indexOf(neighbor);
                Node prev = open.get(index);
                if (neighbor.getCost() < prev.getCost()) {
                    open.set(index, neighbor);
                }
            }
            else {
                open.add(neighbor);
            }
        }
    }
}
