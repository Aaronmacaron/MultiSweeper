package tk.aakado.multisweeper.client.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.Parent;
import javafx.scene.Scene;
import tk.aakado.multisweeper.shared.Logger;

/**
 * This class carries the information of a view.
 * It has the same purpose as the {@link ViewTuple} but it extends it with a scene containing the view
 * and the notificator that corresponds to the view model of this view. The notificator <strong>has</strong>
 * to be implemented by the view model.
 * @param <ViewType>        The generic type of the view
 * @param <ViewModelType>   The generic type of the view model
 * @param <ViewNotificator> The generic type of the notificator
 */
public class MultiSweeperView<ViewType extends FxmlView<? extends ViewModelType>, ViewModelType extends ViewModel, ViewNotificator extends Notificator> {

    private final ViewTuple<? extends ViewType, ViewModelType> tuple;
    private final Scene scene;

    /**
     * @param viewType The view that should be loaded.
     */
    public MultiSweeperView(Class<? extends ViewType> viewType) {
        this.tuple = FluentViewLoader.fxmlView(viewType).load();
        this.scene = new Scene(this.tuple.getView());
    }

    /**
     * @see ViewTuple#getCodeBehind()
     */
    public ViewType getCodeBehind() {
        return this.tuple.getCodeBehind();
    }

    /**
     * @see ViewTuple#getView()
     */
    public Parent getView() {
        return this.tuple.getView();
    }

    /**
     * @see ViewTuple#getViewModel()
     */
    public ViewModelType getViewModel() {
        return this.tuple.getViewModel();
    }

    /**
     * Retrieve the notificator of this view.
     * Every view model has a notificator as an interface. The view model has to implement the specified notificator.
     * @return The notificator of the view model.
     */
    @SuppressWarnings("unchecked")
    public ViewNotificator getNotificator() {
        ViewNotificator notificator;
        try {
            // As per convention this cast is safe
            notificator = (ViewNotificator) getViewModel();
        } catch (ClassCastException e) {
            Logger.get(this).error("The specified notificator has to be implemented in the specified view model ({})",
                    getViewModel().getClass(), e);
            throw e;
        }
        return notificator;
    }

    /**
     * Get the scene of this multi sweeper view with the View as root.
     * @return The scene of this view.
     */
    public Scene getScene() {
        return this.scene;
    }

}
