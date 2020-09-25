public class Temp{
    
public Map<String, Object> departmentById(String id,String test) {
    List<model.Restriction> restrict = new ArrayList<>();
    model.Restriction r1 = model.Restriction.equal("__id", id);
    restrict.add(r1);
    List<DatabaseObject> list = DatabaseManager.retrieveWithRestriction("Department", restrict);
    if (list.size() == 0) throw new NotFoundException(String.format("No department found with id: %s.", id));
    Map<String, Object> return_map = new HashMap<>();
    return_map.put("id", list.get(0).get("__id"));
    return_map.put("name", list.get(0).get("name"));
    return_map.put("description", list.get(0).get("description"));
    return_map.put("Code", list.get(0).get("POSCode"));
    return_map.put("tag", list.get(0).get("tag"));
    return return_map;
}
}