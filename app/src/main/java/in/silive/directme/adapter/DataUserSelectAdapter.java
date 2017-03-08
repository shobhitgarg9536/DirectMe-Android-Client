package in.silive.directme.adapter;

/**
 * Created by simran on 2/24/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.silive.directme.activity.ParkNowActivity;
import in.silive.directme.fragments.ShipTransitionFragment;
import in.silive.directme.application.DirectMe;
import in.silive.directme.model.UserDetailsList;

import in.silive.directme.R;
import in.silive.directme.utils.Constants;

public class DataUserSelectAdapter extends RecyclerView.Adapter<DataUserSelectAdapter.ViewHolder> {
    private ArrayList<UserDetailsList> user_details;
    private Context context;
    private ParkNowActivity parkNowActivity;

    public DataUserSelectAdapter(Context context, ArrayList<UserDetailsList> user_details,ParkNowActivity parkNowActivity) {
        this.context=context;
        this.user_details = user_details;
        this.context = context;
        this.parkNowActivity=parkNowActivity;
    }

    @Override
    public DataUserSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_userselect, viewGroup, false);
        return new ViewHolder(view);
    }
    private String userid;
    @Override
    public void onBindViewHolder(DataUserSelectAdapter.ViewHolder viewHolder, int i) {

        viewHolder.usr_name.setText(user_details.get(i).getUser_name());
        //   Picasso.with(context).load(user_details.get(i).getUser_image_url()).resize(240, 120).into(viewHolder.usr_img);
        viewHolder.usr_img.setImageResource(R.drawable.img_wood);
        userid=user_details.get(i).getUser_id();
        viewHolder.setUserId(userid);
    }

    @Override
    public int getItemCount() {
        return user_details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView usr_name;
        private ImageView usr_img;
        private String user_id;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        ShipTransitionFragment fragment;


        void setUserId(String user_id) {
            this.user_id = user_id;
        }

        ViewHolder(View view) {

            super(view);

            usr_name = (TextView)view.findViewById(R.id.usr_name);
            usr_img = (ImageView) view.findViewById(R.id.img_user);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            fragmentManager = parkNowActivity.getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,
                    R.anim.exit_to_left);
            fragment = new ShipTransitionFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);

            fragmentTransaction.commit();

            SharedPreferences sharedpreferences = DirectMe.getInstance().sharedPrefs;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.USER_ID,user_id);
            editor.commit();


        }
    }

}
