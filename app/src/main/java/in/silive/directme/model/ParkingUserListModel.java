package in.silive.directme.model;

/**
 * Created by Shobhit-pc on 3/6/2017.
 */

public class ParkingUserListModel {

    String userName, user_id;

   public ParkingUserListModel(String userName , String user_id){
        this.userName = userName;
        this.user_id = user_id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
