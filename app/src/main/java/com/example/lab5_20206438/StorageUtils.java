package com.example.lab5_20206438;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageUtils {

    public static void saveImageToInternalStorage(Context context, Bitmap bitmap, String filename) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImageFromInternalStorage(Context context, String filename) {
        try (FileInputStream fis = context.openFileInput(filename)) {
            return BitmapFactory.decodeStream(fis);
        } catch (IOException e) {
            return null;
        }
    }
}