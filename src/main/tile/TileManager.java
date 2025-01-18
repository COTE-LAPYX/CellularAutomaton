package main.tile;

import main.GamePanel;

import java.awt.*;

public class TileManager {
    public int[][] mapTileNum;
    GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    }

    public void loadMap() {
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.camera.worldX + gp.camera.screenX;
            int screenY = worldY - gp.camera.worldY + gp.camera.screenY;

            if (worldX + gp.tileSize > gp.camera.worldX - gp.camera.screenX &&
                    worldX - gp.tileSize < gp.camera.worldX + gp.camera.screenX &&
                    worldY + gp.tileSize > gp.camera.worldY - gp.camera.screenY &&
                    worldY - gp.tileSize < gp.camera.worldY + gp.camera.screenY) {
                g2.setColor(Color.GREEN);
                g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }


            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }


    }
}
