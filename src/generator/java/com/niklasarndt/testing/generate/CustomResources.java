package com.niklasarndt.testing.generate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 2020/07/28.
 */
public class CustomResources {
    private final String path;
    private final File outputFolder;

    public CustomResources(String path, File target) {
        this.path = path;
        this.outputFolder = target;
    }

    public void copyResources() throws IOException {
        getResourceFiles(path).forEach(resource -> {
            try {
                copyResource(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void copyResource(final String name) throws IOException {
        File outFile = new File(outputFolder, name);

        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
            outFile.createNewFile();
        }

        final InputStream in = getResourceAsStream(String.format("%s/%s", path, name));
        final OutputStream out = new FileOutputStream(outFile);
        final byte[] buffer = new byte[256];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory.
     */
    private List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (InputStream in = getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    /**
     * https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory.
     */
    private InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    /**
     * https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory.
     */
    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
