package com.microlabs.utilities;



class B{
	static int a=10;
	static int b=20;
	
}


public class A { 
	
public static void main(String[] args)
{
    B b=new B();
    
    
	System.out.println("Getting a B value is *****************"+B.b);
	B b1=new B();
	
	System.out.println("Getting a B value is *****************"+B.b);
}

}
