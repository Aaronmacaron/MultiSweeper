package tk.aakado.multisweeper.logic;

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
    public boolean equals(Object o) {
        //TODO implement
        return false;
    }

}
