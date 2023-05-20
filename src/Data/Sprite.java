package Data;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import gameloop.Renderer;

/* This will work in tandem with the Sprites.java file in the graphics package. This is a single object of
 * type Sprite. That class will handle the grouping of the type for purposes of game development. */

public class Sprite implements Runnable{
	private BufferedImage _image;
	private int _x, _y;
	private String _file;
	private String _tag;			// Used to find the sprite
	public Sprite(int x, int y, String spriteFileName, String tag){
		try {
			_image = ImageIO.read(new File(spriteFileName));
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		_x = x;
		_y = y;
		_file = spriteFileName;
		_tag = tag;
	}
	
	/* Try a threaded version? (Added 10/13/2020) */
	/*public Sprite(int x, int y, String spriteFileName, String tag){
		_x = x;
		_y = y;
		_file = spriteFileName;
		_tag = tag;
		Thread t = new Thread(this);
		t.start();
	}*/
	
	public Sprite(int x, int y, BufferedImage image, String tag){
		_image = image;
		_x = x;
		_y = y;
		_tag = tag;
	}
	
	public Sprite(Sprite newSprite){
		_x = newSprite.getX();
		_y = newSprite.getY();
		_image = deepCopy(newSprite.getSprite());
		_tag = newSprite.getTag();
	}
	
	/* Used to copy buffered images by value */
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	public String getTag(){
		return _tag;
	}
	
	public void setTag(String newTag){
		_tag = newTag;
	}
	
	public BufferedImage getSprite(){
		return _image;
	}
	
	public int getX(){
		return _x;
	}
	
	public int getY(){
		return _y;
	}
	
	public void moveXAbsolute(int newX){
		_x = newX;
	}
	
	public void moveYAbsolute(int newY){
		_y = newY;
	}
	
	public void moveXRelative(int offsetX){
		_x += offsetX;
	}
	
	public void moveYRelative(int offsetY){
		_y += offsetY;
	}
	
	public void changeImage(BufferedImage newImage){
		_image = newImage;
	}
	
	public String toString(){
		return _file;
	}

	@Override
	public void run() {
		try {
			_image = ImageIO.read(new File(_file));
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		_image = Renderer.toCompatibleImage(_image);
	}
	
	
	//Additional methods for collisions system
	public Collisions dimensions() {		//creates bounding box using width and height of image
		int width = _image.getWidth();
		int height = _image.getHeight();
		return new Collisions(_x, _x + width, _y, _y + height);
	}
	
	public Collisions rangeSign() {
		int width = _image.getWidth();
		int height = _image.getHeight();
		return new Collisions(_x, _x + width, _y, _y + height + 16);
	}
	
	public Collisions rangeKnife() {
		int width = _image.getWidth();
		int height = _image.getHeight();
		return new Collisions(_x - 2, _x + width + 4, _y - 2, _y + height + 4);
	}
	
	public boolean isColliding(Sprite obj) {		//compare dimensions of character to an object
		return dimensions().isColliding(obj.dimensions());
	}
	
	public boolean inSign(Sprite sign) {	//up direction only
		return rangeSign().isColliding(sign.rangeSign());
	}
	
	public boolean inKnife(Sprite knife) {	//horizontal
		return rangeKnife().isColliding(knife.rangeKnife());
	}

}