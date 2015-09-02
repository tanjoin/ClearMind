package in.tanjo.clearmind.app.bitmapedit;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import in.tanjo.clearmind.Constants;
import in.tanjo.clearmind.R;
import in.tanjo.clearmind.utils.DialogUtils;

public class BitmapEditActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clear_mind_bitmap_edit_activity);
        initViews();
        bindViews();
    }

    private void initViews() {
        mImageView = (ImageView)findViewById(
                R.id.clear_mind_bitmap_edit_activity_image_view);
    }

    private DialogInterface.OnClickListener positiveClickListener =
            new DialogInterface.OnClickListener() {
        @Override
        public void onClick(final DialogInterface dialog, final int which) {
            saveImageView();
        }
    };

    private DialogInterface.OnClickListener negativeClickListener =
            new DialogInterface.OnClickListener() {
        @Override
        public void onClick(final DialogInterface dialog, final int which) {
        }
    };

    private void bindViews() {
        Uri uri = getUri();
        if (uri == null) {
            return;
        }
        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), uri);

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Bitmap bitmap = null;
        if (image != null) {
            bitmap = changeFromTransparentToWhite(image);
            // TODO: bitmap = drawWhiteBackground(image);
        }
        mImageView.setImageBitmap(bitmap);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogUtils.showDialog(
                        BitmapEditActivity.this,
                        R.string.bitmap_edit_activity_title,
                        R.string.bitmap_edit_activity_save_message,
                        R.string.bitmap_edit_activity_save_ok,
                        positiveClickListener,
                        R.string.bitmap_edit_activity_save_cancel,
                        negativeClickListener);
            }
        });
    }

    // Alphaチャンネルを持つ画像の透明度を白色に変える
    private static Bitmap changeFromTransparentToWhite(final Bitmap bitmap) {
        Bitmap result = bitmap.copy(bitmap.getConfig(), true);
        if (result != null && result.hasAlpha()) {
            int width = result.getWidth();
            int height = result.getHeight();
            int pixels[] = new int[width * height];
            result.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[x + y * width];
                    float alpha = (float) Color.alpha(pixel) / (float) 0xFF;
                    pixels[x + y * width] = Color.argb(
                            0xFF,
                            (int) (Color.red(pixel) * alpha
                                    + 0xFF * (1.f - alpha)),
                            (int) (Color.green(pixel) * alpha
                                    + 0xFF * (1.f - alpha)),
                            (int) (Color.blue(pixel) * alpha
                                    + 0xFF * (1.f - alpha)));
                }
            }
            result.setPixels(pixels, 0, width, 0, 0, width, height);
        }
        return result;
    }

    private static Bitmap drawWhiteBackground(final Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(),
                                            bitmap.getHeight(),
                                            Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return result;
    }

    private Uri getUri() {
        Intent intent = getIntent();
        Uri uri = null;
        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        }
        return uri;
    }

    private void saveImageView() {
        if (mImageView != null) {
            saveImage(((BitmapDrawable)mImageView.getDrawable()).getBitmap());
        }
    }

    private void saveImage(final Bitmap bitmap) {
        File file = new File(
                Environment.getExternalStorageDirectory().getPath() +
                Constants.SAVE_DIR);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (SecurityException exception) {
            exception.printStackTrace();
            throw exception;
        }
        java.util.Date date = new java.util.Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault());
        String fileName = fileNameDate.format(date) + ".png";
        String attachName = file.getAbsolutePath() + "/" + fileName;
        try {
            FileOutputStream fos = new FileOutputStream(attachName);
            ((BitmapDrawable) mImageView.getDrawable())
                    .getBitmap().compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        values.put(Images.Media.MIME_TYPE, "image/png");
        values.put(Images.Media.TITLE, fileName);
        values.put("_data", attachName);
        contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}
