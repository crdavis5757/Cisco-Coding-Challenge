# Cisco-Coding-Challenge

This is my work product for the coding challenge presented to me.

In the cisco/ folder is a maven project which produces a jar file, challenge-0.0.1.jar.

The GNode / graph portion of the challenge (parts 1 and 2) reside in the package com.crdavis.cisco.gnode
and there is a collection of JUnit tests to show that the code compiles and executes correctly.

The word counting portion of the challenge (part 3) resides in the package com.crdavis.cisco.wordcount.
There is a single class which implements the word counting command.  The command can be executed from a
command shell as

  java -jar challenge-0.0.1.jar <path.to.text.file>


