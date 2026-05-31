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

---

## Two Pointer Method — Reverse an Array

Use two pointers, one at start and one at end, swap and move towards each other until they meet.

```java
static void reverse(int[] arr) {
    int start = 0;
    int end = arr.length - 1;
    while (start < end) {
        swap(arr, start, end);
        start++;
        end--;
    }
}
```

---

## 2D Arrays

A 2D array can be visualised as a matrix (rows and columns). It is essentially an array of arrays.

Think of it as a 1D array written vertically, then each element replaced with another array.

```
int[] num = {2, 5, 6, 9, 34}  →  5 rows

Replace each element with an array:
int[][] num = {
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 10, 11, 12},
    {13, 14, 15, 16},
    {17, 18, 19, 20}
};
// rows = 5, columns = 4, total elements = 5 * 4 = 20
```

### Syntax

```java
// Fixed size
datatype[][] variable-name = new datatype[row-size][column-size];

// Declaration then initialisation
datatype[][] variable-name;
variable-name = new datatype[row-size][column-size];

// Direct initialisation
datatype[][] variable-name = {{array1}, {array2}, {array3}};
```

- Row size is mandatory to give
- Column size is not mandatory (jagged arrays allowed)

### Internal Memory

```
Stack         Heap
------        ---------------------------
arr    →      {[1,2,3], [4,5,6], [2,8,9]}  ← array of arrays
               row 0     row 1    row 2
```

- `arr` reference variable is declared in stack
- A new object is created in heap with the size of the array
- Each row is itself an array object stored in different parts of heap

### Accessing Elements

```java
arr[0]    // gives {1, 2, 3} — first row (an array)
arr[0][2] // gives 3 — element at row 0, column 2
```

### Dynamic 2D Array (jagged)

```java
int[][] num = {
    {1, 2, 3, 4},
    {5, 6, 7},
    {8, 9, 10, 11},
    {12, 13},
    {14}
};
// rows = 5, columns = dynamic (each row has different length)
```

---

## ArrayList

ArrayList is similar to vectors in C++. It is part of the Collections Framework, present in `java.util` package. It provides dynamic arrays in Java and is slower than standard arrays.

Use ArrayList when you don't want to set a fixed size because you don't know how many elements you may add, or you may want to update the size in the future.

### Syntax

```java
ArrayList<Integer> list = new ArrayList<>(5);
//         ↑               ↑                ↑
//    wrapper class    creates object    initial size (optional)
```

- Must use wrapper classes (not primitives) — `Integer` not `int`, `Double` not `double`
- Initial size is optional — ArrayList will resize automatically

### Common Methods

```java
list.add(67);           // adds element to end
list.set(0, 99);        // updates value at index 0
list.get(i);            // retrieves value at index i — use this, not list[i]
list.remove(2);         // removes element at index 2
list.contains(654);     // returns true/false
list.size();            // returns current size
```

### Internal Working

Internally the size of ArrayList is fixed but not permanently — it can change according to input you provide.

Example: initial size set to 5
```
[4 | 9 | 3 |   |   ]
```
You add 4 more elements but don't have enough space. Java will create a new list with a new size (it depends) enough to accommodate new elements, copy the old list to new list, and delete the old list.

```
[4 | 9 | 3 | 6 | 5 | 23 | 12 |   |   |   ]
```

### Array vs ArrayList

| Array | ArrayList |
| :--- | :--- |
| Fixed length/size | Can contain as many elements as you want even though initial size is specified |
| Can be created using both primitive and non-primitive datatypes | Cannot be created using primitives — only objects or wrapper classes |

### Multi-dimensional ArrayList

```java
ArrayList<ArrayList<Integer>> list = new ArrayList<>();

// initialise inner lists
for (int i = 0; i < 3; i++) {
    list.add(new ArrayList<>());
}

// add elements
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        list.get(i).add(in.nextInt());
    }
}
```

---

## Important Notes

- `Arrays.toString(array)` — internally uses a loop and gives output in proper format. Needs `import java.util.Arrays`
- In an array, since we can change the objects, arrays are **mutable**
- Strings are **immutable** — cannot change the object itself, only reassign reference
- ArrayList in Java is similar to vectors in C++