import java.util.*;

class IntPair {
    public final int first;
    public final int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IntPair other = (IntPair) obj;
        return first == other.first && second == other.second;
    }

    @Override
    public int hashCode() {
        return 31 * first + second;
    }
}

public class TreasureValleyExplorer {
    private List<Integer> heights;
    private List<Integer> values;
    private TreeMap<Integer, TreeMap<Integer, Integer>> valleysByDepth; // Depth -> (Value -> Height)
    private TreeMap<Integer, Integer> depths; // Index -> Depth

    public TreasureValleyExplorer(int[] heights, int[] values) {
        this.heights = new ArrayList<>();
        this.values = new ArrayList<>();
        this.valleysByDepth = new TreeMap<>();
        this.depths = new TreeMap<>();
        
        for (int i = 0; i < heights.length; i++) {
            this.heights.add(heights[i]);
            this.values.add(values[i]);
        }
        computeDepths();
    }

    private void computeDepths() {
        depths.clear();
        valleysByDepth.clear();
        if (heights.isEmpty()) return;
        
        int n = heights.size();
        int depth = 0;
        
        for (int i = 0; i < n; i++) {
            if (i == 0 || heights.get(i) > heights.get(i - 1)) {
                depth = 0;
            } else {
                depth++;
            }
            depths.put(i, depth);
            if (isValley(i)) {
                valleysByDepth.putIfAbsent(depth, new TreeMap<>());
                valleysByDepth.get(depth).put(values.get(i), heights.get(i));
            }
        }
    }
    
    private boolean isValley(int i) {
        int n = heights.size();
        if (n == 1) return true;
        if (i == 0) return heights.get(i) < heights.get(i + 1);
        if (i == n - 1) return heights.get(i) < heights.get(i - 1);
        return heights.get(i) < heights.get(i - 1) && heights.get(i) < heights.get(i + 1);
    }
    
    public boolean isEmpty() {
        return heights.isEmpty();
    }

    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        if (!valleysByDepth.containsKey(depth)) return false;
        int maxValue = valleysByDepth.get(depth).lastKey();
        int idx = values.indexOf(maxValue);
        heights.add(idx, height);
        values.add(idx, value);
        computeDepths();
        return true;
    }

    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        if (!valleysByDepth.containsKey(depth)) return false;
        int minValue = valleysByDepth.get(depth).firstKey();
        int idx = values.indexOf(minValue);
        heights.add(idx, height);
        values.add(idx, value);
        computeDepths();
        return true;
    }

    public IntPair removeMostValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth)) return null;
        int maxValue = valleysByDepth.get(depth).lastKey();
        int idx = values.indexOf(maxValue);
        IntPair removed = new IntPair(heights.get(idx), values.get(idx));
        heights.remove(idx);
        values.remove(idx);
        computeDepths();
        return removed;
    }

    public IntPair removeLeastValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth)) return null;
        int minValue = valleysByDepth.get(depth).firstKey();
        int idx = values.indexOf(minValue);
        IntPair removed = new IntPair(heights.get(idx), values.get(idx));
        heights.remove(idx);
        values.remove(idx);
        computeDepths();
        return removed;
    }

    public IntPair getMostValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth)) return null;
        int maxValue = valleysByDepth.get(depth).lastKey();
        int height = valleysByDepth.get(depth).get(maxValue);
        return new IntPair(height, maxValue);
    }

    public IntPair getLeastValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth)) return null;
        int minValue = valleysByDepth.get(depth).firstKey();
        int height = valleysByDepth.get(depth).get(minValue);
        return new IntPair(height, minValue);
    }

    public int getValleyCount(int depth) {
        return valleysByDepth.getOrDefault(depth, new TreeMap<>()).size();
    }
}