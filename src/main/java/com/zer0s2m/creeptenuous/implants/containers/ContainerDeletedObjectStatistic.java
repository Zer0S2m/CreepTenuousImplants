package com.zer0s2m.creeptenuous.implants.containers;

import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;

import java.sql.Timestamp;

public record ContainerDeletedObjectStatistic(

        String userLogin,

        String systemName,

        String systemPath,

        TypeObjectDeleted typeObjectDeleted,

        Timestamp createdAt

) {
}
