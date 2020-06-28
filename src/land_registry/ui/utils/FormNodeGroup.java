package land_registry.ui.utils;

import javafx.scene.Node;

import java.util.ArrayList;

public class FormNodeGroup {
    private final ArrayList<FormNode> formNodes;

    public FormNodeGroup() {
        this(new ArrayList<>());
    }

    public FormNodeGroup(ArrayList<FormNode> formNodeArrayList) {
        formNodes = formNodeArrayList;
    }

    public ArrayList<FormNode> getFormNodes() {
        return formNodes;
    }

    public void append(String name, Node node) {
        formNodes.add(new FormNode(name, node));
    }

    public static FormNodeGroup createFormNodeGroup(ArrayList<FormNode> formNodes) {
        return new FormNodeGroup(formNodes);
    }
}
