package com.auc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aas.auc.cache.util.CacheUtil;
import com.auc.service.CommentService;

public class AUCDatabaseConnectionUtil {
	
	private final static Logger _LOGGER = LoggerFactory.getLogger(CommentService.class);

	public static Connection getCommentDbConnection() throws SQLException {
		Connection driverManager = null;
		final String url_ = CacheUtil.getCustomProperties().get("AUC_COMMENT_MANAGEMENT_URL");
		final String user_ = CacheUtil.getCustomProperties().get("AUC_COMMENT_MANAGEMENT_USERNAME");
		final String password_ = CacheUtil.getCustomProperties().get("AUC_COMMENT_MANAeGMENT_PASSWORD");
		driverManager = DriverManager.getConnection(url_, user_, password_);
		return driverManager;
	}

	public static void closeConnection(Connection connection, CallableStatement callableStatement, ResultSet resultSet)
			throws SQLException {
		if (connection != null)
			connection.close();
		if (callableStatement != null)
			callableStatement.close();
		if (resultSet != null)
			resultSet.close();
	}

}
