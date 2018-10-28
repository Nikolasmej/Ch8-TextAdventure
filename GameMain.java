
/**
 * Class GameMain - 
 *
 * This class is part of the "Corp Adventure" application. 
 * "Corp Adventure" is a very simple, text based adventure game.  
 *
 * It has only main method with an object of Game class to call
 * the play method.
 * 
 * @author  Nicolas M.
 * @version 1.
 */
public class GameMain 
{
    public static void main()
    {
        //object of type Game
        Game game = new Game();
        
        //call the play() method
        game.play();
    }
    
}