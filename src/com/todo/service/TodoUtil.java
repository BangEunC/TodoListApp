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
		
		System.out.print("[�׸��߰�]\n"
				+ "���� > ");
		
		title = sc.next();
		if (l.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		l.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸����]\n"
				+ "������ �׸��� ������ �Է��Ͻÿ� > ");
		int index = sc.nextInt();
		if (index > l.getCount()) {
			System.out.println("���� ��ȣ�Դϴ�!");
			return;
		}
		System.out.println((index)+". "+l.getItem(index-1).toString());
		
		System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
		String yesno = sc.next();
		if (yesno.equals("y")) {
			l.deleteItem(l.getItem(index-1));
			System.out.println("�����Ǿ����ϴ�.");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("�׸����\n"
				+ "������ �׸��� ������ �Է��Ͻÿ� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("���� �����Դϴ�!");
			return;
		}

		System.out.print("�� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("�� ���� > ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {	
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���]");
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
			System.out.println("��� �����Ͱ� ����Ǿ����ϴ�.");
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
			System.out.println(count+"���� �׸��� �о����ϴ�.");
		} catch (FileNotFoundException e) {
			System.out.println(filename+" ������ �����ϴ�.");
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
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

	public static void listCateAll(TodoList l) {
		int count=0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
}
