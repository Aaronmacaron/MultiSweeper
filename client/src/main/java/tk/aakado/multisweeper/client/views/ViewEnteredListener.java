package tk.aakado.multisweeper.client.views;

/**
 * Listener for views.
 * The goal is that the view can react on the event when the view is shown.
 */
public interface ViewEnteredListener {

    /**
     * Invoked every time the view is shown to the users.
     */
    void viewEntered();

}
