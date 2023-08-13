#define bool _Bool

void print(char* str){printf("%s",str);}
void println(char* str){printf("%s\n",str);}
void printInt(int n){printf("%d",n);}
void printlnInt(int n){printf("%d\n",n);}
int getInt(){int n;scanf("%d",&n);return n;}
char* getString(){char* str=malloc(256);scanf("%s",str);return str;}
char* toString(int n){char* str=malloc(16);sprintf(str,"%d",n);return str;}

int string_parseInt(char* str){int n;sscanf(str,"%d",&n);return n;}
int string_ord(char* str,int n){return str[n];}
int string_length(char* str){return strlen(str);}
char* string_substring(char* str,int left,int right){
  int n=right-left;
  char* ret=malloc(n+1);
  memcpy(ret,str+left,n);
  ret[n]='\0';
  return ret;
}

char* string_add(char* lhs, char* rhs){
  int n0=strlen(lhs),n1=strlen(rhs);
  char* str=malloc(n0+n1+1);
  memcpy(str,lhs,n0);
  memcpy(str+n0,rhs,n1);
  str[n0+n1+1]='\0';
  return str;
}
bool string_lt(char* lhs, char* rhs){
  return strcmp(lhs,rhs) < 0;
}
bool string_gt(char* lhs, char* rhs){
  return strcmp(lhs,rhs) > 0;
}
bool string_le(char* lhs, char* rhs){
  return strcmp(lhs,rhs) <= 0;
}
bool string_ge(char* lhs, char* rhs){
  return strcmp(lhs,rhs) >= 0;
}
bool string_eq(char* lhs, char* rhs){
  return strcmp(lhs,rhs) == 0;
}
bool string_ne(char* lhs, char* rhs){
  return strcmp(lhs,rhs) != 0;
}