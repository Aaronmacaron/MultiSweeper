package tk.aakado.multisweeper.server;

public interface Game {

    void addPlayer(Player player);

    void discoverField(Player player, FieldCords fieldCords);

    void flagField(Player player, FieldCords fieldCords);

    void start(Player player);

    void setNumberOfMines(Player player, int numberOfMines);

    void setFieldWidth(Player player, int width);

    void setFieldHeight(Player player, int height);

    void setPassword(Player player, String password);

    void disconnect(Player player);

    void restart(Player player);

    void reconfigure(Player player);

}
