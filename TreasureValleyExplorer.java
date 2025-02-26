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
    private int[] heights;
    private int[] values;
    private int size;

    public TreasureValleyExplorer(int[] heights, int[] values) {
        this.heights = heights.clone();
        this.values = values.clone();
        this.size = heights.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int calculateDepth(int index) {
        if (index == 0) return 0;
        int depth = 0;
        for (int i = index; i > 0; i--) {
            if (heights[i] < heights[i - 1]) {
                depth++;
            } else {
                break;
            }
        }
        return depth;
    }

    private boolean isValley(int index) {
        if (size == 1) return true;
        if (index == 0) return heights[index] < heights[index + 1];
        if (index == size - 1) return heights[index] < heights[index - 1];
        return heights[index] < heights[index - 1] && heights[index] < heights[index + 1];
    }

    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        int maxValue = Integer.MIN_VALUE;
        int insertIndex = -1;

        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i) && values[i] > maxValue) {
                maxValue = values[i];
                insertIndex = i;
            }
        }

        if (insertIndex == -1) return false;

        int[] newHeights = new int[size + 1];
        int[] newValues = new int[size + 1];

        System.arraycopy(heights, 0, newHeights, 0, insertIndex);
        System.arraycopy(values, 0, newValues, 0, insertIndex);

        newHeights[insertIndex] = height;
        newValues[insertIndex] = value;

        System.arraycopy(heights, insertIndex, newHeights, insertIndex + 1, size - insertIndex);
        System.arraycopy(values, insertIndex, newValues, insertIndex + 1, size - insertIndex);

        heights = newHeights;
        values = newValues;
        size++;
        return true;
    }

    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        int minValue = Integer.MAX_VALUE;
        int insertIndex = -1;

        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i) && values[i] < minValue) {
                minValue = values[i];
                insertIndex = i;
            }
        }

        if (insertIndex == -1) return false;

        int[] newHeights = new int[size + 1];
        int[] newValues = new int[size + 1];

        System.arraycopy(heights, 0, newHeights, 0, insertIndex);
        System.arraycopy(values, 0, newValues, 0, insertIndex);

        newHeights[insertIndex] = height;
        newValues[insertIndex] = value;

        System.arraycopy(heights, insertIndex, newHeights, insertIndex + 1, size - insertIndex);
        System.arraycopy(values, insertIndex, newValues, insertIndex + 1, size - insertIndex);

        heights = newHeights;
        values = newValues;
        size++;
        return true;
    }

    public IntPair removeMostValuableValley(int depth) {
        int maxValue = Integer.MIN_VALUE;
        int removeIndex = -1;

        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i) && values[i] > maxValue) {
                maxValue = values[i];
                removeIndex = i;
            }
        }

        if (removeIndex == -1) return null;

        IntPair removed = new IntPair(heights[removeIndex], values[removeIndex]);

        int[] newHeights = new int[size - 1];
        int[] newValues = new int[size - 1];

        System.arraycopy(heights, 0, newHeights, 0, removeIndex);
        System.arraycopy(values, 0, newValues, 0, removeIndex);

        System.arraycopy(heights, removeIndex + 1, newHeights, removeIndex, size - removeIndex - 1);
        System.arraycopy(values, removeIndex + 1, newValues, removeIndex, size - removeIndex - 1);

        heights = newHeights;
        values = newValues;
        size--;
        return removed;
    }

    public IntPair removeLeastValuableValley(int depth) {
        int minValue = Integer.MAX_VALUE;
        int removeIndex = -1;

        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i) && values[i] < minValue) {
                minValue = values[i];
                removeIndex = i;
            }
        }

        if (removeIndex == -1) return null;

        IntPair removed = new IntPair(heights[removeIndex], values[removeIndex]);

        int[] newHeights = new int[size - 1];
        int[] newValues = new int[size - 1];

        System.arraycopy(heights, 0, newHeights, 0, removeIndex);
        System.arraycopy(values, 0, newValues, 0, removeIndex);

        System.arraycopy(heights, removeIndex + 1, newHeights, removeIndex, size - removeIndex - 1);
        System.arraycopy(values, removeIndex + 1, newValues, removeIndex, size - removeIndex - 1);

        heights = newHeights;
        values = newValues;
        size--;
        return removed;
    }

    public IntPair getMostValuableValley(int depth) {
        int maxValue = Integer.MIN_VALUE;
        IntPair result = null;

        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i) && values[i] > maxValue) {
                maxValue = values[i];
                result = new IntPair(heights[i], values[i]);
            }
        }

        return result;
    }

    public IntPair getLeastValuableValley(int depth) {
        int minValue = Integer.MAX_VALUE;
        IntPair result = null;

        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i) && values[i] < minValue) {
                minValue = values[i];
                result = new IntPair(heights[i], values[i]);
            }
        }

        return result;
    }

    public int getValleyCount(int depth) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (calculateDepth(i) == depth && isValley(i)) {
                count++;
            }
        }
        return count;
    }
}