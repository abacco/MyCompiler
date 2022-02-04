#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include<stdlib.h>

#define LENGTH  2048
char str1[LENGTH], str2[LENGTH];

int c=1;
char* concatCD(char *s1, double i) {
char *s;
sprintf(s, "%s%.2f", s1, i);
return s;
}char* concatCD(double i, char *s1) {
char *s;
sprintf(s, "%.2f%s", i, s1);
return s;
}char* concatCI(char *s1, int i) {
char *s;
sprintf(s, "%s%d", s1, i);
return s;
}char* concatIC(int i, char *s1) {
char *s;
sprintf(s, "%d%s", i, s1);
return s;
}
double sommac(char *size, double b, int a)
{
	double result;
	result = a+b+c;
	if(result>100){
		char *valore="grande";
		size = valore;
		
	}
else{
	char *valore="piccola";
		size = valore;
		
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
	char *taglia;
	char *ans="no";
	double risultato=sommac(a,b,(*taglia));
	stampa(strcat(strcat(concatCI(strcat(concatCD(strcat(concatCI("la somma di ",a)," e "),b)," incrementata di "),c)," è "),taglia))
	stampa(concatCD("ed è pari a ",risultato))
	printf("%s\t","vuoi continuare? (si/no)");
	printf();
	scanf("%s",&ans);
	while(ans=="si"){
		printf("inserisci un intero:");
	scanf("%d",&a);
		printf("inserisci un reale:");
	scanf("%f",&b);
		risultato = sommac(a,b,(*taglia));
		stampa(strcat(strcat(concatCI(strcat(concatCD(strcat(concatCI("la somma di ",a)," e "),b)," incrementata di "),c)," è "),taglia))
		stampa(concatCD(" ed è pari a ",risultato))w
		printf("vuoi continuare? (si/no):\t");
	scanf("%s",&ans);
		
	}

	printf("%s\n","");
	printf("%s","ciao");
	 return 0;
}

