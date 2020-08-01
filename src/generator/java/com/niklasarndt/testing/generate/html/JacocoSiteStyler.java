package com.niklasarndt.testing.generate.html;

import com.niklasarndt.testing.generate.util.CustomResources;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.internal.ReportOutputFolder;
import org.jacoco.report.internal.html.resources.Resources;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 2020/07/31.
 */
public class JacocoSiteStyler {

    private final File reportDir;
    private final File resourceDir;
    private final Resources defaultResources;
    private final ReportOutputFolder output;

    public JacocoSiteStyler(File reportDir) {
        this.reportDir = reportDir;
        this.resourceDir = new File(reportDir, "jacoco-resources");
        this.output = new ReportOutputFolder(new FileMultiReportOutput(reportDir));
        this.defaultResources = new Resources(output);
    }

    public void applyChanges() throws IOException {
        copyResources();
        //deleteRedundantResources();
        File index = new File(reportDir, "index.html");
        Document doc = Jsoup.parse(index, "UTF-8");

        doc.getElementsByClass("info").get(0)
                .prepend("<div class=\"inline-svg\"></div>");

        Files.writeString(index.toPath(), doc.html(), StandardCharsets.UTF_8);
    }

    private String link(String filename) {
        return defaultResources.getLink(output, filename);
    }

    private void deleteRedundantResources() {
        List<Path> deleteList = new ArrayList<>();

        for (File file : resourceDir.listFiles((dir, name) -> name.endsWith(".gif"))) {
            deleteList.add(file.toPath());
        }

        deleteList.forEach(file -> {
            try {
                Files.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void copyResources() throws IOException {
        CustomResources resources = new CustomResources("jacoco", resourceDir);
        resources.copyResources();

    }
}
