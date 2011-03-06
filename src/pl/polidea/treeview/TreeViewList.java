package pl.polidea.treeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Tree view, expandable multi-level.
 * 
 * 
 * @attr ref R.styleable#TreeViewList_indicator_expanded
 * @attr ref R.styleable#TreeViewList_indicator_collapsed
 * @attr ref R.styleable#TreeViewList_indicator_gravity
 * @attr ref R.styleable#TreeViewList_indicator_scaleType
 * @attr ref R.styleable#TreeViewList_indicator_background
 * @attr ref R.styleable#TreeViewList_row_background
 * 
 * @param <T>
 */
public class TreeViewList<T> extends ListView {

    private Drawable expandedDrawable;
    private Drawable collapsedDrawable;
    private Drawable rowBackgroundDrawable;
    private Drawable indicatorBackgroundDrawable;
    private ScaleType indicatorScaleType = ScaleType.CENTER;
    private int indentWidth = 0;
    private int indicatorGravity = Gravity.RIGHT;

    public TreeViewList(final Context context, final AttributeSet attrs) {
        this(context, attrs, R.style.treeViewListStyle);
    }

    public TreeViewList(final Context context) {
        this(context, null);
    }

    public TreeViewList(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs, defStyle);
    }

    private void parseAttributes(final Context context, final AttributeSet attrs, final int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TreeViewList);
        expandedDrawable = a.getDrawable(R.styleable.TreeViewList_src_expanded);
        if (expandedDrawable == null) {
            expandedDrawable = context.getResources().getDrawable(R.drawable.expanded);
        }
        collapsedDrawable = a.getDrawable(R.styleable.TreeViewList_src_collapsed);
        if (collapsedDrawable == null) {
            collapsedDrawable = context.getResources().getDrawable(R.drawable.collapsed);
        }
        indentWidth = a.getDimensionPixelSize(R.styleable.TreeViewList_indent_width, 20);
        indicatorGravity = a.getInteger(R.styleable.TreeViewList_indicator_gravity, Gravity.RIGHT);
        final int index = a.getInt(R.styleable.TreeViewList_indicator_scaleType, -1);
        if (index >= 0) {
            indicatorScaleType = ScaleType.values()[index];
        }
        indicatorBackgroundDrawable = a.getDrawable(R.styleable.TreeViewList_indicator_background);
        if (indicatorBackgroundDrawable == null) {
            indicatorBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
        }
        rowBackgroundDrawable = a.getDrawable(R.styleable.TreeViewList_row_background);
        if (rowBackgroundDrawable == null) {
            rowBackgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
        }
    }

    public void setTreeViewAdapter(final TreeViewAdapter<T> adapter) {
        super.setAdapter(adapter);
        adapter.setCollapsedDrawable(collapsedDrawable);
        adapter.setExpandedDrawable(expandedDrawable);
        adapter.setIndicatorGravity(indicatorGravity);
        adapter.setIndentWidth(indentWidth);
        adapter.setIndicatorScaleType(indicatorScaleType);
        adapter.setIndicatorBackgroundDrawable(indicatorBackgroundDrawable);
        adapter.setRowBackgroundDrawable(rowBackgroundDrawable);
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        throw new RuntimeException("Do not use setAdapter directly. Use setTreeViewAdapter instead...");
    }

}
