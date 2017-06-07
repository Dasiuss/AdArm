package model.connection;

import java.util.Arrays;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

public class ComConnection {
	private final static String DEFAULT_COMNAME = "COM5";
	private static final boolean DEBUG = false;
	SerialPort serialPort;

	public ComConnection(String comName) {
		try {
			serialPort = new SerialPort(comName);
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR;
			serialPort.setEventsMask(mask);

			System.out.print("connection... ");
			serialPort.writeInt(0);

			System.out.println(serialPort.readString(3, 5000));
			serialPort.readBytes();

			if (DEBUG) {
				serialPort.addEventListener(new SerialPortEventListener() {

					@Override
					public void serialEvent(SerialPortEvent event) {
						if (event.isRXCHAR() && event.getEventValue() > 0) {
							try {
								String receivedData = serialPort.readString(event.getEventValue());
								System.err.print(receivedData);
							} catch (SerialPortException ex) {
								System.out.println("Error in receiving string from COM-port: " + ex);
							}
						}

					}
				});
			}
		} catch (SerialPortException e) {
			e.printStackTrace();
		} catch (SerialPortTimeoutException e) {
			System.out.println("there is no rdy signal");
			e.printStackTrace();
		}
	}

	public ComConnection() {
		this(DEFAULT_COMNAME);
	}

	public void send(int[] data) {
		try {
			if (!serialPort.writeIntArray(data)) {
				System.out.println("cannot send " + Arrays.toString(data));
			} else {
				System.out.print("sent " + Arrays.toString(data) + "... ");
				System.out.println(serialPort.readString(3, 10000));
			}
		} catch (SerialPortException e) {
			e.printStackTrace();
			System.exit(23);
		} catch (SerialPortTimeoutException e) {
			System.out.println("could not get ack for " + Arrays.toString(data));
			e.printStackTrace();
		}
	}

	public String read() {
		try {
			return serialPort.readString();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		return null;
	}

}