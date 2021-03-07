Results of HashTable H and I by running the test found in WordFreqInfo.java.

Table H used the default Java hashcode function.
Table I used a really crappy hashcode function that I designed to be this way.

H size is: 2999
I size is: 2999
H : Elapsed insert time: 21
I : Elapsed insert time: 61
H : Elapsed find time: 2
I : Elapsed find time: 46
H : average Probes: 2
I : average Probes: 857
H Array size is: 6947
I Array size is: 6947

As we can see from above, this shows a significant difference in the number of probes. We can see that the average probe
count for table H is around 2 while for table I it is 857. We can see that this would lead to a near constant time for
the finding of a given object in the hash table. In my poorly designed hashcode function, there was a lot of clustering.
This was on purpose to show an extreme example of clustering. Finding with this poorly designed hashcode function took
nearly 400 times more effort and 20 times as long.

In conclusion, the length of a string is not a good way to determine a hashtable position. Especially if your strings
are all designed to be nearly the same length. Using the provided Java hashcode is very effective and is able to process
very quickly. Finding times were nearly 0, and probes were close to one. This is about as good as you can get.