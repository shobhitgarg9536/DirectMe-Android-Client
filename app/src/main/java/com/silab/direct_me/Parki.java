package com.silab.direct_me;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lenovo on 05-Nov-16.
 */
public class Parki extends Fragment implements View.OnClickListener {

    ListView lv;
    ImageView iv;
    Toolbar tbParked;

    String things[]={"Bnanana","apple","coins","coconut"};
    String count[]={"1","2","3","4"};
    int[] image={R.drawable.bana,R.drawable.apple,R.drawable.coin,R.drawable.coc};
    public View onCreateView(LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {



        View v= inflater.inflate(

                R.layout.parking, container, false);
        this.iv = (ImageView)v.findViewById(R.id.imageView2);
        this.iv.setOnClickListener(Parki.this);
        return v;
    }





        public void onClick (View view){
            Toast.makeText(getActivity(),"click",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
            builderSingle.setIcon(R.mipmap.ic_launcher);
            builderSingle.setTitle("Select One Name:-");

           final  Myadapter arr=new Myadapter(things);


            builderSingle.setNegativeButton(
                    "cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builderSingle.setAdapter(
                    arr,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strName= (String) arr.getItem(which);

                            AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                    getActivity());
                            builderInner.setMessage(strName);

                            builderInner.setTitle("Your Selected Item is");
                            builderInner.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builderInner.show();
                        }
                    });
            builderSingle.show();



        }
    public class Myadapter extends BaseAdapter
    {String things[];
        public String name;
        public Myadapter(String things[])

        {this.things=things;

        }
        public int getCount() {
            // TODO Auto-generated method stub
            return things.length;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return things[arg0]+"\t"+count[arg0];
        }

        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override

        public View getView(int arg0, View arg1, ViewGroup arg2) {
            LayoutInflater inf=getLayoutInflater(getArguments());
            View view=inf.inflate(R.layout.cust_lay,arg2,false );
            TextView tv1=(TextView)view.findViewById(R.id.textView);

            ImageView tv3=(ImageView) view.findViewById(R.id.imageView7);

            tv1.setText(things[arg0]+"\t"+count[arg0]);
            name=tv1.getText().toString();
            tv3.setImageResource(image[arg0]);

            return view;
        }

    }
}
