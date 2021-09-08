import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Top10 {
	private int[] score = new int[10];
	private String[] name = new String[10];
	public Top10(){
		retrieve();
	}
	public String show(){
		String temp = "";
		for (int x = 0; x < 10; x++){
			temp +="\n" + (x + 1) + ".) " + name[x] + "   -    " + score[x];
		}
		return temp;
	}
	public void save(){
		sort();
		PrintWriter output;
		try {
			output = new PrintWriter("scores.txt");
			for (int x = 0; x < 10; x++){
				output.println(name[x] + "\t" + score[x]);
				
			}
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void retrieve(){
		try {
			Scanner reader = new Scanner(new File("scores.txt"));
			String answer;
			String[] Answer = new String[2];
			int total = 0;
			while(true){
				answer = reader.nextLine();
				Answer = answer.split("\t");
				name[total] = Answer[0];
				score[total] = new Integer(Answer[1]);
				total++;
			}
		} catch (Exception e) {
			
		}
		sort();
	}
	public void add(String Name, int Score){
		sort();
		if (Score > score[9]){
			score[9] = Score;
			name[9] = Name;
		}
	}
	public void sort(){
		for (int x = 0; x < 9; x++) {
			for (int y = x + 1; y < 10; y++) {
				if (score[x] < score[y]){
					String n = name[x];
					int s = score[x];
					name[x] = name[y];
					score[x] = score[y];
					score[y] = s;
					name[y] = n;
				}
			}
		}
	}
}
