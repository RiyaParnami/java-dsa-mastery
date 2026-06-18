# Sorting Algorithms

## Sorting

Sorting is a process of arranging items systematically.

---

## Bubble Sort

The simplest algorithm that works by repeatedly swapping adjacent elements if they are in the wrong order. Also known as **Sinking Sort** or **Exchange Sort**.

### Example
```
[3, 1, 5, 4, 2]

First pass:
3,1,5,4,2 → 1,3,5,4,2 → 1,3,4,5,2 → 1,3,4,2,5
With first pass through the entire array, the largest element (5) came to the end.

Second pass:
1,3,4,2,5 → 1,3,2,4,5
Second largest element comes at second from last index.
Don't need to compare again — it is already sorted.

Third pass:
1,3,2,4,5 → 1,2,3,4,5 → sorted!
```

### How it works internally
```
i = 0, 1st pass:
3,1,5,4,2
↓ swap (j vs j-1)
1,3,5,4,2
↓ swap
1,3,4,5,2
↓ swap
1,3,4,2,5

i = 1, 2nd pass:
1,3,4,2,5
↓ swap
1,3,2,4,5
→ j will only check this because elements after this are already sorted

i = 2, 3rd pass:
1,3,2,4,5
↓ swap
1,2,3,4,5
```

- Outer loop counter `i` — runs `n-1` times
- Inner loop `j` — internal loop runs `(n-i)` times each pass

```java
static void bubble(int[] arr) {
    boolean swapped;
    for (int i = 0; i < arr.length; i++) {
        swapped = false;
        for (int j = 1; j < arr.length - i; j++) {
            if (arr[j] < arr[j-1]) {
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break; // array already sorted, stop early
    }
}
```

---

## Complexity

### Space Complexity
**O(1)** — constant. No extra space is required, like copying the array etc. is not needed. Also known as an **in-place sorting algorithm**.

### Time Complexity

**Best Case — Array is already sorted**
```
i = 0, first pass: 1,2,3,4,5 → no swaps happen
```
Only once it ran, we don't need to check it again. When `j` never swaps for a value of `i`, it means the array is sorted — you can end the program.

```
Best case comparisons = N - 1 → O(N)
```
In time complexity, since we don't want exact time, we just want the relationship i.e. mathematical function (constants are ignored).

**Worst Case — Sorting descending order array to ascending order**
```
5,4,3,2,1
↓
i=0, 1st pass: 4,3,2,1,5 → (N-1) swaps
i=1, 2nd pass: 3,2,1,4,5 → (N-2) swaps
i=2, 3rd pass: 2,1,3,4,5 → (N-3) swaps
i=3, 4th pass: 1,2,3,4,5 → (N-4) swaps
```

### Total Comparisons Calculation
```
total comparisons = (N-1) + (N-2) + (N-3) + (N-4)
                   = 4N - (1+2+3+4)
                   = 4N - [N(N+1)/2]
                   = 4N - (N² + N)/2
                   = O((7N - N²)/2)

total comparisons = O(N²)
```
In time complexity, constant and less dominating terms are ignored — so this becomes O(N²).

---

## Stable vs Unstable Sorting

**Stable Sorting Algorithm** — after sorting, the relative order of elements with the same value is maintained.
```
Before: 10, 20, 20, 30, 10
After:  10, 10, 20, 20, 30
```

**Unstable Sorting Algorithm** — the order of elements with the same value may change after sorting.
```
Before: 10, 20, 20, 30, 10
After:  10, 10, 20, 20, 30  (relative order of equal elements not preserved)
```

---

## Selection Sort

Select an element and put it on its correct index. Get the greatest element of the array and put it on its correct index — can also do vice-versa (select minimum and place it).

### Example
```
i=0: [4, 5, 1, 2, 3] → swap → [4, 3, 1, 2, 5]
i=1: [4, 3, 1, 2, 5] → swap → [2, 3, 1, 4, 5]
i=2: [2, 3, 1, 4, 5] → swap → [2, 1, 3, 4, 5]
i=3: [2, 1, 3, 4, 5] → swap → [1, 2, 3, 4, 5] → sorted!
```
Inner loop searches `N - i - 1` remaining elements for the max each pass.

```java
static void selection(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        int last = arr.length - i - 1;
        int maxIndex = getMaxIndex(arr, 0, last);
        swap(arr, maxIndex, last);
    }
}
```

### Complexity
```
Total comparisons = (n-1) + (n-2) + (n-3) + ... + 1 + 0
                   = n(n-1)/2
                   = (n² - n)/2
                   → neglect less dominating and constant terms
Worst Case = O(n²)
Best Case  = O(n²)  ← even if sorted, still scans for max every pass
```
- Not a stable sorting algorithm
- Performs well on small lists

---

## Insertion Sort

For every index, put that index's element at the correct index of the LHS (left-hand side) — partially sorting the array as you go.

### Example
```
1st pass → i=0: [5,3] sorted → [3,5,4,1,2]
2nd pass → i=1: [3,5,4] sorted → [3,4,5,1,2]
3rd pass → i=2: [3,4,5,1] sorted → [1,3,4,5,2]
4th pass → i=3: [1,3,4,5,2] sorted → [1,2,3,4,5] → sorted!
```

