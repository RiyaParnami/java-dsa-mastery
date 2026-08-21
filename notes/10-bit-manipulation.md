# Number Systems & Bit Manipulation

## Number Systems

| System | Digits Used | Base |
|---|---|---|
| Decimal | 0–9 | 10 |
| Binary | 0, 1 | 2 |
| Octal | 0–7 | 8 |
| Hexadecimal | 0–9, A–F (A=10, B=11, C=12, D=13, E=14, F=15) | 16 |

---

## Converting Any Base → Decimal

**Steps:** Multiply each digit by the corresponding power of the base, and add them up.

**Example — Binary to Decimal:**
```
(11)₂ = 1×2¹ + 1×2⁰ = 2 + 1 = (3)₁₀
```

**Example — Octal to Decimal:**
```
(21)₈ = 2×8¹ + 1×8⁰ = 16 + 1 = (17)₁₀
```

**Example — Hexadecimal to Decimal:**
```
(12)₁₀ = (C)₁₆      // since C = 12 in hex digits
```

---

## Converting Decimal → Any Base

**Steps:** Repeatedly divide by the target base, collect the remainders, then read them **bottom-up** (last remainder first).

**Example — Decimal to Octal:**
```
17 ÷ 8 = 2  remainder 1
 2 ÷ 8 = 0  remainder 2

Read remainders bottom-up: (21)₈
```
Verify: `2×8 + 1 = 17` ✓

**Example — Decimal to Binary:**
```
17 ÷ 2 = 8  remainder 1
 8 ÷ 2 = 4  remainder 0
 4 ÷ 2 = 2  remainder 0
 2 ÷ 2 = 1  remainder 0
 1 ÷ 2 = 0  remainder 1

Read remainders bottom-up: (10001)₂
```

---

## Converting Directly Between Binary, Octal & Hex

Since `8 = 2³` and `16 = 2⁴`, octal and hex digits map **directly** to fixed-length binary groups — no need to go through decimal.

- **Octal digit → 3 binary bits** (each octal digit is 0–7, which fits exactly in 3 bits)
- **Hex digit → 4 binary bits** (each hex digit is 0–15, which fits exactly in 4 bits)

**Example — Octal to Binary:**
```
(21)₈ → 2 = 010,  1 = 001
      → (010 001)₂ = (10001)₂
```

---

## Complement (`~`)

Flips every bit — `1 → 0` and `0 → 1`.

```
a  = 10110
~a = 01001
```

---

## Negative Numbers in Binary — Two's Complement

`1 byte = 8 bits`. In signed binary representation:

```
  MSB                            LSB
[ 0 | 0 | 0 | 0 | 1 | 0 | 1 | 0 ]
  ↑                            ↑
tells sign                  value of the number
```

- **MSB (Most Significant Bit)** — tells us if the number is positive or negative:
  ```
  1 → negative
  0 → positive
  ```
- The remaining bits represent the magnitude.

### Steps to Find the Negative of a Number (2's Complement Method)

1. Take the **complement** of the number (flip every bit).
2. **Add 1** to the result.

### Worked Example — Find `-10`

```
(10)₁₀ = (00001010)₂
```
**Step 1 — Complement:**
```
00001010 → 11110101
```
**Step 2 — Add 1:**
```
  11110101
+        1
----------
  11110110
```
**Answer:** `(-10)₁₀ = (11110110)₂`

### Why Does This Work?

This relies on a binary identity: a `1` followed by `n` zeros always equals `n` ones plus `1`:
```
100000000  =  11111111 + 1        (i.e. 2⁸ = (2⁸ - 1) + 1)
```
So, working modulo `2⁸` (for a byte):
```
-N = 100000000 - N
   = (11111111 - N) + 1
   = complement(N) + 1
   = ~N + 1
```
This is exactly the 2's complement recipe: **flip the bits, then add 1** — because subtracting `N` from all-1s is the same as flipping every bit of `N`.

---

## Range of Numbers Representable with `n` Bits

