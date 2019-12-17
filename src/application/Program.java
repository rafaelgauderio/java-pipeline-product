package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.print.DocFlavor.STRING;

import com.sun.net.httpserver.Filter;

import entities.Product;

public class Program {
	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter file path:");
		String FilePath = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(FilePath))) {
			
			List<Product> list = new ArrayList<>();
			
			String line = br.readLine();
			
			while (line!= null) {
				String [] field = line.split(",");
				list.add(new Product(field[0], Double.parseDouble(field[1])));
				line = br.readLine();
			}
			
			System.out.println("Original List");
			for (Product nickname : list) {
				System.out.println(nickname);
			}
			
			System.out.println("List size = " + list.size());
			System.out.println();
			
			//pipeline 
			double average = list.stream()
					.map(pro->pro.getPrice())
					.reduce(0.0, (x,y) -> x+y) / list.size() ;
								
			System.out.println("Avegare price" + String.format(" U$ %.2f",average));
			System.out.println();
			
			
			Comparator<String> comp =  (s1 ,s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			System.out.println("List in descendig order");			
			List<String> namesDescending = list.stream()
					.filter(p -> p.getPrice() < average)
					.map(p->p.getName()).sorted(comp.reversed())
					.collect(Collectors.toList());
			
			
			namesDescending.forEach(System.out::println);
			
			System.out.println();
			System.out.println("List in Alphabetical order");
			List<String> namesAlpha = list.stream()
					.filter(p -> p.getPrice() < average)
					.map(p->p.getName()).sorted(comp)
					.collect(Collectors.toList());
			namesAlpha.forEach(System.out::println);
		}
		
	
		catch (IOException error) {
			System.out.println("Erro" + error.getMessage());
		}

		sc.close();

	}

}
