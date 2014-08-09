package br.com.vite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.linear.Point2D;
import br.com.vite.collection.MapApplication;
import br.com.vite.collection.isometric.grassland.floor.Grass;
import br.com.vite.collection.isometric.grassland.floor.Marble;
import br.com.vite.collection.isometric.tree.PalmTree1;
import br.com.vite.tile.ImageTileLayer;
import br.com.vite.tile.Tile;
import br.com.vite.tile.colider.IsometricTileColider;
import br.com.vite.tile.colider.TileColider;
import br.com.vite.tile.drawer.IsometricTileDrawer;
import br.com.vite.tile.drawer.TileDrawer;
import br.com.vite.tile.generator.IsometricTileCreator;
import br.com.vite.tile.generator.TileCreator;

public class IsometricMapApplication extends MapApplication {

	private Grass grass;
	private Marble marble;
	private PalmTree1 tree;

	private Tile lastTile;

	private final int tileSize = 64;

	private BufferedImage tileFill;
	
	private Point2D target = new Point2D();

	//TileLayers
	private ImageTileLayer selectedTile;

	private ImageTileLayer selectedObject;
	
	//Isometric
	private TileCreator creator = new IsometricTileCreator(tileSize);
	
	private TileColider colider = new IsometricTileColider(tileSize, tileSize/2);
	
	private TileDrawer drawer = new IsometricTileDrawer(tileSize, tileSize/2);

	public IsometricMapApplication(int w, int h) {
		super(w, h);
	}

	final int columns = 13;
	final int lines = 2;

	@Override
	public void load() {
		
		//create buffer
		int x = 0;
		int y = 0;

		int w = tileSize;
		int h = tileSize/2;

		createIsometricTileBorder(x,y,w,h);

		generateMap(lines, columns, creator);

		createImageTiles();

		loading = 10;
		
		loading = 20;
		
		lastTile = getTargetTile();
		
		loading = 30;

		drawer.setOffsetY(120);
		colider.setOffsetY(120);
		
		updateAtFixedRate(80);

		loading = 100;
	}
		
	private void createIsometricTileBorder(int x, int y, int w, int h) {

		tileFill = new BufferedImage(w, h+1, BufferedImage.TYPE_INT_ARGB);

		//Create Fill
		Graphics2D g = tileFill.createGraphics();
		g.setColor(Color.GREEN);

		Polygon polygon = new Polygon();

		polygon.addPoint(x, y+h/2);
		polygon.addPoint(x+w/2, y);
		polygon.addPoint(x+w, y+h/2);
		polygon.addPoint(x+w/2, y+h);

		g.fillPolygon(polygon);
	}

	private void createImageTiles() {
		grass = new Grass(genereateUniqueId(), 0);
		marble = new Marble(genereateUniqueId(), 0);
		tree = new PalmTree1(genereateUniqueId());

		selectedTile = grass;

		selectedObject = tree;		
	}

	@Override
	public void timeUpdate(long now) {

		getClicked(mx, my);

		lastTile = getTargetTile();

		if(leftPressed) {
			lastTile.setLayer(selectedTile);
		}else if(rightPressed) {
			lastTile.setObjectLayer(selectedObject);
		}else if(middlePressed) {			
			lastTile.setLayer(null);
		}

	}
	
	private Tile getTargetTile() {
		return tiles[(int)target.getY()][(int)target.getX()];
	}

	private Point2D getClicked(int mouseX, int mouseY) {

		int column = (int)(mouseX)/tileSize;

		int line = (int)(mouseY)/(tileSize/4);

		if(line<=0) {
			line = 1;
		}else if (line>=lines) {
			line = lines-1;
		}

		if(column<=0) {
			column = 1;
		}else if (column>=columns) {
			column = columns-1;
		}

		for(int j=line-1;j<line+1;j++) {

			for(int i=column-1;i<column+1;i++) {

				if(colider.colideTile(tiles[j][i],mouseX, mouseY)) {

					target.setLocation(i, j);
				}

			}
		}
		
		return target;

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.TSK_1)) {
			selectedTile = grass;
		}

		if(event.isKeyDown(KeyEvent.TSK_2)) {
			selectedTile = marble;
		}

		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {

		for(int j=0;j<lines;j++) {

			for(int i=0;i<columns;i++) {

				Tile tile = tiles[j][i];

				drawer.drawTile(tile, g);

			}
		}

		g.setAlpha(50);
		g.drawImage(tileFill, lastTile.getX(), lastTile.getY()+drawer.getOffsetY());
		g.setAlpha(100);

	}

}
