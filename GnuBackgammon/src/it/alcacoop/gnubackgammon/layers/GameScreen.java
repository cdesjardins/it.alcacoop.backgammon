package it.alcacoop.gnubackgammon.layers;

import it.alcacoop.gnubackgammon.GnuBackgammon;
import it.alcacoop.gnubackgammon.actors.Board;
import it.alcacoop.gnubackgammon.logic.AICalls;
import it.alcacoop.gnubackgammon.logic.AILevels;
import it.alcacoop.gnubackgammon.logic.FSM;
import it.alcacoop.gnubackgammon.logic.FSM.Events;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class GameScreen implements Screen {

  private Stage stage;
  private Board board;
  public static FSM fsm;
  private SpriteBatch sb;
  private TextureRegion bgRegion;
  
  public GameScreen(GnuBackgammon bg){
    
    sb = new SpriteBatch();
    stage = new Stage(1005, 752, true);
    
    bgRegion = GnuBackgammon.atlas.findRegion("bg");
    
    board = new Board();
    stage.addActor(board);
    
    fsm = new FSM(board);
    fsm.start();
  }


  @Override
  public void render(float delta) {
    
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
    sb.begin();
    sb.draw(bgRegion, 0,0 , GnuBackgammon.width, GnuBackgammon.height);
    sb.end();
    
    stage.act(delta);
    stage.draw();
    
  }


  @Override
  public void resize(int width, int height) {
  }

  
  @Override
  public void show() {
    board.initBoard();
    Gdx.input.setInputProcessor(stage);
    AICalls.SetAILevel(AILevels.SUPREMO);
    fsm.processEvent(Events.START, null);
  }

  @Override
  public void hide() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
  }


}