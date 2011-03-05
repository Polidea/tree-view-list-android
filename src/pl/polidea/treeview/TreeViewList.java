package pl.polidea.treeview;

import android.content.Context;
import android.util.AttributeSet;
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

}
