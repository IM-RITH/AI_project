/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.MyHillClimb;
import Model.RoadState;
import Model.State;
import View.ViewHillClimb;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.util.List;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author minh0
 */
class Point{
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
}
//Lóp vẽ quan hệ
class LineDES{
    private ZEllipse start;
    private ZEllipse end;
    private RoadState load;
    private float xs;//tọa độ điểm giao thuận lợi nhất trong nút start
    private float ys;
    private float x;//tọa độ điểm giao thuận lợi nhất trong nút end
    private float y;

    public RoadState getLoad() {
        return load;
    }

    public void setLoad(RoadState load) {
        this.load = load;
    }

    
    
    public LineDES(ZEllipse start, ZEllipse end,RoadState load) {
        this.start = start;
        this.end = end;
        this.load = load;
    }
    //vẽ đưởng nối
    public void artLine(Graphics2D g){
        getIntersection();
        g.setPaint(new Color(255,0,0));
        g.drawLine((int)xs , (int)ys , (int)x , (int)y);
        
        //artArrow(g);
        //g.drawLine((int)start.x + (int)start.width/2 , (int)start.y + (int)start.height/2, (int)end.x + (int)end.width/2 , (int)end.y + (int)end.height/2);
    }
    //vẽ mũi tên với điểm giao x và y đã tìm đc ở trên
    public void artArrow(Graphics2D g){
        float a,b,c1,c2;
        float h= 10; 
        float h2 = 4;
        a= x - xs;
        b= y - ys;
        c1=(float) ((h*sqrt(a*a+b*b)-a*x-b*y));
        c2=(float) (-(h*sqrt(a*a+b*b))-a*x-b*y);
//        float a1,b1,c;
//        a1= ys-y;
//        b1= x-xs;
//        c = (-xs)*(ys - y)+(ys)*(x - xs);
        float x1,y1;
        x1 = -(c1*x - c1*xs - b*xs*y + b*x*ys)/(a*x - a*xs + b*y - b*ys);
        y1 = -(c1*y + a*xs*y - c1*ys - a*x*ys)/(a*x - a*xs + b*y - b*ys);
        float x2,y2;
        x2 = -(c2*x - c2*xs - b*xs*y + b*x*ys)/(a*x - a*xs + b*y - b*ys);
        y2 = -(c2*y + a*xs*y - c2*ys - a*x*ys)/(a*x - a*xs + b*y - b*ys);
        Point g1 = new Point(x1, y1);
        Point g2 = new Point(x2, y2);
        float x3,y3,x4,y4;
        float t =(float) (2*b*c1 + 2*a*b*x1 - 2*a*a*y1)*(2*b*c1 + 2*a*b*x1 - 2*a*a*y1)- 4*(a*a + b*b)*(c1*c1 - a*a*h2*h2 + 2*a*c1*x1 + a*a*x1*x1 + a*a*y1*y1);
        float t2=((2*a*h2*(a*a+b*b))/b)*((2*a*h2*(a*a+b*b))/b);
        
        if (isInLine(new Point(xs, ys), new Point(x, y), g1)) {
            if(-0.05f<a&&a<0.05f||t<=0||t2+5f<t){
                x3=x-h2;
                x4=x+h2;
                if(end.y>start.y){
                    y3=y-h;
                    y4=y-h;
                }else{
                    y3=y+h;
                    y4=y+h;
                }
                g.drawLine((int)x,(int)y, (int) x3, (int) y3);
                g.drawLine((int)x,(int)y, (int) x4, (int) y4);
                //System.out.println("g1b"+x3+"|"+y3+"|"+x4+"|"+y4+"|a "+a+"|b "+b+"|x "+x+"|y "+y+"|x1 "+x1+"|y1 "+y1+"|t2 "+t2);
            }else{
                x3=(float) (((-c1 + ( (b*b*c1)/(a*a+b*b) ) + ( (a*b*b*x1)/(a*a+b*b ))-( (a*a*b*y1)/(a*a+b*b) )-
                    ((b*sqrt((2*b*c1 + 2*a*b*x1 - 2*a*a*y1) * (2*b*c1 + 2*a*b*x1 - 2*a*a*y1) - 4*(a*a+b*b)*(c1*c1 - a*a*h2*h2 + 2*a*c1*x1 + a*a*x1*x1 + a*a*y1*y1)))/(2*(a*a+b*b)))))/a);
                x4=(float) (( (-c1 + ((b*b*c1)/(a*a+b*b) ) + ((a*b*b*x1) / (a*a+b*b)) - ( (a*a*b*y1)/(a*a+b*b) )+
                    ((b*sqrt(( 2*b*c1 + 2*a*b*x1 - 2*a*a*y1 )*(2*b*c1 + 2*a*b*x1 - 2*a*a*y1)-4*(a*a + b*b)*( c1*c1 - a*a*h2*h2 + 2*a*c1*x1 + a*a*x1*x1 + a*a*y1*y1)))/(2*(a*a+b*b)))))/a);
            
                y3=(float) ((1/(2*(a*a+b*b))) * (-2*b*c1 - 2*a*b*x1 + 2*a*a*y1+
                    sqrt(((2*b*c1 + 2*a*b*x1 - 2*a*a*y1)*(2*b*c1 + 2*a*b*x1 - 2*a*a*y1)-4*(a*a+b*b)*(c1*c1 - a*a*h2*h2 + 2*a*c1*x1 + a*a*x1*x1 + a*a*y1*y1)))));
            
                y4=(float) ((1/(2*(a*a+b*b)))*(-2*b*c1 -2*a*b*x1 + 2*a*a*y1-
                    sqrt(((2*b*c1 + 2*a*b*x1 - 2*a*a*y1)*(2*b*c1 + 2*a*b*x1 - 2*a*a*y1)-4*(a*a+b*b)*(c1*c1 - a*a*h2*h2 + 2*a*c1*x1 + a*a*x1*x1 + a*a*y1*y1)))));
                g.drawLine((int)x,(int)y, (int) x3, (int) y3);
                g.drawLine((int)x,(int)y, (int) x4, (int) y4);
                //System.out.println("g1 "+x3+"|"+y3+"|"+x4+"|"+y4+"|a "+a+"|b "+b+"|x "+x+"|y "+y+"|x1 "+x1+"|y1 "+y1+"|t2 "+t2);
            }
                
        }else if(isInLine(new Point(xs, ys), new Point(x, y), g2)){
            if(-0.05f<a&&a<0.05f||t<=0||t2+5f<t){
                x3=x-h2;
                x4=x+h2;
                if(end.y>start.y){
                    y3=y-h;
                    y4=y-h;
                }else{
                    y3=y+h;
                    y4=y+h;
                }
                g.drawLine((int)x,(int)y, (int) x3, (int) y3);
                g.drawLine((int)x,(int)y, (int) x4, (int) y4);
            }else{
                x3=(float) (((- c2 +((b*b* c2 )/(a*a+b*b))+((a*b*b* x2 )/(a*a+b*b))-((a*a*b* y2 )/(a*a+b*b))-
                    ((b*sqrt((2*b* c2 +2*a*b* x2 -2*a*a* y2 )*(2*b* c2 +2*a*b* x2 -2*a*a* y2 )-4*(a*a+b*b)*( c2 * c2 -a*a* h2 * h2 +2*a* c2 * x2 +a*a* x2 * x2 +a*a* y2 * y2 )))/(2*(a*a+b*b)))))/a);
                x4=(float) (((- c2 +((b*b* c2 )/(a*a+b*b))+((a*b*b* x2 )/(a*a+b*b))-((a*a*b* y2 )/(a*a+b*b))+
                    ((b*sqrt((2*b* c2 +2*a*b* x2 -2*a*a* y2 )*(2*b* c2 +2*a*b* x2 -2*a*a* y2 )-4*(a*a+b*b)*( c2 * c2 -a*a*h2*h2+2*a* c2 * x2 +a*a* x2 * x2 +a*a* y2 * y2 )))/(2*(a*a+b*b)))))/a);
                y3=(float) ((1/(2*(a*a+b*b))) * (-2*b* c2 - 2*a*b* x2 + 2*a*a* y2+
                    sqrt(((2*b* c2 + 2*a*b* x2 - 2*a*a* y2)*(2*b* c2 + 2*a*b* x2 - 2*a*a* y2)-4*(a*a+b*b)*( c2 * c2 - a*a*h2*h2 + 2*a* c2 * x2 + a*a* x2 * x2  + a*a* y2 * y2 )))));
                y4=(float) ((1/(2*(a*a+b*b)))*(-2*b* c2 -2*a*b* x2 + 2*a*a* y2 -
                    sqrt(((2*b* c2 + 2*a*b* x2 - 2*a*a* y2 )*(2*b* c2  + 2*a*b* x2 - 2*a*a* y2 )-4*(a*a+b*b)*( c2 * c2  - a*a*h2*h2 + 2*a* c2 * x2  + a*a* x2 * x2 + a*a* y2 * y2 )))));
                    g.drawLine((int)x,(int)y, (int) x3, (int) y3);
                    g.drawLine((int)x,(int)y, (int) x4, (int) y4);
            }
            //System.out.println("g2: "+x3+"|"+y3+"|"+x4+"|"+y4);
        }
        
    }
    //xác định điểm giao trong nút end
    public void getIntersection(){
        Point tts = new Point((int)start.x + (int)start.width/2 , (int)start.y + (int)start.height/2);
        Point tte = new Point((int)end.x + (int)end.width/2 , (int)end.y + (int)end.height/2);
        
        Point supl = new Point(start.x , start.y);
        Point sbottoml = new Point(start.x , start.y + start.height);
        Point sbright = new Point(start.x + start.width , start.y + start.height);
        Point suright = new Point(start.x + start.width , start.y);
        
        Point eupl = new Point(end.x , end.y);
        Point eupr = new Point(end.x + end.width , end.y);
        Point ebotl = new Point(end.x , end.y + end.height);
        Point ebotr = new Point(end.x + end.width , end.y + end.height);
        
        Point gs1 = intersectiopn(tts, tte, supl, suright);
        Point gs2 = intersectiopn(tts, tte, supl, sbottoml);
        Point gs3 = intersectiopn(tts, tte, sbottoml, sbright);
        Point gs4 = intersectiopn(tts, tte, suright, sbright);
        
        Point ge1 =intersectiopn(tts, tte, eupl, eupr);
        Point ge2 =intersectiopn(tts, tte, eupl, ebotl);
        Point ge3 =intersectiopn(tts, tte, ebotl, ebotr);
        Point ge4 =intersectiopn(tts, tte, ebotr, eupr);
        
        List<Point> dss = new ArrayList<>();
        dss.add(gs1);
        dss.add(gs2);
        dss.add(gs3);
        dss.add(gs4);
        
        List<Point> dse = new ArrayList<>();
        dse.add(ge1);
        dse.add(ge2);
        dse.add(ge3);
        dse.add(ge4);
        
        //thêm điều kiện thuộc hình chữ nhật
        for (int i = 0; i < dss.size(); i++) {
            boolean intt=isInLine(tts, tte, dss.get(i));
            boolean in1=isInLine(supl, suright, dss.get(i));
            boolean in2=isInLine(supl, sbottoml, dss.get(i));
            boolean in3=isInLine(sbright, sbottoml, dss.get(i));
            boolean in4=isInLine(sbright, suright, dss.get(i));
            if(in1||in2||in3||in4){
                //intt=true;
                if (intt) {
                    xs = dss.get(i).getX();
                    ys = dss.get(i).getY();
                    //System.out.println(xs+"|s"+ys);
                }
            }
            
        }
        for (int i = 0; i < dse.size(); i++) {
            boolean intt = isInLine(tts, tte, dse.get(i));
            boolean in1 = isInLine(eupl, eupr, dse.get(i));
            boolean in2 = isInLine(eupl, ebotl, dse.get(i));
            boolean in3 = isInLine(ebotl, ebotr, dse.get(i));
            boolean in4 = isInLine(ebotr, eupr, dse.get(i));
            if(in1||in2||in3||in4){
                //intt=true;
                if (intt) {
                    x = dse.get(i).getX();
                    y = dse.get(i).getY();
                    //System.out.println(x+"|e"+y);
                }
                    //break;
            }
                
        }
    }
    //Vẽ trọng số trong đồ thị có hướng
    public void artWeightd(Graphics2D g){
        float a,b,c1,c2;
        float h= 20;
        a= x - xs;
        b= y - ys;
        c1=(float) ((h*sqrt(a*a+b*b)-a*x-b*y));
        c2=(float) (-(h*sqrt(a*a+b*b))-a*x-b*y);
        float x1,y1;
        x1 = -(c1*x - c1*xs - b*xs*y + b*x*ys)/(a*x - a*xs + b*y - b*ys);
        y1 = -(c1*y + a*xs*y - c1*ys - a*x*ys)/(a*x - a*xs + b*y - b*ys);
        float x2,y2;
        x2 = -(c2*x - c2*xs - b*xs*y + b*x*ys)/(a*x - a*xs + b*y - b*ys);
        y2 = -(c2*y + a*xs*y - c2*ys - a*x*ys)/(a*x - a*xs + b*y - b*ys);
        Point g1 = new Point(x1, y1);
        Point g2 = new Point(x2, y2);
        String t1="";
        if(load.getCost()<=0){
            
        }else{
            t1 = String.valueOf(load.getCost());
        }
        if (isInLine(new Point(xs, ys), new Point(x, y), g1)){
            g.setPaint(new Color(0,0,0));
            g.drawString(t1, x1, y1);
            
        }else if(isInLine(new Point(xs, ys), new Point(x, y), g2)){
            g.setPaint(new Color(0,0,0));
            g.drawString(t1, x2, y2);
        }
    }
    //Vẽ trọng số trong đồ thị vô hướng 
    public void artWeightnd(Graphics2D g){
        float x1 = (x+xs)/2;
        float y1 = (y+ys)/2;
        String t1="";
        if(load.getCost()<=0){
            
        }else{
            t1 = String.valueOf(load.getCost());
        }
        g.setPaint(new Color(0,0,0));
        g.drawString(t1, x1, y1);
    }
    //lấy điểm giao giữa hai đoạn thẳng
    public Point intersectiopn(Point d11,Point d12,Point d21,Point d22){
        float x1 = d11.getX();
        float y1 = d11.getY();
        float x2 = d12.getX();
        float y2 = d12.getY();
        float x3 = d21.getX();
        float y3 = d21.getY();
        float x4 = d22.getX();
        float y4 = d22.getY();
        float xg;
        float yg;
        if((-x3*y1 + x4*y1 + x3*y2 - x4*y2 + x1*y3 - x2*y3 - x1*y4 + x2*y4)==0){
            xg = 0;
            yg = 0;
        }else{
            xg =-((x2*x3*y1 - x2*x4*y1 - x1*x3*y2 + x1*x4*y2 - x1*x4*y3 + x2*x4*y3 + x1*x3*y4 - x2*x3*y4)
                /(-x3*y1 + x4*y1 + x3*y2 - x4*y2 + x1*y3 - x2*y3 - x1*y4 + x2*y4));
            yg =-((-x2*y1*y3 + x4*y1*y3 + x1*y2*y3 - x4*y2*y3 + x2*y1*y4 - x3*y1*y4 - x1*y2*y4 + x3*y2*y4)
                /(x3*y1 - x4*y1 - x3*y2 + x4*y2 - x1*y3 + x2*y3 + x1*y4 - x2*y4));
        }
        //System.out.println(xg+"|giao"+yg);
        return new Point(xg, yg);
    }
    //Kiểm tra điểm C có thuộc dường thảng ab ko?
    public boolean isInLine(Point a,Point b,Point c){
        float A = (float)(a.getY() - b.getY());
	float B = (float)(b.getX() - a.getX());
	float C = (float)(a.getX()* b.getY() - b.getX() * a.getY());
	float xmax = a.getX() > b.getX() ? a.getX() : b.getX();
	float ymax = a.getY() > b.getY() ? a.getY() : b.getY();
	float xmin = a.getX() > b.getX() ? b.getX() : a.getX();
	float ymin = a.getY() > b.getY() ? b.getY() : a.getY();
	//float d = (float) ((A * (float)c.getX() + B * (float)c.getY() + C) / sqrt(Double.valueOf((A * A + B * B))));
        if ( ((int)xmin <= (int)c.getX() && (int)c.getX() <= (int)xmax) && ((int)ymin <= (int)c.getY() && (int)c.getY() <= (int)ymax)){
            return true;
        }
        return false;
    }
    //kiểm tra xem có nhấn vào Đoạn thẳng ko
    public boolean isHitinline(float xs,float ys){
        Point a = new Point(x, y);
        Point b = new Point(this.xs, this.ys);
        Point c = new Point(xs, ys);
        float A = (float)(a.getY() - b.getY());
	float B = (float)(b.getX() - a.getX());
	float C = (float)(a.getX()* b.getY() - b.getX() * a.getY());
	float xmax = a.getX() > b.getX() ? a.getX() : b.getX();
	float ymax = a.getY() > b.getY() ? a.getY() : b.getY();
	float xmin = a.getX() > b.getX() ? b.getX() : a.getX();
	float ymin = a.getY() > b.getY() ? b.getY() : a.getY();
	float d = (float) (abs(A * (float)c.getX() + B * (float)c.getY() + C)/sqrt(Double.valueOf(((A * A) + (B * B)))));
        
        if ( d <= 3&&((int)xmin <= (int)c.getX() && (int)c.getX() <= (int)xmax) && ((int)ymin <= (int)c.getY() && (int)c.getY() <= (int)ymax)){
            //System.out.println("d = "+ d+" A = "+ A+" B = "+ d);
            return true;
        }
        return false;
    }
}

