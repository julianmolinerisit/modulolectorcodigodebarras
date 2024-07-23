package com.ejemplo.lectorcodigosbarras.service;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

@Service
public class BarcodeReaderService {
    private SerialPort serialPort;

    public String[] getAvailablePorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] portNames = new String[ports.length];
        for (int i = 0; i < ports.length; i++) {
            portNames[i] = ports[i].getSystemPortName();
        }
        return portNames;
    }

    public void configurePort(String portName) {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        serialPort.openPort();
    }

    public String readBarcode() {
        if (serialPort == null || !serialPort.isOpen()) {
            throw new IllegalStateException("Serial port is not configured or open.");
        }
        StringBuilder barcode = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead = serialPort.readBytes(buffer, buffer.length);
        for (int i = 0; i < bytesRead; i++) {
            barcode.append((char) buffer[i]);
        }
        return barcode.toString().trim();
    }
}
