%{
#include<stdio.h>
%}

%%
"int"|"float"|"char"|"double"|"if"|"else"|"while"|"for"|"return"|"void"|"switch"|"case"|"break"|"continue" { printf("Keyword\n"); }
[a-zA-Z_]+[a-zA-Z0-9_]* {printf("identifier\n");}
\".*\" {printf("String literal\n");}
[0-9]+(\.?[0-9]+) {printf("constant\n");}
\'.\'  {printf("character literal\n");}
.* {printf("not recognized");}
%%
int main(){
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}