package test;

import static org.junit.Assert.assertEquals;

import javax.swing.JButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.RowGameController;
import model.RowBlockModel;
import model.RowGameModel;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;

    @Before
    public void setUp() {
        game = new RowGameController();
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void testNewGame() {
        assertEquals("1", game.gameModel.player);
        assertEquals(9, game.gameModel.movesLeft);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockNull() {
        new RowBlockModel(null);
    }

    @Test
    public void XWinsFirstColumn() {
        game.move(game.gameView.blocks[0][0]);
        game.move(game.gameView.blocks[0][1]);
        game.move(game.gameView.blocks[1][0]);
        game.move(game.gameView.blocks[1][1]);
        game.move(game.gameView.blocks[2][0]);
        assertEquals("Player 1 wins!", game.gameModel.getFinalResult());
    }

    @Test
    public void tie() {
        game.move(game.gameView.blocks[0][0]);
        game.move(game.gameView.blocks[0][1]);
        game.move(game.gameView.blocks[0][2]);
        game.move(game.gameView.blocks[2][0]);
        game.move(game.gameView.blocks[1][0]);
        game.move(game.gameView.blocks[1][1]);
        game.move(game.gameView.blocks[2][1]);
        game.move(game.gameView.blocks[2][2]);
        game.move(game.gameView.blocks[1][2]);
        assertEquals(RowGameModel.GAME_END_NOWINNER, game.gameModel.getFinalResult());
    }

    @Test
    public void legalMove() {
        game.move(game.gameView.blocks[0][0]);
        assertEquals("2", game.gameModel.player);
        assertEquals("X", game.gameModel.blocksData[0][0].getContents());
    }

    @Test
    public void viewInitializedTest() {
        // All JButtons in board are initialized
        assertEquals(3, game.gameView.blocks.length);
        assertEquals(3, game.gameView.blocks[0].length);
        assertEquals(3, game.gameView.blocks[1].length);
        assertEquals(3, game.gameView.blocks[2].length);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(JButton.class, game.gameView.blocks[i][j].getClass());
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetContentsNull(){
        game.gameModel.blocksData[0][0].setContents(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void blockNotFoundInMove() {
        game.move(new JButton());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalMove() {
        game.gameView.blocks[0][0].doClick();
        // Nothing should happen (not a legal move so should not be able to click)
        game.gameView.blocks[0][0].doClick();
        assertEquals("2", game.gameModel.player);
        assertEquals("X", game.gameModel.blocksData[0][0].getContents());
        // Should not be able to call move again
        game.move(game.gameView.blocks[0][0]);
    }

    @Test
    public void testReset() {
        game.move(game.gameView.blocks[0][0]);
        game.move(game.gameView.blocks[0][1]);
        game.move(game.gameView.blocks[0][2]);
        game.move(game.gameView.blocks[2][0]);
        game.move(game.gameView.blocks[1][0]);
        game.move(game.gameView.blocks[1][1]);
        game.move(game.gameView.blocks[2][1]);
        game.move(game.gameView.blocks[2][2]);
        game.move(game.gameView.blocks[1][2]);
        assertEquals(RowGameModel.GAME_END_NOWINNER, game.gameModel.getFinalResult());
        game.resetGame();

        // loop through blocksData to check if all blocks are reset
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("", game.gameModel.blocksData[i][j].getContents());
            }
        }
        // loop through game.gameView.blocks to check if all blocks text is reset
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals("", game.gameView.blocks[i][j].getText());
            }
        }
        // Verify status is reset
        assertEquals(9, game.gameModel.movesLeft);
        assertEquals("1", game.gameModel.player);
    }
}
