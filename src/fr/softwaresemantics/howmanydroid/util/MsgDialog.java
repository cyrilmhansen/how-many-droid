package fr.softwaresemantics.howmanydroid.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import fr.softwaresemantics.howmanydroid.R;

/**
 * Created by cmh on 15/12/13.
 */
public class MsgDialog {

    public static void showDialog(Activity context, String title, String message) {
        //set up dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle(title);
        dialog.setCancelable(true);
        //there are a lot of settings, for dialog, check them all out!

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        text.setText(message);

//        //set up image view
//        ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
//        img.setImageResource(R.drawable.nista_logo);

        //set up button
        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        dialog.show();
    }
}
