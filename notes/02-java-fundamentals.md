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