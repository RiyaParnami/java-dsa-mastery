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

---

## Problem 4 — Maze with Obstacles

**Problem:** a `3 × 3` maze where some cells are **blocked** (a "river"). Moves are still **Right** and **Down**. Print every path from the top-left cell to the bottom-right cell that **does not step on a river cell**.

### Representing the Maze — a Boolean Matrix

```
false → river (blocked)          true → free cell
```

```java
boolean[][] maze = {
    { true, true,  true },
    { true, false, true },        // the cell (1, 1) is a river
    { true, true,  true }
};
```
```
      0      1      2
0   free   free   free
1   free  RIVER   free
2   free   free   free
```

### The Rule (VVI)

> **When you land on a new cell, check whether that cell is a river or not.**
> **If you land on a river, stop the recursion for that call.**

So the check goes at the **start of every call**, before any further moves are made from that cell. That call simply `return`s — nothing below it is explored, and the other branches carry on as usual.

(Cutting a branch off as soon as it hits something invalid is called **pruning**.)

### Recursion Tree — `(p, 0, 0)`

`(row, col)` shown for each call, `p` = the path so far:

```
                              ( p , 0, 0 )
                    D /                         \ R
               ( 1, 0 )                       ( 0, 1 )
            D /        \ R                  D /        \ R
       ( 2, 0 )      ( 1, 1 )         ( 1, 1 )        ( 0, 2 )
          | R         RIVER ✗          RIVER ✗             | D
       ( 2, 1 )     stop here         stop here         ( 1, 2 )
          | R                                              | D
       ( 2, 2 ) ✓                                        ( 2, 2 ) ✓
```
Both `(1, 1)` calls land on the river and **stop**. The sketch circles the left one with a blue `X`; the right one is cut off for the same reason. Only the two branches that go around it reach `(2, 2)`.

### Code

```java
static void pathRestrictions(String p, boolean[][] maze, int r, int c) {
    if (!maze[r][c]) {
        return;                                       // landed on a river → stop this call
    }

    if (r == maze.length - 1 && c == maze[0].length - 1) {
        System.out.println(p);                        // reached the end: p is a valid path
        return;
    }

    if (r < maze.length - 1) {
        pathRestrictions(p + 'D', maze, r + 1, c);    // move DOWN
    }
    if (c < maze[0].length - 1) {
        pathRestrictions(p + 'R', maze, r, c + 1);    // move RIGHT
    }
}
```
Call it as `pathRestrictions("", maze, 0, 0)`.

**Output:**
```
DDRR
RRDD
```
- `DDRR` → `(0,0) → (1,0) → (2,0) → (2,1) → (2,2)`
- `RRDD` → `(0,0) → (0,1) → (0,2) → (1,2) → (2,2)`

**The two paths on the grid** (`≈` = river, numbers = order of the steps):
```
 DDRR                RRDD
 0  .  .             0  1  2
 1  ≈  .             .  ≈  3
 2  3  4             .  .  4
```

The other 4 paths of the `3 × 3` maze (`DRDR`, `DRRD`, `RDDR`, `RDRD`) all pass through `(1, 1)`, so they are cut off. Without the river there would be **6** paths; with it, **2**.

### Why the River Check Comes First

The river check is placed **before** the "reached the end" check. That way, if the **start** or the **end** cell itself is a river, the function returns immediately and prints **nothing** (no valid path exists) — instead of wrongly printing a path that ends on a blocked cell.

### Complexity

```
Time Complexity:   exponential in the worst case  (no rivers → all C(2(n-1), n-1) paths are explored)
Space Complexity:  O(n)  recursion depth          (the longest path has 2(n-1) moves)
```
Obstacles only **reduce** the work, since every river cell removes all the paths that go through it.

---

## Problem 5 — All Paths in a Maze (4 Directions) — Backtracking

**Problem:** same `3 × 3` maze, but now you may move in **all four directions** — **D**own, **R**ight, **U**p and **L**eft. Print **every path** from the top-left cell `(0, 0)` to the bottom-right cell `(2, 2)`.

### The New Problem — Going Back Over Your Own Steps

