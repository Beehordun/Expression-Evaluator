# Expression Evaluator

An expression evaluator using Recursive-descent parser.

## Grammar

E &rarr; T  (+T)*    Addition production rule  </br> 
E &rarr; T  (-T)*    Subtraction production rule </br>
T &rarr; F  (F)*    Multiplication production rule </br>
T &rarr; F  (/F)*    Division production rule </br>
F &rarr; n | ( E )   Multiplicative expression operand

