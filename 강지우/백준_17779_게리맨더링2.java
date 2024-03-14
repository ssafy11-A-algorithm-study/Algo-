import java.io.*;
import java.util.*;

// 모든 기준 위치에서 완탐을 해보자 d1,d2
// 0,0 의 위치는 할 필요 없음
public class Main {
    static BufferedReader br;
    static StringTokenizer st;
    static int n,mapSum,ans;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                mapSum += arr[i][j];
            }
        }

        ans = Integer.MAX_VALUE;
        for (int si = 0; si < n-2; si++) {
            for (int sj = 1; sj < n-1; sj++) {      // 가능한 모든 기준위치  d1을 기준을 이동 후 d2 이동 -> 범위 내부에서만
                for (int d1 = 1; d1 < n; d1++) {    // 범위내의 조건일때의 d1
                    if(0 <= si + d1 && si + d1 < n && 0 <= sj - d1 && si - d1 < n ){
                        for (int d2 = 1; d2 < n; d2++) {
                            if(0 <= si + d1 + d2 && si + d1 + d2 < n && 0 <= sj + d2 && sj + d2 < n ){
                                ans = Math.min(ans, cal(si,sj,d1,d2));
                            }
                        }
                    }
                }
            }
        }
//        print();
        System.out.println(ans);
    }

    private static int cal(int si, int sj, int d1, int d2) {
        int[][] v = new int[n][n];
        int[] lst = new int[5];

        // 5번구역에 표시
        v[si][sj] = 1;
        int j1 = sj;
        int j2 = sj;
        for (int di = 1; di < d1+d2+1; di++) {
            if(di<=d1){
                j1--;
            }else{
                j1++;
            }

            if(di<=d2){
                j2++;
            }else{
                j2--;
            }

            for (int i = j1; i < j2+1; i++) {
                v[si+di][i] = 1;
            }
        }

        // 1~4구역의 인구수 누적
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(v[i][j] == 1){
                    continue;
                }
                if(i<si+d1 && j<=sj){
                    lst[0] += arr[i][j];
                }
                if(i<=si+d2 && sj<j){
                    lst[1] += arr[i][j];
                }
                if(si+d1<=i && j<sj-d1+d2){
                    lst[2] += arr[i][j];
                }
                if(si+d2<i && sj-d1+d2<=j){
                    lst[3] += arr[i][j];
                }
            }
        }

        // 5구역 인구수
        lst[4] = mapSum - lst[0] - lst[1] - lst[2] - lst[3];
        int max = Arrays.stream(lst).max().getAsInt();
        int min = Arrays.stream(lst).min().getAsInt();
        return max-min;
    }

    private static void print() {
        for (int[] lst : arr) {
            System.out.println(Arrays.toString(lst));
        }
    }
}
