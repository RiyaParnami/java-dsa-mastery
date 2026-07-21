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