package pl.polidea.treeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
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
public class TreeViewList extends ListView {

    private static final ColorDrawable DEFAULT_COLOR_DRAWABLE = new ColorDrawable(Color.TRANSPARENT);
    private static final int DEFAULT_COLLAPSED_RESOURCE = R.drawable.collapsed;
    private static final int DEFAULT_EXPANDED_RESOURCE = R.drawable.expanded;
    private static final int DEFAULT_INDENT = 0;
    private static final int DEFAULT_GRAVITY = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private Drawable expandedDrawable;
    private Drawable collapsedDrawable;
    private Drawable rowBackgroundDrawable;
    private Drawable indicatorBackgroundDrawable;
    private int indentWidth = 0;
    private int indicatorGravity = 0;
    private TreeViewAdapter< ? > treeAdapter;

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
            expandedDrawable = context.getResources().getDrawable(DEFAULT_EXPANDED_RESOURCE);
        }
        collapsedDrawable = a.getDrawable(R.styleable.TreeViewList_src_collapsed);
        if (collapsedDrawable == null) {
            collapsedDrawable = context.getResources().getDrawable(DEFAULT_COLLAPSED_RESOURCE);
        }
        indentWidth = a.getDimensionPixelSize(R.styleable.TreeViewList_indent_width, DEFAULT_INDENT);
        indicatorGravity = a.getInteger(R.styleable.TreeViewList_indicator_gravity, DEFAULT_GRAVITY);
        indicatorBackgroundDrawable = a.getDrawable(R.styleable.TreeViewList_indicator_background);
        if (indicatorBackgroundDrawable == null) {
            indicatorBackgroundDrawable = DEFAULT_COLOR_DRAWABLE;
        }
        rowBackgroundDrawable = a.getDrawable(R.styleable.TreeViewList_row_background);
        if (rowBackgroundDrawable == null) {
            rowBackgroundDrawable = DEFAULT_COLOR_DRAWABLE;
        }
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        if (!(adapter instanceof TreeViewAdapter)) {
            throw new RuntimeException("The adapter is not of TreeViewAdapter type");
        }
        treeAdapter = (TreeViewAdapter< ? >) adapter;
        treeAdapter.setCollapsedDrawable(collapsedDrawable);
        treeAdapter.setExpandedDrawable(expandedDrawable);
        treeAdapter.setIndicatorGravity(indicatorGravity);
        treeAdapter.setIndentWidth(indentWidth);
        treeAdapter.setIndicatorBackgroundDrawable(indicatorBackgroundDrawable);
        treeAdapter.setRowBackgroundDrawable(rowBackgroundDrawable);
        super.setAdapter(treeAdapter);
    }

}
