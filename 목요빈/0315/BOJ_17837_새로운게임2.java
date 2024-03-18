package algorithm;

import java.io.*;
import java.util.*;

public class BOJ_17837_새로운게임2 {
	
	static int N, K;
	static int[][] board;
	static Pos[] player;
	static LinkedList<Integer>[][] map;
	static int[] dr = {0, 0, -1, 1}, dc = {1, -1, 0, 0};
	
	static class Pos{
		int r, c, d, idx;

		public Pos(int r, int c, int d, int idx) {
      super();
			this.r = r;
			this.c = c;
			this.d = d;
			this.idx = idx;
		}
	}
	/*
	 말 위에 말 올릴 수 있음
	 체크판의 각 칸은 흰, 빨, 파
	 말은 1번~k번이며, 이동방향이 정해져있음
	 
	 턴 진행 중 말이 4개 이상 쌓이는 경우 게임 종료
	 
	 턴 한번 : 모든 말을 순서대로 이동
	 
	 다음칸이 --
	 하양: 그 칸으로 이동 -> 다른 말이 있으면 위로 쌓음
	 빨강: 이동 후 새로 쌓인 말 순서 reverse
	 파란: 이동방향 반대 -> 한 칸 이동(반대칸도 파란색이면 이동하지 않는다.)
	 	  체스판을 벗어나는 경우로 파란색처럼 다루기
	
	 절대로 게임이 종료되지 않는 경우 -> 조건이 어떻게 되지?
	 * */
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // <= 12
		K = Integer.parseInt(st.nextToken()); // <= 10: 말의 수
		board = new int[N][N];
		map = new LinkedList[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				map[i][j] = new LinkedList<>();
			}
		}
		
		player = new Pos[K];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			player[i] = new Pos(r, c, d, 0);
			map[r][c].add(i);
		}
		
		int cnt = 0;
		L: while(++cnt <= 1000 ) {
			// 말 개수만큼 반복
			for (int i = 0; i < K; i++) {
				Pos p = player[i];
				int r = p.r;
				int c = p.c;
				int nr = p.r + dr[p.d];
				int nc = p.c + dc[p.d];
				int idx = 0;
				// 현재 말 위에 쌓인 말들 리스트
				List<Integer> list = new ArrayList<>(); // 현재 위치에서 p와 p 위에 있는 말 동시 이동
				for (int j = 0; j < map[p.r][p.c].size(); j++) {
					if(map[p.r][p.c].get(j) == i) {
						idx = j;
						break;
					}
				}
				
				for (int j = idx; j < map[p.r][p.c].size(); j++) {
					list.add(map[p.r][p.c].get(j));
				}
				
				// 이동(말 쌓기 + 변경된 위치 갱신)
				if(nr < 0 || nr >= N || nc < 0 || nc >= N || board[nr][nc] == 2) { // board를 벗어나거나 파란색 칸이면
					p.d = p.d % 2 == 0 ? p.d+1 : p.d-1;
					nr = p.r + dr[p.d];
					nc = p.c + dc[p.d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N || board[nr][nc] == 2) {
						continue;
					}else if(board[nr][nc] == 1) {
						// 리스트 reverse 후 다음 칸에 넣어주기
						for (int j = list.size()-1; j >= 0 ; j--) {
							Integer cur = list.get(j);
							map[nr][nc].add(cur); // 다음칸에 쌓기
							player[cur].r = nr; // 칸 이동 명시
							player[cur].c = nc;
						}
						
					}else {
						for (int j = 0; j < list.size(); j++) {
							Integer cur = list.get(j);
							map[nr][nc].add(cur); // 다음칸에 쌓기
							player[cur].r = nr; // 칸 이동 명시
							player[cur].c = nc;
						}
					}
				}else if(board[nr][nc] == 1) { // 빨간색
					// 리스트 reverse 후 다음 칸에 넣어주기
					for (int j = list.size()-1; j >= 0 ; j--) {
						Integer cur = list.get(j);
						map[nr][nc].add(cur); // 다음칸에 쌓기
						player[cur].r = nr; // 칸 이동 명시
						player[cur].c = nc;
					}
				}else {
					for (int j = 0; j < list.size(); j++) {
						Integer cur = list.get(j);
						map[nr][nc].add(cur); // 다음칸에 쌓기
						player[cur].r = nr; // 칸 이동 명시
						player[cur].c = nc;
					}
				}
				
				// 종료 조건 확인
				if(map[nr][nc].size() >= 4) {
					break L;
				}
				
				// 현 위치 말 빼주기
				for(int j = list.size()-1; j >= 0; j--) {
					map[r][c].pollLast();
				}
			}
		}
		
		System.out.println(cnt > 1000 ? -1 : cnt);
	}
}
