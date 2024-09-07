package com.example.tm4jeubille;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends View {
    private Bille bille;
    private Objectif objectif;
    private List<Obstacle> obstacles;
    private float[] dimensions;
    private long time;
    private float[] cercle; //0 x, 1 y, 2 radius
    private Paint paint = new Paint();

    public Terrain(Context context){
        super(context);
        bille = null;
        objectif = null;
        obstacles = new ArrayList<Obstacle>();
        dimensions = new float[2];
        cercle = new float[3];
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        dimensions[0] = (float) this.getWidth();
        dimensions[1] = (float) getHeight();
        if(bille != null) {
            float[] coordBille = bille.getCoord();
            canvas.drawCircle(coordBille[0], coordBille[1], bille.getRadius(), bille.getPaint());
        }
        if(objectif != null) {
            float[] coordObj = objectif.getCoord();
            canvas.drawCircle(coordObj[0], coordObj[1], objectif.getRadius(), objectif.getPaint());
        }
        for(Obstacle o : obstacles){
            float[] coordObs = o.getCoord();
            canvas.drawCircle(coordObs[0], coordObs[1], o.getRadius(), o.getPaint());
        }
        if(cercle[2] != 0){
            canvas.drawCircle(cercle[0], cercle[1], cercle[2], paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        super.onTouchEvent(motionEvent);
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                time = motionEvent.getEventTime();
                cercle[2] = 20f;
                break;
            case MotionEvent.ACTION_MOVE:
                long end1 = motionEvent.getEventTime() - time;
                cercle[2] = 20f + 4 * (float) end1/100f;
                cercle[0] = motionEvent.getX();
                cercle[1] = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                cercle[2] = 0;
                long end = motionEvent.getEventTime() - time;
                float radius = 4 * (float) end/100f;
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if(objectif == null)
                    objectif = new Objectif(x, y, radius);
                else if(bille == null)
                    bille = new Bille(x, y, radius);
                else
                    obstacles.add(new Obstacle(x, y, radius));
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public boolean roll(float[] deplacement){
        if(bille != null) {
            bille.setCoord(deplacement[0], deplacement[1], dimensions);
            invalidate();
            if(checkPosition())
                return true;
        }
        return false;
    }

    public boolean checkPosition(){
        if(bille.chevauche(objectif)){
            bille = null;
            Toast.makeText(getContext(), "Victoire!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            for(Obstacle o : obstacles){
                if(bille.chevauche(o)) {
                    bille = null;
                    Toast.makeText(getContext(), "Défaite!", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return false;
    }
}
