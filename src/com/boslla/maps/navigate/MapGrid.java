package com.boslla.maps.navigate;

import com.boslla.path.Mover;
import com.boslla.path.TileBasedMap;

public class MapGrid implements TileBasedMap {

	private int height; 
	private int width;
	private int grid[][];
	
	private final int WALL=1; //Assume that walls have the value of 1
	
	public MapGrid(int height,int width)
	{
		//Set the width and height of the grid
		this.height= height;
		this.width= width; 
		grid = new int [height][width]; //Create the grid
		placeObstacles();
	}
	private void placeObstacles() {
		// TODO Change this method to read obstacles from db instead of hardcoding
		fillArea(361,266,3,178,WALL); 
		fillArea(223,444,104,3,WALL);
		fillArea(225,266,245,3,WALL);
		
	}
	
	private void fillArea(int x, int y, int width, int height, int type) {
		for (int xp=x;xp<x+width;xp++) {
			for (int yp=y;yp<y+height;yp++) {
				grid[xp][yp] = type;
			}
		}
	}
	@Override
	public boolean blocked(Mover mover, int x, int y) {
		// Find out if there is a WALL or not in this area
		return grid[x][y]==WALL; //Returns true if this is a WALL
			
	}

	@Override
	public float getCost(Mover arg0, int arg1, int arg2, int arg3, int arg4) {
		// We assume that the cost of any move is the same
		return 1;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	
	//Returns true if object at x,y is an obstacle,false otherwise
	public Boolean isObstacle(int x,int y){
		return grid[x][y]==WALL;
	}

}
