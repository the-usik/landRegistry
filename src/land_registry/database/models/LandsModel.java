package land_registry.database.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import land_registry.ui.utils.FormNodeGroup;
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
        regionChoiceBox.setId("regionId");

        ChoiceBox<LandOwnersModel> ownerChoiceBox = new ChoiceBox<>();
        ownerChoiceBox.setId("ownerId");

        TextField priceField = new TextField();
        priceField.setId("price");
        priceField.setText(Double.toString(getPrice()));
        priceField.setPromptText("Enter a price of land...");

        TextField plottageField = new TextField();
        plottageField.setId("plottage");
        plottageField.setText(Double.toString(getPlottage()));
        plottageField.setPromptText("Enter a area size of land...");

        ChoiceBox<Boolean> forConstructionChoiceBox = new ChoiceBox<>();
        forConstructionChoiceBox.setId("forConstruction");
        forConstructionChoiceBox.getItems().addAll(true, false);
        forConstructionChoiceBox.setValue(getForConstruction());

        formNodeGroup.append("Region", regionChoiceBox);
        formNodeGroup.append("Owner", ownerChoiceBox);
        formNodeGroup.append("Price", priceField);
        formNodeGroup.append("Area size", plottageField);
        formNodeGroup.append("For construction", forConstructionChoiceBox);

        return formNodeGroup;
    }

    public static FormNodeGroup createFormNodeGroup() {
        FormNodeGroup formNodeGroup = new FormNodeGroup();

        ChoiceBox<RegionsModel> regionChoiceBox = new ChoiceBox<>();
        regionChoiceBox.setId("regionId");

        ChoiceBox<LandOwnersModel> ownerChoiceBox = new ChoiceBox<>();
        ownerChoiceBox.setId("ownerId");

        TextField priceField = new TextField();
        priceField.setId("price");
        priceField.setPromptText("Enter a price of land...");

        TextField plottageField = new TextField();
        plottageField.setId("plottage");
        plottageField.setPromptText("Enter a area size of land...");

        ChoiceBox<Boolean> forConstructionChoiceBox = new ChoiceBox<>();
        forConstructionChoiceBox.setId("forConstruction");
        forConstructionChoiceBox.getItems().addAll(true, false);

        formNodeGroup.append("Region", regionChoiceBox);
        formNodeGroup.append("Owner", ownerChoiceBox);
        formNodeGroup.append("Price", priceField);
        formNodeGroup.append("Area size", plottageField);
        formNodeGroup.append("For construction", forConstructionChoiceBox);

        return formNodeGroup;
    }
}
