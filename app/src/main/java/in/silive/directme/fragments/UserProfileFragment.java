package in.silive.directme.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.activity.LoginActivity;
import in.silive.directme.application.DirectMe;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.Keys;


/**
 * Created by simran on 3/12/2017.
 */

public class UserProfileFragment extends Fragment implements View.OnClickListener{
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
    @BindView(R.id.logout)
    ImageView logout ;
    @BindView(R.id.avatar)
    ImageView avatar ;
    public SharedPreferences sharedPrefs;
    String Banana_Count;
    String Bamboo_Count;
    String Gold_Count;
    String Coconut_Count;
    String Wood_Count;
    int Experience_Count;
    String commodity[]=new String[5];
    private GoogleApiClient mGoogleApiClient;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profile, container,
                false);
        ButterKnife.bind(this,rootView);
        logout.setOnClickListener(this);
        sharedPrefs = getActivity().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
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
        final String expreience=sharedpreference.getString(Keys.experience,"");
        Experience_Count =Integer.parseInt(expreience);
        progressBar_experience.setMax(10000);
        progressBar_experience.setProgress(Experience_Count);
        final  String username=sharedpreference.getString(Keys.username,"");
        user_name.setText(username);
        final String island_id=sharedpreference.getString(Keys.island_id,"");
        islandImage(island_id);
        final String gravatar=sharedpreference.getString(Keys.gravatar,"");
        Picasso.with(getActivity()).load(gravatar).into(avatar);
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
    @Override
    public void onClick(View v) {
        alertDialog(getResources().getString(R.string.logout));
    }
    void alertDialog(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                   removeToken();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    public void onStart() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    private void removeToken() {
        SharedPreferences.Editor editor=sharedPrefs.edit();
        editor.clear();
        editor.commit();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }
}
