public class Temp{
    
@PostMapping(value = "/categories/{id}/subcategories", produces = "application/json")
public Category addSubcategory(@CookieValue(name = "uid")
String uid, @CookieValue(name = "token")
String token, @PathVariable(name = "id")
String cid, @Valid()
@RequestBody()
Category category, BindingResult validation) {
    userService.isLogin(new Integer(uid), token);
    if (validation.getFieldErrorCount() > 0) throw new UnprocessableEntityException(String.format("Invalid field value: %s", validation.getFieldErrors().get(0).getField()));
    return hierarchyService.subcategoryInsert(cid, category);
}
}