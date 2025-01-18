package main;

import java.awt.*;
import java.util.Arrays;

public class UI {
    GamePanel gp;

    public UI(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == GameStateEnum.BUILDING) {
            g2.setFont(g2.getFont().deriveFont(16f));
            g2.setColor(Color.white);
            g2.drawString("Building", 32, 32);
        }
        drawCellCount(g2);
    }

    private void drawCellCount(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(16f));
        g2.setColor(Color.white);
        g2.drawString("Cells: " + Arrays.stream(gp.cells).flatMapToInt(Arrays::stream).filter(cell -> cell == 1).count(), 32, 32*3);
        g2.drawString("Gen: " + gp.generation, 32, (32*5));
    }
}