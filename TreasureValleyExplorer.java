import java.util.ArrayList;
import java.util.List;

/**
 * A convenient class that stores a pair of integers.
 * DO NOT MODIFY THIS CLASS.
 */
class IntPair {
    public final int first;
    public final int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
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

/**
 * TreasureValleyExplorer class operates on a landscape of Numerica,
 * selectively modifying the most and least valuable valleys of a specified depth.
 */
public class TreasureValleyExplorer {
    private List<Integer> heights;
    private List<Integer> values;

    public TreasureValleyExplorer(int[] heights, int[] values) {
        this.heights = new ArrayList<>();
        this.values = new ArrayList<>();
        for (int i = 0; i < heights.length; i++) {
            this.heights.add(heights[i]);
            this.values.add(values[i]);
        }
    }

    public boolean isEmpty() {
        return heights.isEmpty();
    }

    private int calculateDepth(int index) {
        int depth = 0;
        while (index > 0 && heights.get(index) < heights.get(index - 1)) {
            depth++;
            index--;
        }
        return depth;
    }

    private boolean isValley(int index) {
        if (heights.size() == 1) return true;
        int h = heights.get(index);
        return (index == 0 || h < heights.get(index - 1)) &&
               (index == heights.size() - 1 || h < heights.get(index + 1));
    }

    private int findValleyIndex(int depth, boolean findMax) {
        int extremeValue = findMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int targetIndex = -1;

        for (int i = 0; i < heights.size(); i++) {
            if (calculateDepth(i) == depth && isValley(i)) {
                int currentValue = values.get(i);
                if ((findMax && currentValue > extremeValue) || (!findMax && currentValue < extremeValue)) {
                    extremeValue = currentValue;
                    targetIndex = i;
                }
            }
        }
        return targetIndex;
    }

    private boolean insertAtValley(int height, int value, int depth, boolean findMax) {
        int index = findValleyIndex(depth, findMax);
        if (index == -1) return false;

        heights.add(index, height);
        values.add(index, value);
        return true;
    }

    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        return insertAtValley(height, value, depth, true);
    }

    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        return insertAtValley(height, value, depth, false);
    }

    private IntPair removeValley(int depth, boolean findMax) {
        int index = findValleyIndex(depth, findMax);
        if (index == -1) return null;

        IntPair removed = new IntPair(heights.get(index), values.get(index));
        heights.remove(index);
        values.remove(index);
        return removed;
    }

    public IntPair removeMostValuableValley(int depth) {
        return removeValley(depth, true);
    }

    public IntPair removeLeastValuableValley(int depth) {
        return removeValley(depth, false);
    }

    private IntPair getValuableValley(int depth, boolean findMax) {
        int index = findValleyIndex(depth, findMax);
        return index == -1 ? null : new IntPair(heights.get(index), values.get(index));
    }

    public IntPair getMostValuableValley(int depth) {
        return getValuableValley(depth, true);
    }

    public IntPair getLeastValuableValley(int depth) {
        return getValuableValley(depth, false);
    }

    public int getValleyCount(int depth) {
        int count = 0;
        for (int i = 0; i < heights.size(); i++) {
            if (calculateDepth(i) == depth && isValley(i)) {
                count++;
            }
        }
        return count;
    }
}