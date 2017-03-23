package in.silive.directme.model;

/**
 * Created by Shobhit-pc on 3/23/2017.
 */

public class LeaderBoardObject  {

    String user_name, user_icon, rank;

   public LeaderBoardObject(String user_name, String user_icon, String rank){
        this.user_name = user_name;
        this.user_icon = user_icon;
        this.rank = rank;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getRank() {
        return rank;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
