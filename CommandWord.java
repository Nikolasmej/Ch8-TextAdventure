/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * Added LOOOK, EAT, BACK. CHARGE & FIRE commands.
 * 
 * @author  Nicolas M
 * @version 1
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string. Added LOOOK, EAT  & BACK commands.
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"),
    EAT("eat"), BACK("back"), CHARGE("charge"), FIRE("fire");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
