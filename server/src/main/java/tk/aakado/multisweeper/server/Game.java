package tk.aakado.multisweeper.server;

public interface Game {

    void addPlayer(Player player);

    void discoverField(Player player, FieldCords fieldCords);

    void flagField(Player player, FieldCords fieldCords);

    void startNewGame(Player player);

    void configure(Player player, int numberOfMines, int width, int height, String password);

    void disconnect(Player player);

}
