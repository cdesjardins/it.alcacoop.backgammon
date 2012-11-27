package it.alcacoop.gnubackgammon.fsm;

import it.alcacoop.gnubackgammon.actors.Board;
import it.alcacoop.gnubackgammon.fsm.BaseFSM.Events;

// SIMULATED GAME STATE MACHINE
// From: http://vanillajava.blogspot.com/2011/06/java-secret-using-enum-as-state-machine.html

interface Context {
  Board board();
  State state();
  void state(State state);
}

interface State {
  boolean processEvent(Context ctx, Events evt, Object params);
  void enterState(Context ctx);
  void exitState(Context ctx);
}


// MAIN FSM
public class BaseFSM implements Context {
  
  public enum Events {
    ACCEPT_DOUBLE,
    ACCEPT_RESIGN,
    ASK_FOR_DOUBLING,
    ASK_FOR_RESIGNATION,
    EVALUATE_BEST_MOVE,
    INITIALIZE_ENVIRONMENT,
    ROLL_DICE,
    SET_BOARD,
    SET_GAME_TURN,
    SET_MATCH_SCORE,
    SET_MATCH_TO,
    UPDATE_MS_CUBEINFO,
    PERFORMED_MOVE,
    NO_MORE_MOVES,
    POINT_TOUCHED,
    GENERATE_MOVES,
    DICE_CLICKED,
    STARTING_SIMULATION,
    SIMULATED_TURN,
    STOPPED
  }

  public enum States implements State {
    STOPPED {
      @Override
      public void enterState(Context ctx) {
        System.out.println("FSM STOPPED");
      }
    };
    
    //DEFAULT IMPLEMENTATION
    public boolean processEvent(Context ctx, BaseFSM.Events evt, Object params) {return false;}
    public void enterState(Context ctx) {}
    public void exitState(Context ctx) {}

  };

  public boolean processEvent(Context ctx, BaseFSM.Events evt, Object params) { return false;}
  public void enterState(Context ctx) {}
  public void exitState(Context ctx) {}
  public State currentState;


  public void start() {
  }

  public void stop() {
    state(States.STOPPED);
  }
  
  public void restart() {
    stop();
    start();
  }
  
  public boolean processEvent(Events evt, Object params) {
//    System.out.println("PROCESS EVENT: "+evt);
//    System.out.println("\tSRC STATE: "+state());
    boolean res = state().processEvent(this, evt, params);
//    System.out.println("\tDST STATE: "+state());
    return res;
  }

  public State state() {
    return currentState;
  }

  public void state(State state) {
    if(currentState != null)
      currentState.exitState(this);
    currentState = state;
    if(currentState != null)
      currentState.enterState(this);        
  }
  
  public boolean isStopped() {
    return currentState == States.STOPPED;
  }
  
  public Board board() {
    return null;
  }
}