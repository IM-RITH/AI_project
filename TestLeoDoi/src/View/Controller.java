/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.EvalFunction;
import Model.MyHillClimb;
import Model.RoadState;
import Model.State;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author minh0
 * Lớp điều khiển các hoạt động liên quan đén dữ liệu và màn hình vẽ
 * 
 */

public class Controller {
    
    private DefaultTableModel tableModel;
    private static Controller c;
    public static Controller getController(){
        if(c==null){
            c=new Controller();
        }
        return c;
    }
    public void loadtblDinh(JTable tbl,MyHillClimb core){
        tableModel = (DefaultTableModel) tbl.getModel();
        tableModel.setRowCount(0);
        if(core.getHm()==null||core.getHm().isEmpty()){
            
        }else{
            for (int i = 0; i < core.getHm().size(); i++) {
                tableModel.addRow(new Object[]{i+1 , core.getHm().get(i).getNameState() , 
                    EvalFunction.evaluationfunction(core.getEnds(), core.getHm().get(i))});
            }
        }
        tbl.setRowHeight(20);
    }
    //load bảng các bước trong thuật toán
    public void loadtblBC(JTable tbl , MyHillClimb core){
        tableModel = (DefaultTableModel) tbl.getModel();
        tableModel.setRowCount(0);
        if(core.getDs()==null||core.getDs().isEmpty()){
            
        }else{
            for (int i = 0; i < core.getDs().size(); i++) {
                String s;
                
                if(core.getDs().get(i).getS()==null||core.getDs().get(i).getS().getNameState()==null){
                    s="";
                }else{
                    s = core.getDs().get(i).getS().getNameState();
                }
                
                tableModel.addRow(new Object[]{i , s , 
                    core.getDs().get(i).childString(core.getEnds()) , 
                    core.getDs().get(i).MOString(core.getEnds()) , 
                    core.getDs().get(i).DONGString()});
            }
        }
        tbl.setRowHeight(20);
    }
    public void loadcbDINH(JComboBox comboBox , MyHillClimb core ){
        comboBox.removeAllItems();
        if(core.getHm()==null||core.getHm().isEmpty()){
            
        }else{
            for (int i = 0; i < core.getHm().size(); i++) {
                comboBox.addItem(core.getHm().get(i).getNameState());
            }
        }
    }
    
    
    public void addDinh(){
        
    }
    
    public void addRoadState1(){
        
    }
    
    public void addRoadState2(){
        
    }
    
    public boolean checkRoad(State dau,State dich,MyHillClimb core){
        List<RoadState> ds= core.getRoadStates();
        boolean check = false;
        for(int i=0;i<ds.size();i++){
            if(dau.getNameState().compareTo(ds.get(i).getStart().getNameState())==0 
                    && dich.getNameState().compareTo(ds.get(i).getEnd().getNameState())==0){
                check =true;
                break;
            }
        }
        return check;
    }
    
}