**General Range Formula (for signed numbers, `n` bits):**
```
-2^(n-1)   to   2^(n-1) - 1
```

**Why?** One bit (the MSB) is reserved for the **sign**, so only `n-1` bits are left to represent the actual magnitude. That gives `2^(n-1)` distinct magnitudes, split across the negative and non-negative sides.

### Worked Example — 1 Byte (8 bits)

```
[ sign | 7 bits for the actual number ]
```
- Total combinations across all 8 bits: `2×2×2×...` (8 times) `= 2⁸ = 256`
- 1 bit is used for the **sign**, leaving **7 bits** for the actual number
- Numbers representable from those 7 bits: `2⁷ = 128`

**Range:** `-128` to `127`

**Note:** `0` is counted on the non-negative side — that's why the range isn't symmetric (`-128` to `127`, not `-127` to `127`): `128` negative values (`-128` to `-1`) + `128` non-negative values (`0` to `127`) = `256` total, matching `2⁸`.

### Verifying `-0 = 0` (via Two's Complement)

```
0 = 00000000
```
**Step 1 — Complement:**
```
00000000 → 11111111
```
**Step 2 — Add 1:**
```
  11111111
+        1
----------
 100000000
```
This needs **9 bits**, but only **8 bits** are available (1 byte) — so the extra 9th bit is **discarded**:
```
100000000 → 00000000     (9th bit discarded)
```
**Result:** `-0 = 0` ✓ — zero maps back to itself under 2's complement, which is exactly why the range comes out asymmetric (`-128 to 127`) rather than symmetric.

---

## Application — Find the Position of the Rightmost Set Bit

**Problem:** Given a number, find the position of its rightmost `1` bit (counting from the right, starting at `1`).

**Example:** `N = 101101100`
```
101101(1)00
   a    b
```
Splitting `N` around its rightmost set bit: `N = a · 1 · b`, where `a` is everything before that bit and `b` is the trailing zeros after it.

**Answer:** position `4` (counting from the right, 1-indexed).

### Formula: `N & (-N)`

**Key insight:** in 2's complement, negating a number **flips every bit before the rightmost set bit, but leaves the rightmost set bit and all trailing zeros unchanged**:
```
 N  =  a  1  b
-N  = ~a  1  b
```
ANDing these together cancels out everything in `a` (since `a` and `~a` never agree), leaving only the rightmost set bit isolated:
```
N & (-N)  =  0...0  1  b
```

**General formula:**
```
Ans = N & (-N)
```
This isolates the rightmost set bit — a handy building block for problems like counting set bits, finding the lowest power of 2 dividing a number, or iterating over set bits one at a time.

---

## Bitwise AND (`&`), OR (`|`), XOR (`^`)

| a | b | a & b | a \| b | a ^ b |
|---|---|---|---|---|
| 0 | 0 | 0 | 0 | 0 |
| 0 | 1 | 0 | 1 | 1 |
| 1 | 0 | 0 | 1 | 1 |
| 1 | 1 | 1 | 1 | 0 |

- **AND (`&`)** — result bit is `1` only if **both** bits are `1`.
- **OR (`|`)** — result bit is `1` if **at least one** bit is `1`.
- **XOR (`^`)** — result bit is `1` **if and only if** the bits are different ("exclusive OR").

### Useful XOR Observations

```
a ^ 1 = ā     (complement of a)
a ^ 0 = a     (no change)
a ^ a = 0     (a number XORed with itself is 0)
```

These identities come up constantly in bit-manipulation problems (e.g. finding the single non-duplicate element in an array using XOR).

---

## Right Shift Operator (`>>`)

Shifts all bits to the right by `n` positions. Bits shifted out from the right are **dropped**.

```
a >> b  =  a / 2^b      (floor division)
```

**Example:** `12 >> 2`
```
12 = 00001100
12 >> 2 = 00000011 = 3        // same as floor(12 / 2²) = floor(12/4) = 3
```

---

## Left Shift Operator (`<<`)

Shifts all bits to the left by `n` positions, filling in `0`s from the right.

```
a << b  =  a × 2^b
```

