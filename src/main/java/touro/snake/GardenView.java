package touro.snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GardenView extends JComponent {

    private final Garden garden;
    public static final int CELL_SIZE = 10;

    public GardenView(Garden garden) {
        this.garden = garden;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrass(g);
        paintFood(g);
        paintSnake(g);
    }

    void paintGrass(Graphics g) {
        // Berger
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void paintSnake(Graphics g) {
        g.setColor(Color.RED);
        for (Square s : garden.getSnake().getSquares()) {
            g.fillRect(s.getX()*CELL_SIZE, s.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    void paintFood(Graphics g) {
        // Berger
        if (garden.getFood() != null) {
            ArrayList<Food> food = garden.getFood();
            g.setColor(Color.LIGHT_GRAY);

            for (Square piece : food) {
                int x = piece.getX() * CELL_SIZE;
                int y = piece.getY() * CELL_SIZE;
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
