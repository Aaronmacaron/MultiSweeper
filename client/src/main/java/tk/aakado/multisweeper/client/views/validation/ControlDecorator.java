package tk.aakado.multisweeper.client.views.validation;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.Control;

/**
 * A Decorator for validation purposes
 */
public interface ControlDecorator {

    /**
     * Decorate the control
     *  @param control        Control to decorate
     * @param isValid decoration needed
     */
    void decorate(Control control, boolean isValid);

    /**
     * Set a decoration which updates automaticaly
     *  @param control        Control to decorate
     * @param validProperty decoration needed property
     */
    void initDecoration(Control control, BooleanProperty validProperty);

    /**
     * Set a decoration which updates automaticaly
     *  @param control        Control to decorate
     * @param validProperty decoration needed property
     */
    void initDecoration(Control control, ReadOnlyBooleanProperty validProperty);

    /**
     * Add a cssClass for a component
     *  @param javaClassName Class name of the component which should be decorated
     * @param cssClassNames  css Class with the decoration*/
    void setDecorationClasses(Class<? extends Control> javaClassName, String... cssClassNames);

    /**
     * Remove a cssClass for a Component
     *
     * @param javaClassName Class name of the component which shouldn't be decorated
     */
    void removeDecorationClass(Class<? extends Control> javaClassName);

    /**
     * Set a decoration which updates automaticaly
     *  @param control        Control to decorate
     * @param validBinding decoration needed binding
     */
    void initDecoration(Control control, BooleanBinding validBinding);

}
