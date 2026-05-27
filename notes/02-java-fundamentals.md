# Java Language Fundamentals

## How Java Code Executes

```
.java file → Compiler (javac) → .class file (bytecode) → JVM → Machine Code
```

- `.java` file is human readable source code
- Compiler converts the entire file to bytecode (`.class` file)
- Bytecode cannot run directly on a system — needs JVM to run it
- This is why Java is **platform independent** but JVM is **platform dependent**
- In C++: `.cpp` → compiler → machine code (`.exe`) — platform dependent
- In Java: bytecode runs on any OS as long as JVM is installed

---

## JDK vs JRE vs JVM vs JIT

```
JDK (Java Development Kit)
└── JRE (Java Runtime Environment)
    └── JVM (Java Virtual Machine)
        └── JIT Compiler (Just-In-Time)
```

**JDK** — to develop and run Java programs. Includes:
- Development tools (to develop your program)
- JRE (to execute your program)
- Compiler — `javac`
- Archiver — `jar`
- Docs generator — `javadoc`
- Interpreter / loader

**JRE** — cannot create a Java program, you can only run programs on this. Includes:
- Deployment technologies
- User interface toolkits
- Integration libraries
- Base libraries
- JVM

**JVM** — executes code line by line. JVM is the content inside JRE (JRE is the box, JVM is the content).
- JVM works with the help of JRE like whatever library files JVM needs, JRE provides it
- JVM contains stack and heap memory allocations

**How JVM works at runtime:**
1. Class loader loads all classes needed to execute the program
2. JVM sends code to Byte Code Verifier to check format of code
3. Interpreter — line by line execution
4. JIT (Just-In-Time) — when one method is called many times, JIT provides direct machine code so re-interpretation is not required. Makes execution faster.

---

