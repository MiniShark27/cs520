package controller;

import javax.swing.JButton;

import model.RowBlockModel;
import model.RowGameModel;
import view.RowGameGUI;

public class RowGameController {
	private RowGameModel gameModel;
	public RowGameGUI gameView;

	/**
	 * Creates a new game initializing the GUI.
	 */
	public RowGameController() {
		gameModel = new RowGameModel();
		gameView = new RowGameGUI(this);

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				gameModel.blocksData[row][column].setContents("");
				gameModel.blocksData[row][column].setIsLegalMove(true);
				gameView.updateBlock(gameModel, row, column);
			}
		}
	}

	private boolean checkColumn(int column, String playerChar) {
		for (int row = 0; row < 3; row++) {
			if (!gameModel.blocksData[row][column].getContents().equals(playerChar)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkRow(int row, String playerChar) {
		for (int column = 0; column < 3; column++) {
			if (!gameModel.blocksData[row][column].getContents().equals(playerChar)) {
				return false;
			}
		}
		return true;
	}

	private boolean checkDiagonal(boolean isTopLeft, String playerChar) {
		for (int row = 0; row < 3; row++) {
			if (!gameModel.blocksData[row][isTopLeft ? row : 2 - row].getContents().equals(playerChar)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Moves the current player into the given block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public void move(JButton block) {
		// Update remaining moves
		gameModel.movesLeft--;
		if (gameModel.movesLeft % 2 == 1) {
			gameView.playerturn.setText("'X': Player 1");
		} else {
			gameView.playerturn.setText("'O': Player 2");
		}

		// Find block's row and column
		RowBlockModel blockModel = null;
		int blockModelRow = 0, blockModelColumn = 0;
		for (blockModelRow = 0; blockModelRow < 3; blockModelRow++) {
			for (blockModelColumn = 0; blockModelColumn < 3; blockModelColumn++) {
				if (gameView.blocks[blockModelRow][blockModelColumn] == block) {
					blockModel = gameModel.blocksData[blockModelRow][blockModelColumn];
					break;
				}
			}
			if (blockModel != null) {
				break;
			}
		}

		// If block wasn't found, throw Exception
		if (blockModel == null) {
			throw new IllegalArgumentException("Block not found.");
		}

		// If block is found, update contents
		String playerChar = gameModel.player.equals("1") ? "X" : "O";
		blockModel.setContents(playerChar);
		gameView.updateBlock(gameModel, blockModelRow, blockModelColumn);

		// Check for win
		boolean isWin = checkColumn(blockModelColumn, playerChar);
		isWin = isWin || checkRow(blockModelRow, playerChar);
		if (blockModelRow == blockModelColumn) {
			isWin = isWin || checkDiagonal(true, playerChar);
		} else if (blockModelRow == 2 - blockModelColumn) {
			isWin = isWin || checkDiagonal(false, playerChar);
		}

		// If win, update final result
		if (isWin) {
			gameModel.setFinalResult(String.format("Player %s wins!", gameModel.player));
			endGame();
		}

		// If no moves left, update final result
		else if (gameModel.movesLeft == 0) {
			gameModel.setFinalResult(RowGameModel.GAME_END_NOWINNER);
		}

		// If there was a final result, update UI
		if (gameModel.getFinalResult() != null) {
			gameView.playerturn.setText(gameModel.getFinalResult());
		}

		// Switch player (set at end because current player is used to format the final
		// result if a player wins)
		gameModel.player = gameModel.player.equals("1") ? "2" : "1";
	}

	/**
	 * Ends the game disallowing further player turns.
	 */
	private void endGame() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				gameView.blocks[row][column].setEnabled(false);
			}
		}
	}

	/**
	 * Resets the game to be able to start playing again.
	 */
	public void resetGame() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				gameModel.blocksData[row][column].reset();
				gameModel.blocksData[row][column].setIsLegalMove(true);
				gameView.updateBlock(gameModel, row, column);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = 9;
		gameModel.setFinalResult(null);
		gameView.playerturn.setText("Player 1 to play 'X'");
	}
}
