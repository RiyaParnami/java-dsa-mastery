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

## Using Limits to Justify Big-O

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

### The Full Family of Asymptotic Notations

Once limits are involved, it's worth distinguishing all five related notations — not just Big-O.

#### Big-O — `O(g)` (upper bound)

- **Words:** `f` grows *no faster than* `g` (already covered above).
- **Math:**
```
f = O(g)   ⟺   lim (n→∞) f(n)/g(n) < ∞
```
- **Relation:** `f ≤ g` (asymptotically)

#### Big-Omega — `Ω(g)` (lower bound)

The **opposite** of Big-O.

- **Words:** `f` grows *at least as fast as* `g` — a lower bound.
- **Math:**
```
f = Ω(g)   ⟺   lim (n→∞) f(n)/g(n) > 0
```
- **Relation:** `f ≥ g` (asymptotically)

#### Big-Theta — `Θ(g)` (tight bound, combining both)

- **Words:** Both the upper bound and lower bound are the same — e.g. if an algorithm has `O(N²)` **and** `Ω(N²)`, then it is `Θ(N²)`.
- **Math:**
```
f = Θ(g)   ⟺   0 < lim (n→∞) f(n)/g(n) < ∞
```
- **Worked example:** If an algorithm has an upper bound of `O(N²)` and a lower bound of `Ω(N²)`, both bounds are `N²`, so the algorithm is `Θ(N²)`.

#### Little-o — `o(g)` (strict/loose upper bound)

This is also giving an upper bound — but it's a **stronger statement** than Big-O.

- **Big-O:** `f = O(g)` means `f ≤ g` (f could grow at the *same* rate as g, or slower).
- **Little-o:** `f = o(g)` means `f < g` **strictly** — `f` grows **strictly slower** than `g`, never at the same rate.
- **Math:**
```
f = o(g)   ⟺   lim (n→∞) f(n)/g(n) = 0
```
- **Worked example:** Let `f = N²` and `g = N³`.
```
lim (n→∞)  N²/N³  =  lim (n→∞)  1/N  =  0
```
Since the limit is `0`, `f = o(g)` — i.e. `N²` is strictly slower-growing than `N³`.

#### Little-omega — `ω(g)` (strict/loose lower bound)

The opposite of little-o.

- **Big-Ω:** `f = Ω(g)` means `f ≥ g` (f could grow at the same rate as g, or faster).
- **Little-ω:** `f = ω(g)` means `f > g` **strictly** — `f` grows **strictly faster** than `g`, never at the same rate.
- **Math:**
```
f = ω(g)   ⟺   lim (n→∞) f(n)/g(n) = ∞
```
- **Worked example:**
```
lim (n→∞)  N³/N²  =  lim (n→∞)  N  =  ∞
```
Since the limit is `∞`, `f = ω(g)` — i.e. `N³` grows strictly faster than `N²`.

### Summary Table

| Notation | Meaning | Relation | Limit condition |
|---|---|---|---|
| `O(g)` | Upper bound (grows no faster than) | `f ≤ g` | `lim < ∞` |
| `Ω(g)` | Lower bound (grows no slower than) | `f ≥ g` | `lim > 0` |
| `Θ(g)` | Tight bound (grows exactly as fast) | `f = g` | `0 < lim < ∞` |
| `o(g)` | Strict upper bound (grows strictly slower) | `f < g` | `lim = 0` |
| `ω(g)` | Strict lower bound (grows strictly faster) | `f > g` | `lim = ∞` |

For `6n³ + 3n + 5`, the most precise classification is `Θ(n³)`, since it grows neither faster nor slower than `n³` asymptotically.

---

## Comparing Algorithms

For sufficiently large inputs, an algorithm with a lower asymptotic complexity (such as O(log n)) will outperform one with a higher complexity (such as O(n)), assuming comparable implementations and hardware. Constant factors can still matter for small inputs, which is why "better complexity" doesn't always mean "faster in every situation" — but it always wins as input size grows large enough.

---

## Recurrence Relations for Complexity Analysis

