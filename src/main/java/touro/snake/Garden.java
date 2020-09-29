package touro.snake;

import java.util.ArrayList;

/**
 * A model that contains the Snake and Food and is responsible for logic of moving the Snake,
 * seeing that food has been eaten and generating new food.
 */
public class Garden {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 40;

    private final Snake snake;
    private final FoodFactory foodFactory;
    private ArrayList<Food> food = new ArrayList<>();

    public Garden(Snake snake, FoodFactory foodFactory) {
        this.snake = snake;
        this.foodFactory = foodFactory;
    }

    public Snake getSnake() {
        return snake;
    }

    public ArrayList<Food> getFood() {
        return food;
    }

    /**
     * Moves the snake, checks to see if food has been eaten and creates food if necessary
     *
     * @return true if the snake is still alive, otherwise false.
     */
    public boolean advance() {
        if (moveSnake()) {
            createFoodIfNecessary();
            return true;
        }
        return false;
    }

    /**
     * Moves the Snake, eats the Food or collides with the wall (edges of the Garden), or eats self.
     *
     * @return true if the Snake is still alive, otherwise false.
     */
    boolean moveSnake() {
        snake.move();

        //if collides with wall or self
        if (!snake.inBounds() || snake.eatsSelf()) {
            return false;
        }

        //if snake eats the food
        int eaten = food.indexOf(snake.getHead());
        if ( eaten != -1 ) {
            //add square to snake
            snake.grow();
            //remove food
            food.remove(eaten);
        }
        return true;
    }

    /**
     * Creates a Food if there isn't one, making sure it's not already on a Square occupied by the Snake.
     */
    void createFoodIfNecessary() {
        //if the array is not at 5, add until it is
        while (food.size() < 5) {
            Food piece = foodFactory.newInstance();
            //don't place food there if snake or other food is there
            if (!food.contains(piece) && !snake.contains(piece)) {
                food.add(piece);
            }
        }
    }

}
