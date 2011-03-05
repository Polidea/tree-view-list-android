package pl.polidea.treeview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Indicator that is filled in with appropriate content depending on the level
 * of the list. It is indented appropriately, acording to depth of the three and
 * in case there are some deeper levels it shows '+' or '-' signs and reacts
 * appropriately - expanding or hiding lower levels.
 * 
 */
public class TreeViewListItemIndicator extends LinearLayout {

    public TreeViewListItemIndicator(final Context context) {
        super(context);
        initialize(context, null);
    }

    public TreeViewListItemIndicator(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private final void initialize(final Context context, final AttributeSet attrs) {
        // TODO: add initialization
    }

}
