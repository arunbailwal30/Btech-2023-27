%{
#include<stdio.h>
%}
%s A B DEAD
%%
<INITIAL>a {BEGIN A;}
<INITIAL>b {BEGIN DEAD;}
<INITIAL>\n {printf("Not accepted \n");}
<A>a {BEGIN A;}
<A>b {BEGIN B;}
<A>\n {printf("Not accepted \n");}
<B>a {BEGIN A;}
<B>b {BEGIN B;}
<B>\n {printf("Accepted\n");}
<DEAD>a {BEGIN DEAD;}
<DEAD>b {BEGIN DEAD;}
<DEAD>\n { printf("Invalid \n"); BEGIN INITIAL;}
%%
int main(){
    printf("Enter string : ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}