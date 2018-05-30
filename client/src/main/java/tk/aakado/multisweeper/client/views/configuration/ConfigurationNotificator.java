package tk.aakado.multisweeper.client.views.configuration;

import java.util.List;

public interface ConfigurationNotificator {

    void playerDisconnected(String players);

    void playerConnected(String players);

    void gameStarted();

}
