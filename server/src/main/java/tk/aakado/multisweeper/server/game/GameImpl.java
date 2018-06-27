package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.server.Server;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.Action;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.Connection;
import tk.aakado.multisweeper.shared.connection.dtos.DisconnectDTO;
import tk.aakado.multisweeper.shared.connection.dtos.FieldDTO;
import tk.aakado.multisweeper.shared.connection.dtos.GameConfigDTO;
import tk.aakado.multisweeper.shared.connection.dtos.StartInfoDTO;
import tk.aakado.multisweeper.shared.game.FieldState;

import java.util.*;

public class GameImpl implements Game {

    private static final GameConfigDTO DEFAULT_CONFIGURATION = new GameConfigDTO(15, 15, 0.2);

    private List<Player> players = new ArrayList<>();
    private PlayingField currentPlayingField;
    private GameConfigDTO configuration;
    private String password = ""; // Default no password

    @Override
    public boolean addPlayer(Player player, String passwordToCheck) {
        if (hasPlayer(player) || !this.password.equals(passwordToCheck)) {
            return false;
        }
        this.players.add(player);
        return true;
    }

    @Override
    public boolean hasPlayer(Player player) {
        return this.players.contains(player);
    }

    @Override
    public void removePlayer(Player player) {
        if (hasPlayer(player)) {
            this.players.remove(player);

            Optional<Connection> connection = Server.getGameManager().getConnection(player);

            // Inform every other player
            Optional<Player> optionalNewAdmin = getAdmin();
            if (!optionalNewAdmin.isPresent()) {
                return;
            }

            Player newAdmin = optionalNewAdmin.get();

            // Send message to non-admin players
            Action nonAdminAction = new Action(
                    ActionType.DISCONNECTED,
                    new DisconnectDTO(player.toString(), false)
            );
            Connection adminConnection = Server.getGameManager().getConnection(newAdmin)
                    .orElseThrow(() -> new IllegalStateException("The player doesn't belong to connection."));
            List<Connection> except = new ArrayList<>();
            except.add(adminConnection);
            connection.ifPresent(except::add);
            Server.getConnector().sendExcept(nonAdminAction, except);

            // Send message to new admin
            Action adminAction = new Action(
                    ActionType.DISCONNECTED,
                    new DisconnectDTO(player.toString(), true)
            );
            Server.getConnector().sendTo(adminAction, adminConnection);
        }
    }

    @Override
    public void discoverField(Player player, FieldCords fieldCords) {
        requiredGameRunning();
        if (this.currentPlayingField.isValidCoordinate(fieldCords)) {
            List<FieldDTO> changedFields = this.currentPlayingField.discoverField(fieldCords, player);

            Server.getConnector().send(new Action(ActionType.CLICKED, changedFields));

            // if one of the changed fields is an exploded mine, the game is finished
            boolean gameLost = changedFields.stream()
                    .anyMatch(fieldDTO -> fieldDTO.getState() == FieldState.MINE_EXPLODED);

            if (gameLost) {
                Action action = new Action(ActionType.GAME_FINISHED, false);
                List<Connection> players = Server.getGameManager().getAllConnectionsOf(this);
                Server.getConnector().sendTo(action, players);
            } else if (this.currentPlayingField.gameWon()) {
                Action action = new Action(ActionType.GAME_FINISHED, true);
                List<Connection> players = Server.getGameManager().getAllConnectionsOf(this);
                Server.getConnector().sendTo(action, players);
            }
        }
    }

    @Override
    public void flagField(Player player, FieldCords fieldCords) {
        requiredGameRunning();
        if (this.currentPlayingField.isValidCoordinate(fieldCords)) {
            Optional<FieldDTO> flaggedField = this.currentPlayingField.flagField(fieldCords, player);

            if (flaggedField.isPresent()) {
                Action action = new Action(ActionType.CLICKED, Collections.singletonList(flaggedField.get()));
                Server.getConnector().send(action);
            }
        }
    }

    @Override
    public void startNewRound(Player player) {
        if (!checkAdmin(player)) {
            return;
        }

        GameConfigDTO runConfig = this.configuration;
        if (this.configuration == null) {
            runConfig = DEFAULT_CONFIGURATION;
        }

        int width = runConfig.getWidth();
        int height = runConfig.getHeight();
        int mines = (int) Math.round(width * height * runConfig.getMinesPercentage());

        this.currentPlayingField = new PlayingField(width, height, mines);

        // Set configuration on clients
        StartInfoDTO startInfo = new StartInfoDTO(width, height, this.currentPlayingField.getCurrentPlayingFieldState());
        Action update = new Action(ActionType.CONFIGURE_GAME, startInfo);
        GameManager gameManager = Server.getGameManager();
        gameManager.getAllConnectionsOf(this)
                .forEach(c -> Server.getConnector().sendTo(update, c));
    }

    @Override
    public void configure(Player player, GameConfigDTO gameConfigDTO) {
        if (checkAdmin(player)) {
            this.configuration = gameConfigDTO;
        }
    }

    @Override
    public GameConfigDTO getCurrentConfiguration() {
        return this.configuration != null ? this.configuration : DEFAULT_CONFIGURATION;
    }

    @Override
    public void setPassword(Player player, String password) {
        if (checkAdmin(player)) {
            this.password = password;
        }
    }

    @Override
    public List<FieldDTO> getCurrentStateOfGame() {
        requiredGameRunning();
        return this.currentPlayingField.getCurrentPlayingFieldState();
    }

    @Override
    public boolean hasPassword() {
        return ! this.password.isEmpty();
    }

    @Override
    public Optional<Player> getAdmin() {
        if (this.players.size() < 1) {
            return Optional.empty();
        }
        return Optional.of(this.players.get(0));
    }

    @Override
    public boolean isStarted() {
        return this.currentPlayingField != null;
    }

    /**
     * Checks if the game ist running. If not an {@link IllegalStateException} is raised.
     */
    private void requiredGameRunning() {
        if (! isStarted()) {
            throw new IllegalStateException("The game is not running.");
        }
    }

    /**
     * Checks if the given player is the admin of this game.
     * @param player The player to check.
     * @return Whether the player is admin or not.
     */
    private boolean checkAdmin(Player player) {
        if (hasPlayer(player)) {
            //noinspection ConstantConditions
            return player.equals(getAdmin().get());
        }
        Logger.get(this).warn("Player {} is not part of this game and tried to invoke admin action.", player);
        return false;
    }

}
