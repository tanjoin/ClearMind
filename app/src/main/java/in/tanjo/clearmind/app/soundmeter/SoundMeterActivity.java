package in.tanjo.clearmind.app.soundmeter;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import in.tanjo.clearmind.Constants;
import in.tanjo.clearmind.R;

public class SoundMeterActivity extends Activity {

    private final double REFERENCEAMP = 0.1;
    private final int INTERVAL_PERIOD = 100;

    private boolean mIsRecording = false;

    private LinearLayout mMainLinearLayout;
    private TextView mTextView;

    private Handler mHandler = new Handler();
    private MediaRecorder mMediaRecorder;
    private Timer mTimer = new Timer();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear_mind_soundmeter_activity);
        initViews();
        bindViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mIsRecording) {
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (!mIsRecording) {
                        return;
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            double value = getPowerDb();
                            BigDecimal bi = new BigDecimal(value);
                            float outputValue = bi.setScale(
                                    1,
                                    BigDecimal.ROUND_HALF_UP).floatValue();
                            // TODO:背景の色変更
                            mMainLinearLayout.setBackgroundColor(
                                    getNoiseParamColor(outputValue));
                            mTextView.setText(
                                    Float.toString(outputValue) + "dB");
                        }
                    });
                }
            }, 0, INTERVAL_PERIOD);
        }
    }

    private void initViews() {
        mMainLinearLayout = (LinearLayout) findViewById(
                R.id.clear_mind_soundmeter_activity_main_linearlayout);
        mTextView = (TextView) findViewById(
                R.id.clear_mind_soundmeter_activity_show_db_textview);
    }

    private void bindViews() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mMediaRecorder.setOutputFile("/dev/null");
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            finish();
            e.printStackTrace();
        } catch (IOException e) {
            finish();
            e.printStackTrace();
        }
        mMainLinearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mIsRecording) {
                    Log.i("SoundMeter", "stop");
                    mMediaRecorder.stop();
                    mMediaRecorder.setAudioSource(
                            MediaRecorder.AudioSource.DEFAULT);
                    mMediaRecorder.setOutputFormat(
                            MediaRecorder.OutputFormat.THREE_GPP);
                    mMediaRecorder.setAudioEncoder(
                            MediaRecorder.AudioEncoder.AMR_NB);
                    mMediaRecorder.setOutputFile("/dev/null");
                    try {
                        mMediaRecorder.prepare();
                    } catch (IllegalStateException e) {
                        finish();
                        e.printStackTrace();
                    } catch (IOException e) {
                        finish();
                        e.printStackTrace();
                    }
                    mIsRecording = false;
                } else {
                    Log.i("SoundMeter", "start");
                    mMediaRecorder.start();
                    mIsRecording = true;
                }
            }
        });
    }

    private double getAmplitude() {
        if (mMediaRecorder != null) {
            return mMediaRecorder.getMaxAmplitude();
        } else {
            return 0.0;
        }
    }

    private double getPowerDb() {
        double value = getAmplitude();
        double logvalue = Math.log10(value / REFERENCEAMP);
        if (value > 0.0) {
            return 20.0 * logvalue;
        } else {
            return 0.0;
        }
    }

    private int getNoiseParamColor(final float value) {
        int result = Color.BLACK;
        if (value > Constants.SOUND_METER_OUTDOOR_BACKGROUND_BLUE_VALUE) {
            result = Color.argb(0x88, 0x00, 0x00, 0xFF);
        }
        if (value > Constants.SOUND_METER_OUTDOOR_BACKGROUND_GREEN_VALUE) {
            result = Color.argb(0x88, 0x00, 0xFF, 0x00);
        }
        if (value > Constants.SOUND_METER_OUTDOOR_BACKGROUND_YELLOW_VALUE) {
            result = Color.argb(0x88, 0xFF, 0xFF, 0x00);
        }
        if (value > Constants.SOUND_METER_OUTDOOR_BACKGROUND_RED_VALUE) {
            result = Color.argb(0x88, 0xFF, 0x00, 0x00);
        }
        return result;
    }
}
