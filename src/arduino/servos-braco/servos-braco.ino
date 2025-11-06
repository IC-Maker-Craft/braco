#include <ESP32Servo.h>

Servo servo18;
Servo servo19;
Servo servo21;

Servo servox;
Servo servoy;
Servo servoz;


void setup() {
  Serial.begin(9600);

  // attach apenas os 3 pinos permitidos
  servo18.attach(18); //garra
  servo19.attach(19); //punho
  servo21.attach(21); //cu to ve lo

  servox.attach(15); // ombro
  servoy.attach(4);  // Base
  servoz.attach(5);  //

  servo18.write(0);
  servo19.write(0);
  servo21.write(0);

  servoz.write(0);
  servoy.write(0);
  servox.write(90);
}

void loop() {
  if (Serial.available() > 0) {
    String mensagem = Serial.readStringUntil('\n');
    mensagem.trim();

    int separador = mensagem.indexOf(':');
    if (separador != -1) {
      int pino = mensagem.substring(1, separador).toInt();
      int valor = mensagem.substring(separador + 1).toInt();

      Serial.print("Pino: ");
      Serial.println(pino);
      Serial.print("Valor: ");
      Serial.println(valor);


      if (valor >= 0 && valor <= 180) {
        switch (pino) {
          case 18:
            servo18.write(valor);
            break;
          case 19:
            servo19.write(valor);
            break;
          case 21:
            servo21.write(valor);
            break;

          case 15:
            servoz.write(valor);
            break;
          case 4:
            servoy.write(valor);
            break;
          case 5:
            servox.write(valor);
            break;


          default:
            Serial.println("Pino inválido.");
        }
      } else {
        Serial.println("Valor inválido.");
      }
    }
  }
}