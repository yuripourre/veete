package br.com.vite.collection.isometric.grassland.objects.crate;

import br.com.vite.tile.layer.ImageTileFloor;

public class CrateOpenedNE extends ImageTileFloor {

	public CrateOpenedNE() {
		super("grassland/grassland.png");

		this.setLayerBounds(3*64, 8*32, 64, 64);
	}
	
}