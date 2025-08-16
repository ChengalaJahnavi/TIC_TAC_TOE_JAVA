import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 750;

    JFrame frame = new JFrame("Tic Tac Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel(); // Panel for restart & scores

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    int playerXScore = 0;
    int playerOScore = 0;
    JLabel scoreLabel = new JLabel();

    TicTacToe() {
        // Frame settings
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Text label settings
        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic Tac Toe");
        textLabel.setOpaque(true);

        // Top panel
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Board panel
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.DARK_GRAY);
        frame.add(boardPanel, BorderLayout.CENTER);

        // Create buttons for board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                // Button click action
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;

                        JButton clickedTile = (JButton) e.getSource();
                        if (clickedTile.getText().isEmpty()) {
                            clickedTile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + " 's turn.");
                            }
                        }
                    }
                });
            }
        }

        // Control panel (Restart button + score)
        controlPanel.setLayout(new GridLayout(1, 2));

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.addActionListener(e -> restartGame());

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        updateScoreLabel();

        controlPanel.add(restartButton);
        controlPanel.add(scoreLabel);

        frame.add(controlPanel, BorderLayout.SOUTH);
    }

    // Check for a winner
    void checkWinner() {
        // Check rows
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().isEmpty()) continue;
            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                updateScore();
                gameOver = true;
                return;
            }
        }

        // Check columns
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().isEmpty()) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                updateScore();
                gameOver = true;
                return;
            }
        }

        // Main diagonal
        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            updateScore();
            gameOver = true;
            return;
        }

        // Anti-diagonal
        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            updateScore();
            gameOver = true;
            return;
        }

        // Tie
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            textLabel.setText("It's a Tie!");
            gameOver = true;
        }
    }

    // Set winning tiles
    void setWinner(JButton tile) {
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.WHITE);
        textLabel.setText(currentPlayer + " wins!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.ORANGE);
        tile.setBackground(Color.GRAY);
    }

    // Restart game
    void restartGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.DARK_GRAY);
                board[r][c].setForeground(Color.WHITE);
            }
        }
        turns = 0;
        gameOver = false;
        currentPlayer = playerX;
        textLabel.setText("Tic Tac Toe");
    }

    // Update scores
    void updateScore() {
        if (currentPlayer.equals(playerX)) {
            playerXScore++;
        } else {
            playerOScore++;
        }
        updateScoreLabel();
    }

    void updateScoreLabel() {
        scoreLabel.setText("X: " + playerXScore + " | O: " + playerOScore);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
