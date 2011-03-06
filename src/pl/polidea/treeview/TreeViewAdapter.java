package pl.polidea.treeview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * Adapter used to feed the table view.
 * 
 * @param <T>
 *            class for ID of the tree
 */
public abstract class TreeViewAdapter<T> implements ListAdapter {

    private static final String TAG = TreeViewAdapter.class.getSimpleName();
    private final TreeStateManager<T> treeStateManager;
    private final int numberOfLevels;
    private final LayoutInflater layoutInflater;

    protected int indentWidth = 0;
    private int indicatorGravity = 0;
    private Drawable collapsedDrawable;
    private Drawable expandedDrawable;
    private Drawable indicatorBackgroundDrawable;
    private Drawable rowBackgroundDrawable;

    private final OnClickListener indicatorClickListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            @SuppressWarnings("unchecked")
            final T id = (T) v.getTag();
            expandCollapse(id);
        }
    };
    private boolean collapsable;

    protected TreeStateManager<T> getManager() {
        return treeStateManager;
    }

    protected void expandCollapse(final T id) {
        final TreeNodeInfo<T> info = treeStateManager.getNodeInfo(id);
        if (!info.isWithChildren()) {
            Log.d(TAG, "The node " + id + " has no children, so there should be no expand/collapse events!");
            return;
        }
        if (info.isExpanded()) {
            treeStateManager.collapseChildren(id);
        } else {
            treeStateManager.expandDirectChildren(id);
        }
    }

    private void calculateIndentWidth() {
        indentWidth = Math.max(Math.max(indentWidth, expandedDrawable.getIntrinsicWidth()),
                collapsedDrawable.getIntrinsicWidth());
    }

    public TreeViewAdapter(final Context context, final TreeStateManager<T> treeStateManager, final int numberOfLevels) {
        this.treeStateManager = treeStateManager;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.numberOfLevels = numberOfLevels;
        this.collapsedDrawable = context.getResources().getDrawable(R.drawable.collapsed);
        this.expandedDrawable = context.getResources().getDrawable(R.drawable.expanded);
        this.rowBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
        this.indicatorBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
        calculateIndentWidth();
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

    public T getTreeId(final int position) {
        return treeStateManager.getVisibleList().get(position);
    }

    public TreeNodeInfo<T> getTreeNodeInfo(final int position) {
        return treeStateManager.getNodeInfo(getTreeId(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(final int position) {
        return getTreeNodeInfo(position).getLevel();
    }

    @Override
    public int getViewTypeCount() {
        return numberOfLevels;
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
        final TreeNodeInfo<T> nodeInfo = getTreeNodeInfo(position);
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

    /**
     * Called when new view is to be created.
     * 
     * @param treeNodeInfo
     *            node info
     * @return view that should be displayed as tree content
     */
    public abstract View getNewChildView(TreeNodeInfo<T> treeNodeInfo);

    /**
     * Called when new view is going to be reused. You should update the view
     * and fill it in with the data required to display the new information. You
     * can also create a new view, which will mean that the old view will not be
     * reused.
     * 
     * @param treeNodeInfo
     *            node info
     * @return view to used as row indented content
     */
    public abstract View updateView(View view, TreeNodeInfo<T> treeNodeInfo);

    /**
     * Retrieves background drawable for the node.
     * 
     * @param treeNodeInfo
     *            node info
     * @return drawable returned as background for the whole row. Might be null,
     *         then default background is used
     */
    public Drawable getBackgroundDrawable(final TreeNodeInfo<T> treeNodeInfo) {
        return null;
    }

    public final LinearLayout populateTreeItem(final LinearLayout layout, final View childView,
            final TreeNodeInfo<T> nodeInfo, final boolean newChildView) {
        final Drawable individualRowDrawable = getBackgroundDrawable(nodeInfo);
        layout.setBackgroundDrawable(individualRowDrawable == null ? rowBackgroundDrawable : individualRowDrawable);
        final LinearLayout.LayoutParams indicatorLayoutParams = new LinearLayout.LayoutParams(
                calculateIndentation(nodeInfo), LayoutParams.FILL_PARENT);
        final LinearLayout indicatorLayout = (LinearLayout) layout.findViewById(R.id.treeview_list_item_image_layout);
        indicatorLayout.setGravity(indicatorGravity);
        indicatorLayout.setLayoutParams(indicatorLayoutParams);
        final ImageView image = (ImageView) layout.findViewById(R.id.treeview_list_item_image);
        image.setImageDrawable(getDrawable(nodeInfo));
        image.setBackgroundDrawable(indicatorBackgroundDrawable);
        image.setScaleType(ScaleType.CENTER);
        image.setTag(nodeInfo.getId());
        if (nodeInfo.isWithChildren()) {
            image.setOnClickListener(indicatorClickListener);
        }
        final FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.treeview_list_item_frame);
        final FrameLayout.LayoutParams childParams = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        if (newChildView) {
            frameLayout.addView(childView, childParams);
        }
        return layout;
    }

    protected int calculateIndentation(final TreeNodeInfo<T> nodeInfo) {
        return indentWidth * (nodeInfo.getLevel() + (collapsable ? 1 : 0));
    }

    private Drawable getDrawable(final TreeNodeInfo<T> nodeInfo) {
        if (!nodeInfo.isWithChildren() || !collapsable) {
            return new ColorDrawable(Color.TRANSPARENT);
        }
        if (nodeInfo.isExpanded()) {
            return expandedDrawable;
        } else {
            return collapsedDrawable;
        }
    }

    public void setIndicatorGravity(final int indicatorGravity) {
        this.indicatorGravity = indicatorGravity;
    }

    public void setCollapsedDrawable(final Drawable collapsedDrawable) {
        this.collapsedDrawable = collapsedDrawable;
        calculateIndentWidth();
    }

    public void setExpandedDrawable(final Drawable expandedDrawable) {
        this.expandedDrawable = expandedDrawable;
        calculateIndentWidth();
    }

    public void setIndentWidth(final int indentWidth) {
        this.indentWidth = indentWidth;
        calculateIndentWidth();
    }

    public void setRowBackgroundDrawable(final Drawable rowBackgroundDrawable) {
        this.rowBackgroundDrawable = rowBackgroundDrawable;
    }

    public void setIndicatorBackgroundDrawable(final Drawable indicatorBackgroundDrawable) {
        this.indicatorBackgroundDrawable = indicatorBackgroundDrawable;
    }

    public void setCollapsable(final boolean collapsable) {
        this.collapsable = collapsable;
    }

}
