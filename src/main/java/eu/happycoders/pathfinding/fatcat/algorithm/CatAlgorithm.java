package eu.happycoders.pathfinding.fatcat.algorithm;

import eu.happycoders.pathfinding.fatcat.common.GameState;

/**
 * Interface for the shortest-path algorithm in the FatCat game.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface CatAlgorithm {
  GameState moveCat(GameState gameState);
}
