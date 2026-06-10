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

## Modified Binary Search Patterns

### Floor and Ceiling — Key Insight
When the while loop breaks, `start = end + 1` (start crossed end). This is the reason:
- **Floor** — return `end` because end is pointing to the greatest element ≤ target
- **Ceiling** — return `start` because start is pointing to the smallest element ≥ target
- When no answer found, `start` will be > target — it is the next big number

```
arr = [2, 3, 5, 9, 14, 16, 18], target = 15
ceiling(arr, 14) = 14
ceiling(arr, 15) = 16
ceiling(arr, 4)  = 5
ceiling(arr, 9)  = 9
```

When condition is violated: `s = e + 1`, so `s > e`. The answer `s` when condition violated will be the smallest element greater than target.

---

### First and Last Position in Sorted Array
Run binary search twice — first time to find start position, second time for last position. Complexity is same — O(log n) both times.

- Find first occurrence → when target found, save index as potential answer then move `end = mid - 1` to keep searching left
- Find last occurrence → when target found, save index as potential answer then move `start = mid + 1` to keep searching right

```
arr = [5, 7, 7, 7, 8, 8, 10], target = 7
first occurrence → end = mid - 1 when found → ans = index 1
last occurrence  → start = mid + 1 when found → ans = index 3
```

---

### Infinite Array — Finding Range First
When array size is unknown (infinite), you cannot directly apply binary search. First find the range where target lies, then apply binary search.

**Algorithm:**
1. Start with box size 2 (start = 0, end = 1)
2. While target > arr[end] → double the box size
3. New start = old end + 1
4. New end = old end + (size of box) * 2

```
size of box = end - start + 1   (finding size by indices)

arr = [2, 3, 5, 6, 7, 8, 10, 11, 12, 15, 20, 23, 30]
target = 15

start=0, end=1 → box=[2,3] → 15 > 3
new start = 2, end = 1 + (1-0+1)*2 = 1+4 = 5 → box=[5,6,7,8,10,11] → 15 > 11  
new start = 6, end = 5 + (5-2+1)*2 = 5+8 = 13 → apply binary search
```

---

### Smallest Letter Greater Than Target
Exact same approach as ceiling of a number with two differences:
1. Ignore the target itself — looking for strictly greater
2. Array wraps around — if start goes out of bounds, return `letters[start % letters.length]`

```
arr = ['c', 'd', 'j', 'j'], target = 'j'
condition violated: start = end + 1 → start = length of array = N
return S % N → N % N = 0 → return letters[0] = 'c'
```

---

### Mountain Array — Peak Finding
When `arr[mid] > arr[mid+1]` → you are in descending part → answer may be here, look left → `end = mid`
When `arr[mid] < arr[mid+1]` → you are in ascending part → peak is to right → `start = mid + 1`

When loop ends, `start == end` pointing to peak element.

**Search in Mountain Array:**
1. Find peak index
2. Binary search on left half (ascending) using order agnostic BS
3. If not found, binary search on right half (descending)

---

## Rotated Binary Search (Search in Rotated Sorted Array)

A rotated sorted array is a sorted array that has been rotated at some pivot point.

```
Original: [2, 4, 5, 7, 8, 9, 10, 12]
After 1 rotation: [12, 2, 4, 5, 7, 8, 9, 10]
After 2 rotations: [10, 12, 2, 4, 5, 7, 8, 9]
```

**Pivot** — the point where the next numbers start ascending again. It is the largest number in the array. From where your next numbers are ascending.

### Algorithm
1. Find the pivot
2. Search in first half (0, pivot) using simple binary search
3. Otherwise search in second half (pivot+1, end)

### Finding Pivot — 4 Cases

**Case 1:** `arr[mid] > arr[mid+1]` → mid element is bigger than next element → mid is the pivot

**Case 2:** `arr[mid] < arr[mid-1]` → mid element is smaller than previous element → mid-1 is the pivot

**Case 3:** `arr[start] >= arr[mid]` → start element is bigger than or equal to mid element → all elements from mid will be smaller than start → these are bigger numbers, ignore mid → `end = mid - 1`

**Case 4:** `arr[start] < arr[mid]` → left side is sorted, pivot is in right half → `start = mid + 1`

```
arr = [3, 4, 5, 6, 7, 0, 1, 2]
                    ↑
                  pivot (index 4, value 7)
```

### RBS Using Pivot
```
arr = [4, 5, 6, 7, 0, 1, 2]
       s        p        e
```
- Case 1: pivot element == target → found
- Case 2: target >= start element → search space = (s, p-1) — because all numbers after pivot are < start
- Case 3: target < start element → search space = (p+1, end)

### For Duplicate Values
When `arr[mid] == arr[start] == arr[end]` → cannot determine which side is sorted → skip duplicates from both ends:
- Check if start is pivot: `if arr[start] > arr[start+1]` → return start
- Increment start
- Check if end is pivot: `if arr[end] < arr[end-1]` → return end
- Decrement end

---

## Common Mistakes

- Using `(start + end) / 2` for mid — can cause integer overflow for large arrays
- Applying binary search on unsorted array — gives wrong results
- Using `while (start < end)` instead of `while (start <= end)` — misses single element arrays
- Forgetting `str.length()` uses parentheses unlike `arr.length`
- Not handling empty array before starting the search loop