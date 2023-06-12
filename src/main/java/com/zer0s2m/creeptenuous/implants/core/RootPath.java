package com.zer0s2m.creeptenuous.implants.core;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Part of the kernel of the filesystem root installation system.
 * <p>The root is specified from the <b>main system</b></p>
 */
@Component
public class RootPath {

    private Path rootPath;

    /**
     * System path root initialization
     * @param rootPath raw filesystem root
     */
    public RootPath(final @Value("${CT_ROOT_PATH}") @NotNull String strRootPath) {
        setRootPath(strRootPath);
    }

    /**
     * Mount filesystem root
     * @param strRootPath raw filesystem root
     */
    private void setRootPath(String strRootPath) {
        this.rootPath = Path.of(strRootPath);
    }

    /**
     * Get the root of the file system
     * @return filesystem root
     */
    public Path getRootPath() {
        return this.rootPath;
    }

}
