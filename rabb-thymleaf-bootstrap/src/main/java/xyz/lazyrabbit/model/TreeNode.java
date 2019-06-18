package xyz.lazyrabbit.model;

import java.util.List;

public class TreeNode {
    private int id;
    private String text;
    private List<TreeNode> nodes;
    private Boolean lazyLoad;
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TreeNode(int id, String text, List<TreeNode> nodes) {
        super();
        this.id = id;
        this.text = text;
        this.nodes = nodes;
    }

    public TreeNode(int id, String text) {
        super();
        this.id = id;
        this.text = text;
        this.setLazyLoad(true);
    }

    public TreeNode() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Boolean getLazyLoad() {
        return lazyLoad;
    }

    public void setLazyLoad(Boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

}
