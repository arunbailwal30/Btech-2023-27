%{
#include<stdio.h>
%}
%s A B C D DEAD
%%
<INITIAL>[0-9]+ {BEGIN A;}
<INITIAL>[A-Za-z_] {BEGIN C;}
<INITIAL>\n {printf("Not Accepted\n");}
<A>[0-9]+ {BEGIN A;}
<A>"." {BEGIN B;}
<A>[^.\n] {BEGIN DEAD;}
<A>\n  {printf("This is a integer\n"); BEGIN INITIAL;}
<B>[0-9] {BEGIN D;}
<B>\n {printf("NOt Accepted\n"); BEGIN INITIAL;}
<C>[a-zA-z_0-9] {BEGIN C;}
<C>\n {printf("This is a identifier\n"); BEGIN INITIAL;}
<C>. {BEGIN DEAD;}
<D>[0-9] {BEGIN D;}
<D>\n {printf("This is a float\n"); BEGIN INITIAL;}
<D>. {BEGIN DEAD;}
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