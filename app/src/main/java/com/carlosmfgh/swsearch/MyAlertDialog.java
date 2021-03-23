package com.carlosmfgh.swsearch;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

/**
 * Simple custom alert message boxes.  Add as needed.
 */
public class MyAlertDialog {

    public static void  OkMessageBox (Context context,
                                      String title,
                                      String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.ok),
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
