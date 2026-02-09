%{

#include<stdio.h>
int vowels=0, consonants=0;
%}

%%
[aeiouAEIOU] {vowels++;}
[a-zA-Z] {consonants++;}
. {}


%%

int main(){
    printf("Enter string: ");
    yylex();
    printf("vowels: %d\nconsonants: %d\n",vowels, consonants);
    return 0;
}
int yywrap(){
    return 1;
}