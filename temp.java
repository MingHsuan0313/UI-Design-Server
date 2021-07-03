package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.service.HierarchyService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public class HierarchyController {
	@Autowired()
	HierarchyService hierarchyService;
	@Autowired()
	UserService userService;

	@GetMapping(value = "/departments", produces = "application/json")
	public List<Map<String, Object>> viewDepartmentList(@CookieValue(name = "uid") String uid,
			@CookieValue(name = "token") String token) {
		userService.isLogin(new Integer(uid), token);
		return hierarchyService.departmentList();
	}

	@GetMapping(value = "/departments/{id}", produces = "application/json")
	public Department viewDepartment(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer did) {
		userService.isLogin(new Integer(uid), token);
		return hierarchyService.departmentById(did);
	}

	@PostMapping(value = "/departments", produces = "application/json")
	public Department addDepartment(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@Valid() @RequestBody() Department department, BindingResult validation) {
		userService.isLogin(new Integer(uid), token);
		if (validation.getFieldErrorCount() > 0)
			throw new UnprocessableEntityException(
					String.format("Invalid field value: %s", validation.getFieldErrors().get(0).getField()));
		return hierarchyService.departmentInsert(department);
	}

	@PatchMapping(value = "/departments/{id}", produces = "application/json")
	public Department editDepartment(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer did, @Valid() @RequestBody() Department department,
			BindingResult validation) {
		userService.isLogin(new Integer(uid), token);
		if (validation.getFieldErrorCount() > 0)
			throw new UnprocessableEntityException(
					String.format("Invalid field value: %s", validation.getFieldErrors().get(0).getField()));
		department.setId(did);
		return hierarchyService.departmentUpdate(department);
	}

	@DeleteMapping(value = "/departments/{id}", produces = "application/json")
	public void deleteDepartment(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer did) {
		userService.isLogin(new Integer(uid), token);
		hierarchyService.departmentDelete(did);
	}

	@GetMapping(value = "/departments/{id}/categories", produces = "application/json")
	public List<Map<String, Object>> viewCategoryList(@CookieValue(name = "uid") String uid,
			@CookieValue(name = "token") String token, @PathVariable(name = "id") Integer did) {
		userService.isLogin(new Integer(uid), token);
		return hierarchyService.categoryList(did);
	}

	public Department viewCategory(String uid, String token, Integer cid) {
	}

	@PostMapping(value = "/departments/{id}/categories", produces = "application/json")
	public Category addCategory(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer did, @Valid() @RequestBody() Category category,
			BindingResult validation) {
		userService.isLogin(new Integer(uid), token);
		if (validation.getFieldErrorCount() > 0)
			throw new UnprocessableEntityException(
					String.format("Invalid field value: %s", validation.getFieldErrors().get(0).getField()));
		return hierarchyService.categoryInsert(did, category);
	}

	@PatchMapping(value = "/categories/{id}", produces = "application/json")
	public Category editCategory(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer cid, @Valid() @RequestBody() Category category,
			BindingResult validation) {
		userService.isLogin(new Integer(uid), token);
		if (validation.getFieldErrorCount() > 0)
			throw new UnprocessableEntityException(
					String.format("Invalid field value: %s", validation.getFieldErrors().get(0).getField()));
		category.setId(cid);
		return hierarchyService.categoryUpdate(category);
	}

	@DeleteMapping(value = "/categories/{id}", produces = "application/json")
	public void deleteCategory(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer cid) {
		userService.isLogin(new Integer(uid), token);
		hierarchyService.categoryDelete(cid);
	}

	@GetMapping(value = "/categories/{id}/subcategories", produces = "application/json")
	public List<Map<String, Object>> viewSubcategoryList(@CookieValue(name = "uid") String uid,
			@CookieValue(name = "token") String token, @PathVariable(name = "id") Integer cid) {
		userService.isLogin(new Integer(uid), token);
		return hierarchyService.subcategoryList(cid);
	}

	@PostMapping(value = "/categories/{id}/subcategories", produces = "application/json")
	public Category addSubcategory(@CookieValue(name = "uid") String uid, @CookieValue(name = "token") String token,
			@PathVariable(name = "id") Integer cid, @Valid() @RequestBody() Category category,
			BindingResult validation) {
		userService.isLogin(new Integer(uid), token);
		if (validation.getFieldErrorCount() > 0)
			throw new UnprocessableEntityException(
					String.format("Invalid field value: %s", validation.getFieldErrors().get(0).getField()));
		return hierarchyService.subcategoryInsert(cid, category);
	}
}