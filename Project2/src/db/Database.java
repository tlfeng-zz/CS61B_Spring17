package db;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String,Table> tblMap;

    public Database() {
        // Create a list to store tables
        tblMap = new HashMap<>();
    }

    public Map<String,Table> getTblSet() {
        return tblMap;
    }

    public String transact(String query, Map<String,Table> tblMap) {
        String[] queryArr = {query};
        Parse.main(queryArr, tblMap);
        return "Action finished.";
    }
}