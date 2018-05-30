package tk.aakado.multisweeper.client.views.connection;

public interface ConnectionNotificator {

    void connected(boolean authRequired);

    void rejected();

}