**Example:** `3 << 2`
```
3 = 00000011
3 << 2 = 00001100 = 12        // same as 3 × 2² = 3 × 4 = 12
```

**Note:** `>>` and `<<` are essentially fast bitwise equivalents of division and multiplication by powers of 2, respectively.

---

## Application — Check if a Number is Odd or Even

**Key point:** every number is stored in binary internally — so parity (odd/even) can be determined directly from its bits, without using `%`.

### Building the Intuition

```
  1100
+ 0111
------
 10011   → (19)₁₀
```
Verify: `10011 = 16 + 2 + 1 = 19` ✓

Any binary number is really just a sum of powers of 2:
```
(10011)₂ = 1×2⁴ + 0×2³ + 0×2² + 1×2¹ + 1×2⁰
          = 16   +  0   +  0   +  2   +  1
```

**Note:** every power of 2 *except* `2⁰` is itself even (`2, 4, 8, 16, ...`). So the **only** bit that determines whether the whole number is odd or even is the very last bit — the `2⁰` place.

- If the last bit = `1` → the number is **odd**
- If the last bit = `0` → the number is **even**

This last bit is called the **LSB — Least Significant Bit**.

### The Trick: `n & 1`

ANDing a number with `1` isolates just the LSB, since `1` in binary is `...0001` — every other bit gets zeroed out by the AND:

```
  10010     (n)
& 00001     (1)
--------
  00000     → LSB = 0 → even
```

```
  10011     (n)
& 00001     (1)
--------
  00001     → LSB = 1 → odd
```

**Sum up:** `n & 1 == 1` → odd, else → even.

This is a classic bit-manipulation trick — faster and more direct than `n % 2 == 1`, and it's the foundation for many other bit-level problems (checking/setting/clearing arbitrary bits, counting set bits, etc.). See `OddEven.java` for the implementation.

---

## Application — Find the Unique Number Among Duplicates

**Problem:** Given an array where every number appears twice except one, find the one that doesn't repeat.

**Key properties of XOR that make this work:**
```
a ^ a = 0        (a number XORed with itself cancels out)
a ^ 0 = a
```
XOR is also **commutative and associative** — the order and grouping of the XOR operations doesn't matter, just like multiplication:
```
(5×3) × (5×4) = 5×5×3×4 = 5×4×3×5 = ...
```
regardless of how the terms are grouped or reordered, the product is the same. The same freedom applies to XOR.

**Approach:** XOR all the numbers in the array together. Every duplicate pair cancels out to `0` (in any order, thanks to the property above), leaving only the unique number.

**Example:**
```
arr = [2, 3, 4, 1, 2, 1, 3, 6, 4]

2^3^4^1^2^1^3^6^4 = 6
```
**Answer:** `6` — the only number without a pair.

---

## Application — Find, Set, and Reset the `i`th Bit

These three operations form the building blocks of most bit-manipulation problems. All of them rely on building a **mask**: a number with a single `1` bit at the target position and `0`s everywhere else.

```
mask = 1 << (i-1)      // 1-indexed from the right (LSB = bit 1)
```

### Find the `i`th Bit

**Rule:** AND the number with the mask — if the result is non-zero, that bit is `1`.

```
Ans = n & (1 << (i-1))
```

**Example:** `n = 10110110`, find the **5th bit**.
```
mask =        00010000      (1 << 4)
n & mask = 10110110
         & 00010000
         -----------
           00010000     → non-zero → 5th bit is 1
```

### Set the `i`th Bit (turn it to `1`)

**Rule:** `0 → 1` and `1 → 1` (stays `1`) — this is exactly what **OR** does.

```
Ans = n | (1 << (i-1))
```

**Example:** `n = 1010110`, set the **4th bit**.
```
  1010110
| 0001000     (mask)
----------
  1011110     → set
```

### Reset the `i`th Bit (turn it to `0`)

**Rule:** `1 → 0` and `0 → 0` (stays `0`) — this is what **AND with the complement of the mask** does.

```
Ans = n & ~(1 << (i-1))
```

