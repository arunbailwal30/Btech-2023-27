%{
#include<stdio.h>
%}
%s A B C
%%
<INITIAL>a {BEGIN A;}
<INITIAL>b {BEGIN B;}
<INITIAL>\n {printf("Accepted\n"); BEGIN INITIAL;}
<A>a {BEGIN INITIAL;}
<A>b {BEGIN C;}
<A>\n {printf("Not accepted\n"); BEGIN INITIAL;}
<B>a {BEGIN C;}
<B>b {BEGIN INITIAL;}
<B>\n {printf("Not accepted\n"); BEGIN INITIAL;}
<C>a {BEGIN B;}
<C>b {BEGIN A;}
<C>\n {printf("Not accepted\n"); BEGIN INITIAL;}
%%
int main(){
    printf("Enter string : ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}