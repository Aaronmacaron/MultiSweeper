package tk.aakado.multisweeper.client.views.game;

import tk.aakado.multisweeper.logic.Field;

public interface GameNotificator {

    void playerDisconnected(String players);

    void playerConnected(String players);

    void restart();

    void updateField(Field field);

    void finished();
}
