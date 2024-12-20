package wordle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WordleGame {
    private Word[] words;
    private Word currentWord;
    private String chosenWord; // Añadida la declaración que faltaba
    private int[] charCounts;
    private boolean game;
    private String hint; // Add a hint field

    public WordleGame(Word[] words) {
        this.words = words;
        resetGame();
    }

    public void resetGame() {
        Random random = new Random();
        int index = random.nextInt(words.length);
        currentWord = words[index];
        chosenWord = currentWord.getWord();
        countCharacters(); // Ensure charCounts is updated
        updateHint(); // Update the hint
        game = true;
    }

    private void updateHint() {
        // Logic to update the hint based on the chosenWord
        hint = "New hint based on the chosen word: " + chosenWord; // Example logic
    }

    public String getHint() {
        return hint;
    }

    public String getChosenWord() {
        return chosenWord;
    }

    public boolean isGameActive() {
        return game;
    }

    public void endGame() {
        game = false;
    }

    public Word[] getWords() {
        return words;
    }

    public void setWords(Word[] words) {
        this.words = words;
    }

    private void countCharacters() {
        charCounts = new int[26];
        for (int i = 0; i < chosenWord.length(); i++) {
            charCounts[chosenWord.charAt(i) - 'a']++;
        }
    }

    public int[] getCharCounts() {
        return charCounts.clone();
    }

    public boolean checkWord(String wordTyped, JLabel[][] charLabels, int row, Color correctColor, Color presentColor, Color unusedColor) {
        int[] checkCounts = getCharCounts();
        for (int i = 0; i < wordTyped.length(); i++) {
            if (wordTyped.charAt(i) == chosenWord.charAt(i)) {
                charLabels[row][i].setBackground(correctColor);
                checkCounts[chosenWord.charAt(i) - 'a']--;
            } else if (chosenWord.contains(wordTyped.charAt(i) + "")) {
                if (checkCounts[wordTyped.charAt(i) - 'a'] > 0) {
                    charLabels[row][i].setBackground(presentColor);
                    checkCounts[wordTyped.charAt(i) - 'a']--;
                }
            } else {
                charLabels[row][i].setBackground(unusedColor);
            }
        }
        return wordTyped.equals(chosenWord);
    }

    public Word getCurrentWord() {
        return currentWord;
    }
}
