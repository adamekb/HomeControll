package com.example.henri_000.homecontroll;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import static android.app.PendingIntent.getActivity;

/**
 * Created by adam on 15-04-27.
 */
public class Dialog extends Fragment {
    private String inputIP;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPref;


    public void showInputDialog(MainActivity mainActivity) {

        sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String defaultValue = mainActivity.getResources().getString(R.string.enter_ip_here);
        inputIP = sharedPref.getString(getString(R.string.saved_ip), defaultValue);

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        inputIP = editText.getText().toString();
                        editor.putString(getString(R.string.saved_ip), editText.getText().toString());
                        editor.commit();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public String getIp () {
        return inputIP;
    }
}
