package in.silive.directme.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import in.silive.directme.application.DirectMe;
import in.silive.directme.network.FetchData;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.utils.API_URL_LIST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//// TODO: 2/22/2017 set animation in a method and uncomment

public class ParkNowActivity extends AppCompatActivity implements View.OnClickListener {

//    public static final String Authorization_Token = "Authorization_Token";
//    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String times = "null";
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> users;
    ImageView cloud1,cloud2;
    String token;
    SharedPreferences sharedPreferences, sharedPreferences1;
    String PARK_NOW = "PARK_NOW";
    SharedPreferences.Editor editor1;
    Thread thread;
    Animation animation, animationb;
    private ImageView island1, island2, island3, island4, pirate_Island;

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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageViewisland1:
                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                sleep(10000);
                                Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
                                startActivity(intent);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();


                //making UserModel list visible
                //    llUserList.setVisibility(View.VISIBLE);

                break;
            case R.id.imageViewisland2:

                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                sleep(10000);
                                Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
                                startActivity(intent);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making UserModel list visible
                //      llUserList.setVisibility(View.VISIBLE);

                break;
            case R.id.imageViewisland3:

                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                sleep(10000);
                                Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
                                startActivity(intent);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making UserModel list visible
                //     llUserList.setVisibility(View.VISIBLE);

                break;
            case R.id.imageViewisland4:

                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                sleep(10000);
                                Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
                                startActivity(intent);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
                //making UserModel list visible
                //     llUserList.setVisibility(View.VISIBLE);
                break;

            case R.id.imageViewpirateisland:
                cloud1.setVisibility(View.VISIBLE);
                cloud2.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1);
                animationb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2);
                cloud1.startAnimation(animation);
                cloud2.startAnimation(animationb);
                thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                sleep(10000);
                                Intent intent=new Intent(getApplicationContext(),UserDetailsActivity.class);
                                startActivity(intent);
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

    public void userList(String island) {


        FetchData myAsyncTask = new FetchData(new AsyncResponse() {
            @Override
            public void processFinish(String output) {
                System.out.println(output);
                try {

                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(output);
                    // If no of array elements is not zero
                    if (arr.length() != 0) {
                        users = new ArrayList<HashMap<String, String>>();
                        // Loop through each array element, get JSON object which has userid and username
                        for (int i = 0; i < arr.length(); i++) {
                            // Get JSON object
                            JSONObject obj = (JSONObject) arr.get(i);
                            System.out.println(obj.get("etName"));
                            System.out.println(obj.get("parking"));

                            queryValues = new HashMap<String, String>();
                            // Add values extracted from Object
                            queryValues.put("etName", obj.get("etName").toString());
                            queryValues.put("parking", obj.get("parking").toString());

                            users.add(queryValues);
                        }

                    }
                    System.out.println(users);

                    sharedPreferences1 = DirectMe.getInstance().sharedPrefs;
                    token = sharedPreferences1.getString("Authorization_Token","");

                    final Dialog dialog = new Dialog(ParkNowActivity.this);
                    dialog.setTitle("Park Now");
                    dialog.setContentView(R.layout.listdialog);
                    ListView userList = (ListView) dialog.findViewById(R.id.listviewparknow);


                    ListAdapter adapter = new SimpleAdapter(ParkNowActivity.this, users, R.layout.parknowuserview, new String[]{
                            "etName", "parking"}, new int[]{R.id.textviewusername, R.id.textViewparking
                    });
                    userList.setAdapter(adapter);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud1b);
                    Animation animationb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud2b);
                    cloud1.startAnimation(animation);
                    cloud2.startAnimation(animationb);
                    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                            String username = users.get(i).get("etName");
                                                            String parking = users.get(i).get("parking");

                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                            //putting values
                                                            editor.putString("Name", username);
                                                            editor.putString("parking", parking);
                                                            editor.commit();

                                                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ParkNowActivity.this);
                                                            alertDialog.setTitle("Park");
                                                            alertDialog.setMessage("Are you sure you want to park your boat in " + username +
                                                                    " " + parking + " area");
                                                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    editor1 = sharedPreferences.edit();
                                                                    if (sharedPreferences.contains(ParkNowActivity.times)) {
                                                                        Toast.makeText(getApplicationContext(), "already parked", Toast.LENGTH_LONG).show();
                                                                    } else {
                                                                        Calendar calendar = Calendar.getInstance();
                                                                        calendar.setTime(new Date());
                                                                        int hour = calendar.get(Calendar.HOUR) * 60 * 60;
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
        });

        String post_data="";
        try {
            post_data = URLEncoder.encode("island_id", "UTF-8") + "=" + URLEncoder.encode(island, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        myAsyncTask.execute(API_URL_LIST.GET_USER_LIST, "POST", token, post_data);

/*
        progressDialog = new ProgressDialog(ParkNowActivity.this);
        progressDialog.setMessage("Loading your UserModel list ......");
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