With `U` and `L` allowed, a path can walk **back onto a cell it has already used**:

```
( "" , 0, 0 )
     │ D
( D , 1, 0 )
     │ U
( DU , 0, 0 )      ← back at the START cell!
     │ D
( DUD , 1, 0 )     ← and again ... D, U, D, U, ...
```
The recursion **never stops** (`StackOverflowError`), and even if it did, a path like `DU…` that comes back to a cell it already visited **cannot be an answer**.

> **Do not move back on the same path.** (Only go to **new** cells.)

### How to Solve? (VVI)

> **All the cells that are visited — mark them as `false`, so that the recursion does not go there again.**

This **reuses the river idea from Problem 4**: a `false` cell stops the call. The only difference is that *we* now create the "rivers" ourselves, as we walk.

```
( "" , 0, 0 )
   ├── ( D , 1, 0 )
   │     ├── ( DD , 2, 0 )
   │     ├── ( DR , 1, 1 )
   │     └── ( DU , 0, 0 )   ✗  (0,0) is already false → stop       ← the X in the sketch
   └── ( R , 0, 1 )
```

### Common Sense (VVI)

> **Marking a cell `false` == "I have that cell in my current path."**

The mark must only mean *"used by the path I am **currently** building"*. So **when that path is over** — for example, when you arrive at another recursion call that belongs to a **different path** — those cells **should not be `false`**.

> ★ **While you are moving back, you restore the maze as it was.**

### When Do We Go Back?

> **When the function returns.**

When you come **out of** a recursive call, you are back in the call that made it, which is now finished with that branch. So **re-mark the cell as `true`** at that point.

```
mark the cell false      ← on the way DOWN (entering the cell)
   ... make all the recursive calls ...
mark the cell true       ← on the way back UP (the function is about to return)
```
**This is known as backtracking** — make a change, explore, then **undo the change** so the next branch starts from the original state.

### Code

```java
static void allPath(String p, boolean[][] maze, int r, int c) {
    if (!maze[r][c]) {
        return;                                       // river OR already on my current path → stop
    }

    if (r == maze.length - 1 && c == maze[0].length - 1) {
        System.out.println(p);                        // reached the end: p is a valid path
        return;
    }

    maze[r][c] = false;                               // I'm using this cell in my current path

    if (r < maze.length - 1) {
        allPath(p + 'D', maze, r + 1, c);             // down
    }
    if (c < maze[0].length - 1) {
        allPath(p + 'R', maze, r, c + 1);             // right
    }
    if (r > 0) {
        allPath(p + 'U', maze, r - 1, c);             // up
    }
    if (c > 0) {
        allPath(p + 'L', maze, r, c - 1);             // left
    }

    maze[r][c] = true;                                // BACKTRACK: restore the cell as it was
}
```
Call it as:
```java
boolean[][] maze = {
    { true, true, true },
    { true, true, true },
    { true, true, true }
};
allPath("", maze, 0, 0);
```

**Why does the "reached the end" `return` not need a restore?** It returns *before* the cell is marked `false`, so there is nothing to undo.

**Bounds checks:** `r > 0` and `c > 0` are the new ones — `U` and `L` must not walk off the **top** or **left** edge.

### Trace — Marking and Restoring (`DDRURD`)

`F` = marked `false` (on my current path), `.` = free:
```
start (0,0)       D → (1,0)        D → (2,0)        R → (2,1)
 F . .             F . .            F . .            F . .
 . . .             F . .            F . .            F . .
 . . .             . . .            F . .            F F .

U → (1,1)         R → (1,2)        D → (2,2) = END → print "DDRURD"
 F . .             F . .
 F F .             F F F
 F F .             F F .
```
Then the calls **return one by one**, and each one sets its own cell back to `true` on the way out — so the next branch (for example `DR…` from `(1,0)`) sees a clean maze again.

### What Goes Wrong Without the Restore?

If you mark cells `false` but **never set them back to `true`**, the first path leaves its cells blocked forever. Every later path that needs one of those cells is wrongly cut off, so **valid answers go missing**. The restore step is what makes the marks apply to the **current path only**.

