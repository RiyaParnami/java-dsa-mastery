# Recursion

## What is Recursion?

Recursion is a programming technique where a method calls **itself** to solve a smaller version of the same problem. It's about reducing a problem until the simplest version can solve the rest.

---

## Two Rules of Recursion

Every recursive method must have two parts to avoid running forever and crashing your program:

- **Base Case (the stop sign):** The simplest condition that stops the recursion from calling itself.
- **Recursive Case (the loop):** The part where the method calls itself with a slightly smaller or simpler input.

**Without a base case**, your code will cause a **Stack Overflow Error** (running out of memory).

---

## How Function Calls Work (Call Stack)

- **Main function** is the first function that goes into the stack and the last function that comes out of the stack.
- While a function is not finished executing, it remains in the stack.
- When a function finishes executing, it's removed from the stack, and flow of the program is restored to where the function was called.

### Example — plain sequential calls

```
print5(5)   → Output: 1
print4(4)   → Output: 2
print3(3)   → Output: 3
print2(2)   → Output: 4
print1(1)   → Output: 5
main()
```

### Recursive function for the same behavior

```
print(5)
print(4)
print(3)
print(2)
print(1)
main()
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

---

## Components of a Recursive Function (VVI)

Every function has three parts to think about:

1. **Arguments**
2. **Return type**
3. **Body of function**

---

## Binary Search with Recursion

- **Comparing** → `O(1)`
- **Dividing into 2 halves** each time

### Recurrence Relation

```
F(N) = O(1)  +  F(N/2)
        ↑           ↑
   comparison   dividing in half
```

This is known as a **recurrence relation**.

---

## Tail Recursion

The recursive call is the **absolute final step** in the function — nothing happens after it.

```java
public int tailFactorial(int n, int accumulator) {
    if (n == 1) return accumulator;
    return tailFactorial(n - 1, n * accumulator);   // Tail recursive
}
```

**Why this is tail recursion:** the multiplication (`n * accumulator`) happens *before* passing the result down. The function immediately hands off its result without needing to remember anything — so it doesn't need to keep the current frame on the call stack to remember the value of `n`.

---

## Why Recursion?

- It helps us in solving bigger/complex problems in a simple way.
- It helps us in breaking down bigger problems into smaller problems.
- You can convert recursion solutions into iteration and vice-versa.
- **Space complexity** is not constant because of recursive calls — each call adds a new frame to the stack.

---

## Visualizing Recursion — Recursion Tree

```
program 1st program
start   over

main()
  |
print(1)
  |
print(2)
  |
print(3)
  |
print(4)
  |
print(5)
```

The recursion tree shows how each call leads into the next, going deeper until the base case is hit, and then unwinding back up as each call finishes and is popped off the stack.

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