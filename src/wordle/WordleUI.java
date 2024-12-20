package wordle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WordleUI extends JFrame implements KeyListener {
    private WordleGame game;
    private String wordTyped;
    private int rowCount;
    private JLabel[][] charLabels;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel infoPanel;
    private JLabel messageLabel;
    private JTextArea descriptionArea;
    private final Color CORRECT_COLOR = new Color(106, 170, 100);
    private final Color PRESENT_COLOR = new Color(201, 180, 88);
    private final Color UNUSED_COLOR = new Color(120, 124, 126);
    private final Color EMPTY_COLOR = new Color(211, 214, 218);
    private final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    private final Color TEXT_COLOR = new Color(255, 255, 255);
    private final Color INPUT_COLOR = new Color(50, 50, 50);

    public WordleUI(WordleGame game) {
        this.game = game;
        this.setTitle("Wordle");
        this.setLayout(new BorderLayout());

        wordTyped = "";
        updateWindowSize();
        charLabels = new JLabel[5][game.getChosenWord().length()];
        rowCount = 0;

        // Crear componentes de la interfaz
        createMenuBar();
        createMainPanel();
        prepareLabels(rowCount);

        this.pack();
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    // Crear la barra de menú
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Juego");

        JMenuItem newGame = new JMenuItem("Nuevo Juego");
        newGame.addActionListener(e -> resetGame());

        JMenuItem exit = new JMenuItem("Salir");
        exit.addActionListener(e -> System.exit(0));

        gameMenu.add(newGame);
        gameMenu.addSeparator();
        gameMenu.add(exit);
        menuBar.add(gameMenu);

        this.setJMenuBar(menuBar);
    }

    // Crear el panel principal
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);

        gamePanel = new JPanel(null);
        gamePanel.setPreferredSize(new Dimension(getWidth(), 400));
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(this);
        gamePanel.setBackground(BACKGROUND_COLOR);

        infoPanel = new JPanel(new BorderLayout(5, 5));
        infoPanel.setBackground(BACKGROUND_COLOR);
        messageLabel = new JLabel("¡Adivina la palabra!");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(TEXT_COLOR);

        descriptionArea = new JTextArea(3, 30);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(new Color(50, 50, 50));
        descriptionArea.setForeground(TEXT_COLOR);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Reemplazar el bucle for por una llamada directa
        updateDescription();

        infoPanel.add(messageLabel, BorderLayout.NORTH);
        infoPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    // Preparar las etiquetas para mostrar las letras
    public void prepareLabels(int row) {
        for (int i = 0; i < game.getChosenWord().length(); i++) {
            charLabels[row][i] = new JLabel("");
            charLabels[row][i].setFont(new Font("Arial", Font.BOLD, 24));
            charLabels[row][i].setOpaque(true);
            charLabels[row][i].setBorder(BorderFactory.createLineBorder(EMPTY_COLOR, 2));
            charLabels[row][i].setHorizontalAlignment(SwingConstants.CENTER);
            charLabels[row][i].setBackground(INPUT_COLOR); // Cambiar el color de fondo para visibilidad
            charLabels[row][i].setForeground(TEXT_COLOR);
            charLabels[row][i].setBounds((i * 60) + 10, (row * 70) + 10, 50, 50);
            gamePanel.add(charLabels[row][i]);
        }
    }

    // Ajustar el tamaño de la ventana según la longitud de la palabra
    private void updateWindowSize() {
        int width = (game.getChosenWord().length() * 60) + 40; // Ajustar el ancho de la ventana
        this.setSize(new Dimension(width, 600));
    }

    // Reiniciar el juego
    private void resetGame() {
        gamePanel.removeAll();
        game.resetGame();
        wordTyped = "";
        rowCount = 0;

        // Reinicializar el array de etiquetas
        charLabels = new JLabel[5][game.getChosenWord().length()];

        updateWindowSize(); // Ajustar el tamaño de la ventana
        messageLabel.setText("¡Adivina la palabra!");
        updateDescription(); // Actualizar la descripción
        prepareLabels(rowCount);
        gamePanel.revalidate();
        gamePanel.repaint();
        this.requestFocusInWindow(); // Cambiar el foco a la ventana principal
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if ((c >= 'a' && c <= 'z' || c == 'ñ') && game.isGameActive()) {
            if (wordTyped.length() < game.getChosenWord().length()) {
                charLabels[rowCount][wordTyped.length()].setText(c + "");
                wordTyped += c;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && game.isGameActive()) {
            if (wordTyped.length() == game.getChosenWord().length()) {
                if (rowCount < 5) { // Permitir hasta 5 intentos
                    boolean isCorrect = game.checkWord(wordTyped, charLabels, rowCount, CORRECT_COLOR, PRESENT_COLOR, UNUSED_COLOR);
                    rowCount++;
                    wordTyped = "";
                    if (isCorrect) {
                        messageLabel.setText("¡Felicidades! ¡Has ganado!");
                        game.endGame();
                        showGameEndDialog(true);
                    } else if (rowCount < 5) {
                        prepareLabels(rowCount);
                    } else {
                        messageLabel.setText("¡Fin del juego! La palabra era: " + game.getChosenWord());
                        game.endGame();
                        showGameEndDialog(false);
                    }
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && game.isGameActive()) {
            if (wordTyped.length() > 0) {
                charLabels[rowCount][wordTyped.length() - 1].setText("");
                wordTyped = wordTyped.substring(0, wordTyped.length() - 1);
            }
        }
    }

    // Mostrar el diálogo de fin del juego
    private void showGameEndDialog(boolean won) {
        String message = won ? "¡Felicidades! ¿Quieres jugar otra vez?" :
                "¡Fin del juego! La palabra era: " + game.getChosenWord() + "\n¿Quieres intentarlo de nuevo?";
        int option = JOptionPane.showConfirmDialog(this, message,
                "Fin del juego",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void updateDescription() {
        Word currentWord = game.getCurrentWord();
        descriptionArea.setText("Pista: " + currentWord.getDescription());
    }

    public static void main(String[] args) {
        // Establecer el tema Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Word[] words = {
            new Word("python", "Lenguaje de programación versátil conocido por su simplicidad y legibilidad"),
            new Word("codigo", "Conjunto de instrucciones escritas que un ordenador puede ejecutar"),
            new Word("clase", "Plantilla que define las propiedades y comportamientos de un objeto"),
            new Word("array", "Estructura que almacena elementos del mismo tipo en posiciones contiguas"),
            new Word("bucle", "Estructura que permite repetir un bloque de código múltiples veces"),
            new Word("objeto", "Instancia de una clase que contiene datos y métodos"),
            new Word("metodo", "Bloque de código que realiza una tarea específica dentro de una clase"),
            new Word("string", "Secuencia de caracteres que representa texto en programación"),
            new Word("debug", "Proceso de identificar y eliminar errores del código"),
            new Word("stack", "Estructura de datos LIFO (último en entrar, primero en salir)"),
            new Word("queue", "Estructura de datos FIFO (primero en entrar, primero en salir)"),
            new Word("cache", "Memoria de acceso rápido que almacena datos frecuentemente usados"),
            new Word("tuple", "Estructura de datos inmutable que agrupa elementos"),
            new Word("shell", "Interfaz de línea de comandos para interactuar con el sistema"),
            new Word("proxy", "Intermediario que actúa entre un cliente y un servidor"),
            new Word("swift", "Lenguaje de programación moderno desarrollado por Apple"),
            new Word("front", "Parte visual de una aplicación con la que interactúa el usuario"),
            new Word("query", "Consulta para obtener o manipular datos en una base de datos"),
            new Word("frame", "Estructura que organiza elementos visuales en una interfaz"),
            new Word("mutex", "Mecanismo de sincronización para acceso exclusivo a recursos"),
            new Word("linux", "Sistema operativo de código abierto basado en Unix"),
            new Word("macro", "Secuencia de instrucciones que se ejecutan como una sola"),
            new Word("vista", "Representación visual de datos o interfaz de usuario"),
            new Word("login", "Proceso de autenticación para acceder a un sistema"),
            new Word("pixel", "Unidad mínima de color que forma una imagen digital"),
            new Word("flash", "Tecnología para almacenamiento no volátil de datos"),
            new Word("robot", "Programa automatizado que realiza tareas repetitivas"),
            new Word("virus", "Software malicioso que se replica y daña sistemas"),
            new Word("tabla", "Estructura que organiza datos en filas y columnas"),
            new Word("chat", "Sistema de comunicación en tiempo real entre usuarios"),
            new Word("redis", "Base de datos en memoria de código abierto"),
            new Word("token", "Elemento que representa una autorización o identidad"),
            new Word("cloud", "Infraestructura de servicios computacionales remotos"),
            new Word("batch", "Proceso que ejecuta tareas sin intervención del usuario"),
            new Word("script", "Archivo que contiene secuencias de comandos ejecutables"),
            new Word("clave", "Identificador único para acceder a datos o recursos"),
            new Word("logic", "Conjunto de reglas que determinan el comportamiento"),
            new Word("scope", "Contexto donde una variable o función es accesible"),
            new Word("panel", "Contenedor de elementos en una interfaz gráfica"),
            new Word("valor", "Dato asignado a una variable o constante"),
            new Word("hiper", "Prefijo que indica conexión o enlace entre elementos"),
            new Word("local", "Variable o recurso accesible solo en su contexto")
        };
        WordleGame game = new WordleGame(words);
        WordleUI ui = new WordleUI(game);
        ui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ui.setLocationRelativeTo(null);
        ui.setVisible(true);
        ui.requestFocusInWindow();
    }
}
