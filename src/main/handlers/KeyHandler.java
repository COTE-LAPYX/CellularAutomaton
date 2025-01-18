package main.handlers;

import main.GamePanel;
import main.GameStateEnum;
import main.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed, upPressed, downPressed = false;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            switch (gp.gameState) {
                case PLAY -> gp.gameState = GameStateEnum.BUILDING;
                case BUILDING -> gp.gameState = GameStateEnum.PLAY;
            }
        }

        if (code == KeyEvent.VK_R) {
            gp.cells = new int[gp.maxWorldRow][gp.maxWorldCol];
            gp.generation = 0;
        }
        if (code == KeyEvent.VK_Q) {
            for (int i = 0; i < gp.maxWorldRow; i++) {
                for (int j = 0; j < gp.maxWorldCol; j++) {
                    gp.cells[i][j] = gp.random.nextInt(2);
                }
            }
        }
        if (code == KeyEvent.VK_E) gp.nextStep();
        if (code == KeyEvent.VK_ESCAPE) System.exit(0);
        if (code == KeyEvent.VK_W) upPressed = true;
        if (code == KeyEvent.VK_S) downPressed = true;
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
        if (code == KeyEvent.VK_F11) {
            gp.isFullScreenOn = !gp.isFullScreenOn;
            gp.setFullScreen();
            Main.setFullScreen(gp.isFullScreenOn);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
