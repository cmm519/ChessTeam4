import board.Board;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import pieces.Piece;
import utils.MoveValidator;
import utils.Position;

// define a serializable board state class
class BoardState implements Serializable {
    private String[][] board;

    public BoardState(String[][] board) {
        this.board = board;
    }

    public String[][] getBoard() {
        return board;
    }
}

public class ChessBoardGUI {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private final JPanel[][] gameBoard = new JPanel[ROWS][COLS];
    private JPanel selectedPanel = null;
    private JLabel selectedLabel = null;
    public static boolean debug = false;

    // The logical board that handles rules and piece positions
    private Board boardLogic;
    private String currentPlayer = "white"; // Track whose turn it is

    Color lightColor = new Color(240, 217, 181);
    Color darkColor = new Color(181, 136, 99);
    static final String wP = "\u2659";
    static final String bP = "\u265F";
    static final String bR = "\u265C";
    static final String wR = "\u2656";
    static final String bN = "\u265E";
    static final String wN = "\u2658";
    static final String bB = "\u265D";
    static final String wB = "\u2657";
    static final String wQ = "\u2655";
    static final String bQ = "\u265B";
    static final String wK = "\u2654";
    static final String bK = "\u265A";

    private static final String[][] initBoard = {
        {bR, bN, bB, bQ, bK, bB, bN, bR},
        {bP, bP, bP, bP, bP, bP, bP, bP},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {wP, wP, wP, wP, wP, wP, wP, wP},
        {wR, wN, wB, wK, wQ, wB, wN, wR}
    };

    private void GameOver(String winner) {
        String message = "Checkmate! " + winner + " wins!";
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(gameBoard[0][0]);
        JOptionPane.showMessageDialog(topFrame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    public ChessBoardGUI() {
        boardLogic = new Board();

        JFrame frame = new JFrame("ChessBoard GUI");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(ROWS, COLS));

        MouseAdapter boardListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel clickPanel = getPanelFromEvent(e);
                if (clickPanel != null) {
                    handleMouseClick(clickPanel);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                if (panel != null && panel.getComponentCount() > 0) {
                    // Only allow selecting pieces of the current player
                    Point coords = getGridCoordinates(panel);
                    Piece p = boardLogic.getPiece(new Position(coords.x, coords.y));
                    if (p != null && p.getColor().equalsIgnoreCase(currentPlayer)) {
                        selectedPanel = panel;
                        selectedLabel = (JLabel) panel.getComponent(0);
                        selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                if (panel != null) {
                    panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selectedPanel != null && selectedLabel != null) {
                    Component root = SwingUtilities.getRootPane(selectedPanel);
                    Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(mousePoint, root);

                    Component dropTarget = SwingUtilities.getDeepestComponentAt(root, mousePoint.x, mousePoint.y);
                    if (dropTarget instanceof JLabel) {
                        dropTarget = ((JLabel) dropTarget).getParent();
                    }

                    if (dropTarget instanceof JPanel) {
                        JPanel targetPanel = (JPanel) dropTarget;
                        Point fromCoords = getGridCoordinates(selectedPanel);
                        Point toCoords = getGridCoordinates(targetPanel);

                        if (!isMoveValid(fromCoords, toCoords)) {
                            // Invalid move: Snap back
                            selectedPanel.setBorder(null);
                            selectedPanel = null;
                            selectedLabel = null;
                            return;
                        }

                        // Execute Move Logic
                        executeMove(fromCoords, toCoords, targetPanel);
                    } else {
                        // Released outside board
                        selectedPanel.setBorder(null);
                        selectedPanel = null;
                        selectedLabel = null;
                    }
                }
            }

            private JPanel getPanelFromEvent(MouseEvent e) {
                Component comp = e.getComponent();
                if (comp instanceof JLabel) {
                    return (JPanel) ((JLabel) comp).getParent();
                } else if (comp instanceof JPanel) {
                    return (JPanel) comp;
                }
                return null;
            }
        };

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground((i + j) % 2 == 0 ? lightColor : darkColor);
                panel.addMouseListener(boardListener);
                panel.addMouseMotionListener(boardListener);

                String pieces = initBoard[i][j];
                if (!pieces.isEmpty()) {
                    JLabel label = new JLabel(pieces, SwingConstants.CENTER);
                    label.setFont(new Font("Serif", Font.BOLD, 32));
                    panel.add(label);
                }
                frame.add(panel);
                gameBoard[i][j] = panel;
            }
        }
        frame.setVisible(true);
    }

