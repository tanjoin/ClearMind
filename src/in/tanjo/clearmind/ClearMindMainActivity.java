package in.tanjo.clearmind;

import android.content.Intent;
import android.os.Bundle;

import in.tanjo.clearmind.stopwatch.StopWatchActivity;

public class ClearMindMainActivity extends BaseActivity {

    private ClearMindMainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchStopWatch();
    }

    private void launchStopWatch() {
        Intent intent = new Intent(ClearMindMainActivity.this,
                                   StopWatchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
