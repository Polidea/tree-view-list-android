package pl.polidea.treeview.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.polidea.treeview.R;
import pl.polidea.treeview.TreeViewList;
import pl.polidea.treeview.TreeViewListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class TreeViewListDemo extends Activity {
    private TreeViewList treeView;

    private final int[] currentNumber = new int[] { -1, -1, -1, -1 };
    int[] levels = new int[] { 0, 0, 1, 1, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0, 1, 2, 3, 2, 0, 0, 1, 2, 0, 1, 2, 0, 2 };
    private int maxLevel;

    private SimpleAdapter demo;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        treeView = (TreeViewList) findViewById(R.id.mainTreeView);

        maxLevel = currentNumber.length - 1;

        final List<Map<String, ? >> demoList = new ArrayList<Map<String, ? >>();
        for (int i = 0; i < levels.length; i++) {
            demoList.add(getMap(i));
        }
        demo = new SimpleAdapter(this, demoList, R.layout.demo_list_item, new String[] { "Level", "Description" },
                new int[] { R.id.demo_list_item_level, R.id.demo_list_item_description });
        treeView.setAdapter(new TreeViewListAdapter(demo));
    }

    private Map<String, String> getMap(final int rowIndex) {
        final Map<String, String> rowMap = new HashMap<String, String>();
        final int currentLevel = levels[rowIndex];
        updateAllLevelNumbersForCurrentAndHigherLevels(currentLevel);
        String description = "Level " + currentLevel + " : ";
        String separator = "";
        for (int i = 0; i <= currentLevel; i++) {
            description += separator + currentNumber[i];
            separator = ".";
        }
        rowMap.put("Description", description);
        rowMap.put("Level", Integer.toString(currentLevel));
        return rowMap;
    }

    private void updateAllLevelNumbersForCurrentAndHigherLevels(final int currentLevel) {
        currentNumber[currentLevel]++;
        for (int i = currentLevel + 1; i <= maxLevel; i++) {
            currentNumber[i] = -1;
        }
    }
}