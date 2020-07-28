package com.niklasarndt.testing.generate;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 2020/07/28.
 */
public class GitHubPagesGenerator {

    public static void main(String[] args) throws IOException {
        File dir = new File(System.getProperty("user.dir"));
        String title = dir.getName();
        File execDataFile = new File(dir,
                String.format("target%sjacoco.exec", File.separator));
        File sourceDir = new File(dir,
                String.format("src%smain", File.separator));
        File targetDir = new File(dir, "target");
        File pagesDir = new File(targetDir, "gh-pages");
        File coverageDir = new File(pagesDir, "coverage");

        File compiledDir = new File(dir,
                String.format("target%sclasses", File.separator));

        System.out.println("Using running directory: " + dir.getAbsolutePath());

        ExecFileLoader execFileLoader = new ExecFileLoader();
        execFileLoader.load(execDataFile);
        IBundleCoverage coverage = analyzeStructure(title, execFileLoader, compiledDir);


        new XmlGenerator(sourceDir, targetDir, coverage, execFileLoader).create();
        new ReportGenerator(execFileLoader,
                sourceDir, coverageDir, coverage).create();
        new IndexGenerator(pagesDir).create();
    }

    private static IBundleCoverage analyzeStructure(String title, ExecFileLoader execFileLoader,
                                                    File classesDirectory) throws IOException {
        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        final Analyzer analyzer = new Analyzer(
                execFileLoader.getExecutionDataStore(), coverageBuilder);

        analyzer.analyzeAll(classesDirectory);

        return coverageBuilder.getBundle(title);
    }
}
