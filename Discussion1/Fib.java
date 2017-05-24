/** fib(n) returns the nth Fibonacci number, for n >= 0.
  * The Fibonacci sequence is 0, 1, 1, 2, 3, 5, 8, 13, 21, ... */
public class Fib {	

	public static int fib(int n) { 
		int a=0, b=1, f=0;
		if (n==0 || n==1) {
			return f;
		}

		for(int i=0; i<n-1; i++) {
			f = a+b;
			a = b;
			b = f;
		}
		return f;
	}

	public static int fib_ans(int n) { 
		int a=0, b=1, temp;

		for(int i=0; i<n; i++) {
			temp = b;
			b = a+b;
			a = temp;
		}
		return a;
	}

/* Extra for experts: Complete fib2 in 5 lines or less. Your answer must be efficient.
/* fib2(n, 0, 0, 1) returns the nth Fibonacci number, for n >=0. */
	public static int fib2(int n, int k, int f0, int f1) {
		if (n==k)
			return f0;
		else
			return fib2(n, k + 1, f1, f0 + f1);
	}

}