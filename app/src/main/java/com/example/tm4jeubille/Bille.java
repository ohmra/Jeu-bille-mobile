package com.example.tm4jeubille;

import android.graphics.Color;

public class Bille extends Element{

    public Bille(float x, float y, float radius){
        super(x, y, radius + 10f, Color.BLUE);
    }

    public boolean setCoord(float x, float y, float[] dimension){
        float[] coord = super.getCoord();
        float newX = coord[0] + x;
        float newY = coord[1] + y;
        if(newX>0 && newX<dimension[0])
            coord[0] = newX;
        if(newY>0 && newY<dimension[1])
            coord[1] = newY;
        return false;
    }
}
