package com.niklasarndt.testing.generate.util;

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import java.io.File;

/**
 * Created by Niklas on 2020/07/28.
 */
public class GeneratorContext {
    private final File dir;
    private final File execDataFile;
    private final File sourceDir;
    private final File targetDir;
    private final File pagesDir;
    private final File coverageDir;
    private final ExecFileLoader execFileLoader;
    private final IBundleCoverage coverage;

    public GeneratorContext(File dir, File execDataFile,
                            File sourceDir, File targetDir, File pagesDir,
                            File coverageDir, ExecFileLoader execFileLoader,
                            IBundleCoverage coverage) {
        this.dir = dir;
        this.execDataFile = execDataFile;
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.pagesDir = pagesDir;
        this.coverageDir = coverageDir;
        this.execFileLoader = execFileLoader;
        this.coverage = coverage;
    }

    public File getDir() {
        return dir;
    }

    public String getTitle() {
        return dir.getName();
    }

    public File getExecDataFile() {
        return execDataFile;
    }

    public File getSourceDir() {
        return sourceDir;
    }

    public File getTargetDir() {
        return targetDir;
    }

    public File getPagesDir() {
        return pagesDir;
    }

    public File getCoverageDir() {
        return coverageDir;
    }

    public ExecFileLoader getExecFileLoader() {
        return execFileLoader;
    }

    public IBundleCoverage getCoverage() {
        return coverage;
    }
}
