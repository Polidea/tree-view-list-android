package pl.polidea.treeview;

import android.app.Activity;
import android.os.Bundle;

public class TreeViewListDemo extends Activity {
    private TreeViewList treeView;
    private TreeViewListAdapterDemo demo;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        treeView = (TreeViewList) findViewById(R.id.mainTreeView);
        demo = new TreeViewListAdapterDemo();
        treeView.setAdapter(demo);
    }
}