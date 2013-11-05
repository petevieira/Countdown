--------------------
RUNNING THE JAR FILE
--------------------

1. To just run the compiled .jar file type the following command

	java -jar CountdownApp.jar

---------------------
COMPILING AND RUNNING
---------------------

1. Open the command prompt

2. Change the directory to the Countdown folder

3. Type the following command to compile the code and run the program

	./run

------------------
PAUSING & RESETING
------------------

1. Pausing
    - Left-click in the countdown frame to pause
    - Left-click again to unpause

2. Resetting
    - Left-click in the countdown frame
    - Right-click in the countdown frame
    - Left-click the console frame
    - Hit RETURN in the console frame
    - Now left-click in the countdown frame to start again
    - Left-click in the console frame to start typing again

-----------------
EDITING PARAMETERS
-----------------

1. To change the console text font open src/CountdownApp.java
   and find the following line and change "Times-20" to some
   other font and size of your liking:

	// PARAMETERS YOU CAN CHANGE
	
	// Console font
	private String CONSOLE_FONT = "Times-20";

2. To change the countdown and error text fonts open
   src/CountdownGui.java and find the following section
   and change the parameters to your liking:

	// PARAMETERS YOU CAN CHANGE
	
	// Error parameters
	private String ERROR_TEXT1   = "Error -";
	private String ERROR_TEXT2   = "Jump Decelerator";
	private String ERROR_TEXT3   = "Control System Failure";
	private int ERROR_FONT_SIZE  = 60;
	private String ERROR_FONT    = "Arial-" + ERROR_FONT_SIZE;
	private double FLASH_TIME    = 3;

	// Countdown clock parameters
	private int CLOCK_FONT_SIZE  = 100;
	private String CLOCK_FONT    = "Arial-" + CLOCK_FONT_SIZE;
	private int startMin         = 9;
	private int startSec         = 37;

