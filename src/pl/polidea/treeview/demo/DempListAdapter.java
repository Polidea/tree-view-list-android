package pl.polidea.treeview.demo;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class DempListAdapter extends SimpleAdapter implements ListAdapter {

    public DempListAdapter(final Context context, final List< ? extends Map<String, ? >> data,
            final int resource, final String[] from, final int[] to) {
        super(context, data, resource, from, to);
    }

}
