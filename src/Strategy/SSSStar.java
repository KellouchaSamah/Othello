package Strategy;

import java.util.*;

public class SSSStar {
    // Définition d'une classe pour représenter les états du problème
    private static class State {
        int[] items;  // Les éléments dans le sous-ensemble
        int sum;  // La somme des éléments dans le sous-ensemble

        public State(int[] items) {
            this.items = items;
            this.sum = Arrays.stream(items).sum();
        }
    }

    // Définition d'une fonction pour trier les états selon leur borne inférieure
    private static class StateComparator implements Comparator<State> {
        @Override
        public int compare(State s1, State s2) {
            return s1.sum - s2.sum;
        }
    }

    // Implémentation de l'algorithme SSS*
    public static int sssstar(int[] items, int targetSum) {
        PriorityQueue<State> queue = new PriorityQueue<>(new StateComparator());
        queue.offer(new State(new int[]{}));
        int bestSum = 0;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.sum > targetSum) {
                continue;
            }

            if (state.sum > bestSum) {
                bestSum = state.sum;
            }

            for (int i = state.items.length; i < items.length; i++) {
                int[] newItems = Arrays.copyOf(state.items, state.items.length + 1);
                newItems[state.items.length] = items[i];
                State newState = new State(newItems);
                queue.offer(newState);
            }
        }

        return bestSum;
    }

    // Exemple d'utilisation de l'algorithme SSS*
    public static void main(String[] args) {
        int[] items = {1, 3, 5, 7, 9};
        int targetSum = 12;
        int bestSum = sssstar(items, targetSum);
        System.out.println("La somme maximale des éléments dans un sous-ensemble est : " + bestSum);
    }
}
