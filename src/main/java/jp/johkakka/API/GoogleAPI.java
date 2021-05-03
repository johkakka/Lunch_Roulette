package jp.johkakka.API;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GoogleAPI {
    private String path = "";
    protected final ArrayList<String> errorMessages;

    GoogleAPI(){
        this(new ArrayList<>());
    }

    GoogleAPI(ArrayList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
    

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public URL getURL() throws MalformedURLException{
        return new URL(path);
    }

    protected String getKey(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get("internal"), StandardCharsets.UTF_8).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            errorMessages.add(e.toString());
            return null;
        }

        if (lines.isEmpty()){
            errorMessages.add("Not found API key.");
            return null;
        }

        return lines.get(0);
    }

    abstract public Object result(String s);

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }
}

