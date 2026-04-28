%{
#include <stdio.h>
%}

%%
"<"[^>]*">"   { fprintf(yyout, "%s\n", yytext); } 
.|\n          {}
%%

int main() {

    yyout = fopen("Output.txt", "w");
    if(!yyout) {
        perror("Error creating output text file");
        fclose(yyin);
        return 1;
    }

    yylex();   

    fclose(yyout);
    return 0;
}
int yywrap(){
    return 1;
}