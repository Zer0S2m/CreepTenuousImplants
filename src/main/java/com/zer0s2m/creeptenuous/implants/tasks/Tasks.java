package com.zer0s2m.creeptenuous.implants.tasks;

import com.zer0s2m.creeptenuous.implants.core.RootPath;
import com.zer0s2m.creeptenuous.implants.redis.repository.DirectoryRedisRepository;
import com.zer0s2m.creeptenuous.implants.redis.repository.FileRedisRepository;
import com.zer0s2m.creeptenuous.implants.services.WalkDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Tasks {

    private final DirectoryRedisRepository directoryRedisRepository;

    private final FileRedisRepository fileRedisRepository;

    private static final Logger log = LoggerFactory.getLogger(Tasks.class);

    private final RootPath rootPath;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public Tasks(DirectoryRedisRepository directoryRedisRepository, FileRedisRepository fileRedisRepository,
                 RootPath rootPath) {
        this.directoryRedisRepository = directoryRedisRepository;
        this.fileRedisRepository = fileRedisRepository;
        this.rootPath = rootPath;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws IOException {
        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("Count directories in redis {}", directoryRedisRepository.count());
        log.info("Count files in redis {}", fileRedisRepository.count());
        log.info("Root path {}", rootPath.getRootPath().toString());
        log.info("Count file system object {}", WalkDirectory.walkDirectory(rootPath.getRootPath()).size());
    }

}
