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
    int rollNo;
    String name;
    float marks;
}
```
Creating the objects with `new`:
```java
Student student1 = new Student();
student1.rollNo = 14;
student1.name = "Kunal";
student1.marks = 82.9f;

Student student2 = new Student();
student2.rollNo = 28;
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

## Summary

- A **class** is a template — a **logical construct**; it takes no memory.
- An **object** is a real instance of a class — **physical reality**; it **occupies space in memory**.
- **`new`** creates an object.
- A variable like `a` or `b` holds a **reference** to the object, not the object itself.
- Many objects can be created from one class, each with **its own values**.