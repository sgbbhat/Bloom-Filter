# Bloom-Filter
An efficient data structure that uses hashing to test if an object is a member of a large set. 

Bloom Filters are probabilistic data structures. That means Suppose that we use a Bloom Filter to represent a set of objects. 
We ask if an object is in the set. If the Bloom Filter answers ‘‘no,’’ then the object is definitely not in the set. 
If it answers ‘‘yes,’’ then the object is in the set only with some (known) probability. 

Because Bloom Filters are probabilistic, we can’t use them for applications that require reliable answers. 
However, we can use them in spelling checkers. To do that, we represent a large set of correctly spelled words as a Bloom Filter. 
If the Bloom Filter says that a word is not in the set, then the word is spelled incorrectly. 
If it says that the word is in the set, then the word may or may not be spelled correctly—it may make a mistake. 
However, we can design the Bloom Filter so that the probability of mistakes is small. All we need now is to decide 
what words should be in the set. 

Basic English is a proposed international language invented in 1930 by Charles K. Ogden. 
It uses 850 words from ordinary English, and a simplified English grammar. 
Ogden intended Basic English to be easy for non-English speakers to learn and use, but there has been little 
interest in it since the 1940’s. However, if it had become popular, then today we would need programs that 
find spelling errors in Basic English text. For this project, you must write a Java class that uses a Bloom Filter 
to detect misspelled Basic English words. 