### Detailed walkthrough
```
i=0: [3,4,5,1,2]
  j: 1<5 swap → [3,4,1,5,2]
     1<4 swap → [3,1,4,5,2]
     1<3 swap → [1,3,4,5,2]

i=1: [1,3,4,5,2]
  j: 2<5 swap → [1,3,4,2,5]
     2<4 swap → [1,3,2,4,5]
     2<3 swap → [1,2,3,4,5]
     already sorted → break!

[1,2,3,4,5]
```
When `j` is not smaller than `j-1`, break the loop — because the previous (left) side array is already sorted, meaning if j starts within an element that means previous ones are already sorted.

```java
static void insertion(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        for (int j = i + 1; j > 0; j--) {
            if (arr[j] < arr[j-1]) {
                swap(arr, j, j-1);
            } else {
                break;
            }
        }
    }
}
```

`i` runs from `0` to `n-2` — i.e. `i < (n-1)`.

### Complexity
```
Worst Case (descending order) → O(n²)
Best Case (already sorted) → O(n) — linear, only (n-1) comparisons
```

### Why use Insertion Sort?
- **Adaptive** — steps get reduced if array is sorted. Number of swaps reduced compared to bubble sort (if `j` is not smaller than `j-1`, break the loop)
- **Stable sorting algorithm**
- Used for smaller values of `n` — works well when array is partially sorted
- Takes part in **hybrid sorting algorithms**. The sorting algorithms studied so far (bubble, selection, insertion) work for small data, not big data — for that we study Quick Sort, Merge Sort etc.
- Insertion sort can be combined with Quick Sort or Merge Sort when array is partially sorted

---

## Cyclic Sort

Used when array elements are in a known range and continuous (e.g. 1 to n).

```java
static void sort(int[] arr) {
    int i = 0;
    while (i < arr.length) {
        int correct = arr[i] - 1; // correct index = value - 1
        if (arr[i] != arr[correct]) {
            swap(arr, i, correct);
        } else {
            i++;
        }
    }
}
```

- Place each element at its correct index directly using `value - 1` as the target index
- If element is already at its correct index, move forward; otherwise swap
- Works in O(n) time since each element is placed in at most one swap

---

### Cyclic Sort — Complexity
```
4 swaps made + 5 swaps made after sorting all at last
= (N-1) + N
= (2N - 1) swaps in worst case
= O(N) — linear

Best Case → O(n)
```

### Cyclic Sort Pattern — Different Cases

| Pattern | Best | Average | Worst | Space |
| :--- | :---: | :---: | :---: | :--- |
| Cyclic Sort (range based) | O(n) | O(n) | O(n) | O(1) auxiliary |
| General Cycle Sort (any data, comparison based) | O(n²) | O(n²) | O(n²) | O(1) auxiliary |

General approach for cyclic sort problems: **Check → Swap → Move**

### When to use Cyclic Sort
- When given numbers are from a range **1 to N** → use cyclic sort
- If range is `[0, N]` → every element will be at `index = value`
- If range is `[1, N]` → every element will be at `index = value - 1`

### Cyclic Sort Walkthrough
```
arr = [3, 5, 2, 1, 4], N = 5

index = value - 1 (since range starts from 1)

3,5,2,1,4 → swap 3 with index 2 → 2,5,3,1,4
2,5,3,1,4 → swap 2 with index 1 → 5,2,3,1,4
5,2,3,1,4 → swap 5 with index 4 → 4,2,3,1,5
4,2,3,1,5 → swap 4 with index 3 → 1,2,3,4,5 → sorted!
```

---

## Cyclic Sort Pattern Problems

### Finding the Missing Number
Numbers from 0 till N → total there will be N+1 numbers.

```
N = 4, arr = [4, 0, 2, 1]
Sorted version would be [0,1,2,3,4] where element == index

Tip: if range is [0,N], every element will be at index = value
After cyclic sort: index where arr[i] != i is missing
If no mismatch found → N+1 is the answer (missing number is N itself, since the array only has N elements but range is 0 to N)
```

### Set Mismatch (find duplicate and missing number both)
```
arr after cyclic sort: if arr[index] != index + 1
→ the value at that index is the duplicate
→ index + 1 is the missing number
```

```java
for (int index = 0; index < arr.length; index++) {
    if (arr[index] != index + 1) {
        return new int[] {arr[index], index + 1}; // {duplicate, missing}
    }
}
```

### Find All Duplicates in an Array
Same cyclic sort approach — after sorting, any index where `arr[index] != index + 1` holds a duplicate value.

### First Missing Positive
Important difference from standard cyclic sort:
- Ignore negative numbers and numbers greater than array length (since only positive numbers within range matter)
- Start checking from 1
- Condition for swap: `arr[i] > 0 && arr[i] <= arr.length && arr[i] != arr[correct]`

```java
if (arr[i] > 0 && arr[i] <= arr.length && arr[i] != arr[correct]) {
    swap(arr, i, correct);
} else {
    i++;
}
```

Two cases for the answer:
1. If a mismatch is found at some index → `index + 1` is the missing positive
2. If no mismatch found (array is fully `1...n`) → answer is `arr.length + 1`

---

## Common Mistakes

- Forgetting the `swapped` flag — without it, bubble sort always runs O(N²) even on a sorted array
- Off-by-one in inner loop bound — should be `arr.length - i`, not `arr.length`
- Confusing stable vs unstable when objects have multiple fields (e.g. sorting by one field but expecting another field's order to be preserved)