-- 한줄씩 블럭 설정 후 alt + c 실행
create user test identified by test
grant resource, connect to test
alter user test default tablespace users
alter user test temporary tablespace temp