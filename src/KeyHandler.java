import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    private final Screen screen;

    public KeyHandler(Screen screen) {
        this.screen = screen;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        screen.setNumber(key-48);
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}