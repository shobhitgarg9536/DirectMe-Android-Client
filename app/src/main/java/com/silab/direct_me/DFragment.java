package com.silab.direct_me;

/**
 * Created by YESH AGNIHOTRI on 18-11-2016.
 */

        import android.app.Dialog;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

public class DFragment extends DialogFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogf, container,
                false);
        final Dialog dialog=getDialog();
        dialog.setTitle("DialogBox");
        TextView message=(TextView)rootView.findViewById(R.id.message);
        String msg="";
        int choice=1;
       if(choice==1)
       {
           msg="You already own this vehicle.Please buy a new dock to buy more";
       }
       if(choice==2)
       {
           msg="Are you sure you want to buy this";
       }
        if (choice==3)
        {
            msg="you dont have enough money to buy this one";
        }

        message.setText(msg);
        Button button=(Button)rootView.findViewById(R.id.ok1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });
        Button buttoncancel=(Button)rootView.findViewById(R.id.cancel);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               dialog.dismiss();
            }
        });
        return rootView;
    }
}
