package in.silive.directme;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yesha on 03-12-2016.
 */

public class checkPurchase
{
Context context;
public checkPurchase(Context context)
{
    this.context =context;
}

    public boolean forpurchase(int banana, int bamboo, int gold, int coco, int wood )
    {
        int c=0,field;

        SharedPreferences pref =context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        if(pref.getString("Banana",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Banana",null));
            if(field>=banana)
                c++;

        }
        if(pref.getString("Coconut",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Coconut",null));
            if(field>=coco)
                c++;
        }
        if(pref.getString("Timber",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Timber",null));
            if(field>=wood)
                c++;
        }
        if(pref.getString("Bamboo",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Bamboo",null));
            if(field>=bamboo)
                c++;
        }
        if(pref.getString("Gold",null)!=null)
        {
            field=Integer.parseInt(pref.getString("Gold",null));
            if(field>=gold)
                c++;
        }
        if(c==5)
        {
            c=0;
            return true;
        }
        else
        {
            c=0;
            return false;
    }}
}