## Structure of a Java Program

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```

**`public`** — this class can be accessed from anywhere (access modifier)

**`class`** — blueprint or custom data type that groups related properties (variables) and functions (methods) together. Defines what data an object will hold and what it can do.

**`Main`** — name (identifier) given to the class or file. If file name is `Main.java`, the main class in the file must be `public class Main`. If public class name and file name are different, Java compiler throws a compile-time error.

**`main` method** — serves as the exclusive entry point / starting button of your program. Computer looks for it first to know where to begin running code. Program will not run without it.

**`public`** (on main) — makes it available to execute from anywhere

**`static`** — method belongs to the class itself, not to an object. Because it is static, the computer can run `main` immediately without needing to create a new `Main()` object first. There is nothing before entry point to run.

**`void`** — function does not return any data when it finishes. Just performs its task and stops.
- `void` = "Do this action" (print text, save file, change a color)
- Non-void = "Give me an answer" (calculate math, check a password, fetch data)

**`String[] args`** — stands for "string arguments". A list (array) of words you can pass into your program from the command line when you launch it. Collection of strings.

```
java Main apple banana orange
// args[0] = "apple"
// args[1] = "banana"
// args[2] = "orange"
```

---

## Packages

A package is a folder that organizes related Java classes together. Acts exactly like a folder system on your computer to keep your project neat and prevent naming conflicts.

- Written in lowercase letters only (suggested)
- `.` means subfolder — `com.riya` means inside `com` → `riya` package
- We can make more packages inside a package

**Why use packages?**
- No name clashes — you can have two classes named `User` as long as they are in different packages (e.g. `shop.user` and `admin.user`)
- Access control — you can hide certain classes or methods so they can only be used by other files inside the same folder

**Compile commands:**
```bash
javac -d . Main.java      # current directory
javac -d .. Main.java     # previous directory
```

---

## System, out, and println

**`System`** — built-in class provided by Java that gives access to your computer's system resources (keyboard, screen, memory). In `java.lang` package so everything in `lang` package we can access it directly.

3 main tools inside System:
- `System.out` — Standard Output: used to print messages onto your computer screen
- `System.in` — Standard Input: used to read text typed into the keyboard by a user
- `System.err` — Standard Error: used to print error messages to the screen

**`out`** — static variable inside System class that represents your computer screen as a standard output stream. Acts as middleman between your Java code and your actual display monitor.
- `out` is a variable of type `PrintStream`
- `out` has method `println`

**`println`** — stands for "print line". Prints text to the screen and automatically jumps to a new line afterward.

```
sout → System.out.println  (IntelliJ shortcut)
psvm → public static void main
```

---

## Input and Output

**`System.out.println`** — prints to screen with new line  
**`System.out.print`** — prints without new line

**Scanner** — built-in Java tool used to read user input from the keyboard. Available in `util` package. It scans what user types and converts it into data your program can use.

```java
import java.util.Scanner;
Scanner input = new Scanner(System.in);
```

- `Scanner` — Type
- `input` — Name
- `new Scanner(System.in)` — creates the object (constructor)
- `System.in` — connects it to the keyboard, passing it to read live typing from keyboard

**Common Scanner methods:**
- `input.next()` — reads first string before space
- `input.nextLine()` — reads a whole line of text (String)
- `input.nextInt()` — reads a whole number (int)
- `input.nextDouble()` — reads a decimal number (double)
- `input.nextFloat()` — reads a decimal number (float)

**What brackets can take:**
1. `System.in` → reads live typing from keyboard
   ```java
   Scanner keyboard = new Scanner(System.in);
   ```
2. A File Object → reads text directly from a saved document on your computer
   ```java
   Scanner fileBook = new Scanner(new File("data.txt"));
   ```

---

## Data Types

### Primitive Data Types
Only data, no functions. 8 categories:

| Type | Size | Range / Details |
| :--- | :--- | :--- |
| `byte` | 1 byte | -128 to 127 |
| `short` | 2 bytes | -32,768 to 32,767 |
| `int` | 4 bytes | -2,147,483,648 to 2,147,483,647 |
| `long` | 8 bytes | very large numbers, use `L` suffix |
| `float` | 4 bytes | decimal, use `f` suffix |
| `double` | 8 bytes | decimal, more precision (default for decimals) |
| `boolean` | 1 bit | `true` or `false` only |
| `char` | 2 bytes | single character, uses ASCII but in Java 2 bytes (e.g. `'A'`, `'b'`, `'#'`) |

**`int` vs `long`:**
```java
int a = 10;         // primitive datatype
int a = 2,147,483,647;   // max int value
// for bigger numbers use long
long a = 2,340,000,000L;  // use L suffix
```

**`float` vs `double`:**
- `int a` holds primitive datatype — `int a` is a primitive datatype
- `double myDouble = myInt` — this is valid, Java promotes smaller to larger automatically
- `float` — less precision decimal (use `f` suffix)
- `double` — more precision decimal (standard, default)
- `double` → `float` → `long` → `int` → `char`

**Narrowing Casting (Manually):**
- Must be done manually by placing the target type in parentheses
- A smaller data type size converts a larger type — can result in data loss
  ```java
  double a = 2.34000000;
  int a = (int) a;   // 2.34 becomes 2, decimal part lost
  ```

**Widening Casting (Automatically):**
- When you assign a value of a smaller data type to a larger one, Java will automatically cast
- No data loss
  ```java
  int a = 10;
  double a = a;  // automatically becomes 10.0
  ```

### Reference Data Types
- `boolean` — holds only two possible values: `true` or `false`. Base functions incompatibility with Java (class `Boolean` is used in 2 byte)
- `char` — 1 byte, holds a single character (uses ASCII, but in Java 2 bytes)
- Also have functions — gives access to wrapper classes, provides methods that are not restricted, wider range by methods

**Decimal Numbers (BigDecimal):**
- `float` — less standard decimal (e.g. 3.14f)
- `double` — the standard choice (e.g. 3.14) — used by default, because by default it stores more in terms of precision
- `double` is used by default because it stores more decimals (less rounding errors)

---

## Static vs Non-Static Methods

```java
// Static — belongs to the class. No object needed.
public class Calculator {
    public static void printWelcome() {
        System.out.println("Welcome to the app!");
    }
}
// Call directly:
Calculator.printWelcome();

