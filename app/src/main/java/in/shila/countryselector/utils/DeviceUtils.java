package in.shila.countryselector.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by shiladitya on 06/11/15.
 */
public class DeviceUtils {
    public static String getDeviceCountryCode(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String countryCode = telephonyManager.getSimCountryIso();
        if (!TextUtils.isEmpty(countryCode)) {
            locale = new Locale("", countryCode.toUpperCase());
        }
        return locale.getCountry();
    }
}
