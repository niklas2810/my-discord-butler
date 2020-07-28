package com.niklasarndt.testing.generate;

import java.io.File;
import java.io.IOException;

/**
 * Created by Niklas on 2020/07/28.
 */
public class IndexGenerator {
    private final File target;

    public IndexGenerator(File target) {

        this.target = target;
    }

    public void create() throws IOException {
        CustomResources resources = new CustomResources("index", target);
        resources.copyResources();
    }
}
