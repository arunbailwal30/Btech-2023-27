%{
#include<stdio.h>
int characters=0, words=0, spaces = 0;
%}

%%
[ \t]+ {spaces += yyleng;}
[a-zA-z0-9]+ {words++; characters += yyleng;}
. {characters++;}
%%

int main(){
    FILE *fp;
    fp = fopen("input.txt", "r");
    yyin = fp;
    yylex();
    fclose(fp);
    printf("Character: %d\nWords: %d\nSpaces: %d\n",characters,words,spaces);
    return 0;
}

int yywrap(){
    return 1;
}