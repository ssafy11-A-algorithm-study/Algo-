import java.io.*;
import java.util.*;

public class BOJ_3190_뱀 {

	static int N, K, L;
	static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
	static class Snake{
		int hr, hc, d;
		Queue<Pos> que = new LinkedList<>();

		public Snake(int hr, int hc, int d) {
			super();
			que.add(new Pos(hr, hc));
			this.d = d;
		}
	}
	
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + "]";
		}
		
	}
	
	/*
	 뱀 이동
	 - 머리 이동
	 - 머리가 위치한 칸에 사과가 있으면 사과 먹기
	 - 사과가 없으면 꼬리 한 칸 줄이기
	 * */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine()); // 사과의 개수 <= 100
		
		int[][] board = new int[N][N];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			board[r][c] = 1;
		}
		
		L = Integer.parseInt(br.readLine()); // 방향 변환 정보 <= 100
		int[][] rotate = new int[L][2];
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			char c = st.nextToken().charAt(0);
			rotate[i][0] = r; // 시간
			rotate[i][1] = c=='D' ? 1 : 0; // 1이면 오른쪽, 0이면 왼쪽 회전
		}
		
		int time = 1;
		int idx = 0;
		Snake s = new Snake(0, 0, 1);
		board[0][0] = 2;
		// 뱀: 2, 사과: 1
		while(true) {
			// 뱀 이동
			int nr = s.hr + dr[s.d];
			int nc = s.hc + dc[s.d];
			if(nr < 0 || nr >= N || nc < 0 || nc >= N || board[nr][nc] == 2) break;
			
			if(board[nr][nc] == 0) {
				Pos p = s.que.poll(); // 사과를 먹지 못하면 꼬리 이동
				board[p.r][p.c] = 0;
			}
			s.que.offer(new Pos(nr, nc)); // 머리 이동
			s.hr = nr;
			s.hc = nc;
			board[nr][nc] = 2; // 뱀 이동 표시
			
			time += 1;
			// 방향 전환
			if(idx < L) {
				if(time > rotate[idx][0]) {
					if(rotate[idx][1] == 0) {
						s.d = (s.d + 4 - 1) % 4;
					}else {
						s.d = (s.d + 1) % 4;
					}
					idx += 1;
				}
			}
		}
		System.out.println(time);
	}
}
