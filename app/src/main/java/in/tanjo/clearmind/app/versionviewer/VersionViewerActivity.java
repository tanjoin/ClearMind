package in.tanjo.clearmind.app.versionviewer;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import in.tanjo.clearmind.R;

public class VersionViewerActivity extends Activity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear_mind_version_viewer_activity);
        initViews();
        bindViews();
        showBuildInfo();
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        showDisplayInfo();
    }

    private void initViews() {
        mListView = (ListView) findViewById(
                R.id.clear_mind_version_viewer_activity_listview);
    }

    private void bindViews() {
        mAdapter = new ArrayAdapter<String>(
                this, R.layout.clear_mind_version_viewer_listview_item);
        mListView.setAdapter(mAdapter);
    }

    private void showBuildInfo() {
        mAdapter.add("VERSION.SDK_INT : " +
                     String.valueOf(Build.VERSION.SDK_INT));
        mAdapter.add("VERSION.RELEASE : " + Build.VERSION.RELEASE);
        mAdapter.add("PRODUCT : " + Build.PRODUCT);
        mAdapter.add("CODENAME : " + getCodeName());
    }

    private void showDisplayInfo() {
        mAdapter.add("DISPLAY_DENSITYDPI : " + getDensityDpi());
        mAdapter.add("DISPLAY_DENSITY : " +
                     getResources().getDisplayMetrics().density);
    }

    private String getDensityDpi() {
        String showValue = null;
        switch (getResources().getDisplayMetrics().densityDpi) {
        case DisplayMetrics.DENSITY_LOW:
            showValue = "LDPI";
            break;
        case DisplayMetrics.DENSITY_MEDIUM:
            showValue = "MDPI";
            break;
        case DisplayMetrics.DENSITY_HIGH:
            showValue = "HDPI";
            break;
        case DisplayMetrics.DENSITY_TV:
            showValue = "TV";
            break;
        case DisplayMetrics.DENSITY_XHIGH:
            showValue = "XHDPI";
            break;
        case DisplayMetrics.DENSITY_XXHIGH:
            showValue = "XXHDPI";
            break;
        default:
            showValue = String.valueOf(
                    getResources().getDisplayMetrics().densityDpi);
            break;
        }
        return showValue;
    }

    private String getCodeName() {
        switch (Build.VERSION.SDK_INT) {
        case Build.VERSION_CODES.BASE:
            return "BASE";
        case Build.VERSION_CODES.BASE_1_1:
            return "BASE_1_1";
        case Build.VERSION_CODES.CUPCAKE:
            return "CUPCAKE";
        case Build.VERSION_CODES.CUR_DEVELOPMENT:
            return "CUR_DEVELOPMENT";
        case Build.VERSION_CODES.DONUT:
            return "DONUT";
        case Build.VERSION_CODES.ECLAIR:
            return "ECLAIR";
        case Build.VERSION_CODES.ECLAIR_0_1:
            return "ECLAIR_0_1";
        case Build.VERSION_CODES.ECLAIR_MR1:
            return "ECLAIR_MR1";
        case Build.VERSION_CODES.FROYO:
            return "FROYO";
        case Build.VERSION_CODES.GINGERBREAD:
            return "GINGERBREAD";
        case Build.VERSION_CODES.GINGERBREAD_MR1:
            return "GINGERBREAD_MR1";
        case Build.VERSION_CODES.HONEYCOMB:
            return "HONEYCOMB";
        case Build.VERSION_CODES.HONEYCOMB_MR1:
            return "HONEYCOMB_MR1";
        case Build.VERSION_CODES.HONEYCOMB_MR2:
            return "HONEYCOMB_MR2";
        case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
            return "ICE_CREAM_SANDWICH";
        case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
            return "ICE_CREAM_SANDWICH_MR1";
        case Build.VERSION_CODES.JELLY_BEAN:
            return "JELLY_BEAN";
        case Build.VERSION_CODES.JELLY_BEAN_MR1:
            return "JELLY_BEAN_MR1";
        default:
            return "UNKNOWN";
        }
    }

}
