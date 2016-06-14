package cc.liyongzhi.usersystem.medcallback;


import cc.liyongzhi.usersystem.MedCallback;
import cc.liyongzhi.usersystem.MedUser;

/**
 * Created by lee on 5/18/16.
 */
public abstract class LoginCallback<T extends MedUser> extends MedCallback<T> {

    public LoginCallback() {
    }

    public abstract void done(T var, Exception e);

    protected final void  internalDone0(T returnValue, Exception e) {
        this.done(returnValue, e);
    }

}
