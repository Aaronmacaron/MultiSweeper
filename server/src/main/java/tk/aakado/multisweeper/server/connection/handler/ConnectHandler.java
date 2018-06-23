package tk.aakado.multisweeper.server.connection.handler;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

public class ConnectHandler {

    /**
     * This method is called when client tries to connect to server. For now this actionHandler doesn't implement any
     * logic and just responses with a confirmation that the connection was established successfully. Later this action
     * handler can be used to implement for example DDOS/Spam protection.
     * @param message The message sent by the client.
     */
    @ActionHandler(actionType = ActionType.CONNECT)
    public void onConnect(ServerMessage message) {
        Action response = new Action(ActionType.CONNECTED, Server.getGameManager().getAllGameIds());
        message.getConnector().send(response);
    }

}
