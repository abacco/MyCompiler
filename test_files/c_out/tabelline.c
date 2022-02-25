#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include<stdlib.h>
#include <math.h>

#define LENGTH  2048


char* concatCD(char *s1, double i) {
char *s=malloc(sizeof(char) * LENGTH);
sprintf(s, "%s%lf", s1, i);
return s;
}
char* concatDC(double i, char *s1) {
char *s=malloc(sizeof(char) * LENGTH);
sprintf(s, "%lf%s", i, s1);
return s;
}
char* concatCI(char *s1, int i) {
char *s=malloc(sizeof(char) * LENGTH);
sprintf(s, "%s%d", s1, i);
return s;
}
char* concatIC(int i, char *s1) {
char *s=malloc(sizeof(char) * LENGTH);
sprintf(s, "%d%s", i, s1);
return s;
}
char* concat(char *str1, char *str2)
{
char *s=malloc(sizeof(char) * LENGTH);
strcat(s, str1);
strcat(s, str2);

return s;
}
char* copy_string(char *str1)
{
char *s=malloc(sizeof(char) * LENGTH);
strcat(s, str1);

return s;
}
void stampatab(char *messaggio)
{
	printf("%s\t",messaggio);
	
}

int main(void){
	int i,  j;
	i = 1;
	while(i<=10){
		j = 1;
		while(j<=10){
		stampatab(concatCI("",( i*j)));
		j = j+1;
		
	}

		printf("%s\n","");
		i = i+1;
		
	}

	 return 0;
}

