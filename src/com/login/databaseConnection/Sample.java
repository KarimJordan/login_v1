package com.login.databaseConnection;

public class Sample {
	public static void main(String[] args) {
		System.out.println("SAMPLE");
		for(int i=0; i <= 100; i++)
		{
			if((i%3) == 0)
			{
				System.out.println("FIZZ");
			}else if((i%5) == 0)
			{
				System.out.println("BUZZ");
			}else if(((i%3) == 0) | ((i%5)== 0))
			{
				System.out.println("FIZZBUZZ");
			}else{
			System.out.println(i);
			}
		}
	}
}
