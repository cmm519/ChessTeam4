import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;


// define a serializable board state class
class BoardState implements Serializable {//what on earth is serializable?? Will have to learn later 
    private String[][] board;

    public BoardState(String[][] board) {
        this.board = board;
    }

    public String[][] getBoard() {
        return board;
    }
}

public class ChessBoardGUI{
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private final JPanel[][] gameBoard = new JPanel[ROWS][COLS];
	private JPanel selectedPanel = null;
	private JLabel selectedLabel = null;
	public static boolean debug = false;//switch for sop's 

	//custom lighter colors for the board// broken??
	Color lightColor = new Color(240,217,181);
	Color darkColor = new Color(181,136,99);
static final String wP="\u2659"; //white pawn
static final String bP="\u265F"; //black pawn			
static final String bR="\u265C"; //black rook
static final String wR="\u2656"; //white rook
static final String bN="\u265E"; //white knight
static final String wN="\u2658"; //black knight
static final String bB="\u265D"; //white bishop
static final String wB="\u2657"; //black bishop		
static final String wQ="\u2655"; //white queen
static final String bQ="\u265B"; //black queen
static final String wK="\u2654"; //white king
static final String bK="\u265A"; //black king
	//unicode -> string variable  for the chess pieces to make ph 3 easier
	private static final String[][] initBoard = {
		{bR,bN,bB,bQ,bK,bB,bN,bR},
		{bP,bP,bP,bP,bP,bP,bP,bP},	
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{wP,wP,wP,wP,wP,wP,wP,wP},		
		{wR,wN,wB,wK,wQ,wB,wN,wR}
	};
/**
     * Displays a "Game Over" pop-up message.
     * @param capturedKing The text of the king piece (wK or bK)
     */
    private void GameOver(String capturedKing) {//pass in which king was captured
        // Determine the winner based on which king was captured
        String winner = capturedKing.equals(wK) ? "Black" : "White";
        String message = "Checkmate! " + winner + " wins!";

        // Get the parent frame to center the dialog
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(gameBoard[0][0]);

        // Show a simple "information" message dialog
        JOptionPane.showMessageDialog(topFrame,
                                      message,
                                      "Game Over",
                                      JOptionPane.INFORMATION_MESSAGE);
            }

	private MouseAdapter boardListener;

	public ChessBoardGUI() {

		//create frame
		JFrame frame = new JFrame("ChessBoard GUI");
	    frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(ROWS, COLS));


		// create a MouseAdapter instance to handle all clicks
		MouseAdapter boardListener = new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if (ChessBoardGUI.debug) {
				System.out.println("Mouse clicked"); //for testing purpose
				}
				JPanel clickPanel = getPanelFromEvent(e);
				if(clickPanel != null){
					handleMouseClick(clickPanel);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e){
				JPanel panel = (JPanel)e.getSource();
				if(panel != null && panel.getComponentCount() > 0){
					selectedPanel = panel;
					selectedLabel = (JLabel) panel.getComponent(0);
					selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
				}

			}