    private Point getGridCoordinates(JPanel panel) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (gameBoard[r][c] == panel) {
                    return new Point(r, c);
                }
            }
        }
        return new Point(-1, -1);
    }

    /** GPT added 185 - 286
     * Validates a move using Board logic and MoveValidator.
     */
    private boolean isMoveValid(Point from, Point to) {
        Position fromPos = new Position(from.x, from.y);
        Position toPos = new Position(to.x, to.y);

        Piece p = boardLogic.getPiece(fromPos);
        if (p == null) return false;

        // 1. Check Turn
        if (!p.getColor().equalsIgnoreCase(currentPlayer)) {
            if (debug) System.out.println("Not your turn!");
            return false;
        }

        // 2. Check Piece Rules (Can this piece physically move there?)
        if (!p.isValidMove(toPos, boardLogic.getGrid())) {
             if (debug) System.out.println("Invalid piece movement.");
            return false;
        }

        // 3. Check King Safety (Does this move leave/put King in check?)
        // We must simulate the move on a copy of the board
        Board tempBoard = boardLogic.copy();
        tempBoard.movePiece(fromPos, toPos);

        if (MoveValidator.isKingInCheck(currentPlayer, tempBoard.getGrid())) {
            if (debug) System.out.println("Move leaves king in check!");
            return false;
        }

        return true;
    }

    /**
     * Executes the move on both the Logical Board and the GUI.
     */
    private void executeMove(Point fromCoords, Point toCoords, JPanel targetPanel) {
        // Update Logic
        Position fromPos = new Position(fromCoords.x, fromCoords.y);
        Position toPos = new Position(toCoords.x, toCoords.y);
        boardLogic.movePiece(fromPos, toPos);

        // Update GUI
        selectedPanel.remove(selectedLabel);
        selectedPanel.setBorder(null);
        targetPanel.removeAll(); // Removes captured piece if any
        targetPanel.add(selectedLabel);

        selectedPanel.revalidate();
        selectedPanel.repaint();
        targetPanel.revalidate();
        targetPanel.repaint();
        targetPanel.setCursor(Cursor.getDefaultCursor());

        selectedPanel = null;
        selectedLabel = null;

        // Switch Turn
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
        System.out.println("Turn: " + currentPlayer);

        // Check Game State (Checkmate/Stalemate)
        if (MoveValidator.isCheckmate(currentPlayer, boardLogic)) {
            GameOver(currentPlayer.equals("white") ? "Black" : "White");
        } else if (MoveValidator.isKingInCheck(currentPlayer, boardLogic.getGrid())) {
            System.out.println("Check!");
        }
    }

    private void handleMouseClick(JPanel clickPanel) {
        if (selectedPanel == null) {
            if (clickPanel.getComponentCount() > 0) {
                // Only select if it matches current player
                Point coords = getGridCoordinates(clickPanel);
                Piece p = boardLogic.getPiece(new Position(coords.x, coords.y));
                
                if (p != null && p.getColor().equalsIgnoreCase(currentPlayer)) {
                    selectedPanel = clickPanel;
                    selectedLabel = (JLabel) selectedPanel.getComponent(0);
                    selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                }
            }
        } else {
            // Move Attempt
            JPanel targetPanel = clickPanel;
            Point fromCoords = getGridCoordinates(selectedPanel);
            Point toCoords = getGridCoordinates(targetPanel);

            if (!isMoveValid(fromCoords, toCoords)) {
                // Invalid move: deselect
                selectedPanel.setBorder(null);
                selectedPanel = null;
                selectedLabel = null;
                return;
            }

            // Valid move: Execute
            executeMove(fromCoords, toCoords, targetPanel);
        }
    }

    public void saveGameToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Note: For a real save, you should serialize 'boardLogic' and 'currentPlayer'
            // Saving just the visual board strings loses game state (like castling rights).
            out.writeObject(new BoardState(getVisualBoardState())); 
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[][] getVisualBoardState() {
        String[][] currentBoard = new String[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Component[] components = gameBoard[i][j].getComponents();
                if (components.length > 0 && components[0] instanceof JLabel) {
                    currentBoard[i][j] = ((JLabel) components[0]).getText();
                } else {
                    currentBoard[i][j] = "";
                }
            }
        }
        return currentBoard;
    }

    public void loadGameFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            BoardState loadedState = (BoardState) in.readObject();
            String[][] loadedBoard = loadedState.getBoard();

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    gameBoard[i][j].removeAll();
                    String piece = loadedBoard[i][j];
                    if (!piece.isEmpty()) {
                        JLabel label = new JLabel(piece, SwingConstants.CENTER);
                        label.setFont(new Font("Serif", Font.BOLD, 32));
                        gameBoard[i][j].add(label);
                    }
                    gameBoard[i][j].revalidate();
                    gameBoard[i][j].repaint();
                }
            }
            // WARNING: Loading visual state does not update boardLogic!
            // You should add logic to reconstruct boardLogic from the loaded strings. This is still broken have yet to update it.
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void applySettings(String boardStyle, String pieceStyle, String boardSize) {
        switch (boardStyle) {
            case "Classic Wooden":
                lightColor = new Color(222, 184, 135);
                darkColor = new Color(139, 69, 19);
                break;
            case "Modern Gray":
                lightColor = new Color(211, 211, 211);
                darkColor = new Color(105, 105, 105);
                break;
            default:
                lightColor = new Color(240, 217, 181);
                darkColor = new Color(181, 136, 99);
        }
        
        int newSize = switch (boardSize) {
            case "Small" -> 400;
            case "Medium" -> 600;
            case "Large" -> 800;
            default -> 500;
        };

        Font font = switch (pieceStyle) {
            case "Colorful" -> new Font("Serif", Font.BOLD, 32);
            case "Minimalist" -> new Font("SansSerif", Font.PLAIN, 28);
            default -> new Font("Serif", Font.PLAIN, 32);
        };

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = gameBoard[i][j];
                panel.setBackground((i + j) % 2 == 0 ? lightColor : darkColor);
                if (panel.getComponentCount() > 0) {
                    JLabel label = (JLabel) panel.getComponent(0);
                    label.setFont(font);
                    if (pieceStyle.equals("Colorful")) {
                        label.setForeground((i < 2) ? Color.PINK : Color.BLUE);
                    } else {
                        label.setForeground(Color.BLACK);
                    }
                }
            }
        }

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(gameBoard[0][0]);
        if (topFrame != null) {
            topFrame.setSize(newSize, newSize);
            topFrame.revalidate();
        }
    }

    class SettingsWindow extends JDialog {
        public SettingsWindow(JFrame parent) {
            super(parent, "Settings", true);
            setLayout(new GridLayout(4, 2, 10, 10));

            JComboBox<String> boardStyleCombo = new JComboBox<>(new String[]{"Classic Wooden", "Modern Gray"});
            JComboBox<String> pieceStyleCombo = new JComboBox<>(new String[]{"Minimalist", "Colorful"});
            JComboBox<String> boardSizeCombo = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
            JButton applyButton = new JButton("Apply");
            
            applyButton.addActionListener(e -> {
                applySettings((String) boardStyleCombo.getSelectedItem(),
                              (String) pieceStyleCombo.getSelectedItem(),
                              (String) boardSizeCombo.getSelectedItem());
                dispose();
            });

            add(new JLabel("Board Style:"));
            add(boardStyleCombo);
            add(new JLabel("Piece Style:"));
            add(pieceStyleCombo);
            add(new JLabel("Board Size:"));
            add(boardSizeCombo);
            add(new JLabel(""));
            add(applyButton);
            pack();
            setLocationRelativeTo(parent);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Bar");
        frame.setSize(300, 400);
        JMenuBar menuBar = new JMenuBar();
        JMenu files = new JMenu("File");
        JMenu edit = new JMenu("Debug");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        JMenuItem exitGame = new JMenuItem("Exit Game");

        files.add(newGame);
        files.add(saveGame);
        files.add(loadGame);
        files.add(exitGame);
        menuBar.add(files);

        JMenu option = new JMenu("Option");
        JMenuItem settingItem = new JMenuItem("Setting");
        ChessBoardGUI gui = new ChessBoardGUI();
        settingItem.addActionListener(e -> gui.new SettingsWindow(frame).setVisible(true));
        option.add(settingItem);
        menuBar.add(option);

        JMenuItem debugItem = new JMenuItem("Debug");
        debugItem.addActionListener(e -> ChessBoardGUI.debug = !ChessBoardGUI.debug);
        edit.add(debugItem);
        menuBar.add(edit);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

        ChessBoardGUI[] gameInstance = new ChessBoardGUI[1];

        newGame.addActionListener(e -> gameInstance[0] = new ChessBoardGUI());
        saveGame.addActionListener(e -> {
            if (gameInstance[0] != null) gameInstance[0].saveGameToFile("chess_save.txt");
        });
        loadGame.addActionListener(e -> {
            if (gameInstance[0] != null) gameInstance[0].loadGameFromFile("chess_save.txt");
        });
        exitGame.addActionListener(e -> System.exit(0));
    }
}