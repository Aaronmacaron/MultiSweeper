package tk.aakado.multisweeper.shared.connection;

import com.google.gson.Gson;

/**
 * This Class represents a Action that can be sent through a Connector. This class is a
 * <a href="https://en.wikipedia.org/wiki/Plain_old_Java_object">POJO</a>.
 * A Action contains a ActionType and params. The Params property is a object and can thus be any kind data.
 */
public class Action {

    private ActionType actionType;
    private Object params;

    /**
     * Constructor
     * @param actionType ActionType of Action
     * @param params Params of Action
     */
    public Action(ActionType actionType, Object params) {
        this.actionType = actionType;
        this.params = params;
    }

    /**
     * Getter for actionType
     * @return actionType
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * Getter for params
     * @return params
     */
    public Object getParams() {
        return params;
    }

    /**
     * Converts Action to Json String
     * @return Json String
     */
    public String toJson() {
        return new Gson().toJson(this);
    }

}
