package pl.polidea.treeview.demo;

import java.util.Arrays;

import pl.polidea.treeview.InMemoryTreeStateManager;
import pl.polidea.treeview.R;
import pl.polidea.treeview.TreeNodeInfo;
import pl.polidea.treeview.TreeSequentialBuilder;
import pl.polidea.treeview.TreeStateManager;
import pl.polidea.treeview.TreeViewAdapter;
import pl.polidea.treeview.TreeViewList;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreeViewListDemo extends Activity {
    private static final String TAG = TreeViewListDemo.class.getSimpleName();
    private TreeViewList treeView;

    private static final int[] demoNodes = new int[] { 0, 0, 1, 1, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1,
            2, 0, 1, 2, 0, 1 };
    private static final int levelNumber = 4;
    private final TreeStateManager<Long> manager = new InMemoryTreeStateManager<Long>();
    private final TreeSequentialBuilder<Long> treeBuilder = new TreeSequentialBuilder<Long>(manager);
    private TreeViewAdapter adapter;

    private String getDescription(final long id) {
        final Integer[] hierarchy = manager.getHierarchyDescription(id);
        return "Node " + id + "<" + manager.getLevel(id) + "> : " + Arrays.asList(hierarchy);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        for (int i = 0; i < demoNodes.length; i++) {
            treeBuilder.sequentiallyAddNextNode((long) i, demoNodes[i]);
        }
        Log.d(TAG, manager.toString());
        treeView = (TreeViewList) findViewById(R.id.mainTreeView);
        adapter = new TreeViewAdapter(this, manager, levelNumber) {
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
                levelView.setText("" + treeNodeInfo.getLevel());
                return viewLayout;
            }
        };
        treeView.setTreeViewAdapter(adapter);
    }

}