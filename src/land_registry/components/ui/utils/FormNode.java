package land_registry.components.ui.utils;

import javafx.scene.Node;

public class FormNode {
    private String name;
    private Node node;

    public FormNode() {
    }

    public FormNode(String name, Node node) {
        setName(name);
        setNode(node);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public Node getNode() {
        return node;
    }
}
