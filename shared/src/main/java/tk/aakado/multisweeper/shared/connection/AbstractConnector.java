package tk.aakado.multisweeper.shared.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract public class AbstractConnector implements Connector {

    private List<Class> actionHandlers = new ArrayList<>();

    @Override
    public void addActionHandler(Class actionHandler) {

    }

    @Override
    public void addAllActionHandlers(Collection<Class> actionsHandlers) {

    }

}
