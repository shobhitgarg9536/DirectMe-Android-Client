package in.silive.directme.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import in.silive.directme.R;
import in.silive.directme.adapter.DataUserSelectAdapter;
import in.silive.directme.model.UserDetailsList;


/**
 * Created by simran on 2/24/2017.
 */

public class UserDetailsActivity extends AppCompatActivity {
    private final String user_names[] = {
            "si",
            "si",
            "si",
            "si",
            "si",
            "si",
            "si",
            "si",
            "si",
            "si"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_userselect);
        initViews();
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 5);

        recyclerView.setLayoutManager(layoutManager);

        ArrayList<UserDetailsList> user_details = prepareData();
        DataUserSelectAdapter adapter = new DataUserSelectAdapter(getApplicationContext(), user_details);
        recyclerView.setAdapter(adapter);


    }

    private ArrayList<UserDetailsList> prepareData() {

        ArrayList<UserDetailsList> user_details = new ArrayList<>();
        for (int i = 0; i < user_names.length; i++) {
            UserDetailsList userDetailsList = new UserDetailsList();
            userDetailsList.setUser_name(user_names[i]);
            userDetailsList.setUser_image_url("simran");
            user_details.add(userDetailsList);
        }
        return user_details;
    }
}
