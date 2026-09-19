# Recursion

## What is Recursion?

Recursion is a programming technique where a method calls **itself** to solve a smaller version of the same problem. It's about reducing a problem until the simplest version can solve the rest.

---

## Two Rules of Recursion

Every recursive method must have two parts to avoid running forever and crashing your program:

- **Base Case (the stop sign):** The simplest condition that stops the recursion from calling itself.
- **Recursive Case (the loop):** The part where the method calls itself with a slightly smaller or simpler input.

**Without a base case** (or if the base case is never reached), recursive calls continue indefinitely until the **call stack** is exhausted, resulting in a `StackOverflowError`.

---

## How Function Calls Work (Call Stack)

- **Main function** is the first function that goes into the stack and the last function that comes out of the stack.
- While a function is not finished executing, it remains in the stack.
- When a function finishes executing, it's removed from the stack, and flow of the program is restored to where the function was called.

### Example — Call Stack

```
main()
 |
print(5)
 |
print(4)
 |
print(3)
 |
print(2)
 |
print(1)
 |
base case
```

Every call of a function will take some memory.

### Worked Example — Tracing the Full Push and Pop Order

**Problem:** print the numbers `5` down to `1`, using recursion.

```
fun(n) {
    print(n)
    fun(n - 1)

    if (n == 1) {
        print(1)
        return
    }
}
```

**Call trace for `N = 5`:**
```
fun(5) → fun(4) → fun(3) → fun(2) → fun(1)
   5        4         3         2       1
```
Each call **prints its own `n`, then immediately calls itself with `n-1`** — so the numbers `5, 4, 3, 2, 1` are printed on the way **down** the call chain, before the base case is ever reached.

**What the stack looks like at its deepest point** (all 5 calls pushed, none finished yet):
```
┌─────────┐
│ fun(5)  │  ← pushed first, bottom of the stack
├─────────┤
│ fun(4)  │
├─────────┤
│ fun(3)  │
├─────────┤
│ fun(2)  │
├─────────┤
│ fun(1)  │  ← pushed last, currently executing (base case hit here)
└─────────┘
```

**Popping order (unwinding), once the base case returns:**
```
fun(1) finishes → popped first   (last one pushed, first one popped — LIFO)
fun(2) finishes → popped next
fun(3) finishes → popped next
fun(4) finishes → popped next
fun(5) finishes → popped last    (first one pushed, last one popped)
```

**Key takeaway:** the call stack always behaves as **LIFO (Last In, First Out)** — whichever function call was pushed most recently is always the one that gets popped off first, regardless of how many calls are stacked up.

---

## How to Understand & Approach a Recursion Problem (VVI)

1. **Identify** if you can break down the problem into smaller problems.
2. **Write the recurrence relation**, if needed.
3. **Draw the recursion tree.**
4. **About the tree:** see the flow of functions — how they are getting pushed onto the stack.
5. **See how the values are returned** at each step. See where the function call comes out of. In the end, you will come out of the main function.

### Tips for debugging recursion

- Identify and focus on **left tree calls** and **right tree calls** separately.
- Draw the tree and pointers **again and again**, using pen and paper.
- Use a **debugger** to see the flow, and what type of values are being returned at each step.

**Tip:** Make sure to return the result of a function call of the return type.

---

## Components of a Recursive Function (VVI)

Every function has three parts to think about:

1. **Arguments** — determine the current subproblem.
2. **Return type** — determines what each recursive call returns.
3. **Function body** — contains the base case and the recursive case.

The arguments (e.g. `s`, `e`, `m` — start, end, mid) are **specific to that particular call** and will go into the **next function call** as its own fresh set of arguments — each call has its own copies.

---

## Common Recursive Patterns

1. **Linear recursion** → e.g. factorial, sum of N numbers (calls itself once per step)
2. **Binary recursion** → e.g. Fibonacci (calls itself twice per step)
3. **Divide & Conquer recursion** → e.g. Binary Search (input reduced by a factor each call)

