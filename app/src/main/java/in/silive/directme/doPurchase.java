package in.silive.directme;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yesha on 03-12-2016.
 */

public class doPurchase
{

    Context context1;
    public doPurchase(Context context)
    {
        this.context1=context;
    }

    public void subtractcommodities(int banana, int bamboo, int gold, int coco, int wood)
    {
        int c=0,field;
        banana=10;
        SharedPreferences pref =context1.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(pref.getString("Banana",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Banana",null));
            editor.putString("Banana",Integer.toString(field-banana));
            editor.commit();


        }
        if(pref.getString("Coconut",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Coconut",null));
            editor.putString("Coconut",Integer.toString(field-coco));
            editor.commit();
        }
        if(pref.getString("Timber",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Timber",null));
            editor.putString("Timber",Integer.toString(field-wood));
            editor.commit();
        }
        if(pref.getString("Bamboo",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Bamboo",null));
            editor.putString("Bamboo",Integer.toString(field-bamboo));
            editor.commit();
        }
        if(pref.getString("Gold",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Gold",null));
            editor.putString("Gold",Integer.toString(field-gold));
            editor.commit();
        }

    }

}
