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
    @BindView(R.id.progressBar_banana)
    ProgressBar progressBar_banana;
    @BindView(R.id.progressBar_coconut)
    ProgressBar progressBar_coconut;
    @BindView(R.id.progressBar_bamboo)
    ProgressBar progressBar_bamboo;
    @BindView(R.id.progressBar_gold)
    ProgressBar progressBar_gold;
    @BindView(R.id.progressBar_wood)
    ProgressBar progressBar_wood;
    @BindView(R.id.circularProgressbar)
    ProgressBar progressBar_experience;
    @BindView(R.id.username)
    TextView user_name;
    int Banana_Count;
    int Bamboo_Count;
    int Gold_Count;
    int Coconut_Count;
     int Wood_Count;
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

            this.Banana_Count = Integer.parseInt(commodity[2]);

            progressBar_banana.setMax(10000);
            progressBar_banana.setProgress(Banana_Count);


             this.Bamboo_Count = Integer.parseInt(commodity[1]);
             progressBar_bamboo.setMax(10000);


             progressBar_bamboo.setProgress(Bamboo_Count);


             this.Gold_Count = Integer.parseInt(commodity[0]);
             progressBar_gold.setMax(10000);
             progressBar_gold.setProgress(Gold_Count);


             this.Coconut_Count = Integer.parseInt(commodity[4]);
             progressBar_coconut.setMax(10000);
             progressBar_coconut.setProgress(Coconut_Count);


             this.Wood_Count = Integer.parseInt(commodity[3]);
             progressBar_wood.setMax(10000);
             progressBar_wood.setProgress(Wood_Count);

        final String expreience=sharedpreference.getString(Keys.experience,"");
        Experience_Count =Integer.parseInt(expreience);
        progressBar_experience.setMax(10000);
        progressBar_experience.setProgress(Experience_Count);
        final  String username=sharedpreference.getString(Keys.username,"");
        user_name.setText(username);

        return rootView;
    }

}
