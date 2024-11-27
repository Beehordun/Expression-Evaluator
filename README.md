# Expression Evaluator

An expression evaluator using Recursive-descent parsing.

## Grammar

E &rarr; T  (+T)*    Addition production  </br> 
E &rarr; T  (-T)*    Subtraction production </br>
T &rarr; F  (*F)*    Multiplication production </br>
T &rarr; F  (/F)*    Division production </br>
F &rarr; n | ( E )   Multiplicative expression operand

