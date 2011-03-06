package pl.polidea.treeview;

/**
 * Information about the node.
 * 
 */
public class TreeNodeInfo<T> {
    private final T id;
    private final int level;
    private final boolean withChildren;
    private final boolean isVisible;

    /**
     * Creates the node information.
     * 
     * @param id
     *            id of the node
     * @param level
     *            level of the node
     * @param withChildren
     *            whether the node has children.
     * @param isVisible
     *            whether the tree node is visible.
     */
    public TreeNodeInfo(final T id, final int level, final boolean withChildren, final boolean isVisible) {
        super();
        this.id = id;
        this.level = level;
        this.withChildren = withChildren;
        this.isVisible = isVisible;
    }

    public T getId() {
        return id;
    }

    public boolean isWithChildren() {
        return withChildren;
    }

    public boolean isExpanded() {
        return isVisible;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "TreeNodeInfo [id=" + id + ", level=" + level + ", withChildren=" + withChildren + ", isVisible="
                + isVisible + "]";
    }

}