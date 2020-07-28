package com.niklasarndt.testing.generate;

import com.niklasarndt.testing.util.ButlerTest;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 2020/07/28.
 */
public class GitHubPagesGenerator extends ButlerTest {

    private GitHubPagesGenerator() throws IOException {
        File dir = new File(System.getProperty("user.dir"));
        File targetDir = new File(dir, "target");
        File compiledDir = new File(targetDir, "classes");
        File pagesDir = new File(targetDir, "gh-pages");
        File coverageDir = new File(pagesDir, "coverage");
        File execDataFile = new File(targetDir, "jacoco.exec");

        File sourceDir = new File(dir,
                String.format("src%smain%sjava", File.separator, File.separator));


        logger.info("Using running directory: " + dir.getAbsolutePath());

        ExecFileLoader execFileLoader = new ExecFileLoader();
        execFileLoader.load(execDataFile);
        IBundleCoverage coverage = analyzeStructure(dir.getName(), execFileLoader, compiledDir);


        GeneratorContext context = new GeneratorContext(dir, execDataFile, sourceDir,
                targetDir, pagesDir, coverageDir, execFileLoader, coverage);

        new XmlGenerator(context).create();
        new ReportGenerator(context).create();
        new IndexGenerator(context).create();
    }

    public static void main(String[] args) {
        try {
            new GitHubPagesGenerator();
        } catch (Exception e) {
            System.exit(1);
        }
    }

    private IBundleCoverage analyzeStructure(String title, ExecFileLoader execFileLoader,
                                             File classesDirectory) throws IOException {
        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        final Analyzer analyzer = new Analyzer(
                execFileLoader.getExecutionDataStore(), coverageBuilder);

        analyzer.analyzeAll(classesDirectory);

        return coverageBuilder.getBundle(title);
    }
}
