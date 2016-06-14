package cc.liyongzhi.usersystem;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by lee on 5/18/16.
 */
public class MedGlobal {

    private static Context applicationContext;

    protected static Handler handler;

    private static MedUser mCurrentUser;

    public static void initialize(Context context) {
        if (handler == null && Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("please call MedGlobal.initialize in main thread");
        } else {
            applicationContext = context;

            if (handler == null) {
                handler = new Handler();
            }
        }
    }

    public static void setCurrentUser(MedUser currentUser) {
        mCurrentUser = currentUser;
    }

    public MedUser getCurrentUser() {
        return mCurrentUser;
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

}
