import javax.swing.*;


public class Frame extends JFrame {

    public static void main(String[] args) {
        new Frame();
    }

    public Frame() {
        new JFrame();

        this.setTitle("Simple Sudoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 700);
        this.setResizable(false);

        Screen screen = new Screen(this);
        this.add(screen);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
