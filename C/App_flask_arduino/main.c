#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include "arduino-serial-lib.h"
#include "./curl-7.54.1/include/curl/curl.h"


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






int main (int argc, char *argv[]){

    CURL *curl;
    CURLcode res;
    char buffer[1024];
    char* jsonObj = "{ \"name\" : \"Pedro\" , \"age\" : \"22\" }";
    char *portname = "/dev/ttyACM0";
    int fd = open (portname, O_RDWR | O_NOCTTY | O_SYNC);

    struct string retourRequete;
    init_string(&retourRequete);

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

    while(1){
        serialport_read_until(fd, buffer,'\0',1024,1000);
        if(strlen(buffer)>0){

                printf("\nchaine:%s",buffer);

            }
        }

    return 0;
}