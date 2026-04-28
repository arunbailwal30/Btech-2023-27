%{
#include<stdio.h>
%}

%%
 
^[a-zA-Z_][a-zA-Z0-9_]* {printf("this is valid identifier: %s\n", yytext);}
.* {printf("this is not a identifier: %s\n", yytext);}
%%
int main(){
    printf("Enter the text: ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}