# MicroJava Compiler

A compiler for the MicroJava programming language implemented in Java as a college project for the Compiler Construction course.

The project was developed through four classical compiler phases:

1. Lexical Analysis
2. Syntax Analysis
3. Semantic Analysis
4. Code Generation

The compiler generates bytecode for the MicroJava Virtual Machine.

---

# Technologies Used

- Java
- JFlex
- CUP / AST-CUP
- Apache Ant

---

# Project Structure

The compiler is implemented in several phases:

## 1. Lexical Analysis

Lexical analysis was implemented using a `.flex` specification and generated using the JFlex tool.

The generated lexer implements the standard CUP lexer interface.

### Supported lexical structures

- Identifiers
- Constants
- Keywords
- Operators
- Comments

The lexer ignores:
- Comments
- Blank spaces
- Tabs
- New lines

### Error Reporting

The lexer reports:
- Line number
- Column number
- Unrecognized token

---

# 2. Syntax Analysis

Syntax analysis was implemented using an LALR(1) bottom-up grammar specification.

The parser was generated using AST-CUP.

This phase generates:
- Standard CUP parser specification
- Abstract Syntax Tree (AST)
- Java AST classes

The generated AST is also printed to the terminal.

## Error Recovery

Parser error recovery is implemented for:

### Global variable definitions
Symbols are ignored until:
- `;`
- `.`

### Assignment statements
Symbols are ignored until:
- `;`

### Function formal parameter declarations
Safe symbols:
- `)`
- `,`

### Logical expressions inside `if` statements
Symbols are ignored until:
- `)`

The `Compiler` class is used as the main entry point for this phase and subsequent phases.

---

# 3. Semantic Analysis

Semantic analysis is performed by traversing the generated AST using the Visitor pattern.

The `SemanticAnalyzer` class extends `VisitorAdaptor` and performs semantic checks.

A symbol table implementation provided through an external `.jar` library is used during this phase.

Because of the large number of semantic rules implemented, they are not individually listed in this documentation.

## Semantic analysis tracks usage of:

- Symbolic constants
- Global variables
- Local variables
- Global function calls
- Array element access
- Function formal arguments

---

# 4. Code Generation

For programs that are both syntactically and semantically correct, MicroJava bytecode is generated for the MicroJava Virtual Machine.

Code generation is implemented using the `CodeGenerator` class, which extends `VisitorAdaptor`.

The following tools from `mj-runtime-1.1.jar` are used:

- `Code`
- `disasm`
- `Run`

---

# Building and Running

The project uses Apache Ant and the provided `build.xml` file.

## Compile the project

Run the `compile` target:

```bash
ant compile
```

This executes:
- Lexical analysis
- Syntax analysis
- Semantic analysis

## Generate bytecode and run the program

Run:

```bash
ant runObj
```

This executes:
- Code generation
- Program execution on the MicroJava Virtual Machine

---

# Dependencies

All required libraries are included in the repository because they are not publicly available online.

---

# Notes

This project was developed as part of a university program compiler course.

Course assignment specifications are not included in this repository due to potential copyright restrictions.

For more information about this project, check: [official program compiler course website](http://ir4pp1.etf.rs/Domaci.html)

---

# Author

[Jovan Beldar]

---

# License

This project is intended for educational purposes.