---

## Binary Search with Recursion

- **Comparing** → `O(1)`
- **Dividing into 2 halves** each time

### How the Search Space Shrinks

At each recursive call, the search space is defined by `s` (start), `e` (end), and `m` (mid) — and each call passes a **narrower** `s...e` range into the next call:

```
s        m        e
[__________|__________]
        ↓
     s   m  e
     [____|____]
        ↓
    s m e
    [__|__]
```
Each level is a **future function call**:

```
f()
 ↓
f()
 ↓
f()
```
(Level 1 → Level 2 → Level 3, and so on, until the base case is hit.)

### Trace Example

```
Array:  [1, 3, 5, 6, 8, 10]
Target: 6

Call 1: s=0, e=5, mid=2 → arr[2]=5 < 6 → search right half
Call 2: s=3, e=5, mid=4 → arr[4]=8 > 6 → search left half
Call 3: s=3, e=3, mid=3 → arr[3]=6 == 6 → found, return 3
```
Each recursive call narrows the `s...e` range. Once the base case is hit (element found, or `s > e`), the result is returned back up through each level of the call stack.

### Recurrence Relation

```
F(N) = O(1)  +  F(N/2)
        ↑           ↑
   comparison   dividing in half
```

This is known as a **recurrence relation**.

---

## Types of Recursion

- **Direct Recursion** — a function calls itself directly.
- **Indirect (Mutual) Recursion** — a function calls another function, which in turn calls the first one back.
- **Tail Recursion** — the recursive call is the last operation in the function (see below).
- **Head Recursion** — the recursive call happens before any other processing in the function (work is done *after* returning from the recursive call, on the way back up).
- **Tree (Multiple) Recursion** — a function makes more than one recursive call (e.g. Fibonacci).

---

## Tail Recursion

The recursive call is the **absolute final step** in the function — nothing happens after it.

```java
public int tailFactorial(int n, int accumulator) {
    if (n == 1) return accumulator;
    return tailFactorial(n - 1, n * accumulator);   // Tail recursive
}
```

**Why this is tail recursion:** the multiplication (`n * accumulator`) happens *before* passing the result down. The current function performs no work after the recursive call returns.

**Note:** Java does not perform tail-call optimization, so a new stack frame is still created for every call even when it's tail recursive — this pattern is more about clean logic than actual memory savings in Java.

---

## Why Recursion?

- It helps solve complex problems by breaking them into smaller, similar subproblems.
- You can convert recursion solutions into iteration and vice-versa.
- **Extra space complexity is O(recursion depth)**, because every recursive call adds a new stack frame.

---

## Visualizing a Recursion Tree

Unlike a linear call stack (a straight chain of calls), a true **recursion tree** branches whenever a function makes more than one recursive call — like Fibonacci:

```
                fib(5)
               /      \
          fib(4)        fib(3)
          /    \         /    \
      fib(3)  fib(2)  fib(2)  fib(1)
      /   \    /  \    /  \
  fib(2) fib(1) fib(1) fib(0) fib(1) fib(0)
   /  \
fib(1) fib(0)
```

Each branch represents a recursive call splitting into further sub-calls, going deeper until the base case (`fib(0)` or `fib(1)`) is hit, then values are combined and returned back up the tree.

---

## Example — Find the Nth Fibonacci Number

```
0th  1st  2nd  3rd  4th  5th  6th  7th
0    1    1    2    3    5    8    13   ...
```

### Recurrence Relation

```
Fibo(N) = Fibo(N-1) + Fibo(N-2)
```

This is known as a **recurrence relation** — one problem is divided into two smaller sub-problems (calling itself twice), unlike a linear recursion like `print()` or factorial which only calls itself once per step.

### Complexity

```
Time Complexity:  O(2^N)
Space Complexity: O(N)
```

---

## Understanding the Recursion Tree Call Order

For a recursive function like `f(4) = f(3) + f(2)`, tracing the exact order in which calls are made (and returned) makes the tree much easier to reason about.

