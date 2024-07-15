CREATE TABLE account
(
    id          BLOB           NOT NULL,
    code        VARCHAR(255)   NULL,
    created_at  datetime       NULL,
    created_by  VARCHAR(255)   NULL,
    expense_sum DECIMAL(38, 2) NULL,
    income_sum  DECIMAL(38, 2) NULL,
    password    VARCHAR(255)   NULL,
    status      INT            NULL,
    update_at   datetime       NULL,
    updated_by  VARCHAR(255)   NULL,
    username    VARCHAR(255)   NULL,
    role_id     BLOB           NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE category
(
    id         BLOB         NOT NULL,
    created_at datetime     NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    status     ENUM         NOT NULL,
    updated_at datetime     NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    account_id BLOB         NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE expense
(
    id                BLOB           NOT NULL,
    amount            DECIMAL(38, 2) NULL,
    created_at        datetime       NOT NULL,
    created_by        VARCHAR(255)   NOT NULL,
    expended_at       date           NULL,
    updated_at        datetime       NOT NULL,
    updated_by        VARCHAR(255)   NOT NULL,
    account_id        BLOB           NOT NULL,
    category_id       BLOB           NOT NULL,
    monthly_log_month TINYINT        NOT NULL,
    monthly_log_year  INT            NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE income
(
    id                BLOB           NOT NULL,
    amount            DECIMAL(38, 2) NOT NULL,
    created_at        datetime       NOT NULL,
    created_by        VARCHAR(255)   NOT NULL,
    incomed_at        date           NOT NULL,
    status            ENUM           NOT NULL,
    type              ENUM           NOT NULL,
    updated_at        datetime       NOT NULL,
    updated_by        VARCHAR(255)   NOT NULL,
    account_id        BLOB           NOT NULL,
    monthly_log_month TINYINT        NOT NULL,
    monthly_log_year  INT            NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE monthly_log
(
    month       TINYINT        NOT NULL,
    year        INT            NOT NULL,
    budget      DECIMAL(38, 2) NOT NULL,
    created_at  datetime       NOT NULL,
    created_by  VARCHAR(255)   NOT NULL,
    expense_sum DECIMAL(38, 2) NOT NULL,
    income_sum  DECIMAL(38, 2) NOT NULL,
    updated_at  datetime       NOT NULL,
    updated_by  VARCHAR(255)   NOT NULL,
    account_id  BLOB           NULL,
    category_id BLOB           NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (month, year)
);

CREATE TABLE `role`
(
    id            BLOB         NOT NULL,
    created_at    datetime     NULL,
    created_by    VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    status        INT          NULL,
    update_at     datetime     NULL,
    updated_by    VARCHAR(255) NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         BLOB         NOT NULL,
    birthday   datetime     NULL,
    code       VARCHAR(255) NULL,
    created_at datetime     NULL,
    created_by VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    gender     INT          NULL,
    last_name  VARCHAR(255) NULL,
    phone      VARCHAR(255) NULL,
    status     INT          NULL,
    update_at  datetime     NULL,
    updated_by VARCHAR(255) NULL,
    account_id BLOB         NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT UK1yov8c5ew74vlt8qra6cewq99 UNIQUE (account_id);

ALTER TABLE users
    ADD CONSTRAINT FK3pwaj86pwopu3ot96qlrfo2up FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE NO ACTION;

ALTER TABLE category
    ADD CONSTRAINT FK6ymhc01g9q7834m0220ruu83m FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE NO ACTION;

CREATE INDEX FK6ymhc01g9q7834m0220ruu83m ON category (account_id);

ALTER TABLE account
    ADD CONSTRAINT FKd4vb66o896tay3yy52oqxr9w0 FOREIGN KEY (role_id) REFERENCES `role` (id) ON DELETE NO ACTION;

CREATE INDEX FKd4vb66o896tay3yy52oqxr9w0 ON account (role_id);

ALTER TABLE monthly_log
    ADD CONSTRAINT FKeud29du2c3l9sr8xfc3jsl8j6 FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE NO ACTION;

CREATE INDEX FKeud29du2c3l9sr8xfc3jsl8j6 ON monthly_log (account_id);

ALTER TABLE income
    ADD CONSTRAINT FKilqen2njdmcsuxvnpjgixiqtt FOREIGN KEY (monthly_log_month, monthly_log_year) REFERENCES monthly_log (month, year) ON DELETE NO ACTION;

CREATE INDEX FKilqen2njdmcsuxvnpjgixiqtt ON income (monthly_log_month, monthly_log_year);

ALTER TABLE income
    ADD CONSTRAINT FKin0g6pdepytwjv6dvcqi45qq3 FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE NO ACTION;

CREATE INDEX FKin0g6pdepytwjv6dvcqi45qq3 ON income (account_id);

ALTER TABLE monthly_log
    ADD CONSTRAINT FKinmr1s11mwwkk3lhcpd8b5u9p FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION;

CREATE INDEX FKinmr1s11mwwkk3lhcpd8b5u9p ON monthly_log (category_id);

ALTER TABLE expense
    ADD CONSTRAINT FKl7p96uyhqlv3raecactp2uet4 FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE NO ACTION;

CREATE INDEX FKl7p96uyhqlv3raecactp2uet4 ON expense (account_id);

ALTER TABLE expense
    ADD CONSTRAINT FKmvjm59reb5i075vu38bwcaqj6 FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION;

CREATE INDEX FKmvjm59reb5i075vu38bwcaqj6 ON expense (category_id);

ALTER TABLE expense
    ADD CONSTRAINT FKn0wd64ex837tt3aiy9ymw3nt2 FOREIGN KEY (monthly_log_month, monthly_log_year) REFERENCES monthly_log (month, year) ON DELETE NO ACTION;

CREATE INDEX FKn0wd64ex837tt3aiy9ymw3nt2 ON expense (monthly_log_month, monthly_log_year);