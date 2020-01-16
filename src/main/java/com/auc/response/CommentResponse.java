package com.auc.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.auc.model.Comment;

@XmlRootElement
public class CommentResponse {
	private String message;
	private List<Comment> comments;
	private Comment comment;
	private int resultCount; 

	public CommentResponse(List<Comment> comments, int resultCount) {
		super();
		this.comments = comments;
		this.resultCount = resultCount;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public CommentResponse(String message, List<Comment> comments) {
		super();
		this.message = message;
		this.comments = comments;
	}

	public CommentResponse(String message, Comment comment) {
		super();
		this.message = message;
		this.comment = comment;
	}

	public CommentResponse(String message) {
		super();
		this.message = message;
	}

	public CommentResponse() {
		super();
	}

	public CommentResponse(List<Comment> comments) {
		super();
		this.comments = comments;
	}

	public CommentResponse(Comment comment) {
		super();
		this.comment = comment;
	}

}
