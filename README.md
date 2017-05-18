# Corplet

A binary-database system designed for tagging words under catgeories.

`Corplet` is a system that stores words or phrases as strings in a binary-trie structure. These are meant to store words in very large sets, with no record order. This binary data structure can check if a word belongs to the set in O(n) time, where n is the number of Characters in the string.

### `Example`

If the word "Hello" is in the set, it takes O(5) complexity.