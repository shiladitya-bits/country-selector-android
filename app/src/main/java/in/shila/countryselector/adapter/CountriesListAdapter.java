package in.shila.countryselector.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import in.shila.countryselector.R;
import in.shila.countryselector.utils.DeviceUtils;

public class CountriesListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private LayoutInflater inflater;

    private static final String SELECTED_DISPLAY_FORMAT = "+%s";
    private static final String DROPDOWN_DISPLAY_FORMAT = "%s (+%s)";

    public CountriesListAdapter(Context context, String[] values) {
        super(context, R.layout.country_list_item, values);
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        DropdownViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.country_list_item, parent, false);

            viewHolder = new DropdownViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgViewFlag);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txtViewCountryName);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (DropdownViewHolder) convertView.getTag();
        }

        final String[] g = values[position].split(",");  //dialing code, country code
        final String countryDisplayName = getCountryDisplayName(g[1].trim());
        final String countryDialCode = g[0].trim();
        viewHolder.textView.setText(String.format(DROPDOWN_DISPLAY_FORMAT, countryDisplayName, countryDialCode));

        final String pngName = g[1].trim().toLowerCase(); //image name

        setImageDrawableFromCountryCode(viewHolder.imageView, pngName);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DropdownViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.country_selected_item, parent, false);

            viewHolder = new DropdownViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgViewFlag);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.txtViewCountryName);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (DropdownViewHolder) convertView.getTag();
        }

        String[] g = values[position].split(",");
        viewHolder.textView.setText(String.format(SELECTED_DISPLAY_FORMAT, g[0].trim()));

        String pngName = g[1].trim().toLowerCase();

        setImageDrawableFromCountryCode(viewHolder.imageView, pngName);

        return convertView;
    }

    private String getCountryDisplayName(String ssid) {
        Locale loc = new Locale("", ssid);
        return loc.getDisplayCountry().trim();
    }

    private String getCountryCode(String ssid) {
        Locale loc = new Locale("", ssid);
        return loc.getCountry().trim();
    }

    public int getPositionForDeviceCountry() {
        String dvcCountry = DeviceUtils.getDeviceCountryCode(getContext());
        for (int i = 0; i < values.length; i++) {
            String[] g = values[i].split(",");
            String countryCode = getCountryCode(g[1].trim());
            if (countryCode.equals(dvcCountry)) {
                return i;
            }
        }
        return -1;
    }

    private static class DropdownViewHolder {
        ImageView imageView;
        TextView textView;
    }

    private void setImageDrawableFromCountryCode(ImageView imageView, String pngName) {
        try {
            imageView.setImageDrawable(Drawable.createFromStream(context.getAssets().open("flags/" + pngName + ".png"), null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}