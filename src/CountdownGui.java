/*
 * File: CountdownGui.java
 * Name: Peter Vieira
 * ------------------------
 */

import acm.graphics.*;
import java.awt.Color;
import java.util.concurrent.atomic.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class CountdownGui extends GCanvas implements Runnable, MouseListener{
	
//--------------------------------------------------------------	
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
//---------------------------------------------------------------	
	
	
	// Instance Variables
	public GLabel error1, error2, error3, clock;
	private AtomicInteger sharedNum, resetNum;
	private int min = 0, sec = 0, ts = 0;
	boolean running = true;
	int LEFT_CLICK = 1;
	int RIGHT_CLICK = 3;
	boolean pause = false;
	boolean reset = true;
	double pauseTime = 0, dt = 0, eTime = 0, startTime = 0;
	
	// Constructor
	CountdownGui(AtomicInteger sharedNum, AtomicInteger resetNum)
	{
		this.sharedNum = sharedNum;
        this.resetNum = resetNum;
		this.addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		//System.out.print("mouse clicked: " + e.getButton() + "\n");
		int button = e.getButton();
		if(button == LEFT_CLICK)
		{
			pause = !pause;
		}
		else if(button == RIGHT_CLICK)
		{
			reset = true;
		}
	}
	public void mouseReleased(MouseEvent e)
	{
		//System.out.print("mouse released\n");
	}
	public void mouseEntered(MouseEvent e)
	{
		//System.out.print("mouse entered\n");
	}
	public void mousePressed(MouseEvent e)
	{
		//System.out.print("mouse pressed\n");
	}
	public void mouseExited(MouseEvent e)
	{
		//System.out.print("mouse exited\n");
	}
	public void run()
	{
		this.setBackground(Color.BLACK);
		// Create empty error string objects on canvas
		error1 = new GLabel("", 55, 150);
		error1.setFont(ERROR_FONT);
		error1.setColor(Color.WHITE);
		this.add(error1);
		error2 = new GLabel("", 55, 150);
		error2.setFont(ERROR_FONT);
		error2.setColor(Color.WHITE);
		this.add(error2);
		error3 = new GLabel("", 55, 150);
		error3.setFont(ERROR_FONT);
		error3.setColor(Color.WHITE);
		this.add(error3);
		
		// Create countdown clock object
		clock = new GLabel("", this.getWidth()/2-CLOCK_FONT_SIZE*2, this.getHeight()/2+CLOCK_FONT_SIZE/3);
		clock.setFont(CLOCK_FONT);
		clock.setColor(Color.WHITE);;
		this.add(clock);

		checkReset();
		startTime = (double)System.currentTimeMillis()/1000;
		double tt = 0, fTime = 0;
		int num = 0;
		boolean displayErr = false;
		while(true)
		{
			startTime += pauseTime;
			pauseTime = 0;
			eTime = (double)System.currentTimeMillis()/1000;
			checkPause(eTime);
			checkReset();
			dt = eTime - startTime;
			error1.setLocation(this.getWidth()/2-error1.getWidth()/2, this.getHeight()/2-ERROR_FONT_SIZE);
			error2.setLocation(this.getWidth()/2-error2.getWidth()/2, this.getHeight()/2+ERROR_FONT_SIZE/2);
			error3.setLocation(this.getWidth()/2-error3.getWidth()/2, this.getHeight()/2+ERROR_FONT_SIZE*2);
			clock.setLocation(this.getWidth()/2-clock.getWidth()/2, this.getHeight()/2+clock.getAscent()/2);
			int sn = sharedNum.get();
			if(num != sn)
			{
				fTime = (double)System.currentTimeMillis()/1000;
			}
			tt = (double)System.currentTimeMillis()/1000 - fTime;
			if(tt < FLASH_TIME)
			{
				displayErr = true;
			}
			else
				displayErr = false;
			if(displayErr)
			{
				removeTime();
				displayError(ERROR_TEXT1, ERROR_TEXT2, ERROR_TEXT3);
			}
			else if(!displayErr)
			{
				removeError();
			}
			
			if(running && !displayErr)
			{
				displayTime(dt);
			}
			num = sn;
			
			checkPause(eTime);
			checkReset();

		}
	}
	
	public void checkPause(double eTime)
	{
		while(pause)
		{
			//System.out.print("Pausing\n");
			pauseTime = System.currentTimeMillis()/1000 - eTime;
			checkReset();
		}
	}
	
	public void checkReset()
	{
		if(reset)
		{
            System.out.println("Reset");
			dt = 0;
			eTime = 0;
			pauseTime = 0;
			startTime = System.currentTimeMillis()/1000;
			min = startMin;
			sec = startSec;
			displayTime(dt);
			reset = false;
            if(resetNum.get() >= 1000)
                resetNum.getAndSet(0);
            resetNum.getAndIncrement();
		}
	}
	
	public void displayTime(double dtt)
	{
		sec = (startSec - (int)dtt) % 60;
		ts = (10 - (int)((dtt - (int)dtt)*10)) % 10;
		if(sec < 0)
			min--;
		if(min < 0)
		{
			running = false;
			min = 0;
			sec = 0;
		}
		
		String t;
		if(min < 10 && sec < 10)
			t = "00:" + "0" + min + ":" + "0" + sec + ":0" + ts;
		else if(min < 10 && sec >= 10)
			t = "00:" + "0" + min + ":" + sec + ":0" + ts ;
		else if(min >= 10 && sec < 10)
			t = "00:" + min + ":" + "0" + sec + ":0" + ts;
		else
			t =  "00:" + min + ":" + sec + ":0" + ts;
		clock.setLabel(t);
	}
	
	public void removeTime()
	{
		clock.setLabel("");
	}
	
/** Resets the display so that only the scaffold appears */
	public void removeError()
	{
		error1.setLabel("");
		error2.setLabel("");
		error3.setLabel("");
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game. The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayError(String word1, String word2, String word3)
	{
		error1.setLabel(word1);
		error2.setLabel(word2);
		error3.setLabel(word3);
	}
}
