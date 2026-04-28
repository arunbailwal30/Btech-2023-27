%{
#include<stdio.h>
%}

%%
"//".* {}
"/*"([^*]|\*+[^*/])*\*+"/" {}
. {fprintf(yyout, "%s",yytext);}  
\n {fprintf(yyout,"\n");}
%%

int main(){

yyout = fopen("output.txt","w");
yylex();
fclose(yyout);
return 0;
}
int yywrap(){
    return 1;
}