Recursive algorithms don't have a simple loop to count — their running time is naturally expressed as a **recurrence relation**: the time for input size `n` in terms of the time for smaller inputs.

```
T(n) = T(n/2) + O(1)        // e.g. Binary Search
T(n) = 2T(n/2) + O(n)       // e.g. Merge Sort
```

- The recursive term(s) (`T(n/2)`, `2T(n/2)`, etc.) represent the work done by the **recursive sub-calls**.
- The extra term (`O(1)`, `O(n)`, etc.) represents the work done **at the current level**, outside of the recursive calls (comparisons, merging, splitting, etc.).

**General rule:** `T(n) = (what you get back from the recursive calls) + (what you do yourself at this level, and how long that takes)`.

To get an actual time complexity like `O(log n)` or `O(n log n)` out of a recurrence, it needs to be **solved** — a recurrence relation on its own is just a description of the algorithm's structure, not yet a closed-form complexity.

### General Form of a Divide & Conquer Recurrence

Any divide-and-conquer recurrence can be written in this general form:

```
T(n) = a₁T(b₁n + ε₁(n)) + a₂T(b₂n + ε₂(n)) + ... + aₖT(bₖn + εₖ(n)) + g(n),   for n ≥ n₀
```

This general form is known as an **Akra–Bazzi recurrence** (Akra & Bazzi, 1996).

- `aᵢ` — how many sub-calls of that "branch" are made
- `bᵢ` — the fraction of `n` passed into that sub-call
- `εᵢ(n)` — a small perturbation term (often `0`, included for full generality)
- `g(n)` — the extra work done *at the current level*, outside of the recursive calls

**Matching a familiar recurrence to this form** — for `T(N) = T(N/2) + C`:
```
a₁ = 1
b₁ = 1/2
ε₁(n) = 0
g(n) = C     (constant)
```

**Example — deriving a recurrence from splitting an array in half:** if a problem of size `N` is split into two halves of size `N/2` each, and merging them back together takes `(N-1)` time:
```
T(N) = T(N/2) + T(N/2) + (N-1)
     = 2·T(N/2) + (N-1)      // recurrence relation
```

---

## How to Actually Solve a Recurrence to Get Complexity

Once you have a recurrence relation like `T(N) = T(N/2) + C`, there are three common ways to solve it:

1. **Plug & Chug (substitution method)** — repeatedly substitute the recurrence into itself and look for the pattern:
```
T(N) = T(N/2) + C
```
2. **Master's Theorem** — a direct formula for solving recurrences of the form `T(n) = a·T(n/b) + f(n)`, without manual expansion.
3. **Akra–Bazzi (1996)** — a more general method that solves the full general form shown above, covering cases the Master Theorem can't (e.g. unequal-sized sub-problems).

---

## Akra–Bazzi Theorem — Formal Statement

For a recurrence of the general form shown earlier, the Akra–Bazzi theorem states:

```
T(x) = Θ( x^p  +  x^p · ∫[1 to x]  g(u)/u^(p+1)  du )
```

### What is `p`?

`p` is the **unique real number** that satisfies:
```
a₁b₁^p + a₂b₂^p + ... + aₖbₖ^p = 1

i.e.   Σ(i=1 to k)  aᵢbᵢ^p = 1
```

Once `p` is found, plug it into the formula above along with `g(u)` and evaluate the integral to get the final time complexity.

---

## Worked Example — Solving Merge Sort's Recurrence with Akra–Bazzi

**Recurrence:**
```
T(N) = 2T(N/2) + (N-1)
```

### Step 1 — Identify the coefficients

```
a₁ = 2
b₁ = 1/2
g(n) = n - 1
```

### Step 2 — Solve for `p`

```
a₁ · b₁^p = 1
2 · (1/2)^p = 1
```
Solving this gives:
```
p = 1
```
(check: `2 · (1/2)¹ = 2 · 1/2 = 1` ✓)

### Step 3 — Plug `p` into the Akra–Bazzi formula

