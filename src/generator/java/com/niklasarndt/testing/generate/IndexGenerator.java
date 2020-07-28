package com.niklasarndt.testing.generate;

import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 2020/07/28.
 */
public class IndexGenerator {
    private final File target;

    public IndexGenerator(GeneratorContext context) {

        this.target = context.getPagesDir();
    }

    public void create() throws IOException {
        CustomResources resources = new CustomResources("index", target);
        resources.copyResources();
    }
}
