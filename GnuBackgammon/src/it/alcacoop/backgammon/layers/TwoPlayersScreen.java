/*
 ##################################################################
 #                     GNU BACKGAMMON MOBILE                      #
 ##################################################################
 #                                                                #
 #  Authors: Domenico Martella - Davide Saurino                   #
 #  E-mail: info@alcacoop.it                                      #
 #  Date:   19/12/2012                                            #
 #                                                                #
 ##################################################################
 #                                                                #
 #  Copyright (C) 2012   Alca Societa' Cooperativa                #
 #                                                                #
 #  This file is part of GNU BACKGAMMON MOBILE.                   #
 #  GNU BACKGAMMON MOBILE is free software: you can redistribute  # 
 #  it and/or modify it under the terms of the GNU General        #
 #  Public License as published by the Free Software Foundation,  #
 #  either version 3 of the License, or (at your option)          #
 #  any later version.                                            #
 #                                                                #
 #  GNU BACKGAMMON MOBILE is distributed in the hope that it      #
 #  will be useful, but WITHOUT ANY WARRANTY; without even the    #
 #  implied warranty of MERCHANTABILITY or FITNESS FOR A          #
 #  PARTICULAR PURPOSE.  See the GNU General Public License       #
 #  for more details.                                             #
 #                                                                #
 #  You should have received a copy of the GNU General            #
 #  Public License v3 along with this program.                    #
 #  If not, see <http://http://www.gnu.org/licenses/>             #
 #                                                                #
 ##################################################################
*/

package it.alcacoop.backgammon.layers;

