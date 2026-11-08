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

### The Same Problem with the "Countdown" Convention — `(p, r, c)`

In Problem 1, `r` and `c` were the **rows and columns still left to cross**. The same convention works for printing the paths, so the code looks just like `count`: the maze is labelled `3, 2, 1` along both sides, you **start at `(3, 3)`** and the **end is `(1, 1)`**.

```
(p, r, c)     p = the path till the point (r, c)
```
- **Move DOWN**  → `r - 1`, add `'D'`  (only if `r > 1`)
- **Move RIGHT** → `c - 1`, add `'R'`  (only if `c > 1`)
- **Base case:** `r == 1 && c == 1` → reached the end → `p` is a complete path.

**Tree (the sketch):**
```
                          ( "" , 3, 3 )
                 D /                         \ R
          ( D, 2, 3 )                     ( R, 3, 2 )
        D /          \ R                        ...
 ( DD, 1, 3 )     ( DR, 2, 2 )
       | R              ...
 ( DDR, 1, 2 )
       | R
 ( DDRR, 1, 1 )   → base case → add "DDRR" to the answer list
```

**Printing:**
```java
static void path(String p, int r, int c) {
    if (r == 1 && c == 1) {
        System.out.println(p);                    // reached the end
        return;
    }
    if (r > 1) {
        path(p + 'D', r - 1, c);                  // move down
    }
    if (c > 1) {
        path(p + 'R', r, c - 1);                  // move right
    }
}
```
Call it as `path("", 3, 3)`.

**Returning a list** (a new `list = ( )` in each call, merged on the way back up):
```java
static ArrayList<String> pathRet(String p, int r, int c) {
    if (r == 1 && c == 1) {
        ArrayList<String> list = new ArrayList<>();
        list.add(p);                              // base case: the finished path goes into the list
        return list;
    }

    ArrayList<String> list = new ArrayList<>();
    if (r > 1) {
        list.addAll(pathRet(p + 'D', r - 1, c));
    }
    if (c > 1) {
        list.addAll(pathRet(p + 'R', r, c - 1));
    }
    return list;
}
```

**Note (from the lecture):** this is **exactly the same theory as `p, up`** (processed / unprocessed) in the **subsets** question — watch that video for the details. Here `p` is the path already taken, and the remaining maze from `(r, c)` plays the role of the unprocessed part.

### Complexity

```
Number of paths (n × n maze):   C(2(n-1), n-1)      → 6 for n = 3
Time Complexity:                exponential          (every path is generated and printed)
Space Complexity:               O(n)  recursion depth   (the longest path has 2(n-1) moves)
                                (plus the list of paths, if they are stored)
```

---

## Problem 3 — Maze with Diagonal Moves

**Problem:** same maze, but now you may also move **diagonally** (one step down **and** one step right at the same time). Count / print the paths.

From a cell there are now **three** moves:
```
→  RIGHT      (r, c - 1)
↓  DOWN       (r - 1, c)
↘  DIAGONAL   (r - 1, c - 1)
```

### Conditions for Each Move (VVI)

A move is allowed only if it **stays inside the maze**. Using the countdown convention (`r`, `c` = rows/columns left, end at `(1, 1)`):

| Move | Goes to | Allowed only if |
|---|---|---|
| Down | `(r - 1, c)` | `r > 1` |
| Diagonal | `(r - 1, c - 1)` | `r > 1 && c > 1` (needs a free row **and** a free column) |
| Right | `(r, c - 1)` | `c > 1` |

### Tree — `(3, 3)`

```
                        ( 3, 3 )
         down /            | diagonal           \ right
         r > 1            r > 1 && c > 1          c > 1
      ( 2, 3 )           ( 2, 2 )              ( 3, 2 )
```
Every call now makes **up to 3** recursive calls (the sketch shows the three branches from `(3, 3)` with the conditions `r > 1`, `r > 1 && c > 1` and `c > 1`).

### Code — Counting

```java
static int countDiag(int r, int c) {
    if (r == 1 || c == 1) {
        return 1;                                 // only one straight path left
    }
    int down     = countDiag(r - 1, c);           // down
    int diagonal = countDiag(r - 1, c - 1);       // diagonal
    int right    = countDiag(r, c - 1);           // right
    return down + diagonal + right;
}
```
Trace for a `3 × 3` maze:
```
count(2,2) = count(1,2) + count(1,1) + count(2,1) = 1 + 1 + 1 = 3
count(2,3) = count(1,3) + count(1,2) + count(2,2) = 1 + 1 + 3 = 5
count(3,2) = count(2,2) + count(2,1) + count(3,1) = 3 + 1 + 1 = 5
count(3,3) = count(2,3) + count(2,2) + count(3,2) = 5 + 3 + 5 = 13
```
So a `3 × 3` maze has **13** paths with diagonals (versus 6 without).

### Code — Printing the Paths

Down and right are both `D`/`R` in the earlier problems, so use **`V`** (vertical), **`H`** (horizontal) and **`D`** (diagonal) so the letters don't clash:

```java
static void pathDiag(String p, int r, int c) {
    if (r == 1 && c == 1) {
        System.out.println(p);                    // reached the end
        return;
    }
    if (r > 1 && c > 1) {
        pathDiag(p + 'D', r - 1, c - 1);          // diagonal
    }
    if (r > 1) {
        pathDiag(p + 'V', r - 1, c);              // vertical (down)
    }
    if (c > 1) {
        pathDiag(p + 'H', r, c - 1);              // horizontal (right)
    }
}
```
**Small check — a `2 × 2` maze** (`pathDiag("", 2, 2)`):
```
( "" , 2, 2 ) → diagonal → ( D , 1, 1 )                         → "D"
              → vertical → ( V , 1, 2 ) → horizontal → ( VH, 1, 1 )   → "VH"
              → horizontal → ( H , 2, 1 ) → vertical → ( HV, 1, 1 )   → "HV"
```
**Output:** `D`, `VH`, `HV` — 3 paths, matching `count(2, 2) = 3`.

The `H / V / D` letters are my choice for the printing version, since the sketch only shows the three arrows and conditions.