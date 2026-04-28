%{
#include<stdio.h>
FILE *out;

%}

%%
[ \t]+ {fprintf(out, " ");}
[\n]+ {fprintf(out,"\n");}
. {fprintf(out, "%s", yytext);}
%%
int main(){
    FILE *fp;
    fp = fopen("input.txt", "r");
    if(!fp) printf("can't open");
    out = fopen("Output.txt", "w");
    if(!out) printf("can't open output.txt");
    yyin = fp;
    yylex();
    fclose(fp);
    fclose(out);
    return 0;
}

int yywrap(){
    return 1;
}