package com.lapin.di.config;

import java.nio.file.Path;

public class JavaConfiguration implements Configuration {
    @Override
    public String getPackageToScan() {
        return "com.lapin.common";
    }

}
