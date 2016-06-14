package cc.liyongzhi.usersystem;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import cc.liyongzhi.usersystem.medcallback.ExecuteCallback;
import cc.liyongzhi.usersystem.medcallback.HttpCallback;
import cc.liyongzhi.usersystem.medcallback.LoginCallback;
import cc.liyongzhi.usersystem.medcallback.ManageLoginReturnValueCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lee on 5/18/16.
 */
public class MedUser implements Parcelable {


    private String sessionToken;
    private String username;
    private transient String password;

    ReadWriteLock lock;

    public MedUser() {
        this.lock = new ReentrantReadWriteLock();
    }

    protected MedUser(Parcel in) {
        this();
        sessionToken = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sessionToken);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedUser> CREATOR = new Creator<MedUser>() {
        @Override
        public MedUser createFromParcel(Parcel in) {
            return new MedUser(in);
        }

        @Override
        public MedUser[] newArray(int size) {
            return new MedUser[size];
        }
    };

    public static void loginInBackground(final String username, String password, final String url, final ManageLoginReturnValueCallback manageLoginReturnValueCallback, final LoginCallback<MedUser> loginCallback) {
        loginInBackground(username, password, new ExecuteCallback() {
            @Override
            public void execute(Map map, final MedCallback loginCallback, final MedUser user) {

                MedHttpUtils.get(map, url, new HttpCallback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
                        loginCallback.internalDone(null, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String content = response.body().string();
                        if (!MedUtils.isBlankString(content)) {
                            if (manageLoginReturnValueCallback.judge(content)) {
                                String sessionToken = manageLoginReturnValueCallback.manage(content);
                                user.setUsername(username);
                                user.setSessionToken(sessionToken);
                                MedUser.changeCurrentUser(user,true);
                                loginCallback.internalDone(user,null);
                            } else {
                                loginCallback.internalDone(null, new Exception("login failed"));
                            }
                        }

                    }
                });
            }
        },loginCallback);
    }


    public static void loginInBackground(String username, String password, ExecuteCallback executeCallback, LoginCallback<MedUser> loginCallback) {
        Map<String, String> map = new HashMap<String, String>();
        if (MedUtils.isBlankString(username) || MedUtils.isBlankString(password)) {
            throw new IllegalArgumentException("Blank username or blank mobile phone number");
        } else {
            map.put("username", username);
            map.put("password", password);
        }
        try {
            final MedUser user = (MedUser) MedUser.class.newInstance();
            if (user != null) {
                //TODO POST
                executeCallback.execute(map,loginCallback,user);
            }
        } catch (Exception e) {

        }
    }

    public static synchronized void changeCurrentUser(MedUser newUser, Boolean save) {
        if (newUser != null) {
            newUser.password = null;
        }
        File currentUserArchivePath = new File(MedGlobal.getApplicationContext().getDir("med",0) + "/currentUser");
        if (newUser != null && save) {
            try {
                newUser.lock.readLock().lock();
                //TODO save to file
            } catch (Exception e) {

            } finally {
                newUser.lock.readLock().unlock();
            }
        } else if (save) {
            //TODO delete saved user
            currentUserArchivePath.delete();
        }

        MedGlobal.setCurrentUser(newUser);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
