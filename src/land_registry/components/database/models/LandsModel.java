package land_registry.components.database.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import land_registry.components.ui.utils.FormNodeGroup;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class LandsModel extends CollectionModel {
    private final ObjectId regionId;
    private final ObjectId ownerId;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty plottage;
    private final SimpleBooleanProperty forConstruction;

    public LandsModel(@NotNull Document document) {
        this(
                document.getObjectId("_id"), document.getObjectId("regionId"),
                document.getObjectId("ownerId"), document.getDouble("plottage"),
                document.getBoolean("forConstruction"), document.getDouble("price")
        );
    }

    public LandsModel(
            ObjectId id, ObjectId regionId, ObjectId ownerId,
            double plottage, boolean forConstruction, double price
    ) {
        super(id);
        this.regionId = regionId;
        this.ownerId = ownerId;
        this.price = new SimpleDoubleProperty(price);
        this.plottage = new SimpleDoubleProperty(plottage);
        this.forConstruction = new SimpleBooleanProperty(forConstruction);
    }

    public ObjectId getOwnerId() {
        return ownerId;
    }

    public ObjectId getRegionId() {
        return regionId;
    }

    public double getPlottage() {
        return plottage.get();
    }

    public double getPrice() {
        return price.get();
    }

    public boolean getForConstruction() {
        return forConstruction.get();
    }

    @Override
    public FormNodeGroup getFormNodeGroup() {
        FormNodeGroup formNodeGroup = super.getFormNodeGroup();

        ChoiceBox<RegionsModel> regionChoiceBox = new ChoiceBox<>();
        ChoiceBox<LandOwnersModel> ownerChoiceBox = new ChoiceBox<>();

        TextField priceField = new TextField();
        priceField.setId("price");
        priceField.setText(Double.toString(getPrice()));
        priceField.setPromptText("Enter a price of land...");

        TextField plottageField = new TextField();
        plottageField.setId("plottage");
        plottageField.setText(Double.toString(getPlottage()));
        plottageField.setPromptText("Enter a plottage of land...");

        ToggleButton forConstructionToggleButton = new ToggleButton();
        forConstructionToggleButton.setId("forConstruction");
        forConstructionToggleButton.setText(getForConstruction() ? "true" : "false");
        forConstructionToggleButton.setSelected(getForConstruction());

        formNodeGroup.append("regionId", regionChoiceBox);
        formNodeGroup.append("ownerId", ownerChoiceBox);
        formNodeGroup.append("price", priceField);
        formNodeGroup.append("plottage", plottageField);
        formNodeGroup.append("For construction", forConstructionToggleButton);

        return formNodeGroup;
    }
}
