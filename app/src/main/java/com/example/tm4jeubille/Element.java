package com.example.tm4jeubille;

import android.graphics.Color;
import android.graphics.Paint;

public class Element {
    private float[] coord = new float[2];
    private float radius;
    private Paint paint;
    private final int DEFAULT_COLOR = Color.BLACK;

    public Element(float x, float y, float radius, int default_color){
        coord[0] = x;
        coord[1] = y;
        this.radius = radius;
        paint = new Paint();
        paint.setColor(default_color);
    }

    public boolean chevauche(Element element){
        float[] coordElement = element.getCoord();
        float xX = coord[0] - coordElement[0];
        float yY = coord[1] - coordElement[1];
        float distance = (float) Math.sqrt(xX*xX + yY*yY);
        if(distance < radius+element.getRadius())
            return true;
        return false;
    }

    public float[] getCoord(){ return this.coord; }
    public float getRadius(){ return this.radius; }
    public Paint getPaint(){ return this.paint; }
}
