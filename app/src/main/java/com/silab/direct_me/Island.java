package com.silab.direct_me;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Lenovo on 25-Nov-16.
 */

public class Island extends Fragment implements View.OnClickListener {
    ImageView iv, iv1, island;
    public  int i = 0;
    int a[] = {R.drawable.island1, R.drawable.island2, R.drawable.island3, R.drawable.island4};

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(
                R.layout.island, container, false);
        iv = (ImageView) v.findViewById(R.id.nexter);
        iv1 = (ImageView) v.findViewById(R.id.previous);
        island = (ImageView) v.findViewById(R.id.island);
        this.iv.setOnClickListener(Island.this);
        this.iv1.setOnClickListener(Island.this);
        this.island.setOnClickListener(Island.this);

        return v;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.previous:
                if (i ==0) {
                    i = 3;
                }
                i--;
                island.setImageResource(a[i]);

                break;
            case R.id.nexter:
                if (i == 3) {
                    i = 0;
                }
                i++;
                island.setImageResource(a[i]);

                break;
            case R.id.island:
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Island Parking");
                dialog.show();
                Button declineButton = (Button) dialog.findViewById(R.id.button2);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
}