### Output — a `3 × 3` Maze

There are **12** paths. Six start with `D`:
```
DDRR     DDRURD     DDRUURD     DRDR     DRRD     DRURDD
```
and six start with `R` (the mirror images, swapping `D ↔ R` and `U ↔ L`):
```
RRDD     RRDLDR     RRDLLDR     RDRD     RDDR     RDLDRR
```
`DDRR`, `DDRURD`, `DRDR`, … are all valid; none of them uses the same cell twice. (A path that goes `D` then `U` is cut off at once because `(0, 0)` is already `false`.)

### Complexity

```
Time Complexity:   exponential   (all simple paths from start to end are explored; 4 choices per call)
Space Complexity:  O(n × m)      recursion depth — a path can visit every cell once
                                 (no extra visited array: the maze itself is used as the "visited" marker)
```

### Pattern — Backtracking in One Line

```
choose (mark)  →  explore (recursive calls)  →  un-choose (restore)
```
The recursion tree is the same as before; the new part is **undoing your change when the function returns**, so every branch starts from the state its parent had.

---

## Problem 6 — Print the Path as a Matrix of Step Numbers

**Problem:** same 4-direction maze as Problem 5, but instead of printing the path as a string (`DDRR`), print the **maze itself**, with each cell on the path filled with **the step number** at which it was visited (`1` for the start, `2` for the next cell, and so on).

**Example 1 — path `DDRR`:**
```
 1  0  0
 2  0  0
 3  4  5
```
**Example 2 — path `DDRURD`:**
```
 1  0  0
 2  5  6
 3  4  7
```
Cells that are **not** on the path stay `0`. (The sketch leaves the last cell of the first grid blank; the path `DDRR` has `5` steps, so the end cell gets `5`.)

### The 4 Steps (from the sketch)

1. **Take a `step` variable** — pass it as an argument; start it at `1`.
2. **Update the `path` array** — when you enter a cell, write the current `step` into `path[r][c]`.
3. **Print it in the base condition** — when you reach the end cell, write its step number and print the whole `path` matrix.
4. **Backtrack** — when the function returns, **undo** the cell in `path` (set it back to `0`), together with the `true` restore from Problem 5.

### Code

```java
static void allPathPrint(String p, boolean[][] maze, int r, int c, int[][] path, int step) {
    if (!maze[r][c]) {
        return;                                       // river OR already on my current path
    }

    if (r == maze.length - 1 && c == maze[0].length - 1) {
        path[r][c] = step;                            // the end cell's step number
        for (int[] row : path) {
            System.out.println(Arrays.toString(row)); // print the matrix of steps
        }
        System.out.println(p);                        // and the path string
        System.out.println();
        path[r][c] = 0;                               // undo the end cell too
        return;
    }

    maze[r][c] = false;                               // cell is on my current path
    path[r][c] = step;                                // step 2: record the step number

    if (r < maze.length - 1) {
        allPathPrint(p + 'D', maze, r + 1, c, path, step + 1);
    }
    if (c < maze[0].length - 1) {
        allPathPrint(p + 'R', maze, r, c + 1, path, step + 1);
    }
    if (r > 0) {
        allPathPrint(p + 'U', maze, r - 1, c, path, step + 1);
    }
    if (c > 0) {
        allPathPrint(p + 'L', maze, r, c - 1, path, step + 1);
    }

    maze[r][c] = true;                                // backtrack: free the cell again
    path[r][c] = 0;                                   // backtrack: erase its step number
}
```
Call it as:
```java
boolean[][] maze = {
    { true, true, true },
    { true, true, true },
    { true, true, true }
};
int[][] path = new int[3][3];                         // all 0 to begin with
allPathPrint("", maze, 0, 0, path, 1);
```

### Why `step + 1` and Not `step++`

Same lesson as the `n--` pitfall in the recursion notes: `step++` would pass the **old** value and change `step` *after* the call, so every neighbour would get the same number. `step + 1` passes the new value and **leaves `step` unchanged in this call** — which is exactly what the other branches of this same call need.

