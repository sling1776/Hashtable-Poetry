import java.util.*;

public class WordFreqInfo {
    public String word;
    public int occurCt;
    ArrayList<Freq> followList;

    public WordFreqInfo(String word, int count) {
        this.word = word;
        this.occurCt = count;
        this.followList = new ArrayList<Freq>();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word :" + word+":");
        sb.append(" (" + occurCt + ") : ");
        for (Freq f : followList)
            sb.append(f.toString());

        return sb.toString();
    }

    public void updateFollows(String follow) {
       //System.out.println("updateFollows " + word + " " + follow);
        for (Freq f : followList) {
            if (follow.compareTo(f.follow) == 0) {
                f.followCt++;
                return;
            }
        }
        followList.add(new Freq(follow, 1));
    }

    public static class Freq {
        String follow;
        int followCt;

        public Freq(String follow, int ct) {
            this.follow = follow;
            this.followCt = ct;
        }

        public String toString() {
            return follow + " [" + followCt + "] ";
        }

        public boolean equals(Freq f2) {
            return this.follow.equals(f2.follow);
        }
    }
/*The Following code is a test of the effectiveness of the Java hashcode generator in comparison with a really bad one.*/

    private static class MyString{
        String string;
        MyString(String s){
            string = s;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyString myString = (MyString) o;
            return Objects.equals(string, myString.string);
        }

        @Override
        public int hashCode() {
            return string.length();
        }
    }



    public static void main(String[] args) {
        {
            HashTable<String, WordFreqInfo> H = new HashTable<>( );
            HashTable<MyString, WordFreqInfo> I = new HashTable<>( );

            final int NUMS = 3000;
            final int GAP  =   37;

            System.out.println( "Checking... " );

            //Insertions:
            long startHInsertTime = System.currentTimeMillis( );
            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                H.insert( ""+i , new WordFreqInfo(""+i,1));
            long endHInsertTime = System.currentTimeMillis( );

            long startIInsertTime = System.currentTimeMillis( );
            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                I.insert( new MyString(""+i) , new WordFreqInfo(""+i,1));
            long endIInsertTime = System.currentTimeMillis( );

            //Finding
            ArrayList<Integer> hProbe = new ArrayList<>();
            long startHFindTime = System.currentTimeMillis( );
            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                hProbe.add(H.testFindPos( ""+i , true));
            long endHFindTime = System.currentTimeMillis( );
            int avgHProbe = 0;
            for (Integer probe:hProbe
                 ) {
                avgHProbe+=probe;
            }
            avgHProbe = avgHProbe/hProbe.size();

            ArrayList<Integer> iProbe = new ArrayList<>();
            long startIFindTime = System.currentTimeMillis( );
            for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
                iProbe.add(I.testFindPos( new MyString(""+i), true));
            long endIFindTime = System.currentTimeMillis( );
            int avgIProbe = 0;
            for (Integer probe:iProbe
            ) {
                avgIProbe+=probe;
            }
            avgIProbe = avgIProbe/iProbe.size();



            //Results
            System.out.println( "H size is: " + H.size( ) );
            System.out.println( "I size is: " + I.size( ) );
            System.out.println( "H : Elapsed time: " + (endHInsertTime - startHInsertTime) );
            System.out.println( "I : Elapsed time: " + (endIInsertTime - startIInsertTime) );
            System.out.println( "H : Elapsed time: " + (endHFindTime - startHFindTime) );
            System.out.println( "I : Elapsed time: " + (endIFindTime - startIFindTime) );
            System.out.println( "H : average Probes: " + avgHProbe );
            System.out.println( "I : average Probes: " + avgIProbe );
            System.out.println( "H Array size is: " + H.capacity( ) );
            System.out.println( "I Array size is: " + I.capacity( ) );
        }
    }

}

