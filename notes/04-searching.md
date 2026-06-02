# Searching Algorithms

## Linear Search

Linear search is a process of finding a given value pointing in a list by sequential approach — it is basic and sequential search algorithm that compares the target value with all the other elements in the list.

### Algorithm
1. Start from index 0
2. Compare each element with target
3. If element == target → return index
4. If not found after all elements → return -1

### Complexity
- **Best Case**: O(1) — element found at first index
- **Worst Case**: O(n) — element found at last index or not found at all. How many comparisons will be made for best case: only 1 comparison. Worst case: the element will be found at n-th index, so only 1 comparison is needed.
- **Time Complexity**: O(n) — linear. As array size grows, time grows linearly.

### Linear vs Constant Time (graph)
- Linear complexity — as n increases, time increases proportionally
- Constant complexity — time stays the same regardless of n

```java
static int linearSearch(int[] arr, int target) {
    if (arr.length == 0) return -1;

    for (int index = 0; index < arr.length; index++) {
        if (arr[index] == target) {
            return index;
        }
    }
    return -1;
}
```

### Variants
- Return index → use regular for loop
- Return element → can use enhanced for loop
- Return true/false → can use enhanced for loop
- Search in range → pass start and end parameters
- Search in 2D array → nested loops, return `{row, col}`
- Search in String → use `charAt(i)` or convert to `toCharArray()`

### Key Observations
- Always handle empty array edge case first
- Return `Integer.MAX_VALUE` instead of `-1` when `-1` could be a valid element
- `str.length()` has parentheses — it is a method, not a property like `arr.length`
- `str.toCharArray()` converts String to `char[]` — needed for enhanced for loop since String doesn't implement Iterable
- `str.charAt(i)` returns char at index i

---

## Binary Search

Binary search works only on a **sorted** array. It repeatedly divides the search space in half.

### Prerequisite
Array must be sorted (ascending or descending).

### Algorithm
1. Find the middle element
2. If target == middle → found, return index
3. If target < middle → search in left half (end = mid - 1)
4. If target > middle → search in right half (start = mid + 1)
5. Repeat until start > end
6. If not found → return -1

### Example
```
arr = [2, 4, 6, 9, 11, 12, 14, 20, 35, 43]
target = 35

1st → mid = (0+9)/2 = 4 → arr[4] = 11 → target > middle → search in right
2nd → arr = [12, 14, 20, 35, 43] → mid = start + (end-start)/2 = 7 → arr[7] = 20 → target > middle → search in right
3rd → arr = [35, 43] → mid = 8 → arr[8] = 35 → found at index 8
```

### Finding Mid — Important
```java
// Wrong — (start + end) might exceed int range
int mid = (start + end) / 2;

// Correct
int mid = start + (end - start) / 2;
```

### Complexity
- **Best Case**: O(1) — target is the middle element
- **Worst Case**: O(log n)

### Why O(log n)?
```
N = 1 → at the end only one element will be left → find value of k
N/2^k = 1
N = 2^k
log N = log(2^k)
log N = k log 2
k = log N / log 2
k = log₂(N)
```
Total comparisons in the worst case → O(log N)

For binary search on 1,000,000 elements:
log₂(1,000,000) = ~20 comparisons only.

### Space Complexity
- O(1) — find the space in which it is used, considering the maximum number of iterations
- Explanation: find the maximum case. N = 2^0 = 1, N = 2^1 = 2 ... at each level the array is divided into half

```java
static int binarySearch(int[] arr, int target) {
    int start = 0;
    int end = arr.length - 1;

    while (start <= end) {
        int mid = start + (end - start) / 2;

        if (target < arr[mid]) {
            end = mid - 1;
        } else if (target > arr[mid]) {
            start = mid + 1;
        } else {
            return mid; // found
        }
    }
    return -1;
}
```

---

## Order Agnostic Binary Search

What if you don't know whether the array is sorted in ascending or descending order? That's where Order Agnostic Binary Search comes into action.

```java
static int orderAgnosticBS(int[] arr, int target) {
    int start = 0;
    int end = arr.length - 1;

    boolean isAsc = arr[start] < arr[end]; // detect order

    while (start <= end) {
        int mid = start + (end - start) / 2;

        if (arr[mid] == target) return mid;

        if (isAsc) {
            if (target < arr[mid]) end = mid - 1;
            else start = mid + 1;
        } else {
            if (target > arr[mid]) end = mid - 1;
            else start = mid + 1;
        }
    }
    return -1;
}
```

- Detect ascending or descending by comparing `arr[start]` and `arr[end]`
- If ascending → same logic as normal binary search
- If descending → flip the condition

---

## Common Mistakes

- Using `(start + end) / 2` for mid — can cause integer overflow for large arrays
- Applying binary search on unsorted array — gives wrong results
- Using `while (start < end)` instead of `while (start <= end)` — misses single element arrays
- Forgetting `str.length()` uses parentheses unlike `arr.length`
- Not handling empty array before starting the search loop