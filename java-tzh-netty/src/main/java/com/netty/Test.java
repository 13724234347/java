package com.netty;

public class Test {
	public static void main(String[] args) {
		int[] arr = new int[]{7,3,9,4,1};
		
		int[] index = new int[]{4,1,0,4,1,0,0,0,2,0,3};
		
		String tel = "";
		
		for (int i : index) {
			tel += arr[i];
		}
		System.out.println("欢迎过来骚扰："+ tel);
	}
}