```
Call order:
1: f(4)
 ├── 2: f(3)
 │    ├── 3: f(2)
 │    │    ├── 4: f(1)
 │    │    └── 5: f(0)
 │    └── 6: f(1)
 └── 7: f(2)
      ├── 8: f(1)
      └── 9: f(0)
```

**Trick:** Only the calls that are **interlinked** (i.e. lie along the *current active path* from the root down to the call currently executing) will be on the stack **at the same time**. The rest of the tree either hasn't been called yet, or has already finished and been popped off.

This is the key insight that separates **time complexity** from **space complexity** in a recursion tree:

- **Time complexity** depends on the **total number of nodes/calls** in the tree (every node does some work).
- **Space complexity** depends only on the **height of the tree** — i.e. the single longest path from root to a leaf — because that's the maximum number of stack frames that are ever live at once.

```
Space Complexity = Height of the tree (the active path)
```

For `f(4)` above, the tree has 9 total calls (contributing to time complexity), but the deepest path (`f(4) → f(3) → f(2) → f(1)`) is only 4 calls deep — so the space complexity is proportional to that height, not the full node count.

---

## Two Types of Recursion (by Recurrence Relation Shape)

### 1. Linear Recursion

Each call branches into a **constant number of calls**, each on a slightly smaller input — but the total number of calls still grows large because of branching (e.g. Fibonacci-style branching into 2 calls per level).

```
F(N) = F(N-1) + F(N-2)
```

### 2. Divide & Conquer Recursion

Each call splits the input into a **fraction** of its original size (typically half) — leading to much better time complexity.

```
F(N) = F(N/2) + O(1)
```

This is the same shape of recurrence seen in Binary Search above — dividing the problem size at each step rather than just decrementing it.

> **Note:** Recurrence relations like these describe *how* a recursive algorithm's work is structured. For how to actually **solve** them (Master Theorem, Akra–Bazzi, deriving full time complexities like Merge Sort's `Θ(N log N)`), see [`complexity/time-complexity-notes.md`](../complexity/time-complexity-notes.md) — that's algorithm *analysis*, a separate topic from recursion itself.

---

## Application — Printing N to 1, 1 to N, and Both Directions

These three variants show how **where** you place the `print` statement relative to the recursive call completely changes the output order — a direct, concrete illustration of Head vs Tail recursion.

### Print N Down to 1 (Tail-style — print, *then* recurse)

```java
static void fun(int n) {
    if (n == 0) {
        return;
    }
    System.out.println(n);
    fun(n - 1);
}
```
```
fun(5) → prints 5, 4, 3, 2, 1     (on the way DOWN, before the base case)
```

### Print 1 to N (Head-style — recurse first, *then* print)

```java
static void funRev(int n) {
    if (n == 0) {
        return;
    }
    funRev(n - 1);
    System.out.println(n);
}
```
```
funRev(5) → prints 1, 2, 3, 4, 5     (on the way BACK UP, after each call returns)
```
**Why this reverses the order:** every call reaches the base case (`n == 0`) *before* printing anything — so `funRev(1)` is the first to actually print (it's the first to *return*), and `funRev(5)` is the last to print (it's the last to return, at the very top of the unwind).

### Print Both Directions (N down to 1, then back up to N)

```java
static void funBoth(int n) {
    if (n == 0) {
        return;
    }
    System.out.println(n);
    funBoth(n - 1);
    System.out.println(n);
}
```
```
funBoth(5) → prints 5, 4, 3, 2, 1, 1, 2, 3, 4, 5
              ↑___________↑  ↑___________↑
              going down       coming back up
```
Each level prints its `n` **once on the way down** (before the recursive call) and **once on the way back up** (after the recursive call returns) — combining both patterns above into a single "palindrome-shaped" output.

---

## Pitfall — `n--` vs `--n` in a Recursive Call

**A subtle bug that causes infinite recursion:**

