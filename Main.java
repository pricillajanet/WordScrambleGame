import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Main {
    static String scrambleWord(String word) {
        char[] letters;
        String scrambled;

        do {
            letters = word.toCharArray();

            for (int i = 0; i < letters.length; i++) {
                int randomIndex = (int) (Math.random() * letters.length);

                char temp = letters[i];
                letters[i] = letters[randomIndex];
                letters[randomIndex] = temp;
            }

            scrambled = new String(letters);

        } while (scrambled.equals(word));

        return scrambled;
    }
    public static void main(String[] args) {


        String[] words = {"APPLE", "MANGO"};
        String[] hints = {"A fruit that keeps the doctor away.", "A tropical fruit, often yellow when ripe."};
        int[] index = {0};

        int[] score = {0};
        int[] seconds = {0};

        JFrame frame = new JFrame("Word Scramble Game");

        JLabel title = new JLabel("Word Scramble Game", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel scrambledWord = new JLabel(scrambleWord(words[index[0]]), JLabel.CENTER);
        scrambledWord.setFont(new Font("Arial", Font.BOLD, 36));

        JLabel scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        JLabel timerLabel = new JLabel("Time: 0 sec", JLabel.CENTER);
        Timer timer = new Timer(1000, e -> {
            seconds[0]++;
            timerLabel.setText("Time: " + seconds[0] + " sec");
        });

        JTextField answerField = new JTextField(15);
        JButton checkButton = new JButton("Check Answer");

        JButton hintButton = new JButton("Hint");
        JButton restartButton = new JButton("Restart");


        JPanel inputPanel = new JPanel();
        inputPanel.add(answerField);
        inputPanel.add(checkButton);
        inputPanel.add(hintButton);
        inputPanel.add(restartButton);

        checkButton.addActionListener(e -> {

            String userAnswer = answerField.getText();
            if (userAnswer.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an answer!");
                return;
            }
            if (seconds[0] == 0) {
                timer.start();
            }

            if (userAnswer.equalsIgnoreCase(words[index[0]])) {

                JOptionPane.showMessageDialog(frame, "Correct!");
                score[0] += 10;
                scoreLabel.setText("Score: " + score[0]);
                answerField.setText("");

                index[0]++;

                if (index[0] < words.length) {
                    scrambledWord.setText(scrambleWord(words[index[0]]));
                } else {
                    timer.stop();
                    scrambledWord.setText("");
                    scoreLabel.setText("");
                    timerLabel.setText("");
                    answerField.setEnabled(false);
                    checkButton.setEnabled(false);
                    hintButton.setEnabled(false);
                    JOptionPane.showMessageDialog(frame,
                            "Game Completed!\nFinal Score: " + score[0] +
                                    "\nTime Taken: " + seconds[0] + " seconds");
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Wrong answer!");
            }

        });
        hintButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, hints[index[0]]);
        });
        restartButton.addActionListener(e -> {
            timer.stop();

            score[0] = 0;
            index[0] = 0;
            seconds[0] = 0;

            scoreLabel.setText("Score: 0");
            timerLabel.setText("Time: 0 sec");
            scrambledWord.setText(scrambleWord(words[index[0]]));
            answerField.setText("");

            answerField.setEnabled(true);
            checkButton.setEnabled(true);
            hintButton.setEnabled(true);

        });

        JPanel statusPanel = new JPanel();
        statusPanel.add(scoreLabel);
        statusPanel.add(timerLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(statusPanel, BorderLayout.SOUTH);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrambledWord, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}