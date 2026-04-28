    %{
    #include<stdio.h>
    int lines=0, spaces =0, tabs=0, chars=0, letters=0,name = 0;
    %}
    %%
    " " {spaces++;}
    \t {tabs++;}
    \n {lines++;}
    . {chars++;}
    %%
    int main(){
        printf("Enter the text: ");
        yylex();
        printf("Lines: %d\nSpaces: %d\nTabs: %d\nChars: %d\n",lines,spaces,tabs,chars);
        return 0;
    }
    int yywrap(){
        return 1;
    }