lexer grammar MxLexer;

BlockComment: '/*' .*? '*/' -> skip;
LineComment: '//' ~[\r\n\u2028\u2029]* -> skip;

StringLiteral: '"' ('\\\\'|'\\n'|'\\"'|~[\\"])* '"';
IntLiteral: [1-9] [0-9]* | '0';

Void: 'void';
Bool: 'bool';
Int: 'int';
String: 'string';
New: 'new';
Class: 'class';
Null: 'null';
True: 'true';
False: 'false';
This: 'this';
If: 'if';
Else: 'else';
For: 'for';
While: 'while';
Break: 'break';
Continue: 'continue';
Return: 'return';

Identifier: [a-zA-Z][a-zA-Z_0-9]*;

Add: '+';
Sub: '-';
Mul: '*';
Div: '/';
Mod: '%';

Less: '<';
Greater: '>';
LessEqual: '<=';
GreaterEqual: '>=';
Equal: '==';
NotEqual: '!=';

And: '&&';
Or: '||';
Not: '!';

LeftShift: '<<';
RightShift: '>>';
BitAnd: '&';
BitOr: '|';
BitXor: '^';
BitNot: '~';

Assign: '=';
Increment: '++';
Decrement: '--';

Dot: '.';
LeftParen: '(';
RightParen: ')';
LeftBracket: '[';
RightBracket: ']';

LeftBrace: '{';
RightBrace: '}';
Comma: ',';
Semicolon: ';';
Question: '?';
Colon: ':';

Whitespace: [ \t\u000B\u000C\u00A0]+ -> skip;
Newline: ( '\r' '\n'? | [\n\u2028\u2029]) -> skip;