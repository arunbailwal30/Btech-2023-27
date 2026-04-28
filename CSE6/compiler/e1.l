%{
#include<stdio.h>
%}
%s A B DEAD
%%
<INITIAL>a {BEGIN A;}
<INITIAL>b {BEGIN B;}
<INITIAL>\n {BEGIN INITIAL;}
<INITIAL>. {BEGIN DEAD;}
<A>b {BEGIN B;}
<A>a {BEGIN DEAD;}
<A>\n {printf("Not Accepted\n"); BEGIN INITIAL;}
<B>a {BEGIN A;}
<B>b {BEGIN B;}
<B>\n {printf("Accepted \n"); BEGIN INITIAL;}
<DEAD>\n {printf("Not Accepted\n"); BEGIN INITIAL;}
<DEAD>. {BEGIN DEAD;}
%%
int main(){
    printf("Enter string: ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}