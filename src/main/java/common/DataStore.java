package common;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created by vedangjoshi on 6/10/17.
 */

public class DataStore {
    MongoClient mongoClient;
    DB database;
    DBCollection col;

    public Boolean init() {
        mongoClient = new MongoClient("localhost" , 27017);
        database = mongoClient.getDB("URL");
        col = database.getCollection("zippedURL");

        return true;
    }

    /**
     *  False - not unique
     *  True - unique
     */
    public Boolean ifURLUnique(final String hashedURL) {
        BasicDBObject query = new BasicDBObject();
        query.put("hashedUrl", hashedURL);

        DBCursor res = col.find(query);

        if(res.count() > 0)
            return false;

        return true;
    }
}
