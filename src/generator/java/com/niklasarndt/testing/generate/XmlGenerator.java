package com.niklasarndt.testing.generate;

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.xml.XMLFormatter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Niklas on 2020/07/28.
 */
public class XmlGenerator {
    private final File targetDirectory;
    private final IBundleCoverage coverage;
    private final ExecFileLoader executionData;
    private final File sourceDirectory;

    public XmlGenerator(File sourceDirectory, File targetDirectory,
                        IBundleCoverage coverage, ExecFileLoader execFileLoader) {
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
        this.coverage = coverage;
        this.executionData = execFileLoader;
    }

    public void create() throws IOException {
        XMLFormatter formatter = new XMLFormatter();

        IReportVisitor visitor = formatter.createVisitor(
                new FileOutputStream(new File(targetDirectory, "coverage.xml")));
        visitor.visitInfo(executionData.getSessionInfoStore().getInfos(),
                executionData.getExecutionDataStore().getContents());

        visitor.visitBundle(coverage,
                new DirectorySourceFileLocator(sourceDirectory, "utf-8", 4));

        visitor.visitEnd();
    }
}
