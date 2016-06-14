package cc.liyongzhi.usersystem;

import java.util.Map;

import cc.liyongzhi.usersystem.medcallback.HttpCallback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lee on 5/19/16.
 */
public class MedHttpUtils {

    public synchronized static void get(Map map, String url, HttpCallback httpCallback ) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(httpCallback);
    }

}