//Lóp vẽ các nút
class ZEllipse extends Ellipse2D.Float {
        //private String text;
        private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
        
        public ZEllipse(float x, float y, float width, float height,State state) {
            setFrame(x, y, width, height);
            this.state = state;
        }
        
        public void addText(Graphics2D g){
            g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
            //g.setColor(Color.red);
            g.setPaint(new Color(255,0,0));
            String text = this.state.getNameState()+"["+this.state.getNumofState()+"]";
            g.drawString(text, x + width/4,y+height/2);
        }
        
        public boolean isHit(float x, float y) {
             
            return getBounds2D().contains(x, y);
        }
 
        public void addX(float x) {
             
            this.x += x;
        }
 
        public void addY(float y) {
             
            this.y += y;
        }
 
        public void addWidth(float w) {
             
            this.width += w;
        }
 
        public void addHeight(float h) {
             
            this.height += h;
        }
    }
//màn hình vẽ 
class Surface extends JPanel {
    private Graphics2D g2d;
    
    private List<ZEllipse> ds;
    private int clicking;
    private int moving;
    
    private List<LineDES> dsqh;
    private int islinedes;
    
    private ViewHillClimb mainview;
    private MyHillClimb data;
    
    public ViewHillClimb getMainview() {
        return mainview;
    }
    
