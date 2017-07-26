package examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class LeituraOrdenacaoStreamLambda {

	public static void main2(String[] args) throws IOException {
		File file = new File("/home/elvisrocha/Downloads/Telegram Desktop/file.txt");

		InputStream inputStream = new FileInputStream(file);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
		List<Servidor> servidorList = new ArrayList<>();

		buffer.lines().forEach(l -> servidorList.add(new Servidor(l)));

		servidorList.stream()
				.collect(Collectors.groupingBy(Servidor::getName,
						Collectors.maxBy(Comparator.comparing(Servidor::getAcessos))))
				.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEachOrdered(s -> System.out.println(s.getKey() + ":" + s.getValue().get().getAcessos()));

		buffer.close();
	}

	public static void main(String[] args) throws IOException {
		File file = new File("/home/elvisrocha/Downloads/Telegram Desktop/operations.txt");

		UnaryOperator<Integer> apply = (x) -> x;
		Map<String, BinaryOperator<Integer>> operators = new HashMap<>();
		operators.put("add", (x, y) -> x + y);
		operators.put("multiply", (x, y) -> x * y);
		operators.put("divide", (x, y) -> x / y);
		operators.put("subtract", (x, y) -> x - y);
		operators.put("mod", (x, y) -> x % y);

		InputStream inputStream = new FileInputStream(file);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
		List<Operator> operatorList = new ArrayList<>();

		buffer.lines().forEach(l -> operatorList.add(new Operator(l)));
		Operator lastLine = operatorList.get(operatorList.size() - 1);
		Integer value = apply.apply(lastLine.getNumber());

		for (int i = 0; i < operatorList.size() - 1; i++) {
			Operator o = operatorList.get(i);
			value = operators.get(o.getOperation()).apply(value, o.getNumber());
		}

		buffer.close();

		System.out.println(value);

	}
}

class Servidor {

	private String name;
	private int acessos;

	Servidor(String l) {
		name = l.split(";")[0].trim();
		acessos = Integer.valueOf(l.split(";")[1].trim());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAcessos() {
		return acessos;
	}

	public void setAcessos(int value) {
		this.acessos = value;
	}
}

class Operator {

	private String operation;
	private Integer number;

	Operator(String l) {
		operation = l.split(" ")[0].trim();
		number = Integer.parseInt(l.split(" ")[1].trim());
	}

	public String getOperation() {
		return operation;
	}

	public Integer getNumber() {
		return number;
	}
}
