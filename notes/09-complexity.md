# Time Complexity

## What is Time Complexity?

**Time Complexity** is a function that measures how the runtime of an algorithm scales as the input size (`n`) grows. It gives us the relationship about how the time will grow as the input grows.

---

## Motivating Example

**Data:** 1,000,000 elements in an array.

**Algorithm:** Linear search for a target that does **not** exist in the array (worst case — has to scan every element).

Run the same algorithm on two different machines:

| Machine | Time Taken |
|---|---|
| Old Computer | 10 secs |
| M1 MacBook (very fast) | 1 sec |

At first glance it looks like the M1 MacBook has "better" performance — and it does, in terms of raw speed. But that's not what time complexity measures.

---

## Time Complexity ≠ Time Taken

> **Both machines have the same time complexity.**

Even though the *time taken* is different (10 secs vs 1 sec), the **relationship between input size and time** is the same for both — i.e. **linear**. As input size doubles, the time taken on *each* machine roughly doubles too. The machines differ only in their constant factor (raw hardware speed), not in how their runtime *scales* with input size.

```
Old Machine:            M1 MacBook:
Time                     Time
 10k |            /       10k |
  5k |          /           5k |
 200 |        /             200|
 100 |      /                100|
  10 | θ1 /                  10 | θ2  /
     +----------------- Size      +------------------ Size
     10  100  200  5k  10k         10  100  200  5k  10k
```

Both graphs are **straight lines** (linear growth) — they just have different slopes/starting points because of the machines' different raw speeds. The *shape* of the relationship (linear) is what time complexity captures — not the actual seconds on the clock.

---

## Why This Matters

- Time complexity is about the **algorithm**, not the **hardware** it runs on.
- It lets us compare algorithms independent of machine speed, language, or implementation details.
- An algorithm with a *better* time complexity (e.g. O(log n)) will eventually always outperform one with a *worse* time complexity (e.g. O(n)) as input size grows — regardless of which machine either one runs on.