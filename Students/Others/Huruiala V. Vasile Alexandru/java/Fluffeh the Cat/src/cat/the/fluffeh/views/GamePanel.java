package cat.the.fluffeh.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cat.the.fluffeh.game.CellContent;
import cat.the.fluffeh.game.GameState;
import cat.the.fluffeh.models.Fluffeh;

/**
 * 
 * @author alexh This class represents the view. It contains a matrix of all
 *         panels and reprints everything at every logic step.
 *
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = -4394426239681487648L;

	private DrawPanel[][] grid;

	private int rows;
	private int cols;

	public GamePanel(GameState gameState, int width, int height) {
		this.rows = gameState.getRows();
		this.cols = gameState.getCols();


		int cellWidth = width / cols;
		int cellHeight = height / rows;

		this.grid = new DrawPanel[rows][cols];
		this.setLayout(new GridLayout(rows, cols));

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				this.grid[row][col] = new DrawPanel(cellWidth, cellHeight, gameState.getCellContents()[row][col],
						new BorderLayout());
				this.add(this.grid[row][col]);
			}
		}

		this.printSight(gameState);
	}

	public void printSight(GameState gameState) {
		Fluffeh fluffeh = gameState.getFluffeh();

		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				double distance = fluffeh.getPosition().distance(row, col);
				if (distance < fluffeh.getViewDistance()) {
					this.setCellContent(row, col, gameState.getCellContents()[row][col]);
				} else {
					this.setCellContent(row, col, CellContent.FOG);
				}
			}
		}
		
		this.setCellContent(fluffeh.getPosition().x, fluffeh.getPosition().y, CellContent.FLUFFEH);
		
		this.repaint();
	}

	public void setCellContent(int row, int col, CellContent content) {
		this.grid[row][col].setContent(content);
	}
}
