package br.com.vite.editor;

import br.com.etyllica.core.Drawable;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.vite.map.Map;
import br.com.vite.map.MapType;
import br.com.vite.map.selection.SelectionMapListener;
import br.com.vite.tile.Tile;
import br.com.vite.tile.layer.ImageTileFloor;
import br.com.vite.tile.layer.ImageTileObject;

public abstract class MapEditor implements Drawable, SelectionMapListener {

	protected MapType type;
	
	protected Map map;

	protected Tile[][] tiles;
	
	//Selection
	protected ImageTileFloor selectedTile;

	protected ImageTileObject selectedObject;
	
	//Input
	protected int mx, my;	
	
	protected boolean leftPressed = false;
	protected boolean rightPressed = false;
	protected boolean middlePressed = false;
	
	public MapEditor(int columns, int lines) {
		this(columns, lines, 64, 64);		
	}
	
	public MapEditor(int columns, int lines, int tileWidth, int tileHeight) {
		super();
	}

	public void offsetMap(int offsetX, int offsetY) {
		map.setOffset(map.getOffsetX()+offsetX, map.getOffsetY()+offsetY);
	}
	
	public void translateMap(int x, int y) {
		map.setOffset(x, y);
	}
	
	public void setFloorTile(ImageTileFloor tile) {
		selectedTile = tile;
		map.getFiller().setFloorTile(tile);
	}
	
	public void setObjectTile(ImageTileObject tile) {
		selectedObject = tile;
		map.getFiller().setObjectTile(tile);
	}

	public void draw(Graphic g) {
		map.draw(g, 0, 0);				
	}
	
	public GUIEvent updateMouse(PointerEvent event) {
				
		my = event.getY();
		mx = event.getX();
		
		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_LEFT)) {
			leftPressed = true;
		}else if(event.isButtonUp(MouseButton.MOUSE_BUTTON_LEFT)) {
			leftPressed = false;
		}
		
		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_RIGHT)) {
			rightPressed = true;
		}else if(event.isButtonUp(MouseButton.MOUSE_BUTTON_RIGHT)) {
			rightPressed = false;
		}
		
		if(event.isButtonDown(MouseButton.MOUSE_BUTTON_MIDDLE)) {
			middlePressed = true;
		}else if(event.isButtonUp(MouseButton.MOUSE_BUTTON_MIDDLE)) {
			middlePressed = false;
		}
				
		return GUIEvent.NONE;
	}
	
	public void update(long now) {
				
		Tile lastSelectedTile = map.getTargetTile(mx, my);
		
		if(map.isOnMouse()) {

			if(leftPressed) {
				lastSelectedTile.setLayer(selectedTile);
			} else if(rightPressed) {
				lastSelectedTile.setObjectLayer(selectedObject);
			} else if(middlePressed) {
				lastSelectedTile.setLayer(null);
			}
		}
	}
	
	public MapType getType() {
		return type;
	}

	public void swapGridShow() {
		map.getDrawer().swapDrawGrid();
	}

	public int getTileWidth() {
		return map.getTileWidth();
	}

	public int getTileHeight() {
		return map.getTileHeight();
	}
	
	public int getColumns() {
		return map.getColumns();
	}
	
	public int getLines() {
		return map.getLines();
	}

	public Tile[][] getTiles() {
		return tiles;
	}
	
}
