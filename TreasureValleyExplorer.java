import java.util.*;

/**
 * A convenient class that stores a pair of integers.
 * DO NOT MODIFY THIS CLASS.
 */
class IntPair {
    // Make the fields final to ensure they cannot be changed after initialization
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

/**
 * TreasureValleyExplorer class operates on a landscape of Numerica,
 * selectively modifying the most and least valuable valleys of a specified
 * depth.
 * 
 * DO NOT MODIFY THE SIGNATURE OF THE METHODS PROVIDED IN THIS CLASS.
 * You are encouraged to add methods and variables in the class as needed.
 *
 */
public class TreasureValleyExplorer {

    private final List<Landform> landscape;
    private final Map<Integer, TreeMap<Integer, Valley>> depthToValleys;
    private final Map<Integer, TreeMap<Integer, Valley>> depthToLeastValuableValleys;

    /**
     * Constructor to initialize the TreasureValleyExplorer with the given heights
     * and values
     * of points in Numerica.
     *
     * @param heights An array of distinct integers representing the heights of
     *                points in the landscape.
     * @param values  An array of distinct integers representing the treasure value
     *                of points in the landscape.
     */
    public TreasureValleyExplorer(int[] heights, int[] values) {
        landscape = new ArrayList<>();
        depthToValleys = new HashMap<>();
        depthToLeastValuableValleys = new HashMap<>();

        for (int i = 0; i < heights.length; i++) {
            landscape.add(new Landform(heights[i], values[i]));
        }

        computeValleys();
    }

    /**
     * Checks if the entire landscape is excavated (i.e., there are no points
     * left).
     *
     * @return true if the landscape is empty, false otherwise.
     */
    public boolean isEmpty() {
        return landscape.isEmpty();
    }

    /**
     * A method to insert a new landform prior to the most valuable valley of the
     * specified depth
     *
     * @param height The height of the new landform
     * @param value  The treasure value of the new landform
     * @param depth  The depth of the valley we wish to insert at
     *
     * @return true if the insertion is successful, false otherwise
     */
    public boolean insertAtMostValuableValley(int height, int value, int depth) {
        TreeMap<Integer, Valley> valleys = depthToValleys.get(depth);
        if (valleys == null || valleys.isEmpty()) {
            return false;
        }

        Valley mostValuableValley = valleys.lastEntry().getValue();
        int index = mostValuableValley.index;

        landscape.add(index, new Landform(height, value));
        computeValleys();
        return true;
    }

    /**
     * A method to insert a new landform prior to the least valuable valley of the
     * specified depth
     *
     * @param height The height of the new landform
     * @param value  The treasure value of the new landform
     * @param depth  The depth of the valley we wish to insert at
     *
     * @return true if the insertion is successful, false otherwise
     */
    public boolean insertAtLeastValuableValley(int height, int value, int depth) {
        TreeMap<Integer, Valley> valleys = depthToLeastValuableValleys.get(depth);
        if (valleys == null || valleys.isEmpty()) {
            return false;
        }

        Valley leastValuableValley = valleys.firstEntry().getValue();
        int index = leastValuableValley.index;

        landscape.add(index, new Landform(height, value));
        computeValleys();
        return true;
    }

    /**
     * A method to remove the most valuable valley of the specified depth
     *
     * @param depth The depth of the valley we wish to remove
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the removed valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair removeMostValuableValley(int depth) {
        TreeMap<Integer, Valley> valleys = depthToValleys.get(depth);
        if (valleys == null || valleys.isEmpty()) {
            return null;
        }

        Valley mostValuableValley = valleys.pollLastEntry().getValue();
        int index = mostValuableValley.index;

        Landform removedLandform = landscape.remove(index);
        computeValleys();
        return new IntPair(removedLandform.height, removedLandform.value);
    }

    /**
     * A method to remove the least valuable valley of the specified depth
     *
     * @param depth The depth of the valley we wish to remove
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the removed valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair removeLeastValuableValley(int depth) {
        TreeMap<Integer, Valley> valleys = depthToLeastValuableValleys.get(depth);
        if (valleys == null || valleys.isEmpty()) {
            return null;
        }

        Valley leastValuableValley = valleys.pollFirstEntry().getValue();
        int index = leastValuableValley.index;

        Landform removedLandform = landscape.remove(index);
        computeValleys();
        return new IntPair(removedLandform.height, removedLandform.value);
    }

    /**
     * A method to get the treasure value of the most valuable valley of the
     * specified depth
     *
     * @param depth The depth of the valley we wish to find the treasure value of
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the found valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair getMostValuableValley(int depth) {
        TreeMap<Integer, Valley> valleys = depthToValleys.get(depth);
        if (valleys == null || valleys.isEmpty()) {
            return null;
        }

        Valley mostValuableValley = valleys.lastEntry().getValue();
        return new IntPair(mostValuableValley.height, mostValuableValley.value);
    }

    /**
     * A method to get the treasure value of the least valuable valley of the
     * specified depth
     *
     * @param depth The depth of the valley we wish to find the treasure value of
     *
     * @return An IntPair where the first field is the height and the second field
     *         is the treasure value of the found valley
     * @return null if no valleys of the specified depth exist
     */
    public IntPair getLeastValuableValley(int depth) {
        TreeMap<Integer, Valley> valleys = depthToLeastValuableValleys.get(depth);
        if (valleys == null || valleys.isEmpty()) {
            return null;
        }

        Valley leastValuableValley = valleys.firstEntry().getValue();
        return new IntPair(leastValuableValley.height, leastValuableValley.value);
    }

    /**
     * A method to get the number of valleys of a given depth
     *
     * @param depth The depth that we want to count valleys for
     *
     * @return The number of valleys of the specified depth
     */
    public int getValleyCount(int depth) {
        TreeMap<Integer, Valley> valleys = depthToValleys.get(depth);
        if (valleys == null) {
            return 0;
        }
        return valleys.size();
    }

    private void computeValleys() {
        depthToValleys.clear();
        depthToLeastValuableValleys.clear();

        int depth = 0;
        for (int i = 0; i < landscape.size(); i++) {
            Landform current = landscape.get(i);

            if (i > 0 && current.height < landscape.get(i - 1).height) {
                depth++;
            } else {
                depth = 0;
            }

            if ((i == 0 || current.height < landscape.get(i - 1).height) &&
                (i == landscape.size() - 1 || current.height < landscape.get(i + 1).height)) {
                Valley valley = new Valley(current.height, current.value, depth, i);

                depthToValleys.computeIfAbsent(depth, k -> new TreeMap<>()).put(valley.value, valley);
                depthToLeastValuableValleys.computeIfAbsent(depth, k -> new TreeMap<>()).put(valley.value, valley);
            }
        }
    }

    private static class Landform {
        int height;
        int value;

        Landform(int height, int value) {
            this.height = height;
            this.value = value;
        }
    }

    private static class Valley {
        int height;
        int value;
        int depth;
        int index;

        Valley(int height, int value, int depth, int index) {
            this.height = height;
            this.value = value;
            this.depth = depth;
            this.index = index;
        }
    }
}