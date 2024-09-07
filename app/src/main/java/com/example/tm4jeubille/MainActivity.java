package com.example.tm4jeubille;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView[] view = new TextView[3];
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Terrain terrain;
    private static final float g = 9.81f;
    private float difficulte;

    private SensorEventListener listener = new SensorEventListener(){
        @Override
        public void onSensorChanged(SensorEvent sensorEvent){
            float[] pos = new float[2];
            pos[0] = (float) -sensorEvent.values[0] * difficulte;
            pos[1] = (float) sensorEvent.values[1] * difficulte;
            if(terrain.roll(pos))
                recreate();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };

    //code de test de l'accelerometre
    private SensorEventListener listenerTest = new SensorEventListener(){
        @Override
        public void onSensorChanged(SensorEvent sensorEvent){
            for(int i = 0; i<3; i++){
                view[i].setText(Float.toString(sensorEvent.values[i]));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(listener, accelerometer, sensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(listener);
    }

    public void chooseDifficulty(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        CharSequence[] items = {"lent", "moyen", "rapide"};
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                switch(i){
                    case 1:
                        difficulte = 3f;
                        break;
                    case 2:
                        difficulte = 4f;
                        break;
                    default:
                        difficulte = 1f;
                        break;
                }
            }
        });
        dialogBuilder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);
        view[0] = findViewById(R.id.textView);
        view[1] = findViewById(R.id.textView2);
        view[2] = findViewById(R.id.textView3);*/
        chooseDifficulty();
        setContentView(terrain = new Terrain(getApplicationContext()));
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if((accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) == null){
            Log.v("sensor", "Il n'y a pas d'acceleromètre");
            finish();
        }
    }
}