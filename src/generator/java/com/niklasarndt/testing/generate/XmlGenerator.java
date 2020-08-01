package com.niklasarndt.testing.generate;

import com.niklasarndt.testing.generate.util.GeneratorContext;
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

    private final ExecFileLoader execFileLoader;
    private final GeneratorContext context;


    public XmlGenerator(GeneratorContext context) {
        this.context = context;
        this.execFileLoader = context.getExecFileLoader();
    }

    public void create() throws IOException {
        XMLFormatter formatter = new XMLFormatter();

        IReportVisitor visitor = formatter.createVisitor(
                new FileOutputStream(new File(context.getTargetDir(), "coverage.xml")));
        visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
                execFileLoader.getExecutionDataStore().getContents());

        visitor.visitBundle(context.getCoverage(),
                new DirectorySourceFileLocator(context.getSourceDir(), "utf-8", 4));

        visitor.visitEnd();
    }
}
