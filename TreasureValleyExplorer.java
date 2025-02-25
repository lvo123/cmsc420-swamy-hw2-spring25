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
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        IntPair other = (IntPair) obj;
        return first == other.first && second == other.second;
    }

    @Override
    public int hashCode() {
        return 31 * first + second;
    }
}

public class TreasureValleyExplorer {
    private List<IntPair> landscape;
    private Map<Integer, List<IntPair>> valleysByDepth;

    public TreasureValleyExplorer(int[] heights, int[] values) {
        landscape = new ArrayList<>();
        valleysByDepth = new HashMap<>();
        
        for (int i = 0; i < heights.length; i++) {
            landscape.add(new IntPair(heights[i], values[i]));
        }
        computeValleys();
    }

    private void computeValleys() {
        valleysByDepth.clear();
        int depth = 0;
        
        for (int i = 0; i < landscape.size(); i++) {
            int height = landscape.get(i).first;
            if (i > 0 && height < landscape.get(i - 1).first) {
                depth++;
            } else {
                depth = 0;
            }
            valleysByDepth.putIfAbsent(depth, new ArrayList<>());
            if (isValley(i)) {
                valleysByDepth.get(depth).add(landscape.get(i));
            }
        }
    }

    private boolean isValley(int index) {
        int height = landscape.get(index).first;
        if ((index == 0 || height < landscape.get(index - 1).first) &&
            (index == landscape.size() - 1 || height < landscape.get(index + 1).first)) {
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return landscape.isEmpty();
    }

    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        if (!valleysByDepth.containsKey(depth) || valleysByDepth.get(depth).isEmpty()) return false;
        
        IntPair mostValuable = Collections.max(valleysByDepth.get(depth), Comparator.comparingInt(p -> p.second));
        int index = landscape.indexOf(mostValuable);
        landscape.add(index, new IntPair(height, value));
        computeValleys();
        return true;
    }

    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        if (!valleysByDepth.containsKey(depth) || valleysByDepth.get(depth).isEmpty()) return false;
        
        IntPair leastValuable = Collections.min(valleysByDepth.get(depth), Comparator.comparingInt(p -> p.second));
        int index = landscape.indexOf(leastValuable);
        landscape.add(index, new IntPair(height, value));
        computeValleys();
        return true;
    }

    public IntPair removeMostValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth) || valleysByDepth.get(depth).isEmpty()) return null;
        
        IntPair mostValuable = Collections.max(valleysByDepth.get(depth), Comparator.comparingInt(p -> p.second));
        landscape.remove(mostValuable);
        computeValleys();
        return mostValuable;
    }

    public IntPair removeLeastValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth) || valleysByDepth.get(depth).isEmpty()) return null;
        
        IntPair leastValuable = Collections.min(valleysByDepth.get(depth), Comparator.comparingInt(p -> p.second));
        landscape.remove(leastValuable);
        computeValleys();
        return leastValuable;
    }

    public IntPair getMostValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth) || valleysByDepth.get(depth).isEmpty()) return null;
        return Collections.max(valleysByDepth.get(depth), Comparator.comparingInt(p -> p.second));
    }

    public IntPair getLeastValuableValley(int depth) {
        if (!valleysByDepth.containsKey(depth) || valleysByDepth.get(depth).isEmpty()) return null;
        return Collections.min(valleysByDepth.get(depth), Comparator.comparingInt(p -> p.second));
    }

    public int getValleyCount(int depth) {
        return valleysByDepth.getOrDefault(depth, new ArrayList<>()).size();
    }
}
