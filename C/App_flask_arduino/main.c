#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include "arduino-serial-lib.h"
#include <curl/curl.h>



struct string {
    char *ptr;
    size_t len;
};

void init_string(struct string *s) {
    s->len = 0;
    s->ptr = malloc(s->len+1);
    if (s->ptr == NULL) {
        fprintf(stderr, "malloc() failed\n");
        exit(EXIT_FAILURE);
    }
    s->ptr[0] = '\0';
}

size_t writefunc(void *ptr, size_t size, size_t nmemb, struct string *s)
{
    size_t new_len = s->len + size*nmemb;
    s->ptr = realloc(s->ptr, new_len+1);
    if (s->ptr == NULL) {
        fprintf(stderr, "realloc() failed\n");
        exit(EXIT_FAILURE);
    }
    memcpy(s->ptr+s->len, ptr, size*nmemb);
    s->ptr[new_len] = '\0';
    s->len = new_len;

    return size*nmemb;
}

void delimiterString(char* tampon){
    char buffer[512];
    int i=0;
    int length=strlen(tampon);
    for(i=0;i<512 && i<length;i++){
        if(tampon[i]!='\n') {
            buffer[i] = tampon[i];
        }else{
            break;
        }
    }
    buffer[i]='\0';
    memcpy(tampon,buffer,i+1);
}

void sendJson(char* a_envoyer){

    CURL* curl;
    CURLcode res;
    char* jsonObj[512];
    struct string retourRequete;
    init_string(&retourRequete);

    sprintf(jsonObj,"{ \"time\" : \" %s \" }",a_envoyer);

    curl_global_init(CURL_GLOBAL_ALL);

    curl = curl_easy_init();
    if(curl) {
        struct curl_slist *headers = NULL;
        curl_slist_append(headers, "Accept: application/json");
        curl_slist_append(headers, "Content-Type: application/json");
        curl_slist_append(headers, "charsets: utf-8");

        curl_easy_setopt(curl, CURLOPT_URL, "https://ponderosaproject.herokuapp.com/posttest");

        curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "POST");
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, jsonObj);
        curl_easy_setopt(curl, CURLOPT_USERAGENT, "libcrp/0.1");


        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, writefunc);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &retourRequete);
        res = curl_easy_perform(curl);
        if(res != CURLE_OK)
            fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
    }


    /* always cleanup */
    curl_easy_cleanup(curl);
    curl_global_cleanup();


    printf("\nRetour:%s",retourRequete.ptr);

}


int main (int argc, char *argv[]){


    char buffer[1024];

    char *portname = "/dev/ttyACM0";
    int fd = open (portname, O_RDWR | O_NOCTTY | O_SYNC);


    while(1){
        serialport_read_until(fd, buffer,'\0',1024,2000);
        if(strlen(buffer)>0){
                delimiterString(buffer);
                //printf("\nchaine:%s",buffer);
                //buffer[strlen(buffer)-1]='\0';
                sendJson(buffer);
            }
        }

    return 0;
}