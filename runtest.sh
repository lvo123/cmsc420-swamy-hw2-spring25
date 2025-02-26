#!/bin/bash

# Start time
start_time=$(date +%s)

# Compile the Java files
javac Evaluator.java TreasureValleyExplorer.java

# Run the Evaluator with the test case
java Evaluator tests/tc_23_remove_large.txt

# End time
end_time=$(date +%s)

# Calculate the duration
duration=$((end_time - start_time))

# Convert the duration to minutes and seconds
minutes=$((duration / 60))
seconds=$((duration % 60))

# Display the duration
echo "Time taken: $minutes minutes and $seconds seconds"