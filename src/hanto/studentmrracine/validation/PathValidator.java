/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentmrracine.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import hanto.common.HantoCoordinate;
import hanto.studentmrracine.common.HantoUtil;

/**
 * Validates the path from one hex to another is within a distance limit
 * 
 * @author mrracine
 */
public class PathValidator {

	private static final Map<HantoCoordinate, HantoCoordinate> PATH = 
			new HashMap<HantoCoordinate, HantoCoordinate>();
	private static final List<HantoCoordinate> TOVISIT = new ArrayList<HantoCoordinate>();
	private static final List<HantoCoordinate> VISITED = new ArrayList<HantoCoordinate>();

	private static final Map<HantoCoordinate, Double> GSCORE = 
			new HashMap<HantoCoordinate, Double>();
	private static final Map<HantoCoordinate, Double> FSCORE = 
			new HashMap<HantoCoordinate, Double>();

	/**
	 * Private constructor for singleton
	 */
	private PathValidator(){}

	/**
	 * Checks whether the two coordinates are within the given distance
	 * of each other
	 * 
	 * @param dist the maximum distance the two coordinates can be separated by
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @return if the path is within the distance bound
	 */
	public static boolean isWithinDistance(int dist, HantoCoordinate from, HantoCoordinate to){

		int pathLength = getShortestPath(from, to).size() - 1;
		return pathLength <= dist;		
	}

	/**
	 * Returns a linked list containing the shortest path between two nodes
	 * 
	 * @param from the starting coordinate
	 * @param to the destination coordinate
	 * @return the shortest path between the two coordinates
	 */
	public static Queue<HantoCoordinate> getShortestPath(HantoCoordinate from, 
			HantoCoordinate to){

		clearData();

		TOVISIT.add(from);

		GSCORE.put(from, 0.0);
		FSCORE.put(from, GSCORE.get(from) + heuristicEstimate(from, to));

		while(!TOVISIT.isEmpty()){

			HantoCoordinate current = lowestFScore(TOVISIT, FSCORE);

			if (current.equals(to)){
				break;
			}

			TOVISIT.remove(current);
			VISITED.add(current);

			for (HantoCoordinate neighbor : HantoUtil.getAllNeighbors(current)){

				if(VISITED.contains(neighbor)){
					continue;
				}

				double tempGScore = GSCORE.get(current) + heuristicEstimate(current, neighbor);

				if(!TOVISIT.contains(neighbor) || tempGScore < GSCORE.get(neighbor)){

					PATH.put(neighbor, current);
					GSCORE.put(neighbor, tempGScore);
					FSCORE.put(neighbor, GSCORE.get(neighbor) + heuristicEstimate(neighbor, to));

					if(!TOVISIT.contains(neighbor)){
						TOVISIT.add(neighbor);
					}	
				}
			}	
		}

		return reconstructPath(PATH, to);
	}

	/**
	 * Reconstructs the shortest path between two coordinates
	 * 
	 * @param path the hashmap of paths
	 * @param current the current node
	 * @return the shortest path
	 */
	private static Queue<HantoCoordinate> reconstructPath(
			Map<HantoCoordinate, HantoCoordinate> path, HantoCoordinate current) {

		Queue<HantoCoordinate> actualPath = new LinkedList<HantoCoordinate>();

		if(path.containsKey(current)){

			actualPath = reconstructPath(path, path.get(current));
			actualPath.add(current);

		} else {

			actualPath.add(current);
		}

		return actualPath;
	}

	/**
	 * Retrieves the coordinate with the lowest fscore in the unvisited nodes
	 * 
	 * @param toVisit the list of unvisited nodes
	 * @param fScore the hashmap of fscores
	 * @return the coordinate with the lowest fscore
	 */
	private static HantoCoordinate lowestFScore(List<HantoCoordinate> toVisit, 
			Map<HantoCoordinate, Double> fScore) {

		HantoCoordinate lowestFCoord = null;
		double lowestFScore = Double.POSITIVE_INFINITY;

		for(HantoCoordinate c : toVisit){

			if(fScore.get(c) < lowestFScore){
				lowestFScore = fScore.get(c);
				lowestFCoord = c;
			}
		}

		return lowestFCoord;
	}

	/**
	 * A heuristic cost estimate function for A* search. Basically calculates distance
	 * 
	 * @param from the starting coordinate
	 * @param to the goal coordinate
	 * @return the heuristic cost (distance) between the two
	 */
	private static double heuristicEstimate(HantoCoordinate from, HantoCoordinate to) {

		int xDist = Math.abs(from.getX() - to.getX());
		int yDist = Math.abs(from.getY() - to.getY());

		return Math.sqrt((xDist * xDist) + (yDist * yDist));
	}

	private static void clearData(){
		PATH.clear();
		TOVISIT.clear();
		VISITED.clear();
		GSCORE.clear();
		FSCORE.clear();
	}
}
