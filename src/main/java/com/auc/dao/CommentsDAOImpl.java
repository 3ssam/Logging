package com.auc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.aas.auc.cache.util.CacheUtil;
import com.auc.model.Comment;

public class CommentsDAOImpl implements CommentDAO {

	public CommentsDAOImpl() {
		CacheUtil.loadAll("jdbc/AUCCP");
	}

	public boolean addComment(Comment comment) throws SQLException {
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		String title = getItemTitle(comment.getRecordId(), 1);
		if (title == null)
			title = getItemTitle(comment.getRecordId(), 0);
		String type = getItemType(comment.getRecordId(), 1);
		if (type == null)
			type = getItemType(comment.getRecordId(), 0);
		callableStatement = connection.prepareCall("{call arabauthorscomment_insertpendingcomments(?,?,?,?,?,?)}");
		callableStatement.setInt(1, comment.getRecordId());
		callableStatement.setString(2, comment.getUsername());
		callableStatement.setString(3, comment.getCommentContent());
		callableStatement.setInt(4, comment.getUserId());
		callableStatement.setString(5, type);
		callableStatement.setString(6, title);
		resultSet = callableStatement.executeQuery();
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return true;
	}

	public Comment getCommentById(long commentId) throws SQLException {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		Comment comment = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		callableStatement = connection.prepareCall("{call arabauthorscomment_retrivebyid(?)}");
		callableStatement.setInt(1, (int) commentId);
		resultSet = callableStatement.executeQuery();
		while (resultSet.next()) {
			if (resultSet.getDate(3) == null)
				continue;
			comment = new Comment();
			comment.setRecordId(resultSet.getInt(1));
			comment.setUsername(resultSet.getString(2));
			SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm aa");
			String CommentDate = formatterDate.format(resultSet.getDate(3));
			String CommentTime = formatterTime.format(resultSet.getTime(3));
			comment.setCommentDate(CommentDate);
			comment.setCommentTime(CommentTime);
			comment.setCommentContent(resultSet.getString(4));
			comment.setUserId(resultSet.getInt(5));
			comment.setRecordType(resultSet.getString(6));
			comment.setRecordTitle(resultSet.getString(7));
		}
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return comment;
	}

	public Comment deleteCommentById(long commentId) throws SQLException {
		Comment comment = getCommentById(commentId);
		if (comment == null)
			return null;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		callableStatement = connection.prepareCall("{call arabauthorscomment_deletebyid(?)}");
		callableStatement.setInt(1, (int) commentId);
		resultSet = callableStatement.executeQuery();
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return comment;
	}

	public boolean updateCommentById(long commentId) throws SQLException {
		Comment comment = getCommentById(commentId);
		if (comment == null)
			return false;
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		callableStatement = connection.prepareCall("{call arabauthorscomment_approvecomments(?)}");
		callableStatement.setInt(1, (int) commentId);
		resultSet = callableStatement.executeQuery();
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return true;
	}

	private String getItemTitle(int recordId, int flag) throws SQLException {
		String title = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		if (flag == 1)
			callableStatement = connection.prepareCall("{call getitemtitlebib1(?)}");
		else
			callableStatement = connection.prepareCall("{call getitemtitlebib2(?)}");
		callableStatement.setInt(1, recordId);
		resultSet = callableStatement.executeQuery();
		while (resultSet.next()) {
			title = resultSet.getString(1);
		}
		if (title != null)
			title = EditString(title);
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return title;
	}

	private String getItemType(int recordId, int flag) throws SQLException {
		String Type = null;
		CallableStatement callableStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		if (flag == 1)
			callableStatement = connection.prepareCall("{call getItemTypeBib1(?)}");
		else
			callableStatement = connection.prepareCall("{call getItemTypeBib2(?)}");
		callableStatement.setInt(1, recordId);
		resultSet = callableStatement.executeQuery();
		while (resultSet.next()) {
			Type = resultSet.getString(1);
		}
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return Type;
	}

	public List<Comment> getPendingComments(int start, int end) throws SQLException {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		List<Comment> comments = new ArrayList<>();
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		callableStatement = connection.prepareCall("{call arabauthorscomment_retrievependingcomments(?,?)}");
		callableStatement.setInt(1, start);
		callableStatement.setInt(2, end);
		resultSet = callableStatement.executeQuery();
		Comment comment;
		while (resultSet.next()) {
			if (resultSet.getDate(4) == null)
				continue;
			comment = new Comment();
			comment.setCommentId(resultSet.getInt(1));
			comment.setRecordId(resultSet.getInt(2));
			comment.setUsername(resultSet.getString(3));
			SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm aa");
			String CommentDate = formatterDate.format(resultSet.getDate(4));
			String CommentTime = formatterTime.format(resultSet.getTime(4));
			comment.setCommentDate(CommentDate);
			comment.setCommentTime(CommentTime);
			comment.setCommentContent(resultSet.getString(5));
			comment.setUserId(resultSet.getInt(6));
			comment.setRecordType(resultSet.getString(7));
			comment.setRecordTitle(resultSet.getString(8));
			comments.add(comment);
		}
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return comments;
	}

	public List<Comment> getApprovedComments(long recordId) throws SQLException {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		List<Comment> comments = new ArrayList<Comment>();
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		callableStatement = connection.prepareCall("{call arabauthorscomment_retrieveApprovedComments(?)}");
		callableStatement.setInt(1, (int) recordId);
		resultSet = callableStatement.executeQuery();
		Comment comment;
		while (resultSet.next()) {
			if (resultSet.getDate(4) == null)
				continue;
			comment = new Comment();
			comment.setCommentId(resultSet.getInt(1));
			comment.setRecordId((int) recordId);
			comment.setUsername(resultSet.getString(3));
			SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm aa");
			String CommentDate = formatterDate.format(resultSet.getDate(4));
			String CommentTime = formatterTime.format(resultSet.getTime(4));
			comment.setCommentDate(CommentDate);
			comment.setCommentTime(CommentTime);
			comment.setCommentContent(resultSet.getString(5));
			comment.setUserId(resultSet.getInt(6));
			comment.setRecordType(resultSet.getString(7));
			comment.setRecordTitle(resultSet.getString(8));
			comments.add(comment);
		}
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return comments;
	}

	public int countPendingComments() throws SQLException {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		connection = AUCDatabaseConnectionUtil.getCommentDbConnection();
		callableStatement = connection.prepareCall("{call arabauthorscomment_countpendingcomments()}");
		resultSet = callableStatement.executeQuery();
		int resultCount = -1;
		while (resultSet.next()) {
			resultCount = resultSet.getInt(1);
		}
		AUCDatabaseConnectionUtil.closeConnection(connection, callableStatement, resultSet);
		return resultCount;
	}

	private String EditString(String text) {
		StringBuilder line = new StringBuilder();
		line.append(text);
		int index = line.indexOf("$");
		while (index != -1) {
			line.replace(index, index+2, " ");
			index = line.indexOf("$");
		}
		return line.toString();
	}

}
