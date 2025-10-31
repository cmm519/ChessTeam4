import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ChessBoardGUI{
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private final JPanel[][] gameBoard = new JPanel[ROWS][COLS];
	private JPanel selectedPanel = null;
	private JLabel selectedLabel = null;

	//custom lighter colors for the board
	Color lightColor = new Color(240,217,181);
	Color darkColor = new Color(181,136,99);
 
	//unicode for the chess pieces
	private static final String[][] initBoard = {
		{"\u2656","\u2658","\u2657","\u2655","\u2654","\u2657","\u2658","\u2656"},
		{"\u2659","\u2659","\u2659","\u2659","\u2659","\u2659","\u2659","\u2659"},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"\u265F","\u265F","\u265F","\u265F","\u265F","\u265F","\u265F","\u265F"},		
		{"\u265C","\u265E","\u265D","\u265B","\u265A","\u265D","\u265E","\u265C"}
	};
 

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
				System.out.println("Mouse clicked"); //for testing purpose
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
				
				if (selectedPanel != null && selectedLabel !=  null){
					Component root = SwingUtilities.getRootPane(selectedPanel);
					Point mousePoint = MouseInfo.getPointerInfo().getLocation();
					SwingUtilities.convertPointFromScreen(mousePoint, root);

					Component dropTarget = SwingUtilities.getDeepestComponentAt(root,mousePoint.x,mousePoint.y);

					if (dropTarget instanceof JLabel) {
						dropTarget = ((JLabel) dropTarget).getParent();
					}

					if(dropTarget instanceof JPanel){
						JPanel targetPanel = (JPanel) dropTarget;

						selectedPanel.remove(selectedLabel);
						selectedPanel.setBorder(null);

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
				selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,3)); 
			}
		}else{
				selectedPanel.remove(selectedLabel);
				selectedPanel.setBorder(null);

				
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

	public static void main(String[] argc){
		SwingUtilities.invokeLater(ChessBoardGUI::new); 
    }
}