			@Override
			public void mouseDragged(MouseEvent e){
				JPanel panel = (JPanel)e.getSource();
				if (panel != null){
					panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e){
				if (ChessBoardGUI.debug) {
					System.out.println(selectedLabel+""+ selectedPanel); //for testing purpose
				}
				if (selectedPanel != null && selectedLabel !=  null){
					Component root = SwingUtilities.getRootPane(selectedPanel);
					Point mousePoint = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePoint, root);

					Component dropTarget = SwingUtilities.getDeepestComponentAt(root,mousePoint.x,mousePoint.y);

					if (dropTarget instanceof JLabel) {
						dropTarget = ((JLabel) dropTarget).getParent();
					}
if (ChessBoardGUI.debug) {
					System.out.println(dropTarget); //for testing purpose
				}
					if(dropTarget instanceof JPanel){
						JPanel targetPanel = (JPanel) dropTarget;

						selectedPanel.remove(selectedLabel);
						selectedPanel.setBorder(null);

						if (targetPanel.getComponentCount() > 0) {
            Component capturedComponent = targetPanel.getComponent(0);
            
           
            if (capturedComponent instanceof JLabel) {
                JLabel capturedLabel = (JLabel) capturedComponent;
                String capturedPiece = capturedLabel.getText();

                // Check if the captured piece is a White King or Black King
                if (capturedPiece.equals(wK) || capturedPiece.equals(bK)) {
                    // If it is, show the "Game Over" dialog
                    GameOver(capturedPiece);
                }
            }
        }
						targetPanel.removeAll();
						targetPanel.add(selectedLabel);

						selectedPanel.revalidate();
						selectedPanel.repaint();
						targetPanel.revalidate();
						targetPanel.repaint();

						targetPanel.setCursor(Cursor.getDefaultCursor());

						selectedPanel = null;
						selectedLabel = null;

					}
				}
			}
			private JPanel getPanelFromEvent(MouseEvent e){
				Component comp = e.getComponent();
				if(comp instanceof JLabel){
					return (JPanel) ((JLabel) comp).getParent();
				}else if (comp instanceof JPanel) {
					return (JPanel) comp;
				}
				return null;
			}
		};



		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLS; j++) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.setBackground((i+j) % 2 == 0 ? lightColor : darkColor);

				// add listener to each step
				panel.addMouseListener(boardListener);
				panel.addMouseMotionListener(boardListener);


				String pieces = initBoard[i][j];
				if(!pieces.isEmpty()){
					JLabel label = new JLabel(pieces,SwingConstants.CENTER);
					label.setFont(new Font("Serif",Font.BOLD,32));
					panel.add(label);
				}
				frame.add(panel);
				gameBoard[i][j] = panel;	
			}
		}
		frame.setVisible(true);

	}

	private void handleMouseClick(JPanel clickPanel){
		if(selectedPanel == null){
			if(clickPanel.getComponentCount() > 0){
				selectedPanel = clickPanel;
				selectedLabel = (JLabel) selectedPanel.getComponent(0);
				//highlight selection
						System.out.println("lines 175 passed"); //for testing purpose
System.out.println(selectedPanel); //for testing purpose

				selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,3)); 
			}
		}else{
				selectedPanel.remove(selectedLabel);
				selectedPanel.setBorder(null);

				if (targetPanel.getComponentCount() > 0) {
            Component capturedComponent = targetPanel.getComponent(0);
            
            if (capturedComponent instanceof JLabel) {
                JLabel capturedLabel = (JLabel) capturedComponent;
                String capturedPiece = capturedLabel.getText();

                // Check if the captured piece is a White King or Black King
                if (capturedPiece.equals(wK) || capturedPiece.equals(bK)) {
                    // If it is, show the "Game Over" dialog
                    GameOver(capturedPiece);
                }
            }
        }
        		// Remove any piece in the destination panel
        		clickPanel.removeAll();

        		// Add the selected piece to the destination
       	 		clickPanel.add(selectedLabel);

				// Refresh both panels
				selectedPanel.revalidate();
				selectedPanel.repaint();
				clickPanel.revalidate();
				clickPanel.repaint();

				// Clear selection
				selectedPanel = null;
				selectedLabel = null;
		}
	}

	// add savegame method and loadgame met
	
	public void saveGameToFile(String filename) {
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

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(new BoardState(currentBoard));
			System.out.println("Game saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
						label.addMouseListener(boardListener);
						label.addMouseMotionListener(boardListener);
						gameBoard[i][j].add(label);
					}
					gameBoard[i][j].revalidate();
					gameBoard[i][j].repaint();
				}
			}

			System.out.println("Game loaded successfully.");
		}
		 catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

