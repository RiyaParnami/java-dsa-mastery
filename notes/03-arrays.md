# Arrays

## Why Arrays?

Storing 5 numbers with 5 variables is fine. Storing 5000 numbers with 5000 variables is not possible. Arrays solve this — store a collection of similar data type values under one variable name.

**Array** — a data structure used to store a collection of similar data type values.

---

## Syntax

```java
// Declaration + Initialisation together
datatype[] variable-name = new datatype[size];

// Declaration first, Initialisation later
datatype[] variable-name;
variable-name = new datatype[size];

// Direct initialisation with values
datatype[] variable-name = {value1, value2, value3, ...};
```

All data types in an array must be the same.

---

## Internal Working — Stack and Heap

```java
int[] rollnos;           // declaration — rollnos reference variable is defined in stack
rollnos = new int[5];    // initialisation — object is created in heap memory
```

- **Declaration** happens at compile time — reference variable goes on the stack
- **Initialisation** happens at runtime — actual object is created in heap (Dynamic Memory Allocation)
- The reference variable on the stack holds the address of the object in heap
- If the reference variable has nothing to point to, it returns `null` when called

**Dynamic Memory Allocation** — memory is allocated at runtime (execution time), not at compile time. This is what `new` does.

```
Stack         Heap
------        ----------------------
arr    →      [ 1 | 5 | 7 | 8 | 9 ]
               n[0] n[1] n[2] n[3] n[4]
```

- Primitives (`int`, `char` etc.) are stored in stack
- All other objects are stored in heap memory
- Array itself is an object and is stored in heap
- Each element of the array (if it is a String or object) is also stored in different parts of heap memory

---

## Default Values

If you don't provide values in the array, Java stores defaults:
- `int` array → `0`
- `String` array → `null`
- `boolean` array → `false`
- `double` array → `0.0`

```java
int[] arr = new int[5];
System.out.println(arr[1]); // 0

String[] arr = new String[4];
System.out.println(arr[0]); // null
```

---

## null

- `null` is a special literal meaning "nothing / no object"
- Can be assigned to non-primitives (reference types) only
- Cannot be assigned to primitives

```java
String str = null;  // valid
int num = null;     // compile error
```

---

## Indexing

Array indexing starts at 0.

```
index →  0   1   2   3   4   5
arr   → [3 | 8 | 9 | 10 | 53 | 33]

arr[0] = 3    arr[2] = 9    arr[4] = 53
arr[1] = 8    arr[3] = 10   arr[5] = 33
```

To change a value at a specific index:
```java
arr[4] = 99;
// arr becomes [3, 8, 9, 10, 99, 33]
```

---

## Internal Representation

- In C, arrays are always stored in contiguous memory
- In Java, memory allocation depends on JVM — heap objects are not guaranteed to be contiguous
- Java Language Specification (JLS) states heap objects are not necessarily contiguous
- Dynamic memory allocation means array objects in Java may not be stored continuously (depends on JVM)

---

## new Keyword

`new` is used to create an object in heap memory.

```java
int[] arr = new int[5];
// creates an object of array size 5 in heap memory
```

---

## Common Mistakes

- Using `==` to compare arrays instead of `Arrays.equals()`
- Accessing index out of bounds — throws `ArrayIndexOutOfBoundsException`
- Forgetting that array size is fixed after creation
- Trying to assign `null` to a primitive type array element directly
- Confusing declaration (stack) with initialisation (heap)