```
T(x) = Θ( x¹ + x¹ · ∫[1 to x]  (u-1)/u^(1+1)  du )
```

### Step 4 — Simplify the integrand

```
(u-1)/u²  =  1/u - 1/u²
```

So:
```
T(x) = Θ( x + x · ∫[1 to x] (1/u - 1/u²) du )
     = Θ( x + x · [ ∫[1 to x] du/u  -  ∫[1 to x] du/u² ] )
```

### Step 5 — Evaluate the integrals

```
∫ du/u   = log u
∫ du/u²  = -1/u     (since u⁻¹ / -1 = -1/u)
```

So the bracket becomes:
```
[ log u + 1/u ]  evaluated from 1 to x
```

### Step 6 — Substitute the limits

```
= [ log x + 1/x ]  -  [ log 1 + 1/1 ]
= log x + 1/x - 1        (since log 1 = 0)
```

### Step 7 — Substitute back into `T(x)`

```
T(x) = Θ( x + x·(log x + 1/x - 1) )
     = Θ( x + x·log x + 1 - x )
     = Θ( x·log x + 1 )
```

### Step 8 — Drop the lower-order term

`1` is negligible compared to `x·log x`, so:
```
T(x) = Θ(x · log x)     // Time Complexity
```

**Conclusion:** For an array of size `N`, **Merge Sort's time complexity is `Θ(N log N)`** — confirmed via the Akra–Bazzi theorem, matching what's already known from the Master Theorem.

---

## Shortcut — When You Don't Need the Integral

**When `p` is less than the power (exponent) of the dominant term in `g(n)`, the integral can be skipped entirely — the answer is simply `O(g(n))`.**

**Example:** if `g(n) = n²` and you solve for `p` and find `p < 2` (i.e. `p` is less than the power of `g(n)`):
```
Hence, ans = O(g(n))
```

This shortcut only applies when `p` is strictly smaller than `g(n)`'s exponent — otherwise you still need to evaluate the integral (as in the Merge Sort example above, where `p = 1` matched `g(n) = n - 1`'s degree).

### Why the shortcut works (formal derivation)

Starting from the Akra–Bazzi formula with `g(n) = n²`:
```
T(x) = Θ( x^p + x^p · ∫₁ˣ u²/u^(p+1) du )
```
Simplify the integrand:
```
= Θ( x^p + x^p · ∫₁ˣ u^(1-p) du )
```
Evaluating the integral (roughly `x^(2-p)`) and multiplying back by `x^p`:
```
= Θ( x^p + x² )
```
Now compare the two terms, `x^p` and `x²`. If `p < 2`, then `x^p` is the **less-dominating term**, so it gets ignored — leaving just:
```
Ans = O(x²) = O(g(n))
```
This is exactly why the shortcut holds: whenever `p` is smaller than `g(n)`'s exponent, the `x^p` term from the recursive branching is dominated by `g(n)` itself, so `g(n)` alone determines the final complexity.

---

## Worked Example — Two-Term Divide & Conquer Recurrence

```
T(N) = 2·T(N/2) + (8/9)·T(3N/4) + N²
```

### Step 1 — Identify the coefficients

```
a₁ = 2,   b₁ = 1/2
a₂ = 8/9, b₂ = 3/4
g(n) = n²
```

### Step 2 — Solve for `p`

```
a₁·b₁^p + a₂·b₂^p = 1
2·(1/2)^p + (8/9)·(3/4)^p = 1
```
Try `p = 2`:
```
2·(1/4) + (8/9)·(9/16) = 1
1/2 + 1/2 = 1   ✓
```
So **`p = 2`**.

### Step 3 — Apply the formula

Here `p` (`= 2`) is **equal to** the power of `g(n) = n²`, so the shortcut above doesn't apply — the integral still needs to be evaluated:
```
T(n) = Θ( n² + n²·∫₁ⁿ u²/u³ du )
     = Θ( n² + n²·ln n )
     = Θ( n²·ln n )
```

---

## Worked Example — Finding `p` by Trial and Error

Not every recurrence gives a clean integer `p` on the first guess. Sometimes you have to search for it.

