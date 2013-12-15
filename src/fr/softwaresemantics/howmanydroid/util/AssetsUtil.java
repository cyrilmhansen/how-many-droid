package fr.softwaresemantics.howmanydroid.util;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cmh on 15/12/13.
 */
public class AssetsUtil {

    public static InputStream loadAsset(Activity context, String path, String filename) {
        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream = null;

        try {
            String fullFilename = path + File.separator + filename;
            inputStream = assetManager.open(fullFilename);
            if ( inputStream != null)
                Log.d(  "AssetsUtil", "Loaded "+ fullFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static String loadUTF8AssetAsString(Activity context, String path, String filename) throws Exception {
        final InputStream is = loadAsset(context, path, filename);

        InputSupplier<InputStream> supplier = new InputSupplier<InputStream>() {
            @Override
            public InputStream getInput() throws IOException {
                return is;
            }
        };

       return CharStreams.toString(CharStreams.newReaderSupplier(supplier, Charsets.UTF_8));
    }
}
