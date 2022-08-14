CREATE TABLE developer (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100),
	age INTEGER,
	gender VARCHAR(10)
	);

ALTER TABLE developer
ADD CONSTRAINT gender_enum_values
CHECK (gender IN ('male', 'female', 'unknown'));

ALTER TABLE developer
ALTER COLUMN gender VARCHAR(10);

CREATE TABLE project (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    description VARCHAR (200)
);

CREATE TABLE project_developer (
    project_id BIGINT NOT NULL,
    developer_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, developer_id),
    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY (developer_id) REFERENCES developer(id)
);

CREATE TABLE skill (
    id IDENTITY PRIMARY KEY,
    branch VARCHAR(200),
    level VARCHAR(150)
);

CREATE TABLE developer_skill (
    developer_id BIGINT NOT NULL,
	skill_id BIGINT NOT NULL,
    PRIMARY KEY (developer_id, skill_id),
    FOREIGN KEY (developer_id) REFERENCES developer(id),
	FOREIGN KEY(skill_id) REFERENCES skill(id)
);


CREATE TABLE company (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    country VARCHAR(150)
);

CREATE TABLE customer (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    description VARCHAR(150)
);

CREATE TABLE project_customer (
    project_id BIGINT NOT NULL,
	customer_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, project_id),
    FOREIGN KEY(customer_id) REFERENCES customer(id),
    FOREIGN KEY (project_id) REFERENCES project(id)
);

CREATE TABLE company_developer (
    developer_id BIGINT NOT NULL,
    company_id BIGINT NOT NULL,
    PRIMARY KEY (developer_id, company_id),
    FOREIGN KEY(developer_id) REFERENCES developer(id),
    FOREIGN KEY (company_id) REFERENCES company(id)
);

