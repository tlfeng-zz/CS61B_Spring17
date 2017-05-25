public class IntList {
	public int first;
	public IntList rest;

	public IntList(int f, IntList r) {
		first = f;
		rest = r;
	}

	public int size() {
		if (rest == null)
			return 1;
		else
			return 1+rest.size();
	}

	public int iterativeSize() {
		IntList p = this;
		int s = 0;
		while (p != null) {
			s++;
			p = p.rest;
		}
		return s;
	}
}