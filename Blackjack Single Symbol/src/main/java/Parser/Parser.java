package Parser;

import java.util.Scanner;
/**
 * Parses the input of the player. It deals with inputs for play and for quit.
 */
public class Parser {
    /**
     * Reads the user's input.
     */
    private final Scanner scanner;
    /**
     * To check which string corresponds to which {@link AvailableCommand}.
     */
    private final Commands commands;

    /**
     * Creates an instance of the Command class and also a Scanner class
     * which takes inputs from console.
     */
    public Parser(){
        commands = new Commands();
        scanner = new Scanner(System.in);
    }

    /**
     * To be used during a round of Blackjack Single Symbol.
     *
     * @return  An {@link AvailableCommand} enum corresponding to the user's
     * input. UNKNOWN if the input is not valid.
     */
    public AvailableCommand getPlayCommand(){
        System.out.println("Hit or Stay?");
        final String userInput = scanner.nextLine();
        return commands.getPlayCommand(userInput);
    }

    public Scanner getScanner() {
        return scanner;
    }

    /**
     * To prompt the player if they would like another game. This function will run
     * until the player has entered a valid input.
     *
     * @return  An {@link AvailableCommand} enum corresponding to the user's input.
     */
    public AvailableCommand getQuitCommand(){
        System.out.println("Play again? (Y/N)");
        String userInput = scanner.nextLine();
        while (!commands.isQuitCommand(userInput)) {
            System.out.println("Just type \"Y\" for yes or \"N\" for no man.");
            userInput = scanner.nextLine();
        }
        return commands.getQuitCommand(userInput);
    }

    public void helpDuringPlay(){
        commands.getHelpPlayCommand();
    }

    public void helpDuringQuit(){
        commands.getHelpQuitCommand();
    }
}
