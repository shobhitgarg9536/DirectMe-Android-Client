package com.silab.direct_me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Lenovo on 01-Dec-16.
 */

public class Shipx extends Fragment implements View.OnClickListener {
    Button undock;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_parking, container,
                false);
        this.undock=(Button)v.findViewById(R.id.undocker);
        this.undock.setOnClickListener(Shipx.this);
        return v;

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Undock");
        alertDialog.setMessage("Do you want to undock");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }
}
