/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(100);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		int speed = 10;			//variable edit speed of character movement
		int addFrame = 1;		//keep at value 1 so no animation frames skipped
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		
		case 'w':
			Main.direction = "up";
			Main.opposite = 10;
			if(Main.wallTop == false){		//as long as there is no collision, character can move
				speed = 0;
				addFrame = 0;
			}
			Main.frameCount += addFrame;	//frameCount++
			Main.newY += -(speed);			//movement for going up
			break;
			
		case 'a':
			Main.direction = "left";
			Main.opposite = 10;
			if(Main.wallLeft == false){
				speed = 0;
				addFrame = 0;
			}
			Main.frameCount += addFrame;
			Main.newX += -(speed);			//movement for going left
			break;
			
		case 's':
			Main.direction = "down";
			Main.opposite = -10;
			if(Main.wallBottom == false){
				speed = 0;
				addFrame = 0;
			}
			Main.frameCount += addFrame;
			Main.newY += speed;				//movement for going down
			break;
			
		case 'd':
			Main.direction = "right";
			Main.opposite = -10;
			if(Main.wallRight == false){
				speed = 0;
				addFrame = 0;
			}
			Main.frameCount += addFrame;
			Main.newX += speed;				//movement for going right
			break;
		
		case '$':
			if(Main.signRange == false && Main.direction == "up"){		//if spacebar is pressed, dialogue will appear if in range
				Main.appearSignDialogue = true;
			}
			if(Main.knifeRange == false){
				Main.appearKnifeDialogue = true;
			}
			break;
			
		case '*':
			if(Main.appearSignDialogue = true){		// backspace to close dialogue, if its open
				Main.appearSignDialogue = false;
			}
			if(Main.appearKnifeDialogue = true){
				Main.appearKnifeDialogue = false;
			}
			break;
		
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
		
	}
	
	
}