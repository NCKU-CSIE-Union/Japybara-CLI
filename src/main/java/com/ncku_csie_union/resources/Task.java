package com.ncku_csie_union.resources;
import com.ncku_csie_union.resources.interfaces.ITask;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.InputStream;


public class Task extends Base implements Runnable {
    private URI uri = null;
    private HttpURLConnection connection = null;
    private long responseTime = -1;
    private int dataSize = -1;
    private Config config = null;

    @Override
    public void run() {
        this.Execute();
    }

    public Task() {
        Init_uri(Config.GetInstance().uri);
    }
    public void Init_uri(String uri_string){
        config = Config.GetInstance();
        logger.Debug("Task constructor called");
        try {
            uri = new URI(uri_string);
        } catch (Exception e) {
            System.err.println("Error: Invalid URI.");
            logger.Warn("Error: Invalid URI.");
            // System.exit(1);
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
                logger.Warn("For uri: " + uri.toString() + " occurs some HTTP error.");
                logger.Warn("Error code: " + response_code);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            logger.Error( e.getMessage());
        } finally {
            if (connection != null) 
                connection.disconnect();
            logger.Debug("Response Time: " + responseTime + "ms");
            logger.Debug("Data Size: " + dataSize + " bytes");
            
        }
    }
    public void Stop() {
        if (connection != null){
            connection.disconnect();
        }
        else{
            logger.Error("Error: Connection is null.");
        }
    }
}