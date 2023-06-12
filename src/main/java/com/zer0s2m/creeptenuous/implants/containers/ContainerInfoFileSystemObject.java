package com.zer0s2m.creeptenuous.implants.containers;

import java.nio.file.Path;

public record ContainerInfoFileSystemObject(
        Path source,

        String systemName,

        Boolean isFile,

        Boolean isDirectory
) { }