```java
static void concept(int n) {
    if (n == 0) {
        return;
    }
    System.out.println(n);
    concept(n--);      // ⚠️ BUG: passes n's ORIGINAL value, decrements AFTER
}
```

**Why this breaks:** `n--` is **post-decrement** — the *current* value of `n` is used first (passed into the recursive call as-is), and only *after* the expression is evaluated does `n` get decremented. So `concept(n--)` is equivalent to:
```
concept(n)      // passes the ORIGINAL n, unchanged
n = n - 1       // decrement happens too late to matter
```
The next call receives the **exact same value of `n`** as the current call — so `n` never actually decreases, the base case (`n == 0`) is never reached, and the function recurses **forever**, eventually causing a `StackOverflowError`.

**The fix — use pre-decrement instead:**
```java
static void concept(int n) {
    if (n == 0) {
        return;
    }
    System.out.println(n);
    concept(--n);      // ✓ correct: decrement happens FIRST, then the new value is passed
}
```
`--n` is **pre-decrement** — `n` is decremented *immediately*, and the **already-updated** value is what gets passed into the recursive call. This guarantees `n` shrinks by `1` on every call, so the base case is eventually reached.

**Rule of thumb:** when passing a decremented/incremented variable directly into a recursive call, always use the **pre**-increment/decrement form (`--n` / `++n`) — never the post form (`n--` / `n++`) — since the post form silently passes the *old* value, which can hide an infinite-recursion bug.

---

## Application — Factorial of a Number

**Problem:** find `N!` (N factorial) — the product of all integers from `N` down to `1`.

**Example — `N = 5`:**
```
5! = 5 × 4 × 3 × 2 × 1 = 120
```

### Spotting the Recursive Structure

Notice that `5!` can be rewritten in terms of a **smaller factorial**:
```
5! = 5 × 4!
4! = 4 × 3!
```
This is exactly the recursive pattern: each factorial is defined in terms of the **next smaller** factorial.

### Recurrence Relation

```
F(N) = N × F(N-1)
F(1) = 1                    // base case
```

### Code

```java
static int fact(int n) {
    if (n <= 1) {
        return 1;
    }
    return n * fact(n - 1);
}
```

**Why `n <= 1` instead of `n == 1`?** Using `<= 1` as the base case also correctly handles `n = 0` (since `0! = 1` by definition), without needing a separate check. It also guards against negative input accidentally skipping past the base case and recursing forever. See `Fact.java` for the implementation.

### Worked Example — Tracing `fact(5)`

**Going down** (building up the chain of pending multiplications):
```
fact(5) = 5 × fact(4)
fact(4) = 4 × fact(3)
fact(3) = 3 × fact(2)
fact(2) = 2 × fact(1)
fact(1) = 1                  ← base case reached
```

**Coming back up** (each call resolves once its recursive call returns):
```
F(1) = 1
F(2) = 2 × F(1) = 2 × 1  = 2
F(3) = 3 × F(2) = 3 × 2  = 6
F(4) = 4 × F(3) = 4 × 6  = 24
F(5) = 5 × F(4) = 5 × 24 = 120
```

**Answer:** `fact(5) = 120` ✓ — matching the direct calculation `5 × 4 × 3 × 2 × 1`.

**Note:** just like the Fibonacci example earlier, no actual multiplication happens on the way *down* — every call just sets up a pending `n × fact(n-1)` expression. The real computation only happens on the way **back up**, once the base case provides a concrete starting value to multiply against.

---

## Application — Sum of Digits of a Number

**Problem:** given a number `N`, find the sum of its digits.

**Example — `N = 1342`:**
```
1 + 3 + 4 + 2 = 10
```

### Spotting the Recursive Structure

The sum can be peeled apart one digit at a time — take the **first** digit and add it to the sum of the rest:
```
sum(1342) = 1 + sum(342)
sum(342)  = 3 + sum(42)
```

In code, it's easier to peel digits from the **right** instead, using two operations:
```
rem = N % 10      →  extracts the LAST digit
N   = N / 10      →  removes the last digit (integer division)
```

