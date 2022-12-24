#include "FirebaseESP8266.h"
#include <ESP8266WiFi.h>



#define FIREBASE_HOST "voiceautomation-8d4cc-default-rtdb.asia-southeast1.firebasedatabase.app"  
#define FIREBASE_AUTH "jPTXvHEUTM1BnCgglW65n3hRwcUg1PbVuFfmjPXB"

#define WIFI_SSID "IOT_Voice"     
#define WIFI_PASSWORD "11111111" 


FirebaseData firebaseData,loadData;
FirebaseJson json;



//Receiving data
String readData(String field){
if (Firebase.getString(loadData, "/Load/"+field)){
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
  pinMode(D3,INPUT_PULLUP);//light input
  pinMode(D1,INPUT_PULLUP);//pump input
  
  pinMode(D6,OUTPUT);//Light
  pinMode(D7,OUTPUT);//Pump
  
}




void loop() {

    digitalWrite(D7,!digitalRead(D3) || readData("Light")=="1");
    delay(100);
    digitalWrite(D6,!digitalRead(D1) || readData("Pump")=="1");
    delay(100);
 
}
