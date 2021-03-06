package br.com.vite;

import br.com.etyllica.core.context.Application;
import br.com.etyllica.core.context.UpdateIntervalListener;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphics;
import br.com.vite.editor.MapEditor;
import br.com.vite.tile.layer.ImageTileObject;

public abstract class MapApplication extends Application implements UpdateIntervalListener {
		
	protected MapEditor editor;
	
	private int normalSpeed = 6;
	private int fastSpeed = 50;
	
	private int offsetSpeed = normalSpeed;
	
	//Input
	protected int mx = 0;
	protected int my = 0;
	
	protected boolean upArrowPressed = false;
	protected boolean leftArrowPressed = false;
	protected boolean downArrowPressed = false;
	protected boolean rightArrowPressed = false;
	
	//Selection
	protected ImageTileObject selectedObject;
	
	public MapApplication(int w, int h) {
		super(w, h);
	}
	
	@Override
	public void updateKeyboard(KeyEvent event) {
		
		editor.updateKeyboard(event);

		if(event.isKeyDown(KeyEvent.VK_UP_ARROW)) {
			upArrowPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_UP_ARROW)) {
			upArrowPressed = false;
		}
		
		if(event.isKeyDown(KeyEvent.VK_DOWN_ARROW)) {
			downArrowPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_DOWN_ARROW)) {
			downArrowPressed = false;
		}
		
		if(event.isKeyDown(KeyEvent.VK_LEFT_ARROW)) {
			leftArrowPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_LEFT_ARROW)) {
			leftArrowPressed = false;
		}
		
		if(event.isKeyDown(KeyEvent.VK_RIGHT_ARROW)) {
			rightArrowPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_RIGHT_ARROW)) {
			rightArrowPressed = false;
		}
		
		if(event.isKeyDown(KeyEvent.VK_G)) {
			editor.swapGridShow();
		}
		
		if(event.isKeyDown(KeyEvent.VK_C)) {
			editor.swapCollisionShow();
		}
		
		if(event.isAnyKeyDown(KeyEvent.VK_SHIFT_LEFT, KeyEvent.VK_SHIFT_RIGHT)) {
			offsetSpeed = fastSpeed;
		} else if(event.isKeyUp(KeyEvent.VK_SHIFT_LEFT)||event.isKeyUp(KeyEvent.VK_SHIFT_RIGHT)) {
			offsetSpeed = normalSpeed;
		}
	}
	
	@Override
	public void updateMouse(PointerEvent event) {
		editor.updateMouse(event);
	}
	
	@Override
	public void timeUpdate(long now) {
		editor.update(now);
		
		if(upArrowPressed) {
			offsetMap(0, offsetSpeed);
		} else if(downArrowPressed) {
			offsetMap(0, -offsetSpeed);
		}
		
		if(leftArrowPressed) {
			offsetMap(offsetSpeed, 0);			
		} else if(rightArrowPressed) {
			offsetMap(-offsetSpeed, 0);
		}
		
	}
	
	protected void offsetMap(int offsetX, int offsetY) {
		editor.offsetMap(offsetX, offsetY);
	}
	
	@Override
	public void draw(Graphics g) {
		editor.draw(g);
	}
	
}
