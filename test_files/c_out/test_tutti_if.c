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
int somma(int x, int y)
{
	int output=0,  i=0;
	while(i<2){
		if(i==0){
		output = output+x;
		
	}
else{
	output = output+y;
		
	}

		i = i+1;
		
	}

	return output;
	
}
int molt(int x, int y)
{
	if(y==1){
		return x;
		
	}
else{
	return somma(x,molt(x,y-1));
		
	}

	
}
void pot(int x, int y, char *result)
{
	strcpy(result,concatCI(concat(concatCI(concatIC(x,"^"),y),"="),pow(x,y)));
	
}
bool divInt(int x, int y, int *output)
{
	if(x<0){
		x = -(x);
		
	}

	if(y<0){
		y = -(y);
		
	}

	if(y==0){
		printf("%s\n","impossibile dividere per zero.");
		return false;
		
	}

	*output = ((int)x/y);
	return true;
	
}
bool fibonacci(int n, int *output)
{
	int ultimo=1;
bool flag=false;
int penultimo=0;
int i=2;

	int f;
	if(n==0){
		*output = 0;
		flag = true;
		
	}

	if(n==1){
		*output = 1;
		flag = true;
		
	}

	if(n>1){
		while(i<=n){
		f = somma(penultimo,ultimo);
		penultimo = ultimo;
		ultimo = f;
		i = i+1;
		
	}

		*output = f;
		flag = true;
		
	}

	if(n<0){
		printf("%s\n",concat(concatCI("Errore! ",n)," e\' infieriore a 0"));
		flag = false;
		
	}

	return flag;
	
}

int main(void){
	int operando1,  operazioneScelta,  operando2,  resultFibo,  resultDivInt;
	char *resultPot=malloc(sizeof(char)*LENGTH),  *ans=malloc(sizeof(char)*LENGTH);strcpy(ans,"si");

	while((strcmp(ans,"si")==0)){
		printf("%s\n","Operazioni disponibili:");
		printf("%s\n","1 - somma");
		printf("%s\n","2 - moltiplicazione");
		printf("%s\n","3 - potenza");
		printf("%s\n","4 - divisione intera numeri positivi");
		printf("%s\n","5 - fibonacci");
		printf("inserire un numero da 1 a 5:");
	scanf("%d",&operazioneScelta);
		while(operazioneScelta<1||operazioneScelta>5){
		printf("errore... inserire una scelta valida:");
	scanf("%d",&operazioneScelta);
		
	}

		if(operazioneScelta!=5){
		printf("inserire il primo operando:");
	scanf("%d",&operando1);
		printf("inserire il secondo operando:");
	scanf("%d",&operando2);
		
	}
else{
	printf("inserire n:");
	scanf("%d",&operando1);
		
	}

		if(operazioneScelta==1){
		printf("%s\n",concatCI(concat(concatCI(concatIC(operando1," + "),operando2)," = "),somma(operando1,operando2)));
		
	}

		if(operazioneScelta==2){
		printf("%s\n",concatCI(concat(concatCI(concatIC(operando1," * "),operando2)," = "),molt(operando1,operando2)));
		
	}

		if(operazioneScelta==3){
		pot(operando1,operando2,resultPot);
		printf("%s\n",resultPot);
		
	}

		if(operazioneScelta==4){
		if(( divInt(operando1,operando2,&resultDivInt))){
		printf("%s\n",concatCI(concat(concatCI(concatIC(operando1," divInt "),operando2)," = "),resultDivInt));
		
	}

		
	}

		if(operazioneScelta==5){
		if(( fibonacci(operando1,&resultFibo))){
		printf("%s\n",concatCI(concat(concatCI("fibonacci(",operando1),") = "),resultFibo));
		
	}

		
	}

		printf("vuoi continuare? (si/no):\t");
	scanf("%s",ans);
		
	}

	printf("%s\n","fine.");
	 return 0;
}

