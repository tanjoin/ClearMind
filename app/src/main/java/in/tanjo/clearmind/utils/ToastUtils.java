package in.tanjo.clearmind.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    /** Toastの表示時間 Long */
    public static int DURATION_LENGTH_LONG  = Toast.LENGTH_LONG;
    /** Toastの表示時間 Short */
    public static int DURATION_LENGTH_SHORT = Toast.LENGTH_SHORT;

    /** Toastのレイアウト位置 Center */
    public static int GRAVITY_CENTER = Gravity.CENTER;
    /** Toastのレイアウト位置 Top */
    public static int GRAVITY_TOP    = Gravity.TOP;
    /** Toastのレイアウト位置 Bottom */
    public static int GRAVITY_BOTTOM = Gravity.BOTTOM;
    /** Toastのレイアウト位置 Right */
    public static int GRAVITY_RIGHT  = Gravity.RIGHT;
    /** Toastのレイアウト位置 Left */
    public static int GRAVITY_LEFT   = Gravity.LEFT;

    private ToastUtils() {
    }

    public static void showToast(final Context context,
                                 final int resId,
                                 final int duration,
                                 final int gravity) {
        final int xOffset = 0;
        final int yOffset = 0;
        Toast toast = new Toast(context);
        toast.setText(resId);
        toast.setDuration(duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

    public static void showToast(final Context context,
                                 final CharSequence s,
                                 final int duration,
                                 final int gravity) {
        final int xOffset = 0;
        final int yOffset = 0;
        Toast toast = new Toast(context);
        toast.setText(s);
        toast.setDuration(duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

}
