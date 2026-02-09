%{
#include<stdio.h>
%}

%%
^[+-]?[0-9]*\.[0-9]+ {printf("This is a float\n");}
^[+-]?[0-9]+ {printf("This is an integer\n");}
.* {printf("This is not a valid number\n");}

%%

int main(){
    printf("Enter any number you want : ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}