package tk.aakado.multisweeper.shared.connection.dtos;

public class DisconnectDTO {

    private String player;
    private boolean isAdmin;

    public DisconnectDTO(String player, boolean isAdmin) {
        this.player = player;
        this.isAdmin = isAdmin;
    }

    public String getPlayer() {
        return player;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
