package in.silive.directme.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
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
import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.activity.LoginActivity;
import in.silive.directme.application.DirectMe;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.Keys;
import it.sephiroth.android.library.tooltip.Tooltip;


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
    @BindView(R.id.textView_xp_count)
    TextView xp_count;
    @BindView(R.id.textView_gold_count)
    TextView gold_count;
    @BindView(R.id.textView_coconut_count)
    TextView coconut_count;
    @BindView(R.id.textView_wood_count)
    TextView wood_count;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.welcome)
    TextView welcome;
    @BindView(R.id.island_name)
    TextView island_name;
    @BindView(R.id.island)
    ImageView island;
    @BindView(R.id.logout)
    ImageView logout ;
    @BindView(R.id.avatar)
    ImageView avatar ;
    @BindView(R.id.dme_icon)
    ImageView dme_icon_imgview;
    public SharedPreferences sharedPrefs;
    String Banana_Count;
    String Bamboo_Count;
    String Gold_Count;
    String Coconut_Count;
    String Wood_Count;
    int Experience_Count;
    String commodity[]=new String[5];
    private GoogleApiClient mGoogleApiClient;
    Typeface type;



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
        type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/CarnevaleeFreakshow.ttf");
        level.setTypeface(type);
        welcome.setTypeface(type);
        this.Banana_Count = commodity[2];
        banana_count.setText(Banana_Count);
        this.Bamboo_Count = commodity[1];
        bamboo_count.setText(Bamboo_Count);
        this.Gold_Count = commodity[0];
        gold_count.setText(Gold_Count);
        this.Coconut_Count = commodity[4];
        coconut_count.setText(Coconut_Count);
        this.Wood_Count=commodity[3];
        wood_count.setText(Wood_Count);
        final String expreience=sharedpreference.getString(Keys.experience,"");
        Experience_Count =Integer.parseInt(expreience);
       setExperiencelevel(expreience);
        xp_count.setText(expreience);
        final  String username=sharedpreference.getString(Keys.username,"");
        user_name.setText(username);
        final String island_id=sharedpreference.getString(Keys.island_id,"");
        islandImage(island_id);
        island.setOnClickListener(this);
        final String gravatar=sharedpreference.getString(Keys.gravatar,"");
        Picasso.with(getActivity()).load(gravatar).into(avatar);
        avatar.setOnClickListener(this);
        dme_icon_imgview.setOnClickListener(this);
        return rootView;
    }
    void setExperiencelevel(String experience)
    {int count=Integer.parseInt(experience);
       if(count<=Constants.XPCOUNT_LEVEL1)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL1);
           progressBar_experience.setProgress(count);
           level.setText("Level-1");
       }
       else if(count>Constants.XPCOUNT_LEVEL1&&count<=Constants.XPCOUNT_LEVEL2)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL2);
           progressBar_experience.setProgress(count);
           level.setText("Level-1");
       }
       else if(count>Constants.XPCOUNT_LEVEL2&&count<=Constants.XPCOUNT_LEVEL3)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL3);
           progressBar_experience.setProgress(count);
           level.setText("Level-2");
       }
       else if(count>Constants.XPCOUNT_LEVEL3&&count<=Constants.XPCOUNT_LEVEL4)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL4);
           progressBar_experience.setProgress(count);
           level.setText("Level-3");
       }
        else if(count>Constants.XPCOUNT_LEVEL4&&count<=Constants.XPCOUNT_LEVEL5)
        {
            progressBar_experience.setMax(Constants.XPCOUNT_LEVEL5);
            progressBar_experience.setProgress(count);
            level.setText("Level-4");
        }
        else if(count>Constants.XPCOUNT_LEVEL5&&count<=Constants.XPCOUNT_LEVEL6)
        {
            progressBar_experience.setMax(Constants.XPCOUNT_LEVEL6);
            progressBar_experience.setProgress(count);
            level.setText("Level-5");
        }
       else if(count>Constants.XPCOUNT_LEVEL6&&count<=Constants.XPCOUNT_LEVEL7)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL7);
           progressBar_experience.setProgress(count);
           level.setText("Level-6");
       }
       else if(count>Constants.XPCOUNT_LEVEL7&&count<=Constants.XPCOUNT_LEVEL8)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL8);
           progressBar_experience.setProgress(count);
           level.setText("Level-7");
       }
       else if(count>Constants.XPCOUNT_LEVEL8&&count<=Constants.XPCOUNT_LEVEL9)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL9);
           progressBar_experience.setProgress(count);
           level.setText("Level-8");
       }
       else if(count>Constants.XPCOUNT_LEVEL9&&count<=Constants.XPCOUNT_LEVEL10)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL10);
           progressBar_experience.setProgress(count);
           level.setText("Level-9");
       }
       else if(count>Constants.XPCOUNT_LEVEL10&&count<=Constants.XPCOUNT_LEVEL11)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL11);
           progressBar_experience.setProgress(count);
           level.setText("Level-10");
       }
       else if(count>Constants.XPCOUNT_LEVEL11&&count<=Constants.XPCOUNT_LEVEL12)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL12);
           progressBar_experience.setProgress(count);
           level.setText("Level-11");
       }
       else if(count>Constants.XPCOUNT_LEVEL12&&count<=Constants.XPCOUNT_LEVEL13)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL13);
           progressBar_experience.setProgress(count);
           level.setText("Level-12");
       }
       else if(count>Constants.XPCOUNT_LEVEL13&&count<=Constants.XPCOUNT_LEVEL14)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL14);
           progressBar_experience.setProgress(count);
           level.setText("Level-13");
       }
       else if(count>Constants.XPCOUNT_LEVEL14&&count<=Constants.XPCOUNT_LEVEL15)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL15);
           progressBar_experience.setProgress(count);
           level.setText("Level-14");
       }
       else if(count>Constants.XPCOUNT_LEVEL15&&count<=Constants.XPCOUNT_LEVEL16)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL16);
           progressBar_experience.setProgress(count);
           level.setText("Level-15");
       }
       else if(count>Constants.XPCOUNT_LEVEL16&&count<=Constants.XPCOUNT_LEVEL17)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL17);
           progressBar_experience.setProgress(count);
           level.setText("Level-16");
       }
       else if(count>Constants.XPCOUNT_LEVEL17&&count<=Constants.XPCOUNT_LEVEL18)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL18);
           progressBar_experience.setProgress(count);
           level.setText("Level-17");
       }
       else if(count>Constants.XPCOUNT_LEVEL18&&count<=Constants.XPCOUNT_LEVEL19)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL19);
           progressBar_experience.setProgress(count);
           level.setText("Level-18");
       }
       else if(count>Constants.XPCOUNT_LEVEL19&&count<=Constants.XPCOUNT_LEVEL20)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL20);
           progressBar_experience.setProgress(count);
           level.setText("Level-19");
       }
       else if(count>Constants.XPCOUNT_LEVEL20)
       {
           progressBar_experience.setMax(Constants.XPCOUNT_LEVEL20);
           progressBar_experience.setProgress(count);
           level.setText("Level-20");
       }
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
        {
            switch (v.getId()) {
                case R.id.logout:
                    alertDialog(getResources().getString(R.string.logout));
                    break;
                case R.id.island:
                    Tooltip.make(
                            getContext(),
                            new Tooltip.Builder()
                                    .anchor(v, Tooltip.Gravity.RIGHT)
                                    .closePolicy(new Tooltip.ClosePolicy()
                                            .insidePolicy(true, false)
                                            .outsidePolicy(true, false), 3000)
                                    .text("Island alloted")
                                     .withStyleId(R.style.ToolTipLayoutCustomStyle)
                                    .fitToScreen(true)
                                    .activateDelay(800)
                                    .showDelay(300)
                                    .maxWidth(500)
                                    .withOverlay(false)
                                    .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                                    .build()
                    ).show();
                    final  String alloted_island_name=sharedpreference.getString(Keys.island_name,"");
                    island_name.setText(alloted_island_name);
                    AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
                    AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
                    island_name.startAnimation(fadeIn);
                    island_name.startAnimation(fadeOut);
                    fadeIn.setDuration(3000);
                    fadeIn.setFillAfter(true);
                    fadeOut.setDuration(2000);
                    fadeOut.setFillAfter(true);
                    fadeOut.setStartOffset(3000+fadeIn.getStartOffset());

                    break;
                case R.id.avatar:Tooltip.make(
                        getContext(),
                        new Tooltip.Builder()
                                .anchor(v, Tooltip.Gravity.RIGHT)
                                .closePolicy(new Tooltip.ClosePolicy()
                                        .insidePolicy(true, false)
                                        .outsidePolicy(true, false), 3000)
                                .text("Xp Required for next level")
                                .withStyleId(R.style.ToolTipLayoutCustomStyle)
                                .fitToScreen(true)
                                .activateDelay(800)
                                .showDelay(300)
                                .maxWidth(500)
                                .withArrow(true)
                                .withOverlay(false)
                                .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                                .build()
                ).show();
                    break;
                case R.id.dme_icon:Intent intent=new Intent(getActivity(),DashboardActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                                   getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                   getActivity().finish();
             break;
            }
        }
    }
    void alertDialog(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView= inflater.inflate(R.layout.alert_label_editor, null);
        builder1.setView(dialogView);
        builder1.setCancelable(false);
        final AlertDialog alertDialog = builder1.create();
        TextView message_textview=(TextView)dialogView.findViewById(R.id.message);
        message_textview.setText(message);
        message_textview.setTextSize(30);
        message_textview.setTypeface(type);
        Button yes=(Button)dialogView.findViewById(R.id.yes);
        Button no=(Button)dialogView.findViewById(R.id.No);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeToken();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();

            }
        });
        builder1.setCancelable(true);

        alertDialog.show();



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
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                        getActivity().finish();
                    }
                });
    }
    }
