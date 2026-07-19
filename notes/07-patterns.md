# Pattern Questions (Nested Loop Logic)

## How to Approach

1. **Number of rows = number of iterations of the outer loop**
2. **Identify, for every row number:**
   - How many columns are there in that row
   - What should be printed? (stars, numbers, alphabets, spaces, etc.)
3. **Decide what you need to print**

**Note:** Try to find the formula relating row and column.

**General Template**

```java
for (int row = 0; row < rows; row++) {
    for (int col = 0; col < columnsInCurrentRow; col++) {
        // print required element
    }
    System.out.println();
}
```

### Example — increasing columns per row

```
*
* *
* * *
* * * *
```
```
4 rows,
1st row → 1 col
2nd row → 2 cols
3rd row → 3 cols
4th row → 4 cols
```

---

## Diamond Pattern – Number of Stars per Row

```
1
2
3
4
5
4
3
2
1
```

### Upper half
```
If rows start from 1:
    stars = row

If rows start from 0:
    stars = row + 1
```

### Lower half
```
If rows start from 1:
    stars = 2*N - row

(Equivalent formulas are used for other indexing conventions.)
```

### Clean implementation

```java
int totalRows = 2 * N - 1;

for (int row = 1; row <= totalRows; row++) {
    int stars;

    if (row <= N)
        stars = row;
    else
        stars = totalRows - row + 1;
}
```

This avoids the confusion of indexing-dependent conditions like `row > N` — it directly compares the current row against the total number of rows in the pattern.

> This pattern-printing topic also **completes the "Pattern questions" checklist item** from the Arrays section, since the same nested-loop logic is reused there.

---

# Minimum Distance to the Matrix Boundary

## Distances

For a cell at `(row, col)` in an `N x N` matrix, using standard 0-indexing (`0 ... N-1`):

```
left  = col
up    = row
right = N - 1 - col
down  = N - 1 - row
```

## Answer for Each Cell

```
min(left, up, right, down)
```

### Worked example

```
Grid size = 9×9
Indices  = 0..8

Cell (row = 1, col = 1)

left  = 1
up    = 1
right = 8 - 1 = 7
down  = 8 - 1 = 7

min(1, 1, 7, 7) = 1
```

## Full Table (9×9 grid, indices 0..8)

|   | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |
|---|---|---|---|---|---|---|---|---|---|
| 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| 1 | 0 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 0 |
| 2 | 0 | 1 | 2 | 2 | 2 | 2 | 2 | 1 | 0 |
| 3 | 0 | 1 | 2 | 3 | 3 | 3 | 2 | 1 | 0 |
| 4 | 0 | 1 | 2 | 3 | 4 | 3 | 2 | 1 | 0 |
| 5 | 0 | 1 | 2 | 3 | 3 | 3 | 2 | 1 | 0 |
| 6 | 0 | 1 | 2 | 2 | 2 | 2 | 2 | 1 | 0 |
| 7 | 0 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 0 |
| 8 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |

Each cell's value is the minimum straight-line distance to the nearest edge of the grid. Since the grid has no obstacles and distances are measured directly to the boundaries, the answer can be computed using the four formulas above — no BFS or graph traversal is required.