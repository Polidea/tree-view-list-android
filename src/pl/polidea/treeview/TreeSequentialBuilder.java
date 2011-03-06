package pl.polidea.treeview;

import android.util.Log;

/**
 * Allows to build tree easily in sequential mode (you have to know levels of
 * all the tree elements upfront).
 * 
 * @param <T>
 */
public class TreeSequentialBuilder<T> {
    private static final String TAG = TreeSequentialBuilder.class.getSimpleName();

    private final TreeStateManager<T> manager;

    private T lastAddedId = null;
    private int lastLevel = -1;

    public TreeSequentialBuilder(final TreeStateManager<T> manager) {
        this.manager = manager;
    }

    /**
     * Adds sequentially new node. Using this method is the simplest way of
     * building tree - if you have all the elements in the sequence.
     * 
     * @param id
     *            id of the node
     * @param level
     *            its level
     */
    public synchronized void sequentiallyAddNextNode(final T id, final int level) {
        Log.d(TAG, "Adding node " + id + " at level " + level);
        if (lastAddedId == null) {
            addNodeToParentOneLevelDown(null, id, level);
        } else {
            if (level <= lastLevel) {
                final T parent = findParentAtLevel(level - 1);
                addNodeToParentOneLevelDown(parent, id, level);
            } else {
                addNodeToParentOneLevelDown(lastAddedId, id, level);
            }
        }
    }

    private T findParentAtLevel(final int levelToFind) {
        T parent = manager.getParent(lastAddedId);
        while (parent != null) {
            if (manager.getLevel(parent) == levelToFind) {
                break;
            }
            parent = manager.getParent(parent);
        }
        return parent;
    }

    private void addNodeToParentOneLevelDown(final T parent, final T id, final int level) {
        if (parent == null && level != 0) {
            throw new RuntimeException("Trying to add new id " + id + " to top level with level != 0 (" + level + ")");
        }
        if (parent != null && manager.getLevel(parent) != level - 1) {
            throw new RuntimeException("Trying to add new id " + id + " <" + level + "> to " + parent + " <"
                    + manager.getLevel(parent) + ">. The difference in levels up is bigger than 1.");
        }
        manager.addAfterChild(parent, id, null);
        setLastAdded(id, level);
    }

    private void setLastAdded(final T id, final int level) {
        lastAddedId = id;
        lastLevel = level;
    }

}
