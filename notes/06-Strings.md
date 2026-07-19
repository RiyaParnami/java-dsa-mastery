# Strings — Advanced Concepts

## String Concatenation Cost

- Strings are immutable — every `+` concatenation creates a **new object**, the original is never changed
- Building a string character-by-character in a loop touches every intermediate string:

```
[a, ab, abc, abcd, ..., abcdef...y]
```
- None of these intermediate strings have a reference variable pointing to them — each one is discarded right after the next `+`
- Total work done: `1 + 2 + ... + N = N(N+1)/2 = (N² + N)/2`
- Complexity: **O(N²)** for building an N-length string this way, not O(N)
- Modern Java compilers usually optimize a single `+` expression into `StringBuilder` calls behind the scenes, but that optimization only helps *within one expression*. Repeated concatenation across loop iterations is still **O(N²)**, because a brand-new result string is created and copied on every iteration regardless.

---

## StringBuilder

- Creates a **mutable (changeable)** sequence of characters
- Modifies text **in-place**, inside normal heap memory — bypasses the String pool
- Fast and efficient for frequent text updates
- It is a separate class, not part of the String class hierarchy

### Sorting
- `StringBuilder` has no built-in sorting method
- To sort its characters: convert it to a `char[]` (or a `String`) first, sort the array with `Arrays.sort()`, then build a new `String`/`StringBuilder` from the sorted characters
- A `char[]` is often the simplest route, since you don't have to go through `String` specifically

---

## Formatted Printing

Java uses `%` placeholders for formatted output:

```java
float a = 483.12345f;
System.out.printf("Formatted no. is %.2f", a);
```

```java
System.out.printf("Hello my name is %s & I am %s", "Riya", "Cool");
```

| Placeholder | Meaning |
|---|---|
| `%s` | String |
| `%.2f` | Float, 2 decimal places |

---

## The `+` Operator on Strings

```java
'a' + 'b'   // = 195   → char arithmetic, adds ASCII values (not concatenation!)
"a" + 1     // = "a1"  → string concatenation
```

- **Rule:** `+` only performs concatenation when at least one operand is a String — it never works on arbitrary objects
- When one operand is a `String`, Java converts the other operand to its string representation — internally using mechanisms equivalent to `String.valueOf()` or compiler-generated `StringBuilder` code. There's no need to think of it as first creating an `Integer` wrapper object.
- The `-` operator does **not** work for Strings at all

```java
System.out.println("a" + "b");   // → "ab"  (a brand-new object)
```

Each step of concatenation creates a new object:
```
""            →  ""
"" + "a"      →  "a"
"a" + "b"     →  "ab"
"ab" + "c"    →  "abc"     // new object created every time
```

---

## String Pool & Comparison

### Example 1 — Literal syntax (uses the pool)
```java
String s1 = "Riya";
String s2 = "Riya";
```
Both point to the **same pooled object** → `s1 == s2` is **true**.

### Example 2 — The `new` keyword (bypasses the pool)
```java
String s3 = new String("Riya");
```
`s3` is created on the heap, **outside** the pool — Java is explicitly told to create a different object with the same value.

`s1 == s3` is **false**.

### `==` vs `.equals()`

| Method | Checks |
|---|---|
| `==` (comparator) | Whether reference variables point to the **same object** |
| `.equals()` | Whether the **values** are equal |

- Use `==` only to check identity (same object in memory)
- Use `.equals()` when you only care about value equality
- Example: `s1.equals(s3)` → `true` (values match, even though `==` gave `false`)

**Note:** `println()` internally calls `.toString()` — for arrays specifically, use `Arrays.toString()` to get a readable output instead of a memory address.