    //s là state cần sửa
    //fix là state muốn sửa
    public void fixState(State s,State fix){
        for(int i=0;i<ds.size();i++){
            if(ds.get(i).getState().getNameState().compareTo(s.getNameState())==0){
                ds.get(i).setState(fix);
                break;
            }
                
        }
    }
    //index là vị trí cần sửa
    public void fixStateint(int index,State fix){
        State s = ds.get(index).getState();
        
                ds.get(index).setState(fix);
                for(int i=0;i<dsqh.size();i++){
                    if(dsqh.get(i).getLoad().getStart().getNameState().compareTo(s.getNameState())==0){
                        dsqh.get(i).getLoad().setStart(fix);
                    }
                    if(dsqh.get(i).getLoad().getEnd().getNameState().compareTo(s.getNameState())==0){
                        dsqh.get(i).getLoad().setEnd(fix);
                    }
                }
    }
    public void removeState(State s){
        if(ds==null||ds.isEmpty()){
            
        }else{
            int ck = -1;
            for (int i = 0; i < ds.size(); i++) {
                if(ds.get(i).getState().getNameState().compareTo(s.getNameState())==0){
                    ck = i;
                    break;
                }
            }
            if(ck<0){
                
            }else{
                ds.remove(ck);
                int i=0;
                while (i<dsqh.size()) {            
                    if(dsqh.get(i).getLoad().getStart().getNameState().compareTo(s.getNameState())==0
                        ||dsqh.get(i).getLoad().getEnd().getNameState().compareTo(s.getNameState())==0){
                        dsqh.remove(i);
                    }else{
                    i++;
                    }
                }
            }
        }
        
    }
    
