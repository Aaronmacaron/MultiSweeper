package tk.aakado.multisweeper.client.views.configuration;

public interface ConfigurationNotificator {

    void playerDisconnected(String players);

    void playerConnected(String players);

    void gameStarted();

}
