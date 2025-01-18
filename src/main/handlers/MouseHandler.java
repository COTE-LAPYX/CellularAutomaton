package main.handlers;

import main.GamePanel;
import main.GameStateEnum;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 2) return;
        if (e.getButton() == 1) {
            if (gp.gameState == GameStateEnum.BUILDING){
                gp.cells[((e.getX() + 16 + gp.camera.worldX - gp.screenWidth2 / 2) / gp.tileSize)][((e.getY() + 16 + gp.camera.worldY - gp.screenHeight2 / 2) / gp.tileSize)] = 1;
                }
        } else if (e.getButton() == 3) {
            if (gp.gameState == GameStateEnum.BUILDING) {
                gp.cells[((e.getX() + 16 + gp.camera.worldX - gp.screenWidth2 / 2) / gp.tileSize)][((e.getY() + 16 + gp.camera.worldY - gp.screenHeight2 / 2) / gp.tileSize)] = 0;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
