import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * This class can make a HttpUrlConnection and connect it and
 * get the respond
 */


public class Request implements Serializable{

    private static final long serialVersionUID = -7918451096468596888L;
    private URL url;
    private String method;
    private String body[];
    private String header[];

    /**
     * @param url : link or address
     * @param method : method of HttpUrlConnection
     * @param body : string of all the form data
     * @param header: string of all the header
     */
    public Request(URL url, String method, String[] body, String[] header){
        this.url = url;
        this.method = method;
        this.body = body;
        this.header = header;
    }

    public void setUrl(URL url) { this.url = url; }

    public URL getUrl() { return url; }

    public void setMethod(String method) { this.method = method; }

    public String getMethod() { return method; }

    public void setBody(String[] body) { this.body = body; }

    public String[] getBody() { return body; }

    public void setHeader(String[] header) { this.header = header; }

    public String [] getHeader() { return header; }


    /**
     * In this method we create a new HttpUrlConnection and also split the body
     * and header and set them in the right place (also method and url)
     * and shoe the respond of request
     * @param saveRespond : boolean that tell us we want to save a respond or not
     * @param nameOutput :if we want to save a respond whats the name
     * @param headerRespond  boolean that tell us we want to show a headerFields or not
     * @param followRedirect  boolean that tell us if re url is redirect chose the right url
     * @throws IOException
     */


    public void createRequest(boolean saveRespond, String nameOutput, boolean headerRespond, boolean followRedirect) throws IOException {

        System.out.println(followRedirect);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setRequestMethod(method);
        urlCon.setInstanceFollowRedirects(true);
        urlCon.setDoOutput(true);
        String boundary = System.currentTimeMillis() + "";
        if (!method.equals("GET"))
            urlCon.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        if (header != null) {
            for (String s : header) {
//                System.out.println("header2: " +s);
                if (s!=null) {
                    if (s.contains(":")) {
                        String nameValue[] = s.split(":");
//                        System.out.println("header" +nameValue[0]);
                        urlCon.setRequestProperty(nameValue[0], nameValue[1]);
                    }
                }
            }
        }


        HashMap<String, String> Body = new HashMap<>();
        if (body != null) {
            for (String s : body) {
//                System.out.println("formdata2:  " +s);
                if (s!=null) {
                    if (s.contains("=")) {
                        String test[] = s.split("=");
//                        System.out.println("dataform "+test[0]);
                        Body.put(test[0], test[1]);
                    }
                }
            }
        }

        if (!followRedirect){
            urlCon.setInstanceFollowRedirects(false);
        }



        if (!method.equals("GET")) {
            BufferedOutputStream bos = new BufferedOutputStream(urlCon.getOutputStream());
//            System.out.println("bodyyyyyy " +Body.size());
            bufferOutFormData(Body, boundary, bos);
        }
        String output = new String();
        try {
            BufferedInputStream bis = new BufferedInputStream(urlCon.getInputStream());
//            System.out.println(bis.readAllBytes().toString());
            output = new String(bis.readAllBytes());
            System.out.println(output);
        }
        catch (IOException e){
//            e.printStackTrace();
            System.out.println("NO body returned a respond");
//            System.out.println(urlCon.getRequestMethod());
        }
        if (saveRespond){
            String contentType = urlCon.getContentType().toString();
            String type[] = contentType.split(";");
            String type2[] = type[0].split("/");

            if (nameOutput.equals("")) {
                Date date = new Date();
                nameOutput = "output_[" +date.toString() +"]." +type2[1];
            }
            else {
                nameOutput += "."+ type2[1];
            }
            Save save = new Save();
            save.SaveRespond(output, nameOutput);
        }



        System.out.println("***********************");
        System.out.println("Code: " +urlCon.getResponseCode() +" " +urlCon.getResponseMessage());
        System.out.println("Method: " +urlCon.getRequestMethod());
        System.out.println("direct: " +urlCon.getInstanceFollowRedirects());
        System.out.println("***********************");


        Map<String, List<String>> map = urlCon.getHeaderFields();
        String[] HeaderRespond = new String[map.keySet().size()];
        if (headerRespond){
            System.out.println("header:");
            int counter = 0;
            for (String key : map.keySet()) {
                HeaderRespond[counter++] = key +"---->" +map.get(key);
            }
            for (String s :HeaderRespond)
                System.out.println(s);
        }
//        System.out.println("***********************");

    }


    public static void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
            bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }






}