**Example:** `n = 1010110`, reset the **5th bit**.
```
mask      = 0010000
~mask     = 1101111     (complement)

  1010110
& 1101111
----------
  1000110     → reset
```

---

## Application — Find the `n`th Magic Number

**Problem:** A "magic number" is defined by writing `n` in binary, and for every set bit at position `i` (1-indexed from the LSB), adding `5^i` to the result.

### Pattern

| n | Binary (S³ S² S¹) | Magic No. |
|---|---|---|
| 1 | 001 | 5¹ = **5** |
| 2 | 010 | 5² = **25** |
| 3 | 011 | 5¹ + 5² = 5 + 25 = **30** |
| 4 | 100 | 5³ = **125** |
| 5 | 101 | 5³ + 5¹ = 125 + 5 = **130** |

**Rule:** treat each bit of `n`'s binary representation as a "switch" — if bit `i` is `1`, include `5^i` in the sum; if `0`, skip it.

### Algorithm

Loop through the bits of `n` using the same `& 1` / `>> 1` combo seen earlier:
```
n & 1     →  gives the last digit in binary (the current LSB)
n >> 1    →  shifts right, moving on to the next bit
```
At each step, if the extracted bit is `1`, add `5^i` (where `i` is the current bit position, starting at `1`) to the running total.

### Worked Example — `n = 6`

```
n = 6 = 110₂
```
Processing bit by bit (position `1` = LSB):
```
bit 1 = 0   →  0 × 5¹
bit 2 = 1   →  1 × 5²
bit 3 = 1   →  1 × 5³
```
```
Answer = 0×5¹ + 1×5² + 1×5³
       = 0 + 25 + 125
       = 150
```
**The 6th magic number is `150`.**

---

## Application — Sum of the `n`th Row of Pascal's Triangle

```
Row 1:          1
Row 2:         1 1
Row 3:        1 2 1
Row 4:       1 3 3 1
Row 5:      1 4 6 4 1
Row 6:    1 5 10 10 5 1
```

Each row `n` (1-indexed) consists of binomial coefficients: `ⁿC₀, ⁿC₁, ..., ⁿCₙ`.

**Known identity:**
```
ⁿC₀ + ⁿC₁ + ⁿC₂ + ... + ⁿCₙ = 2ⁿ
```

**For the `n`th row (1-indexed as shown above):**
```
Sum = 2^(n-1)
```

Using the left-shift operator (a fast way to compute powers of 2):
```
Ans = 1 << (n-1)
```

---

## Application — Number of Digits of a Number in Base `b`

**Problem:** Given a number `n`, find how many digits it has when written in base `b`.

**Example:** `(6)₁₀ = (110)₂` → 3 digits.

### Deriving the Formula

Starting from the logarithm identity:
```
log_b(a) = x   ⟺   a = b^x
```

For `n = 6`, base `2`:
```
log₂(6) = x   →   6 = 2^x   →   x ≈ 2.58
```
```
floor(x) + 1 = floor(2.58) + 1 = 2 + 1 = 3     ✓ matches (110)₂ having 3 digits
```

### Change of Base Formula

Most calculators/languages only give `log` in base 10 or base `e`, so use the change-of-base identity to compute a log in any base:
```
log_b(a) = log_x(a) / log_x(b)
```

**Example:** `log₂(10)`
```
log₂(10) = log(10) / log(2) ≈ 3.32
```
```
floor(3.32) + 1 = 3 + 1 = 4 digits     ✓ matches (10)₁₀ = (1010)₂ having 4 digits
```

### General Formula

```
Number of digits of n in base b  =  int( log_b(n) ) + 1
```

---

## Application — Check if a Number is a Power of 2

**Problem:** Given a number, determine whether it is a power of 2.

**Key insight:** any power of 2 has exactly **one** set bit in binary — a `1` followed by all zeros:
```
n = 10000000
```

**Why `n & (n-1) == 0` works:** subtracting `1` from a power of 2 flips that lone `1` to `0` and turns every bit after it into `1`:
```
  n   = 10000000
  n-1 = 01111111
```
These two values share **no common set bits** — so ANDing them together always gives `0`:
```
  10000000
& 01111111
----------
  00000000
```

