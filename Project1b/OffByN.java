public class OffByN implements CharacterComparator{
    int diff = 0;

    public OffByN (int N) {
        this.diff = N;
    }

    public boolean equalChars(char x, char y) {
        if ( Math.abs((int)x-(int)y) == diff )
            return true;
        else
            return false;
    }
}
