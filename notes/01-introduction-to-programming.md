# Introduction to Programming

## Types of Languages

### Procedural
- Specifies a series of well-structured steps and procedures to compose a program
- Contains a systematic order of statements, functions, and commands to complete a task
- Examples: Python, C++, Java, C

### Functional
- Writing a program only in pure functions — never modifies variables, only creates new ones as output
- Used in situations where we perform lots of different operations on the same set of data, like ML
- Functions are first class — can be passed to other functions
- Examples: Python (supports it), Haskell

### Object Oriented
- Revolves around objects — Code + Data = Object
- Developed to make it easier to develop, debug, reuse, and maintain software
- A single data type can be a custom type (class) which holds string, integer, decimal etc. together
- Class is a blueprint, object is an instance of that class
- Examples: Java, Python, C++

---

## Static vs Dynamic Languages

### Static (e.g. Java)
- Type checking happens at compile time
- Errors show at compile time
- Must declare data type before using a variable
- More control
- Example: `int a = 10` is valid, `int a = "Riya"` throws error at compile time

### Dynamic (e.g. Python)
- Type checking happens at run time
- Errors might not show till program is run
- No need to declare data type
- Saves time writing code but might give errors at runtime
- Example: `a = 10` then `a = "Riya"` is valid — value just points to new object

---

## Memory Management

Two types of memory: **Stack** and **Heap**

- **Stack** — stores primitive values and references (variable names)
- **Heap** — stores objects and actual values that reference variables point to

### How it works
- `a = 10` → stack stores `a`, heap stores `10`, `a` points to address of `10`
- `a = 37` → `10` is removed from heap, `a` now points to `37`
- In dynamic languages like Python — no error when reassigning different types, old value is removed from heap
- In Java — `NullPointerException`, `ArrayIndexOutOfBoundsException` are common heap/reference errors

### Reference Variables
- More than one reference variable can point to the same object
- If any one of them changes the object, the change is visible to all variables pointing to it
- An object with no reference variable pointing to it is alone in memory — garbage collector removes it automatically when it hits

---

## Flow of a Program

Programs execute line by line, top to bottom, unless redirected by conditions or loops.

---

## Flowcharts

Symbols used:
- **Oval** — Start / Stop
- **Parallelogram** — Input / Output
- **Rectangle** — Processing
- **Diamond** — Condition

Example — Salary bonus problem:
- Input salary
- If salary > 10,000 → salary = salary + 2000
- Else → salary = salary + 1000
- Output salary

---

## Pseudocode

Written in plain language, not actual code. Used to plan logic before writing real code.

Example — Check if a number is prime (basic):
```
start
read n
num = 2
while num < n:
    if n % num == 0:
        output "not prime"
        exit
    num = num + 1
end while
output "prime"
exit
```

Example — Check if a number is prime (optimized):
```
start
input n
if n <= 1:
    print "neither prime nor composite"
num = 2
while num * num <= n:
    if n % num == 0:
        output "not prime"
        exit
    num = num + 1
end while
output "prime"
exit
```

Why optimized? For n = 36, sqrt(36) = 6 — only need to check till 6, not till 35. Complexity reduced to O(sqrt n).