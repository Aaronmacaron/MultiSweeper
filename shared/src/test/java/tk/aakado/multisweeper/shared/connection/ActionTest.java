package tk.aakado.multisweeper.shared.connection;

import static org.junit.Assert.*;

public class ActionTest {

    @org.junit.Test
    public void getActionType() {
        ActionType actionType = ActionType.CLICK;
        Action action = new Action(actionType, null);
        assertEquals(actionType, action.getActionType());
    }

    @org.junit.Test
    public void getParams() {
        Object object = "Example Object";
        Action action = new Action(null, object);
        assertEquals(object, action.getParams());
    }

    @org.junit.Test
    public void toJson() {
        ActionType actionType = ActionType.CLICK;
        Object params = "Example Object";
        Action action = new Action(actionType, params);

        String expectedJson = "{\"actionType\":\"CLICK\",\"params\":\"Example Object\"}";
        assertEquals(action.toJson(), expectedJson);
    }
}