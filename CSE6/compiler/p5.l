%{
#include<stdio.h>

%}

%%
(auto|switch|int|char|float|double|return|unsigned|signed|void) {printf("%s is a keyword\n",yytext);}
[a-bA-Z_][a-zA-Z0-9_]* {printf("%s is an identifier\n",yytext);}
[,;] {printf("%s is a separator\n",yytext);}
[+-/*%^](=)? {printf("%s is an operator\n",yytext);}
[0-9]+ {printf("%s is a constant\n",yytext);}
%%

int main(){
    printf("Enter text: ");
    yylex();
    return 0;
}
int yywrap(){
    return 1;
}