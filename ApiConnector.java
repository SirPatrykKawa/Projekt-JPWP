import java.lang.reflect.Type;
import java.net.*;
import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

// ~~Documentation for additional libraries~~
// https://hc.apache.org/httpcomponents-client-5.0.x/httpclient5/apidocs/overview-summary.html
// https://hc.apache.org/httpcomponents-core-5.0.x/httpcore5/apidocs/overview-summary.html

// ~~Links to download .jar libraries needed for this package, when not using maven
// https://hc.apache.org/downloads.cgi
// https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5
// https://mvnrepository.com/artifact/org.slf4j/slf4j-simple/1.7.25

/**
 * This class is used to connect javaFX GUI
 * with a Python-made REST API
 * to store password records.
 *
 * @author Adam Twardosz
 *
 */
public class ApiConnector {

    //-------------------------------------------------------------------------------------------------//
    //                                            CODE_AREA                                            //
    //-------------------------------------------------------------------------------------------------//

    /**
     * Just default constructor.
     */
    public ApiConnector() {}

    /**
     * Method for logging into the application and database
     *
     * @param login User's login
     * @param password User's password
     *
     * @return boolean Returns if login & Password is correct or not
     *
     * @throws  IOException
     * @throws  URISyntaxException
     */
    public boolean logIn(String login, String password) throws IOException, URISyntaxException{
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isLoginTrue = false;

            byte[] bytePass = password.getBytes();
            String passHex = DigestUtils.sha256Hex(bytePass);

            URI uri = new URIBuilder()
                        .setScheme("http")
                        .setHost("127.0.0.1")
                        .setPort(12715)
                        .setPath("/login")
                        .setParameter("login", login)
                        .setParameter("password", passHex)
                        .build();
            HttpGet getLogin = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(getLogin);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isLoginTrue = true;
                }
            } finally {
                response.close();
            }

            return isLoginTrue;
        }
    }

    /**
     * Method to synchronise local and remote database
     *
     * @return boolean Returns if the synchronisation was successful or not
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean synchronise() throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isSynchronised = false;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/sync")
                    .build();
            HttpGet synchronise = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(synchronise);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isSynchronised = true;
                }
            } finally {
                response.close();
            }

            response.close();
            return isSynchronised;
        }
    }

    /**
     * Method to initiate settings file and initial settings
     *
     * @param data document with initial settings
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean setSetup(HashMap<String, String> data) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isSet = false;

            /* Setup content:
                login
                password
                server_ip
                server_port
                database
                local_db_path
             */

            ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key: data.keySet()) {
                parameters.add(new BasicNameValuePair(key, data.get(key)));
            }

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/setup")
                    .setParameters(parameters)
                    .build();
            HttpPost setupSet = new HttpPost(uri);
            CloseableHttpResponse response = client.execute(setupSet);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isSet = true;
                }
            } finally {
                response.close();
            }

            return isSet;
        }
    }

    /**
     * Method to fetch settings from a settings file
     *
     * @return HashMap<String, String>
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public HashMap<String, String> getSetup() throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HashMap<String, String> data = null;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/setup")
                    .build();
            HttpGet setupGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(setupGet);

            String content = "";

            try {
                HttpEntity entity = response.getEntity();
                Scanner sc = new Scanner(entity.getContent());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if ( !line.isEmpty() ) {
                        content = content + line;
                    }
                }
            } finally {
                response.close();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            data = gson.fromJson(content, type);

            return data;
        }
    }

    /**
     * Method to change the setup by value
     *
     * @param valueName name of the settings position
     * @param newValue new value for the position
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean changeSetup(String valueName, String newValue) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isChanged = false;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/setup")
                    .setParameter("position_name", valueName)
                    .setParameter("new_value", newValue)
                    .build();
            HttpPut setupUpdate = new HttpPut(uri);
            CloseableHttpResponse response = client.execute(setupUpdate);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isChanged = true;
                }
            } finally {
                response.close();
            }

            return isChanged;
        }
    }

    /**
     * Method to save a document in the database
     *
     * @param collectionName name of the collection
     * @param data document to add
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean addRecord(String collectionName, HashMap<String, String> data) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isAdded = false;

            ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key: data.keySet()) {
                parameters.add(new BasicNameValuePair(key, data.get(key)));
            }

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/db/" + collectionName + "/add")
                    .setParameters(parameters)
                    .build();
            HttpPost recordAdd = new HttpPost(uri);
            CloseableHttpResponse response = client.execute(recordAdd);

            try {
                HttpEntity entity = response.getEntity();
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isAdded = true;
                }

                /*Scanner sc = new Scanner(entity.getContent());
                if (sc.nextLine().equals("REC_ADD success")) {
                    isAdded = true;
                }*/
            } finally {
                response.close();
            }

            return isAdded;
        }
    }

    /**
     * Method to fetch a document by criteria
     *
     * @param collectionName name of the collection
     * @param criteria criteria by which we choose the document
     * @param value the value of the criteria
     *
     * @return HashMap<String, String>
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public HashMap<String, String> getRecord(String collectionName, String criteria, String value) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HashMap<String, String> data = new HashMap<>();
            String requestPath = "/db/" + collectionName + "/" + criteria + "/" + value;

            /*
            * Criteria:
            *   "id"
            *   "name"
            *   "url"
            */

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath(requestPath)
                    .build();
            HttpGet recordGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(recordGet);

            String content = "";

            try {
                HttpEntity entity = response.getEntity();
                Scanner sc = new Scanner(entity.getContent());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if ( !line.isEmpty() ) {
                        content = content + line;
                    }
                }
            } finally {
                response.close();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            data = gson.fromJson(content, type);

            return data;
        }
    }

    /**
     * Method to update a record in local database
     *
     * @param collectionName name of the collection
     * @param criteria criteria of choice of the document
     * @param value value to choose by the document
     * @param last_modified time of the modification
     * @param field field to change
     * @param new_val new of the field
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean updateRecord(String collectionName, String criteria, String value, String last_modified, String field, String new_val) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isUpdated = false;
            String requestPath = "/db/" + collectionName + "/" + criteria + "/" + value;

            /*
             * Criteria:
             *   "id"
             *   "name"
             */

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath(requestPath)
                    .setParameter("last_modified", last_modified)
                    .setParameter("position_to_change", field)
                    .setParameter("new_value", new_val)
                    .build();
            HttpPut recordUpdate = new HttpPut(uri);
            CloseableHttpResponse response = client.execute(recordUpdate);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isUpdated = true;
                }
            } finally {
                response.close();
            }

            return isUpdated;
        }
    }

    /**
     * Method to delete Record from a local database
     *
     * @param collectionName name of the collection
     * @param criteria criteria of choice of the document
     * @param value value to choose by the document
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean deleteRecord(String collectionName, String criteria, String value) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isDeleted = false;
            String requestPath = "/db/" + collectionName + "/" + criteria + "/" + value;

            /*
             * Criteria:
             *   "id"
             *   "name"
             */

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath(requestPath)
                    .build();
            HttpDelete recordDel = new HttpDelete(uri);
            CloseableHttpResponse response = client.execute(recordDel);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isDeleted = true;
                }
            } finally {
                response.close();
            }

            return isDeleted;
        }
    }

    /**
     * Method to save a document with note in the database
     *
     * @param data note to add
     * @param number number of note
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean addNote(HashMap<String, String> data, Integer number) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isAdded = false;

            ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key: data.keySet()) {
                parameters.add(new BasicNameValuePair(key, data.get(key)));
            }

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/db/notes/" + Integer.toString(number))
                    .setParameters(parameters)
                    .build();
            HttpPost recordAdd = new HttpPost(uri);
            CloseableHttpResponse response = client.execute(recordAdd);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isAdded = true;
                }
            } finally {
                response.close();
            }

            return isAdded;
        }
    }

    /**
     * Method to fetch a note document by criteria
     *
     * @param number number of the note
     *
     * @return HashMap<String, String>
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public HashMap<String, String> getNote(Integer number) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HashMap<String, String> data = null;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/db/notes/" + Integer.toString(number))
                    .build();
            HttpGet recordGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(recordGet);

            String content = "";

            try {
                HttpEntity entity = response.getEntity();
                Scanner sc = new Scanner(entity.getContent());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if ( !line.isEmpty() ) {
                        content = content + line;
                    }
                }
            } finally {
                response.close();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            data = gson.fromJson(content, type);

            return data;
        }
    }

    /**
     * Method to update a record in local database
     *
     * @param number number of the note
     * @param field field to change
     * @param new_val new value of the field
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean updateNote(Integer number, String last_modified, String field, String new_val) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isUpdated = false;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/db/notes/" + Integer.toString(number))
                    .setParameter("last_modified", last_modified)
                    .setParameter("position_to_change", field)
                    .setParameter("new_value", new_val)
                    .build();
            HttpPut recordUpdate = new HttpPut(uri);
            CloseableHttpResponse response = client.execute(recordUpdate);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isUpdated = true;
                }
            } finally {
                response.close();
            }

            return isUpdated;
        }
    }

    /**
     * Method to delete note from a local database
     *
     * @param number number of the note document
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean deleteNote(Integer number) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isDeleted = false;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/db/notes/" + Integer.toString(number))
                    .build();
            HttpDelete recordDel = new HttpDelete(uri);
            CloseableHttpResponse response = client.execute(recordDel);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isDeleted = true;
                }
            } finally {
                response.close();
            }

            return isDeleted;
        }
    }

    /**
     * Method to delete collection from the local database
     *
     * @param collectionName name of the collection
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean deleteCollection(String collectionName) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isDeleted = false;
            String requestPath = "/db/" + collectionName;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath(requestPath)
                    .build();
            HttpDelete collectionDel = new HttpDelete(uri);
            CloseableHttpResponse response = client.execute(collectionDel);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isDeleted = true;
                }
            } finally {
                response.close();
            }

            return isDeleted;
        }
    }

    /**
     * Method tp fetch collection from the local database
     *
     * @param collectionName name of the collection
     *
     * @return ArrayList<HashMap>
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public ArrayList<HashMap> getCollection(String collectionName) throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            ArrayList<HashMap> collection = new ArrayList<HashMap>();
            String requestPath = "/db/" + collectionName;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath(requestPath)
                    .build();
            HttpGet collectionGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(collectionGet);

            String content = "";

            try {
                HttpEntity entity = response.getEntity();
                Scanner sc = new Scanner(entity.getContent());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if ( !line.isEmpty() ) {
                        content = content + line;
                    }
                }
            } finally {
                response.close();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
            collection = gson.fromJson(content, type);

            return collection;
        }
    }

    //-------------------------------------------------------------------------------------------------//
    //                                            TEST_AREA                                            //
    //-------------------------------------------------------------------------------------------------//

    /**
     * Method to test API
     *
     * @return boolean
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean testAPI() throws IOException, URISyntaxException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isSuccess = false;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/test")
                    .build();
            HttpGet testGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(testGet);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isSuccess = true;
                }
            } finally {
                response.close();
            }

            return isSuccess;
        }
    }

    /**
     * Method to test simple Json response
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public HashMap<String, String> testGetJsonAPI() throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HashMap<String, String> data = null;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/test/json")
                    .build();
            HttpGet setupGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(setupGet);

            String content = "";

            try {
                HttpEntity entity = response.getEntity();
                Scanner sc = new Scanner(entity.getContent());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (!line.isEmpty()) {
                        content = content + line;
                    }
                }
            } finally {
                response.close();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            data = gson.fromJson(content, type);

            return data;
        }
    }

    /**
     * Method to test API with JSON and POST method
     *
     * @return HashMap<String, String>
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean testPostJsonAPI() throws IOException, URISyntaxException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            boolean isPassed = false;

            HashMap<String, String> data = new HashMap<>();
            data.put("test", "line_1");
            data.put("test_line_2", "works!");

            ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
            for (String key: data.keySet()) {
                parameters.add(new BasicNameValuePair(key, data.get(key)));
            }

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/test/json")
                    .setParameters(parameters)
                    .build();
            HttpPost testPost = new HttpPost(uri);
            CloseableHttpResponse response = client.execute(testPost);

            try {
                int code = response.getCode();
                if (code >= 200 && code < 300) {
                    isPassed = true;
                }
            } finally {
                response.close();
            }

            return isPassed;
        }
    }

    /**
     * Method to test API with JSON
     *
     * @return HashMap<String, String>
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public ArrayList<HashMap> testGetJsonListAPI() throws IOException, URISyntaxException {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            ArrayList<HashMap> testContent = null;

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("127.0.0.1")
                    .setPort(12715)
                    .setPath("/test/json_coll")
                    .build();
            HttpGet testGet = new HttpGet(uri);
            CloseableHttpResponse response = client.execute(testGet);

            String content = "";
            try {
                HttpEntity entity = response.getEntity();
                Scanner sc = new Scanner(entity.getContent());
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if ( !line.isEmpty() ) {
                        content = content + line;
                    }
                }
            } finally {
                response.close();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
            testContent = gson.fromJson(content, type);

            return testContent;
        }
    }

    //-------------------------------------------------------------------------------------------------//
    //                                            MAIN_AREA                                            //
    //-------------------------------------------------------------------------------------------------//

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("JAVA_REST_Client Test Field: ");
        ApiConnector api = new ApiConnector();
        if (api.testAPI()) {
            System.out.println(">\tTEST passed\n");
        } else {
            System.out.println("Sorry, sth went wrong..\n");
        }
        if (!api.testGetJsonAPI().isEmpty()) {
            System.out.println(api.testGetJsonAPI());
            System.out.println(">\tJSON_TEST_GET passed\n");
        } else {
            System.out.println("Sorry, sth went wrong.., but with Json it's more complicated..\n");
        }
        if (api.testPostJsonAPI()) {
            System.out.println(">\tJSON_TEST_POST passed\n");
        } else {
            System.out.println("Sorry, sth went wrong.., but with Json it's more complicated.. and with the POST method\n");
        }
        if (!api.testGetJsonListAPI().isEmpty()) {
            System.out.println(api.testGetJsonListAPI());
            System.out.println(">\tJSON_LIST_TEST_GET passed\n");
        } else {
            System.out.println("Sorry, sth went wrong.., but with Json it's more complicated.. especially with Arrays\n");
        }
        String coll = "coll1";
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "hej");
        map.put("login", "QQ");
        map.put("password", "WW");
        map.put("url", "EE");
        map.put("expires", "false");
        map.put("expires_when", "2020-10-14-13:00:00");
        map.put("description", "QQQQQ");
        map.put("created_when", "2020-03-19-13:00:00");
        map.put("last_modified", "2020-03-18-13:00:00");
        if (api.addRecord(coll, map)) {
            System.out.print(">\tSUCCESS");
        } else {
            System.out.print(">\tFAILURE");
        }
    }
}
