package cc.liyongzhi.usersystem;

import android.os.Looper;

/**
 * Created by lee on 5/18/16.
 */
public abstract class MedCallback<T> {

    public MedCallback() {
    }

    public void internalDone(final T t, final Exception exception) {
        if (this.mustRunOnUIThread() && Looper.myLooper() != Looper.getMainLooper()) {
            if (!MedGlobal.handler.post(new Runnable() {
                @Override
                public void run() {
                    MedCallback.this.internalDone0(t, exception);
                }
            })) {

            }
        } else {
            this.internalDone0(t, exception);
        }
    }

    protected boolean mustRunOnUIThread() {
        return true;
    }

    public void internalDone(Exception parseException) {
        this.internalDone((T) null, parseException);
    }

    protected abstract void internalDone0(T var1, Exception var2);

}
