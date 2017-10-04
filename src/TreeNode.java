import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sophia on 11/15/2016.
 */
public class TreeNode {
    public ArrayList<TreeNode> children;
    public TreeNode parent;
    public int data;
    public int height;

    TreeNode(int data, TreeNode parent) {
        this.parent = parent;
        this.data = data;
        this.height = 1;
        children = new ArrayList<TreeNode>();
    }

    TreeNode(TreeNode parent) {
        this.parent = parent;
        this.height = 1;
        children = new ArrayList<TreeNode>();
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public boolean isParentof(int findData) {
        boolean found = false;
        for(TreeNode tree : children) {
            if(tree.data == findData) {
                return true;
            }
            else {
                Iterator<TreeNode> iterator = tree.children.iterator();
                TreeNode next;
                if(iterator.hasNext()) {
                    next = iterator.next();
                    for(int i=0; i<tree.children.size(); i++) {
                        if(next.parent!=null)  {
                            TreeNode.removeParent(next);
                        }
                        next.parent = this;
                        if(next.data == findData) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public static boolean hasChildren(TreeNode node) {
        if (node.children != null) return true;
        return false;
    }


    public void addChild(TreeNode node) {
        children.add(node);
        int max = 0;
        for(TreeNode tree : children) {
            if(tree.height > max) {
                max = tree.height;
            }
        }
        height = max + 1;
    }

    public void remove(int node) {
        for(TreeNode tree : children) {
            if(tree.data == node) {
                children.remove(tree);
            }
        }
    }

    public static void removeParent(TreeNode tree) {
        tree.parent.remove(tree.data);
    }


    public void setData(int data) {
        this.data = data;
    }

}
