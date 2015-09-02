package in.tanjo.clearmind.app.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import in.tanjo.clearmind.R;

public class AccelerometerActivity extends Activity {

    TextView mTextView;
    SensorManager mSensorManager;
    float[] mGravity = new float[3];
    float[] mLinearAcceleration = new float[3];

    private SensorEventListener mSensorEventListener =
            new SensorEventListener() {
        @Override
        public void onSensorChanged(final SensorEvent event) {
            // In this example, alpha is calculated as t / (t + dT),
            // where t is the low-pass filter's time-constant and
            // dT is the event delivery rate.

            final float alpha = 0.8f;

            // Isolate the force of gravity with the low-pass filter.
            mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
            mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
            mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

            // Remove the gravity contribution with the high-pass filter.
            mLinearAcceleration[0] = event.values[0] - mGravity[0];
            mLinearAcceleration[1] = event.values[1] - mGravity[1];
            mLinearAcceleration[2] = event.values[2] - mGravity[2];
        }

        @Override
        public void onAccuracyChanged(final Sensor sensor,
                                      final int accuracy) {

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear_mind_accelerometer_activity);
        initViews();
    }

    @Override
    protected void onDestroy() {
        mSensorManager.unregisterListener(mSensorEventListener);
        mSensorManager = null;
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindViews();
    }

    private void initViews() {
        mTextView = (TextView) findViewById(
                R.id.clear_mind_accelerometer_activity_textview_master);
    }

    private void bindViews() {
        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(
                mSensorEventListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

// method 001
//        List<Sensor> sensors =
//                mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
//        if (sensors != null && sensors.size() > 0) {
//            Sensor sensor = sensors.get(0);
//            mSensorManager.registerListener(mSensorEventListener,
//                                            sensor,
//                                            SensorManager.SENSOR_DELAY_UI);
//        } else {
//            mTextView.setText("Failure! No Accelerometer sensor.");
//        }
    }

    private void unbindViews() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(mSensorEventListener);
            mSensorManager = null;
        }
    }

}
