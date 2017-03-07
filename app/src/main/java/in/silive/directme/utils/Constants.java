package in.silive.directme.utils;

/**
 * Created by shobhit on 28/2/17.
 */

public class Constants {
    static final String LOG = "debugging";
    public static final String SHARED_PREFS = "directme_prefs";
    public static final String AUTH_TOKEN = "Authorization_Token";
    public static final String SHIP_IMAGE_URL ="SHIP_IMAGE_URL";
    public static final String SHIP_ID ="SHIP_ID";
    public static final String ISLAND_ID ="ISLAND_ID";
    public static final String USER_ID ="USER_ID";
    public static final String COCONUT_ISLAND_ID ="1";
    public static final String BANANA_ISLAND_ID ="3";
    public static final String TIMBER_ISLAND_ID ="2";
    public static final String PIRATE_ISLAND_ID ="5";
    public static final String BAMBOO_ISLAND_ID ="4";


    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String FIREBASE_ID_SENT = "FirebaseIdSendToServer";
}