private void applySettings(String boardStyle, String pieceStyle, String boardSize){
       
        //update with boardstyle
        switch(boardStyle) {
            case "Classic Wooden":
                lightColor = new Color(222,184,135);   // burlywood light wood
                darkColor = new Color(139,69,19);  // saddlebrown dark wood         
                break;
            case "Modern Gray":
                lightColor =  new Color(211,211,211); //lightGray
                darkColor = new Color(105,105,105); // dimgray
                break;
            default:
                lightColor = new Color(240,217,181);
                darkColor = new Color(181,136,99);
        }
        //resize board
        int newSize = switch (boardSize) {
            case "Small" -> 400;
            case "Medium" -> 600;
            case "Large" -> 800;
            default -> 500;
        };

        // update piece style
        Font font = switch (pieceStyle) {
            case "Colorful" -> new Font("Serif", Font.BOLD,32);
            case "Minimalist" -> new Font ("SansSerif", Font.PLAIN, 28);
            default -> new Font ("Serif", Font.PLAIN,32);
        };

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground((i + j) % 2 == 0 ? lightColor : darkColor);

                if(panel.getComponentCount() > 0) {
                    JLabel label = (JLabel) panel.getComponent(0);
                    label.setFont(font);
                    if(pieceStyle.equals("Colorful")) {
                        label.setForeground(( i < 2 ) ? Color.PINK : Color.BLUE);
                    }else {
                        label.setForeground(Color.BLACK);
                    }
                }
            }       
        }    

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(gameBoard[0][0]);
        topFrame.setSize(newSize, newSize);
        topFrame.revalidate();
    }

    class SettingsWindow extends JDialog {
        public SettingsWindow(JFrame parent) {
            super(parent, "Settings", true);
            setLayout(new GridLayout(4, 2, 10, 10));

            JComboBox<String> boardStyleCombo = new JComboBox<>(new String[] {
                "Classic Wooden", "Modern Gray"
            });
            JComboBox<String> pieceStyleCombo = new JComboBox<>(new String[] {
                "Minimalist", "Colorful"
            });
            JComboBox<String> boardSizeCombo = new JComboBox<>(new String[] {
                "Small", "Medium", "Large"
            });

            JButton applyButton = new JButton("Apply");
            applyButton.addActionListener(e -> {
                String boardStyle = (String) boardStyleCombo.getSelectedItem();
                String pieceStyle = (String) pieceStyleCombo.getSelectedItem();
                String boardSize = (String) boardSizeCombo.getSelectedItem();
                applySettings(boardStyle, pieceStyle, boardSize);
                dispose();
            });

            add(new JLabel("Board Style:"));
            add(boardStyleCombo);
            add(new JLabel("Piece Style:"));//not implemented 
            add(pieceStyleCombo);
            add(new JLabel("Board Size:"));
            add(boardSizeCombo);
            add(new JLabel(""));
            add(applyButton);

            pack();
            setLocationRelativeTo(parent);
        }
    }

	
   // main function
    public static void main(String[] args) {
        //create frame
        JFrame frame = new JFrame("Menu Bar");
        frame.setSize(300,400);

        //create a bar
        JMenuBar menuBar = new JMenuBar();
        //create menu files
        JMenu files = new JMenu("File");
        //create menu edit
        JMenu edit = new JMenu("debug");//will use to turn on global debug sop's
        // create new game,save game, and load game for files
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        JMenuItem exitGame = new JMenuItem("Exit Game");
        // add to the files
        files.add(newGame);
        files.add(saveGame);
        files.add(loadGame);
        files.add(exitGame);
        //add to menuBar
        menuBar.add(files);



        // create additional menu named option
        JMenu option = new JMenu("Option");
		//JMenuItem debugItem = new JMenuItem("Debug");
        JMenuItem settingItem = new JMenuItem("Setting");
        ChessBoardGUI gui = new ChessBoardGUI();
        settingItem.addActionListener(e -> gui.new SettingsWindow(frame).setVisible(true));
        option.add(settingItem);
        menuBar.add(option);

		//set menubar to the frame
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

        frame.setJMenuBar(menuBar);

        menuBar.add(edit);
        //set menubar to the frame
		JMenuItem debugItem = new JMenuItem("Debug");
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
debugItem.addActionListener(e -> {
            // This toggles the boolean variable
            ChessBoardGUI.debug = !ChessBoardGUI.debug;
        });

        // create a single instance of the game
        ChessBoardGUI[] gameInstance = new ChessBoardGUI[1];
        
        // new game
		newGame.addActionListener(e -> {
            gameInstance[0] = new ChessBoardGUI();
        });
		//save game
        saveGame.addActionListener(e -> {
            if (gameInstance[0] != null) {
                gameInstance[0].saveGameToFile("chess_save.txt");
            }   
        });
		//load game
        loadGame.addActionListener(e ->{
            if (gameInstance[0] != null){
                gameInstance[0].loadGameFromFile("chess_save.txt");
            }
        });
		//exit game
        exitGame.addActionListener(e -> System.exit(0));
            
    }
} 