import it.alcacoop.backgammon.GnuBackgammon;
import it.alcacoop.backgammon.actions.MyActions;
import it.alcacoop.backgammon.actors.FixedButtonGroup;
import it.alcacoop.backgammon.fsm.BaseFSM.Events;
import it.alcacoop.backgammon.ui.IconButton;
import it.alcacoop.backgammon.ui.UIDialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class TwoPlayersScreen extends BaseScreen {

  private Label connecting;
  private Table table;
  private FixedButtonGroup type;

  private Label llocal;
  private Label lfibs;
  private Label ltiga;

  private ScrollPane sp;
  
  private int variant = 0; //0=LOCAL,1=FIBS,2=TIGA
  
  
  public TwoPlayersScreen(){
    ClickListener cl = new ClickListener() {
      public void clicked(InputEvent event, float x, float y) {
        String s = ((TextButton)event.getListenerActor()).getText().toString().toUpperCase();
        if (!s.equals("BACK"))
          s+=variant;
        GnuBackgammon.fsm.processEvent(Events.BUTTON_CLICKED, s);
      };
    };

    llocal = new Label("", GnuBackgammon.skin);
    llocal.setWrap(true);
    
    String sl = "LOCAL\n\n" +
        "Play against human player on the same device\n" +
        "As on single player mode, you can choose from 1 to 15 points match," +
        "with or without cube, an between two variant: " +
        "\n1. Backgammon" +
        "\n2. Nackgammon";
    llocal.setText(sl);
    
    lfibs = new Label("", GnuBackgammon.skin);
    lfibs.setWrap(true);
    String sf = "FIBS\n\n" +
        "FIBS is the First Internet Backgammon Server, " +
        "it allows Internet users to play backgammon in real-time against " +
        "real people (and even some bots). There are players of every " +
        "conceivable ability logging onto FIBS, from absolute beginners " +
        "to serious backgammon champion contenders. \n\n" +
        "All players need a username (nick) and password to play. The name " +
        "must be 3-20 letters only (no numbers) and underscore (_) is "  +
        "the only punctuation allowed, also remember that it is " +
        "case-sensitive ('A' and 'a' are different). \n\n " +
        "Please do not forget your password. There is currently no way " +
        "for the FIBS administrators to retrieve this information. If you " +
        "forget your password then you must start again under a new username.";
    lfibs.setText(sf);
    
    ltiga = new Label("", GnuBackgammon.skin);
    ltiga.setWrap(true);
    String st = "TIGERGAMMON\n\n" +
        "TigerGammon is just another backgammon server like FIBS. TigerGammon " +
        "wants to keep the institution FIBS alive. " +
        "TigerGammon works just like FIBS. Over time you will see features " +
        "that exceed, what you can see on FIBS\n\n" +
        "ANDREAS HAUSMANN features TigerGammon. He is another Fibster discontent " +
        "with the flaws of FIBS just like so many others.\n\n" +
        "Like FIBS, TigerGammon needs a username/password account.\nNOTE: FIBS account is " +
        "not compatible with TigerGammon account - You must create another one, with different ranking\n\n" +
        "Please do not forget your password. There is currently no way " +
        "for the TigerGammon administrator to retrieve this information. If you " +
        "forget your password then you must start again under a new username.";
    ltiga.setText(st);
    
    stage.addListener(new InputListener() {
      @Override
      public boolean keyDown(InputEvent event, int keycode) {
        if(Gdx.input.isKeyPressed(Keys.BACK)||Gdx.input.isKeyPressed(Keys.ESCAPE)) {
          if (UIDialog.isOpened()) return false;
          GnuBackgammon.fsm.processEvent(Events.BUTTON_CLICKED, "BACK");
        }
        return super.keyDown(event, keycode);
      }
    });
    
    type = new FixedButtonGroup();
    
    Label titleLabel = new Label("TWO PLAYERS SETTINGS", GnuBackgammon.skin);
    
    float height = stage.getHeight()/8.5f;
    float pad = 0;
    
    table = new Table();
    table.setWidth(stage.getWidth()*0.9f);
    table.setHeight(stage.getHeight()*0.9f);
    table.setX((stage.getWidth()-table.getWidth())/2);
    table.setY((stage.getHeight()-table.getHeight())/2);
    
    
    TextButton play = new TextButton("PLAY", GnuBackgammon.skin);
    play.addListener(cl);
    TextButton back = new TextButton("BACK", GnuBackgammon.skin);
    back.addListener(cl);
    
    TextButtonStyle ts = GnuBackgammon.skin.get("toggle", TextButtonStyle.class);
    IconButton local = new IconButton("Local", GnuBackgammon.atlas.findRegion("dp"), ts);
    IconButton fibs = new IconButton("FIBS", GnuBackgammon.atlas.findRegion("mpl"), ts);
    IconButton tiga = new IconButton("TigerGammon", GnuBackgammon.atlas.findRegion("mpl"), ts);
    type.add(local);
    type.add(fibs);
    type.add(tiga);
    
    local.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Table text = new Table();
        text.add(llocal).left().top().expandX().fillX();
        text.row();
        text.add().fill().expand();
        sp.setWidget(text);
        variant = 0;
        GnuBackgammon.Instance.server = "";
        super.clicked(event, x, y);
      }
    });
    
    fibs.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Table text = new Table();
        text.add(lfibs).left().top().expand().fill();
        text.row();
        text.add().fill().expand();
        sp.setWidget(text);
        variant = 1;
        GnuBackgammon.Instance.server = "fibs.com";
        super.clicked(event, x, y);
      }
    });
    
    tiga.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Table text = new Table();
        text.add(ltiga).left().top().expand().fill();
        text.row();
        text.add().fill().expand();
        sp.setWidget(text);
        variant = 2;
        GnuBackgammon.Instance.server = "ti-ga.com";
        super.clicked(event, x, y);
      }
    });
    
    table.add(titleLabel).colspan(7);
    
    table.row();
    table.add().fill().expandX().colspan(7).height(height/2);
    
    Table t1 = new Table();
    
    t1.add().expandX().fill().height(height/10);
    t1.row();
    t1.add(local).fillX().expandX().height(height).padRight(pad);
    t1.row();
    t1.add().expandX().fill().height(height/10);

    t1.row();
    t1.add(fibs).fillX().expandX().height(height).padRight(pad);
    t1.row();
    t1.add().expandX().fill().height(height/10);
    
    t1.row();
    t1.add(tiga).fillX().expandX().height(height).padRight(pad);
    t1.row();
    t1.add().expand().fill();
    
    Table text = new Table();
    text.add(llocal).expandX().fillX();
    text.row();
    text.add().fillY().expandY();
    sp = new ScrollPane(text, GnuBackgammon.skin.get("info", ScrollPaneStyle.class));
    sp.setFadeScrollBars(false);
    sp.setForceOverscroll(false, false);
    sp.setOverscroll(false, false);
    
    table.row();
    table.add(t1).colspan(3).fill().expand();
    table.add(sp).colspan(4).fill().expand().padLeft(stage.getWidth()/20);
    
    table.row();
    table.add().fill().expand().colspan(7).height(height/2);
    
    table.row().height(height);
    table.add();
    table.add(back).fill().colspan(2);
    table.add();
    table.add(play).fill().colspan(2);
    table.add();
    
    stage.addActor(table);
    
    connecting = new Label("Connecting to server...", GnuBackgammon.skin);
    connecting.setVisible(false);
    connecting.setX((stage.getWidth()-connecting.getWidth())/2);
    connecting.setY(height*1.5f);
    connecting.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.4f), Actions.alpha(1,  0.4f))));
    stage.addActor(connecting);
  }
  

  public void hideConnecting() {
    connecting.setVisible(false);
    Gdx.graphics.setContinuousRendering(false);
    Gdx.graphics.requestRendering();
  }
  public void showConnecting(String msg) {
    connecting.setText(msg);
    connecting.setX((stage.getWidth()-connecting.getWidth())/2);
    connecting.setVisible(true);
    Gdx.graphics.setContinuousRendering(true);
    Gdx.graphics.requestRendering();
  }
  
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    stage.act(delta);
    stage.draw();
  }

  
  @Override
  public void show() {
    super.show();
    Gdx.input.setInputProcessor(stage);
    Gdx.input.setCatchBackKey(true);
    table.setColor(1,1,1,0);
    table.addAction(MyActions.sequence(Actions.delay(0.1f),Actions.fadeIn(0.6f)));
  }
}
