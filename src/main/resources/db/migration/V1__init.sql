
CREATE TABLE IF NOT EXISTS student_groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL UNIQUE,
    group_id BIGINT NOT NULL REFERENCES student_groups(id)
);
CREATE TABLE IF NOT EXISTS lectors (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS disciplines (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS lessons (
    id BIGSERIAL PRIMARY KEY,
    discipline_id BIGINT NOT NULL REFERENCES disciplines(id),
    group_id BIGINT NOT NULL REFERENCES student_groups(id),
    lector_id BIGINT NOT NULL REFERENCES lectors(id),
    date DATE NOT NULL,
    lesson_number INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS attendances (
    id BIGSERIAL PRIMARY KEY,
    lesson_id BIGINT NOT NULL REFERENCES lessons(id),
    student_id BIGINT NOT NULL REFERENCES students(id),
    is_present BOOLEAN NOT NULL
);

