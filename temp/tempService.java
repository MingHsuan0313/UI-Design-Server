public class Temp {
	
@GetMapping(value = "/test3", produces = "application/json")
public int viewCategoryTest3() {
    int x = 5;
    return x;
}
}