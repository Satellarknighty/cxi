import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandsTest {
    @Test
    void testPlayCommand() {
        Commands testCommands = new Commands();
        assertThat(testCommands.getPlayCommand("hit")).isEqualTo(AvailableCommand.HIT);
        assertThat(testCommands.getPlayCommand("Lmao")).isEqualTo(AvailableCommand.UNKNOWN);
    }

    @Test
    void testYesCommand() {
        assertThat(new Commands().getQuitCommand("YES")).isEqualTo(AvailableCommand.YES);
    }
}
