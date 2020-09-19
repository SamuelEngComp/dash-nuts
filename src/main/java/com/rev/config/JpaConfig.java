package com.rev.config;

//@Configuration
public class JpaConfig {
	/*
	 * @Profile("local")
	 * 
	 * @Bean public DataSource dataSource() { JndiDataSourceLookup dataSourceLookup
	 * = new JndiDataSourceLookup(); dataSourceLookup.setResourceRef(true); return
	 * dataSourceLookup.getDataSource("jdbc/projetobase"); }
	 * 
	 * @Profile("prod")
	 * 
	 * @Bean public DataSource dataSourceProd() throws URISyntaxException { URI
	 * jdbUri = new URI(System.getenv("JAWSDB_URL"));
	 * 
	 * String username = jdbUri.getUserInfo().split(":")[0]; String password =
	 * jdbUri.getUserInfo().split(":")[1]; String port =
	 * String.valueOf(jdbUri.getPort()); String jdbUrl = "jdbc:mysql://" +
	 * jdbUri.getHost() + ":" + port + jdbUri.getPath();
	 * 
	 * BasicDataSource dataSource = new BasicDataSource();
	 * dataSource.setUrl(jdbUrl); dataSource.setUsername(username);
	 * dataSource.setPassword(password); dataSource.setInitialSize(10); return
	 * dataSource; }
	 * 
	 * @Bean public JpaVendorAdapter jpaVendorAdapter() { HibernateJpaVendorAdapter
	 * adapter = new HibernateJpaVendorAdapter();
	 * adapter.setDatabase(Database.MYSQL); adapter.setShowSql(false);
	 * adapter.setGenerateDdl(false);
	 * adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect"); return
	 * adapter; }
	 */
}
