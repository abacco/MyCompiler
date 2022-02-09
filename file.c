#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include<stdlib.h>
#include <math.h>

#define LENGTH  2048

int counter=0;
char* concatCD(char *s1, double i) {
char *s=malloc(sizeof(char) * LENGTH);
sprintf(s, "%s%.2f", s1, i);
return s;
}
char* concatDC(double i, char *s1) {
char *s=malloc(sizeof(char) * LENGTH);
sprintf(s, "%.2f%s", i, s1);
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
void inserisci_valore(double value, double *somma)
{
	*somma = *somma+value;
	counter = counter+1;
	
}
bool controlla_valore(double value)
{
	if(value==0.0){
		return true;
		
	}
else{
	return false;
		
	}

	
}

int main(void){
	double somma=0;
	double value=0;
	bool continua=true;
	printf("%s\n","Inserisci una sequenza di valori.Inserendo 0 termina l inserimento e viene riportata\n la media e il numero di valori inseriti");
	while(continua){
		printf("%s\t","inserisci un valore : ");
		printf("");
	scanf("%lf",&value);
		continua = !(controlla_valore(value));
		if(continua){
		inserisci_valore(value,&somma);
		
	}

		
	}

	printf("%s\n",concatCD(concat(concatCI("Hai inserito  n° valori : ",counter)," la media è: "),somma/counter));
	 return 0;
}

