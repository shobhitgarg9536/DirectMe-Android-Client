package com.silab.direct_me.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.silab.direct_me.AsyncTask.ApiCalling;
import com.silab.direct_me.Interface.AsyncResponse;
import com.silab.direct_me.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Parknow extends AppCompatActivity implements View.OnClickListener {

    private ImageView island1,island2,island3,island4,pirate_Island;
    LinearLayout llUserList;
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> users;
    Button removeuserList;
    ListView lvUsers;
    ImageView imageview,cloud1,cloud2;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String times = "null";
    String PARK_NOW= "PARK_NOW";
    SharedPreferences.Editor editor1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parknow);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        island1 = (ImageView) findViewById(R.id.imageViewisland1);
        island2 = (ImageView) findViewById(R.id.imageViewisland2);
        island3 = (ImageView) findViewById(R.id.imageViewisland3);
        island4 = (ImageView) findViewById(R.id.imageViewisland4);
        pirate_Island = (ImageView) findViewById(R.id.imageViewpirateisland);
     //  lvUsers =(ListView) findViewById(R.id.listviewusers);

   //     llUserList = (LinearLayout) findViewById(R.id.linearLayoutUserList);
    //    removeuserList = (Button) findViewById(R.id.buttonRemoveUserList);

        island1.setOnClickListener(this);
        island2.setOnClickListener(this);
        island3.setOnClickListener(this);
        island4.setOnClickListener(this);
        pirate_Island.setOnClickListener(this);
        cloud1 = (ImageView) findViewById(R.id.imageViewcloud1);
        cloud2 = (ImageView) findViewById(R.id.imageViewcloud2);
        cloud1.setVisibility(View.INVISIBLE);
        cloud2.setVisibility(View.INVISIBLE);
        final ImageView animImageView = (ImageView) findViewById(R.id.water);

        animImageView.setBackgroundResource(R.drawable.animation);
        animImageView.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation =
                        (AnimationDrawable) animImageView.getBackground();
                frameAnimation.start();
            }
        });
       // removeuserList.setOnClickListener(this);

    }
    Thread thread;
    Animation animation,animationb;
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageViewisland1:
                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while(true) {
                                sleep(10000);
                                userList("4");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();


                //making User list visible
            //    llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland2:

                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while(true) {
                                sleep(10000);
                                userList("4");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making User list visible
          //      llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland3:

                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while(true) {
                                sleep(10000);
                                userList("4");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making User list visible
           //     llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland4:

                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while(true) {
                                sleep(10000);
                                userList("4");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making User list visible
           //     llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonRemoveUserList:
                //making ISland view Visible
            //    llUserList.setVisibility(View.GONE);
                break;
            case R.id.imageViewpirateisland:
                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while(true) {
                                sleep(10000);
                                userList("4");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making ISland view Visible
           //     llUserList.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void userList(String island){


        ApiCalling myAsyncTask = new ApiCalling(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                System.out.println(output);
                try {

                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(output);
                    // If no of array elements is not zero
                    if(arr.length() != 0){
                        users = new ArrayList<HashMap<String, String>>();
                        // Loop through each array element, get JSON object which has userid and username
                        for (int i = 0; i < arr.length(); i++) {
                            // Get JSON object
                            JSONObject obj = (JSONObject) arr.get(i);
                            System.out.println(obj.get("name"));
                            System.out.println(obj.get("parking"));

                            queryValues = new HashMap<String, String>();
                            // Add values extracted from Object
                            queryValues.put("name", obj.get("name").toString());
                            queryValues.put("parking", obj.get("parking").toString());

                            users.add(queryValues);
                        }

                    }System.out.println(users);


                    final Dialog dialog = new Dialog(Parknow.this);
                    dialog.setTitle("Park Now");
                    dialog.setContentView(R.layout.listdialog);
                    ListView userList = (ListView) dialog.findViewById(R.id.listviewparknow);


                    ListAdapter adapter = new SimpleAdapter(Parknow.this , users , R.layout.parknowuserview , new String[] {
                            "name" ,"parking" }, new int[] { R.id.textviewusername , R.id.textViewparking
                    });
                    userList.setAdapter(adapter);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1b);
                    Animation animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2b);
                    cloud1.startAnimation(animation);
                    cloud2.startAnimation(animationb);
                    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String username = users.get(i).get("name");
                            String parking = users.get(i).get("parking");

                            sharedPreferences = getSharedPreferences(PARK_NOW , MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //putting values
                            editor.putString("Name", username);
                            editor.putString("parking" , parking);
                            editor.commit();

                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Parknow.this);
                            alertDialog.setTitle("Park");
                            alertDialog.setMessage("Are you sure you want to park your boat in " + username +
                                    " "+parking+ " area");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    sharedPreferences = getSharedPreferences(MyPREFERENCES , MODE_PRIVATE);
                                    editor1 = sharedPreferences.edit();
                                    if(sharedPreferences.contains(Parknow.times))
                                    {
                                        Toast.makeText(getApplicationContext(),"already parked",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(new Date());
                                        int hour = calendar.get(calendar.HOUR) * 60 * 60;
                                        int min = calendar.get(Calendar.MINUTE) * 60;
                                        int sec = calendar.get(Calendar.SECOND);
                                        int Seco = hour + min + sec;
                                        String Sec = Integer.toString(Seco);

                                        Toast.makeText(getApplicationContext(), Sec + "show", Toast.LENGTH_LONG).show();
                                        SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                        editor1.putString(times, Sec);


                                        editor1.commit();

                                        dialog.dismiss();
                                    }
                                }
                            });
                            alertDialog.create();
                            alertDialog.show();
                        }
                    }

                    );
                    dialog.show();


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cloud1.setVisibility(View.INVISIBLE);
                cloud2.setVisibility(View.INVISIBLE);
              thread.interrupt();

            }
        },this);
        myAsyncTask.execute("parkNowUserList", "", island);

/*
        progressDialog = new ProgressDialog(Parknow.this);
        progressDialog.setMessage("Loading your User list ......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://demo8496338.mockable.io/parknowuser", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {

            }
            // When error occured

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub
                // Hide ProgressBar
                if (statusCode == 404) {

                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    }