    public void removeState(int index){
        State tg;
        if(ds==null||ds.isEmpty()||index < 0||index>ds.size()-1){
            
        }else{
            tg = ds.get(index).getState();
            ds.remove(index);
            int i=0;
            while (i<dsqh.size()) {            
                if(dsqh.get(i).getLoad().getStart().getNameState().compareTo(tg.getNameState())==0
                    ||dsqh.get(i).getLoad().getEnd().getNameState().compareTo(tg.getNameState())==0){
                    dsqh.remove(i);
                }else{
                    i++;
                }
            }
        }
    }
    
    
    public void fixRoadState(State start,State end,int tt){
        if(dsqh==null||dsqh.isEmpty()){
            
        }else{
            for(int i=0;i<dsqh.size();i++){
                if(dsqh.get(i).getLoad().getStart().getNameState().compareTo(start.getNameState())==0
                    &&dsqh.get(i).getLoad().getEnd().getNameState().compareTo(end.getNameState())==0){
                    
                    dsqh.get(i).getLoad().setCost(tt);
                }
            }
        }
    }
    public void removeRoadState(State start,State end){
        if(dsqh==null||dsqh.isEmpty()){
            
        }else{
            for(int i=0;i<dsqh.size();i++){
                if(dsqh.get(i).getLoad().getStart().getNameState().compareTo(start.getNameState())==0
                    &&dsqh.get(i).getLoad().getEnd().getNameState().compareTo(end.getNameState())==0){
                    
                    dsqh.remove(i);
                    break;
                }
            }
        }
    }
    