**Example — `N = 1342`:**
```
1342 % 10 = 2        ← last digit extracted
1342 / 10 = 134      ← last digit removed
```

### Recurrence Relation

```
F(N) = F(N/10) + (N % 10)
```
i.e. the sum of digits of `N` equals the **last digit** (`N % 10`) plus the sum of digits of **everything before it** (`N / 10`).

**Base case:** when `N` reaches `0`, there are no digits left to add, so return `0`.

### Code

```java
static int sumOfDigits(int n) {
    if (n == 0) {
        return 0;
    }
    return (n % 10) + sumOfDigits(n / 10);
}
```

### Worked Example — Tracing `f(1342)`

**Going down** (peeling one digit per call):
```
f(1342) = 2 + f(134)
f(134)  = 4 + f(13)
f(13)   = 3 + f(1)
f(1)    = 1 + f(0)
f(0)    = 0             ← base case reached
```

**Coming back up:**
```
f(0)    = 0
f(1)    = 1 + 0 = 1
f(13)   = 3 + 1 = 4
f(134)  = 4 + 4 = 8
f(1342) = 2 + 8 = 10
```

**Answer:** `10` ✓ — matching the direct calculation `1 + 3 + 4 + 2`.

**Note:** the `% 10` / `/ 10` pair here plays the same role that `& 1` / `>> 1` played in the bit-manipulation problems — extract the last unit, then shift everything over to move on to the next one. The only difference is the base: `10` for decimal digits, `2` for binary bits.

---

## Application — Reverse a Number

**Problem:** given a number `N`, reverse its digits.

**Example:**
```
N = 1824  →  4281
```

### Why the "Sum of Digits" Recurrence Doesn't Work Here

It might seem tempting to reuse the exact same recurrence from the previous problem:
```
F(N) = (N % 10) + F(N / 10)      ← this is the SUM-OF-DIGITS formula, not reversal!
```
This only **adds** each extracted digit — it never accounts for the digit's new **position** in the reversed number. Reusing it here would just recompute the digit sum again, not build a reversed number.

### The Correct Approach — Build the Result with an Accumulator

Instead, keep a running accumulator (`sum`) **outside** the recursive calls, and at each step:
```
rem = n % 10          // extract the last digit
sum = sum × 10 + rem  // shift the accumulator left by one digit, then append the new digit
```
Multiplying `sum` by `10` before adding `rem` is what correctly gives the newly extracted digit the **right positional weight** — each existing digit in `sum` gets pushed one place further left, making room for the new digit at the units place.

### Code

```java
static int sum = 0;

static void fun(int n) {
    if (n == 0) {
        return;
    }
    int rem = n % 10;
    sum = sum * 10 + rem;
    fun(n / 10);
}
```

### Worked Example — Tracing `fun(1342)`

| Call | `n` | `rem = n % 10` | `sum = sum×10 + rem` | `n / 10` (next call) |
|---|---|---|---|---|
| 1 | 1342 | 2 | `0×10 + 2 = 2` | 134 |
| 2 | 134  | 4 | `2×10 + 4 = 24` | 13 |
| 3 | 13   | 3 | `24×10 + 3 = 243` | 1 |
| 4 | 1    | 1 | `243×10 + 1 = 2431` | 0 |
| 5 | 0    | — | base case, return | — |

**Answer:** `sum = 2431` — the reverse of `1342` ✓

**Quick check — `N = 1824`:**
```
rem sequence: 4, 2, 8, 1
sum: 0×10+4=4  →  4×10+2=42  →  42×10+8=428  →  428×10+1=4281
```
**Answer:** `4281` — the reverse of `1824` ✓

**Note:** unlike the sum-of-digits and factorial examples, this pattern computes the answer entirely on the way **down** (updating a shared accumulator each call), rather than combining values on the way back **up** — closer in spirit to the tail-recursive `tailFactorial` example covered earlier.