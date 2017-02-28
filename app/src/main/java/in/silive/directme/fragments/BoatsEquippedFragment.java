package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import in.silive.directme.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by YESH AGNIHOTRI on 19-11-2016.
 */

public class BoatsEquippedFragment extends Fragment

{
    static JSONObject json_data;
    static int slot;
    int dockstatus = 1;
    ImageView img;
    SharedPreferences prefrences;

    public static BoatsEquippedFragment newInstance(JSONObject jsonObject, int value) {
        BoatsEquippedFragment boats_equipped = new BoatsEquippedFragment();
        json_data = jsonObject;
        slot = value;
        return boats_equipped;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefrences = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.dockyard, container,
                false);

        try {

            String name = json_data.getString("name");

            TextView boatname;
            boatname = (TextView) rootView.findViewById(R.id.boatname);
            boatname.setText(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (dockstatus == 1) {
            img = (ImageView) getView().findViewById(R.id.upgrade);
            // set a onclick listener for when the button gets clicked
            img.setOnClickListener(new View.OnClickListener() {
                // Start new list activity
                public void onClick(View v) {

                }
            });
        }
    }
}