```
T(n) = 3·T(n/3) + 4·T(n/4) + n²
```

**Try `p = 1`:**
```
3·(1/3)¹ + 4·(1/4)¹ = 1 + 1 = 2
```
`2 > 1` → the sum is too large. Since each `bᵢ < 1`, raising `bᵢ` to a **higher power** makes `bᵢ^p` **smaller** — so increasing `p` shrinks the sum back down.

∴ `p > 1`

**Try `p = 2`:**
```
3·(1/3)² + 4·(1/4)² = 1/3 + 1/4 = 7/12
```
`7/12 < 1` → now the sum is too small.

**Conclusion:** the correct `p` lies **between 1 and 2** — it isn't always a clean integer, and pinning it down exactly may require further trial and error or a numerical method.

---

## Solving Linear (Homogeneous) Recurrences — Characteristic Equation Method

Akra–Bazzi solves **divide & conquer** recurrences (where `n` gets divided). A different technique is needed for **linear (homogeneous)** recurrences, where `n` just gets *decremented* — like Fibonacci.

**Example:**
```
F(N) = F(N-1) + F(N-2)
```

### General Form

```
f(n) = a₁·f(n-1) + a₂·f(n-2) + a₃·f(n-3) + ... + aₙ·f(n-n)

f(n) = Σ(i=1 to n)  aᵢ·f(n-i),   where aᵢ are fixed and n is the order of the recurrence
```

### Steps to Solve — Worked Example (Fibonacci)

**Recurrence:**
```
f(n) = f(n-1) + f(n-2)    ... (1)
```

**Step 1 — Assume a solution of the form `f(n) = αⁿ`**, for some constant `α`.

Substituting into (1):
```
αⁿ = αⁿ⁻¹ + αⁿ⁻²
```

**Step 2 — Divide through by `αⁿ⁻²`** to eliminate the exponent:
```
αⁿ/αⁿ⁻²  =  αⁿ⁻¹/αⁿ⁻²  +  αⁿ⁻²/αⁿ⁻²
α²        =  α            +  1
```

This gives the **characteristic equation** of the recurrence:
```
α² - α - 1 = 0
```

**Step 3 — Solve the characteristic equation** using the quadratic formula:
```
α = ( -b ± √(b² - 4ac) ) / 2a
```
For `α² - α - 1 = 0` (so `a=1, b=-1, c=-1`):
```
α = ( 1 ± √5 ) / 2
```

This gives two roots:
```
α₁ = (1 + √5) / 2     ≈ 1.618   (the golden ratio, φ)
α₂ = (1 - √5) / 2     ≈ -0.618
```

**Note:** `α₁ = (1+√5)/2` is the famous **golden ratio**, and it's no coincidence — Fibonacci growth is fundamentally tied to it. This confirms the well-known result that Fibonacci numbers grow as `Θ(φⁿ)`.

### Step 4 — General Solution

When a characteristic equation has **2 roots** (`α₁`, `α₂`), the general solution to the recurrence is a linear combination of both:
```
f(n) = c₁·α₁ⁿ + c₂·α₂ⁿ            ... (2)
```
This satisfies the recurrence: `f(n) = f(n-1) + f(n-2)`.

### Step 5 — Solve for the Constants Using Initial Conditions

**Fact:** the number of roots equals the number of "answers" (initial conditions) you need to already know.

Here we have **2 roots** (`α₁`, `α₂`), so we need **2 known values** of the sequence. For Fibonacci:
```
f(0) = 0   and   f(1) = 1
```

**Using `f(0) = 0`:**
```
f(0) = 0 = c₁ + c₂   ⟹   c₁ = -c₂     ... (5)
```

**Using `f(1) = 1`:**
```
f(1) = 1 = c₁·( (1+√5)/2 ) + c₂·( (1-√5)/2 )
```
Substituting `c₂ = -c₁` from (5):
```
1 = c₁·( (1+√5)/2 ) - c₁·( (1-√5)/2 )
```
Solving:
```
c₁ = 1/√5
c₂ = -1/√5
```

