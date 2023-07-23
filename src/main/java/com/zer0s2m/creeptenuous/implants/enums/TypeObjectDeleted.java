package com.zer0s2m.creeptenuous.implants.enums;

/**
 * Deleted dead object type storage
 */
public enum TypeObjectDeleted {

    /**
     * File object of type - directory
     */
    DIRECTORY,

    /**
     * File object of type - file
     */
    FILE,

    /**
     * Object type - Redis hash of the directory
     */
    DIRECTORY_REDIS,

    /**
     * Object type - Redis hash of the file
     */
    FILE_REDIS,

    /**
     * Object type - the right for the user to interact with file objects
     */
    RIGHT_USER,

    /**
     * Unrecognized object type
     */
    UNIDENTIFIED_TYPE

}
