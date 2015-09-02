
package in.tanjo.clearmind.app.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import in.tanjo.clearmind.R;
import in.tanjo.clearmind.app.soundmeter.SoundMeterActivity;
import in.tanjo.clearmind.app.versionviewer.VersionViewerActivity;

public class ClearMindMenuActivity extends Activity {

    private LinearLayout mMainLinearLayout;
    private MenuItem mMenuItemAppFinish;
    private MenuItem mMenuItemStartSoundMeter;
    private MenuItem mMenuItemVersionViewer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear_mind_menu_activity);
        initViews();
        bindViews();
    }

   @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.clear_mind_menu_activity_menu, menu);
       initMenus();
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
        // アプリケーションを終了させる
        case R.id.clear_mind_menu_activity_menu_item_app_finish:
            moveTaskToBack(true);
            break;
        case R.id.clear_mind_menu_activity_menu_item_start_sound_meter:
            launchSoundMeterActivity();
            break;
        case R.id.clear_mind_menu_activity_menu_item_start_version_viewer:
            launchVersionViewerActivity();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mMainLinearLayout = (LinearLayout) findViewById(
                R.id.clear_mind_menu_activity_main_linearlayout);
    }

    private void bindViews() {
        mMainLinearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                openOptionsMenu();
            }
        });
    }

    private void initMenus() {
        mMenuItemAppFinish = (MenuItem) findViewById(
                R.id.clear_mind_menu_activity_menu_item_app_finish);
        mMenuItemStartSoundMeter = (MenuItem) findViewById(
                R.id.clear_mind_menu_activity_menu_item_start_sound_meter);
        mMenuItemVersionViewer = (MenuItem) findViewById(
                R.id.clear_mind_menu_activity_menu_item_start_version_viewer);
    }

    private void launchSoundMeterActivity() {
        Intent intent = new Intent(ClearMindMenuActivity.this,
                                   SoundMeterActivity.class);
        startActivity(intent);
    }

    private void launchVersionViewerActivity() {
        Intent intent = new Intent(ClearMindMenuActivity.this,
                                   VersionViewerActivity.class);
        startActivity(intent);
    }

}
