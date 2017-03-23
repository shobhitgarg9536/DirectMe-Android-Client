package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.utils.Keys;
import in.silive.directme.utils.LoggerUtils;

/**
 * Created by simran on 3/12/2017.
 */

public class UserProfileFragment extends Fragment {
    SharedPreferences sharedpreference;

    @BindView(R.id.circularProgressbar)
    ProgressBar progressBar_experience;
    @BindView(R.id.username)
    TextView user_name;
    @BindView(R.id.textView_bamboo_count)
    TextView bamboo_count;
    @BindView(R.id.textView_banana_count)
    TextView banana_count;
    @BindView(R.id.textView_gold_count)
    TextView gold_count;
    @BindView(R.id.textView_coconut_count)
    TextView coconut_count;
    @BindView(R.id.textView_wood_count)
    TextView wood_count;
    @BindView(R.id.island)
    ImageView island;
    String Banana_Count;
    String Bamboo_Count;
    String Gold_Count;
    String Coconut_Count;
    String Wood_Count;
    int Experience_Count;
    String commodity[]=new String[5];
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profile, container,
                false);
        ButterKnife.bind(this,rootView);
        sharedpreference = DirectMe.getInstance().sharedPrefs;
        for(int i=0;i<5;i++)
        {
            commodity[i]=sharedpreference.getString(Keys.co[i],"");
        }

        this.Banana_Count = commodity[2];
        banana_count.setText(Banana_Count);




        this.Bamboo_Count = commodity[1];
        bamboo_count.setText(Bamboo_Count);





        this.Gold_Count = commodity[0];
        gold_count.setText(Gold_Count);


        this.Coconut_Count = commodity[4];
        coconut_count.setText(Coconut_Count);


        this.Wood_Count = commodity[3];
        wood_count.setText(Wood_Count);

        final String expreience=sharedpreference.getString(Keys.experience,"");
        Experience_Count =Integer.parseInt(expreience);
        progressBar_experience.setMax(10000);
        progressBar_experience.setProgress(Experience_Count);
        final  String username=sharedpreference.getString(Keys.username,"");
        user_name.setText(username);
        final String island_id=sharedpreference.getString(Keys.island_id,"");
        islandImage(island_id);
        return rootView;
    }
    void islandImage(String island_id)
    {
        int id=Integer.parseInt(island_id);
        switch (id)
        {
            case 1:island.setImageResource(R.drawable.coconut_island);
                break;
            case 2:island.setImageResource(R.drawable.wood_island);
                break;
            case 3:island.setImageResource(R.drawable.banana_island);
                break;
            case 4:island.setImageResource(R.drawable.bamboo_island);
                break;
            case 5:island.setImageResource(R.drawable.pirate_island);
                break;
        }

    }
}
