# 🦾 Minecraft Robotic Arm Mod (Fabric)

Este projeto é um mod para Minecraft (usando a Fabric API) com o objetivo de controlar **um braço robótico real com 3 servos motores**, usando comandos in-game e comunicação via **porta serial com Arduino**.

---

## 🎯 Objetivo

Criar uma ponte entre o Minecraft e o mundo físico, permitindo que jogadores controlem um **braço robótico real** utilizando **Command Blocks**. O mod envia os ângulos desejados para cada servo motor do braço robótico diretamente para um **Arduino**, por meio de uma conexão **Serial (USB)**.

---

## ⚙️ Funcionamento

- O braço robótico possui **3 servos**: base, meio e ponta.
- O controle é feito via **comandos Minecraft** do tipo:

```bash
/braco <id> <modo>
```

* Onde:

  * `id` representa o número do servo (1, 2 ou 3).
  * `modo` pode ser `add` (aumentar o ângulo) ou `remove` (diminuir o ângulo).
* A cada comando, o mod atualiza o valor interno do ângulo do servo e envia o novo valor para o Arduino via **porta serial**.

---

## 🔌 Comunicação Serial

A comunicação com o Arduino é feita utilizando a biblioteca **jSerialComm**:

* O mod abre uma conexão com a porta serial configurada.
* Sempre que um comando é executado, o mod envia um sinal com o seguinte formato:

```
S<id>:<graus>
```

Exemplo:

```
S1:90
```

> Isso indica que o servo 1 deve ser movido para 90 graus.

---

## 🧱 Integração com o Minecraft

* O mod registra comandos personalizados com a Fabric API.
* Pode ser utilizado **em Command Blocks** para automações e construções.
* Feedback dos comandos pode ser enviado via `sendMessage()` para feedback visual.

---

## 🛠️ Tecnologias Utilizadas

* Fabric API
* Minecraft Brigadier (sistema de comandos)
* jSerialComm (comunicação Serial Java)
* Arduino (com sketch para ler os comandos e mover os servos)

---

## 🧪 Exemplo de Uso

```bash
/braco 1 add     # Aumenta o ângulo do servo 1
/braco 2 remove  # Diminui o ângulo do servo 2
```

---

## 🚧 Status

🔧 Em desenvolvimento — funcionalidades básicas de envio e comando já implementadas.

---


## 💡 Futuras Melhorias

* Interface gráfica in-game para controle mais intuitivo.
* Limites configuráveis de rotação por servo.
* Suporte a múltiplos dispositivos/portas serial.
* Feedback do Arduino de volta para o jogo.


