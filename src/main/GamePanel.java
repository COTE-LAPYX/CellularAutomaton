package main;

import main.handlers.KeyHandler;
import main.handlers.MouseHandler;
import main.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    //region Variables
    private final int originalTileSize = 8; // 16x16
    public final int scale = 2; // 2
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 120; //20 /120
    public final int maxScreenRow = 72; //12 /72
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public int FPS = 60;
    public double drawInterval;
    public int frames;
    public long generation = 0;
    long timer = 0;
    int drawCount = 0;
    double delta = 0;
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    public final int maxWorldCol = 1000;
    public final int maxWorldRow = 1000;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean isFullScreenOn = false;
    Thread gameThread;
    public int[][] cells = new int[maxWorldRow][maxWorldCol];
    public GameStateEnum gameState = GameStateEnum.BUILDING;
    public KeyHandler keyHandler = new KeyHandler(this);
    public MouseHandler mouseHandler = new MouseHandler(this);
    public Camera camera = new Camera(this); // наш игрок
    TileManager tileManager = new TileManager(this);
    UI ui = new UI(this);
    public float fullScreenOffsetFactor;
    private final int updateBaseCounter = 5;
    private int updateCounter = updateBaseCounter;
    public Random random = new Random();
    //endregion

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
    }

    public void setUpGame() {
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        gameState = GameStateEnum.BUILDING;
        tileManager.loadMap();
    }

    public void startGameThread() {
        try {
            gameThread = new Thread(this);
            gameThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        drawInterval = 1000000000 / FPS;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if (timer > 1000000000) {
                frames = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        if (gameState == GameStateEnum.PLAY){
            if (updateCounter == 0){
                nextStep();
            }
        }
        camera.update();

        if (updateCounter == 0){
            updateCounter = updateBaseCounter;
        } else updateCounter--;
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        if (g != null) {
            g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
            g.dispose();
        }
    }

    public void drawToTempScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeight);
        tileManager.draw(g2);

        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                int screenX = row*tileSize - camera.worldX + camera.screenX;
                int screenY = col*tileSize - camera.worldY + camera.screenY;

                if (cells[row][col] == 1) {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(screenX, screenY, tileSize, tileSize);
                }
            }
        }
        ui.draw(g2);
    }

    public void setFullScreen() {
        if (isFullScreenOn) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            screenWidth2 = (int) width;
            screenHeight2 = (int) height;
            /*//offset factor to be used by mouse listener or mouse motion listener if you are using cursor in your game. Multiply your e.getX()e.getY() by this.*/
            fullScreenOffsetFactor = (float) screenWidth / (float) screenWidth2;
        } else {
            screenWidth2 = screenWidth;
            screenHeight2 = screenHeight;
            Main.window.setSize(screenWidth2, screenHeight2);
            Main.window.setLocationRelativeTo(null);
        }
    }

    public void nextStep() {
        generation++;
        int[][] nextGen = new int[maxWorldRow][maxWorldCol];

        for (int col = 0; col < maxWorldCol; col++) {
            for (int row = 0; row < maxWorldRow; row++) {
                int liveNeighbors = countLiveNeighbors(row, col);

                if (cells[row][col] == 1) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        nextGen[row][col] = 0;
                    } else {
                        nextGen[row][col] = 1;
                    }
                } else {
                    if (liveNeighbors == 3) {
                        nextGen[row][col] = 1;
                    }
                }
            }
        }
        cells = nextGen;
    }

    private int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;
        int[] directions = {-1, 0, 1};

        for (int dCol : directions) {
            for (int dRow : directions) {
                if (dRow == 0 && dCol == 0) {
                    continue;
                }
                int newRow = row + dRow;
                int newCol = col + dCol;

                if (newRow >= 0 && newRow < maxWorldRow && newCol >= 0 && newCol < maxWorldCol) {
                    liveNeighbors += cells[newRow][newCol];
                }
            }
        }

        return liveNeighbors;
    }
}