    public int getClicking() {
        return clicking;
    }

    public void setClicking(int clicking) {
        this.clicking = clicking;
    }
    
    public void setMainview(ViewHillClimb mainview) {
        this.mainview = mainview;
    }

    public List<ZEllipse> getDs() {
        return ds;
    }

    public void setDs(List<ZEllipse> ds) {
        this.ds = ds;
    }

    public List<LineDES> getDsqh() {
        return dsqh;
    }

    public void setDsqh(List<LineDES> dsqh) {
        this.dsqh = dsqh;
    }
    
    public void addDes(ZEllipse dau,ZEllipse dich,RoadState road){
        LineDES ld = new LineDES(dau, dich,road);
        dsqh.add(ld);
    }
    
    public int checkinlistDES(int x,int y){
        int index=-1;
        for(int i=0;i<dsqh.size();i++){
            if(dsqh.get(i).isHitinline(x, y)){
                index =i;
            }
        }
        return index;
    }
    
    public Surface(ViewHillClimb m) {
        initUI();
        this.mainview = m;
        setBackground(Color.WHITE);
    }
     
    private void initUI() {
         
        MovingAdapter ma = new MovingAdapter();
 
        addMouseMotionListener(ma);
        addMouseListener(ma);
        addMouseWheelListener(new ScaleHandler());
        ds = new ArrayList<>();
        dsqh = new ArrayList<>();
    }
    //phương thức vẽ màn hình
    private void doDrawing(Graphics g) {
        g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
   
          if(ds==null||ds.isEmpty()){
              
          }else{
              for(int i=0;i<ds.size();i++){
                  if(mainview.getCore().checkinList(ds.get(i).getState(), mainview.getCore().getEnds())){
                      if(i==clicking){
                          g2d.setPaint(new Color(191,193,8));
                      }else{
                          g2d.setPaint(new Color(247,250,8));
                      }
                  }else if(mainview.getCore().isStart(ds.get(i).getState())){
                      if(i==clicking){
                          g2d.setPaint(new Color(245, 144, 3));
                      }else{
                          g2d.setPaint(new Color(250, 183, 85));
                      }
                  }else{
                      if(mainview.getCore().checkinList(ds.get(i).getState(), mainview.getCore().getRoadsearch())){
                        if(i==clicking){
                          g2d.setPaint(new Color(18, 111, 155));
                        }else{
                          g2d.setPaint(new Color(40, 159, 219));
                        }
                      }else{
                        if(i==clicking){
                          g2d.setPaint(new Color(5, 160, 26));
                        }else{
                          g2d.setPaint(new Color(3, 245, 36));
                        }
                      }
                      
                    
                  }
                  g2d.fill(ds.get(i));
                  ds.get(i).addText(g2d);
              }
          }
          
          if(dsqh==null||dsqh.isEmpty()){

          }else{
              
              for(int i=0;i<dsqh.size();i++){
                  dsqh.get(i).artLine(g2d);
                  dsqh.get(i).artArrow(g2d);
                  dsqh.get(i).artWeightd(g2d);
              }
          }
    }
 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);        
    }
    
    public void resetView(){
        ds.removeAll(ds);
        dsqh.removeAll(dsqh);
        
    }
    //định nghĩa lóp hành động nhấn chuột và kéo chuột
    class MovingAdapter extends MouseAdapter {
 
        private int x;
        private int y;

        @Override
        public void mouseMoved(MouseEvent e) {
            int x1 = e.getX();
            int y1 = e.getY();
            boolean checkS = false;
            int rec =-1;
            for(int i=0;i<ds.size();i++){
                if(ds.get(i).isHit(x1, y1)){
                    checkS = true;
                    rec = i;
                    break;
                }
            }
            if(checkS){
                setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }else{
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                int isline =-1;
                boolean checkL = false;
                for(int i=0;i<dsqh.size();i++){
                    if(dsqh.get(i).isHitinline(x1, y1)){
                        checkL = true;
                        isline = i;
                        break;
                    }
                }
                if(checkL){
                    //System.out.println("bl");
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }else{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            
        }
        
        
        @Override
        public void mouseClicked(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            boolean check = false;
            int cli=-1;
            for(int i=0;i<ds.size();i++){
                if(ds.get(i).isHit(x, y)){
                    check = true;
                    cli = i;
                    break;
                }
            }
            if (check) {
                if(e.getClickCount()==2){
                    mainview.loadinforState(ds.get(cli).getState());
                    clicking=cli;
                    if(mainview.getCore().checkinList(ds.get(clicking).getState(), mainview.getCore().getEnds())){
                        mainview.setckbBdKt(2);
                    }else if(mainview.getCore().isStart(ds.get(clicking).getState())){
                        mainview.setckbBdKt(3);
                    }else{
                        mainview.setckbBdKt(6);
                    }
                    repaint();
                }
            }else if(checkinlistDES(x, y)<0){
                int ckb = mainview.loadinforbdkt();
                if (mainview.getTenDinh().compareTo("")==0||mainview.getTenDinh()==null||ckb==1) {
                    JOptionPane.showMessageDialog(mainview,"Đỉnh không hợp lệ");
                }else{
                    data = mainview.getCore();
                    State s = new State(mainview.getTenDinh(), mainview.getTrangThai());
                
                    if(data.checkinList(s, data.getHm())||mainview.getCore().isIsRunning()){
                    
                    }else{
                        ZEllipse nut = new ZEllipse(x, y, 60, 60, s);
                        ds.add(nut);
                        mainview.getCore().addState(s);
                        if(ckb==3){
                            if(mainview.getCore().getStarts()==null||mainview.getCore().getStarts().getNameState()==null||mainview.getCore().getStarts().getNameState().compareTo("")==0){
                                mainview.getCore().setStarts(s);
                            }else{
                                JOptionPane.showMessageDialog(mainview,"Đỉnh bắt đầu đã tồn tại!");
                            }
                            
                        }else if(ckb==2){
                            mainview.getCore().addEnds(s);
                        }
                        clicking =-1;
                        g2d.setPaint(new Color(0, 200, 0));
                        mainview.loadCB();
                        mainview.setlbText();
                        repaint();
                    }
                }
            }else{
                islinedes = checkinlistDES(x, y);
                mainview.setRoad(islinedes);
            }

        }
        
        @Override
        public void mousePressed(MouseEvent e) {
             
            x = e.getX();
            y = e.getY();
            for(int i=0;i<ds.size();i++){
                if(ds.get(i).isHit(x, y)){
                    moving = i;
                    break;
                }
            }
        }
 
        @Override
        public void mouseDragged(MouseEvent e) {
 
            doMove(e);
        }   
         
        private void doMove( MouseEvent e) {
             
            int dx = e.getX() - x;
            int dy = e.getY() - y;
            if(ds==null||ds.isEmpty()){
                
            }else{
                
            if(ds.get(moving).isHit(x,y)){
                if(e.getX()<0||e.getY()<0){
                  
                }
                else{
                    ds.get(moving).addX(dx);
                    ds.get(moving).addY(dy);
                    repaint();
                }
            }
            x += dx;
            y += dy;
            }
        }
    }
    //định nghĩa hành động con lăn chuột
    class ScaleHandler implements MouseWheelListener {
         
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
 
            doScale(e);
        }
        //ĐỊnh nghĩa con lăn chuột
        private void doScale(MouseWheelEvent e) {
             
            int x = e.getX();
            int y = e.getY();
            if(clicking<0){
            }else{
                if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                if(ds.get(clicking).isHit(x, y)){
                    float amount = e.getWheelRotation()*3f;
                    ds.get(clicking).addWidth(amount);
                    ds.get(clicking).addHeight(amount);
                    repaint();
                }
            }
            
            
            
// 
//                if (zrect.isHit(x, y)) {
//                     
//                    float amount =  e.getWheelRotation() * 5f;
//                    zrect.addWidth(amount);
//                    zrect.addHeight(amount);
//                    repaint();
//                }
// 
//                if (zell.isHit(x, y)) {
//                     
//                    float amount =  e.getWheelRotation() * 5f;
//                    zell.addWidth(amount);
//                    zell.addHeight(amount);
//                    repaint();
//                }
            }            
        }
    }
}
public class TTView extends JFrame{
    private void initUI() {
        add(new Surface(new ViewHillClimb()));
        setTitle("Moving and scaling");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);        
    }

    public TTView() {
        initUI();
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TTView t= new TTView();
                t.setVisible(true);
            }
        });
    }
}
