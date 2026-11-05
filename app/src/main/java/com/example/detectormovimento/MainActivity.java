package com.example.detectormovimento;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;

    private TextView valorX, valorY, valorZ, statusSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valorX = findViewById(R.id.eixoX);
        valorY = findViewById(R.id.eixoY);
        valorZ = findViewById(R.id.eixoZ);
        statusSensor = findViewById(R.id.statusSensor);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (acelerometro == null) {
            statusSensor.setText("Este aparelho não possui acelerômetro.");
        } else {
            statusSensor.setText("Acelerômetro disponível. Movimente o celular.");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            valorX.setText(String.format(Locale.getDefault(), "%.2f m/s²", x));
            valorY.setText(String.format(Locale.getDefault(), "%.2f m/s²", y));
            valorZ.setText(String.format(Locale.getDefault(), "%.2f m/s²", z));

            String direcao;
            if (Math.abs(x) > Math.abs(y) && Math.abs(x) > 2.0f) {
                direcao = x > 0 ? "Inclinação para a direita/esquerda (eixo X)" :
                        "Inclinação no sentido oposto (eixo X)";
            } else if (Math.abs(y) > 2.0f) {
                direcao = y > 0 ? "Inclinação em um sentido (eixo Y)" :
                        "Inclinação no sentido oposto (eixo Y)";
            } else {
                direcao = "Aparelho relativamente estável";
            }

            statusSensor.setText(direcao);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
