public class Temp{
    
@GetMapping(value = "/login", produces = "application/json")
public Map<String, Object> login(@RequestParam(name = "username")
String username, @RequestParam(name = "password")
String password, HttpServletResponse response) {
    User user = userService.authenticate(username, password);
    for (Token token : user.getTokens()) userService.expireToken(token);
    Token token = userService.assignNewToken(user);
    Cookie cookie;
    cookie = new Cookie("uid", token.getUser().getId().toString());
    cookie.setPath("/");
    response.addCookie(cookie);
    cookie = new Cookie("token", token.getToken());
    cookie.setPath("/");
    response.addCookie(cookie);
    Map<String, Object> body = new HashMap<>();
    body.put("privilege", 1);
    return body;
}
}