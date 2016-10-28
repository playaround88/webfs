CREATE TABLE
    file_meta
    (
        id bigint NOT NULL AUTO_INCREMENT,
        ori_name VARCHAR(30) NOT NULL,
        save_name VARCHAR(20) NOT NULL,
        fsize bigint NOT NULL,
        file_url VARCHAR(256) NOT NULL,
        del_url VARCHAR(256) NOT NULL,
        del_type VARCHAR(10),
        catalog VARCHAR(20) DEFAULT 'default' NOT NULL,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf-8;