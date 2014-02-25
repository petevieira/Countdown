/*
 * File: CountdownApp.java
 * Name: Peter Vieira
 * ------------------
 */

import acm.io.IOConsole;
import acm.program.*;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.atomic.*;

@SuppressWarnings("serial")
public class CountdownApp extends Program {
	
//--------------------------------------------------------------	
	// PARAMETERS YOU CAN CHANGE
	
	// Console font
    private String CONSOLE_FONT = "SquareFont-20";
//--------------------------------------------------------------	
	
	@SuppressWarnings("unused")
	private String cmdString;
	private static AtomicInteger sharedNum, resetNum;
	private boolean increment = true;
    public static CountdownGui canvas;
    public static IOConsole console;
    public int rn = 1;
    
	public static void main(String[] args)
	{
		sharedNum = new AtomicInteger(0);
        resetNum = new  AtomicInteger(0);
		console = new IOConsole();
		canvas = new CountdownGui(sharedNum, resetNum);
		(new Thread(canvas)).start();
		new CountdownApp().start(args);
	}
	
	public void init()
	{
		console.setFont(CONSOLE_FONT);
		console.setInputColor(Color.WHITE);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.WHITE);
		this.add(console);
		this.add(canvas);
	}
	
    public void run() {
		
    	while(true)
    	{
			cmdString = console.readLine("Enter Command:   ");

            // Check if we need to reset
            int rnTemp = resetNum.get();
            if(rnTemp != rn)
            {
                System.out.println("Clearing console");
                console.clear();
                rn = rnTemp;
                continue;
            }

			console.println("\n\tError!\n");

            // Change shared int whenever we hit RETURN	
			int sn = sharedNum.get();
			if(sn > 1000)
				increment = false;
			else if(sn < -1000)
				increment = true;
			if(increment)
				sharedNum.incrementAndGet();
			else
				sharedNum.decrementAndGet();


    	}
	}
}
