public class Temp{
    
public void itemHistoryPriceSet(String iid, User user, String data) {
    readAllEvent();
    readAllReason();
    dd
    ItemHistory itemHistory = new ItemHistory(null, user);
    DatabaseObject itemHistoryDatabaseObject = DatabaseObject.initMethod("ItemHistory");
    itemHistoryDatabaseObject.putString("iid", iid);
    itemHistoryDatabaseObject.putDate("date", itemHistory.getDate());
    itemHistoryDatabaseObject.putString("event", eventMap.get("Price set"));
    Double price = 0.0;
    for (DatabaseObject databaseObject : manager.DatabaseManager.retrieveAll("Item")) {
        if (databaseObject.get("__id").toString().equals(iid)) {
            price = (Double)databaseObject.get("price");
        }
    }
    itemHistoryDatabaseObject.putDouble("price", price);
    itemHistoryDatabaseObject.putInteger("adjust", 0);
    itemHistoryDatabaseObject.putString("reason", "");
    itemHistoryDatabaseObject.putString("uid", user.getId().toString());
    itemHistoryDatabaseObject.putString("comment", "");
    manager.DatabaseManager.save(itemHistoryDatabaseObject);
}
}