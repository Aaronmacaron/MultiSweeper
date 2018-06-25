package tk.aakado.multisweeper.server.connection.handler;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.server.connection.ServerMessage;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

public class DisconnectedHandler {

    /**
     * This method handles the disconnection of a player.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.DISCONNECT)
    public void onDisconnect(ServerMessage message) {
        Server.getGameManager().removePlayer(message.getSender());
        // TODO: Remove connection from connector
        Logger.get(this).info("Client {} disconnected from server.", message.getSender().getSocket().getRemoteSocketAddress());
    }

}
