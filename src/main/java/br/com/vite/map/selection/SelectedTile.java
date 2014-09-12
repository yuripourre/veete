package br.com.vite.map.selection;

import br.com.vite.tile.collision.CollisionType;

public class SelectedTile {

	private String path;
	
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	private CollisionType collision = CollisionType.FREE;

	public SelectedTile(String path, int x, int y) {
		super();
		
		this.path = path;
		this.x = x;
		this.y = y;
	}
	
	public SelectedTile(String path, int x, int y, int width, int height) {
		super();
		
		this.path = path;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public SelectedTile(String path, int x, int y, int width, int height, CollisionType collision) {
		super();
		
		this.path = path;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.collision = collision;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public CollisionType getCollision() {
		return collision;
	}

	public void setCollision(CollisionType collision) {
		this.collision = collision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectedTile other = (SelectedTile) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
