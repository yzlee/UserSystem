package cc.liyongzhi.usersystem.medcallback;

import java.util.Map;

import cc.liyongzhi.usersystem.MedCallback;
import cc.liyongzhi.usersystem.MedUser;


/**
 * Created by lee on 5/20/16.
 */
public abstract class ExecuteCallback {

    public abstract void execute(Map map, MedCallback callback, MedUser user);

}
