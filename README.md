## Introduction
This is a small widget that provides quite configurable tree view list. It is based on standard android list view. 

Note that using generic tree is probably not the best approach for Android (or any other mobile) UI. 
See http://developer.android.com/design/index.html for all the control that it makes sense to use. 
Thus tree view should be considered as deprecated. 

The widget is fully configurable - with your custom adapter you can provide your own implementation of the item views - even completely different implementation of item views depending on the tree level you are at. 
Implementation follows best approach of Adapters from android so views at the same tree level are reusable. 

## Usage

The whole project can be included as external android library. 
After unsetting the "isLibrary?" flag, the project can also be compiled and installed on its own - providing demo application that presents capability of the widget. 
It shows how the tree behaves dynamically including explanding and collapsing nodes for many/all node, 
providing context menu for the tree, custom tree view with checkboxes only available for leaf nodes, 
custom colouring and different text sizes for text for different levels of the tree (albeit ugly) of the tree. 
The activity handling it is [TreeViewListDemo.java](https://github.com/Polidea/tree-view-list-android/blob/master/src/pl/polidea/treeview/demo/TreeViewListDemo.java), 
adapters used are [SimpleStandardAdapter.java](https://github.com/Polidea/tree-view-list-android/blob/master/src/pl/polidea/treeview/demo/SimpleStandardAdapter.java) and extending it, 
colorful [FancyColouredVariousSizesAdapter.java](https://github.com/Polidea/tree-view-list-android/blob/master/src/pl/polidea/treeview/demo/FancyColouredVariousSizesAdapter.java). 
Layout for tree items is defined in [demo_list_item.xml](https://github.com/Polidea/tree-view-list-android/blob/master/res/layout/demo_list_item.xml). 
You can learn from the demo how to use the widget. 
The tree can be configured through XML layout file 
(attributes are defined in [attrs.xml](https://github.com/Polidea/tree-view-list-android/blob/master/res/values/attrs.xml) ). 
