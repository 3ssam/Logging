package com.auc.dao;

import java.sql.SQLException;
import java.util.List;

import com.auc.model.Comment;


public interface CommentDAO {
	public boolean addComment(Comment comment) throws SQLException;
	public Comment getCommentById(long commentId) throws SQLException;
	public List<Comment> getPendingComments(int start,int end) throws SQLException;
	public List<Comment> getApprovedComments(long recordId) throws SQLException;
	public Comment deleteCommentById(long commentId) throws SQLException;
	public boolean updateCommentById(long commentId) throws SQLException;
	public int countPendingComments() throws SQLException;
}
