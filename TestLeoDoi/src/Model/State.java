/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author minh0
 */
public class State {
    private String nameState;
    private int NumofState;
    //private int ValueofState;

    public State() {
    }

    
    public State(String nameState, int NumofState) {
        this.nameState = nameState;
        this.NumofState = NumofState;
    }
    
    public String getNameState() {
        return nameState;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    public int getNumofState() {
        return NumofState;
    }

    public void setNumofState(int NumofState) {
        this.NumofState = NumofState;
    }

//    public int getValueofState() {
//        return ValueofState;
//    }
//
//    public void setValueofState(int ValueofState) {
//        this.ValueofState = ValueofState;
//    }
    
    
}
