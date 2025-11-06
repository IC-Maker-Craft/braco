package com.maker.craft;
import com.fazecast.jSerialComm.SerialPort;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public class Arduino {
    private static final AtomicReference<SerialPort> sharedPort = new AtomicReference<>();

    // conecta o ardunio/servo na primeira porta usb
    public static SerialPort Connect() {
        SerialPort comPort = SerialPort.getCommPort("COM7");
        //SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 0);
        if (!comPort.openPort()) {
            throw new IllegalStateException("Erro ao abrir porta serial.");
        }
        sharedPort.set(comPort);
        return comPort;
    }

    // manda mensagem pro arduino, entao o ardunio pode dar um serial.read
    public static void sendSerialMessage(SerialPort port, String message) {
        try {
            OutputStream out = port.getOutputStream();
            out.write((message + "\n").getBytes());
            out.flush();
            System.out.println("Enviado para Arduino: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //fecha a porta
    public static void close() {
        if (sharedPort.get() != null && sharedPort.get().isOpen()) {
            sharedPort.get().closePort();
        }
    }




}
