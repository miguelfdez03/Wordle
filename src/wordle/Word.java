package wordle;

// Clase para representar una palabra y su descripción
public class Word {
    String word;
    String description;

    public Word(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }
}