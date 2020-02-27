package Backend;


import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class DataObject {

//    private Rectangle rect;
    private float data;
    private Label dataLabel;
    private Label name;
    private VBox vbox;

    public DataObject(float data, Label username, VBox vbox) {
        this.data = data;
        name = username;
        this.vbox = vbox;

//        rect = new Rectangle();

//        rect.setWidth(40);
//        rect.setHeight(20);

        if (data == 1.0) this.vbox.setStyle("-fx-background-color: #FF435F");
            //rect.setFill(Color.hsb(330, 1, 1));
//        if (0.90 <= data && data < 1.0) rect.setFill(Color.hsb(300, 1, 1));
//        if (0.80 <= data && data < 0.90) rect.setFill(Color.hsb(270, 1, 1));
//        if (0.70 <= data && data < 0.80) rect.setFill(Color.hsb(240, 1, 1));
//        if (0.60 <= data && data < 0.70) rect.setFill(Color.hsb(210, 1, 1));
//        if (0.50 <= data && data < 0.60) rect.setFill(Color.hsb(180, 1, 1));
//        if (0.40 <= data && data < 0.50) rect.setFill(Color.hsb(150, 1, 1));
//        if (0.30 <= data && data < 0.40) rect.setFill(Color.hsb(120, 1, 1));
//        if (0.20 <= data && data < 0.30) rect.setFill(Color.hsb(90, 1, 1));
//        if (0.10 <= data && data < 0.20) rect.setFill(Color.hsb(60, 1, 1));
//        if (0.0 < data && data < 0.10) rect.setFill(Color.hsb(30, 1, 1));
//        if (0.0 == data) rect.setFill(Color.hsb(0, 1, 1));


        dataLabel = new Label(Float.toString(data));

    }

    public VBox getVbox() {
        return this.vbox;
    }

    public Label getLabel() {
        return dataLabel;
    }

    public Label getName() {
        return name;
    }

    public float getData() {
        return data;
    }

//    public Rectangle getRect() {
//        return rect;
//    }
}