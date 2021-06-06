#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>



#define FIREBASE_HOST "voice-control-home-autom-dce60-default-rtdb.asia-southeast1.firebasedatabase.app"  //Database link
#define FIREBASE_AUTH "SZ8Ykj6AuNued4AjZxUHv7eNjHn27pNSu5IlDaTH"  //Database secrate

#define WIFI_SSID "Roboment"      //Router name
#define WIFI_PASSWORD "roboment@2018"  //Router password


FirebaseData firebaseData,loadData;
FirebaseJson json;



//Receiving data
String readData(String field){
if (Firebase.getString(loadData, "/"+field)){
    return loadData.stringData();
  }
}



void initFire(){
  pinMode(D4,OUTPUT);
  Serial.begin(9600);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  
  while (WiFi.status() != WL_CONNECTED)
  {
    digitalWrite(D4,0);
    Serial.print(".");
    delay(200);
    digitalWrite(D4,1);
    Serial.print(".");
    delay(200);
    
  }
  
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
}














void setup() {
  
  Serial.begin(9600);
  pinSet();
  initFire();

}



void pinSet(){
  pinMode(D7,OUTPUT);//Light
  pinMode(D8,OUTPUT);
  
}




void loop() {

  
    if(readData("Light")=="1"){
      
      Serial.print("Light is on ");
      digitalWrite(D7,1);
      
    }else{

      Serial.print("Light is off ");
      digitalWrite(D7,0);
      
    }


      
    if(readData("Fan")=="1"){
      
      Serial.println("Fan is on");
      digitalWrite(D8,1);
      
    }else{

      Serial.println("Fan is off");
      digitalWrite(D8,0);
      
    }


   
  
}
