public class OffByOne implements CharacterComparator{
    public boolean equalChars(char x, char y) {
        if ( Math.abs((int)x-(int)y) == 1 )
            return true;
        else
            return false;
    }
}
