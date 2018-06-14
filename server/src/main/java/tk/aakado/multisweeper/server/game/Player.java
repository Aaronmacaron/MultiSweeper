package tk.aakado.multisweeper.server.game;

import java.util.UUID;

public class Player {

    private final String name;
    private final UUID uuid;

    public Player(String name) {
        this(name, UUID.randomUUID());
    }

    public Player(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player other = (Player) obj;
            return this.uuid.equals(other.uuid);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.uuid);
    }

}