### Step 6 — Final Closed-Form Formula

Substituting `c₁` and `c₂` back into (2):
```
f(n) = (1/√5)·( (1+√5)/2 )ⁿ  -  (1/√5)·( (1-√5)/2 )ⁿ
```

**This is the closed-form formula for the nth Fibonacci number** — known as **Binet's Formula**.

### Step 7 — Deriving the Time Complexity from the Closed Form

**Note on what this represents:** `f(n)` here is the *value* of the nth Fibonacci number, not the running time of an algorithm. But since `f(n)` itself grows as `Θ(φⁿ)`, and the naive recursive algorithm does roughly one unit of work per unit `f(n)` grows by, the *value's* growth rate and the *naive algorithm's* time complexity turn out to share the same asymptotic form — which is why the same closed-form expression is reused below to describe the algorithm's running time.

```
f(n) = (1/√5)·[ ( (1+√5)/2 )ⁿ  -  ( (1-√5)/2 )ⁿ ]
```

As `n → ∞`, the second term — `( (1-√5)/2 )ⁿ` — shrinks toward `0`, since `(1-√5)/2 ≈ -0.618` and any fraction with magnitude `< 1` raised to a growing power vanishes. Hence, this is the **less-dominating term**, and it's ignored.

That leaves:
```
Time Complexity = O( ( (1+√5)/2 )ⁿ )
```

Since `(1+√5)/2` is the **golden ratio (`φ ≈ 1.618`)**:
```
T(N) = O(1.6180ⁿ)
```

So the naive recursive Fibonacci algorithm runs in **exponential time**, with a base equal to the golden ratio — a tighter bound than the commonly cited (and looser) `O(2ⁿ)`.

---

## Special Case — Repeated (Equal) Roots

The Fibonacci example above had **two distinct roots**. But what happens if the characteristic equation gives the **same root twice**?

### Worked Example

```
f(n) = 2·f(n-1) - f(n-2)
```

**Step 1 — Substitute `f(n) = αⁿ`:**
```
αⁿ = 2·αⁿ⁻¹ - αⁿ⁻²
```

**Step 2 — Rearrange and divide through by `αⁿ⁻²`:**
```
αⁿ - 2·αⁿ⁻¹ - αⁿ⁻² = 0
─────────────────────────    (dividing both sides by αⁿ⁻²)
        αⁿ⁻²

⟹  α² - 2α + 1 = 0
```

**Step 3 — Solve the characteristic equation:**
```
α² - 2α + 1 = 0
(α - 1)² = 0
⟹  α = 1     (a double root)
```

### Why the General Solution Changes

When a root is **repeated**, using `c₁αⁿ + c₂αⁿ` doesn't actually give two independent solutions — they'd collapse into one, since both terms are just multiples of the same `αⁿ`. To get a valid general solution, the second term needs an extra factor of `n`:

```
The two independent solutions become:  αⁿ  and  n·αⁿ
```

So the general solution is:
```
f(n) = c₁·αⁿ + c₂·n·αⁿ
```

**General rule:** if a root `α` is repeated `r` times, then:
```
αⁿ, n·αⁿ, n²·αⁿ, ..., n^(r-1)·αⁿ
```
are **all** independent solutions to the recurrence.

### Applying Initial Conditions

Since `α = 1` here, the general solution simplifies:
```
f(n) = c₁·(1)ⁿ + c₂·n·(1)ⁿ
     = c₁ + c₂·n
```

Using `f(0) = 0` and `f(1) = 1`:
```
f(0) = 0 = c₁                       ⟹  c₁ = 0
f(1) = 1 = c₁ + c₂                  ⟹  c₂ = 1
```

**Closed form:**
```
f(n) = n
```

**Time Complexity:**
```
O(n)
```

Unlike the Fibonacci case (which grew exponentially, `O(φⁿ)`), this repeated-root recurrence grows only **linearly**, `O(n)` — a good reminder that the *shape* of the characteristic roots (distinct vs. repeated, and their magnitude) is what determines the growth rate, not just the recurrence "looking" similar to Fibonacci.

