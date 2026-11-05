# Backtracking — Maze Problems

> **Prerequisite:** the `processed / unprocessed` pattern, "list as argument" vs "new list per call", and the `for`-loop recursion from [`08-recursion.md`](./08-recursion.md) (Subsets, Permutations, Dice Throw).

These first maze problems are the lead-in to backtracking: the recursion tree is the same, but now the "choices" are **moves on a grid**.

---

## Problem 1 — Count the Number of Paths in a Maze

**Problem:** a maze (grid) of `r` rows and `c` columns. Start at the **top-left** cell and reach the **bottom-right** cell. You may only move **right** or **down**. Count the number of different paths.

**Example:** a `3 × 3` maze → **6** paths.

### The Idea (VVI)

Let `count(r, c)` be the number of paths in a maze with `r` rows and `c` columns still to cross. From the current cell there are only two moves:

```
move DOWN   → one row is used up      → count(r - 1, c)
move RIGHT  → one column is used up   → count(r, c - 1)

count(r, c) = count(r - 1, c) + count(r, c - 1)
```
**Base case:** when only **one row** or **one column** is left (`r == 1` or `c == 1`), there is only **one** straight path → return `1`.

### Recursion Tree — `count(3, 3)`

```
                         (3, 3) = 6
                      /              \
              (2, 3) = 3            (3, 2) = 3
              /        \            /        \
        (1, 3) = 1   (2, 2) = 2  (2, 2) = 2   (3, 1) = 1
                      /     \      /     \
                (1, 2)=1 (2, 1)=1 (1, 2)=1 (2, 1)=1
```

**Coming back up (adding the two branches):**
```
count(1,2) = 1      count(2,1) = 1      count(1,3) = 1      count(3,1) = 1
count(2,2) = count(1,2) + count(2,1) = 1 + 1 = 2
count(2,3) = count(1,3) + count(2,2) = 1 + 2 = 3
count(3,2) = count(2,2) + count(3,1) = 2 + 1 = 3
count(3,3) = count(2,3) + count(3,2) = 3 + 3 = 6      ← answer
```

### Code

```java
static int count(int r, int c) {
    if (r == 1 || c == 1) {
        return 1;                                 // only one straight path left
    }
    int down  = count(r - 1, c);                  // move down
    int right = count(r, c - 1);                  // move right
    return down + right;
}
```
Call it as `count(3, 3)` → `6`.

### Notice: the Same Subproblem Is Solved Twice

`(2, 2)` appears **twice** in the tree (it was circled in the sketch), and its whole subtree is computed again each time. For bigger mazes this repeated work grows very fast.

> This is the idea behind **Dynamic Programming (DP)** — remember the answer to a subproblem instead of recomputing it. It is covered in **future videos**.

---

## Problem 2 — Print All the Paths in a Maze

**Problem:** same maze, same moves (**R**ight and **D**own), but now **print every path** instead of counting them.

```
Start A = (0, 0)      End B = (2, 2)       (3 × 3 maze, cells numbered 0 … 2)
```

**Answer:** the 6 paths for a `3 × 3` maze:
```
RRDD    RDRD    RDDR    DRRD    DRDR    DDRR
```
(The sketch lists four of them — `RRDD, DDRR, RDDR, RDRD` — and `DRRD` and `DRDR` complete the six.)

### The Idea (VVI)

The function takes **three** arguments — the path so far, and the current position:

```
(p, r, c)
 │   └──┴── current cell (row, column)
 └── p = processed  = the path taken till that point
```
This is the same **processed / unprocessed** pattern as before: `p` is what is already done, and the remaining maze (from `(r, c)` to `B`) is what is left.

From `(r, c)` there are two moves:
```
move RIGHT → add 'R' to p, go to (r, c + 1)
move DOWN  → add 'D' to p, go to (r + 1, c)
```

**Why carry the path in `p`?** Different routes can reach the **same cell**, so the cell alone doesn't tell you how you got there:

```
R D  +  ( rest of the path )        both RD and DR end up at the cell (1, 1),
D R  +  ( rest of the path )        but they are different paths so far
 └── "path till that point" ──┘
```
The path so far (`RD` or `DR`) is carried in `p`, and the recursive calls from `(1, 1)` add the rest of the path onto it.

**Base case:** reached the end cell `B = (2, 2)` → `p` is a complete path → print it.

**Boundaries:** only move **right** if `c < 2`, and only move **down** if `r < 2`, so we never walk off the maze.

### Code — Printing

```java
static void path(String p, int r, int c) {
    if (r == 2 && c == 2) {
        System.out.println(p);                    // reached B: p is one complete path
        return;
    }

    if (c < 2) {
        path(p + 'R', r, c + 1);                  // move RIGHT
    }
    if (r < 2) {
        path(p + 'D', r + 1, c);                  // move DOWN
    }
}
```
Call it as `path("", 0, 0)`.

**For any maze size**, replace the `2`s with the last row and column index (`rows - 1`, `cols - 1`), or pass them in as arguments.

### Recursion Tree — `path("", 0, 0)`

`( p , r , c )` at every node:

```
                                ( "" , 0, 0 )
                    R /                              \ D
              ( R , 0, 1 )                        ( D , 1, 0 )
          R /            \ D                   R /            \ D
   ( RR, 0, 2 )      ( RD, 1, 1 )         ( DR, 1, 1 )      ( DD, 2, 0 )
        | D            R /     \ D           R /     \ D          | R
   ( RRD, 1, 2 )  ( RDR,1,2) ( RDD,2,1)  ( DRR,1,2) ( DRD,2,1)  ( DDR, 2, 1 )
        | D            | D        | R         | D        | R         | R
   RRDD ✓          RDRD ✓      RDDR ✓      DRRD ✓      DRDR ✓       DDRR ✓
```
**Output (right-first order):**
```
RRDD
RDRD
RDDR
DRRD
DRDR
DDRR
```
Notice `( RD, 1, 1 )` and `( DR, 1, 1 )` — the **same cell**, reached by two different paths.

### Code — Returning the Paths in a List

Same "new list in the body, merge on the way back up" pattern as permutations and the dice problem:

```java
static ArrayList<String> pathRet(String p, int r, int c) {
    if (r == 2 && c == 2) {
        ArrayList<String> list = new ArrayList<>();
        list.add(p);                              // one complete path
        return list;
    }

    ArrayList<String> ans = new ArrayList<>();    // new list for THIS call

    if (c < 2) {
        ans.addAll(pathRet(p + 'R', r, c + 1));   // paths that start by going right
    }
    if (r < 2) {
        ans.addAll(pathRet(p + 'D', r + 1, c));   // paths that start by going down
    }
    return ans;
}
```

### Complexity

```
Number of paths (n × n maze):   C(2(n-1), n-1)      → 6 for n = 3
Time Complexity:                exponential          (every path is generated and printed)
Space Complexity:               O(n)  recursion depth   (the longest path has 2(n-1) moves)
                                (plus the list of paths, if they are stored)
```