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

### Java Implementation

```java
package com.riya;

public class OddEven {

    public static void main(String[] args) {
        int n = 68;
        System.out.println(isOdd(n));
    }

    static boolean isOdd(int n){
        return (n & 1) == 1;
    }
}
```

**Time Complexity:** `O(1)`
**Space Complexity:** `O(1)`

This is a classic bit-manipulation trick — faster and more direct than `n % 2 == 1`, and it's the foundation for many other bit-level problems (checking/setting/clearing arbitrary bits, counting set bits, etc.).