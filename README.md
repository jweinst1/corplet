# Corplet

`Corplet` is a binary-corpus storage system that is designed to store extremely large sets of words in a space-optimized format. Corplet is particularly useful for speech and word tagging, and can be used for more complex categories than parts of speech.

## Usage

`Corplet` is a scala package built with the sbt build system. Running the command `$ sbt run` in your shell or running the `main` object will start an interactive Repl where you can play around with Corplets.

```
----- Corplet Repl -----
------************------
|--By: Josh Weinstein--|
corp> open foo
:[Opened Corplet foo]
corp> insert foo rats
:[Inserted rats into Corp: foo]
corp> contains foo rats
:[foo = rats -> true]
corp> contains foo rats hello
:[foo = rats -> true]
:[foo = hello -> true]
corp> close foo
:[Closed Corplet foo]
corp> quit
:[Quit Corplet]

```

You can also use Corplet in scala or java projects.

### `Example`

If the word "Hello" is in the set, it takes O(5) complexity.