## Lust, The Limited Use Somewhat Trivial Java REPL

### Introduction

Lust is a *very* simple read-eval-print loop ([REPL](http://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop)) for Java. No parsing, error-checking, or interpretation is performed by Lust; the input is placed into a temporary .java file, compiled and passed to the JVM for execution. There is no sullying up of your file system; Lust cleans up after itself.

Lust gets its inspiration from the Scala command line interpreter.

### Description

Lust provides a simple interface to input Java statements for execution. Its current usefulness is likely limited to quick, ad hoc Java inquiries (syntax-checking, perhaps). The next step is to add support for functional programming, maybe classes...

### Usage

Input Java commands at the prompt, separated in the conventional manner (by semi-colon and newline).

When series of statements are entered, issue EOF command, which is CTRL+D in UNIX-like systems (you're on your own if you use Windows).

Results of code are output to console (if there is any).

When finished, exit Lust by typing:

```
:exit
```

### Requirements

The Lust REPL is written in Java, and therefore requires a JVM.

### Installation

```
// Compile Lust
javac Repl.java

// Execute Lust
java Repl
```

### Author

[Matt Mayo](http://about.me/mattmayo)

### License

This software is made available under the [MIT License](http://choosealicense.com/licenses/mit/)

