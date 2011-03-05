package pl.polidea.treeview;

import java.util.List;

/**
 * Manages information about state of the tree. It only keeps information about
 * tree elements, not the elements themselves.
 * 
 * @param <T>
 *            type of the identifier for nodes in the tree
 */
public interface TreeStateManager<T extends Object> {
    /**
     * Information about the node.
     * 
     */
    public static class TreeNodeInfo<T> {
        private final T id;
        private final int level;
        private final boolean withChildren;
        private final boolean isExpanded;

        /**
         * Creates the node information.
         * 
         * @param id
         *            id of the node
         * @param level
         *            level of the node
         * @param withChildren
         *            whether the node has children.
         * @param isExpanded
         *            whether the tree is expanded.
         */
        public TreeNodeInfo(final T id, final int level, final boolean withChildren, final boolean isExpanded) {
            super();
            this.id = id;
            this.level = level;
            this.withChildren = withChildren;
            this.isExpanded = isExpanded;
        }

        public T getId() {
            return id;
        }

        public boolean isWithChildren() {
            return withChildren;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public int getLevel() {
            return level;
        }

    }

    /**
     * Returns information about the node
     * 
     * @param id
     *            node id
     * @return node info
     */
    public TreeNodeInfo<T> getNodeInfo(T id);

    /**
     * Returns children of the node.
     * 
     * @param id
     *            id of the node or null if asking for top nodes
     * @return children of the node
     */
    public List<T> getChildren(T id);

    /**
     * Returns parent of the node.
     * 
     * @param id
     *            id of the node
     * @return parent id or null if no parent
     */
    public T getParent(T id);

    /**
     * Adds the node before child or at the beginning.
     * 
     * @param id
     *            id of the node. If null - adds at the top level
     * @param newChild
     *            new child to add if null - adds at the beginning.
     * @param beforeChild
     *            child before which to add the new child
     */
    public void addBeforeChild(T id, T newChild, T beforeChild);

    /**
     * Adds the node after child or at the end.
     * 
     * @param id
     *            id of the node. If null - adds at the top level.
     * @param newChild
     *            new child to add. If null - adds at the end.
     * @param afterChild
     *            child after which to add the new child
     */
    public void addAfterChild(T id, T newChild, T afterChild);

    /**
     * Removes the node and all children from the tree.
     * 
     * @param id
     *            id of the node to remove or null if all nodes are to be
     *            removed.
     */
    public void removeNodeRecursively(T id);

    /**
     * Expands all children of the node.
     * 
     * @param id
     *            node which children should be expanded. cannot be null (top
     *            nodes are always expanded!).
     */
    public void expandDirectChildren(T id);

    /**
     * Expands everything below the node specified. Might be null - then expands
     * all.
     * 
     * @param id
     *            node which children should be expanded or null if all nodes
     *            are to be expanded.
     */
    public void expandEverythingBelow(T id);

    /**
     * Collapse children.
     * 
     * @param id
     *            id collapses everything below node specified. If null,
     *            collapses everything but top-level nodes.
     */
    public void collapseChildren(T id);

    /**
     * Returns next sibling of the node (or null if no further sibling).
     * 
     * @param id
     *            node id
     * @return the sibling (or null if no next)
     */
    public T getNextSibling(T id);

    /**
     * Returns previous sibling of the node (or null if no previous sibling).
     * 
     * @param id
     *            node id
     * @return the sibling (or null if no previous)
     */
    public T getPreviousSibling(T id);

    /**
     * Checks if given node is already in tree.
     * 
     * @param id
     *            id of the node
     * @return true if node is already in tree.
     */
    public boolean isInTree(T id);

}
