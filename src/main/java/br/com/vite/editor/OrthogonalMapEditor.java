package br.com.vite.editor;

import br.com.vite.map.OrthogonalMap;

public class OrthogonalMapEditor extends MapEditor {
	
	public OrthogonalMapEditor(int columns, int lines, int tileWidth, int tileHeight) {
		super(tileWidth, tileHeight);
				
		map = new OrthogonalMap(columns, lines, tileWidth, tileHeight);

		map.createTiles();
	}
	
}
