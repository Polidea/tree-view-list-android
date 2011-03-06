package pl.polidea.treeview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * Adapter used to feed the table view.
 * 
 * @param <T>
 *            class for ID of the tree
 */
public abstract class TreeViewAdapter implements ListAdapter {

    private final TreeStateManager<Long> treeStateManager;
    private final int levelNumber;
    private final LayoutInflater layoutInflater;
    private final Context context;

    protected TreeStateManager<Long> getManager() {
        return treeStateManager;
    }

    public TreeViewAdapter(final Context context, final TreeStateManager<Long> treeStateManager, final int levelNumber) {
        this.context = context;
        this.treeStateManager = treeStateManager;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.levelNumber = levelNumber;
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        treeStateManager.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        treeStateManager.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return treeStateManager.getVisibleCount();
    }

    @Override
    public Object getItem(final int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(final int position) {
        return treeStateManager.getVisibleList().get(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(final int position) {
        return treeStateManager.getNodeInfo(getItemId(position)).getLevel();
    }

    @Override
    public int getViewTypeCount() {
        return levelNumber;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(final int position) {
        return true;
    }

    @Override
    public final View getView(final int position, final View convertView, final ViewGroup parent) {
        final Long id = treeStateManager.getVisibleList().get(position);
        final TreeNodeInfo<Long> nodeInfo = treeStateManager.getNodeInfo(id);
        if (convertView == null) {
            final LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.tree_list_item_wrapper, null);
            return populateTreeItem(layout, getNewChildView(nodeInfo), nodeInfo, true);
        } else {
            final LinearLayout linear = (LinearLayout) convertView;
            final FrameLayout frameLayout = (FrameLayout) linear.findViewById(R.id.treeview_list_item_frame);
            final View childView = frameLayout.getChildAt(0);
            updateView(childView, nodeInfo);
            return populateTreeItem(linear, childView, nodeInfo, false);
        }
    }

    public abstract View getNewChildView(TreeNodeInfo<Long> treeNodeInfo);

    public abstract View updateView(View view, TreeNodeInfo<Long> treeNodeInfo);

    public final LinearLayout populateTreeItem(final LinearLayout layout, final View childView,
            final TreeNodeInfo<Long> nodeInfo, final boolean newChildView) {
        final ImageView image = (ImageView) layout.findViewById(R.id.treeview_list_item_image);
        image.setImageDrawable(getDrawable(nodeInfo));
        final FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.treeview_list_item_frame);
        final LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        if (newChildView) {
            frameLayout.addView(childView, layoutParams);
        }
        return layout;
    }

    private Drawable getDrawable(final TreeNodeInfo<Long> nodeInfo) {
        if (!nodeInfo.isWithChildren()) {
            return new ColorDrawable(Color.TRANSPARENT);
        }
        if (nodeInfo.isExpanded()) {
            return context.getResources().getDrawable(R.drawable.expanded);
        } else {
            return context.getResources().getDrawable(R.drawable.collapsed);
        }
    }

}
