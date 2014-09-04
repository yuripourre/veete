package br.com.vite.editor;

import br.com.vite.map.HexagonalMap;
import br.com.vite.map.MapType;

public class HexagonalMapEditor extends MapEditor {
	
	public HexagonalMapEditor(int columns, int lines, int tileWidth, int tileHeight) {
		super(columns, lines, tileWidth, tileHeight);
		
		type = MapType.HEXAGONAL;
		
		map = new HexagonalMap(columns, lines, tileWidth, tileHeight);

		tiles = map.createTiles();
	}

}