If `n` is **not** a power of 2 (i.e. it has more than one set bit), then `n` and `n-1` will still overlap in at least one bit position, and `n & (n-1)` will be **non-zero**.

### Formula

```
If  n & (n-1) == 0   →  n is a power of 2
```

---

## Application — Fast Exponentiation (Binary Exponentiation)

**Problem:** Calculate `a^b`.

**Naive approach:** multiply `a` by itself `b` times — `O(b)`.
```
3⁶ = 3×3×3×3×3×3     // O(b)
```

**Faster approach — using the binary representation of the exponent:**
```
3⁶ = 3^(110₂) = 3^(2+4) = 3² × 3⁴
```
Instead of multiplying `a` by itself `b` times, break the exponent `b` into powers of 2 (its binary representation) and combine only the pieces where a bit is set.

### Algorithm

```
ans = 1
base = a

while (n > 0):
    if (n & 1) == 1:
        ans = ans × base
    base = base × base
    n = n >> 1

return ans
```

At each step: check the current LSB with `n & 1` (does this power-of-2 term contribute?), square the base (moving to the next power of 2: `a¹ → a² → a⁴ → a⁸ ...`), then shift `n` right to move to the next bit.

### Worked Example — `3⁶`

```
n = 6 = 110₂
```

| Step | `n` (binary) | `n & 1` | Action | `base` after squaring |
|---|---|---|---|---|
| 1 | `110` | `0` | skip (bit not set) | `3² = 9` |
| 2 | `11`  | `1` | `ans = 1 × 9 = 9`     | `9² = 81` |
| 3 | `1`   | `1` | `ans = 9 × 81 = 729`  | loop ends (`n = 0`) |

**Answer:** `3⁶ = 729`

### Complexity

```
Time Complexity = O(log b)
```
Since the loop runs once per **bit** of `b`, not once per unit of `b` — a massive improvement over the naive `O(b)` approach for large exponents.

---

## Application — Count the Number of Set Bits

**Problem:** Given a number `n`, find how many `1` bits (set bits) it has.

**Example:** `n = 9 = 1001₂` → **2 set bits**.

### Key Idea: Remove the Rightmost Set Bit, One at a Time

Recall the earlier formula for isolating the rightmost set bit:
```
n & (-n)  →  isolates the rightmost set bit
```

**Removing** that bit (instead of just isolating it) is done by subtracting it out:
```
n - (n & (-n))  →  clears the rightmost set bit, leaves everything else unchanged
```

If we keep applying this — clear the rightmost set bit, repeat — the number eventually becomes `0`. The number of times we had to repeat is exactly the number of set bits.

### Worked Example — `n = 9`

```
n = 1001
```

**Iteration 1:**
```
n & (-n) = 0001                     (isolate rightmost set bit)

n - (n & (-n)):
  1001
- 0001
------
  1000
```

**Iteration 2:**
```
n = 1000
n & (-n) = 1000                     (isolate rightmost set bit — it's the only one)

n - (n & (-n)):
  1000
- 1000
------
  0000                              → n is now 0, stop
```

**Result:** it took **2 iterations** to reduce `n` to `0`.

### Formula / Algorithm

```
count = 0
while (n != 0):
    n = n - (n & (-n))     // clear the rightmost set bit
    count = count + 1

return count
```

**Rule:** `No. of set bits = No. of iterations` until `n` becomes `0`.

**Note:** this is a variant of the classic **Brian Kernighan's Algorithm**, which more commonly clears the rightmost set bit via `n = n & (n-1)` — both approaches remove one set bit per iteration and run in `O(number of set bits)`, which is faster than checking all `n` bits one by one.

---

## Application — Find XOR of All Numbers from 0 to `a`

**Problem:** Given a number `a`, find `0 ^ 1 ^ 2 ^ ... ^ a`.

### Spotting the Pattern

