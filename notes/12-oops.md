# Object-Oriented Programming — Classes & Objects

## Class vs Object (VVI)

| | Class | Object |
|---|---|---|
| What it is | a **logical construct** | **physical reality** |
| In memory | takes **no space** by itself — it is only a blueprint / template | **occupies space in memory** |
| Describes | which properties (data) a thing has | one actual thing, with real values for those properties |

```
Class   → logical construct      (the design)
Object  → physical reality       (the real thing — occupies space in memory)
```
One class can be used to create **many objects**, and every object has **its own copy** of the values.

---

## Example 1 — `Student`

A **class** groups the properties that every student has:

```
Student
────────────────────────
roll no,  name,  marks
```

Three **objects** (instances) made from this class, each with its own values:

```
                         Student
                    ( roll no, name, marks )
                  /           |            \
     student1            student2             student3
  14, Kunal, 82.9      28, Rahul, 92.7      33, Anais, 95.89
```

| Object | roll no | name | marks |
|---|---|---|---|
| `student1` | 14 | Kunal | 82.9 |
| `student2` | 28 | Rahul | 92.7 |
| `student3` | 33 | Anais | 95.89 |

The **class** is written **once**; the **objects** are the actual students. Each student has a different roll no, name and marks.

### In Java

```java
class Student {
    int rno;
    String name;
    float marks;
}
```
Creating the objects with `new`:
```java
Student student1 = new Student();
student1.rno = 14;
student1.name = "Kunal";
student1.marks = 82.9f;

Student student2 = new Student();
student2.rno = 28;
student2.name = "Rahul";
student2.marks = 92.7f;
```

---

## Example 2 — `Car`

```
Car
────────────────────────
engine,  price,  seats
```

| Object | engine | price | seats |
|---|---|---|---|
| Maruti | petrol engine | 1 lakh | 4 |
| Ferrari | diesel engine | 2 crore | 2 |
| Audi | electric engine | 1 crore | 4 |

```
                        Car
                 ( engine, price, seats )
              /           |             \
         Maruti        Ferrari          Audi
   petrol, 1 lakh, 4  diesel, 2 crore, 2  electric, 1 crore, 4
```
One `Car` class — three different car objects.

```java
class Car {
    String engine;
    double price;
    int seats;
}
```

---

## Creating an Object — the `new` Keyword (VVI)

`new` **creates the object** — it asks for space in memory and builds the object there.

You have already been doing this:
```java
ArrayList<Integer> list = new ArrayList<>();
//                        ^^^
//        `new` creates an ArrayList OBJECT in memory
```
`ArrayList` is a **class** (the blueprint); `list` refers to one **object** made from it.

---

## Objects in Memory — References

An object lives in memory, and the variable you write in code does **not** hold the object itself — it holds a **reference** (a way to reach the object).

```
   STACK                          HEAP
 ┌─────────┐                ┌──────────────────┐
 │   a  ───┼──────────────► │  object          │
 │         │                └──────────────────┘
 │   b  ───┼──────────────► ┌──────────────────┐
 │         │                │  object          │
 └─────────┘                └──────────────────┘
 reference variables            real objects
```
- **`a`, `b`** are **reference variables** — they point to objects.
- The **objects** are the physical thing that **occupies space in memory**.

```java
Student a = new Student();     // a → one Student object
Student b = new Student();     // b → another Student object
```
(The drawing in the sketch is partly covered, so the right-hand side shows only the arrows from `a` and `b` into the object area.)

---

## Declaring vs Creating an Object — Compile Time vs Runtime

```java
class Student {
    int rno;
    String name;
    float marks;
}
```
Making an object takes **two steps**, and they can be written separately:

```java
Student student1;                 // 1. declare — a reference variable of type Student
student1 = new Student();         // 2. initialize — create the object and store its reference
```
or in one line:
```java
Student student1 = new Student();
```

| Part | When it happens | What it does |
|---|---|---|
| `Student student1` | **compile time** | declares the reference variable `student1` (its name and type are known before the program runs) |
| `new Student()` | **runtime** | creates the object **while the program is running** |

(Until `new` runs, `student1` has **no object** to point to.)

### What Is `Student()`? — the Constructor (VVI)

