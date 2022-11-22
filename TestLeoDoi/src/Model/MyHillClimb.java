/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author minh0
 */
public class MyHillClimb {
    
    //Đỉnh bát đầu
    private State start;
    //Tập đỉnh kết thúc
    private List<State> ends;
    //Danh sách đỉnh
    private List<State> hm;
    //danh sách đường đi
    private List<RoadState> roadStates;
    private boolean isRunning;
    private boolean isEnds;
    
    //Danh sách các bước làm 
    private List<HillClimb> ds;
    //Đường đi đã tìm đc
    private List<State> roadsearch;
    //Lưu trữ danh sách dỉnh MO đang xét
    private Stack<State> MOs;

    public MyHillClimb(State start, List<State> ends, List<State> hm, List<RoadState> roadStates, List<HillClimb> ds, List<State> roadsearch, Stack<State> MOs) {
        this.start = start;
        this.ends = ends;
        this.hm = hm;
        this.roadStates = roadStates;
        this.ds = ds;
        this.roadsearch = roadsearch;
        this.MOs = MOs;
        this.isRunning = false;
        this.isEnds = false;
    }
    
    public MyHillClimb() {
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isIsEnds() {
        return isEnds;
    }

    public void setIsEnds(boolean isEnds) {
        this.isEnds = isEnds;
    }

    
    public State getStarts() {
        return start;
    }

    public void setStarts(State starts) {
        this.start = starts;
    }

    public List<State> getEnds() {
        return ends;
    }

    public void setEnds(List<State> ends) {
        this.ends = ends;
    }

    public List<State> getRoadsearch() {
        return roadsearch;
    }

    public void setRoadsearch(List<State> roadsearch) {
        this.roadsearch = roadsearch;
    }

    public List<State> getHm() {
        return hm;
    }

    public void setHm(List<State> hm) {
        this.hm = hm;
    }

    public List<HillClimb> getDs() {
        return ds;
    }

    public void setDs(List<HillClimb> ds) {
        this.ds = ds;
    }

    public List<RoadState> getRoadStates() {
        return roadStates;
    }

    public void setRoadStates(List<RoadState> roadStates) {
        this.roadStates = roadStates;
    }

    public Stack<State> getMOs() {
        return MOs;
    }

    public void setMOs(Stack<State> MOs) {
        this.MOs = MOs;
    }
    
    //chạy từng bước trong thuật toán
    public void StepHillClimb(){
        if(!isEnds){
            this.isRunning = true;
        if(MOs==null||MOs.isEmpty()){
            MOs.add(start);
            
            HillClimb ds= new HillClimb();
            ds.setMO(MOs.peek());
            this.ds.add(ds);
        }
        
        if(MOs!=null&&!MOs.isEmpty()){
            HillClimb bc = new HillClimb();
            //Lấy dỉnh đầu stack MOs và loại nó khỏi ds
            bc.setS(MOs.pop());
            List<State> dongt = this.ds.get(ds.size()-1).getDONG();
            bc.setDONG(dongt);
            //System.out.println("dong  "+bc.getDONG());
            if(roadsearch.contains(bc.getS())){
                
            }else{
                this.roadsearch.add(bc.getS());
            }
            
            if (checkinList(bc.getS(), ends)) {
                this.ds.add(bc);
                this.isEnds = true;
                this.isRunning = false;
                return;
            }
            
            bc.setChildS(roadStates, hm, ends);
            List<State> tg = new ArrayList<>();
            //
            if(bc.getChildS()!=null||!bc.getChildS().isEmpty()){
                for(int i=bc.getChildS().size()-1 ; i>=0 ; i--){
                    if(!checkinList(bc.getChildS().get(i), bc.getDONG())){
                        tg.add(bc.getChildS().get(i));
                        //MOs.add(bc.getChildS().get(i));
                    }
                }
                
                if(tg==null||tg.isEmpty()){
                    roadsearch.remove(bc.getS());
                    if(roadsearch.isEmpty()||roadsearch==null){
                        
                    }else{
                        MOs.add(roadsearch.get(roadsearch.size()-1));
                    }
                    
                    //quay lui
                }else{
                    //xóa đi đỉnh nào dã tồn tại trong stack rồi nạp lại vào stack MOs
                    loadMOs(tg);
                    bc.setMO(MOs.peek());
                    
                }
  
            }else{
                if(MOs==null||MOs.isEmpty()){
                    //thoát chương  trình
                }else{
                    //quay lui
                    roadsearch.remove(bc.getS());
                    MOs.add(roadsearch.get(roadsearch.size()-1));
                }
                
            }
            this.ds.add(bc);
        }else{
            this.isEnds = true;
            this.isRunning = false;
            this.roadsearch.removeAll(roadsearch);
            return;
        }
        
        }
    }
    //chạy toàn bộ thuật toán
    public void AllHillClimb(){
        if(!isEnds){
            this.isRunning = true;
        if(MOs==null||MOs.isEmpty()){
            MOs.add(start);
            
            HillClimb ds= new HillClimb();
            ds.setMO(MOs.peek());
            this.ds.add(ds);
        }
        
        while(MOs!=null&&!MOs.isEmpty()){
            HillClimb bc = new HillClimb();
            //Lấy dỉnh đầu stack MOs và loại nó khỏi ds
            bc.setS(MOs.pop());
            List<State> dongt = this.ds.get(ds.size()-1).getDONG();
            bc.setDONG(dongt);
            //System.out.println("dong  "+bc.getDONG());
            if(roadsearch.contains(bc.getS())){
                
            }else{
                this.roadsearch.add(bc.getS());
            }
            
            if (checkinList(bc.getS(), ends)) {
                this.ds.add(bc);
                this.isEnds = true;
                this.isRunning = false;
                return;
            }
            
            bc.setChildS(roadStates, hm, ends);
            List<State> tg = new ArrayList<>();
            //
            if(bc.getChildS()!=null||!bc.getChildS().isEmpty()){
                for(int i=bc.getChildS().size()-1 ; i>=0 ; i--){
                    if(!checkinList(bc.getChildS().get(i), bc.getDONG())){
                        tg.add(bc.getChildS().get(i));
                        //MOs.add(bc.getChildS().get(i));
                    }
                }
                
                if(tg==null||tg.isEmpty()){
                    roadsearch.remove(bc.getS());
                    if(roadsearch.isEmpty()||roadsearch==null){
                        
                    }else{
                        MOs.add(roadsearch.get(roadsearch.size()-1));
                    }
                    
                    //xét th road seach = null
                    //quay lui
                }else{
                    //xóa đi đỉnh nào dã tồn tại trong stack rồi nạp lại vào stack MOs
                    loadMOs(tg);
                    bc.setMO(MOs.peek());
                    
                }
  
            }else{
                if(MOs==null||MOs.isEmpty()){
                    //thoát chương  trình
                }else{
                    //quay lui
                    roadsearch.remove(bc.getS());
                    MOs.add(roadsearch.get(roadsearch.size()-1));
                }
                
            }
            this.ds.add(bc);
        }
        this.isEnds = true;
        this.isRunning = false;
        this.roadsearch=null;
        return;
        }
    }
    public String EndsString(){
        String t ="";
        if(ends.isEmpty()||ends==null){
            
        }else{
            for(int i=0;i<ends.size();i++){
                if(i==0){
                    t = t+ends.get(i).getNameState();
                }else{
                    t= t+" , "+ends.get(i).getNameState();
                }
            }
        }
        return t;
    }
    public String startString(){
        String t = "";
        if(start==null||start.getNameState()==null){
            
        }else{
            t = t+start.getNameState();
        }
        return t;
    }
    private void loadMOs(List<State> ds){
        if(MOs==null||MOs.isEmpty()){
            MOs = new Stack<>();
        }else{
            for(int i=0;i<ds.size();i++){
                int index =-1;
                for(int j=0;j<MOs.size();j++){
                    if(ds.get(i).getNameState().compareTo(MOs.get(j).getNameState())==0){
                        index = j;
                    }
                }
                
                if(index<0){
                    
                }else{
                    MOs.remove(index);
                }
            }
        }
        for(int i=0;i<ds.size();i++){
                MOs.add(ds.get(i));
            }
    }
    //chuyển đường đi về dạng chuỗi
    public String roadString(){
        String t="";
        for (int i = 0; i < roadsearch.size(); i++) {
            if(i==0){
                t=t + roadsearch.get(i).getNameState();
            }else{
                t=t + " -> "+roadsearch.get(i).getNameState();
            }
        }
        return t;
    }
    //kiểm tra đỉnh s có thuộc tập ds
    public boolean checkinList(State s,List<State> ds){
        if(ds==null||ds.isEmpty()){
            return false;
        }else{
            for (int i = 0; i < ds.size(); i++) {
                if(s.getNameState().compareTo(ds.get(i).getNameState())==0){
                    return true;
                }
            }
        }
        return false;
    }
    //trả về chỉ số của s trong mảng
    public int getindexList(State s,List<State> ds){
        int index =-1;
        if(ds==null||ds.isEmpty()){
            
        }else{
            for (int i = 0; i < ds.size(); i++) {
                if(s.getNameState().compareTo(ds.get(i).getNameState())==0){
                    index = i;
                }
            }
        }
        return index;
    }
    //reset toàn bộ bài toán
    public void resetall(){
        start = new State();
        ends.removeAll(ends);
        hm.removeAll(hm);
        if(roadsearch==null||roadsearch.isEmpty()){
            
        }else{
            roadsearch.removeAll(roadsearch);
        }
        
        ds.removeAll(ds);
        roadsearch.removeAll(roadsearch);
        MOs.removeAllElements();
        this.isRunning =false;
        this.isEnds = false;
    }
    public void restartall(){
        ds.removeAll(ds);
        if(roadsearch==null||roadsearch.isEmpty()){
            
        }else{
            roadsearch.removeAll(roadsearch);
        }
        
        MOs.removeAllElements();
        this.isRunning =false;
        this.isEnds = false;
    }
    public void fixState(int index,State fix){
        State s =  this.hm.get(index);
        hm.set(index, fix);
        for(int i=0;i<roadStates.size();i++){
            if(roadStates.get(i).getStart().getNameState().compareTo(s.getNameState())==0){
                roadStates.get(i).setStart(fix);
            }
            if(roadStates.get(i).getEnd().getNameState().compareTo(s.getNameState())==0){
                roadStates.get(i).setEnd(fix);
            }
        }
        if(isStart(s)){
            start=fix;
        }
        for( int i=0;i<ends.size();i++){
            if(ends.get(i).getNameState().compareTo(s.getNameState())==0){
                ends.set(i, fix);
            }
        }
    }
    public boolean isStart(State s){
        if(start.getNameState()==null){
            return false;
        }else{
            if(start.getNameState().compareTo(s.getNameState())==0){
                return true;
            }
        }
        return false;
    }
    public void addState(State s){
        this.hm.add(s);
    }
    public void removeState(State s){
        this.hm.remove(s);
    }
    public void removeState(int index){
        State s = this.hm.get(index);
        this.hm.remove(index);
        int i=0;
        while(i<roadStates.size()){
            if(roadStates.get(i).getStart().getNameState().compareTo(s.getNameState())==0||
                    roadStates.get(i).getEnd().getNameState().compareTo(s.getNameState())==0){
                roadStates.remove(i);
            }else{
                i++;
            }
        }
        if(isStart(s)){
            start= new State();
        }else{
            
        }
        for(int j=0;j<ends.size();j++){
            if(ends.get(j).getNameState().compareTo(s.getNameState())==0){
                ends.remove(j);
                break;
            }
        }
    }
    public void addRoad(RoadState rs){
        this.roadStates.add(rs);
    }
    public void removeRoad(State start,State end){
        if(roadStates.isEmpty()||roadStates==null){
            
        }else{
            for(int i=0;i<roadStates.size();i++){
                if(roadStates.get(i).getStart().getNameState().compareTo(start.getNameState())==0&&
                        roadStates.get(i).getEnd().getNameState().compareTo(end.getNameState())==0){
                    roadStates.remove(i);
                    break;
                }
            }
        }
    }
    public void fixRoad(State start,State end,int tt){
        if(roadStates.isEmpty()||roadStates==null){
            
        }else{
            for(int i=0;i<roadStates.size();i++){
                if(roadStates.get(i).getStart().getNameState().compareTo(start.getNameState())==0&&
                        roadStates.get(i).getEnd().getNameState().compareTo(end.getNameState())==0){
                    roadStates.get(i).setCost(tt);
                }
            }
        }
    }
    public void addEnds(State s){
        this.ends.add(s);
    }
    public void setStartEnds(int index,int kt){
        switch (kt){
            case 2:
                if(checkinList(hm.get(index), ends)){
                    
                }else{
                    if(isStart(hm.get(index))){
                        start = new State();
                        ends.add(hm.get(index));
                    }else{
                        ends.add(hm.get(index));
                    }
                    
                }
                break;
            case 3:
                if(isStart(hm.get(index))){
                    
                }else{
                    if(checkinList(hm.get(index), ends)){
                        ends.remove(hm.get(index));
                        start = hm.get(index);
                    }else{
                        start = hm.get(index);
                    }
                    
                }
                break;
            default:
                break;
        }
    }
    public int getindexStart(int index){
        int dex =-1;
        State s = roadStates.get(index).getStart();
        for(int i=0;i<hm.size();i++){
            if(hm.get(i).getNameState().compareTo(s.getNameState())==0){
                dex= i;
            }
        }
        return dex;
    }
    public int getindexEnd(int index){
        int dex =-1;
        State s = roadStates.get(index).getEnd();
        for(int i=0;i<hm.size();i++){
            if(hm.get(i).getNameState().compareTo(s.getNameState())==0){
                dex= i;
            }
        }
        return dex;
    } 
    public static void main(String[] args) {
        Stack<String> ds = new Stack<>();
        ds.add("A");
        ds.add("B");
        ds.add("C");
        ds.add("D");
        System.out.println("Stack:" + ds);
        System.out.println("top: "+ds.peek());
        //System.out.println("Stack:" + ds);
        //System.out.println("top: "+ds.pop());
        //System.out.println("Stack:" + ds);
        //System.out.println("Search D:"+ds.search("D"));
        List<String> ds1 = new ArrayList<>();
        ds1.add("A1");
        ds1.add("A2");
        ds1.add("A3");
        ds1.add("A4");
        for(int i = ds1.size()-1 ;i>=0 ; i--){
            ds.add(ds1.get(i));
        }
        //ds.add(ds1.get(3));
        System.out.println("Stack:" + ds);
        System.out.println("top: "+ds.peek());        
    }
}
