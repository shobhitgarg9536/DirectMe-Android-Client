package com.silab.direct_me;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yesha on 30-01-2017.
 */

    public class DGFragment extends DialogFragment implements Purchase_check.ResponsePurchase{



     Purchase_check.FetchRequest object=new Purchase_check().new FetchRequest(this);
     Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView1 = inflater.inflate(R.layout.dialogf, container,
                false);
         final int dockid=getArguments().getInt("dock_id");

        dialog=getDialog();
        dialog.setTitle("DialogBox");
        TextView message=(TextView)rootView1.findViewById(R.id.message);
        String msg="";

            msg="Are you sure you want to buy this";
            //msg=strtext;
            message.setText(msg);
            Button button=(Button)rootView1.findViewById(R.id.ok1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {


                    object.execute(""+dockid);

                }
            });
            Button buttoncancel=(Button)rootView1.findViewById(R.id.cancel);
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    dialog.dismiss();
                }
            });

        return rootView1;
    }

    @Override
    public void postFinish(String output)
    {
        FragmentTransaction ft=this.getChildFragmentManager().beginTransaction();
        final DFragment dFragment = new DFragment();
        try {
            JSONObject jsonObject=new JSONObject(output);
             if(jsonObject.getString("non_field_errors")==null)
             {
                 String message="Your purchase has been completed";
                 Intent intent = new Intent();
                 intent.putExtra("message",message);
                 getTargetFragment().onActivityResult(0,1,intent);
                 //Bundle args=new Bundle();
                 //args.putString("message",message);

                 //dFragment.setArguments(args);
                 //dFragment.show(ft,"Dialog Fragment");
                 dialog.dismiss();

             }
            else
             {
                 String message=jsonObject.getString("non_field_errors");
                 Intent intent = new Intent();
                 intent.putExtra("message",message);
                 getTargetFragment().onActivityResult(0,0,intent);

                 //Bundle args=new Bundle();
                 //args.putString("message",message);
                 //dFragment.setArguments(args);
                 //dFragment.show(ft, "Dialog Fragment");
                  dialog.dismiss();
             }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            dialog.dismiss();
        }
    }
}




