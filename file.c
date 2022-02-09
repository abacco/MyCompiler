#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include<stdlib.h>
#include <math.h>

#define LENGTH  2048

bool enemy_lost=false;
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
char* askName(char *enemy_name)
{
	char *user_name=malloc(sizeof(char)*LENGTH);
	printf("Dimmi il tuo nome ");
	scanf("%s",user_name);
	printf("Il tuo nemico è ? ");
	scanf("%s",enemy_name);
	return user_name;
	
}
void startDialogue(char *enemy_name, char *user_name)
{
	printf("%s\n",concat(concat(concat("benvenuti al match tra ",enemy_name)," e "),user_name));
	printf("%s\n","Ora inizia il combattimento! ");
	
}
void fight(char *enemy_name, int damage, int *enemy_life)
{
	*enemy_life = *enemy_life-damage;
	printf("%s\n",concatCI(concat(concatCI(concat(enemy_name," ha incassato un attacco ed ha preso "),damage)," danni, ora la sua vita è "),*enemy_life));
	checkEnemyLife(copy_string(enemy_name),*enemy_life);
	
}
void checkEnemyLife(char *enemy_name, int enemy_life)
{
	if(enemy_life<=0){
		enemy_lost = true;
		printf("%s\n",concat(enemy_name," è stato sconfitto "));
		
	}
else{
	printf("%s\n","il nemico è ancora in piedi!");
		
	}

	
}
int selectMove()
{
	int move;
	printf("%s\n","Scegli la tua mossa! :");
	printf("%s\n","1 pugno - 2");
	printf("%s\n","2 calcio - 5");
	printf("%s\n","3 insulto - 0");
	printf("");
	scanf("%d",&move);
	if(move==1){
		return 2;
		
	}

	if(move==2){
		return 5;
		
	}

	if(move==3){
		return 0;
		
	}
else{
	printf("%s\n","Non fai nulla ");
		return 0;
		
	}

	
}

int main(void){
	char *enemy_name=malloc(sizeof(char)*LENGTH),  *user_name=malloc(sizeof(char)*LENGTH);
	int enemy_life=10;

	strcpy(user_name,askName(enemy_name));
	startDialogue(copy_string(enemy_name),copy_string(user_name));
	while(!(enemy_lost)){
		int damage;
		damage = selectMove();
		fight(copy_string(enemy_name),damage,&enemy_life);
		
	}

	printf("%s\n","battaglia conclusa");
	 return 0;
}

