package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목추가]\n"
				+ "제목 > ");
		
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		l.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목삭제]\n"
				+ "삭제할 항목의 제목을 입력하시오 > ");
		int index = sc.nextInt();
		if (index > l.getCount()) {
			System.out.println("없는 번호입니다!");
			return;
		}
		System.out.println((index)+". "+l.getItem(index-1).toString());
		
		System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
		String yesno = sc.next();
		if (yesno.equals("y")) {
			l.deleteItem(l.getItem(index-1));
			System.out.println("삭제되었습니다.");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("항목수정\n"
				+ "수정할 항목의 제목을 입력하시오 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다!");
			return;
		}

		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		System.out.print("새 내용 > ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {	
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("todolist.txt");
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("모든 데이터가 저장되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
			String line;
			int count = 0;
			while((line = br.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String description = st.nextToken();
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title, description);
				item.setCurrent_date(current_date);
				l.addItem(item);
				count++;
			}
			br.close();
			System.out.println(count+"개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			System.out.println(filename+" 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void findList(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getList(keyword)) {
				System.out.println(item.toString());
				count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listCateAll(TodoList l) {
		int count=0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
}
