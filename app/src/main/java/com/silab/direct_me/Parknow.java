package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Parknow extends AppCompatActivity implements View.OnClickListener {

    private ImageView island1,island2,island3,island4,pirate_Island;
    Toolbar tbPArkNow;
    LinearLayout llUserList;
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> users;
    Button removeuserList;
    ListView lvUsers;

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
        lvUsers =(ListView) findViewById(R.id.listviewusers);

        llUserList = (LinearLayout) findViewById(R.id.linearLayoutUserList);
        removeuserList = (Button) findViewById(R.id.buttonRemoveUserList);

        island1.setOnClickListener(this);
        island2.setOnClickListener(this);
        island3.setOnClickListener(this);
        island4.setOnClickListener(this);
        pirate_Island.setOnClickListener(this);
        removeuserList.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageViewisland1:
                userList("island1");
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland2:
                userList("island2");
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland3:
                userList("island3");
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland4:
                userList("island4");
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonRemoveUserList:
                //making ISland view Visible
                llUserList.setVisibility(View.GONE);
                break;
            case R.id.imageViewpirateisland:
                //making ISland view Visible
                llUserList.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void userList(String island){

        AsyncHttpClient client = new AsyncHttpClient();

        client.post("http://demo8496338.mockable.io/parknow", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {

                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(response);
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

                    }
                    ListAdapter adapter = new SimpleAdapter(Parknow.this , users , R.layout.parknowuserview , new String[] {
                            "name" ,"parking" }, new int[] { R.id.textviewusername , R.id.textViewparking ,
                            });
                    lvUsers.setAdapter(adapter);
                    lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String username = users.get(i).get("name");
                            String parking = users.get(i).get("parking");

                            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Parknow.this);
                            alertDialog.setTitle("Park");
                            alertDialog.setMessage("Are you sure you want to park your boat in" + username +
                            " "+parking+ " area");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialog.create();
                            alertDialog.show();
                        }
                    }

                    );
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
        });
    }

    }

