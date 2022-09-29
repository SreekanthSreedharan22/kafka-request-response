package com.learning.kafkaintegration.model;

import lombok.Data;


@Data
public class WikiChange {

    private String _id;
    private Meta meta;
    private String type;
    private Integer namespace;
    private String title;
    private String comment;
    private Long timestamp;
    private String user;
    private Boolean bot;
    private Boolean minor;
    private String server_url;
    private String server_name;
    private String server_script_path;
    private Boolean patrolled;
    private Length length;
    private Revision revision;
    private String serverUrl;
    private String serverName;
    private String serverScriptPath;
    private String wiki;
    private String parsedcomment;

}