// Regular (Non-static) — belongs to the object. Requires 'new'.
public class Calculator {
    public void addNumbers(int a, int b) {
        System.out.println(a + b);
    }
}
// Must create object first:
Calculator myCalc = new Calculator();
myCalc.addNumbers(5, 10);
```

**The difference:**
- Static: call it directly using the class name
- Regular: must use `new` to build an object first, then call it

# Java Fundamentals

## Conditionals and Loops

### if-else
```java
if (boolean expression) {
    // body
} else {
    // do this
}
```
- Condition must be a boolean expression (true or false)
- Can chain with `else if` for multiple conditions
- Operators: `&&` (and), `||` (or), `!=` (not equal)

### for loop — use when you know how many times the loop will run
```java
for (initialisation; condition; increment/decrement) {
    // body
}
```

### while loop — use when you don't know how many times the loop will run
```java
while (condition) {
    // body
}
```

### do-while — executes at least once even if condition is not met
```java
do {
    // body
} while (condition);
```

---

## Important Digit Extraction Pattern

To get the last digit of a number: `n % 10`  
To remove the last digit: `n / 10`

This pattern is used in many problems:

**Counting occurrences of a digit:**
```
n = 13839, count occurrences of 3
rem = n % 10  → last digit
if rem == 3 → count++
n = n / 10   → remove last digit
repeat while n > 0
```

**Reversing a number:**
```
n = 23597
ans = 0
rem = n % 10 → 7
ans = ans * 10 + rem → 7
n = n / 10 → 2359

rem = 9 → ans = 79
rem = 5 → ans = 795
rem = 3 → ans = 7953
rem = 2 → ans = 79532
```

---

## Fibonacci Numbers

Sequence: 0, 1, 1, 2, 3, 5, 8, 13 ...  
Each number = sum of previous two numbers.

To find nth Fibonacci number:
- Start with a = 0, b = 1
- Each step: new b = a + b, new a = old b
- Repeat n-2 times (since we already have first two)

---

## Key Observations from Practice

- `Math.max(a, b)` — returns larger of two numbers, can be nested: `Math.max(c, Math.max(a, b))`
- `in.next().trim().charAt(0)` — to read a single character from Scanner
- Always handle division by zero: check `num2 != 0` before dividing
- `do-while` is the right choice when a menu needs to show at least once (like a Calculator)

## Switch Statements

In switch statements you can jump to various cases based on your expression.

### Old Syntax
```java
switch (expression) {
    case one:
        // do something
        break;
    case two:
        // do something
        break;
    default:
        // do something
}
```

### Enhanced Syntax (Java 14+) — preferred
```java
switch (expression) {
    case one -> // do this;
    case two -> // do this;
    default  -> // do this;
}
```
- `break` is not required in enhanced syntax
- For multiple statements in one case, use `{ }`

### Multiple values in one case
```java
case 1, 2, 3, 4, 5 -> System.out.println("Weekday");
case 6, 7          -> System.out.println("Weekend");
```

### Important Rules
- Cases must be the same type as the expression
- Cases must be a constant or literal — not a variable
- Duplicate case values are not allowed
- `break` terminates the sequence — without it, execution falls through to the next case
- `default` executes when none of the cases match
- If `default` is not at the end, put `break` after it

### Nested Switch
A switch inside another switch case. Used when one case needs further branching.

```java
switch (empID) {
    case 3 -> {
        switch (department) {
            case "IT"         -> System.out.println("IT Department");
            case "Management" -> System.out.println("Management Department");
            default           -> System.out.println("No department entered");
        }
    }
}
```

---

## == vs .equals()

### == (Identity operator)
- For primitives: compares raw values directly
- For objects: compares memory addresses (references)
- Question it asks: "Are these two variables pointing to the exact same physical spot in memory?"
- Safe for primitives, unreliable for objects
- Can throw `NullPointerException` if the first object is null

### .equals() (Content method)
- Compares the actual text or values inside the objects
- Question it asks: "Do these two objects look identical on the inside, even if they are in different memory spots?"
- `.equals()` will give true in both cases — whether same reference or different reference with same value
- Always use `.equals()` for String comparison, never `==`

```java
// Example
String a = "Riya";  // a points to object "Riya"
String b = "Riya";  // b points to a different object also called "Riya"

