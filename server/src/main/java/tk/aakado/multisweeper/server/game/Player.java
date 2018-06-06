package tk.aakado.multisweeper.server.game;

import java.util.UUID;

public class Player {

    private String name;
    private UUID uuid;

    public Player(String name) {
        this.name = name;
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
