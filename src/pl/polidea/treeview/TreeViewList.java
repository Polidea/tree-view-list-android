package pl.polidea.treeview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TreeViewList extends ListView {

    public TreeViewList(final Context context, final AttributeSet attrs) {
        this(context, attrs, R.style.treeViewListStyle);
    }

    public TreeViewList(final Context context) {
        this(context, null);
    }

    public TreeViewList(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTreeViewAdapter(final TreeViewAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        throw new RuntimeException("Do not use setAdapter directly. Use setTreeViewAdapter instead...");
    }

}
