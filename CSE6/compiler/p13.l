%{

#include<stdio.h>

%}

%%

^[a-zA-Z0-9]*([-_.+][a-zA-Z0-9]+)*\@[a-z]+(\.[a-z]{2,})+$ {printf("valid email\n");}
.* {printf("not a valid email\n");}

%%

int main(){
    printf("Enter string: ");
    yylex();
    return 0;
}

int yywrap(){
    return 1;
}