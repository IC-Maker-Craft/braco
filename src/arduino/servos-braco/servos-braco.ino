#include <ESP32Servo.h>

Servo servo18;
Servo servo19;
Servo servo21;

void setup() {
  Serial.begin(9600);

  // attach apenas os 3 pinos permitidos
  servo18.attach(18);
  servo19.attach(19);
  servo21.attach(21);

  servo18.write(0);
  servo19.write(0);
  servo21.write(0);
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
          default:
            Serial.println("Pino inválido.");
        }
      } else {
        Serial.println("Valor inválido.");
      }
    }
  }
}
