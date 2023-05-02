package Strategy;

import java.util.*;

public class SSSStar {
    private static class State {
        int[] items;
        int sum;

        public State(int[] items) {
            this.items = items;
            this.sum = Arrays.stream(items).sum();
        }
    }

    private static class StateComparator implements Comparator<State> {
        @Override
        public int compare(State s1, State s2) {
            return s1.sum - s2.sum;
        }
    }

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

    public static void main(String[] args) {
        int[] items = {1, 3, 5, 7, 9};
        int targetSum = 12;
        int bestSum = sssstar(items, targetSum);
        System.out.println("La somme maximale des éléments dans un sous-ensemble est : " + bestSum);
    }
}
