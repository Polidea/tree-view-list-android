package pl.polidea.treeview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory manager of tree state.
 * 
 * <T> type of identifier
 */
public class InMemoryTreeStateManager<T extends Object> implements TreeStateManager<T> {
    private final Map<T, InMemoryTreeNode<T>> allNodes = new HashMap<T, InMemoryTreeNode<T>>();
    private final InMemoryTreeNode<T> topSentinel = new InMemoryTreeNode<T>(null, null, -1, true);

    private boolean visibleByDefault = true;

    /**
     * If true new nodes are visible by default.
     * 
     * @param visibleByDefault
     *            if true, then newly added nodes are expanded by default
     */
    public void setVisibleByDefault(final boolean visibleByDefault) {
        this.visibleByDefault = visibleByDefault;
    }

    private InMemoryTreeNode<T> getNodeFromTreeOrThrow(final T id) {
        if (id == null) {
            throw new NodeNotInTreeException("(null)");
        }
        final InMemoryTreeNode<T> node = allNodes.get(id);
        if (node == null) {
            throw new NodeNotInTreeException(id.toString());
        }
        return node;
    }

    private InMemoryTreeNode<T> getNodeFromTreeOrThrowAllowRoot(final T id) {
        if (id == null) {
            return topSentinel;
        }
        return getNodeFromTreeOrThrow(id);
    }

    private void expectNodeNotInTreeYet(final T id) {
        final InMemoryTreeNode<T> node = allNodes.get(id);
        if (node != null) {
            throw new NodeAlreadyInTreeException(id.toString(), node.toString());
        }
    }

    @Override
    public synchronized TreeStateManager.TreeNodeInfo<T> getNodeInfo(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrow(id);
        return new TreeNodeInfo<T>(id, node.level, node.getChildrenListSize() > 0, node.isVisible());
    }

    @Override
    public synchronized List<T> getChildren(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        return node.getChildIdList();
    }

    @Override
    public synchronized T getParent(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrow(id);
        return node.parent;
    }

    @Override
    public synchronized void addBeforeChild(final T id, final T newChild, final T beforeChild) {
        expectNodeNotInTreeYet(newChild);
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        // top nodes are always expanded.
        if (beforeChild == null) {
            node.add(0, newChild, visibleByDefault);
        } else {
            final int index = node.indexOf(beforeChild);
            node.add(index == -1 ? 0 : index, newChild, visibleByDefault);
        }
    }

    @Override
    public synchronized void addAfterChild(final T id, final T newChild, final T afterChild) {
        expectNodeNotInTreeYet(newChild);
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        if (afterChild == null) {
            node.add(node.getChildrenListSize(), newChild, visibleByDefault);
        } else {
            final int index = node.indexOf(afterChild);
            node.add(index == -1 ? node.getChildrenListSize() : index, newChild, visibleByDefault);
        }
    }

    @Override
    public synchronized void removeNodeRecursively(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        removeNodeRecursively(node);
    }

    private void removeNodeRecursively(final InMemoryTreeNode<T> node) {
        for (final InMemoryTreeNode<T> child : node.getChildren()) {
            removeNodeRecursively(child);
        }
        node.clearChildren();
        if (node.id != null) {
            allNodes.remove(node.id);
        }
    }

    private void setChildrenVisibility(final InMemoryTreeNode<T> node, final boolean visible, final boolean recursive) {
        for (final InMemoryTreeNode<T> child : node.getChildren()) {
            child.setVisible(visible);
            if (recursive) {
                setChildrenVisibility(child, visible, true);
            }
        }
    }

    @Override
    public synchronized void expandDirectChildren(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        setChildrenVisibility(node, true, false);
    }

    @Override
    public synchronized void expandEverythingBelow(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        setChildrenVisibility(node, true, true);

    }

    @Override
    public synchronized void collapseChildren(final T id) {
        final InMemoryTreeNode<T> node = getNodeFromTreeOrThrowAllowRoot(id);
        setChildrenVisibility(node, false, true);
    }

    @Override
    public synchronized T getNextSibling(final T id) {
        final T parent = getParent(id);
        final InMemoryTreeNode<T> parentNode = getNodeFromTreeOrThrowAllowRoot(parent);
        boolean returnNext = false;
        for (final InMemoryTreeNode<T> child : parentNode.getChildren()) {
            if (returnNext) {
                return child.id;
            }
            if (child.id.equals(id)) {
                returnNext = true;
            }
        }
        return null;
    }

    @Override
    public synchronized T getPreviousSibling(final T id) {
        final T parent = getParent(id);
        final InMemoryTreeNode<T> parentNode = getNodeFromTreeOrThrowAllowRoot(parent);
        final T previousSibling = null;
        for (final InMemoryTreeNode<T> child : parentNode.getChildren()) {
            if (child.id.equals(id)) {
                return previousSibling;
            }
        }
        return null;
    }

    @Override
    public synchronized boolean isInTree(final T id) {
        return allNodes.containsKey(id);
    }

}
