package tk.aakado.multisweeper.shared.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract implementation of Connector interface. This abstract class handles the ActionHandler registering of a
 * connector.
 */
abstract public class AbstractConnector implements Connector {

    /**
     * This List stores the ActionsHandlers.
     */
    protected List<Class> actionHandlers = new ArrayList<>();

    /**
     * Adds new ActionHandler to Connector.
     * @param actionHandler The ActionHandler to add to the Connector.
     * @see ActionHandler
     */
    @Override
    public void addActionHandler(Class actionHandler) {
        actionHandlers.add(actionHandler);
    }

    /**
     * Adds a collection of ActionHandlers to Connector.
     * @param actionHandlers The Collection of ActionHandlers to add to the Connector.
     * @see ActionHandler
     */
    @Override
    public void addAllActionHandlers(Collection<Class> actionHandlers) {
        this.actionHandlers.addAll(actionHandlers);
    }

}
