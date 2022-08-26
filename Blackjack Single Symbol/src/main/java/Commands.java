import java.util.HashMap;
import java.util.Map;

/**
 *  Stores the valid commands for play and quit.
 */
public class Commands {
    /**
     * Valid commands for play.
     */
    private final Map<String, AvailableCommand> validPlayCommands;
    /**
     * Valid commands for quit.
     */
    private final Map<String, AvailableCommand> validQuitCommands;

    /**
     * Generates valid commands for play and quit.
     */
    public Commands() {
        validPlayCommands = new HashMap<>();
        generateValidPlayCommands();
        validQuitCommands = new HashMap<>();
        generateValidQuitCommands();
    }

    /**
     * Generates valid commands for play.
     */
    private void generateValidPlayCommands(){
        validPlayCommands.put("hit", AvailableCommand.HIT);
        validPlayCommands.put("stay", AvailableCommand.STAY);
        validPlayCommands.put("cheat", AvailableCommand.CHEAT);
        validPlayCommands.put("shuffle", AvailableCommand.SHUFFLE);
        validPlayCommands.put("peek", AvailableCommand.PEEK);
        validPlayCommands.put("quit", AvailableCommand.QUIT);
    }
    /**
     * Generates valid commands for quit.
     */
    private void generateValidQuitCommands(){
        validQuitCommands.put("yes", AvailableCommand.YES);
        validQuitCommands.put("no", AvailableCommand.QUIT);
        validQuitCommands.put("y", AvailableCommand.YES);
        validQuitCommands.put("n", AvailableCommand.QUIT);
        validQuitCommands.put("quit", AvailableCommand.QUIT);
    }

    public AvailableCommand getPlayCommand(final String input){
        final AvailableCommand command = validPlayCommands.get(input.toLowerCase());
        return command != null ? command : AvailableCommand.UNKNOWN;
    }

    public AvailableCommand getQuitCommand(final String input){
        final AvailableCommand command = validQuitCommands.get(input.toLowerCase());
        return command != null ? command : AvailableCommand.UNKNOWN;
    }
    public boolean isQuitCommand(final String input){
        return validQuitCommands.containsKey(input.toLowerCase());
    }
}