---

## Non-Homogeneous Linear Recurrences

**Form:**
```
f(n) = a₁f(n-1) + a₂f(n-2) + ... + a_d·f(n-d) + g(n)
```
The extra `g(n)` term makes it **non-homogeneous**.

### How to Solve

1. **Homogeneous part** — replace `g(n)` with `0`, solve as usual to get `f(n) = c₁·αⁿ`.
2. **Particular part** — guess a solution matching the shape of `g(n)`:
   - `g(n)` exponential (e.g. `cⁿ`) → guess `f(n) = a·cⁿ`
   - `g(n)` polynomial of degree `d` → guess a polynomial of the same degree
   - `g(n)` = exponential + polynomial → guess a combination of both
   - **If a guess fails** (matches the homogeneous root — makes both sides cancel out to something wrong), multiply the guess by `n`. If that also fails, increase the degree again (`n²`, etc).
3. **General solution** = homogeneous + particular.
4. Use initial conditions to solve for the constants.

### Worked Example 1

```
f(n) = 2f(n-1) + 2ⁿ,   f(0) = 1
```
- Homogeneous: `α = 2` → `f(n) = c₁·2ⁿ`
- Guess `f(n) = a·2ⁿ` for particular → fails (`2` is already the homogeneous root)
- Retry `f(n) = (an+b)·2ⁿ` → gives `a = 1` (b discarded)
- Particular solution: `f(n) = n·2ⁿ`
- General: `f(n) = c₁·2ⁿ + n·2ⁿ`, and `f(0)=1 ⟹ c₁=1`

**Answer:** `f(n) = 2ⁿ + n·2ⁿ`   →   **Complexity: `O(n·2ⁿ)`**

### Worked Example 2

```
f(n) = 4f(n-1) + 3ⁿ,   f(1) = 1
```
- Homogeneous: `α = 4` → `f(n) = c₁·4ⁿ`
- Guess `f(n) = c·3ⁿ` for particular → solving gives `c = -3`, so particular `f(n) = -3ⁿ⁺¹`
- General: `f(n) = c₁·4ⁿ - 3ⁿ⁺¹`, and `f(1)=1 ⟹ c₁ = 5/2`

**Answer:** `f(n) = (5/2)·4ⁿ - 3ⁿ⁺¹`

---

## Space Complexity vs Auxiliary Space

- **Auxiliary Space** is the extra or temporary space used by an algorithm (beyond the input itself).
- **Space Complexity** of an algorithm is the *total* space taken by the algorithm with respect to the input size — it includes **both** the auxiliary space **and** the space used by the input.

**Example — comparing sorting algorithms by space:**

If we want to compare standard sorting algorithms on the basis of space, **Auxiliary Space is a better criterion than Space Complexity**:

- Merge Sort uses `O(n)` auxiliary space.
- Insertion Sort and Heap Sort use `O(1)` auxiliary space.
- But the **space complexity** of all three is `O(n)` (since they all still need to store the input array itself).

If we only compared space complexity, all three would look identical (`O(n)`) — but auxiliary space reveals the real difference: Insertion Sort and Heap Sort need no extra space beyond the input, while Merge Sort needs an additional `O(n)` for merging.

---

## Worked Example — Nested Loop with a Step Increment

```java
for (i = 1; i <= N; i = i + k) {
    for (j = 1; j <= k; j++) {
        // some operation that takes time t
    }
}
```

### Step 1 — Inner loop

The inner loop always runs exactly `k` times, and each iteration does an operation taking time `t`:
```
Inner loop = O(k·t) time
```

### Step 2 — How many times does the outer loop run?

The outer loop doesn't increment by 1 — it increments by `k` each time:
```
i = 1, 1+k, 1+2k, 1+3k, 1+4k, ..., 1+xk
```

The loop keeps running as long as `1 + xk ≤ N`:
```
1 + xk ≤ N
xk ≤ N - 1
x ≤ (N-1)/k
```

