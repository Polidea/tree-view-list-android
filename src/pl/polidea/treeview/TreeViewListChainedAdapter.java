package pl.polidea.treeview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class TreeViewListChainedAdapter implements ListAdapter {

    private final ListAdapter dependentAdapter;
    private final TreeStateManager<Integer> treeStateManager;

    public TreeViewListChainedAdapter(final ListAdapter dependentAdapter,
            final TreeStateManager<Integer> treeStateManager) {
        this.dependentAdapter = dependentAdapter;
        this.treeStateManager = treeStateManager;
        dependentAdapter.registerDataSetObserver(new DependentSetObserver());
        initializeFromDependentAdapter();
    }

    private void initializeFromDependentAdapter() {
        treeSetManager
    }

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
        return true;
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

}
