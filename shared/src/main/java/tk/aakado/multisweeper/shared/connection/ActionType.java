package tk.aakado.multisweeper.shared.connection;

/**
 * This Enum contains all possible ActionTypes that can be sent through a Connector.
 */
public enum ActionType {
    CLICK,
    CONNECT,
    DISCONNECT,
    SAVE_PASSWORD,
    START,
    AUTHENTICATE,
    RESTART,
    CONFIGURE_GAME,
    JOIN_GAME,
    PASSWORD_WRONG,
    PASSWORD_NEEDED,
    WRONG_GAME_ID,
    CONNECTED,
    GAME_JOINED,
    CLICKED,
    REQUEST_GAME_INFO,
    DISCONNECTED,
    PLAYER_CONNECTED,
    GAME_STARTED,
    GAME_FINISHED,
    LEAVE_GAME,
    ;
}