### Trace — `DDRURD`

Step number written when each cell is entered:
```
(0,0) → 1      (1,0) → 2      (2,0) → 3      (2,1) → 4
(1,1) → 5      (1,2) → 6      (2,2) → 7  ← end: print
```
```
 1  0  0
 2  5  6
 3  4  7
```
Then every call returns and resets its own cell to `0` (and `true` in `maze`), so the next path starts from an empty `path`.

### Why Reset `path` Too?

If you only restore `maze` and forget `path[r][c] = 0`, **leftover numbers from earlier paths stay in the matrix**. A later path like `DRDR` (which does not visit those cells) would be printed with stale step numbers from a different path mixed in. Backtracking must undo **every** change you made — the `visited` mark **and** the `path` entry.

### Complexity

```
Time Complexity:   exponential   (all simple paths are explored)
                   + O(n × m) extra for each path printed (printing the matrix)
Space Complexity:  O(n × m)      the extra `path` matrix (plus the recursion depth, also up to n × m)
```

---

## Problem 7 — N-Queens

**Problem:** place `N` queens on an `N × N` chessboard so that **no two queens attack each other**. Count (and print) every valid arrangement.

A queen attacks in **8 directions** — along its row, its column and both diagonals, as far as the board goes:
```
  ↖   ↑   ↗
    \ | /
  ← — Q — →
    / | \
  ↙   ↓   ↘
```
So no two queens may share a **row**, a **column** or a **diagonal**.

**Example — `N = 4`** (place 4 queens on a `4 × 4` board). There are **2** valid answers:
```
 . Q . .          . . Q .
 . . . Q          Q . . .
 Q . . .          . . . Q
 . . Q .          . Q . .
```

**Points (from the sketch):** it uses **recursion** ✓ and **backtracking** ✓.

### The Idea (VVI)

**One queen per row.** Every row must hold exactly one queen (two queens in a row would attack each other), so build the answer **row by row**, from the top:

```
for the current row r:
    try every column c = 0 … N-1
        if (r, c) is SAFE → place a queen there, then solve row r + 1
        after coming back  → REMOVE the queen (backtrack), try the next column
```
**Base case:** `row == N` → all `N` rows have a queen → one valid arrangement → count it / print the board.

The sketch writes the function as `(board, r, c)`. In the tree, every node is a cell `(r, c)` we **try** to place a queen in:
```
Row 0 choices:    (0,0)   (0,1)   (0,2)   (0,3)
                    |
Row 1 choices:    (1,0)   (1,1)   (1,2)   ...      ← tried under the queen at (0,0)
```
Most of these are **not safe** — for example, with a queen at `(0,0)`, both `(1,0)` (same column) and `(1,1)` (diagonal) are rejected — so those branches are cut at once (pruning, like the river in Problem 4).

### How to Check if a Cell Is Safe (VVI) — `isSafe(board, r, c)`

Because queens are placed **from the top, row by row**, there is nothing below the current row yet. So we only look **upward**, in 3 directions from `(r, c)`:

| Direction | Cells to check | How many cells |
|---|---|---|
| **Vertical** (same column, above) | `(0, c) … (r-1, c)` | rows `[0, r)` |
| **Left diagonal** (up-left) | `(r-1, c-1)`, `(r-2, c-2)`, … → **`r--, c--`** | `min(r, c)` |
| **Right diagonal** (up-right) | `(r-1, c+1)`, `(r-2, c+2)`, … → **`r--, c++`** | `min(r, length - c - 1)` |

**Why `min(…)` for the diagonals?** The diagonal stops at whichever edge you hit **first**:
- Going **up-left** you run out of rows after `r` steps, or out of columns after `c` steps → `min(r, c)`.
- Going **up-right** you run out of rows after `r` steps, or out of columns after `length - c - 1` steps (the columns to the right of `c`) → `min(r, length - c - 1)`.

```
Example, a 5 × 5 board (columns 0-4), checking cell (3, 2):
  left diagonal  : min(3, 2)         = 2 cells  → (2,1), (1,0)
  right diagonal : min(3, 5 - 2 - 1) = 2 cells  → (2,3), (1,4)
```

