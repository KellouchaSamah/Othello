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

	// La HashMap contenant les noeuds déjà visités.
	private HashMap<Board, Node> nodes;
	
	// La liste contenant le chemin actuel.
	private ArrayList<Board> currentPath;
	
	// Le noeud actuel.
	private Node current;
	
	// La profondeur de recherche maximale.
	private int threshold;

	// Constructeur de la classe SSSStrategy.
	public SSSStrategy(Board boardC, int difficulte, Player player, Player playerADV) {
	    super(boardC, difficulte, player, playerADV);
	    
	    // Initialisation de la HashMap et de la liste.
	    nodes = new HashMap<>();
	    currentPath = new ArrayList<>();
	}

	@Override
	public int executeAlgo() {
	    // Création du premier noeud.
	    current = new Node(null, board, 0, 0, Integer.MAX_VALUE);
	    
	    // Ajout du premier noeud dans la HashMap.
	    nodes.put(board, current);
	    
	    while (true) {
	        // Recherche du meilleur résultat possible pour le joueur.
	        int result = search(current, 0);
	        
	        if (result >= current.threshold) {
	            // La recherche a été coupée.
	            current.threshold = result;
	        } else if (result == Integer.MIN_VALUE) {
	            // La recherche a échoué.
	            return current.bestValue;
	        } else {
	            // La recherche a réussi.
	            current.bestValue = result;
	            current.threshold = result - 1;
	        }
	    }
	}

	// Fonction récursive de recherche.
	private int search(Node node, int depth) {
	    // Ajout du noeud courant dans la liste.
	    currentPath.set(depth, node.board);
	    
	    // Si le jeu est terminé ou si la profondeur maximale est atteinte, on retourne le résultat.
	    if (node.board.endGame() || depth == difficulte) {
	        return eval(node);
	    }
	    
	    // Si le noeud n'a pas d'enfants, on les crée.
	    if (node.children == null) {
	        expand(node);
	    }
	    
	    // Si le noeud n'a pas d'enfants, c'est une feuille.
	    if (node.children.isEmpty()) {
	        return Integer.MAX_VALUE;
	    }
	    
	    // Recherche récursive des enfants du noeud courant.
	    int value = Integer.MIN_VALUE;
	    for (Node child : node.children) {
	        int result = search(child, depth + 1);
	        
	        // Si le résultat est meilleur que le précédent, on le remplace.
	        if (result > value) {
	            value = result;
	        }
	        
	        // Si la valeur est supérieure ou égale à la threshold, on retourne la valeur courante.
	        if (value >= node.threshold) {
	            return value;
	        }
	    }
	    
	    // On retourne la meilleure valeur trouvée.
	    return value;
	}

	// Fonction permettant de créer les enfants du noeud courant.
	private void expand(Node node) {
	    // Obtention des coups possibles.
	    ArrayList<Board> boards = node.board.getSuccessors(player);
	    
	 // Si la liste de plateaux de jeu successifs est vide, alors le noeud n'a pas d'enfants, donc c'est une feuille.
	    if (boards.isEmpty()) {
	    return; // on retourne immédiatement pour sortir de la fonction.
	    }
	    // Création d'une nouvelle liste d'enfants de la taille de la liste de plateaux successifs.
	    node.children = new ArrayList<>(boards.size());
	    // Pour chaque plateau de jeu successif, on crée un nouveau noeud enfant.
	    for (Board board : boards) {
	    Node child = nodes.get(board);
	    // Si le plateau n'a pas encore été exploré, on crée un nouveau noeud enfant avec des valeurs initiales.
	    if (child == null) {
	    child = new Node(node, board, node.depth + 1, Integer.MAX_VALUE, node.threshold);
	    nodes.put(board, child); // on ajoute le nouveau noeud à la HashMap des noeuds explorés.
	    } else {
	    // Si le plateau a déjà été exploré, on réutilise le noeud enfant existant en mettant à jour ses valeurs.
	    child.parent = node;
	    child.depth = node.depth + 1;
	    }
	    node.children.add(child); // on ajoute le nouveau noeud enfant à la liste d'enfants du noeud parent actuel.
	    }
	}
	    // Fonction d'évaluation d'un noeud.
	    private int eval(Node node) {
	    return threshold; // retourne la valeur de la variable threshold définie dans la classe.
	    }
	
	    // Classe interne représentant un noeud dans l'algorithme SSS*.
	    private static class Node {
	    Node parent; // le noeud parent
	    ArrayList<Node> children; // la liste des noeuds enfants
	    Board board; // le plateau de jeu associé au noeud
	    int depth; // la profondeur du noeud dans l'arbre de recherche
	    int bestValue; // la meilleure valeur de tous les descendants du noeud
	    int threshold; // la valeur seuil pour la recherche en cours

	    Node(Node parent, Board board, int depth, int bestValue, int threshold) {
	        this.parent = parent;
	        this.board = board;
	        this.depth = depth;
	        this.bestValue = bestValue;
	        this.threshold = threshold;
	        this.children = null; // la liste des enfants est initialisée à null au départ.
	    }
		}

	}    

