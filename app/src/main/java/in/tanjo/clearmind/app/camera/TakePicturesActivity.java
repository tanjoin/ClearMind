package in.tanjo.clearmind.app.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceView;

import in.tanjo.clearmind.R;

public class TakePicturesActivity extends Activity {

    private Camera mCamera;
    private int mCameraId;
    private Parameters mParameters;

    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_pictures_activity);
        initViews();
        // take picture
    }

    private void initViews() {
        mSurfaceView = (SurfaceView) findViewById(
                R.id.take_pictures_activity_camera_preview);
    }
}
