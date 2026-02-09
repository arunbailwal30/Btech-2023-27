%{
#include<stdio.h>


%}

%%
[0-9]*[02468]$ {printf("This is an even number");}
[0-9]*[13579]$ {printf("this is a odd number");}

%%

int main(){
    printf("Enter digit: ");
    yylex();
    return 0;
}
int yywrap(){return 1;}