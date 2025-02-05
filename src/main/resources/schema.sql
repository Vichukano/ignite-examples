create table if not exists Items(
    id bigserial primary key,
    created_at timestamptz not null default current_timestamp
);