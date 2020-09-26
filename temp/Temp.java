public class Temp{
    
@GetMapping(value = "/", produces = "application/json")
public List<Map<String, Object>> viewAcquisitionList(@CookieValue(name = "uid")
String uid, @CookieValue(name = "token")
String token) {
    return acquisitionService.acquisitionList();
}
}