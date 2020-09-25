public class Temp{
    
public void itemHistoryPriceChanged(String iid, User user, Double price) {
    readAllEvent();
    readAllReason();
    ItemHistory itemHistory = new ItemHistory(null, user);
    DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
    itemHistoryDatabaseObject.putString("iid", iid);
    itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
    itemHistoryDatabaseObject.putString("event", eventMap.get("Price changed"));
    itemHistoryDatabaseObject.putDouble("price", price);
    itemHistoryDatabaseObject.putInteger("adjust", 0);
    itemHistoryDatabaseObject.putString("reason", "");
    itemHistoryDatabaseObject.putString("uid", user.getId().toString());
    itemHistoryDatabaseObject.putString("comment", "");
    manager.DatabaseManager.save(itemHistoryDatabaseObject);
}
}