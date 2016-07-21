package controller;

import java.util.ArrayList;
import java.util.List;

import model.Program;
import model.connection.ComConnection;
import model.programs.SimpleMaxReachMotion;

public class Runner {

	private static final Class<? extends Program> ProgramToRun = SimpleMaxReachMotion.class;
	private List<Program> programs;
	private ComConnection connection;

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Runner runner = new Runner(ProgramToRun.newInstance());
		runner.start();
	}

	public Runner(Program... ProgramToRun) {
		// new connection
		connection = new ComConnection();

		// store programs
		programs = new ArrayList<>();
		for (Program program : ProgramToRun) {
			programs.add(program);
		}
	}

	public void start() {

		for (Program program : programs) {
			while (program.hasNextAction()) {
				connection.send(program.getNextAction().encode().getValue());
			}
		}
	}

}
