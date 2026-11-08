# Assignment 4. Patient Zero

Student ID:252223
Name:Assylzhan Abishev
Group: SE-2512


## Program output

The 9 lines your program prints on your data (`java -cp out Main`):

    Mutations:67
    Shared core:1177
    Marker hits:6
    First hit:2788
    Border length:3
    Shared words:603
    Tests needed:27
    Cheapest testing:112
    Longest chain:22

My outbreak report (a screenshot of `output/outbreak.html`):

![outbreak](output/outbreak.png)

## Questions


Mission 1. d[i][j] represents the minimum number of edits needed to convert the first i characters of string A into the first j characters of string B. It is computed using the minimum of insertion, deletion, and replacement, with zero cost when the compared characters match.

Mission 2. A subsequence keeps the order of characters but allows gaps, while a substring requires characters to be consecutive. My Shared core can be larger than the number of matching positions because LCS can match characters at different indices.

Mission 3. The prefix function gives the length of the longest proper prefix that is also a suffix for each prefix of the pattern. KMP avoids moving backward in the text by using this information to skip unnecessary comparisons. My Border length is 3, meaning the marker's first three characters are the same as its last three characters.

Mission 4. Equal hash values do not guarantee equal strings because hash collisions can occur. Therefore, the actual characters must be compared. If many windows have the same hash, the algorithm performs more comparisons and becomes slower.

Mission 5. Each person has two states: tested and not tested. If a person is not tested, all their children must be tested; otherwise, each child can be either tested or not tested, whichever costs less. A greedy strategy may fail because selecting a person with many connections does not always minimize the total testing cost.

## AI use

I used ChatGPT to understand the dynamic programming and string matching algorithms, obtain example implementations, review the Java code, and troubleshoot issues during testing. I tested the algorithms using Check.java, verified the output with the lab console, and reviewed the code to understand how it works.

## Timing (Mission 7)

Milliseconds, average of 3 runs, from `java -cp out Bench`.

| test | size | time                            |
|---|---|---------------------------------|
| naive search | 20 000 / 40 000 / 80 000 | 46,3ms / 89,5ms / 185,0ms       |
| KMP | 20 000 / 40 000 / 80 000 | 2,6ms / 1,2ms / 0,3ms           |
| edit distance | 500 / 1000 / 2000 / 4000 | 5,6ms / 7,4ms / 19,3ms / 85,2ms |


The benchmark shows that naive search takes approximately twice as long when the text length doubles (46.3 ms, 89.5 ms, 185.0 ms). KMP remains much faster, although its measured times vary because of JVM optimization and measurement overhead.

Edit Distance becomes significantly slower as the input length grows. When the length increases from 2000 to 4000, the running time increases from 19.3 ms to 85.2 ms, approximately 4.4 times, which is consistent with its O(nm) time complexity.

## Complexity

n is the length of the text (or of a strain), m is the length of the pattern, k is the length of the window, v is the number of people.

| Algorithm | Time | Space | Assumptions |
|---|---|---|---|
| Edit Distance | O(nm) | O(nm) | Full DP table, insertion, deletion and replacement |
| LCS | O(nm) | O(nm) | Full DP table, subsequence can skip characters |
| Prefix Function and KMP | O(n+m) | O(m+h) | h is the number of matches, including overlapping matches |
| Rabin-Karp (shared words) | O(n+m+Ck) expected | O(m) | C is the number of candidate comparisons; hash matches are verified |
| Tree DP | O(v) | O(v) | Connected tree rooted at person 0 |

For Rabin-Karp, rolling hashes allow each window hash to be updated in O(1). When many windows have equal hashes, additional character comparisons increase the running time.


## Verification

The project was tested using java tools/Check.java.

- tiny: all 9 lines are right
- small: all 9 lines are right
- dense: all 9 lines are right

All examples passed successfully.

## Personal Results Analysis

The edit distance between the two strains is 67.
The longest common subsequence has length 1177.

KMP found the marker 6 times, with the first occurrence at position 2788.
The infection tree requires at least 27 tested people, while the minimum total testing cost is 112.
The longest infection chain contains 22 links.

## Algorithm Implementation Notes

Edit Distance and LCS use dynamic programming tables.
KMP uses the prefix function to avoid unnecessary comparisons.
Rabin-Karp uses rolling hashes and verifies matching candidates.
Tree DP uses two states for each person and reconstructs the selected testing set.
The longest infection chain is calculated using the two deepest branches.
