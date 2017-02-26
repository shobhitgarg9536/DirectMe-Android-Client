package in.silive.directme.adapter;

/**
 * Created by simran on 2/24/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.silive.directme.model.UserDetails;

import in.silive.directme.R;

public class DataUserSelectAdapter extends RecyclerView.Adapter<DataUserSelectAdapter.ViewHolder> {
    private ArrayList<UserDetails> user_details;
    private Context context;

    public DataUserSelectAdapter(Context context, ArrayList<UserDetails> user_details) {
        this.context=context;
        this.user_details = user_details;
        this.context = context;
    }

    @Override
    public DataUserSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_userselect, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataUserSelectAdapter.ViewHolder viewHolder, int i) {

        viewHolder.usr_name.setText(user_details.get(i).getUser_name());
    //   Picasso.with(context).load(user_details.get(i).getUser_image_url()).resize(240, 120).into(viewHolder.usr_img);
        viewHolder.usr_img.setImageResource(R.drawable.banana);
    }

    @Override
    public int getItemCount() {
        return user_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView usr_name;
        private ImageView usr_img;
        public ViewHolder(View view) {
            super(view);

            usr_name = (TextView)view.findViewById(R.id.usr_name);
            usr_img = (ImageView) view.findViewById(R.id.img_user);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
        }
    }

}