The circled `Student()` after `new` (the `?` in the sketch) is the **constructor**.

> **A constructor is a special function that runs when you create an object, and it allocates some variables.**

So `new Student()` runs the constructor of the `Student` class, which sets up the object's variables (`rno`, `name`, `marks`). Its name is the **same as the class name**.

### What Does `new` Do? (VVI)

> **`new` dynamically allocates memory and returns a reference to it.**

- **Dynamically** → the memory is allocated **at runtime**, not before the program runs.
- It returns a **reference**, which is stored in `student1`.

```
   STACK                          HEAP
 ┌───────────┐              ┌──────────────┐
 │ student2 ─┼────────────► │   ( object ) │
 │           │              └──────────────┘
 │ student1 ─┼────────────► ┌──────────────┐
 │           │              │ rno          │
 └───────────┘              │ name         │
                            │ marks        │
                            └──────────────┘
```
Objects live in the **heap**; the reference variables (`student1`, `student2`) live in the **stack** and point to them.

### The Dot Operator `.`

The **dot operator** reaches the variables **inside** an object:

```java
System.out.println(student1.rno);       // prints 14
```
`student1.rno` means "the `rno` of the object that `student1` refers to". Each object has its own `rno`, so `student2.rno` is a different value.

---

## Constructor with Arguments — Binding Values to the Object

**What:** give the values at the moment the object is created.

```java
Student kunal = new Student(13, "Kunal Kushwaha", 84.3f);
```
The `Student(...)` after `new` is the constructor, and now it receives **arguments** — `13`, `"Kunal Kushwaha"`, `84.3`.

> The constructor is a **special type of function in the class**. Its job is to **bind these arguments with the object** — the values you pass become the values of that object's variables.

### Default Values vs Constructor Values (VVI)

When an object is created, its variables first get **default values**; the constructor then **replaces** them with the arguments.

```
Student (the class — default values)
┌────────────────────┐
│ rno   = 0          │
│ name  = null       │
│ marks = 0.0        │
└────────────────────┘
        │ new kunal                         │ rahul
        ▼                                   ▼
┌────────────────────────────┐      ┌────────────────────────────┐
│ rno   = 13                 │      │  its OWN rno, name, marks  │
│ name  = "Kunal Kushwaha"   │      │                            │
│ marks = 84.3               │      │                            │
└────────────────────────────┘      └────────────────────────────┘
```
Every `new` makes a **separate object**, and each one has **its own** copy of `rno`, `name` and `marks`. Setting `kunal`'s values does not touch `rahul`'s.

| Variable | Default value |
|---|---|
| `int rno` | `0` |
| `String name` | `null` |
| `float marks` | `0.0` |

### Code — Writing the Constructor

```java
class Student {
    int rno;
    String name;
    float marks;

    // constructor: same name as the class, no return type
    Student(int r, String n, float m) {
        rno = r;              // bind the arguments to this object's variables
        name = n;
        marks = m;
    }
}
```
```java
Student kunal = new Student(13, "Kunal Kushwaha", 84.3f);
Student rahul = new Student(28, "Rahul", 92.7f);

System.out.println(kunal.rno);        // 13
System.out.println(rahul.name);       // Rahul
```
Once you write a constructor **with arguments**, `new Student()` (with no arguments) no longer works unless you also write a constructor that takes none.

---

## Summary

- A **class** is a template — a **logical construct**; it takes no memory.
- An **object** is a real instance of a class — **physical reality**; it **occupies space in memory**.
- **`new`** creates an object.
- A variable like `a` or `b` holds a **reference** to the object, not the object itself.
- Many objects can be created from one class, each with **its own values**.
- `Student student1` is the declaration (**compile time**); `new Student()` creates the object (**runtime**).
- `Student()` is the **constructor** — a special function that runs when an object is created.
- `new` **dynamically allocates memory and returns a reference** to it.
- The **dot operator** (`student1.rno`) accesses the variables of an object.
- A constructor with arguments (`new Student(13, "Kunal Kushwaha", 84.3f)`) **binds the arguments to the object**.
- Variables start with **default values** (`0`, `null`, `0.0`); the constructor replaces them. Every object has its **own copy**.