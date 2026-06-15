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

## Common Mistakes

- Forgetting the `swapped` flag — without it, bubble sort always runs O(N²) even on a sorted array
- Off-by-one in inner loop bound — should be `arr.length - i`, not `arr.length`
- Confusing stable vs unstable when objects have multiple fields (e.g. sorting by one field but expecting another field's order to be preserved)