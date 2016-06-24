package com.asismedia.japrisma.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImages extends AsyncTask<String,Void,Bitmap> {

    private Bitmap DownloadImageBitmap() {
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            URL get_url = new URL("http://10.0.3.2/japrisma/assets/images/tourism/");
            connection = (HttpURLConnection) get_url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            is = new BufferedInputStream(connection.getInputStream());
            final Bitmap bitmap = BitmapFactory.decodeStream(is);
            // ???
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return DownloadImageBitmap();
    }


    public static void saveFile(Context context, Bitmap image, String picName){
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        }
        catch (FileNotFoundException e) {
            Log.d("Image", "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d("Image", "io exception");
            e.printStackTrace();
        }

    }

    public boolean saveImageToInternalStorage(Bitmap image) {
        Context context = null;

        try {
            // Use the compress method on the Bitmap object to write image to
            // the OutputStream
            FileOutputStream fos = context.openFileOutput("desiredFilename.jpg", Context.MODE_PRIVATE);

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

}
