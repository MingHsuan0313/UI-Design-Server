public class Temp{
    
@PostMapping(value = "/login2", produces = "application/json")
public Map<String, Object> login(@RequestBody()
Map<String, Object> map, HttpServletResponse response, String test) {
    DatabaseObject user = userService.authenticate((String)map.getOrDefault("username", ""), (String)map.getOrDefault("password", ""));
    userService.expireToken(user);
    DatabaseObject token = userService.assignNewToken(user);
    Cookie cookie;
    cookie = new Cookie("uid", token.get("uid").toString());
    cookie.setPath("/");
    response.addCookie(cookie);
    cookie = new Cookie("token", token.get("token").toString());
    cookie.setPath("/");
    response.addCookie(cookie);
    Map<String, Object> body = new HashMap<>();
    List<Restriction> restrict = new ArrayList<>();
    model.Restriction r1 = model.Restriction.equal("__id", token.get("uid").toString());
    restrict.add(r1);
    List<DatabaseObject> list_user = DatabaseManager.retrieveWithRestriction("User", restrict);
    body.put("privilege", list_user.get(0).get("privilege"));
    return body;
}
}