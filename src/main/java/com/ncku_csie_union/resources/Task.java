package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.ITask;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.InputStream;

public class Task implements ITask {
    private URI uri = null;
    private HttpURLConnection connection = null;
    private long responseTime = -1;
    private int dataSize = -1;
    public Task() {
        Init_uri(Config.GetInstance().uri);
    }
    public void Init_uri(String uri_string){
        try {
            uri = new URI(uri_string);
        } catch (Exception e) {
            System.err.println("Error: Invalid URI.");
        }
    }
    public void Execute() {
        try {
            this.connection = (HttpURLConnection) uri.toURL().openConnection();
            this.connection.setRequestMethod("GET");

            long startTime = System.currentTimeMillis();
            int response_code = this.connection.getResponseCode();  // Triggers the request
            long endTime = System.currentTimeMillis();

            this.responseTime = endTime - startTime;

            if(response_code == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                byte[] data = new byte[1024];
                int bytesRead = 0;
                int totalBytesRead = 0;
                while((bytesRead = inputStream.read(data, 0, data.length)) != -1)
                    totalBytesRead += bytesRead;
                this.dataSize = totalBytesRead;
            } else {
                System.out.println("For uri: " + uri.toString() + " occurs some HTTP error.");
                System.err.println("Error code: " + response_code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) 
                connection.disconnect();
            System.out.println("Response Time: " + responseTime + "ms");
            System.out.println("Data Size: " + dataSize + " bytes");
            
        }
    }
    public void Stop() {
        if (connection != null) 
            connection.disconnect();
        else 
            System.out.println("Error: Connection is null.");
    }
}