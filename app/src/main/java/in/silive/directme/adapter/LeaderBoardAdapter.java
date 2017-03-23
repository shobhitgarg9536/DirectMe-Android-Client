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
import in.silive.directme.model.LeaderBoardObject;

/**
 * Created by Shobhit-pc on 3/23/2017.
 */

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {

    private List<LeaderBoardObject> leaderBoardUserLists;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_userName,tv_rank;
        ImageView tv_userIcon;

        public MyViewHolder(View view) {
            super(view);
            tv_userName = (TextView) view.findViewById(R.id.textViewLeaderBoardUserName);
            tv_rank = (TextView) view.findViewById(R.id.textViewLeaderBoardRank);
            tv_userIcon = (ImageView) view.findViewById(R.id.imageViewLeaderBoardAvatar);
        }
    }


    public LeaderBoardAdapter(List<LeaderBoardObject> leaderBoardObjects, Context context) {
        this.leaderBoardUserLists = leaderBoardObjects;
        this.context = context;
    }

    @Override
    public LeaderBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_user_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaderBoardAdapter.MyViewHolder holder, int position) {
        LeaderBoardObject leaderBoardObject = leaderBoardUserLists.get(position);
        holder.tv_userName.setText(leaderBoardObject.getUser_name());
        holder.tv_rank.setText(leaderBoardObject.getRank());
        Picasso.with(context)
                .load(leaderBoardObject.getUser_icon())
                .into(holder.tv_userIcon);
    }

    @Override
    public int getItemCount() {
        return leaderBoardUserLists.size();
    }
}