package Strategy;

import java.util.ArrayList;
import java.util.HashMap;
import Model.Board;
import Model.Player;

/**
 * Classe modélisant le joueur de l'IA en basant sur l'algorithme SSS*
 *
 */
public class SSSStrategy extends Strategy {

	private HashMap<Board, Node> nodes;
	
	private ArrayList<Board> currentPath;
	
	private Node current;
	
	private int threshold;

	public SSSStrategy(Board boardC, int difficulte, Player player, Player playerADV) {
	    super(boardC, difficulte, player, playerADV);
	    
	    nodes = new HashMap<>();
	    currentPath = new ArrayList<>();
	}

	@Override
	public int executeAlgo() {
	    current = new Node(null, board, 0, 0, Integer.MAX_VALUE);
	    
	    nodes.put(board, current);
	    
	    while (true) {
	        int result = search(current, 0);
	        
	        if (result >= current.threshold) {
	            current.threshold = result;
	        } else if (result == Integer.MIN_VALUE) {
	            return current.bestValue;
	        } else {
	            current.bestValue = result;
	            current.threshold = result - 1;
	        }
	    }
	}

	private int search(Node node, int depth) {
	    currentPath.set(depth, node.board);
	    
	    if (node.board.endGame() || depth == difficulte) {
	        return eval(node);
	    }
	    
	    if (node.children == null) {
	        expand(node);
	    }
	    
	    if (node.children.isEmpty()) {
	        return Integer.MAX_VALUE;
	    }
	    
	    int value = Integer.MIN_VALUE;
	    for (Node child : node.children) {
	        int result = search(child, depth + 1);
	        
	        if (result > value) {
	            value = result;
	        }
	        
	        if (value >= node.threshold) {
	            return value;
	        }
	    }
	    
	    return value;
	}

	private void expand(Node node) {
	    ArrayList<Board> boards = node.board.getSuccessors(player);
	    
	    if (boards.isEmpty()) {
	    return;
	    }
	    node.children = new ArrayList<>(boards.size());
	    for (Board board : boards) {
	    Node child = nodes.get(board);
	    if (child == null) {
	    child = new Node(node, board, node.depth + 1, Integer.MAX_VALUE, node.threshold);
	    nodes.put(board, child);
	    } else {
	    child.parent = node;
	    child.depth = node.depth + 1;
	    }
	    node.children.add(child);
	    }
	}
	    private int eval(Node node) {
	    return threshold;
	    }
	
	    private static class Node {
	    Node parent;
	    ArrayList<Node> children;
	    Board board;
	    int depth;
	    int bestValue;
	    int threshold;
	    Node(Node parent, Board board, int depth, int bestValue, int threshold) {
	        this.parent = parent;
	        this.board = board;
	        this.depth = depth;
	        this.bestValue = bestValue;
	        this.threshold = threshold;
	        this.children = null;
	    }
		}

	}    

