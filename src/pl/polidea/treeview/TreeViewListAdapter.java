package pl.polidea.treeview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class TreeViewListAdapter implements ListAdapter {
    @Override
    public boolean areAllItemsEnabled() {
        return dependentAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(final int position) {
        return dependentAdapter.isEnabled(position);
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        dependentAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        dependentAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return dependentAdapter.getCount();
    }

    @Override
    public Object getItem(final int position) {
        return dependentAdapter.getItem(position);
    }

    @Override
    public long getItemId(final int position) {
        return dependentAdapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return dependentAdapter.hasStableIds();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        return dependentAdapter.getView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(final int position) {
        return dependentAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return dependentAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return dependentAdapter.isEmpty();
    }

    private final ListAdapter dependentAdapter;

    public TreeViewListAdapter(final ListAdapter dependentAdapter) {
        this.dependentAdapter = dependentAdapter;
    }

}
