package ua.dp.michaellang.gitsurf.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class SpinnerArrayListAdapter extends ArrayAdapter<CharSequence> implements ThemedSpinnerAdapter {
    private final ThemedSpinnerAdapter.Helper mDropDownHelper;

    public SpinnerArrayListAdapter(Context context, CharSequence[] objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    public static SpinnerArrayListAdapter newInstance(@NonNull Context context, @ArrayRes int textArrayResId) {
        final CharSequence[] strings = context.getResources().getTextArray(textArrayResId);
        return new SpinnerArrayListAdapter(context, strings);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;

        if (convertView == null) {
            // Inflate the drop down using the helper's LayoutInflater
            LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return mDropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        mDropDownHelper.setDropDownViewTheme(theme);
    }
}
