package in.silive.directme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.silive.directme.R;
import in.silive.directme.model.ParkingUserListModel;

/**
 * Created by Shobhit-pc on 3/6/2017.
 */

public class ParkingUserListAdapter extends RecyclerView.Adapter<ParkingUserListAdapter.MyViewHolder> {

    private final Context context;
    private List<ParkingUserListModel> parkingUserLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView userName;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.textViewParkingUserName);
        }
    }


    public ParkingUserListAdapter(List<ParkingUserListModel> parkingUserLists, Context context) {
        this.parkingUserLists = parkingUserLists;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parking_user_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ParkingUserListModel parkingUserList = parkingUserLists.get(position);
        holder.userName.setText(parkingUserList.getUserName());
    }

    @Override
    public int getItemCount() {
        return parkingUserLists.size();
    }
}