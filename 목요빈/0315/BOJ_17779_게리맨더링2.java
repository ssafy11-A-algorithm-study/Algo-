import java.io.*;
import java.util.*;

public class BOJ_17779_게리맨더링2 {

	static int N, total, min;
	static int[][] board;
	static int[] dx = {1, 1}, dy = {-1, 1}; // 왼쪽 아래, 오른쪽 아래

	public static void main(String[] args) throws NumberFormatException, IOException {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine()); // <= 20
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				total += board[i][j];
			}
		}
		
		min = Integer.MAX_VALUE;
		for (int x = 0; x < N; x++) {
			for (int y = 1; y < N-1; y++) {
				int d = N-x-1;
				for (int d1 = 1; d1 <= y; d1++) {
					for (int d2 = 1; d2 <= Math.min(d - d1, N-y-1); d2++) { //////// N-y-1(-1이 맞는지?)
						split(x, y, d1, d2);
					}
				}
			}
		}
		
		System.out.println(min);
	}
	
	static void split(int x, int y, int d1, int d2) {
		int[] sum = new int[5];
		int[][] temp = new int[N][N];
		for (int i = 0; i <= d1; i++) {
			temp[x + i][y - i] = 5;
			temp[x + d2 + i][y + d2 - i] = 5;
		}
		
		for (int i = 1; i <= d2; i++) {
			temp[x + i][y + i] = 5;
			temp[x + d1 + i][y - d1 + i] = 5;
		}
		
		for (int i = 0; i < x+d1; i++) { 
			for (int j = 0; j <= y; j++) { 
				if(temp[i][j] == 5) break;
				sum[0] += board[i][j];
			}
		}
		
		for (int i = 0; i <= x+d2; i++) { 
			for (int j = N-1; j > y; j--) { 
				if(temp[i][j] == 5) break;
				sum[1] += board[i][j];
			}
		}
		
		for (int i = x+d1; i < N; i++) { 
			for (int j = 0; j < y-d1+d2; j++) {
				if(temp[i][j] == 5) break;
				sum[2] += board[i][j];
			}
		}
		
		for (int i = x+d2+1; i < N; i++) { 
			for (int j = N-1; j >= y-d1+d2; j--) {
				if(temp[i][j] == 5) break;
				sum[3] += board[i][j];
			}
		}

		
		sum[4] = total;
		for (int i = 0; i < 4; i++) {
			sum[4] -= sum[i];
		}
		Arrays.sort(sum);
		min = Math.min(min, sum[4] - sum[0]);
	}

}
