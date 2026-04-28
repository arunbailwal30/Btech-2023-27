%{
#include<stdio.h>
%}
%s A B DEAD
%%
<INITIAL>[0-9]+ {BEGIN A;}
<INITIAL>[^0-9\n]+ {BEGIN DEAD;}
<INITIAL>\n {printf("Not Accepted\n");}
<A>"." {BEGIN B;}
<A>[^.\n] {BEGIN DEAD;}
<A>\n  {printf("This is a integer\n");}
<B>[0-9]+ {BEGIN B;}
<B>\n {printf("This is a float\n");}
<DEAD>[^\n] {BEGIN DEAD;}
<DEAD>\n {printf("Not accepted\n"); BEGIN INITIAL;}
%%
int main(){
    printf("Enter string : ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}