package views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Fluffy;
import repositories.FluffyFileReader;

public class GameView extends JFrame {

	private static final long serialVersionUID = 7149973553630253601L;

	private JLabel[][] gameLabelMatrix ;
	private static final int SQUARE_SIZE = 20;
	private Fluffy fluffy;
	private JPanel gamePanel = new JPanel();
	private final int MAX_VIEW = 1;
	private char[][] cluesMatrix;

	public GameView(char[][] gameMatrix,char[][] cluesMatrix,Fluffy fluffy)
	{
		super("The game frame");
		this.setLayout(new BorderLayout());
		this.fluffy = fluffy;
		this.cluesMatrix = cluesMatrix;

		int rows = FluffyFileReader.getRows();
		int colums = FluffyFileReader.getCols();
		gameLabelMatrix = new JLabel[rows][colums];

		System.out.println("Rows: "+rows+" Cols:"+colums);

		gamePanel.setLayout(new GridLayout(FluffyFileReader.getRows(),FluffyFileReader.getCols()));

		Map<Character, String> icons  = FluffyFileReader.getIcons();

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < colums; j++) {
				
				gameLabelMatrix[i][j] = new JLabel();
				gameLabelMatrix[i][j].setIcon(new ImageIcon(icons.get(gameMatrix[i][j])));   
				gameLabelMatrix[i][j].setText(String.valueOf(gameMatrix[i][j]));
				gameLabelMatrix[i][j].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				gamePanel.add(gameLabelMatrix[i][j]);

			}

		this.setLayout(new BorderLayout());
		this.add(gamePanel, BorderLayout.CENTER);

		this.setVisible(true);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		move(fluffy, 0, 1);
		move(fluffy, 0, -1);

	}
	public void addNewLabels(int maxRows, int maxCols)
	{
		for (int i = 0; i < maxRows; i++)
			for (int j = 0; j < maxCols; j++) {
				gameLabelMatrix[i][j].setVisible(false);
				gamePanel.add(gameLabelMatrix[i][j]);
			}

	}

	public void move(Fluffy fluffy, int xOffset, int yOffset) {
		int maxRows = FluffyFileReader.getRows();
		int maxCols = FluffyFileReader.getCols();
		int x = fluffy.getFluffyX();
		int y = fluffy.getFluffyY();
		switch (gameLabelMatrix[x + xOffset][y + yOffset].getText()) {
		case " ":

			gameLabelMatrix[x][y].setText(" ");
			gameLabelMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameLabelMatrix[x][y].setText("F");
			gameLabelMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			gamePanel.removeAll();

			addNewLabels(maxRows, maxCols);

			break;
		case "H":

			gameLabelMatrix[x][y].setText(" ");
			gameLabelMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameLabelMatrix[x][y].setText("F");
			gameLabelMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			gamePanel.removeAll();

			addNewLabels(maxRows, maxCols);

			break;
		case "W":

			gameLabelMatrix[x][y].setText(" ");
			gameLabelMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameLabelMatrix[x][y].setText("F");
			gameLabelMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			gamePanel.removeAll();

			addNewLabels(maxRows, maxCols);
			setFluffyVision();
			gameOver();
			break;
		}
		setFluffyVision();
	}

	public void keepYellowClues()
	{
		int nbOfRows = FluffyFileReader.getRows();
		int nbOfCols = FluffyFileReader.getCols();

		for(int i = 0; i < nbOfRows; i++)
		{
			for(int j = 0 ; j < nbOfCols; j++)
			{
				if((!gameLabelMatrix[i][j].getText().equals(cluesMatrix[i][j]))&&(cluesMatrix[i][j]=='H')&&(!gameLabelMatrix[i][j].getText().equals("F")))
				{
					gameLabelMatrix[i][j].setText("H");
					gameLabelMatrix[i][j].setIcon(new ImageIcon("wall2.png"));
				}
			}
		}

	}

	public void setFluffyVision() 
	{
		if (fluffy.getFluffyX() == 1 && fluffy.getFluffyY() == 0) {
			for (int i = -MAX_VIEW; i <= MAX_VIEW; i++) {
				for (int j = -MAX_VIEW + 1; j <= MAX_VIEW; j++) {
					gameLabelMatrix[fluffy.getFluffyX() + i][fluffy.getFluffyY() + j].setVisible(true);

				}
			}
		} else {
			for (int i = -MAX_VIEW; i <= MAX_VIEW; i++) {
				for (int j = -MAX_VIEW; j <= MAX_VIEW; j++) {
					gameLabelMatrix[fluffy.getFluffyX() + i][fluffy.getFluffyY() + j].setVisible(true);

				}
			}
		}
	}

	public void gameOver() 
	{
		JOptionPane.showMessageDialog(null, "You won!");
		System.exit(0);
	}



}
