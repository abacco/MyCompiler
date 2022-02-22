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
int print_menu()
{
	int choose;
	printf("%s\n","\n\rScegli l\'operazione da svolgere per continuare");
	printf("%s\n","\t(1) Somma di due numeri\r");
	printf("%s\n","\t(2) Moltiplicazione di due numeri");
	printf("%s\n","\t(3) Divisione intera fra due numeri positivi");
	printf("%s\n","\t(4) Elevamento a potenza");
	printf("%s\n","\t(5) Successione di Fibonacci (ricorsiva)");
	printf("%s\n","\t(6) Successione di Fibonacci (iterativa)");
	printf("%s\n","\t(0) Esci");
	printf("--> ");
	scanf("%d",&choose);
	return choose;
	
}
void do_sum()
{
	double op2,  op1;
	printf("%s\n","\n(1) SOMMA\n");
	printf("Inserisci il primo operando: ");
	scanf("%lf",&op1);
	printf("Inserisci il secondo operando: ");
	scanf("%lf",&op2);
	printf("%s\n","");
	printf("%s\n",concatCD(concat(concatCD(concat(concatCD("La somma tra ",op1)," e "),op2)," vale "),op1+op2));
	
}
void do_mul()
{
	double op2,  op1;
	printf("%s\n","\n(2) MOLTIPLICAZIONE");
	printf("\nInserisci il primo operando: ");
	scanf("%lf",&op1);
	printf("Inserisci il secondo operando: ");
	scanf("%lf",&op2);
	printf("%s\n","");
	printf("%s\n",concatCD(concat(concatCD(concat(concatCD("La moltiplicazione tra ",op1)," e "),op2)," vale "),op1*op2));
	
}
void do_div_int()
{
	int op2,  op1;
	printf("%s\n","\n(3) DIVISIONE INTERA");
	printf("\nInserisci il primo operando: ");
	scanf("%d",&op1);
	printf("Inserisci il secondo operando: ");
	scanf("%d",&op2);
	printf("%s\n","");
	printf("%s\n",concatCI(concat(concatCI(concat(concatCI("La divisione intera tra ",op1)," e "),op2)," vale "),((int)op1/op2)));
	
}
void do_pow()
{
	double op2,  op1;
	printf("%s\n","\n(4) POTENZA");
	printf("\nInserisci la base: ");
	scanf("%lf",&op1);
	printf("Inserisci l esponente: ");
	scanf("%lf",&op2);
	printf("%s\n","");
	printf("%s\n",concatCI(concat(concatCD(concat(concatCD("La potenza di ",op1)," elevato a "),op2)," vale "),pow(op1,op2)));
	
}
int recursive_fib(int n)
{
	if(n==0){
		return 0;
		
	}

	if(n==1){
		return 1;
		
	}

	return recursive_fib(n-1)+recursive_fib(n-2);
	
}
int iterative_fib(int n)
{
	if(n==0){
		return 0;
		
	}

	if(n==1){
		return 1;
		
	}

	if(n>2){
		int res=1,  prev=0,  i=3;
		while(i<=n){
		int tmp=res;
		res = res+prev;
		prev = tmp;
		i = i+1;
		
	}

		return res;
		
	}

	return -(1);
	
}
void do_fib(bool recursive)
{
	int n;
	char *message=malloc(sizeof(char)*LENGTH);
	printf("%s\n","\n(5) FIBONACCI");
	printf("\nInserisci n: ");
	scanf("%d",&n);
	printf("%s\n","");
	strcpy(message,concat(concatCI("Il numero di Fibonacci in posizione ",n)," vale "));
	if(recursive){
		strcpy(message,concatCI(message,recursive_fib(n)));
		
	}
else{
	strcpy(message,concatCI(message,iterative_fib(n)));
		
	}

	printf("%s\n",message);
	
}
void do_operation(int choose)
{
	if(choose==1){
		do_sum();
		
	}
else{
	if(choose==2){
		do_mul();
		
	}
else{
	if(choose==3){
		do_div_int();
		
	}
else{
	if(choose==4){
		do_pow();
		
	}
else{
	if(choose==5){
		do_fib(true);
		
	}
else{
	if(choose==6){
		do_fib(false);
		
	}

		
	}

		
	}

		
	}

		
	}

		
	}

	
}
void print_continue(bool *continues)
{
	char *in=malloc(sizeof(char)*LENGTH);
	printf("Vuoi continuare? (s/n) --> ");
	scanf("%s",in);
	if((strcmp(in,"s")==0)){
		*continues = true;
		
	}
else{
	*continues = false;
		
	}

	
}

int main(void){
	bool continues=true;
int choose=0;

	while(continues){
		choose = print_menu();
		if(choose==0){
		continues = false;
		
	}
else{
	do_operation(choose);
		print_continue(&continues);
		
	}

		
	}

	 return 0;
}

