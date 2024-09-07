package com.example.tm4jeubille;

import android.graphics.Color;

public class Obstacle extends Element{
    public Obstacle(float x, float y, float radius){
        super(x, y, radius + 20f, Color.RED);
    }
}