### Code

```java
static int queens(boolean[][] board, int row) {
    if (row == board.length) {
        display(board);                               // all N queens placed → one valid answer
        System.out.println();
        return 1;
    }

    int count = 0;

    for (int col = 0; col < board.length; col++) {    // try every column of this row
        if (isSafe(board, row, col)) {
            board[row][col] = true;                   // place the queen
            count += queens(board, row + 1);          // solve the remaining rows
            board[row][col] = false;                  // BACKTRACK: remove the queen
        }
    }
    return count;
}

private static boolean isSafe(boolean[][] board, int row, int col) {
    // vertical — same column, rows above
    for (int i = 0; i < row; i++) {
        if (board[i][col]) {
            return false;
        }
    }

    // left diagonal — (r--, c--)
    int maxLeft = Math.min(row, col);
    for (int i = 1; i <= maxLeft; i++) {
        if (board[row - i][col - i]) {
            return false;
        }
    }

    // right diagonal — (r--, c++)
    int maxRight = Math.min(row, board.length - col - 1);
    for (int i = 1; i <= maxRight; i++) {
        if (board[row - i][col + i]) {
            return false;
        }
    }

    return true;                                      // nothing attacks this cell
}

private static void display(boolean[][] board) {
    for (boolean[] row : board) {
        for (boolean cell : row) {
            System.out.print(cell ? "Q " : "X ");
        }
        System.out.println();
    }
}
```
Call it as:
```java
int n = 4;
boolean[][] board = new boolean[n][n];
System.out.println(queens(board, 0));                 // prints the boards, then 2
```

**Why no row check?** There is exactly one queen per row by construction (we move to `row + 1` after each placement), so two queens can never share a row.

### Trace — `N = 4`, Finding the First Answer

`Q(r, c)` = queen placed, `✗` = not safe (reason in brackets), `↩` = backtrack:
```
row 0:  try (0,0) ✓ place
  row 1:  (1,0) ✗ column     (1,1) ✗ diagonal     (1,2) ✓ place
    row 2:  (2,0) ✗ col   (2,1) ✗ diag   (2,2) ✗ col   (2,3) ✗ diag      → dead end ↩ remove (1,2)
  row 1:  (1,3) ✓ place
    row 2:  (2,0) ✗ col   (2,1) ✓ place
      row 3:  (3,0) ✗   (3,1) ✗   (3,2) ✗ diag   (3,3) ✗ col             → dead end ↩ remove (2,1)
    row 2:  (2,2) ✗ diag   (2,3) ✗ col                                    → dead end ↩ remove (1,3)
  row 1:  nothing left                                                   ↩ remove (0,0)

row 0:  try (0,1) ✓ place
  row 1:  (1,0) ✗ diag   (1,1) ✗ col   (1,2) ✗ diag   (1,3) ✓ place
    row 2:  (2,0) ✓ place
      row 3:  (3,0) ✗ col   (3,1) ✗ col   (3,2) ✓ place
        row 4 == N → ANSWER:  (0,1) (1,3) (2,0) (3,2)
```
```
 . Q . .
 . . . Q
 Q . . .
 . . Q .
```
The recursion then keeps backtracking and, starting from `(0,2)`, finds the second answer (the mirror image). `(0,3)` gives nothing more.

### Why Backtrack (Remove the Queen)?

`board[row][col] = false` after the recursive call is what makes **every column in a row start from the same board** — the cell was placed **for this attempt only**. Without it, the earlier queens would stay on the board forever and later attempts would find them as "attackers", rejecting valid cells. Same idea as restoring the maze cells in Problem 5.

### Complexity

```
Time Complexity:   about O(N!)    — row 0 has N choices, but each queen removes at least one column
                                    (and some diagonals) from the next row, so fewer than N each time;
                                    isSafe adds an extra O(N) per check
Space Complexity:  O(N²)          the board  (the recursion depth is only N, one call per row)
```
Pruning with `isSafe` is what keeps this far below the `N^N` of trying every cell blindly.