package main;

public class Camera {
    public final int screenX;
    public final int screenY;
    public int worldX;
    public int worldY;
    public int speed = 32;
    GamePanel gp;

    public Camera(GamePanel gp) {
        this.gp = gp;

        screenX = gp.screenWidth/2 - (gp.tileSize / 2);
        screenY = gp.screenHeight/2 - (gp.tileSize / 2);
        worldX = gp.tileSize * 22;
        worldY = gp.tileSize * 21;
    }
    public void update(){
        if (gp.keyHandler.upPressed) {
            worldY -= speed;
        }
        if (gp.keyHandler.downPressed) {
            worldY += speed;
        }
        if (gp.keyHandler.leftPressed) {
            worldX -= speed;
        }
        if (gp.keyHandler.rightPressed) {
            worldX += speed;
        }
    }
}
