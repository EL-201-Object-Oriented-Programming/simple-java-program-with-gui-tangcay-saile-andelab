import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RockPaperScissors extends JFrame {
    private ImageLabel rockLabel, paperLabel, scissorsLabel;
    private JLabel headingLabel, resultLabel, scoreLabel, userChoiceLabel, computerChoiceLabel, userLabel, computerLabel;
    private int wins = 0, losses = 0, ties = 0;

    public RockPaperScissors() {
        setTitle("Rock-Paper-Scissors");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Add heading
        headingLabel = new JLabel("Rock Paper Scissors", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.WHITE);
        add(headingLabel, BorderLayout.NORTH);

        // Load and resize images
        ImageIcon rockIcon = resizeImageIcon("rock.png", 100, 100);
        ImageIcon paperIcon = resizeImageIcon("paper.png", 100, 100);
        ImageIcon scissorsIcon = resizeImageIcon("scissors.png", 100, 100);

        // Create labels with images and wide round borders
        rockLabel = new ImageLabel(rockIcon, "Rock", 4, 50, Color.WHITE);
        paperLabel = new ImageLabel(paperIcon, "Paper", 4, 50, Color.WHITE);
        scissorsLabel = new ImageLabel(scissorsIcon, "Scissors", 4, 50, Color.WHITE);

        rockLabel.addMouseListener(new ImageClickListener(rockLabel.getMove()));
        paperLabel.addMouseListener(new ImageClickListener(paperLabel.getMove()));
        scissorsLabel.addMouseListener(new ImageClickListener(scissorsLabel.getMove()));

        JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        choicePanel.setBackground(Color.BLACK);
        choicePanel.add(rockLabel);
        choicePanel.add(paperLabel);
        choicePanel.add(scissorsLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(choicePanel, BorderLayout.NORTH);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.WHITE);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setBackground(Color.BLACK);
        resultsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        userLabel = new JLabel("You", SwingConstants.CENTER);
        userLabel.setForeground(Color.WHITE);
        computerLabel = new JLabel("Computer", SwingConstants.CENTER);
        computerLabel.setForeground(Color.WHITE);

        // Initialize userChoiceLabel and computerChoiceLabel with round white borders
        userChoiceLabel = new ImageLabel(null, "", 4, 50, Color.WHITE);
        computerChoiceLabel = new ImageLabel(null, "", 4, 50, Color.WHITE);

        // Positioning "You" label above user choice image
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 80, 10, 60); // Adjust the right inset to add space
        gbc.anchor = GridBagConstraints.CENTER; // Align center
        resultsPanel.add(userLabel, gbc);

        // Positioning "Computer" label above computer choice image
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 80, 10, 0); // Adjust the left inset to add space
        gbc.anchor = GridBagConstraints.CENTER; // Align center
        resultsPanel.add(computerLabel, gbc);

        // Adding result label between images
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 115, 10, 0); // Adjust the bottom inset to move the label upward
        gbc.anchor = GridBagConstraints.CENTER; // Align center
        resultsPanel.add(resultLabel, gbc);

        // Positioning user choice image with space
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 60); // Adjust the right inset to add space
        resultsPanel.add(userChoiceLabel, gbc);

        // Positioning computer choice image with space
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 60, 0, 0); // Adjust the left inset to add space
        resultsPanel.add(computerChoiceLabel, gbc);

        centerPanel.add(resultsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Wins: 0, Losses: 0, Ties: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(Color.WHITE);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.BLACK);
        southPanel.add(scoreLabel, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset Score");
        resetButton.addActionListener(e -> resetScore());
        southPanel.add(resetButton, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
    }

    private ImageIcon resizeImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private void resetScore() {
        wins = 0;
        losses = 0;
        ties = 0;
        updateScoreLabel();
    }

    private class ImageClickListener extends MouseAdapter {
        private String move;

        public ImageClickListener(String move) {
            this.move = move;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            String computerMove = getComputerMove();
            String result = determineWinner(move, computerMove);

            resultLabel.setText(result);
            userChoiceLabel.setIcon(resizeImageIcon(move.toLowerCase() + ".png", 100, 100));
            computerChoiceLabel.setIcon(resizeImageIcon(computerMove.toLowerCase() + ".png", 100, 100));
            updateScore(result);
        }
    }

    private void updateScore(String result) {
        if (result.contains("win")) {
            wins++;
        } else if (result.contains("lose")) {
            losses++;
        } else if (result.contains("tie")) {
            ties++;
        }
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Wins: " + wins + ", Losses: " + losses + ", Ties: " + ties);
    }

    private String getComputerMove() {
        String[] moves = {"Rock", "Paper", "Scissors"};
        int index = (int) (Math.random() * moves.length);
        return moves[index];
    }

    private String determineWinner(String userMove, String computerMove) {
        if (userMove.equals(computerMove)) {
            return "It's a tie!";
        }
        switch (userMove) {
            case "Rock":
                return (computerMove.equals("Scissors")) ? "You win!" : "You lose!";
            case "Paper":
                return (computerMove.equals("Rock")) ? "You win!" : "You lose!";
            case "Scissors":
                return (computerMove.equals("Paper")) ? "You win!" : "You lose!";
        }
        return "Invalid move!";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RockPaperScissors frame = new RockPaperScissors();
            frame.setVisible(true);
        });
    }

    class ImageLabel extends JLabel {
        private String move;
        private int borderThickness;
        private int padding;
        private Color borderColor;

        public ImageLabel(ImageIcon icon, String move, int borderThickness, int padding, Color borderColor) {
            super(icon);
            this.move = move;
            this.borderThickness = borderThickness;
            this.padding = padding;
            this.borderColor = borderColor;
        }

        public String getMove() {
            return move;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderThickness));
            g2d.drawOval(padding / 2, padding / 2, getWidth() - padding - 1, getHeight() - padding - 1);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            size.width += padding;
            size.height += padding;
            return size;
        }
    }
}
