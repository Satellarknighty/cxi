import java.util.HashMap;
import java.util.Map;

public class Command {
    private final Map<String, AvailableCommand> validCommands;

    public Command() {
        validCommands = new HashMap<>();
        validCommands.put("[Hh]it", AvailableCommand.HIT);
        validCommands.put("[Ss]tay", AvailableCommand.STAY);
        validCommands.put("")
    }
}
