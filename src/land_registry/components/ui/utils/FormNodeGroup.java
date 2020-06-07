package land_registry.components.ui.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    public static FormNodeGroup createFormNodeGroup(ArrayList<FormNode> formNodes) {
        return new FormNodeGroup(formNodes);
    }
}
