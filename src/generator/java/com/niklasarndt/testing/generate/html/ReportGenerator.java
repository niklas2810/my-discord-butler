package com.niklasarndt.testing.generate.html;

import com.niklasarndt.testing.generate.util.GeneratorContext;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.html.HTMLFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

    private final GeneratorContext context;
    private final File reportDir;


    public ReportGenerator(GeneratorContext context) {
        this.context = context;
        this.reportDir = context.getCoverageDir();
    }

    public void create() throws IOException {

        Files.deleteIfExists(reportDir.toPath());
        reportDir.mkdir();

        final HTMLFormatter htmlFormatter = new HTMLFormatter();
        htmlFormatter.setFooterText("IM HERE");
        final IReportVisitor visitor = htmlFormatter
                .createVisitor(new FileMultiReportOutput(reportDir));
        visitor.visitInfo(context.getExecFileLoader().getSessionInfoStore().getInfos(),
                context.getExecFileLoader().getExecutionDataStore().getContents());

        visitor.visitBundle(context.getCoverage(),
                new DirectorySourceFileLocator(context.getSourceDir(), "utf-8", 4));

        visitor.visitEnd();

        new JacocoSiteStyler(reportDir).applyChanges();
    }
}
