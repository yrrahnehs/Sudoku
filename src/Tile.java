import java.awt.*;
import java.awt.geom.Rectangle2D;


public class Tile {
    private double width, height;
    private double x, y;
    private int posx, posy;
    private int number;
    private boolean locked;

    public Tile(double x, double y) {
        this.x = x;
        this.y = y;
        this.posx = this.posy = 0;
        this.width = this.height = 60;
        this.number = 0;
        this.locked = false;
    }

    public void drawTile(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D tile = new Rectangle2D.Double(x, y, width, height);
        g2.draw(tile);
        Font font = new Font("arial", Font.BOLD, 30);
        if (locked) {
            g2.setColor(Color.blue);
        } else {
            g2.setColor(Color.BLACK);
        }
        g.setFont(font);
        if (number != 0) {
            g.drawString(String.valueOf(number), (int) (this.x + this.width / 2 - 8), (int) (this.y + this.height / 2) + 9);
        }
        g2.setColor(Color.black);

    }

    public double getHEIGHT() {
        return height;
    }

    public double getWIDTH() {
        return width;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    // sets the x and y coordinates of the tile
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosXY(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getLocked() {
        return this.locked;
    }
}
