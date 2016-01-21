package epsi.md4.com.epsicalendar.ws.interceptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import epsi.md4.com.epsicalendar.Common;

public class SetCookiesInterceptor implements Interceptor {

    public static final String TAG = SetCookiesInterceptor.class.getName();
    public static final String SET_COOKIE_HEADER_NAME = "Set-Cookie";

    private Context mContext;

    public SetCookiesInterceptor(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers(SET_COOKIE_HEADER_NAME).isEmpty()) {
            Set<String> cookies = new HashSet<>();
            for (String cookie : originalResponse.headers(SET_COOKIE_HEADER_NAME)) {
                cookies.add(cookie);
                Log.v(TAG, String.format("Setting cookie: %s", cookie));
            }
            SharedPreferences.Editor simpleCalendar = mContext.getSharedPreferences(Common.PREFS_SCOPE, Context.MODE_PRIVATE).edit();
            simpleCalendar.putStringSet(Common.COOKIES_KEY, cookies).apply();
        }

        return originalResponse;
    }
}
