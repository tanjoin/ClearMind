
package in.tanjo.clearmind.app.main;

import android.os.Bundle;

import in.tanjo.clearmind.app.menu.ClearMindMenuActivity;
import in.tanjo.clearmind.base.BaseActivity;

public class ClearMindMainActivity extends BaseActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchActivity(ClearMindMenuActivity.class);
    }
}
