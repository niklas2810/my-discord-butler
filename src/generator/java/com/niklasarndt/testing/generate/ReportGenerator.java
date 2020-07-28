package com.niklasarndt.testing.generate;

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.html.HTMLFormatter;
import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 2020/07/28.
 * <p>
 * Copyright (c) 2009, 2020 Mountainminds GmbH & Co. KG and Contributors
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 * <p>
 * Contributors:
 * Brock Janiczak - initial API and implementation
 * <p>
 * Fetched from: https://www.jacoco.org/jacoco/trunk/doc/api.html
 * <p>
 * This is a modified version.
 */

public class ReportGenerator {

    private final File sourceDir;
    private final File reportDir;
    private final ExecFileLoader execFileLoader;
    private final IBundleCoverage coverage;


    public ReportGenerator(ExecFileLoader execFileLoader,
                           File sourceDir, File targetDir,
                           IBundleCoverage coverage) {
        this.execFileLoader = execFileLoader;
        this.sourceDir = sourceDir;
        this.reportDir = targetDir;
        this.coverage = coverage;
    }

    public void create() throws IOException {

        if (reportDir.exists()) reportDir.delete();
        reportDir.mkdir();

        final HTMLFormatter htmlFormatter = new HTMLFormatter();
        final IReportVisitor visitor = htmlFormatter
                .createVisitor(new FileMultiReportOutput(reportDir));

        CustomResources resources = new CustomResources("jacoco",
                new File(reportDir, "jacoco-resources"));
        resources.copyResources();

        visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(),
                execFileLoader.getExecutionDataStore().getContents());

        visitor.visitBundle(coverage,
                new DirectorySourceFileLocator(sourceDir, "utf-8", 4));

        visitor.visitEnd();
    }

    private void createReport(final IBundleCoverage bundleCoverage)
            throws IOException {


    }


}
