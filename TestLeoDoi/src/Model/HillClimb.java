/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minh0
 */
public class HillClimb {
    //Đỉnh đang xét
    private State S;
    //Tập đỉnh con của S
    private List<State> childS;
    //Đỉnh MO đc chon trong Child(S)
    private State MO;
    //Tập các đỉnh đã xét
    private List<State> DONG;

    public HillClimb(State S, List<State> childS, State MO, List<State> DONG) {
        this.S = S;
        this.childS = childS;
        this.MO = MO;
        this.DONG = DONG;
    }

    public HillClimb() {
        this.DONG = new ArrayList<>();
        this.MO = new State();
        this.childS = new ArrayList<>();
        this.S = new State();
    }

    public State getS() {
        return S;
    }

    public void setS(State S) {
        this.S = S;
    }

    public List<State> getChildS() {
        return childS;
    }

    public void setChildS(List<State> childS) {
        this.childS = childS;
    }

    public State getMO() {
        return MO;
    }

    public void setMO(State MO) {
        this.MO = MO;
    }

    public List<State> getDONG() {
        return DONG;
    }

    public void setDONG(List<State> DONGt) {
        
        if(DONGt.contains(S)){
            for(int i=0;i<DONGt.size();i++){
                this.DONG.add(DONGt.get(i));
            }
        }else{
            for(int i=0;i<DONGt.size();i++){
                this.DONG.add(DONGt.get(i));
            }
            this.DONG.add(S);
        }
        
    }
    
    //Chuyển List childs về dạng chuỗi
    public String childString(List<State> end){
        String t="";
        if (childS==null||childS.isEmpty()) {
            
        }else{
            for(int i=0;i<childS.size();i++){
                if (i==0) {
                    t=t+childS.get(i).getNameState()+"["+EvalFunction.evaluationfunction(end, childS.get(i))+"]";
                }else{
                    t=t+" , "+childS.get(i).getNameState()+"["+EvalFunction.evaluationfunction(end, childS.get(i))+"]";
                }
            
            }
        }
        
        return t;
    }
    //chuyển List MO về dạng chuỗi
    public String DONGString(){
        String t= "";
        if(DONG ==null||DONG.isEmpty()){
            
        }else{
            for(int i=0;i<DONG.size();i++){
                if(i==0){
                    t = t + DONG.get(i).getNameState();
                }else{
                t = t+" , "+ DONG.get(i).getNameState();
                }
            
            }
        }
        
        return t;
    }
    public String MOString(List<State> end){
        String t = "";
        if(MO==null||MO.getNameState()==null){
            
        }else{
            t = t+MO.getNameState()+"["+EvalFunction.evaluationfunction(end, MO)+"]";
        }
        return t;
    }
    //tìm con của S đang xét được sắp xếp theo thứ tự tăng dần và ưu tiên đỉnh nhập trước
    public void setChildS(List<RoadState> ds , List<State> hm , List<State> end){
        //Lấy ra danh sách đỉnh con 
        List<State> ds1 = new ArrayList<>();
        for (int i = 0; i < ds.size(); i++) {
            //
            if (ds.get(i).getStart().getNameState().compareTo(S.getNameState())==0) {
                ds1.add(ds.get(i).getEnd());
            }
        }
        //điều kiện ko null
        //sắp xếp theo thứ tự nhập vào
        List<Integer> tt = new ArrayList<>();
        for (int i = 0; i < ds1.size(); i++) {
            for (int j = 0; j < hm.size(); j++) {
                if (ds1.get(i).getNameState().compareTo(hm.get(j).getNameState())==0) {
                    tt.add(j);
                }
                
            }
        }
        
        for(int i=0;i<tt.size()-1;i++){
            for (int j = 0; j < tt.size()-i-1; j++) {
                if(tt.get(j)>tt.get(j+1)){
                    int tg = tt.get(j);
                    tt.set(j, tt.get(j+1));
                    tt.set(j+1, tg);
                    
                    State tg1 = ds1.get(j);
                    ds1.set(j, ds1.get(j+1));
                    ds1.set(j+1, tg1);
                }
            }
        }
        //xắp xếp theo thứ tự tăng dần thuật toán nổi bọt
        for (int i = 0; i < ds1.size()-1; i++) {
            for(int j=0; j < ds1.size()-i-1 ; j++){
                if(EvalFunction.evaluationfunction(end, ds1.get(j)) > EvalFunction.evaluationfunction(end, ds1.get(j+1))){
                    State tg1 = ds1.get(j);
                    ds1.set(j, ds1.get(j+1));
                    ds1.set(j+1, tg1);
                }
            }
        }
        this.childS=ds1;
    }
    public boolean checkinlist(State s,List<State> ds){
        return ds.contains(s);
    }
}
