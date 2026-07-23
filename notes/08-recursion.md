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