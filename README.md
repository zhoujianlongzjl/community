## 码匠社区

## 资料
[Spring 文档](https://spring.io/guides)

[Spring web](https://spring.io/guides/gs/serving-web-content/)

[Github OAuth](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)

[Mysql 菜鸟教程](https://www.runoob.com/mysql/mysql-tutorial.html)

[Bootstrap](https://v3.bootcss.com/)

[Markdown 插件](https://pandao.github.io/editor.md/)
## 工具
[Git](https://git-scm.com/downloads)

[Maven](https://mvnrepository.com/)

[Lombok](https://projectlombok.org/)

## 脚本
```sql
create table user
(
	id int auto_increment
		primary key,
	account_id varchar(100) null,
	name varchar(50) null,
	token char(36) null,
	gmt_create bigint null,
	gmt_modified bigint null
);
```
```text
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