So the outer loop executes **approximately `N/k` times** — more precisely, `⌊(N-1)/k⌋ + 1` times, but since this is Big-O analysis the exact count doesn't matter:
```
Number of outer loop iterations = Θ(N/k)
```

### Step 3 — Combine

Total work = (cost of inner loop) × (number of times outer loop runs):
```
O(k·t) × N/k
= O(k·t·N / k)
```

**Answer:** Since `k` is treated as a constant, it is ignored in Big-O notation, leaving `O(N·t)`.

---

## Sorting Algorithms — Complexity Comparison

### Bubble Sort

Repeatedly steps through the array, compares adjacent elements, and swaps them if they're in the wrong order — the largest element "bubbles up" to its correct position each pass.

```
Step 1
[4, 9, 5, 1, 0]
   ↑  ↑  no swap

Iteration 1: [4, 9, 5, 1, 0] → swap 9,5 → [4, 5, 9, 1, 0]
Iteration 2: [4, 5, 9, 1, 0] → swap 9,1 → [4, 5, 1, 9, 0]
Iteration 3: [4, 5, 1, 9, 0] → swap 9,0 → [4, 5, 1, 0, 9]
Iteration 4: [4, 5, 1, 0, 9]  (sorted)
```

| Property | Value |
|---|---|
| Worst & Average Time | `O(n²)` — occurs when array is reverse sorted |
| Best Time | `O(n)` — occurs when array is already sorted |
| Auxiliary Space | `O(1)` |
| Sorting In Place | Yes |
| Stable | Yes |

**Boundary case:** Bubble sort takes minimum time (`O(n)`) when elements are already sorted.

---

### Selection Sort

Repeatedly selects the minimum (or maximum) element from the unsorted portion and swaps it into its correct position.

| Property | Value |
|---|---|
| Worst Complexity | `n²` |
| Average Complexity | `n²` |
| Best Complexity | `n²` |
| Space Complexity | `O(1)` |
| Stable | No |

**Note:** Unlike Bubble/Insertion Sort, Selection Sort has **no best-case improvement** — even a sorted array still takes `O(n²)`, because it always scans the remaining unsorted portion to find the minimum, regardless of the array's current order.

The good thing about selection sort is it **never makes more than `O(n)` swaps**, which can be useful when memory writes are a costly operation (e.g. flash memory).

---

### Insertion Sort

Builds the sorted array one element at a time — takes each element and inserts it into its correct position among the already-sorted elements to its left.

```
Insertion Sort Execution Example
[4, 3, 2, 10, 12, 1, 5, 6]
 ↓ insert 3 before 4
[3, 4, 2, 10, 12, 1, 5, 6]
 ↓ insert 2 before 3
[2, 3, 4, 10, 12, 1, 5, 6]
 ↓ 10 already in place
[2, 3, 4, 10, 12, 1, 5, 6]
 ↓ 12 already in place
[2, 3, 4, 10, 12, 1, 5, 6]
 ↓ insert 1 at the front
[1, 2, 3, 4, 10, 12, 5, 6]
 ↓ insert 5 before 10
[1, 2, 3, 4, 5, 10, 12, 6]
 ↓ insert 6 before 10
[1, 2, 3, 4, 5, 6, 10, 12]
```

| Property | Value |
|---|---|
| Time Complexity | `O(n²)` |
| Auxiliary Space | `O(1)` |
| Sorting In Place | Yes |

**Boundary cases:** Insertion sort takes maximum time to sort if elements are sorted in **reverse order**, and minimum time (`O(n)`) when elements are **already sorted**.

---

### Quick Comparison

| Algorithm | Best | Average | Worst | Space | Stable |
|---|---|---|---|---|---|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | No |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |

---

## Summary — Key Points When Dealing With Time Complexity
1. Big-O analysis usually focuses on the **worst-case running time**.
2. Consider how the algorithm behaves as the input size (`n`) becomes very large — analyze the growth rate as `n → ∞`.
3. Don't care about the actual time — **ignore constants**.
4. **Ignore lower-order terms** — keep only the dominant term.