a == b        // false — different memory addresses
a.equals(b)   // true  — same content
```

- Always use `.equals()` for String comparison in Java
- `==` can throw a NullPointerException if the first object is null

---

## Functions and Methods

In Java, functions are called methods because everything lives inside a class. A method is a block of code that only runs when it is called.

**The Problem without methods — WET Code (Write Everything Twice)**
Without methods you end up copy-pasting the same logic over and over, making your program messy and hard to fix.

**The Solution — DRY Code (Don't Repeat Yourself)**
Bundle that logic into a single reusable method. Define it once, use it many times.

### Syntax
```java
access-modifier return-type methodName() {
    // code
    return statement; // function ends here
}
```

### Return Type
- A return statement causes program control to transfer back to the caller of a method
- Return type may be a primitive type like `int`, `char`, or `void` (returns nothing)
- The type of data returned must be compatible with the return type specified by the method
- The variable receiving the returned value must also be compatible with the return type
- After `return`, nothing executes in that method

### Calling a method
```java
methodName(); // calling the function
```

---

## Pass by Value

In Java there is no such thing as pass by reference — there is only pass by value.

### For primitives (int, short, char, byte etc.)
- The value is copied and passed
- Changes inside the method do not affect the original variable

```java
// In main:
int a = 10, b = 20;
swap(a, b);
// a and b are still 10 and 20 here

// Inside swap:
static void swap(int a, int b) {
    int temp = a;
    a = b;
    b = temp;
    // change is only valid inside this function scope
}
```

### For objects and arrays
- The value of the reference variable is passed (i.e. the memory address)
- Both the original and parameter variable point to the same object
- So changes to the object's content ARE visible outside

```java
// arr and nums both point to same array [1,3,2,45,6]
static void change(int[] nums) {
    nums[0] = 99; // changes the actual object → arr becomes [99,3,2,45,6]
}
```

### For Strings (important edge case)
- Strings are objects but they are immutable
- Assigning a new value inside a method creates a new object, does not change the original

```java
static void changeName(String naam) {
    naam = "Akriti"; // creates new object, naam now points to new object
    // original name variable still points to "Riya"
}
```

---

## Scopes

### Function scope
Variables declared inside a method can't be accessed outside that method.

### Block scope
Refers to the visibility and lifetime of a variable declared within a specific set of curly braces `{ }`.
- Variables initialized outside a block can be updated inside the block
- Variables initialized inside a block cannot be updated outside the block, but can be re-initialized outside
- Variables like `a` declared outside, updated inside a block, retain the updated value outside too

### Loop scope
Variables declared inside a loop have loop scope — they cannot be accessed outside the loop.

---

## Shadowing

Shadowing in Java is the practice of using variables in overlapping scopes with the same name, where the low-level scope overrides the variable of the high-level scope.

```java
static int x = 90; // class-level variable

public static void main(String[] args) {
    System.out.println(x); // 90 — class variable
    int x;                 // class variable is now shadowed
    x = 40;
    System.out.println(x); // 40 — local variable
}

static void fun() {
    System.out.println(x); // 90 — sees class variable, not shadowed here
}
```

- Scope of a local variable begins when its value is initialized, not after declaration

---

## Variable Arguments (varargs)

Used to take a variable number of arguments. A method that takes a variable number of arguments is a varargs method.

```java
static void fun(int ...v) {
    System.out.println(Arrays.toString(v));
    // v is treated as an array of type int[]
}
```

- Can pass strings, chars, or any type with varargs
- varargs parameter must always be the last in the parameter list

```java
static void multiple(int a, int b, String ...v) {
    // a and b are fixed, v takes the rest
}
```

---

## Method Overloading

Method overloading happens when two methods have the same name but different arguments.

- Same name, same arguments with no return type difference → NOT overloading (not allowed)
- Same name, different arguments → allowed, this is overloading
- At compile time, Java decides which method to run based on the arguments passed

```java
static void fun(int a)     { } // called when int is passed
static void fun(String s)  { } // called when String is passed

static int sum(int a, int b)         { return a + b; }
static int sum(int a, int b, int c)  { return a + b + c; }
```

---

## Armstrong Number

A number where the sum of each digit raised to the power of the number of digits equals the original number.

```
153 → (1)³ + (5)³ + (3)³ = 1 + 125 + 27 = 153 ✓
```

Logic: extract each digit using `% 10`, cube it, add to sum, remove digit using `/ 10`. Compare final sum with original number.