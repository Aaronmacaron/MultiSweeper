package tk.aakado.multisweeper.client.connection;

import com.google.gson.Gson;

public class Action {
    private ActionType actionType;
    private Object params;

    public Action(ActionType actionType, Object params) {
        this.actionType = actionType;
        this.params = params;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Object getParams() {
        return params;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
