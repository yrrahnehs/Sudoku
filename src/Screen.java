import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Screen extends JPanel implements Runnable, MouseListener, MouseMotionListener {
    private final Frame frame;
    private int fps;
    private boolean paused;
    private final Board board;
    private final Tile highlighted_box;
    public int number;
    public final Tile[] SELECTION;
    private int scene;
    private Color color1, color2, color3, color4;

    public Screen(Frame frame) {
        Thread thread = new Thread(this);
        this.frame = frame;
        frame.addKeyListener(new KeyHandler(this));

        this.scene = 0;
        this.paused = false;

        board = new Board(frame);

        fps = 0;

        highlighted_box = new Tile(-60, -60);
        highlighted_box.setHeight(56);
        highlighted_box.setWidth(56);

        SELECTION = new Tile[9];
        for (int i = 1; i <= SELECTION.length; i++) {
            SELECTION[i - 1] = new Tile(frame.getWidth() - 220, 40 + (((i - 1) * 65)));
            SELECTION[i - 1].setNumber(i);
        }

        addMouseListener(this);
        addMouseMotionListener(this);
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);

        // title scene
        g.setColor(Color.black);
        if (scene == 0) {
            // Draws out title, start and quit buttons
            Font titlefont = new Font("arial", Font.BOLD, 70);
            g.setFont(titlefont);
            g.drawString("S U D O K U", frame.getWidth() / 4 + 25, 150);

            Font textfont = new Font("arial", Font.BOLD, 30);
            g.setFont(textfont);
            g.setColor(color1);
            Rectangle playbutton = new Rectangle(325, 250, 250, 50);
            g2d.draw(playbutton);
            g.drawString("PLAY", playbutton.x + 85, playbutton.y + 35);

            g.setColor(color2);
            Rectangle quitbutton = new Rectangle(325, 350, 250, 50);
            g2d.draw(quitbutton);
            g.drawString("QUIT", quitbutton.x + 87, quitbutton.y + 35);
        }
        if (scene == 1) {
            // Draws out title, start and quit buttons
            Font difficultyfont = new Font("arial", Font.BOLD, 70);
            g.setFont(difficultyfont);
            g.drawString("Select the Difficulty", frame.getWidth() / 8 + 25, 100);

            Font textfont = new Font("arial", Font.BOLD, 30);
            g.setFont(textfont);

            g.setColor(color1);
            Rectangle easybutton = new Rectangle(325, 200, 250, 50);
            g2d.draw(easybutton);
            g.drawString("EASY", easybutton.x + 85, easybutton.y + 35);

            g.setColor(color2);
            Rectangle mediumbutton = new Rectangle(325, 260, 250, 50);
            g2d.draw(mediumbutton);
            g.drawString("MEDIUM", mediumbutton.x + 63, mediumbutton.y + 35);

            g.setColor(color3);
            Rectangle hardbutton = new Rectangle(325, 320, 250, 50);
            g2d.draw(hardbutton);
            g.drawString("HARD", hardbutton.x + 85, hardbutton.y + 35);

            g.setColor(color4);
            Rectangle quitbutton = new Rectangle(325, 450, 250, 50);
            g2d.draw(quitbutton);
            g.drawString("QUIT", quitbutton.x + 87, quitbutton.y + 35);
        }

        // game scene
        if (scene == 2) {
            if (paused) {
                g.setColor(Color.black);
                Rectangle blackbar1 = new Rectangle(frame.getWidth() - 20, 10, 7, 25);
                Rectangle blackbar2 = new Rectangle(frame.getWidth() - 35, 10, 7, 25);
                g2d.fill(blackbar1);
                g2d.fill(blackbar2);

                Font difficultyfont = new Font("arial", Font.BOLD, 70);
                g.setFont(difficultyfont);
                g.drawString("PAUSED", frame.getWidth() / 3, 100);

                Font textfont = new Font("arial", Font.BOLD, 30);
                g.setFont(textfont);

                g.setColor(color1);
                Rectangle resumebutton = new Rectangle(325, 200, 250, 50);
                g2d.draw(resumebutton);
                g.drawString("RESUME", resumebutton.x + 62, resumebutton.y + 35);

                g.setColor(color2);
                Rectangle changedifficulty = new Rectangle(285, 270, 320, 50);
                g2d.draw(changedifficulty);
                g.drawString("CHANGE DIFFICULTY", changedifficulty.x + 3, changedifficulty.y + 35);

                g.setColor(color3);
                Rectangle quitbutton = new Rectangle(325, 400, 250, 50);
                g2d.draw(quitbutton);
                g.drawString("QUIT", quitbutton.x + 85, quitbutton.y + 35);


            } else {
                g.setColor(Color.black);
                Rectangle blackbar1 = new Rectangle(frame.getWidth() - 20, 10, 7, 25);
                Rectangle blackbar2 = new Rectangle(frame.getWidth() - 35, 10, 7, 25);
                g2d.fill(blackbar1);
                g2d.fill(blackbar2);

                g.setColor(Color.green);
                g2d.drawString("fps: " + fps + "", 10, 20);

                board.drawBoard(g);
                g.setColor(Color.green);
                g2d.setStroke(new java.awt.BasicStroke(3));
                highlighted_box.drawTile(g);
                for (Tile tile : SELECTION) {
                    if (number == tile.getNumber()) {
                        g2d.setColor(Color.RED);
                    } else {
                        g2d.setColor(Color.BLACK);
                    }
                    tile.drawTile(g);
                }
            }
        }

        // finished scene
        if (scene == 3) {
            // Draws out congratulations, play again and quit buttons
            Font difficultyfont = new Font("arial", Font.BOLD, 70);
            g.setFont(difficultyfont);
            g.drawString("CONGRATULATIONS!", frame.getWidth() / 9 - 10, 100);

            Font textfont = new Font("arial", Font.BOLD, 30);
            g.setFont(textfont);

            g.setColor(color1);
            Rectangle easybutton = new Rectangle(325, 350, 250, 50);
            g2d.draw(easybutton);
            g.drawString("PLAY AGAIN", easybutton.x + 35, easybutton.y + 35);

            g.setColor(color2);
            Rectangle quitbutton = new Rectangle(325, 450, 250, 50);
            g2d.draw(quitbutton);
            g.drawString("QUIT", quitbutton.x + 87, quitbutton.y + 35);
        }
    }

    public void run() {
        long lastFrame = System.currentTimeMillis();
        int frames = 0;

        boolean running = true;

        // while game is running
        while (running) {
            frames++;
            // displays fps ----------------------------------------
            if (System.currentTimeMillis() - 1000 >= lastFrame) {
                fps = frames;
                frames = 0;
                lastFrame = System.currentTimeMillis();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // -----------------------------------------------------
            if (scene == 2) {
                if (!paused) {
                    try {
                        checkBoard();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            repaint();
        }
        System.exit(1);

    }

    public void setNumber(int number) {
        this.number = number;
    }

    // checks if board is full, then check if it is the correct answer
    public void checkBoard() throws InterruptedException {
        int filled = 0;
        for (int i = 0; i < board.getTileArrayList().length; i++) {
            for (int j = 0; j < board.getTileArrayList().length; j++) {
                if (board.getTile(i, j).getNumber() != 0) {
                    filled++;
                }
            }
        }
        // if board is full, check if it is fully completed
        if (filled == 81) {
            board.verify();
            if (board.isCompleted() == 2) {
                Thread.sleep(2000);
                scene = 3;
            }
        } else {
            board.setCompleted(0);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (scene == 0) {
            // If mouse is clicked on play
            if (325 <= e.getX() && e.getX() <= 575 && 250 <= e.getY() && e.getY() <= 300) {
                scene = 1;
            }
            if (325 <= e.getX() && e.getX() <= 575 && 350 <= e.getY() && e.getY() <= 400) {
                System.exit(0);
            }
        } else if (scene == 1) {
            // If mouse is clicked on easy
            if (325 <= e.getX() && e.getX() <= 575 && 200 <= e.getY() && e.getY() <= 251) {
                board.clearBoard();
                board.makeBoard();
                board.setDifficulty(0);
                board.lockBoard();
                scene = 2;
            }
            // If mouse is clicked on medium
            if (325 <= e.getX() && e.getX() <= 575 && 260 <= e.getY() && e.getY() <= 310) {
                board.clearBoard();
                board.makeBoard();
                board.setDifficulty(1);
                board.lockBoard();
                scene = 2;
            }
            // If mouse is clicked on hard
            if (325 <= e.getX() && e.getX() <= 575 && 320 <= e.getY() && e.getY() <= 370) {
                board.clearBoard();
                board.makeBoard();
                board.setDifficulty(2);
                board.lockBoard();
                scene = 2;
            }
            // if mouse is clicked on quit
            if (325 <= e.getX() && e.getX() <= 575 && 450 <= e.getY() && e.getY() <= 500) {
                System.exit(0);
            }
        } else if (scene == 2) {
            if (!paused) {
                if (855 <= e.getX() && e.getX() <= 900 && 0 <= e.getY() && e.getY() <= 40) {
                    paused = true;
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (board.getTile(j, i).getX() < e.getX() && e.getX() < (board.getTile(j, i).getX() + board.getTile(j, i).getWIDTH())) {
                            if (board.getTile(j, i).getY() < e.getY() && e.getY() < (board.getTile(j, i).getY() + board.getTile(j, i).getHEIGHT())) {
                                if (!board.getTile(j, i).getLocked()) {
                                    board.getTile(j, i).setNumber(number);
                                }
                            }
                        }
                    }
                }

                for (Tile tile : SELECTION) {
                    if (tile.getX() < e.getX() && e.getX() < tile.getX() + tile.getWIDTH()) {
                        if (tile.getY() < e.getY() && e.getY() < tile.getY() + tile.getHEIGHT()) {
                            setNumber(tile.getNumber());
                        }
                    }
                }
            } else {
                // unpauses
                if (855 <= e.getX() && e.getX() <= 900 && 0 <= e.getY() && e.getY() <= 40) {
                    paused = false;
                }
                // unpauses
                else if (325 <= e.getX() && e.getX() <= 575 && 200 <= e.getY() && e.getY() <= 250) {
                    paused = false;
                }
                // changes the difficulty
                else if (285 <= e.getX() && e.getX() <= 605 && 270 <= e.getY() && e.getY() <= 325) {
                    scene = 1;
                    paused = false;
                }
                // quits
                else if (325 <= e.getX() && e.getX() <= 575 && 400 <= e.getY() && e.getY() <= 450) {
                    System.exit(0);
                }
            }
        } else if (scene == 3) {
            // If mouse is clicked on play again, go to difficulty screen
            if (325 <= e.getX() && e.getX() <= 575 && 350 <= e.getY() && e.getY() <= 400) {
                scene = 1;
            }
            // click on quit button
            if (325 <= e.getX() && e.getX() <= 575 && 450 <= e.getY() && e.getY() <= 500) {
                System.exit(0);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (scene == 0) {
            if (325 <= e.getX() && e.getX() <= 575 && 250 <= e.getY() && e.getY() <= 300) {
                color1 = new Color(10, 225, 10);
            } else if (325 <= e.getX() && e.getX() <= 575 && 350 <= e.getY() && e.getY() <= 400) {
                color2 = new Color(10, 225, 10);
            } else {
                color1 = Color.black;
                color2 = Color.black;
            }
        } else if (scene == 1) {
            color1 = Color.black;
            color2 = Color.black;
            color3 = Color.black;
            color4 = Color.black;

            if (325 <= e.getX() && e.getX() <= 575 && 200 <= e.getY() && e.getY() <= 251) {
                color1 = new Color(10, 225, 10);
            } else if (325 <= e.getX() && e.getX() <= 575 && 260 <= e.getY() && e.getY() <= 310) {
                color2 = new Color(10, 225, 10);
            } else if (325 <= e.getX() && e.getX() <= 575 && 320 <= e.getY() && e.getY() <= 370) {
                color3 = new Color(10, 225, 10);
            } else if (325 <= e.getX() && e.getX() <= 575 && 450 <= e.getY() && e.getY() <= 500) {
                color4 = new Color(10, 225, 10);
            } else {
                color1 = Color.black;
                color2 = Color.black;
                color3 = Color.black;
                color4 = Color.black;
            }
        } else if (scene == 2) {
            if (!paused) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (board.getTile(j, i).getX() < e.getX() && e.getX() < (board.getTile(j, i).getX() + board.getTile(j, i).getWIDTH())) {
                            if (board.getTile(j, i).getY() < e.getY() && e.getY() < (board.getTile(j, i).getY() + board.getTile(j, i).getHEIGHT())) {
                                highlighted_box.setXY(board.getTile(j, i).getX() + 2, board.getTile(j, i).getY() + 2);
                            }
                        }
                    }
                }
            } else {
                color1 = Color.black;
                color2 = Color.black;
                color3 = Color.black;
                // unpauses
                if (325 <= e.getX() && e.getX() <= 575 && 200 <= e.getY() && e.getY() <= 250) {
                    color1 = new Color(10, 225, 10);
                }
                // changes the difficulty
                else if (285 <= e.getX() && e.getX() <= 605 && 270 <= e.getY() && e.getY() <= 325) {
                    color2 = new Color(10, 225, 10);
                }
                // quits
                else if (325 <= e.getX() && e.getX() <= 575 && 400 <= e.getY() && e.getY() <= 450) {
                    color3 = new Color(10, 225, 10);
                } else {
                    color1 = Color.black;
                    color2 = Color.black;
                    color3 = Color.black;
                }
            }
        }
        if (scene == 3) {
            if (325 <= e.getX() && e.getX() <= 575 && 350 <= e.getY() && e.getY() <= 400) {
                color1 = new Color(10, 225, 10);
            } else if (325 <= e.getX() && e.getX() <= 575 && 450 <= e.getY() && e.getY() <= 500) {
                color2 = new Color(10, 225, 10);
            } else {
                color1 = Color.black;
                color2 = Color.black;
            }
        }
        repaint();
    }
}
