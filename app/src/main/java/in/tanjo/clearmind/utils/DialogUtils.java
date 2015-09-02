package in.tanjo.clearmind.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {

    private DialogUtils() {
    }

    public static void showDialog(final Context activityContext,
                                  final int title,
                                  final int message,
                                  final int positive,
                                  final DialogInterface.OnClickListener positiveClickListener,
                                  final int negative,
                                  final DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positive, positiveClickListener);
        builder.setNegativeButton(negative, negativeClickListener);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
