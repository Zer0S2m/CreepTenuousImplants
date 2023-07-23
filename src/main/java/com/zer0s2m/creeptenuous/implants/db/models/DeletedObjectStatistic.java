package com.zer0s2m.creeptenuous.implants.db.models;

import com.zer0s2m.creeptenuous.implants.enums.TypeObjectDeleted;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "\"deleted_object_statistics\"")
public class DeletedObjectStatistic {

    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "DeletedObjectStatisticsSequence",
            sequenceName = "deleted_object_statistics_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DeletedObjectStatisticsSequence")
    private Long id;

    @Column(name = "user_login", length = 30, columnDefinition = "VARCHAR(30) DEFAULT 'NOT_LOGIN'")
    private String userLogin = "NOT_LOGIN";

    @Column(name = "type_object")
    @Enumerated(EnumType.STRING)
    private TypeObjectDeleted typeObject;

    @Column(name = "system_name", columnDefinition = "TEXT DEFAULT 'NO_PATH'")
    private String systemName;

    @Column(name = "system_path", columnDefinition = "TEXT DEFAULT 'NO_PATH'")
    private String systemPath = "NO_PATH";

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false)
    private Timestamp createdAt;

    public DeletedObjectStatistic() {}

    public DeletedObjectStatistic(String userLogin, TypeObjectDeleted typeObject) {
        this.userLogin = userLogin;
        this.typeObject = typeObject;
    }

    public DeletedObjectStatistic(String systemName, String systemPath, TypeObjectDeleted typeObject) {
        this.systemName = systemName;
        this.systemPath = systemPath;
        this.typeObject = typeObject;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setTypeObject(TypeObjectDeleted typeObject) {
        this.typeObject = typeObject;
    }

}
