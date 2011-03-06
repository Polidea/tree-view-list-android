package pl.polidea.treeview.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

//CSOFF: FinalClassCheck

/**
 * Demo activity showing how the tree view can be used.
 * 
 */
public class TreeViewListDemo extends Activity {

    private final Set<Long> selected = new HashSet<Long>();

    private final class FancyColouredVariousSizesAdapter extends
            SimpleStandardAdapter {
        private FancyColouredVariousSizesAdapter(final Activity activity,
                final TreeStateManager<Long> treeStateManager,
                final int numberOfLevels) {
            super(activity, treeStateManager, numberOfLevels);
        }

        @Override
        public LinearLayout updateView(final View view,
                final TreeNodeInfo<Long> treeNodeInfo) {
            final LinearLayout viewLayout = super
                    .updateView(view, treeNodeInfo);
            final TextView descriptionView = (TextView) viewLayout
                    .findViewById(R.id.demo_list_item_description);
            final TextView levelView = (TextView) viewLayout
                    .findViewById(R.id.demo_list_item_level);
            descriptionView.setTextSize(20 - 2 * treeNodeInfo.getLevel());
            levelView.setTextSize(20 - 2 * treeNodeInfo.getLevel());
            return viewLayout;
        }

        @Override
        public Drawable getBackgroundDrawable(
                final TreeNodeInfo<Long> treeNodeInfo) {
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
        }
    }

    /**
     * This is a very simple adapter that provides very basic tree view with a
     * checkboxes and simple item description.
     * 
     */
    private class SimpleStandardAdapter extends TreeViewAdapter<Long> {

        private SimpleStandardAdapter(final Activity activity,
                final TreeStateManager<Long> treeStateManager,
                final int numberOfLevels) {
            super(activity, treeStateManager, numberOfLevels);
        }

        @Override
        public View getNewChildView(final TreeNodeInfo<Long> treeNodeInfo) {
            final LinearLayout viewLayout = (LinearLayout) getLayoutInflater()
                    .inflate(R.layout.demo_list_item, null);
            return updateView(viewLayout, treeNodeInfo);
        }

        @Override
        public LinearLayout updateView(final View view,
                final TreeNodeInfo<Long> treeNodeInfo) {
            final LinearLayout viewLayout = (LinearLayout) view;
            final TextView descriptionView = (TextView) viewLayout
                    .findViewById(R.id.demo_list_item_description);
            final TextView levelView = (TextView) viewLayout
                    .findViewById(R.id.demo_list_item_level);
            descriptionView.setText(getDescription(treeNodeInfo.getId()));
            levelView.setText("" + treeNodeInfo.getLevel());
            final CheckBox box = (CheckBox) viewLayout
                    .findViewById(R.id.demo_list_checkbox);
            box.setTag(treeNodeInfo.getId());
            if (treeNodeInfo.isWithChildren()) {
                box.setVisibility(View.GONE);
            } else {
                box.setVisibility(View.VISIBLE);
                box.setChecked(selected.contains(treeNodeInfo.getId()));
            }
            box.setOnCheckedChangeListener(onCheckedChange);
            return viewLayout;
        }

        @Override
        public long getItemId(final int position) {
            return getTreeId(position);
        }
    }

    private final OnCheckedChangeListener onCheckedChange = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final CompoundButton buttonView,
                final boolean isChecked) {
            final Long id = (Long) buttonView.getTag();
            if (isChecked) {
                selected.add(id);
            } else {
                selected.remove(id);
            }
        }
    };

    private static final String TAG = TreeViewListDemo.class.getSimpleName();
    private TreeViewList treeView;

    private static final int[] DEMO_NODES = new int[] { 0, 0, 1, 1, 1, 2, 2, 1,
            1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1, 2, 0, 1, 2, 0, 1 };
    private static final int LEVEL_NUMBER = 4;
    private final TreeStateManager<Long> manager = new InMemoryTreeStateManager<Long>();
    private final TreeBuilder<Long> treeBuilder = new TreeBuilder<Long>(manager);
    private FancyColouredVariousSizesAdapter fancyAdapter;
    private SimpleStandardAdapter simpleAdapter;

    private String getDescription(final long id) {
        final Integer[] hierarchy = manager.getHierarchyDescription(id);
        return "Node " + id + Arrays.asList(hierarchy);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        for (int i = 0; i < DEMO_NODES.length; i++) {
            treeBuilder.sequentiallyAddNextNode((long) i, DEMO_NODES[i]);
        }
        Log.d(TAG, manager.toString());
        treeView = (TreeViewList) findViewById(R.id.mainTreeView);
        fancyAdapter = new FancyColouredVariousSizesAdapter(this, manager,
                LEVEL_NUMBER);
        simpleAdapter = new SimpleStandardAdapter(this, manager, LEVEL_NUMBER);
        treeView.setAdapter(simpleAdapter);
        registerForContextMenu(treeView);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final MenuItem collapsibleMenu = menu
                .findItem(R.id.collapsible_menu_item);
        if (treeView.isCollapsible()) {
            collapsibleMenu.setTitle(R.string.collapsible_menu_disable);
            collapsibleMenu.setTitleCondensed(getResources().getString(
                    R.string.collapsible_condensed_disable));
        } else {
            collapsibleMenu.setTitle(R.string.collapsible_menu_enable);
            collapsibleMenu.setTitleCondensed(getResources().getString(
                    R.string.collapsible_condensed_enable));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
        case R.id.simple_menu_item:
            treeView.setAdapter(simpleAdapter);
            break;
        case R.id.fancy_menu_item:
            treeView.setAdapter(fancyAdapter);
            break;
        case R.id.collapsible_menu_item:
            treeView.setCollapsible(!treeView.isCollapsible());
            break;
        case R.id.expand_all_menu_item:
            manager.expandEverythingBelow(null);
            break;
        case R.id.collapse_all_menu_item:
            manager.collapseChildren(null);
            break;
        default:
            return false;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v,
            final ContextMenuInfo menuInfo) {
        final AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) menuInfo;
        final long id = adapterInfo.id;
        final TreeNodeInfo<Long> info = manager.getNodeInfo(id);
        if (info.isWithChildren()) {
            final MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.context_menu, menu);
            if (info.isExpanded()) {
                menu.findItem(R.id.context_menu_expand_item).setVisible(false);
                menu.findItem(R.id.context_menu_expand_all).setVisible(false);
            } else {
                menu.findItem(R.id.context_menu_collapse).setVisible(false);
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();
        final long id = info.id;
        switch (item.getItemId()) {
        case R.id.context_menu_collapse:
            manager.collapseChildren(id);
            return true;
        case R.id.context_menu_expand_all:
            manager.expandEverythingBelow(id);
            return true;
        case R.id.context_menu_expand_item:
            manager.expandDirectChildren(id);
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }
}