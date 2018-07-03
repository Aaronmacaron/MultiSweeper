package tk.aakado.multisweeper.client.views.validation;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.Control;

public class ControlDecoratorImpl implements ControlDecorator {

    private Map<Class, String[]> validatoinClasses;

    public ControlDecoratorImpl() {
        validatoinClasses = new HashMap<>();
    }

    @Override
    public void setDecorationClasses(Class<? extends Control> javaClassName, String... cssClassNames) {
        this.validatoinClasses.put(javaClassName, cssClassNames);
    }

    @Override
    public void removeDecorationClass(Class<? extends Control> javaClassName) {
        this.validatoinClasses.remove(javaClassName);
    }

    @Override
    public void initDecoration(Control control, BooleanBinding validBinding) {
        validBinding.addListener((observable, oldValue, newValue) -> {
            decorate(control, newValue);
        });
    }

    @Override
    public void decorate(Control control, boolean isValid) {
        if (isValid) {
            control.getStyleClass().removeAll(validatoinClasses.get(control.getClass()));
        } else {
            control.getStyleClass().addAll(validatoinClasses.get(control.getClass()));
        }

    }

    @Override
    public void initDecoration(Control control, BooleanProperty validProperty) {
        initDecoration(control, (ReadOnlyBooleanProperty) validProperty);
    }

    @Override
    public void initDecoration(Control control, ReadOnlyBooleanProperty validProperty) {
        validProperty.addListener((observable, oldValue, newValue) -> {
            decorate(control, newValue);
        });
    }


}