| a | XOR from 0 to a |
|---|---|
| 0 | 0 |
| 1 | 0^1 = 1 |
| 2 | 0^1^2 = 3 |
| 3 | 0 |
| 4 | 4 |
| 5 | 1 |
| 6 | 7 |
| 7 | 0 |
| 8 | 8 |
| 9 | 1 |

The result **repeats in a cycle of 4**, based on `a % 4`:

```
a % 4 == 0   →   answer = a
a % 4 == 1   →   answer = 1
a % 4 == 2   →   answer = a + 1
a % 4 == 3   →   answer = 0
```

**Verifying against the table:** `a = 6` → `6 % 4 = 2` → `answer = a+1 = 7` ✓. `a = 9` → `9 % 4 = 1` → `answer = 1` ✓.

### Why This Works

XORing four consecutive integers whose first term is a multiple of 4 always cancels down to `0` (a property of how the low bits cycle every 4 numbers). This means the XOR of `0` to `a` only depends on **where `a` falls in its group of 4**, not on `a`'s actual size — which is why the whole range collapses into a constant-time formula instead of needing a loop.

### Formula (constant time, `O(1)`)

```
Ans = a % 4 == 0 ? a
    : a % 4 == 1 ? 1
    : a % 4 == 2 ? a + 1
    : 0
```

---

## Application — Find XOR of All Numbers Between `a` and `b`

**Problem:** Given `a` and `b`, find `a ^ (a+1) ^ (a+2) ^ ... ^ b`.

**Example:** `a = 3, b = 9` → find `3^4^5^6^7^8^9`.

### Building on the Previous Formula

Let `f(n)` denote **XOR of all numbers from `0` to `n`** (the `O(1)` formula derived in the previous section).

Write out XOR from `0` to `b`:
```
0^1^2 ^ 3^4^5^6^7^8^9
```
The numbers `0, 1, 2` (i.e. `0` to `a-1`) are **extras** that don't belong to the range `[a, b]` we actually want.

Since `x ^ x = 0`, XORing those same extras back in a second time **cancels them out**, leaving only the range `[a, b]`:
```
f(b) ^ f(a-1)
  = (0^1^2 ^ 3^4^5^6^7^8^9) ^ (0^1^2)
  = 3^4^5^6^7^8^9                        // the 0^1^2 pairs cancel via x^x = 0
```

### Formula

```
Ans = f(b) ^ f(a-1)
```
where `f(n)` is the XOR-from-`0`-to-`n` function defined earlier:
```
f(n) = n % 4 == 0 ? n
     : n % 4 == 1 ? 1
     : n % 4 == 2 ? n + 1
     : 0
```

**Note:** this whole computation is `O(1)`, since `f(n)` itself is `O(1)`.

---

## Application — Flipping an Image

**Problem:** Given a binary matrix (each cell is `0` or `1`), "flip" it: reverse each row, then invert every bit (`0 → 1`, `1 → 0`).

**Example:**
```
Original:          Reversed rows:      Flipped (inverted):
1 1 0               0 1 1                1 0 0
1 0 1       →        1 0 1        →      0 1 0
0 0 0                0 0 0                1 1 1
```

### Step 1 — Reverse Each Row

Simply reverse the order of elements within each row:
```
1 1 0  →  0 1 1
1 0 1  →  1 0 1
0 0 0  →  0 0 0
```

### Step 2 — Invert Every Bit

Inverting a bit (`0 → 1`, `1 → 0`) is the same **XOR with 1** trick seen earlier:
```
0 ^ 1 = 1
1 ^ 1 = 0
```
Applying this to every cell of the reversed matrix:
```
0 1 1        1 0 0
1 0 1   ^1   0 1 0
0 0 0        1 1 1
```

### Formula

```
For each row:
    reversed_row = reverse(row)
    flipped_row  = [cell ^ 1 for cell in reversed_row]
```

**Note:** since each row only contains `0`s and `1`s, `cell ^ 1` is a fast, branch-free way to invert a bit — equivalent to `1 - cell` but using a bitwise operator instead of arithmetic.