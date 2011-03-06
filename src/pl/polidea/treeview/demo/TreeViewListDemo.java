package pl.polidea.treeview.demo;

import java.util.Arrays;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.R;
import pl.polidea.treeview.TreeBuilder;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewAdapter;
import pl.polidea.treeview.TreeViewList;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreeViewListDemo extends Activity {
    private static final String TAG = TreeViewListDemo.class.getSimpleName();
    private TreeViewList<Long> treeView;

    private static final int[] demoNodes = new int[] { 0, 0, 1, 1, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1,
            2, 0, 1, 2, 0, 1 };
    private static final int levelNumber = 4;
    private final TreeStateManager<Long> manager = new InMemoryTreeStateManager<Long>();
    private final TreeBuilder<Long> treeBuilder = new TreeBuilder<Long>(manager);
    private TreeViewAdapter<Long> adapter;

    private String getDescription(final long id) {
        final Integer[] hierarchy = manager.getHierarchyDescription(id);
        return "Node " + id + "<" + manager.getLevel(id) + "> : " + Arrays.asList(hierarchy);
    }

    /** Called when the activity is first created. */
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        for (int i = 0; i < demoNodes.length; i++) {
            treeBuilder.sequentiallyAddNextNode((long) i, demoNodes[i]);
        }
        Log.d(TAG, manager.toString());
        treeView = (TreeViewList<Long>) findViewById(R.id.mainTreeView);
        adapter = new TreeViewAdapter<Long>(this, manager, levelNumber) {
            @Override
            public View getNewChildView(final TreeNodeInfo<Long> treeNodeInfo) {
                final LinearLayout viewLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.demo_list_item,
                        null);
                return updateView(viewLayout, treeNodeInfo);
            }

            @Override
            public View updateView(final View view, final TreeNodeInfo<Long> treeNodeInfo) {
                final LinearLayout viewLayout = (LinearLayout) view;
                final TextView descriptionView = (TextView) viewLayout.findViewById(R.id.demo_list_item_description);
                final TextView levelView = (TextView) viewLayout.findViewById(R.id.demo_list_item_level);
                descriptionView.setText(getDescription(treeNodeInfo.getId()));
                descriptionView.setTextSize(20 - 2 * treeNodeInfo.getLevel());
                levelView.setText("" + treeNodeInfo.getLevel());
                levelView.setTextSize(20 - 2 * treeNodeInfo.getLevel());
                return viewLayout;
            }

            @Override
            public Drawable getBackgroundDrawable(final TreeNodeInfo<Long> treeNodeInfo) {
                switch (treeNodeInfo.getLevel()) {
                case 0:
                    return new ColorDrawable(Color.WHITE);
                case 1:
                    return new ColorDrawable(Color.GRAY);
                case 2:
                    return new ColorDrawable(Color.YELLOW);
                default:
                    return null;
                }
            };

            @Override
            public long getItemId(final int position) {
                return getTreeId(position);
            }
        };
        treeView.setTreeViewAdapter(adapter);
    }
}