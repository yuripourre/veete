package br.com.vite.collection.grassland.objects.crate;

import br.com.vite.tile.ImageTileLayer;

public class CrateOpenedNE extends ImageTileLayer {

	public CrateOpenedNE(long uniqueId) {
		super(uniqueId, "grassland/grassland.png");

		this.setLayerBounds(4*64, 8*32, 64, 64);
	}
	
}
