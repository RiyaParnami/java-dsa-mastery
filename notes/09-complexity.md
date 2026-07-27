# Time Complexity

## What is Time Complexity?

**Time Complexity** describes how the running time of an algorithm grows as the input size (`n`) increases. It focuses on the **growth rate**, not the actual execution time.

---

## Why Not Measure Seconds?

**Data:** 1,000,000 elements in an array.

**Algorithm:** Linear search for a target that does **not** exist in the array (worst case — has to scan every element).

Run the same algorithm on two different machines:

| Machine | Time Taken |
|---|---|
| Old Computer | 10 secs |
| M1 MacBook (very fast) | 1 sec |

At first glance it looks like the M1 MacBook has "better" performance — and it does, in terms of raw speed. But that's not what time complexity measures.

> **Both machines have the same time complexity.**

Even though the *time taken* is different (10 secs vs 1 sec), the **relationship between input size and time** is the same for both — i.e. **linear**. As input size doubles, the time taken on *each* machine roughly doubles too.

**Time Complexity ≠ Time Taken.**

---

## Hardware Independence

```
Time
 ^
 |              O(n)
 |            /
 |          /
 |        /
 |      /
 +------------------------> Input Size (n)
```

Both machines produce a graph with the **same shape**. Faster hardware only changes the constant factor (the slope), not the order of growth.

- Time complexity is about the **algorithm**, not the **hardware** it runs on.
- It lets us compare algorithms independent of machine speed, language, or implementation details.

---

## Big-O Notation

Big-O notation describes an **asymptotic upper bound** on an algorithm's growth rate. It is commonly used to express worst-case time complexity. It describes how the running time grows for sufficiently large input sizes.

- `O(1)` → Constant
- `O(log n)` → Logarithmic
- `O(n)` → Linear
- `O(n log n)` → Linearithmic
- `O(n²)` → Quadratic
- `O(2ⁿ)` → Exponential
- `O(n!)` → Factorial

---

## Common Complexities

| Complexity | Example |
|---|---|
| O(1) | Array indexing |
| O(log n) | Binary Search |
| O(n) | Linear Search |
| O(n log n) | Merge Sort |
| O(n²) | Bubble Sort |
| O(2ⁿ) | Naive Fibonacci |
| O(n!) | Permutations |

**Order from best to worst:**

```
O(1)  <  O(log n)  <  O(n)  <  O(n log n)  <  O(n²)  <  O(2ⁿ)  <  O(n!)

Best ────────────────────────────────────────────────────────► Worst
```

---

## Worked Example — Linear Search vs Binary Search

```
Time
 ^                                    O(N)  ← linear search
 |                                  /
 |                                /      O(logN) ← binary search
 |                              /      _______________
 |                            /   ____/
 |                          / ___/
 |                        /_/
 +---------------------------------------------------------> Size
              Smaller size              Large size
```

**Key observations:**

1. **For smaller input sizes**, the time taken by linear search is *less* than the time taken by binary search.
2. **For larger input sizes**, the time taken by linear search is *more* than binary search — and it keeps increasing relative to binary search as size grows.
3. Time complexity focuses on how an algorithm scales as the input size (`n`) becomes large. Worst-case analysis is commonly used because it provides an upper bound on the running time.
4. Thus, **Binary Search has a better asymptotic time complexity (`O(log n)`) than Linear Search (`O(n)`)**, so it scales much better as the input size grows.

---

## Cases of Time Complexity

- **Best Case** — the minimum time an algorithm takes (most favorable input).
- **Average Case** — the expected time over all possible inputs.
- **Worst Case** — the maximum time an algorithm takes (least favorable input). This is what Big-O typically describes.

**Example — Linear Search:**

| Case | Complexity |
|---|---|
| Best | O(1) — target is the first element |
| Average | O(n) |
| Worst | O(n) — target is the last element or absent |

---

## Ignoring Constants

When analyzing time complexity, constant factors are ignored — what matters is how the runtime scales, not the exact coefficient.

```
5n + 2        → O(n)
1000n         → O(n)
3n² + 10n + 7 → O(n²)
```

Another way to see it:

```
y = 2x
y = x       }  All of these have linear growth → O(n)
y = x/2
```

The actual time is different for each line, but in all cases the time is **growing linearly** as the input grows — so all of them are `O(N)`, regardless of the constant multiplier.

This is one of the most commonly asked interview concepts.

---

## Ignoring Lower-Order Terms

For large `n`, lower-order terms become insignificant compared to the fastest-growing term, so they're dropped as well:

```
n² + n        → O(n²)     (the "+ n" barely matters as n grows)
n³ + n² + n   → O(n³)
```

**Worked example:**
```
O(N³ + log(N))

for N = 1×10⁶ = 1 million:

O((10⁶)³ + log(10⁶))
= O((10⁶)³ + 6)
```
Here, `6` is very small compared to `(1 million)³` — that's why it's ignored:
```
~ O((10⁶)³)
~ O(N³)
```

Only the **dominant term** is kept when expressing complexity in Big-O.

---

## Formal Justification — The Limit Definition of Big-O

The intuitive rules above ("ignore constants," "ignore lower-order terms") can be proven formally using limits.

**Definition:**
```
f(n) = O(g(n))   if and only if   lim (n→∞) f(n)/g(n) < ∞
```

**Worked example:** Show that `6n³ + 3n + 5 = O(n³)`.

```
lim (n→∞)  (6n³ + 3n + 5) / n³
```

Divide every term by `n³`:
```
= lim (n→∞)  6 + 3/n² + 5/n³
```

As `n → ∞`:
```
= 6 + 3/∞ + 5/∞
= 6 + 0 + 0
= 6
```

Since the limit evaluates to a **finite constant** (`6 < ∞`), by definition `6n³ + 3n + 5 = O(n³)`.

This is the formal reason **why we ignore constants and lower-order terms**: as `n → ∞`, the ratio between the full expression and its dominant term settles on a finite constant — the lower-order terms and coefficients don't change the *growth rate*, only the exact value.

### One function, multiple valid Big-O bounds

Since `6n³ + 3n + 5` grows asymptotically like `n³`, it is `O(n³)`. It is also technically `O(n⁴)`, `O(n⁵)`, and so on — but `O(n³)` is the **tightest** commonly used upper bound. An algorithm doesn't have only one valid Big-O; Big-O just describes *an* upper bound, and the tightest one is the most useful.

### Big-O, Big-Omega, and Big-Theta

Once limits are involved, it's worth distinguishing three related notations:

| Notation | Meaning |
|---|---|
| `O(n³)` | Upper bound — grows *at most* this fast |
| `Ω(n³)` | Lower bound — grows *at least* this fast |
| `Θ(n³)` | Tight bound — grows *exactly* this fast (both upper and lower) |

For `6n³ + 3n + 5`, the most precise classification is `Θ(n³)`, since it grows neither faster nor slower than `n³` asymptotically.

---

## Comparing Algorithms

For sufficiently large inputs, an algorithm with a lower asymptotic complexity (such as O(log n)) will outperform one with a higher complexity (such as O(n)), assuming comparable implementations and hardware. Constant factors can still matter for small inputs, which is why "better complexity" doesn't always mean "faster in every situation" — but it always wins as input size grows large enough.

---

## Summary — Key Points When Dealing With Time Complexity

1. Big-O analysis usually focuses on the **worst-case running time**.
2. Consider how the algorithm behaves as the input size (`n`) becomes very large — analyze the growth rate as `n → ∞`.
3. Don't care about the actual time — **ignore constants**.
4. **Ignore lower-order terms** — keep only the dominant term.