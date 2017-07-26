package examples;

public class VarreduraArray {

	public static void main(String... args) {
		System.out.println(solution(new int[] { 5, -2, 3, 8, 6 }));
		System.out.println(solution(new int[] { -5, -5, -5, -42, 6, 12 }));
		System.out.println(solution(new int[] { -5, -10, -37, -1, 6, -6, 12 }));
	}
	
	public static int solution(int[] degree){
		int indexWinter = 0;
		int minimumDegree = degree[0];
		
		for(int d = 1 ; d < degree.length; d++){
			if(degree[d] < minimumDegree){
				minimumDegree =  degree[d];
				indexWinter = d;
			}
		}
		
		
		for(int d = indexWinter + 1 ; d < degree.length; d++){
			for(int passArray = 0; passArray <= indexWinter; passArray++ ){
				if(degree[d] <= degree[passArray]){
					indexWinter = d;
					break;
				}
			}
		}
		
		return indexWinter+1;
	}
}
