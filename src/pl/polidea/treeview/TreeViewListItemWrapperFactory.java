package pl.polidea.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Item view that is filled in with appropriate content depending on the level
 * of the list. It is indented appropriately, acording to depth of the three and
 * in case there are some deeper levels it shows '+' or '-' signs and reacts
 * appropriately - expanding or hiding lower levels.
 * 
 */
public class TreeViewListItemWrapperFactory {

    public enum ItemState {
        COLLAPSED,
        EXPANDED,
    }

    public enum HasChildre {
        COLLAPSED,
        EXPANDED,
    }

    private final Context context;
    private final LayoutInflater layoutInflater;

    public TreeViewListItemWrapperFactory(final Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public LinearLayout createListItem(final ViewGroup parentView, final int level, final View childView) {
        final LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.tree_list_item_wrapper, parentView);
        final LinearLayout indent = (LinearLayout) layout.findViewById(R.id.treeview_list_item_indent);
        final ImageView image = (ImageView) layout.findViewById(R.id.treeview_list_item_image);
        final FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.treeview_list_item_frame);
        final LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        frameLayout.addView(childView, layoutParams);
        return layout;
    }
}
