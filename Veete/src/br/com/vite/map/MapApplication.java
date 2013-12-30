package br.com.vite.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.event.PointerState;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.util.SVGColor;
import br.com.vite.grassland.Grass1;
import br.com.vite.grassland.Marble1;
import br.com.vite.tile.ImageTileLayer;
import br.com.vite.tile.IsometricTile;

public class MapApplication extends Application {

	int uniqueId = 0;

	//TileLayers
	
	private ImageTileLayer selectedTile;
	
	private Grass1 grass;
	private Marble1 marble;

	private IsometricTile[][] tiles;

	private final int tileSize = 64;

	private IsometricTile lastTile;

	private BufferedImage tileBorder;
	private BufferedImage tileFill;

	public MapApplication(float w, float h) {
		super(w, h);
		// TODO Auto-generated constructor stub
	}

	final int columns = 13;
	final int lines = 32;

	int offsetY = 50;
	int offsetX = 0;

	@Override
	public void load() {

		tiles = new IsometricTile[lines][columns];

		int oddOffsetX = 0;

		for(int j=0;j<lines;j++){

			oddOffsetX = (tileSize/2)*(j%2);

			for(int i=0;i<columns;i++){

				tiles[j][i] = new IsometricTile(oddOffsetX+i*tileSize, offsetY+(tileSize/4)*j, tileSize);	
			}
		}

		lastTile = tiles[0][0];

		//create buffer
		int x = 0;
		int y = 0;

		int w = tileSize;
		int h = tileSize/2;

		createTileBorder(x,y,w,h);

		createImageTiles();

		loading = 100;
	}

	private void createTileBorder(int x, int y, int w, int h){

		tileBorder = new BufferedImage(w, h+1, BufferedImage.TYPE_INT_ARGB);
		tileFill = new BufferedImage(w, h+1, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = tileBorder.createGraphics();
		g.setColor(Color.BLACK);

		Polygon polygon = new Polygon();

		polygon.addPoint(x, y+h/2);
		polygon.addPoint(x+w/2, y);
		polygon.addPoint(x+w, y+h/2);
		polygon.addPoint(x+w/2, y+h);

		g.drawPolygon(polygon);

		//Create Fill
		g = tileFill.createGraphics();
		g.setColor(Color.GREEN);
		g.fillPolygon(polygon);
	}

	private void createImageTiles(){
		grass = new Grass1(genereateUniqueId());
		marble = new Marble1(genereateUniqueId());
		
		selectedTile = marble;
		
	}

	private int genereateUniqueId(){
		return uniqueId++;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		IsometricTile tile = getClicked(event.getX(), event.getY());

		if(lastTile!=tile){

			tile.setColor(SVGColor.GREEN);
			lastTile.setColor(Color.BLACK);
			lastTile = tile;

		}
		
		if(event.isKey(MouseButton.MOUSE_BUTTON_LEFT)){
			
			tile.setLayer(selectedTile);
			
		}
		

		// TODO Auto-generated method stub
		return null;
	}

	private IsometricTile getClicked(float mouseX, float mouseY){

		int column = (int)(mouseX-offsetX)/tileSize;

		int line = (int)(mouseY-offsetY)/(tileSize/4);

		if(line<=0){
			line = 1;
		}else if (line>=lines){
			line = lines-1;
		}

		if(column<=0){
			column = 1;
		}else if (column>=columns){
			column = columns-1;
		}

		for(int j=line-1;j<line+1;j++){

			for(int i=column-1;i<column+1;i++){

				if(tiles[j][i].colideIsometric(mouseX, mouseY)){

					return tiles[j][i];
				}

			}
		}

		return lastTile;

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_1)){
			selectedTile = grass;
		}
		
		if(event.isKeyDown(KeyEvent.TSK_2)){
			selectedTile = marble;
		}
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphic g) {

		for(int j=0;j<lines;j++){

			for(int i=0;i<columns;i++){

				IsometricTile tile = tiles[j][i];

				tile.draw(g);
				
				//Draw Grid
				g.drawImage(tileBorder, tile.getX(),tile.getY());
				
			}
		}

		g.setAlpha(50);
		g.drawImage(tileFill, lastTile.getX(), lastTile.getY());
		g.setAlpha(100);
		
	}

}
