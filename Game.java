/** 
 *  This class is the main class of the "World of Corp" application. 
 *  "World of Corp" is a very simple, text based adventure game.  Users 
 *  can walk around some company scenery. You can type and ask for help.
 *  You can type look to see location. You can eat during your journey.
 *  You can type back to go back outside.
 *  You can charge and fire your beamer. There is a time limit, it is based on moves,
 *  you have fifteen moves in total. In the company each room contains a unique item
 *  with description and weight.
 *  
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Nicolas M.
 * @version 1
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room prevRoom;
    private Room roomStack[];    //stack to go back more than once.
    private int top;
    private int counter = 0;    // int for time limit counter, # of commands. 
    private Room chargeRoom;    //beamer device to charge and fire.
    private Room fireRoom;
    /**
     * Create the game and initialise its internal map.
     * create room stack with operator and size (to go back more than once)
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomStack = new Room [500];
        top =-1;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, reception, cafeteria, financing, office,
             lab, production, mail, bathroom,shipping, gym, conference;
      
        // create the rooms
        outside = new Room("outside the main entrance of the "+
                            "corp", "nothing ",0);
        reception = new Room("in the reception "+
                            "corp", "badge ID. ",25);
        cafeteria = new Room("in the cafeteria "+
                            "corp", "coffee. ",250);
        financing = new Room("in the finance office "+
                            "corp", "pay check. ",20);
        office = new Room("in the executive's office "+
                            "corp", "folder. ",300);
        lab = new Room("in the lab "+
                       "corp", "prduction samples ",1000);
        production = new Room("in the production department "+
                              "corp", "fineshed goods ",3200); 
        mail = new Room("in the mail room "+
                            "corp", "mail. ",750);
        bathroom = new Room("in the bathroom "+
                            "sanitizer", "mail. ",1);
        shipping= new Room("in the shipping department "+
                            "boxes", "mail. ",1200);
        gym = new Room("in the gym "+
                            "corp", "energy drink. ",500);   
        conference = new Room("in the conference room "+
                               "corp", "tablet. ",1800);                    
                            
        // initialise room exits
        
        outside.setExit("south", reception);

        reception.setExit("east", cafeteria);
        reception.setExit("north", outside);
        reception.setExit("west", financing);
        reception.setExit("south", lab);
        
        cafeteria.setExit("west", reception);
        cafeteria.setExit("south", mail);
        cafeteria.setExit("east", bathroom);

        financing.setExit("east", reception );
        financing.setExit("south", office);

        office.setExit("north", financing);
        office.setExit("south", conference);
        
        conference.setExit("north", office);
        
        lab.setExit("south", production);
        lab.setExit("north", reception);
        lab.setExit("east", mail);
        
        production.setExit("north",lab);
        production.setExit("south",gym);
        production.setExit("east",shipping);
        
        mail.setExit("north", cafeteria);
        mail.setExit("south", shipping);
        mail.setExit("west", lab);
        
        bathroom.setExit("west", cafeteria);
        
        shipping.setExit("north", mail);
        shipping.setExit("west", production);
        shipping.setExit("south", outside);
        
        gym.setExit("north", production);
        gym.setExit("south", outside);

        currentRoom = outside;  // start game outside
        prevRoom = null;
        
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  added time limit to player. count the number of
     *  commads entered, when reach max # display message.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            if (counter > 15)   
            {
            System.out.println("Sorry, time is over");
            finished = true;
            }
        else
           {
            Command command = parser.getCommand();
            finished = processCommand(command);
            counter++;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * Added LOOK and command
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
            
            case BACK:
                backRoom();
                break;    
                
            case CHARGE:
                chargeBeamer();
                break;
                
            case FIRE:
                fireBeamer();
                break;    

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            prevRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * LOOK method added. . 
     * "look" describe the current room status.
     * give description.
     */
    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /** 
     * EAT method added. . 
     * "eat" re-charge energy.
     * give description.
     */
    private void eat() 
    {
        System.out.println(" You have eaten now and your are not " +
                            "hungry any more. " );
    }
    
    /** 
     * Back method added. . 
     * To go to the previous room and
     * give the room description.
     * call pop() to go back more than once.
     */
    private void backRoom() 
    {
        currentRoom = prevRoom;
        currentRoom = pop();
        if (currentRoom != null)
        System.out.println(currentRoom.getLongDescription());
    }
    
    /** 
     *Method to add the current room to satck
     *Book and Online references
     */
    private void push(Room room) 
    {
       
        if (top== roomStack.length-1)
        System.out.println("Room is full");
        else
            roomStack[++top] = room;
    }
    
    /** 
     *Method returns the room at the top of roomStack
     *@return Room or null
     *Book and Online references
     */
    private Room pop() 
    {
       
        if (top < 0)    {
        System.out.println("Sorry, you are outside the building and "+
                            " there is no previous room to go back anymore. ");
        return null;
    }
        else
        return roomStack [top--];
    }
    
    /** 
     *Method to charge beamer
     *Book and Online references
     */
    private void chargeBeamer() 
    {
        chargeRoom = currentRoom;
        System.out.println("Beamer is charged");
    }
    
    /** 
     *Method to fire beamer
     *Book and Online references
     */
    private void fireBeamer() 
    {
        System.out.println("Beamer is fired");
        currentRoom = chargeRoom;
        
    }
    
}
