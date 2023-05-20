package Main;

import java.awt.Color;
import logic.Control;
import timer.stopWatchX;
import Data.Sprite;
import java.util.Queue;
import java.util.LinkedList;

public class Main{
	// Fields (Static) below...
	public static Color c = new Color(0, 175, 175);
	public static stopWatchX timer = new stopWatchX(10);
	public static Queue<Sprite> sprites = new LinkedList<Sprite>();		//queue here
	public static Sprite changeSprite = new Sprite(0, 0, "Art/down0.png", "");
	public static Sprite leftwall = new Sprite(0, 0, "Art/leftwall.png", "");
	public static Sprite rightwall = new Sprite(1150, 90, "Art/rightwall.png", "");
	public static Sprite topwall = new Sprite(100, 0, "Art/topwall.png", "");
	public static Sprite bottomwall = new Sprite(100, 640, "Art/bottomwall.png", "");
	public static Sprite sign = new Sprite(280, 250, "Art/sign.png", "");
	public static Sprite knives = new Sprite(800, 300, "Art/knives.png", "");
	public static String direction;
	public static int frameCount, maxFrame, newX, newY, opposite;
	public static boolean appearSignDialogue, appearKnifeDialogue, signRange, 
	knifeRange, wallLeft, wallRight, wallTop, wallBottom, theSign, theKnives;
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		frameCount = 0;
		maxFrame = 6;
		direction = "down";
		newX = 400;	//starting location
		newY = 400;
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)	
		boundingBoxes();
		
		if(frameCount >= maxFrame)
			frameCount = 0;
		if(timer.isTimeUp()) {	//timer start
			//add to sprites queue and transfer to changeSprite after removing
			if(sprites.isEmpty())
				sprites.add(new Sprite(newX, newY, "Art/"+(direction + frameCount)+".png", String.valueOf(frameCount)));
			changeSprite = sprites.remove();
			
			if(theSign == false || theKnives == false){			//collision with sign & knives
				if(direction == "left" || direction == "right"){
					newX += opposite;
				} else if(direction == "up" || direction == "down"){
					newY += opposite;
				}
			}
			timer.resetWatch();		//timer reset
		}
		
		//print image of the room level first
		ctrl.addSpriteToFrontBuffer(0, 0, "ice");
		
		//display left, right, top walls
		ctrl.addSpriteToFrontBuffer(leftwall.getX(), leftwall.getY(), "leftwall");
		ctrl.addSpriteToFrontBuffer(rightwall.getX(), rightwall.getY(), "rightwall");
		ctrl.addSpriteToFrontBuffer(topwall.getX(), topwall.getY(), "topwall");
		
		//print sign and knives for interactions
		ctrl.addSpriteToFrontBuffer(sign.getX(), sign.getY(), "sign");
		ctrl.addSpriteToFrontBuffer(knives.getX(), knives.getY(), "knives");
		
		//store tag from Queue<Sprite>
		String fullTag, tag;
		fullTag = changeSprite.toString();
		tag = (fullTag.replace("Art/", "")).replace(".png", "");
		
		//display character sprite, using x,y,tag
		ctrl.addSpriteToFrontBuffer(changeSprite.getX(), changeSprite.getY(), tag);
		
		//display bottom wall
		ctrl.addSpriteToFrontBuffer(bottomwall.getX(), bottomwall.getY(), "bottomwall");
		
		if(appearSignDialogue == true){
			ctrl.addSpriteToFrontBuffer(160, 430, "interactsign");
		}
		if(appearKnifeDialogue == true){
			ctrl.addSpriteToFrontBuffer(160, 430, "interactknife");
		}
		ctrl.drawString(120, 60, "Programmed by: Ethan N. Ha", c);
		
		/*	//testing outputs
		ctrl.drawString(20, 100, tag, c);
		ctrl.drawString(20, 225, "Change Sprite | X: "+changeSprite.getX()+" | Y: "+changeSprite.getY(), c);
		ctrl.drawString(20, 275, "Current Frame: "+frameCount, c);
		ctrl.drawString(20, 325, "Left Wall: " + wallLeft, c);
		ctrl.drawString(20, 350, "Right Wall: " + wallRight, c);
		ctrl.drawString(20, 375, "Top Wall: " + wallTop, c);
		ctrl.drawString(20, 400, "Bottom Wall: " + wallBottom, c);
		ctrl.drawString(20, 425, "Sign: " + theSign, c);
		ctrl.drawString(20, 450, "Knives: " + theKnives, c);
		ctrl.drawString(20, 475, "Sign Range: " + signRange, c);
		ctrl.drawString(20, 500, "Knives Range: " + knifeRange, c);
		*/
	}
	// Additional Static methods below...(if needed)
	public static void boundingBoxes(){
		wallLeft = changeSprite.isColliding(leftwall);
		wallRight = changeSprite.isColliding(rightwall);
		wallTop = changeSprite.isColliding(topwall);
		wallBottom = changeSprite.isColliding(bottomwall);
		theSign = changeSprite.isColliding(sign);
		theKnives = changeSprite.isColliding(knives);
		
		signRange = changeSprite.inSign(sign);
		knifeRange = changeSprite.inKnife(knives);
		}
	}
