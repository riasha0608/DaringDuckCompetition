import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class TM {

	public static void main( String[] argv ) {
		char[] tape = readInputFile( argv[0] );
		String[][] tm = readTuringMachine( argv[1] );
		runTuringMachine( tape, tm );
	}

	private static void runTuringMachine( char[] tape, String[][] tm ) {

		// prep output message
		StringBuffer messBuff = new StringBuffer();
		messBuff.append( "INPUT : " );
		messBuff.append( tape );
		messBuff.append( "\n" );

		// prep execution image buffer
		StringBuffer execImag = new StringBuffer();

		int n = tape.length; // n = tape length
		int head = -1; // index into current position of the tape head

		// prep the tape pad input tape with 3 blanks on both ends
		n += 6;
		head = 3;
		char[] temp = new char[ n ];
		for( int i = 0; i < head; i++ ) {
			temp[i] = '#';
		}
		for( int i = head; i < (head+tape.length); i++ ) {
			temp[i] = tape[ i - head ];
		}
		for( int i = (head+tape.length); i < temp.length; i++ ) {
			temp[i] = '#';
		}
		tape = temp;

		boolean done = false;
		boolean found = false;
		String message = null;
		String currentState = null;
		char read;
		String action = null;
		String newState = null;
		Vector executionPath = new Vector();
		int[] stateCounter = new int[ tm.length ];

		// prep state... by defualt the first state in the input file
		// is the start state... and "halt" is the halt state
		currentState = tm[0][0];
		for( int i = 0; i < stateCounter.length; i++ ) {
			stateCounter[i] = 0;
		}

		while( !done ) {

			read = tape[ head ];

			for( int i = 0; i < tm.length; i++ ) {
				if( currentState.equals( tm[i][0] ) && read == tm[i][1].charAt( 0 ) ) {
					found = true;
					action = tm[i][2];
					newState = tm[i][3];
					executionPath.add( new Integer( i ) );
					stateCounter[i]++;

					// execution image generation
					execImag.append( tape );
					execImag.append( "\n" );
					for( int j = 0; j < head; j++ ) {
						execImag.append( " " );
					}
					execImag.append( "^\n" );
				}
			}

			if( !found ) {
				message = "No match for state and input : " + currentState + " " + read;
				break;
			}
			found = false;

			if( action.equals( "R" ) ) {
				if( (head+1) == tape.length ) {
					// extend tape to the right
					temp = new char[ tape.length + 10 ];
					for( int i = 0; i < tape.length; i++ ) {
						temp[i] = tape[i];
					}
					for( int i = tape.length; i < temp.length; i++ ) {
						temp[i] = '#';
					}
				}
				head++;
			}
			else if( action.equals( "L" ) ) {
				if( head == 0 ) {
					// extend tape to the left
					temp = new char[ tape.length + 10 ];
					for( int i = 0; i < 10; i++ ) {
						temp[i] = '#';
					}
					for( int i = 10; i < temp.length; i++ ) {
						temp[i] = tape[ i - 10 ];
					}
					head += 10;
				}
				head--;
			}
			else {
				tape[ head ] = action.charAt( 0 );
			}

			if( newState.equals( "halt" ) ) {
				// execution image generation for final step
				execImag.append( tape );
				execImag.append( "\n" );
				for( int i = 0; i < head; i++ ) {
					execImag.append( " " );
				}
				execImag.append( "^\n" );

				// put tape content in message and break
				messBuff.append( "OUTPUT : " );
				messBuff.append( stripBlankChars( tape ) );
				messBuff.append( "\n\n%%%%%%%%%%%%% Statistics %%%%%%%%%%%%%\n\n" );
				messBuff.append( createStatMessage( tm, executionPath, stateCounter, execImag ) );
				message = messBuff.toString();
				break;
			}
			else {
				currentState = newState;
				newState = null;
				action = null;
			}

		}

		System.out.println( message );
	}

	private static String createStatMessage( String[][] t, Vector v, int[] c, StringBuffer ei ) {

		StringBuffer buf = new StringBuffer();

		buf.append( "Execution Image\n\n" );
		buf.append( ei.toString() );
		buf.append( "\n\n" );

		buf.append( "Turing Machine Table\n\n" );
		buf.append( "ID   STATE     READ      ACTION    NEW STATE HITS\n" );
		for( int i = 0; i < c.length; i++ ) {
			int k = (new Integer(i)).toString().length();
			buf.append( i );
			for( int j = 0; j < (5 - k); j++ ) {
				buf.append( " " );
			}
			buf.append( t[i][0] );
			for( int j = 0; j < (10 - t[i][0].length()); j++ ) {
				buf.append( " " );
			}
			buf.append( t[i][1] );
			for( int j = 0; j < (10 - t[i][1].length()); j++ ) {
				buf.append( " " );
			}
			buf.append( t[i][2] );
			for( int j = 0; j < (10 - t[i][2].length()); j++ ) {
				buf.append( " " );
			}
			buf.append( t[i][3] );
			for( int j = 0; j < (10 - t[i][3].length()); j++ ) {
				buf.append( " " );
			}
			buf.append( c[i] + "\n" );
		}
		buf.append( "\n\n" );

		buf.append( "Execution Path\n\n" );
		int length = 0;
		for( int i = 0; i < v.size(); i++ ) {
			String num = ((Integer)v.elementAt( i )).toString();
			StringBuffer temp_b = new StringBuffer();
			if( i < (v.size()-1) ) {
				temp_b.append( num );
				temp_b.append( " -> " );
				length += temp_b.length();
			}
			else {
				temp_b.append( num );
			}
			if( length >= 70 ) {
				temp_b.append( "\n" );
				length = 0;
			}
			buf.append( temp_b.toString() );
		}

		return buf.toString();
	}

	private static String stripBlankChars( char[] c ) {
		StringTokenizer st = new StringTokenizer( (new String( c )), "#" );
		StringBuffer buf = new StringBuffer("#");
		while( st.hasMoreTokens() ) {
			buf.append( st.nextToken() );
			buf.append( "#" );
		}
		return buf.toString();
	}

	private static char[] readInputFile( String file ) {
		BufferedReader in = null;
		String line = null;
		try {
			in = new BufferedReader( new FileReader( file ) );
			line = in.readLine().trim();
			in.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return line.toCharArray();
	}

	private static String[][] readTuringMachine( String file ) {
		BufferedReader in = null;
		String line = null;
		Vector v = new Vector();
		StringTokenizer st = null;
		String[] temp = null;
		String[][] retval = null;
		try {
			in = new BufferedReader( new FileReader( file ) );
			while( (line = in.readLine() ) != null ) {
				st = new StringTokenizer( line.trim(), "," );
				temp = new String[4];
				temp[0] = st.nextToken().trim();
				temp[1] = st.nextToken().trim();
				temp[2] = st.nextToken().trim();
				temp[3] = st.nextToken().trim();
				v.add( temp );
				line = null;
			}
			in.close();
			retval = new String[ v.size() ][ 4 ];
			for( int i = 0; i < v.size(); i++ ) {
				retval[i] = (String[])v.elementAt( i );
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		return retval;
	}

}