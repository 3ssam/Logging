package com.auc.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.auc.model.Comment;

public class ResponseWriterUtility {

	public static JSONObject WriteResponse(List<Comment> comments, int resultCount) {
		JSONObject ja = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		for (Comment comment : comments) {
			object = new JSONObject();
			if (comment.getCommentDate() == null || comment.getCommentTime() == null)
				continue;
			object.put("commentContent", comment.getCommentContent());
			object.put("commentDate", comment.getCommentDate());
			object.put("commentTime", comment.getCommentTime());
			object.put("recordId", comment.getRecordId());
			object.put("username", comment.getUsername());
			object.put("commentId", comment.getCommentId());
			object.put("userId", comment.getUserId());
			object.put("recordType", comment.getRecordType());
			object.put("recordTitle", comment.getRecordTitle());
			array.add(object);
		}
		ja.put("comments", array);
		ja.put("resultCount", resultCount);
		return ja;
	}
}
