package touro.snake.strategy.astar.gottlieb;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;
import touro.snake.strategy.astar.Node;

import java.util.ArrayList;
import java.util.List;

public class AStarStrategy implements SnakeStrategy {
    List<Node> open = new ArrayList<>();
    List<Node> closed = new ArrayList<>();
    List<Square> path = new ArrayList<>();
    List<Square> searchSpace = new ArrayList();

    @Override
    public void turnSnake(Snake snake, Garden garden) {
        open.clear();
        closed.clear();
        path.clear();
        searchSpace.clear();

        Food food = garden.getFood();
        Node start = new Node(snake.getHead());

        open.add(start);

        if (food == null) {
            return;
            }

        runAStar(snake, food, start);
    }

    @Override
    public List<Square> getPath() {
        return path;
    }

    @Override
    public List<Square> getSearchSpace() {
        return searchSpace;
    }

    private void runAStar(Snake snake, Food food, Node start) {
        Node currNode;
        while (!open.isEmpty()) {

            currNode = getLowestCostNode();

            currNode = moveToFood(snake, food, start, currNode);
            if (currNode == null) {
                break;
            }

            generateChildren(snake, food, currNode);
        }
    }

    private Node getLowestCostNode() {
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
                path.add(new Square(currNode.getX(), currNode.getY()));
            }

            Direction direction = start.directionTo(currNode);
            snake.turnTo(direction);
            return null;
        }
        return currNode;
    }

    private void generateChildren(Snake snake, Food food, Node currNode) {
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
                searchSpace.add(neighbor);
            }
        }
    }
}
