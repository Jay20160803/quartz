package org.jdonee.cooking.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.redis")
public class MyRedisProperties {

	/**
	 * Database index used by the connection factory.
	 */
	private int database = 0;

	private int secondaryDatabase = 1;

	/**
	 * Redis url, which will overrule host, port and password if set.
	 */
	private String url;

	/**
	 * Redis server host.
	 */
	private String host = "localhost";

	/**
	 * Login password of the redis server.
	 */
	private String password;

	/**
	 * Redis server port.
	 */
	private int port = 6379;

	/**
	 * Enable SSL.
	 */
	private boolean ssl;

	/**
	 * Connection timeout in milliseconds.
	 */
	private int timeout;

	private int sessionExpire;

	private Pool pool;

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getSecondaryDatabase() {
		return secondaryDatabase;
	}

	public void setSecondaryDatabase(int secondaryDatabase) {
		this.secondaryDatabase = secondaryDatabase;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getSessionExpire() {
		return sessionExpire;
	}

	public void setSessionExpire(int sessionExpire) {
		this.sessionExpire = sessionExpire;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	/**
	 * Pool properties.
	 */
	public static class Pool {
		/**
		 * Max number of "idle" connections in the pool. Use a negative value to indicate
		 * an unlimited number of idle connections.
		 */
		private int maxIdle = 8;

		/**
		 * Target for the minimum number of idle connections to maintain in the pool. This
		 * setting only has an effect if it is positive.
		 */
		private int minIdle = 0;

		/**
		 * Max number of connections that can be allocated by the pool at a given time.
		 * Use a negative value for no limit.
		 */
		private int maxActive = 8;

		/**
		 * Maximum amount of time (in milliseconds) a connection allocation should block
		 * before throwing an exception when the pool is exhausted. Use a negative value
		 * to block indefinitely.
		 */
		private int maxWait = -1;


		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}
	}

}
