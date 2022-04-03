import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.RowGameModel;
import controller.RowGameController;

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
    public void testNewBlockViolatesPrecondition() {
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

    @Test(expected = IllegalArgumentException.class)
    public void blockNotFoundInMove() {
        game.move(null);
    }

    @Test
    public void illegalMove() {
        game.gameView.blocks[0][0].doClick();
        System.out.println(game.gameView.blocks[0][0].getText());
        game.gameView.blocks[0][0].doClick();
        // game.move(game.gameView.blocks[0][0]);
        assertEquals("2", game.gameModel.player);
        assertEquals("X", game.gameModel.blocksData[0][0].getContents());
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
