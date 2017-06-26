#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include "arduino-serial-lib.h"
#include <curl/curl.h>
#include <json-c/json.h>
#define NB_PREVISIONS 10

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
    memset(tampon, 0, 512);
    memcpy(tampon,buffer,i+1);
}

int sendJson(char* a_envoyer){



    char *url = "https://ponderosaproject.herokuapp.com/metrology";

    CURL* curl;
    CURLcode res;
    json_object *json;                                      /* json post body */
    json_object *timestamp;
    enum json_tokener_error jerr = json_tokener_success;    /* json parse error */
    char jsonTime[32];
    char meteo[NB_PREVISIONS][32];
    char* pch;
    char* pchTemp;//sert a isoler la donnée

    struct string retourRequete;
    init_string(&retourRequete);
    struct curl_slist *headers = NULL; /* http headers to send with request */

    pchTemp=strstr(a_envoyer,"time");

    if(pchTemp!=NULL){
        pch=strchr(a_envoyer,',');
        pchTemp=a_envoyer+4;
        memcpy( jsonTime, &pchTemp[0],&pch[0]-&pchTemp[0]);
        jsonTime[&pch[0]-&pchTemp[0]]='\0';
        printf("\ntime:%s\n",jsonTime);
        pch=pch+1;
        for(int i=0;i<NB_PREVISIONS;i++){
            pchTemp=strchr(pch,',');
            if(pchTemp==NULL){
                pchTemp=strchr(pch-1,';');
            }
            if(pchTemp==NULL){
                printf("\nerreur de formatage:%s\n",a_envoyer);
                return 1;
            }

            memcpy(meteo[i], &pch[0],&pchTemp[0]-&pch[0]);
            meteo[i][&pchTemp[0]-&pch[0]]='\0';
            pch=pchTemp+1;
        }

        /* create json object for post */
        json = json_object_new_object();

        json_object_object_add(json, "timestamp", json_object_new_string(jsonTime));

        /*Creating a json array*/
        json_object *jarray = json_object_new_array();
        json_object *weather[NB_PREVISIONS];

        //on forme l'array des prévisions météo
        for(int i=0;i<NB_PREVISIONS;i++){
            weather[i]=json_object_new_object();
            json_object_object_add(weather[i], "weather",json_object_new_string(meteo[i]));
            json_object_object_add(weather[i],"dnf",json_object_new_int(i));
            json_object_array_add(jarray,weather[i]);
        }
        json_object_object_add(json, "weather", jarray);

        curl_global_init(CURL_GLOBAL_ALL);

        curl = curl_easy_init();
        if(curl) {

            /* set content type */
            headers = curl_slist_append(headers, "charsets: utf-8");
            headers = curl_slist_append(headers, "Accept: application/json");
            headers = curl_slist_append(headers, "Content-Type: application/json");

            curl_easy_setopt(curl, CURLOPT_URL, url);

            curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "POST");
            curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
            curl_easy_setopt(curl, CURLOPT_POSTFIELDS, json_object_to_json_string(json));
            //curl_easy_setopt(curl, CURLOPT_USERAGENT, "libcrp/0.1");


            curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, writefunc);
            curl_easy_setopt(curl, CURLOPT_WRITEDATA, &retourRequete);
            res = curl_easy_perform(curl);
            if(res != CURLE_OK)
                fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
        }


        /* always cleanup */
        curl_easy_cleanup(curl);
        curl_global_cleanup();


        printf("\nMessage envoyé:%s\n",json_object_to_json_string(json));
        printf("\nRetour:%s",retourRequete.ptr);
        return 0;
    }else{
        printf("\nerreur de formatage:%s\n",a_envoyer);
        return 1;
    }


}


int main (int argc, char *argv[]){


    char buffer[1024];
    int longueurBuffer=0;

    char *portname = "/dev/ttyACM0";
    int fd = open (portname, O_RDWR | O_NOCTTY | O_SYNC);


    while(1){
        serialport_read_until(fd, buffer,'\0',1024,100);
        longueurBuffer=strlen(buffer);
        if(longueurBuffer>0){
                delimiterString(buffer);
                longueurBuffer=strlen(buffer);
                if(longueurBuffer<512) {
                    sendJson(buffer);
                }else {
                    memset(buffer, 0, 512);
                }
            }
        }

    return 0;
}