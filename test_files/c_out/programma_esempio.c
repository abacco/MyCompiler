#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include<stdlib.h>
#include <math.h>

#define LENGTH  2048

int c=1;

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
double sommac(int a, double b, char *size)
{
	double result;
	result = a+b+c;
	if(result>100){
		char *valore=malloc(sizeof(char)*LENGTH);
strcpy(valore,"grande");

		strcpy(size,valore);
		
	}
else{
	char *valore=malloc(sizeof(char)*LENGTH);
strcpy(valore,"piccola");

		strcpy(size,valore);
		
	}

	return result;
	
}
void stampa(char *messaggio)
{
	int i=1;
	while(i<=4){
		int incremento=1;

		printf("%s\n","");
		i = i+incremento;
		
	}

	printf("%s\n",messaggio);
	
}

int main(void){
	int a=1;
double b=2.2;

	char *taglia=malloc(sizeof(char)*LENGTH);
	char *ans=malloc(sizeof(char)*LENGTH);
strcpy(ans,"no");

	double risultato=sommac(a,b,taglia);
	stampa(concat(concat(concatCI(concat(concatCD(concat(concatCI("la somma di ",a)," e "),b)," incrementata di "),c)," è "),taglia));
	stampa(concatCD("ed è pari a ",risultato));
	printf("%s\t","vuoi continuare? (si/no)");
	scanf("%s",ans);
	while((strcmp(ans,"si")==0)){
		printf("inserisci un intero:");
	scanf("%d",&a);
		printf("inserisci un reale:");
	scanf("%lf",&b);
		risultato = sommac(a,b,taglia);
		stampa(concat(concat(concatCI(concat(concatCD(concat(concatCI("la somma di ",a)," e "),b)," incrementata di "),c)," è "),taglia));
		stampa(concatCD(" ed è pari a ",risultato));
		printf("vuoi continuare? (si/no):\t");
	scanf("%s",ans);
		
	}

	printf("%s\n","");
	printf("%s","ciao");
	 return 0;
}

